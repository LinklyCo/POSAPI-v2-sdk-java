package com.linkly.pos.sdk.service.impl;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.linkly.pos.sdk.service.IPosApiEventListener;

/**
 * Event listener proxy for {@link IPosApiEventListener} which will forward requests to a real
 * event listener and provide error handling and logging.
 */
public class PosApiEventListenerProxy implements IPosApiEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PosApiEventListenerProxy.class);
    private WeakReference<IPosApiEventListener> eventListener;

    /**
     * Initialise a new Pos Api Event Listener Proxy.
     * 
     * @param eventListener listener to use.
     * @throws IllegalArgumentException
     */
    public PosApiEventListenerProxy(IPosApiEventListener eventListener) {
        if (eventListener == null) {
            String message = "EventListener must not be null";
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }
        this.eventListener = new WeakReference<IPosApiEventListener>(eventListener);
    }

    /**
     * {@inheritDoc}
     * Implementation of receipt event handler.
     */
    @Override
    public void receipt(UUID sessionId, PosApiRequest request, ReceiptResponse response) {
        invokeListener(() -> getEventListener().receipt(sessionId, request, response), "receipt");
    }

    /**
     * {@inheritDoc}
     * Implementation of PIN pad display event handler.
     */
    @Override
    public void display(UUID sessionId, PosApiRequest request, DisplayResponse response) {
        invokeListener(() -> getEventListener().display(sessionId, request, response), "display");

    }

    /**
     * {@inheritDoc}
     * Implementation of API error response handler.
     */
    @Override
    public void error(UUID sessionId, IBaseRequest request, ErrorResponse error) {
        invokeListener(() -> getEventListener().error(sessionId, request, error), "error");

    }

    /**
     * {@inheritDoc}
     * Implementation of pairing complete handler.
     */
    @Override
    public void pairingComplete(PairingRequest request, PairingResponse response) {
        invokeListener(() -> getEventListener().pairingComplete(request, response),
            "pairingComplete");

    }

    /**
     * {@inheritDoc}
     * Implementation of transaction complete handler.
     */
    @Override
    public void transactionComplete(UUID sessionId, TransactionRequest request,
        TransactionResponse response) {
        invokeListener(() -> getEventListener().transactionComplete(sessionId, request, response),
            "transactionComplete");

    }

    /**
     * {@inheritDoc}
     * Implementation of status request completion handler.
     */
    @Override
    public void statusComplete(UUID sessionId, StatusRequest request, StatusResponse response) {
        invokeListener(() -> getEventListener().statusComplete(sessionId, request, response),
            "statusComplete");
    }

    /**
     * {@inheritDoc}
     * Implementation of logon completion handler.
     */
    @Override
    public void logonComplete(UUID sessionId, LogonRequest request, LogonResponse response) {
        invokeListener(() -> getEventListener().logonComplete(sessionId, request, response),
            "logonComplete");
    }

    /**
     * {@inheritDoc}
     * Implementation of settlement completion handler.
     */
    @Override
    public void settlementComplete(UUID sessionId, SettlementRequest request,
        SettlementResponse response) {
        invokeListener(() -> getEventListener().settlementComplete(sessionId, request, response),
            "settlementComplete");
    }

    /**
     * {@inheritDoc}
     * Implementation of query card completion handler.
     */
    @Override
    public void queryCardComplete(UUID sessionId, QueryCardRequest request,
        QueryCardResponse response) {
        invokeListener(() -> getEventListener().queryCardComplete(sessionId, request, response),
            "queryCardComplete");
    }

    /**
     * {@inheritDoc}
     * Implementation of configure merchant completion handler.
     */
    @Override
    public void configureMerchantComplete(UUID sessionId, ConfigureMerchantRequest request,
        ConfigureMerchantResponse response) {
        invokeListener(() -> getEventListener().configureMerchantComplete(sessionId, request,
            response), "configureMerchantComplete");
    }

    /**
     * {@inheritDoc}
     * Implementation of reprint receipt completion handler.
     */
    @Override
    public void reprintReceiptComplete(UUID sessionId, ReprintReceiptRequest request,
        ReprintReceiptResponse response) {
        invokeListener(() -> getEventListener().reprintReceiptComplete(sessionId, request,
            response), "reprintReceiptComplete");
    }

    /**
     * {@inheritDoc}
     * Implementation of result completion handler.
     */
    @Override
    public void resultComplete(ResultRequest request, List<PosApiResponse> responses) {
        invokeListener(() -> getEventListener().resultComplete(request, responses),
            "resultComplete");
    }

    /**
     * {@inheritDoc}
     * Implementation of retrieve transaction completion handler.
     */
    @Override
    public void retrieveTransactionComplete(RetrieveTransactionRequest request,
        List<TransactionResponse> responses) {
        invokeListener(() -> getEventListener().retrieveTransactionComplete(request, responses),
            "retrieveTransactionComplete");
    }

    /**
     *  Return pos event listener.
     * 
     * @return IPosApiEventListener
     */
    private IPosApiEventListener getEventListener() {
        IPosApiEventListener listener = eventListener.get();
        if (listener == null) {
            LOGGER.error("Listener does not exist");
            return null;
        }
        return listener;
    }

    /**
     * Invoke a listener with logging and exception handling.
     * 
     * @param action Listener to invoke wrapped in a lambda
     * @param listenerName Name of the listener, for logging purposes.
     */
    private void invokeListener(Runnable action, String listenerName) {
        try {
            LOGGER.debug("{}: Invoking {}() listener", getEventListener().getClass()
                .getCanonicalName());
            action.run();
            LOGGER.debug("{}: Invoking {}() listener completed", getEventListener().getClass()
                .getCanonicalName());
        }
        catch (Exception e) {
            LOGGER.error("{}: Invoking {}() listener threw an exception", getEventListener()
                .getClass().getCanonicalName(), e);
        }
    }
}
