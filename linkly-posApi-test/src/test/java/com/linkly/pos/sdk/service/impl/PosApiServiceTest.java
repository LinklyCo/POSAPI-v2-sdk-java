package com.linkly.pos.sdk.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.linkly.pos.sdk.models.ApiServiceEndpoint;
import com.linkly.pos.sdk.models.AuthToken;
import com.linkly.pos.sdk.models.PosApiServiceOptions;
import com.linkly.pos.sdk.models.PosVendorDetails;
import com.linkly.pos.sdk.models.authentication.PairingRequest;
import com.linkly.pos.sdk.models.authentication.PairingResponse;
import com.linkly.pos.sdk.models.configureMerchant.ConfigureMerchantRequest;
import com.linkly.pos.sdk.models.logon.LogonRequest;
import com.linkly.pos.sdk.models.queryCard.QueryCardRequest;
import com.linkly.pos.sdk.models.reprintReceipt.ReprintReceiptRequest;
import com.linkly.pos.sdk.models.result.ResultRequest;
import com.linkly.pos.sdk.models.sendKey.SendKeyRequest;
import com.linkly.pos.sdk.models.settlement.SettlementRequest;
import com.linkly.pos.sdk.models.status.StatusRequest;
import com.linkly.pos.sdk.models.transaction.CashRequest;
import com.linkly.pos.sdk.models.transaction.DepositRequest;
import com.linkly.pos.sdk.models.transaction.PreAuthCancelRequest;
import com.linkly.pos.sdk.models.transaction.PreAuthCompletionRequest;
import com.linkly.pos.sdk.models.transaction.PreAuthExtendRequest;
import com.linkly.pos.sdk.models.transaction.PreAuthInquiryRequest;
import com.linkly.pos.sdk.models.transaction.PreAuthPartialCancelRequest;
import com.linkly.pos.sdk.models.transaction.PreAuthRequest;
import com.linkly.pos.sdk.models.transaction.PreAuthSummaryRequest;
import com.linkly.pos.sdk.models.transaction.PreAuthTopUpRequest;
import com.linkly.pos.sdk.models.transaction.PurchaseRequest;
import com.linkly.pos.sdk.models.transaction.RefundRequest;
import com.linkly.pos.sdk.models.transaction.RetrieveTransactionRequest;
import com.linkly.pos.sdk.models.transaction.VoidRequest;
import com.linkly.pos.sdk.service.IHttpAsyncExecutor;
import com.linkly.pos.sdk.service.IPosApiService;
import com.linkly.pos.sdk.service.impl.testData.AuthTokenMock;
import com.linkly.pos.sdk.service.impl.testData.ConfigureMerchantMock;
import com.linkly.pos.sdk.service.impl.testData.DisplayMock;
import com.linkly.pos.sdk.service.impl.testData.LogonMock;
import com.linkly.pos.sdk.service.impl.testData.PairingMock;
import com.linkly.pos.sdk.service.impl.testData.QueryCardMock;
import com.linkly.pos.sdk.service.impl.testData.ReceiptMock;
import com.linkly.pos.sdk.service.impl.testData.ReprintReceiptMock;
import com.linkly.pos.sdk.service.impl.testData.ResultRequestMock;
import com.linkly.pos.sdk.service.impl.testData.SendKeyMock;
import com.linkly.pos.sdk.service.impl.testData.SettlementMock;
import com.linkly.pos.sdk.service.impl.testData.StatusMock;
import com.linkly.pos.sdk.service.impl.testData.TransactionMock;

class PosApiServiceTest {

    private static final String AUTH_API = "http://auth.mock";
    private static final String POS_API = "http://pos.mock";
    public static boolean objectsInitialized;

    private static IPosApiService service;
    private MockEventListener eventListener;
    private PosVendorDetails posVendorDetails;
    private PosApiServiceOptions options;
    private ApiServiceEndpoint serviceEndpoints;
    private IHttpAsyncExecutor asyncHttpExecutor;
    private AsyncHttpClient httpClient;
    private Response response;
    private Logger logger;

    @BeforeEach
    public void beforeEach() {
        if (!objectsInitialized) {
            posVendorDetails = posVendorDetails();
            options = new PosApiServiceOptions();
            serviceEndpoints = new ApiServiceEndpoint(AUTH_API, POS_API);
            asyncHttpExecutor = mock(HttpAsyncExecutor.class);
            httpClient = mock(AsyncHttpClient.class);
            response = mock(Response.class);
            logger = mock(Logger.class);
        }
        eventListener = new MockEventListener();
        service = new PosApiService(eventListener, httpClient, posVendorDetails, options,
            serviceEndpoints, logger);
        injectAsyncHttpExector();
        reset(asyncHttpExecutor, response, httpClient);
    }

