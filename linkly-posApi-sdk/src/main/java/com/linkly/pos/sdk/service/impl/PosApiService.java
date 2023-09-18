package com.linkly.pos.sdk.service.impl;

import static com.linkly.pos.sdk.common.MoshiUtil.getAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import com.linkly.pos.sdk.common.ApiEndpoints;
import com.linkly.pos.sdk.common.ApiResponse;
import com.linkly.pos.sdk.common.ApiType;
import com.linkly.pos.sdk.common.AuthTokenExtensions;
import com.linkly.pos.sdk.common.Constants.ResponseType;
import com.linkly.pos.sdk.common.HttpStatusCodeUtil;
import com.linkly.pos.sdk.common.JSONUtil;
import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.common.StringUtil;
import com.linkly.pos.sdk.models.ApiServiceEndpoint;
import com.linkly.pos.sdk.models.AuthToken;
import com.linkly.pos.sdk.models.ErrorResponse;
import com.linkly.pos.sdk.models.IBaseRequest;
import com.linkly.pos.sdk.models.IValidatable;
import com.linkly.pos.sdk.models.PosApiRequest;
import com.linkly.pos.sdk.models.PosApiResponse;
import com.linkly.pos.sdk.models.PosApiServiceOptions;
import com.linkly.pos.sdk.models.PosVendorDetails;
import com.linkly.pos.sdk.models.authentication.PairingRequest;
import com.linkly.pos.sdk.models.authentication.PairingResponse;
import com.linkly.pos.sdk.models.authentication.TokenRequest;
import com.linkly.pos.sdk.models.authentication.TokenResponse;
import com.linkly.pos.sdk.models.configureMerchant.ConfigureMerchantRequest;
import com.linkly.pos.sdk.models.configureMerchant.ConfigureMerchantResponse;
import com.linkly.pos.sdk.models.display.DisplayResponse;
import com.linkly.pos.sdk.models.enums.ErrorSource;
import com.linkly.pos.sdk.models.logon.LogonRequest;
import com.linkly.pos.sdk.models.logon.LogonResponse;
import com.linkly.pos.sdk.models.queryCard.QueryCardRequest;
import com.linkly.pos.sdk.models.queryCard.QueryCardResponse;
import com.linkly.pos.sdk.models.receipt.ReceiptResponse;
import com.linkly.pos.sdk.models.reprintReceipt.ReprintReceiptRequest;
import com.linkly.pos.sdk.models.reprintReceipt.ReprintReceiptResponse;
import com.linkly.pos.sdk.models.result.ResultRequest;
import com.linkly.pos.sdk.models.sendKey.SendKeyRequest;
import com.linkly.pos.sdk.models.settlement.SettlementRequest;
import com.linkly.pos.sdk.models.settlement.SettlementResponse;
import com.linkly.pos.sdk.models.status.StatusRequest;
import com.linkly.pos.sdk.models.status.StatusResponse;
import com.linkly.pos.sdk.models.transaction.RetrieveTransactionRequest;
import com.linkly.pos.sdk.models.transaction.TransactionRequest;
import com.linkly.pos.sdk.models.transaction.TransactionResponse;
import com.linkly.pos.sdk.service.IHttpAsyncExecutor;
import com.linkly.pos.sdk.service.IPosApiEventListener;
import com.linkly.pos.sdk.service.IPosApiService;
import com.squareup.moshi.Types;

import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * POS API service for communicating with the Cloud POS API v2. This service adopts an event-driven
 * architecture to facilitate asynchronous communication with the API. Requests will be sent to the
 * API in a thread and the result returned via the corresponding {@link IPosApiEventListener}
 * method.
 * 
 * The user of this service must provided an implementation of {@link IPosApiEventListener}
 * which handles all the methods accordingly.
 * 
 * To use this service the PIN pad must first be paired using {@link PairingRequest} or if
 * the PIN pad was previously paired use {@link SetPairSecret} to set the pairing secret.
 */
public class PosApiService implements IPosApiService {

    private static final String COMMON_REQUEST_WRAPPER = "request";

    private final IPosApiEventListener eventListener;
    private final PosVendorDetails posVendorDetails;
    private final PosApiServiceOptions options;
    private final ApiServiceEndpoint serviceEndpoints;
    private final IHttpAsyncExecutor asyncHttpExecutor;
    private final Logger logger;

