package com.linkly.pos.sdk.models.enums;

import com.linkly.pos.sdk.common.Constants;
import com.linkly.pos.sdk.models.configureMerchant.ConfigureMerchantRequest;
import com.linkly.pos.sdk.models.logon.LogonRequest;
import com.linkly.pos.sdk.models.queryCard.QueryCardRequest;
import com.linkly.pos.sdk.models.reprintReceipt.ReprintReceiptRequest;
import com.linkly.pos.sdk.models.settlement.SettlementRequest;
import com.linkly.pos.sdk.models.status.StatusRequest;
import com.linkly.pos.sdk.models.transaction.TransactionRequest;

/**
 * Type of API response.
 */
public enum ResponseType {

    /**
     * Response is for a {@link LogonRequest}.
     */
    Logon(Constants.ResponseType.LOGON),

    /**
     * Response is for a {@link StatusRequest}.
     */
    Status(Constants.ResponseType.STATUS),

    /**
     * Response contains text the PIN pad is displaying.
     */
    Display(Constants.ResponseType.DISPLAY),

    /**
     * Response contains a receipt.
     */
    Receipt(Constants.ResponseType.RECEIPT),

    /**
     * Response is for a {@link ConfigureMerchantRequest} request.
     */
    ConfigureMerchant(Constants.ResponseType.CONFIGUREMERCHANT),

    /**
     * Response is for a {@link QueryCardRequest} request.
     */
    QueryCard(Constants.ResponseType.QUERYCARD),

    /**
     * Response is for a {@link ReprintReceiptRequest} request.
     */
    ReprintReceipt(Constants.ResponseType.REPRINTRECEIPT),

    /**
     * Response is for a {@link TransactionRequest} request.
     */
    Transaction(Constants.ResponseType.TRANSACTION),

    /**
     * Response is for a {@link SettlementRequest} request.
     */
    Settlement(Constants.ResponseType.SETTLEMENT),

    /**
     * This value is returned when an unexpected value was deserialised. Rather than throwing a
     * deserialisation exception it us up to the end-user how they want to handle the error.
     */
    Unknown("Unknown");

    private String value;

    /**
     * Private constructor for the ResponseType enum.
     * 
     * @param value
     *            The value associated with the response type.
     */
    private ResponseType(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with the response type.
     * 
     * @return String The value representing the response type.
     */
    public String getValue() {
        return value;
    }
}