    @Test
    void pairingRequest_throws_apiCallException() throws InterruptedException {
        when(asyncHttpExecutor.post(anyString(), anyString()))
            .thenThrow(new RuntimeException("socket timeout"));
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,
            () -> {
                service.pairingRequest(PairingMock.request());
            });
        Thread.sleep(1000);
        String listenerResponse = eventListener.getResponseContent("500");
        assertEquals("{\"httpStatusCode\":500,\"message\":\"socket timeout\",\"source\":\"API\"}",
            listenerResponse);
        assertEquals("socket timeout", thrown.getMessage());
    }

    @Test
    void authenticate_throws_apiCallException() throws InterruptedException {
        pairingRequest();

        when(asyncHttpExecutor.post(anyString(), anyString()))
            .thenThrow(new RuntimeException("unknown host")); // authenticate mock call

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,
            () -> {
                service.logonRequest(new LogonRequest());
            });

        Thread.sleep(1000);
        String listenerResponse = eventListener.getResponseContent("500");
        assertEquals("{\"httpStatusCode\":500,\"message\":\"unknown host\",\"source\":\"API\"}",
            listenerResponse);
        assertEquals("unknown host", thrown.getMessage());
    }

    @Test
    void logonRequest_throws_apiCallException() throws InterruptedException {
        pairingRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.post(anyString(), anyString(), anyString()))
            .thenThrow(new RuntimeException("no route to host exception"));
        when(response.getStatusCode()).thenReturn(200).thenReturn(200);
        when(response.getResponseBody())
            .thenReturn(AuthTokenMock.tokenResponseContent());

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,
            () -> {
                service.logonRequest(new LogonRequest());
            });

        Thread.sleep(1000);
        String listenerResponse = eventListener.getResponseContent("500");
        assertEquals(
            "{\"httpStatusCode\":500,\"message\":\"no route to host exception\",\"source\":\"API\"}",
            listenerResponse);
        assertEquals("no route to host exception", thrown.getMessage());
    }

    @Test
    void resultRequest_throws_apiCallException() throws InterruptedException {
        pairingRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString()))
            .thenThrow(new RuntimeException("port unreachable exception"));
        when(response.getStatusCode()).thenReturn(200).thenReturn(200);
        when(response.getResponseBody())
            .thenReturn(AuthTokenMock.tokenResponseContent());

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,
            () -> {
                service.resultRequest(new ResultRequest(UUID.randomUUID()));
            });

        Thread.sleep(1000);
        String listenerResponse = eventListener.getResponseContent("500");
        assertEquals(
            "{\"httpStatusCode\":500,\"message\":\"port unreachable exception\",\"source\":\"API\"}",
            listenerResponse);
        assertEquals("port unreachable exception", thrown.getMessage());
    }

    @Test
    void should_not_refreshSession_success() {
        pairingRequest();
        injectAuthTokenNotExpiringSoon();

        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.post(anyString(), anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200).thenReturn(200);
        when(response.getResponseBody())
            .thenReturn("Mock content")
            .thenReturn(LogonMock.logonResponseContent());

        service.logonRequest(new LogonRequest());

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(asyncHttpExecutor, atLeast(1)).post(argumentCaptor.capture(), argumentCaptor
            .capture());

        List<String> arguments = argumentCaptor.getAllValues();
        for (String arg : arguments) {
            // verify that the captured argument does not contain the endpoint for Authentication
            // This simply means that refreshing of session is not invoked.
            assertFalse(arg.contains("/v1/tokens/cloudpos"));
        }
    }

    @Test
    void should_refreshSession_success() {
        pairingRequest();

        LocalDateTime dateDateTimeNow = LocalDateTime.now();
        injectAuthToken(dateDateTimeNow);

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.post(anyString(), anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200).thenReturn(200).thenReturn(200);
        when(response.getResponseBody())
            .thenReturn(AuthTokenMock.tokenResponseContent())
            .thenReturn("Mock content")
            .thenReturn(LogonMock.logonResponseContent());

        service.logonRequest(new LogonRequest());

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(asyncHttpExecutor, atLeast(1)).post(argumentCaptor.capture(), argumentCaptor
            .capture());

        List<String> arguments = argumentCaptor.getAllValues();

        // Verify that token is in the capture argument
        // If value below is present, it means that session is refreshed
        // by calling the authenticate method
        assertEquals("http://auth.mock/v1/tokens/cloudpos", arguments.get(2));

        // Verify that session token expiry has been increased to 30 minutes
        AuthToken authToken = getAuthToken();
        assertEquals(30, ChronoUnit.MINUTES.between(dateDateTimeNow, authToken.getExpiry()));
    }

    @Test
    void pairingRequest_success() {
        String content = pairingRequest();
        assertEquals("{\"secret\":\"test12345secret\"}", eventListener.getResponseContent(
            "pairing"));
        verify(asyncHttpExecutor, times(1)).post(AUTH_API + "/v1/pairing/cloudpos", content);
        verifySecretAndAuthTokenAfterPair(false);
    }

    @Test
    void pairingRequest_failed() {
        PairingRequest pairingRequest = PairingMock.request();
        String requestContent = PairingMock.toJson(pairingRequest);
        String responseContent = "Error";

        when(response.getStatusCode()).thenReturn(401);
        when(response.getResponseBody()).thenReturn(responseContent);
        when(asyncHttpExecutor.post(AUTH_API + "/v1/pairing/cloudpos", requestContent))
            .thenReturn(response);

        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
            () -> {
                service.pairingRequest(pairingRequest);
            });
        assertEquals(exception.getClass(), UnsupportedOperationException.class);
        assertEquals("{\"httpStatusCode\":401,\"message\":\"Error\",\"source\":\"API\"}",
            eventListener.getResponseContent("pairing"));
        verify(asyncHttpExecutor, times(1)).post(AUTH_API + "/v1/pairing/cloudpos", requestContent);
        verifySecretAndAuthTokenAfterPair(true);
    }

    @Test
    void setPairSecret_nullSecret() {
        PairingRequest pairingRequest = PairingMock.request();
        PairingResponse pairingResponse = PairingMock.responseEmptySecret();
        String requestContent = PairingMock.toJson(pairingRequest);
        String responseContent = PairingMock.toJson(pairingResponse);

        when(response.getStatusCode()).thenReturn(200);
        when(response.getResponseBody()).thenReturn(responseContent);
        when(asyncHttpExecutor.post(AUTH_API + "/v1/pairing/cloudpos", requestContent))
            .thenReturn(response);

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
            () -> {
                service.pairingRequest(pairingRequest);
            });
        assertEquals("Required pairSecret", thrown.getMessage());
    }

    @Test
    void setPairSecret_sameSecret() throws InterruptedException {
        PairingRequest pairingRequest = PairingMock.request();
        PairingResponse pairingResponse = PairingMock.response();
        String requestContent = PairingMock.toJson(pairingRequest);
        String responseContent = PairingMock.toJson(pairingResponse);

        when(response.getStatusCode()).thenReturn(200);
        when(response.getResponseBody()).thenReturn(responseContent);
        when(asyncHttpExecutor.post(AUTH_API + "/v1/pairing/cloudpos", requestContent))
            .thenReturn(response);

        service.pairingRequest(pairingRequest);

        Thread.sleep(2000);
        String firstContent = eventListener.getResponseContent("secret");
        injectAuthToken();
        service.pairingRequest(pairingRequest);
        AuthToken token = getAuthToken();

        assertEquals(firstContent, eventListener.getResponseContent("secret"));
        assertTrue(token.getToken().length() > 1);
    }

    @Test
    void logonRequest_pairingFailed() {

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
            () -> {
                service.logonRequest(new LogonRequest());
            });
        assertEquals("Pairing is required", thrown.getMessage());
    }

    @Test
    void logonRequest_validaitonFailed() {
        eventListener.clear();
        pairingRequest();
        LogonRequest request = new LogonRequest();
        request.setLogonType(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.logonRequest(request);
        });
        assertEquals("logonType: Enum null not found in the list: [Standard, RSA, TmsFull, "
            + "TmsParams, TmsSoftware, Logoff, Diagnostics].", exception.getMessage());
    }

    @Test
    void logonRequest_success() throws InterruptedException {
        pairingRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200).thenReturn(200).thenReturn(200);
        when(response.getResponseBody())
            .thenReturn(AuthTokenMock.tokenResponseContent())
            .thenReturn("Mock content")
            .thenReturn(LogonMock.logonResponseContent());

        when(asyncHttpExecutor.post(anyString(), anyString(), anyString())).thenReturn(response);

        UUID uuid = service.logonRequest(new LogonRequest());

        Thread.sleep(2000);
        String listener = eventListener.getResponseContent("logon");
        AuthToken authToken = getAuthToken();

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();

        assertNotNull(uuid);
        assertTrue(listener.contains("logon"));
        assertTrue(listener.contains("Logon Success"));
        assertEquals("testvalidtoken", authToken.getToken());
        assertTrue(LocalDateTime.now().isBefore(authToken.getExpiry()));
    }

    @Test
    void sendPosRequestAsync_failed() {
        pairingRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(203).thenReturn(422);
        when(response.getResponseBody())
            .thenReturn(AuthTokenMock.tokenResponseContent())
            .thenReturn("Unprocessable Entity.");
        when(asyncHttpExecutor.post(anyString(), anyString(), anyString())).thenReturn(response);

        service.logonRequest(new LogonRequest());

        String listener = eventListener.getResponseContent("unknown");

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();

        assertTrue(listener.contains("422"));
        assertTrue(listener.contains("Unprocessable Entity."));
    }

    @Test
    void sendResultRequestAsync_timeExhausted_tooEarlyResponse() throws InterruptedException {
        reInitializeService();
        pairingRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200).thenReturn(200).thenReturn(425);
        when(response.getResponseBody())
            .thenReturn(AuthTokenMock.tokenResponseContent())
            .thenReturn("Mock content");

        when(asyncHttpExecutor.post(anyString(), anyString(), anyString())).thenReturn(response);

        service.logonRequest(new LogonRequest());

        /*
         * wait is needed because the result process takes a minute to be exhausted
         * 75000 is 1m 25s. Add 25 seconds to make sure that response has been capture by the
         * listener
         */
        Thread.sleep(75000);
        String errorResponse = eventListener.getResponseContent("Timed out");
        assertEquals(errorResponse,
            "{\"message\":\"Timed out waiting for response\",\"source\":\"Internal\"}");
    }

    @Test
    void sendResultRequestAsync_failed() throws InterruptedException {
        reInitializeService();
        pairingRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200).thenReturn(200).thenReturn(500);
        when(response.getResponseBody())
            .thenReturn(AuthTokenMock.tokenResponseContent())
            .thenReturn("Mock content")
            .thenReturn("Internal Server Exception");

        when(asyncHttpExecutor.post(anyString(), anyString(), anyString())).thenReturn(response);

        service.logonRequest(new LogonRequest());

        Thread.sleep(2000);
        String listener = eventListener.getResponseContent("unknown");

        assertTrue(listener.contains("500"));
        assertTrue(listener.contains("Internal Server Exception"));
        assertTrue(listener.contains("API"));
    }

    @Test
    void transactionRequest_pairingFailed() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
            () -> {
                service.transactionRequest(new CashRequest(0));
            });
        assertEquals("Pairing is required", thrown.getMessage());
    }

    @Test
    void transactionRequest_failed() {
        eventListener.clear();
        pairingRequest();
        CashRequest cashRequest = TransactionMock.cashRequest();
        cashRequest.setTxnRef(null);
        String requestContent = TransactionMock.cashRequestContent(cashRequest);
        String responseContent = "Error";

        when(response.getStatusCode()).thenReturn(401);
        when(response.getResponseBody()).thenReturn(responseContent);
        when(asyncHttpExecutor.post(anyString(), eq(requestContent)))
            .thenReturn(response);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.transactionRequest(cashRequest);
        });
        assertEquals("txnRef: Must not be empty.", exception.getMessage());
    }

    @Test
    void transactionRequest_cash_success() throws InterruptedException {
        pairingRequest();
        CashRequest cashRequest = TransactionMock.cashRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200);
        when(response.getResponseBody())
            .thenReturn(AuthTokenMock.tokenResponseContent(), "Mock content",
                DisplayMock.displayResponseContent("Started Transaction"),
                ReceiptMock.receiptResponseContent(), TransactionMock.transactionResponseContent());

        when(asyncHttpExecutor.post(anyString(), eq("Bearer testvalidtoken"), anyString()))
            .thenReturn(response);

        UUID uuid = service.transactionRequest(cashRequest);

        Thread.sleep(2000);
        List<String> getResponseContents = eventListener.getResponseContents();

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();

        assertNotNull(uuid);
        assertTrue(getResponseContents.get(0).equals("{\"secret\":\"test12345secret\"}"));
        assertTrue(getResponseContents.get(1).contains("\"ResponseType\":\"display\""));
        assertTrue(getResponseContents.get(1).contains("Started Transaction"));
        assertTrue(getResponseContents.get(2).contains("\"ResponseType\":\"receipt\""));
        assertTrue(getResponseContents.get(2).contains("Receipt"));
        assertTrue(getResponseContents.get(3).contains("\"ResponseType\":\"transaction\""));
        assertTrue(getResponseContents.get(3).contains("Transaction Done"));
    }

    @Test
    void transactionRequest_deposit_success() throws InterruptedException {
        pairingRequest();
        DepositRequest depositRequest = TransactionMock.depositRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200);
        when(response.getResponseBody()).thenReturn(AuthTokenMock.tokenResponseContent(),
            "Mock content", DisplayMock.displayResponseContent("Started Transaction"),
            ReceiptMock.receiptResponseContent(), TransactionMock.transactionResponseContent());

        when(asyncHttpExecutor.post(anyString(), eq("Bearer testvalidtoken"), anyString()))
            .thenReturn(response);

        UUID uuid = service.transactionRequest(depositRequest);

        Thread.sleep(2000);
        List<String> getResponseContents = eventListener.getResponseContents();

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();

        assertNotNull(uuid);
        assertEquals(4, getResponseContents.size());
    }

    @Test
    void transactionRequest_preAuthCancelRequest_success() throws InterruptedException {
        pairingRequest();
        PreAuthCancelRequest preAuthCancelRequest = TransactionMock
            .preAuthCancelRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200);
        when(response.getResponseBody()).thenReturn(AuthTokenMock.tokenResponseContent(),
            "Mock content", DisplayMock.displayResponseContent("Started Transaction"),
            ReceiptMock.receiptResponseContent(), TransactionMock.transactionResponseContent());

        when(asyncHttpExecutor.post(anyString(), eq("Bearer testvalidtoken"), anyString()))
            .thenReturn(response);

        UUID uuid = service.transactionRequest(preAuthCancelRequest);

        Thread.sleep(2000);
        List<String> getResponseContents = eventListener.getResponseContents();

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();

        assertNotNull(uuid);
        assertEquals(4, getResponseContents.size());
    }

    @Test
    void transactionRequest_preAuthCompletionRequest_success() throws InterruptedException {
        pairingRequest();
        PreAuthCompletionRequest preAuthCompletionRequest = TransactionMock
            .preAuthCompletionRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200);
        when(response.getResponseBody()).thenReturn(AuthTokenMock.tokenResponseContent(),
            "Mock content", DisplayMock.displayResponseContent("Started Transaction"),
            ReceiptMock.receiptResponseContent(), TransactionMock.transactionResponseContent());

        when(asyncHttpExecutor.post(anyString(), eq("Bearer testvalidtoken"), anyString()))
            .thenReturn(response);

        UUID uuid = service.transactionRequest(preAuthCompletionRequest);

        Thread.sleep(2000);
        List<String> getResponseContents = eventListener.getResponseContents();

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();

        assertNotNull(uuid);
        assertEquals(4, getResponseContents.size());
    }

    @Test
    void transactionRequest_preAuthExtendRequest_success() throws InterruptedException {
        pairingRequest();
        PreAuthExtendRequest preAuthExtendRequest = TransactionMock.preAuthExtendRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200);
        when(response.getResponseBody()).thenReturn(AuthTokenMock.tokenResponseContent(),
            "Mock content", DisplayMock.displayResponseContent("Started Transaction"),
            ReceiptMock.receiptResponseContent(), TransactionMock.transactionResponseContent());

        when(asyncHttpExecutor.post(anyString(), eq("Bearer testvalidtoken"), anyString()))
            .thenReturn(response);

        UUID uuid = service.transactionRequest(preAuthExtendRequest);

        Thread.sleep(2000);
        List<String> getResponseContents = eventListener.getResponseContents();

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();

        assertNotNull(uuid);
        assertEquals(4, getResponseContents.size());
    }

    @Test
    void transactionRequest_preAuthInquiryRequest_success() throws InterruptedException {
        pairingRequest();
        PreAuthInquiryRequest preAuthInquiryRequest = TransactionMock.preAuthInquiryRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200);
        when(response.getResponseBody()).thenReturn(AuthTokenMock.tokenResponseContent(),
            "Mock content", DisplayMock.displayResponseContent("Started Transaction"),
            ReceiptMock.receiptResponseContent(), TransactionMock.transactionResponseContent());

        when(asyncHttpExecutor.post(anyString(), eq("Bearer testvalidtoken"), anyString()))
            .thenReturn(response);

        UUID uuid = service.transactionRequest(preAuthInquiryRequest);

        Thread.sleep(2000);
        List<String> getResponseContents = eventListener.getResponseContents();

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();

        assertNotNull(uuid);
        assertEquals(4, getResponseContents.size());
    }

    @Test
    void transactionRequest_preAuthPartialCancelRequest_success() throws InterruptedException {
        pairingRequest();
        PreAuthPartialCancelRequest preAuthPartialCancelRequest = TransactionMock
            .preAuthPartialCancelRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200);
        when(response.getResponseBody()).thenReturn(AuthTokenMock.tokenResponseContent(),
            "Mock content", DisplayMock.displayResponseContent("Started Transaction"),
            ReceiptMock.receiptResponseContent(), TransactionMock.transactionResponseContent());

        when(asyncHttpExecutor.post(anyString(), eq("Bearer testvalidtoken"), anyString()))
            .thenReturn(response);

        UUID uuid = service.transactionRequest(preAuthPartialCancelRequest);

        Thread.sleep(2000);
        List<String> getResponseContents = eventListener.getResponseContents();

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();

        assertNotNull(uuid);
        assertEquals(4, getResponseContents.size());
    }

    @Test
    void transactionRequest_preAuthRequest_success() throws InterruptedException {
        pairingRequest();
        PreAuthRequest preAuthRequest = TransactionMock.preAuthRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200);
        when(response.getResponseBody()).thenReturn(AuthTokenMock.tokenResponseContent(),
            "Mock content", DisplayMock.displayResponseContent("Started Transaction"),
            ReceiptMock.receiptResponseContent(), TransactionMock.transactionResponseContent());

        when(asyncHttpExecutor.post(anyString(), eq("Bearer testvalidtoken"), anyString()))
            .thenReturn(response);

        UUID uuid = service.transactionRequest(preAuthRequest);

        Thread.sleep(2000);
        List<String> getResponseContents = eventListener.getResponseContents();

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();

        assertNotNull(uuid);
        assertEquals(4, getResponseContents.size());
    }

    @Test
    void transactionRequest_preAuthSummaryRequest_success() throws InterruptedException {
        pairingRequest();
        PreAuthSummaryRequest preAuthSummaryRequest = TransactionMock.preAuthSummaryRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200);
        when(response.getResponseBody()).thenReturn(AuthTokenMock.tokenResponseContent(),
            "Mock content", DisplayMock.displayResponseContent("Started Transaction"),
            ReceiptMock.receiptResponseContent(), TransactionMock.transactionResponseContent());

        when(asyncHttpExecutor.post(anyString(), eq("Bearer testvalidtoken"), anyString()))
            .thenReturn(response);

        UUID uuid = service.transactionRequest(preAuthSummaryRequest);

        Thread.sleep(2000);
        List<String> getResponseContents = eventListener.getResponseContents();

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();

        assertNotNull(uuid);
        assertEquals(4, getResponseContents.size());
    }

    @Test
    void transactionRequest_preAuthTopUpRequest_success() throws InterruptedException {
        pairingRequest();
        PreAuthTopUpRequest preAuthTopUpRequest = TransactionMock.preAuthTopUpRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200);
        when(response.getResponseBody()).thenReturn(AuthTokenMock.tokenResponseContent(),
            "Mock content", DisplayMock.displayResponseContent("Started Transaction"),
            ReceiptMock.receiptResponseContent(), TransactionMock.transactionResponseContent());

        when(asyncHttpExecutor.post(anyString(), eq("Bearer testvalidtoken"), anyString()))
            .thenReturn(response);

        UUID uuid = service.transactionRequest(preAuthTopUpRequest);

        Thread.sleep(2000);
        List<String> getResponseContents = eventListener.getResponseContents();

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();

        assertNotNull(uuid);
        assertEquals(4, getResponseContents.size());
    }

    @Test
    void transactionRequest_purchaseRequest_success() throws InterruptedException {
        pairingRequest();
        PurchaseRequest purchaseRequest = TransactionMock.purchaseRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200);
        when(response.getResponseBody()).thenReturn(AuthTokenMock.tokenResponseContent(),
            "Mock content", DisplayMock.displayResponseContent("Started Transaction"),
            ReceiptMock.receiptResponseContent(), TransactionMock.transactionResponseContent());

        when(asyncHttpExecutor.post(anyString(), eq("Bearer testvalidtoken"), anyString()))
            .thenReturn(response);

        UUID uuid = service.transactionRequest(purchaseRequest);

        Thread.sleep(2000);
        List<String> getResponseContents = eventListener.getResponseContents();

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();

        assertNotNull(uuid);
        assertEquals(4, getResponseContents.size());
    }

    @Test
    void transactionRequest_refundRequest_success() throws InterruptedException {
        pairingRequest();
        RefundRequest refundRequest = TransactionMock.refundRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200);
        when(response.getResponseBody()).thenReturn(AuthTokenMock.tokenResponseContent(),
            "Mock content", DisplayMock.displayResponseContent("Started Transaction"),
            ReceiptMock.receiptResponseContent(), TransactionMock.transactionResponseContent());

        when(asyncHttpExecutor.post(anyString(), eq("Bearer testvalidtoken"), anyString()))
            .thenReturn(response);

        Thread.sleep(2000);
        UUID uuid = service.transactionRequest(refundRequest);

        List<String> getResponseContents = eventListener.getResponseContents();

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();

        assertNotNull(uuid);
        assertEquals(1, getResponseContents.size());
    }

    @Test
    void transactionRequest_voidRequest_success() throws InterruptedException {
        pairingRequest();
        VoidRequest voidRequest = TransactionMock.voidRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200);
        when(response.getResponseBody()).thenReturn(AuthTokenMock.tokenResponseContent(),
            "Mock content", DisplayMock.displayResponseContent("Started Transaction"),
            ReceiptMock.receiptResponseContent(), TransactionMock.transactionResponseContent());

        when(asyncHttpExecutor.post(anyString(), eq("Bearer testvalidtoken"), anyString()))
            .thenReturn(response);

        UUID uuid = service.transactionRequest(voidRequest);

        Thread.sleep(2000);
        List<String> getResponseContents = eventListener.getResponseContents();

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();

        assertNotNull(uuid);
        assertEquals(4, getResponseContents.size());
    }

    @Test
    void retrieveTransaction_noImplementation() {
        pairingRequest();
        RetrieveTransactionRequest retrieveTransactionRequest = TransactionMock
            .retrieveTransactionRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200);
        when(response.getResponseBody()).thenReturn(AuthTokenMock.tokenResponseContent(),
            TransactionMock.transactionResponseContent());

        when(asyncHttpExecutor.get(anyString(), eq("Bearer testvalidtoken")))
            .thenReturn(response);

        service.retrieveTransactionRequest(retrieveTransactionRequest);
        assertTrue(eventListener.getResponseContent("unknown").contains("No Implementation Yet!"));
    }

    @Test
    void statusRequest_pairingFailed() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
            () -> {
                service.statusRequest(new StatusRequest());
            });
        assertEquals("Pairing is required", thrown.getMessage());
    }

    @Test
    void statusRequest_validaitonFailed() {
        eventListener.clear();
        pairingRequest();
        StatusRequest request = new StatusRequest();
        request.setStatusType(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.statusRequest(request);
        });
        assertEquals("statusType: Enum null not found in the list: [Standard, TerminalAppInfo, "
            + "AppCpat, AppNameTable, Undefined, Preswipe].", exception.getMessage());
    }

    @Test
    void statusRequest_success() throws InterruptedException {
        pairingRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200).thenReturn(200).thenReturn(200);
        when(response.getResponseBody())
            .thenReturn(AuthTokenMock.tokenResponseContent())
            .thenReturn("Mock content")
            .thenReturn(StatusMock.statusResponseContent());

        when(asyncHttpExecutor.post(anyString(), anyString(), anyString())).thenReturn(response);

        UUID uuid = service.statusRequest(StatusMock.statusRequest());

        Thread.sleep(2000);
        String listener = eventListener.getResponseContent("status");

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();

        assertNotNull(uuid);
        assertEquals("{\"AIIC\":\"TESTAIIC\",\"CardMisreadCount\":0,\"CashoutLimit\":0,"
            + "\"FreeMemoryInTerminal\":0,\"LoggedOn\":false,\"MaxSaf\":0,\"NII\":0,"
            + "\"NumAppsInTerminal\":0,\"NumLinesOnDisplay\":0,\"OptionsFlags\":"
            + "{\"AutoCompletion\":false,\"Balance\":false,\"CashOut\":false,\"Completions\":"
            + "false,\"Deposit\":false,\"EFB\":false,\"EMV\":false,\"Moto\":false,\"PreAuth\":"
            + "false,\"Refund\":false,\"StartCash\":false,\"Tipping\":false,\"Training\":false,"
            + "\"Transfer\":false,\"Voucher\":false,\"Withdrawal\":false},\"RefundLimit\":0,"
            + "\"ResponseType\":\"status\",\"SafCount\":0,\"SafCreditLimit\":0,\"SafDebitLimit\":"
            + "0,\"Success\":false,\"TimeOut\":0,\"TotalMemoryInTerminal\":0}", listener);
    }

    @Test
    void settlementRequest_pairingFailed() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
            () -> {
                service.settlementRequest(SettlementMock.settlementRequest());
            });
        assertEquals("Pairing is required", thrown.getMessage());
    }

    @Test
    void settlementRequest_validaitonFailed() {
        eventListener.clear();
        pairingRequest();
        SettlementRequest request = SettlementMock.settlementRequest();
        request.setSettlementType(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.settlementRequest(request);
        });
        assertEquals("settlementType: Enum null not found in the list: [Settlement, PreSettlement, "
            + "LastSettlement, SummaryTotals, SubShiftTotals, DetailedTransactionListing, "
            + "StartCash, StoreAndForwardTotals, DailyCashStatement].", exception.getMessage());
    }

    @Test
    void settlementRequest_success() throws InterruptedException {
        pairingRequest();
        SettlementRequest request = SettlementMock.settlementRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200).thenReturn(200).thenReturn(200);
        when(response.getResponseBody())
            .thenReturn(AuthTokenMock.tokenResponseContent())
            .thenReturn("Mock content")
            .thenReturn(SettlementMock.settlementResponseContent());

        when(asyncHttpExecutor.post(anyString(), anyString(), anyString())).thenReturn(response);

        UUID uuid = service.settlementRequest(request);

        Thread.sleep(2000);
        String listener = eventListener.getResponseContent("settlement");

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();

        assertNotNull(uuid);
        assertEquals("{\"Merchant\":\"00\",\"ResponseType\":\"settlement\",\"SettlementData\":"
            + "\"Settlement Data\",\"Success\":false}", listener);
    }

    @Test
    void queryCardRequest_pairingFailed() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
            () -> {
                service.queryCardRequest(new QueryCardRequest());
            });
        assertEquals("Pairing is required", thrown.getMessage());
    }

    @Test
    void queryCardRequest_validaitonFailed() {
        eventListener.clear();
        pairingRequest();
        QueryCardRequest request = new QueryCardRequest();
        request.setQueryCardType(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.queryCardRequest(request);
        });
        assertEquals("queryCardType: Enum null not found in the list: [ReadCard, "
            + "ReadCardAndSelectAccount, SelectAccount, PreSwipe, PreSwipeSpecial].", exception
                .getMessage());
    }

    @Test
    void queryCardRequest_success() throws InterruptedException {
        pairingRequest();
        QueryCardRequest request = new QueryCardRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200).thenReturn(200).thenReturn(200);
        when(response.getResponseBody())
            .thenReturn(AuthTokenMock.tokenResponseContent())
            .thenReturn("Mock content")
            .thenReturn(QueryCardMock.queryCardResponseContent());

        when(asyncHttpExecutor.post(anyString(), anyString(), anyString())).thenReturn(response);

        UUID uuid = service.queryCardRequest(request);

        Thread.sleep(2000);
        String listener = eventListener.getResponseContent("query");

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();

        assertNotNull(uuid);
        assertEquals("{\"AccountType\":\"3\",\"CardName\":\"card 001\",\"IsTrack1Available\":true,"
            + "\"IsTrack2Available\":false,\"IsTrack3Available\":false,\"ResponseType\":"
            + "\"querycard\",\"Success\":false,\"Track1\":\"track1 value\"}", listener);
    }

    @Test
    void configureMerchantRequest_pairingFailed() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
            () -> {
                service.configureMerchantRequest(new ConfigureMerchantRequest());
            });
        assertEquals("Pairing is required", thrown.getMessage());
    }

    @Test
    void configureMerchantRequest_validaitonFailed() {
        eventListener.clear();
        pairingRequest();
        ConfigureMerchantRequest request = new ConfigureMerchantRequest();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.configureMerchantRequest(request);
        });
        assertEquals("catId: Must not be empty., caId: Must not be empty.", exception.getMessage());
    }

    @Test
    void configureMerchantRequest_success() throws InterruptedException {
        pairingRequest();
        ConfigureMerchantRequest request = ConfigureMerchantMock.request();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200).thenReturn(200).thenReturn(200);
        when(response.getResponseBody())
            .thenReturn(AuthTokenMock.tokenResponseContent())
            .thenReturn("Mock content")
            .thenReturn(ConfigureMerchantMock.configureMerchantResponseContent());

        when(asyncHttpExecutor.post(anyString(), anyString(), anyString())).thenReturn(response);

        UUID uuid = service.configureMerchantRequest(request);

        Thread.sleep(2000);
        String listener = eventListener.getResponseContent("configuremerchant");

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();

        assertNotNull(uuid);
        assertEquals("{\"ResponseCode\":\"200\",\"ResponseType\":\"configuremerchant\","
            + "\"Success\":true}", listener);
    }

    @Test
    void reprintReceiptRequest_pairingFailed() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
            () -> {
                service.reprintReceiptRequest(new ReprintReceiptRequest());
            });
        assertEquals("Pairing is required", thrown.getMessage());
    }

    @Test
    void reprintReceiptRequest_validaitonFailed() {
        eventListener.clear();
        pairingRequest();
        ReprintReceiptRequest request = ReprintReceiptMock.request();
        request.setReceiptAutoPrint(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.reprintReceiptRequest(request);
        });
        assertEquals("receiptAutoPrint: Enum null not found in the list: [POS, PinPad, Both].",
            exception.getMessage());
    }

    @Test
    void reprintReceiptRequest_success() throws InterruptedException {
        eventListener.clear();
        pairingRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200).thenReturn(200).thenReturn(200);
        when(response.getResponseBody())
            .thenReturn(AuthTokenMock.tokenResponseContent())
            .thenReturn("Mock content")
            .thenReturn(ReprintReceiptMock.reprintReceiptResponseContent());

        when(asyncHttpExecutor.post(anyString(), anyString(), anyString())).thenReturn(response);

        UUID uuid = service.reprintReceiptRequest(ReprintReceiptMock.request());

        Thread.sleep(1000);
        String listener = eventListener.getResponseContent("reprintreceipt");

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();

        assertNotNull(uuid);
        assertEquals("{\"Merchant\":\"01\",\"ReceiptText\":[\"Official Receipt\"],"
            + "\"ResponseType\":\"reprintreceipt\",\"Success\":false}", listener);
    }

    @Test
    void sendKeyRequest_pairingFailed() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
            () -> {
                service.sendKeyRequest(new SendKeyRequest());
            });
        assertEquals("Pairing is required", thrown.getMessage());
    }

    @Test
    void sendKeyRequest_validaitonFailed() {
        eventListener.clear();
        pairingRequest();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.sendKeyRequest(new SendKeyRequest());
        });
        assertEquals("sessionId: Must not be empty., key: Must not be empty.", exception
            .getMessage());
    }

    @Test
    void sendKeyRequest_failed() throws InterruptedException {
        pairingRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200).thenReturn(500);
        when(response.getResponseBody())
            .thenReturn(AuthTokenMock.tokenResponseContent())
            .thenReturn("Internal server error!");

        when(asyncHttpExecutor.post(anyString(), anyString(), anyString())).thenReturn(response);

        service.sendKeyRequest(SendKeyMock.request());

        Thread.sleep(2000);
        String listener = eventListener.getResponseContent("Internal");

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();
        assertEquals("{\"httpStatusCode\":500,\"message\":\"Internal server error!\","
            + "\"source\":\"API\"}", listener);
    }

    @Test
    void sendKeyRequest_success() {
        pairingRequest();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200).thenReturn(200);
        when(response.getResponseBody())
            .thenReturn(AuthTokenMock.tokenResponseContent())
            .thenReturn("Mock content");

        when(asyncHttpExecutor.post(anyString(), anyString(), anyString())).thenReturn(response);

        service.sendKeyRequest(SendKeyMock.request());

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();
    }

    @Test
    void resultRequest_pairingFailed() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
            () -> {
                service.resultRequest(new ResultRequest(UUID.randomUUID()));
            });
        assertEquals("Pairing is required", thrown.getMessage());
    }

    @Test
    void resultRequest_success() throws InterruptedException {
        pairingRequest();

        UUID uuid = UUID.randomUUID();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200).thenReturn(200);
        when(response.getResponseBody())
            .thenReturn(AuthTokenMock.tokenResponseContent())
            .thenReturn(ResultRequestMock.resultRequestResponseContent());

        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);

        service.resultRequest(new ResultRequest(uuid));

        Thread.sleep(2000);
        String listener = eventListener.getResponseContent("logon");

        ArgumentCaptor<String> getArgument = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> postArgument = ArgumentCaptor.forClass(String.class);
        verify(asyncHttpExecutor, times(1)).get(getArgument.capture(), getArgument.capture());
        verify(asyncHttpExecutor, times(2)).post(postArgument.capture(), postArgument.capture());

        List<String> getArguments = getArgument.getAllValues();
        List<String> postArguments = postArgument.getAllValues();

        assertEquals("http://pos.mock/v2/sessions/" + uuid.toString() + "/result?all=true",
            getArguments.get(0));
        assertEquals("Bearer testvalidtoken", getArguments.get(1));
        assertEquals("http://auth.mock/v1/tokens/cloudpos", postArguments.get(2));
        assertEquals("{\"pairCode\":\"testcode\",\"password\":\"p@szw0rd\",\"username\":"
            + "\"testuser\"}", postArguments.get(1));

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();
        assertEquals("[{\"PurchaseAnalysisData\":{\"testkey\":\"Test Value\"},\"ResponseType\":"
            + "\"logon\"}]", listener);
    }

    public void reInitializeService() {
        injectOptionValue();
        eventListener = new MockEventListener();
        service = new PosApiService(eventListener, httpClient, posVendorDetails, options,
            serviceEndpoints, logger);
        injectAsyncHttpExector();
        reset(asyncHttpExecutor, response, httpClient);
    }

    private String pairingRequest() {
        PairingRequest pairingRequest = PairingMock.request();
        PairingResponse pairingResponse = PairingMock.response();
        String requestContent = PairingMock.toJson(pairingRequest);
        String responseContent = PairingMock.toJson(pairingResponse);

        when(response.getStatusCode()).thenReturn(200);
        when(response.getResponseBody()).thenReturn(responseContent);
        when(asyncHttpExecutor.post(AUTH_API + "/v1/pairing/cloudpos", requestContent))
            .thenReturn(response);

        service.pairingRequest(pairingRequest);
        return requestContent;
    }

    private PosVendorDetails posVendorDetails() {
        return new PosVendorDetails("test", "1.0", UUID.randomUUID(), UUID
            .randomUUID());
    }

    private void injectAsyncHttpExector() {
        try {
            Field field = service.getClass().getDeclaredField("asyncHttpExecutor");
            field.setAccessible(true);
            field.set(service, asyncHttpExecutor);
        }
        catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private void injectAuthTokenNotExpiringSoon() {
        injectAuthToken(LocalDateTime.now().plus(1, ChronoUnit.HOURS));
    }

    private void injectAuthToken(LocalDateTime dateTime) {
        Field authTokenField;
        try {
            authTokenField = service.getClass().getDeclaredField("authToken");
            authTokenField.setAccessible(true);
            authTokenField.set(service, new AuthToken("validtoken", dateTime));
        }
        catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private void injectAuthToken() {
        injectAuthToken(LocalDateTime.now());
    }

    private AuthToken getAuthToken() {
        Field authTokenField;
        try {
            authTokenField = service.getClass().getDeclaredField("authToken");
            authTokenField.setAccessible(true);
            return (AuthToken) authTokenField.get(service);
        }
        catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private void injectOptionValue() {
        try {
            Field field = options.getClass().getDeclaredField("asyncRequestTimeout");
            field.setAccessible(true);
            field.set(options, 1);
        }
        catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private void verifySecretAndAuthTokenAfterPair(boolean failed) {
        try {
            Field secretField = service.getClass().getDeclaredField("pairSecret");

            secretField.setAccessible(true);
            String secret = (String) secretField.get(service);
            AuthToken authToken = getAuthToken();
            if (failed) {
                assertNull(secret);
            }
            else {
                assertNotNull(secret);
            }
            assertNull(authToken);
        }
        catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
