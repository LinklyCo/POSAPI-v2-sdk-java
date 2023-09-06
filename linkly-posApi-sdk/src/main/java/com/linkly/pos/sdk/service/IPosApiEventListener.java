package com.linkly.pos.sdk.service;

import java.util.List;
import java.util.UUID;

import com.linkly.pos.sdk.models.ErrorResponse;
import com.linkly.pos.sdk.models.IBaseRequest;
import com.linkly.pos.sdk.models.PosApiRequest;
import com.linkly.pos.sdk.models.PosApiResponse;
import com.linkly.pos.sdk.models.authentication.PairingRequest;
import com.linkly.pos.sdk.models.authentication.PairingResponse;
import com.linkly.pos.sdk.models.configureMerchant.ConfigureMerchantRequest;
import com.linkly.pos.sdk.models.configureMerchant.ConfigureMerchantResponse;
import com.linkly.pos.sdk.models.display.DisplayResponse;
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

/**
 * Interface for listening to POS API v2 responses. This interface needs to be implemented and
 * all methods should provide logic for handling the response.
 */
public interface IPosApiEventListener {

    /**
     * Receipt event handler. Will be invoked automatically during the course of
     * some requests when a receipt event is fired by the PIN pad.
     * 
     * @param sessionId Session ID of the original request.
     * @param request Original request the event belongs to.
     * @param response Receipt message from the PIN pad.
     */
    void receipt(UUID sessionId, PosApiRequest request, ReceiptResponse response);

    /**
     * PIN pad display event handler. Will be invoked automatically during the course of
     * some requests when the PIN pad displays some text to the customer.
     * 
     * @param sessionId Session ID of the original request.
     * @param request Original request the event belongs to.
     * @param response Display message from the PIN pad.
     */
    void display(UUID sessionId, PosApiRequest request, DisplayResponse response);

    /**
     * API error response handler, invoked when the API returns an unsuccessful response to
     * a request.
     * 
     * @param sessionId Session ID of the original request. Will be null for requests which
     * do not use sessions such as pairing.
     * @param request Original request the event belongs to.
     * @param error Error message thrown by the API.
     */
    void error(UUID sessionId, IBaseRequest request, ErrorResponse error);

    /**
     * Pairing complete handler. Returns the secret which should be stored by the POS.
     * 
     * @param request Original request the event belongs to.
     * @param response Pairing completion response
     */
    void pairingComplete(PairingRequest request, PairingResponse response);

    /**
     * Transaction completion handler.
     * 
     * @param sessionId Session ID of the original request.
     * @param request Original request the event belongs to.
     * @param response Status completion response
     */
    void transactionComplete(UUID sessionId, TransactionRequest request,
        TransactionResponse response);

    /**
     * Status request completion handler.
     * 
     * @param sessionId Session ID of the original request.
     * @param request Original request the event belongs to.
     * @param response Status completion response
     */
    void statusComplete(UUID sessionId, StatusRequest request, StatusResponse response);

    /**
     * Logon completion handler.
     * 
     * @param sessionId Session ID of the original request.
     * @param request Original request the event belongs to.
     * @param response Status completion response
     */
    void logonComplete(UUID sessionId, LogonRequest request, LogonResponse response);

    /**
     * Settlement completion handler.
     * 
     * @param sessionId Session ID of the original request.
     * @param request Original request the event belongs to.
     * @param response Settlement completion response
     */
    void settlementComplete(UUID sessionId, SettlementRequest request,
        SettlementResponse response);

    /**
     * Query card completion handler.
     * 
     * @param sessionId Session ID of the original request.
     * @param request Original request the event belongs to.
     * @param response Querycard completion response
     */
    void queryCardComplete(UUID sessionId, QueryCardRequest request, QueryCardResponse response);

    /**
     * Configure merchant completion handler.
     * 
     * @param sessionId Session ID of the original request.
     * @param request Original request the event belongs to.
     * @param response Configure completion response
     */
    void configureMerchantComplete(UUID sessionId, ConfigureMerchantRequest request,
        ConfigureMerchantResponse response);

    /**
     * Reprint receipt completion handler.
     * 
     * @param sessionId Session ID of the original request.
     * @param request Original request the event belongs to.
     * @param response Reprint receipt completion response
     */
    void reprintReceiptComplete(UUID sessionId, ReprintReceiptRequest request,
        ReprintReceiptResponse response);

    /**
     * Result completion handler.
     * 
     * @param request Original request the event belongs to.
     * @param responses All current responses associated with the
     * {@link ResultRequest#getSessionId}
     */
    void resultComplete(ResultRequest request, List<PosApiResponse> responses);

    /**
     * Retrieve transaction completion handler.
     * 
     * @param request Original request the event belongs to.
     * @param responses Transaction result(s)
     */
    void retrieveTransactionComplete(RetrieveTransactionRequest request,
        List<TransactionResponse> responses);
}
