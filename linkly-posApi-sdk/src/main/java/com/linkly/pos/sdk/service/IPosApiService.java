package com.linkly.pos.sdk.service;

import java.util.UUID;

import com.linkly.pos.sdk.models.authentication.PairingRequest;
import com.linkly.pos.sdk.models.configureMerchant.ConfigureMerchantRequest;
import com.linkly.pos.sdk.models.logon.LogonRequest;
import com.linkly.pos.sdk.models.queryCard.QueryCardRequest;
import com.linkly.pos.sdk.models.reprintReceipt.ReprintReceiptRequest;
import com.linkly.pos.sdk.models.result.ResultRequest;
import com.linkly.pos.sdk.models.sendKey.SendKeyRequest;
import com.linkly.pos.sdk.models.settlement.SettlementRequest;
import com.linkly.pos.sdk.models.status.StatusRequest;
import com.linkly.pos.sdk.models.transaction.RetrieveTransactionRequest;
import com.linkly.pos.sdk.models.transaction.TransactionRequest;

/**
 * Interface for communicating with the POS API v2. All of the requests in this interface should use
 * the {@link IPosApiEventListener} to return the API result and any related messages
 * asynchronously. Any API errors should invoke the provided {@link IPosApiEventListener#error}
 * listener.
 */
public interface IPosApiService {

    /**
     * Set the pair secret obtained from initial pairing. See {@link PairingRequest}. It is not
     * necessary to call this post initial pairing.
     * @param pairSecret Pairing secret required to authenticate the service.
     */
    void setPairSecret(String pairSecret);

    /**
     * Pair with the PIN pad. Pairing only needs to occur once and must occur before any requests can be
     * sent to the PIN pad. Upon successful pairing the
     * {@link IPosApiEventListener#pairingComplete} listener will be invoked.
     * {@link com.linkly.pos.sdk.models.authentication.PairingResponse#getSecret} should be stored securely by the client. In cases where the
     * {@link PosApiService} is initialised and the pair secret is known, it should be passed to the
     * service using {@link SetPairSecret} to avoid unnecessary re-pairing.
     * 
     * @param request Pairing request.
     */
    void pairingRequest(PairingRequest request);

    /**
     * Send a transaction request. This method will invoke {@link IPosApiEventListener#display} and
     * {@link IPosApiEventListener#receipt} listeners as the corresponding events are received from
     * the PIN pad and {@link IPosApiEventListener#transactionComplete} upon successful completion
     * of the request.
     * 
     * @param request The following transaction models are supported:
     * {@link PurchaseRequest}
     * {@link RefundRequest}
     * {@link CashRequest}
     * {@link DepositRequest}
     * {@link PreAuthRequest}
     * {@link PreAuthExtendRequest}
     * {@link PreAuthTopUpRequest}
     * {@link PreAuthCancelRequest}
     * {@link PreAuthPartialCancelRequest}
     * {@link PreAuthCompletionRequest}
     * {@link PreAuthInquiryRequest}
     * {@link PreAuthSummaryRequest}
     * {@link VoidRequest}
     * @return UUID SessionId of the request
     */
    UUID transactionRequest(TransactionRequest request);

    /**
     * Send a status request. This method will invoke the
     * {@link  IPosApiEventListener.StatusComplete} listener upon successful completion of the
     * request.
     * 
     * @param request Status request object.
     * @return UUID SessionId of the request
     */
    UUID statusRequest(StatusRequest request);

    /**
     * Send a logon request. This method will invoke {@link IPosApiEventListener#display} and
     * {@link IPosApiEventListener#receipt} listeners as the corresponding events are received from
     * the PIN pad and {@link IPosApiEventListener#logonComplete} upon successful completion
     * of the request.
     * 
     * @param request Logon request object.
     * @return UUID SessionId of the request
     */
    UUID logonRequest(LogonRequest request);

    /**
     * Send a settlement request. This method will invoke {@link IPosApiEventListener#display} and
     * {@link IPosApiEventListener#receipt} listeners as the corresponding events are received from
     * the PIN pad and {@link IPosApiEventListener#settlementComplete} upon successful completion
     * of the request.
     * 
     * @param request Settlement request object.
     * @return UUID SessionId of the request
     */
    UUID settlementRequest(SettlementRequest request);

    /**
     * Send a query card request. This method will invoke the
     * {@link IPosApiEventListener#display} listener as the events are received from the
     * PIN pad and {@link IPosApiEventListener#queryCardComplete} upon successful completion
     * of the request.
     * 
     * @param request Query card request object.
     * @return UUID SessionId of the request
     */
    UUID queryCardRequest(QueryCardRequest request);

    /**
     * Send a request to configure the merchant's PIN pad settings. This method will invoke the
     * {@link IPosApiEventListener#configureMerchantComplete} listener upon successful
     * completion of this request.
     * 
     * @param request Configure merchant request object.
     * @return UUID SessionId of the request
     */
    UUID configureMerchantRequest(ConfigureMerchantRequest request);

    /**
     * Send a request to reprint a previous receipt. This method will invoke the
     * {@link IPosApiEventListener#reprintReceiptComplete} listener upon successful completion
     * of this request.
     * 
     * @param request Reprint receipt request object.
     * @return UUID SessionId of the request
     */
    UUID reprintReceiptRequest(ReprintReceiptRequest request);

    /**
     * Send a key press to the PIN pad.
     * 
     * @param request Send key request object.
     */
    void sendKeyRequest(SendKeyRequest request);

    /**
     * Retrieve all the events for a session. This method will invoke the
     * {@link IPosApiEventListener#resultComplete} listener upon successful completion of this
     * request.
     * 
     * @param request Result request object
     */
    void resultRequest(ResultRequest request);

    /**
     * Retrieve historical transaction results. This method will invoke the
     * {@link IPosApiEventListener#retrieveTransactionComplete} listener upon successful
     * completion of this request.
     * 
     * @param request Retrieve transaction request object.
     */
    void retrieveTransactionRequest(RetrieveTransactionRequest request);
}
