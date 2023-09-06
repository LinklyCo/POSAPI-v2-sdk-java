package com.linkly.pos.sdk.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        service.pairingRequest(pairingRequest);
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
        service.logonRequest(request);
        assertTrue(eventListener.getResponseContents().size() > 1);
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
    void sendResultRequestAsync_timeExhausted_tooEarlyResponse() {
        pairingRequest();
        injectOptionValue();

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200).thenReturn(200).thenReturn(425);
        when(response.getResponseBody())
            .thenReturn(AuthTokenMock.tokenResponseContent())
            .thenReturn("Mock content")
            .thenReturn(LogonMock.logonResponseContent());

        when(asyncHttpExecutor.post(anyString(), anyString(), anyString())).thenReturn(response);

        service.logonRequest(new LogonRequest());

        eventListener.getResponseContent("");
    }

    @Test
    void sendResultRequestAsync_failed() throws InterruptedException {
        pairingRequest();
        injectOptionValue();

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

        service.transactionRequest(cashRequest);
        assertTrue(eventListener.getResponseContents().size() > 1);
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

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            service.retrieveTransactionRequest(retrieveTransactionRequest);
        });
        assertEquals("No Implementation Yet!", exception.getMessage());
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
        service.statusRequest(request);

        assertTrue(eventListener.getResponseContents().size() > 1);
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
            + "{\"\":false,\"AutoCompletion\":false,\"Balance\":false,\"CashOut\":"
            + "false,\"Completions\":false,\"Deposit\":false,\"EFB\":false,\"EMV\":"
            + "false,\"Moto\":false,\"PreAuth\":false,\"Refund\":false,\"Tipping\":"
            + "false,\"Training\":false,\"Transfer\":false,\"Voucher\":false,"
            + "\"Withdrawal\":false},\"RefundLimit\":0,\"ResponseType\":\"status\","
            + "\"SafCount\":0,\"SafCreditLimit\":0,\"SafDebitLimit\":0,\"Success\":"
            + "false,\"TimeOut\":0,\"TotalMemoryInTerminal\":0}", listener);
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
        service.settlementRequest(request);
        assertTrue(eventListener.getResponseContents().size() > 1);
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
        service.queryCardRequest(request);
        assertTrue(eventListener.getResponseContents().size() > 1);
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
        service.configureMerchantRequest(request);
        assertTrue(eventListener.getResponseContents().size() > 1);
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
        service.reprintReceiptRequest(request);
        assertTrue(eventListener.getResponseContents().size() > 1);
    }

    @Test
    void reprintReceiptRequest_success() {
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

        String listener = eventListener.getResponseContent("reprintReceipt");

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();

        assertNotNull(uuid);
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
        service.sendKeyRequest(new SendKeyRequest());
        assertTrue(eventListener.getResponseContents().size() > 1);
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

        when(asyncHttpExecutor.post(anyString(), anyString())).thenReturn(response);
        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);
        when(response.getStatusCode()).thenReturn(200).thenReturn(200);
        when(response.getResponseBody())
            .thenReturn(AuthTokenMock.tokenResponseContent())
            .thenReturn(ResultRequestMock.resultRequestResponseContent());

        when(asyncHttpExecutor.get(anyString(), anyString())).thenReturn(response);

        service.resultRequest(new ResultRequest(UUID.randomUUID()));

        Thread.sleep(2000);
        String listener = eventListener.getResponseContent("logon");

        verify(response, atLeast(3)).getStatusCode();
        verify(response, atLeast(3)).getResponseBody();
        assertEquals("[{\"PurchaseAnalysisData\":{\"testkey\":\"Test Value\"},\"ResponseType\":"
            + "\"logon\"}]", listener);
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

    private void injectAuthToken() {
        Field authTokenField;
        try {
            authTokenField = service.getClass().getDeclaredField("authToken");
            authTokenField.setAccessible(true);
            authTokenField.set(service, new AuthToken("validtoken", LocalDateTime.now()));
        }
        catch (Exception e) {
            throw new AssertionError(e);
        }
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
            Field authTokenField = service.getClass().getDeclaredField("authToken");
            secretField.setAccessible(true);
            authTokenField.setAccessible(true);
            String secret = (String) secretField.get(service);
            AuthToken authToken = (AuthToken) authTokenField.get(service);
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