    private AuthToken authToken;
    private String pairSecret;

    /**
     * Initialise a new POS API service.
     * 
     * @param eventListener
     *            Listener for all events triggered by this service.
     * @param httpClient
     *            Optional client for sending requests to the API. If null a new instance will be
     *            created.
     * @param posVendorDetails
     *            Identification of the POS vendor - client of this service.
     * @param options
     *            Optional service options.
     * @param serviceEndpoints
     *            Auth and POS API endpoint URIs.
     * @param logger
     *            custom implementation of {@link java.util.logging.Logging Logger}
     */
    public PosApiService(IPosApiEventListener eventListener, AsyncHttpClient httpClient,
        PosVendorDetails posVendorDetails, PosApiServiceOptions options,
        ApiServiceEndpoint serviceEndpoints, Logger logger) {
        this.eventListener = new PosApiEventListenerProxy(eventListener);
        httpClient = httpClient == null
            ? Dsl.asyncHttpClient()
            : httpClient;
        this.asyncHttpExecutor = new HttpAsyncExecutor(httpClient);
        this.posVendorDetails = posVendorDetails;
        this.options = options;
        this.serviceEndpoints = serviceEndpoints;
        this.logger = logger;
    }

    /**
     * {@inheritDoc}
     * Implementation of setting the pair secret obtained from initial pairing.
     */
    @Override
    public void setPairSecret(String pairSecret) {
        logger.log(Level.INFO, "Setting pair-secret");
        if (StringUtil.isNullOrWhiteSpace(pairSecret)) {
            logger.log(Level.WARNING, "pairSecret must not be null or whitespace");
            throw new IllegalArgumentException("Required pairSecret");
        }
        // Pair secret has not changed. Do nothing.
        if (this.pairSecret != null && this.pairSecret.equals(pairSecret)) {
            logger.log(Level.INFO, "Attempting to set the same pair-secret, aborting");
            return;
        }
        // Clearing the auth token will trigger a re-authentication.
        logger.log(Level.INFO, "Clearing auth token due to pair-secret update");
        authToken = null;
        this.pairSecret = pairSecret;
    }

