package com.linkly.pos.sdk.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.models.ErrorResponse;
import com.linkly.pos.sdk.models.PosApiRequest;
import com.linkly.pos.sdk.models.PosApiResponse;
import com.linkly.pos.sdk.models.authentication.PairingRequest;
import com.linkly.pos.sdk.models.authentication.PairingResponse;
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
import com.linkly.pos.sdk.models.settlement.SettlementRequest;
import com.linkly.pos.sdk.models.settlement.SettlementResponse;
import com.linkly.pos.sdk.models.status.StatusRequest;
import com.linkly.pos.sdk.models.status.StatusResponse;
import com.linkly.pos.sdk.models.transaction.RetrieveTransactionRequest;
import com.linkly.pos.sdk.models.transaction.TransactionRequest;
import com.linkly.pos.sdk.models.transaction.TransactionResponse;
import com.linkly.pos.sdk.service.IPosApiEventListener;

@SuppressWarnings("unchecked")
class PosApiEventListenerProxyTest {

    private IPosApiEventListener mockListener = mock(IPosApiEventListener.class);
    private PosApiEventListenerProxy proxyListener = new PosApiEventListenerProxy(mockListener);

    @Test
    public void should_invoke_receipt() {
        proxyListener.receipt(UUID.randomUUID(), new PosApiRequest(), new ReceiptResponse());
        verify(mockListener, times(1)).receipt(any(UUID.class), any(PosApiRequest.class), any(
            ReceiptResponse.class));
    }

    @Test
    public void should_invoke_display() {
        proxyListener.display(UUID.randomUUID(), new PosApiRequest(), new DisplayResponse());
        verify(mockListener, times(1)).display(any(UUID.class), any(PosApiRequest.class), any(
            DisplayResponse.class));
    }

    @Test
    public void should_invoke_error() {
        ErrorResponse errorResponse = new ErrorResponse(ErrorSource.API, 500, "error", null);
        proxyListener.error(UUID.randomUUID(), new PosApiRequest(), errorResponse);
        verify(mockListener, times(1)).error(any(UUID.class), any(PosApiRequest.class), any(
            ErrorResponse.class));
    }

    @Test
    public void should_invoke_pairingComplete() {
        proxyListener.pairingComplete(new PairingRequest(), new PairingResponse());
        verify(mockListener, times(1)).pairingComplete(any(PairingRequest.class), any(
            PairingResponse.class));
    }

    @Test
    public void should_invoke_transactionComplete() {
        proxyListener.transactionComplete(UUID.randomUUID(), new TransactionRequest(),
            new TransactionResponse());
        verify(mockListener, times(1)).transactionComplete(any(UUID.class), any(
            TransactionRequest.class), any(TransactionResponse.class));
    }

    @Test
    public void should_invoke_statusComplete() {
        proxyListener.statusComplete(UUID.randomUUID(), new StatusRequest(),
            new StatusResponse());
        verify(mockListener, times(1)).statusComplete(any(UUID.class), any(
            StatusRequest.class), any(StatusResponse.class));
    }

    @Test
    public void should_invoke_logonComplete() {
        proxyListener.logonComplete(UUID.randomUUID(), new LogonRequest(),
            new LogonResponse());
        verify(mockListener, times(1)).logonComplete(any(UUID.class), any(
            LogonRequest.class), any(LogonResponse.class));
    }

    @Test
    public void should_invoke_settlementComplete() {
        proxyListener.settlementComplete(UUID.randomUUID(), new SettlementRequest(),
            new SettlementResponse());
        verify(mockListener, times(1)).settlementComplete(any(UUID.class), any(
            SettlementRequest.class), any(SettlementResponse.class));
    }

    @Test
    public void should_invoke_queryCardComplete() {
        proxyListener.queryCardComplete(UUID.randomUUID(), new QueryCardRequest(),
            new QueryCardResponse());
        verify(mockListener, times(1)).queryCardComplete(any(UUID.class), any(
            QueryCardRequest.class), any(QueryCardResponse.class));
    }

    @Test
    public void should_invoke_configureMerchantComplete() {
        proxyListener.configureMerchantComplete(UUID.randomUUID(), new ConfigureMerchantRequest(),
            new ConfigureMerchantResponse());
        verify(mockListener, times(1)).configureMerchantComplete(any(UUID.class), any(
            ConfigureMerchantRequest.class), any(ConfigureMerchantResponse.class));
    }

    @Test
    public void should_invoke_reprintReceiptComplete() {
        proxyListener.reprintReceiptComplete(UUID.randomUUID(), new ReprintReceiptRequest(),
            new ReprintReceiptResponse());
        verify(mockListener, times(1)).reprintReceiptComplete(any(UUID.class), any(
            ReprintReceiptRequest.class), any(ReprintReceiptResponse.class));
    }

    @Test
    public void should_invoke_resultComplete() {
        List<PosApiResponse> responses = Arrays.asList(new PosApiResponse());
        proxyListener.resultComplete(new ResultRequest(UUID.randomUUID()), responses);
        verify(mockListener, times(1)).resultComplete(any(ResultRequest.class), any(List.class));
    }

    @Test
    public void should_invoke_retrieveTransactionComplete() {
        List<TransactionResponse> responses = Arrays.asList(new TransactionResponse());
        proxyListener.retrieveTransactionComplete(new RetrieveTransactionRequest(), responses);
        verify(mockListener, times(1)).retrieveTransactionComplete(any(
            RetrieveTransactionRequest.class), any(List.class));
    }
}