    /**
     * {@inheritDoc}
     * Implementation of pairing with the PIN pad.
     */
    @Override
    public void pairingRequest(PairingRequest request) {
        logger.log(Level.INFO, "Pairing request for Username: {0}", new Object[] { request
            .getUsername() });

        String uri = uri(ApiType.AUTH, ApiEndpoints.PAIR_ENDPOINT);
        String requestBody = getAdapter(PairingRequest.class).toJson(request);

        Response completeResponse;
        try {
            completeResponse = asyncHttpExecutor.post(uri, requestBody);
        }
        catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(ErrorSource.API,
                HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), e.getMessage(), e);
            eventListener.error(null, request, errorResponse);
            throw e;
        }

        invokeErrorIfFailed(request, completeResponse, null);
        try {
            PairingResponse pairingResponse = MoshiUtil.fromJson(completeResponse
                .getResponseBody(), PairingResponse.class);
            setPairSecret(pairingResponse.getSecret());
            eventListener.pairingComplete(request, pairingResponse);
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "pairingRequest: Error: {0}", new Object[] { e
                .getMessage() });
            ErrorResponse errorResponse = new ErrorResponse(ErrorSource.Internal, null, e
                .getMessage(), e);
            eventListener.error(null, request, errorResponse);
        }
    }

    /**
     * {@inheritDoc}
     * Implementation of sending a logon request.
     */
    @Override
    public UUID logonRequest(LogonRequest request) {
        setPosVendorDetails(request);
        validatePairing("logonRequest");
        return executeCommon(request, ApiEndpoints.LOGON_ENDPOINT);
    }

    /**
     * {@inheritDoc}
     * Implementation of sending a transaction request.
     */
    @Override
    public UUID transactionRequest(TransactionRequest request) {
        logger.log(Level.INFO, "Starting transactionRequest");
        setPosVendorDetails(request);
        validatePairing("transactionRequest");
        return executeCommon(request, ApiEndpoints.TRANSACTION_ENDPOINT);
    }

    /**
     * {@inheritDoc}
     * Implementation of sending a status request.
     */
    @Override
    public UUID statusRequest(StatusRequest request) {
        logger.log(Level.INFO, "Starting statusRequest");
        setPosVendorDetails(request);
        validatePairing("statusRequest");
        return executeCommon(request, ApiEndpoints.STATUS_ENDPOINT);
    }

    /**
     * {@inheritDoc}
     * Implementation of sending a settlement request.
     */
    @Override
    public UUID settlementRequest(SettlementRequest request) {
        logger.log(Level.INFO, "Starting settlementRequest");
        setPosVendorDetails(request);
        validatePairing("settlementRequest");
        return executeCommon(request, ApiEndpoints.SETTLEMENT_ENDPOINT);
    }

    /**
     * {@inheritDoc}
     * Implementation of sending a query card request.
     */
    @Override
    public UUID queryCardRequest(QueryCardRequest request) {
        logger.log(Level.INFO, "Starting queryCardRequest");
        setPosVendorDetails(request);
        validatePairing("queryCardRequest");
        return executeCommon(request, ApiEndpoints.QUERYCARD_ENDPOINT);
    }

    /**
     * {@inheritDoc}
     * Implementation of sending a request to configure the merchant's PIN pad settings.
     */
    @Override
    public UUID configureMerchantRequest(ConfigureMerchantRequest request) {
        logger.log(Level.INFO, "Starting configureMerchantRequest");
        setPosVendorDetails(request);
        validatePairing("configureMerchantRequest");
        return executeCommon(request, ApiEndpoints.CONFIGURE_MERCHANT_ENDPOINT);
    }

    /**
     * {@inheritDoc}
     * Implementation of sending a request to reprint a previous receipt.
     */
    @Override
    public UUID reprintReceiptRequest(ReprintReceiptRequest request) {
        logger.log(Level.INFO, "Starting reprintReceiptRequest");
        setPosVendorDetails(request);
        validatePairing("reprintReceiptRequest");
        return executeCommon(request, ApiEndpoints.REPRINT_RECEIPT_ENDPOINT);
    }

    /**
     * {@inheritDoc}
     * Implementation of sending a key press to the PIN pad.
     */
    @Override
    public void sendKeyRequest(SendKeyRequest request) {
        logger.log(Level.INFO, "Starting sendKeyRequest");
        setPosVendorDetails(request);
        validatePairing("sendKeyRequest");
        validateRequest(request);

        String unformattedUri = uri(ApiType.POS, ApiEndpoints.SEND_KEY_ENDPOINT);
        String uri = MessageFormat.format(unformattedUri, request.getSessionId());
        ApiResponse response = sendPosRequestAsync(request, HttpMethod.POST, uri, request
            .getSessionId());
        if (!response.isSuccess()) {
            ErrorResponse errorResponse = new ErrorResponse(ErrorSource.API, response
                .getStatusCode(), response.getBody(), null);
            eventListener.error(request.getSessionId(), request, errorResponse);
        }
    }

    /**
     * {@inheritDoc}
     * Implementation of retrieving all the events for a session.
     */
    @Override
    public void resultRequest(ResultRequest request) {
        logger.log(Level.INFO, "Starting resultRequest");
        validatePairing("resultRequest");
        List<PosApiResponse> responses = sendResultRequestAsync(request, request.getSessionId(),
            true);
        eventListener.resultComplete(request, responses);
    }

    /**
     * {@inheritDoc}
     * Implementation of retrieving historical transaction results.
     */
    @Override
    public void retrieveTransactionRequest(RetrieveTransactionRequest request) {
        logger.log(Level.INFO, "Starting retrieveTransactionRequest");
        validatePairing("retrieveTransactionRequest");

        String unformattedUri = uri(ApiType.POS, ApiEndpoints.RETRIEVE_TRANSACTION_ENDPOINT);
        String uri = MessageFormat.format(unformattedUri, request.getReference(), request
            .getReferenceType());
        try {
            ApiResponse response = sendPosRequestAsync(request, HttpMethod.GET, uri, UUID
                .randomUUID());
            if (response.isSuccess()) {
                Type type = Types.newParameterizedType(List.class, TransactionResponse.class);
                List<TransactionResponse> transactionResponses = MoshiUtil.fromJson(response
                    .getBody(), type);
                eventListener.retrieveTransactionComplete(request, transactionResponses);
            }
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "retrieveTransactionRequest: Error: {0}", new Object[] { e
                .getMessage() });
            throw new UnsupportedOperationException("retrieveTransactionRequest: Error!", e);
        }
    }

    private <T extends IBaseRequest> void authenticate(T request, UUID uuid) {
        logger.log(Level.INFO, "authenticate");
        TokenRequest tokenRequest = new TokenRequest(this.pairSecret, posVendorDetails.getPosName(),
            posVendorDetails.getPosVersion(), posVendorDetails.getPosId(), posVendorDetails
                .getPosVendorId());

        String uri = uri(ApiType.AUTH, ApiEndpoints.TOKENS_ENDPOINT);
        String requestBody = getAdapter(TokenRequest.class).toJson(tokenRequest);
        Response completeResponse = null;
        try {
            completeResponse = asyncHttpExecutor.post(uri, requestBody);
            invokeErrorIfFailed(request, completeResponse, uuid);
        }
        catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(ErrorSource.API,
                HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), e.getMessage(), e);
            eventListener.error(uuid, request, errorResponse);
            throw e;
        }
        try {
            TokenResponse tokenResponse = MoshiUtil.fromJson(completeResponse.getResponseBody(),
                TokenResponse.class);
            LocalDateTime expiry = LocalDateTime.now().plus(tokenResponse.getExpirySeconds(),
                ChronoUnit.SECONDS);
            authToken = new AuthToken(tokenResponse.getToken(), expiry);

        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "authenticate: Error: {0}", new Object[] { e.getMessage() });
        }
    }

    private <T extends IBaseRequest> ApiResponse sendPosRequestAsync(T request,
        HttpMethod method, String uri, UUID sessionId) {

        if (authToken == null || authToken.isExpiringSoon()) {
            authenticate(request, sessionId);
        }
        Response completeResponse = null;
        String authHeaderValue = AuthTokenExtensions.getAuthenticationHeaderValue(authToken);

        try {
            if (HttpMethod.POST == method) {
                String requestBody = getAdapter(request.getClass()).toJson(request);
                requestBody = JSONUtil.wrapRequest(COMMON_REQUEST_WRAPPER, requestBody);
                completeResponse = asyncHttpExecutor.post(uri, authHeaderValue, requestBody);
            }
            else if (HttpMethod.GET == method) {
                completeResponse = asyncHttpExecutor.get(uri, authHeaderValue);
            }
        }
        catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(ErrorSource.API,
                HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), e.getMessage(), e);
            eventListener.error(sessionId, request, errorResponse);
            throw e;
        }

        int statusCode = completeResponse.getStatusCode();
        if (statusCode == HttpResponseStatus.UNAUTHORIZED.code()) {
            logger.log(Level.INFO,
                "sendPosRequestAsync: Clearing auth token due to failed authentication");
            authToken = null;
        }
        String responseBody = completeResponse.getResponseBody();
        if (!HttpStatusCodeUtil.isSuccess(statusCode) && !HttpStatusCodeUtil.tooEarly(
            statusCode) && !(request instanceof SendKeyRequest)) {
            logger.log(Level.SEVERE, "sendPosRequestAsync: Request unsuccessful. {0}",
                new Object[] { responseBody });
            eventListener.error(sessionId, request, new ErrorResponse(ErrorSource.API,
                statusCode, responseBody, null));
        }
        return new ApiResponse(HttpStatusCodeUtil.isSuccess(statusCode), statusCode,
            responseBody);
    }

    private <T extends IBaseRequest> UUID executeCommon(T request, String endpoint) {
        logger.log(Level.INFO, "Validating request");
        validateRequest(request);

        UUID sessionId = UUID.randomUUID();
        String unformattedUri = uri(ApiType.POS, endpoint);
        String uri = MessageFormat.format(unformattedUri, sessionId);

        ApiResponse response = sendPosRequestAsync(request, HttpMethod.POST, uri, sessionId);
        if (!response.isSuccess()) {
            logger.log(Level.SEVERE, "Request failed, aborting!");
            logger.log(Level.SEVERE, "HTTP Status: {0}, Session: {1}",
                new Object[] { response.getStatusCode(), sessionId });
            logger.log(Level.SEVERE, "HTTP Body: {0}", new Object[] { response.getBody() });
            return sessionId;
        }

        CompletableFuture.runAsync(() -> {
            Instant start = Instant.now();
            long timeElapsed = Duration.between(start, start).toMinutes();
            boolean responseCompleted = false;
            while (timeElapsed < options.getAsyncRequestTimeout() && !responseCompleted) {
                logger.log(Level.INFO, "Checking for new events");
                timeElapsed = Duration.between(start, Instant.now()).toMinutes();
                List<PosApiResponse> posApiResponses = sendResultRequestAsync(request, sessionId,
                    false);
                for (PosApiResponse posApiResponse : posApiResponses) {
                    if (posApiResponse instanceof ReceiptResponse) {
                        eventListener.receipt(sessionId, (PosApiRequest) request,
                            (ReceiptResponse) posApiResponse);
                    }
                    else if (posApiResponse instanceof DisplayResponse) {
                        eventListener.display(sessionId, (PosApiRequest) request,
                            (DisplayResponse) posApiResponse);
                    }
                    else {
                        invokeCommonListener(sessionId, request, posApiResponse);
                        responseCompleted = true;
                    }
                }
            }
            if (!responseCompleted) {
                logger.log(Level.SEVERE,
                    "executeCommon: {0} minutes elapsed waiting for {1} response",
                    new Object[] { options.getAsyncRequestTimeout(), request.getClass()
                        .getSimpleName() });
                eventListener.error(sessionId, request, new ErrorResponse(ErrorSource.Internal,
                    null, "Timed out waiting for response", null));
            }
        });

        return sessionId;
    }

    private <T extends IBaseRequest> List<PosApiResponse> sendResultRequestAsync(
        T request, UUID sessionId, boolean all) {
        List<PosApiResponse> posApiResponses = new ArrayList<>();

        String uri = uri(ApiType.POS, ApiEndpoints.RESULT_ENDPOINT);
        uri = MessageFormat.format(uri, sessionId, all);
        ApiResponse apiResponse = sendPosRequestAsync(request, HttpMethod.GET, uri, sessionId);

        if (HttpStatusCodeUtil.tooEarly(apiResponse.getStatusCode())) {
            logger.log(Level.INFO, "No new events from API. Session: {0}", new Object[] {
                sessionId });
            return posApiResponses;
        }
        if (!HttpStatusCodeUtil.isSuccess(apiResponse.getStatusCode())) {
            logger.log(Level.SEVERE, "Result request unsuccessful. Session: {0}", new Object[] {
                sessionId });
            logger.log(Level.SEVERE, "HTTP Status: {0}", new Object[] { apiResponse
                .getStatusCode() });
            throw new UnsupportedOperationException(
                "sendResultRequestAsync(). Result request unsuccessful."
                    + " Status: " + apiResponse.getStatusCode());
        }

        logger.log(Level.INFO, "Result request successful. {0}", new Object[] { sessionId });

        try {
            JSONArray jsonArray = new JSONArray(apiResponse.getBody());
            for (int ctr = 0; ctr < jsonArray.length(); ctr++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(ctr);
                String responseIdentifier = String.valueOf(jsonObject.get("ResponseType"));
                PosApiResponse posApiResponse = null;
                switch (responseIdentifier) {
                    case ResponseType.LOGON:
                        posApiResponse = MoshiUtil.fromJson(jsonObject.toString(),
                            LogonResponse.class);
                        break;
                    case ResponseType.STATUS:
                        posApiResponse = MoshiUtil.fromJson(jsonObject.toString(),
                            StatusResponse.class);
                        break;
                    case ResponseType.DISPLAY:
                        posApiResponse = MoshiUtil.fromJson(jsonObject.toString(),
                            DisplayResponse.class);
                        break;
                    case ResponseType.RECEIPT:
                        posApiResponse = MoshiUtil.fromJson(jsonObject.toString(),
                            ReceiptResponse.class);
                        break;
                    case ResponseType.CONFIGUREMERCHANT:
                        posApiResponse = MoshiUtil.fromJson(jsonObject.toString(),
                            ConfigureMerchantResponse.class);
                        break;
                    case ResponseType.QUERYCARD:
                        posApiResponse = MoshiUtil.fromJson(jsonObject.toString(),
                            QueryCardResponse.class);
                        break;
                    case ResponseType.REPRINTRECEIPT:
                        posApiResponse = MoshiUtil.fromJson(jsonObject.toString(),
                            ReprintReceiptResponse.class);
                        break;
                    case ResponseType.TRANSACTION:
                        posApiResponse = MoshiUtil.fromJson(jsonObject.toString(),
                            TransactionResponse.class);
                        break;
                    case ResponseType.SETTLEMENT:
                        posApiResponse = MoshiUtil.fromJson(jsonObject.toString(),
                            SettlementResponse.class);
                        break;
                    default:
                        logger.log(Level.INFO, "No valid implementation for {0}", new Object[] {
                            responseIdentifier });
                        break;
                }
                posApiResponses.add(posApiResponse);
            }
        }
        catch (IOException e) {
            throw new RuntimeException("sendResultRequestAsync: Error parsing Request", e);
        }
        return posApiResponses;
    }

    private void validateRequest(IBaseRequest validatableRequest) {
        IValidatable request = (IValidatable) validatableRequest;
        request.validate();
    }

    private String uri(ApiType apiType, String relativeUri) {
        return apiType == ApiType.AUTH ? serviceEndpoints.getAuthApiHost() + relativeUri
            : serviceEndpoints.getPosApiHost() + relativeUri;
    }

    private void invokeCommonListener(UUID sessionId, IBaseRequest request,
        PosApiResponse response) {
        switch (response.getClass().getSimpleName()) {
            case "LogonResponse":
                eventListener.logonComplete(sessionId, (LogonRequest) request,
                    (LogonResponse) response);
                break;
            case "StatusResponse":
                eventListener.statusComplete(sessionId, (StatusRequest) request,
                    (StatusResponse) response);
                break;
            case "ReceiptResponse":
                eventListener.receipt(sessionId, (PosApiRequest) request,
                    (ReceiptResponse) response);
                break;
            case "ConfigureMerchantResponse":
                eventListener.configureMerchantComplete(sessionId,
                    (ConfigureMerchantRequest) request,
                    (ConfigureMerchantResponse) response);
                break;
            case "QueryCardResponse":
                eventListener.queryCardComplete(sessionId, (QueryCardRequest) request,
                    (QueryCardResponse) response);
                break;
            case "ReprintReceiptResponse":
                eventListener.reprintReceiptComplete(sessionId, (ReprintReceiptRequest) request,
                    (ReprintReceiptResponse) response);
                break;
            case "TransactionResponse":
                eventListener.transactionComplete(sessionId, (TransactionRequest) request,
                    (TransactionResponse) response);
                break;
            case "SettlementResponse":
                eventListener.settlementComplete(sessionId, (SettlementRequest) request,
                    (SettlementResponse) response);
                break;
        }
    }

    private void validatePairing(String method) {
        if (pairSecret == null) {
            logger.log(Level.SEVERE, "{0}: Invoked without pairing", new Object[] { method });
            throw new IllegalArgumentException("Pairing is required");
        }
    }

    private void setPosVendorDetails(PosApiRequest request) {
        request.setPosName(posVendorDetails.getPosName());
        request.setPosVersion(posVendorDetails.getPosVersion());
        request.setPosId(posVendorDetails.getPosId());
    }

    private <T extends IBaseRequest> void invokeErrorIfFailed(T request, Response response,
        UUID sessionId) {
        if (!HttpStatusCodeUtil.isSuccess(response.getStatusCode())) {
            String responseBody = response.getResponseBody();
            if (StringUtil.isNullOrWhiteSpace(responseBody)) {
                responseBody = response.getStatusText();
            }
            logger.log(Level.SEVERE, "{0}: Error: {1}", new Object[] { request.getClass()
                .getSimpleName(), responseBody });
            ErrorResponse errorResponse = new ErrorResponse(ErrorSource.API, response
                .getStatusCode(), responseBody, null);
            eventListener.error(sessionId, request, errorResponse);
            throw new UnsupportedOperationException(responseBody);
        }
    }
}