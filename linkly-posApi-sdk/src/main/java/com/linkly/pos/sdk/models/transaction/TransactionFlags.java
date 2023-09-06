package com.linkly.pos.sdk.models.transaction;

import com.linkly.pos.sdk.models.enums.CardEntryType;
import com.linkly.pos.sdk.models.enums.CommsMethodType;
import com.linkly.pos.sdk.models.enums.CurrencyStatus;
import com.linkly.pos.sdk.models.enums.PayPassStatus;
import com.squareup.moshi.Json;

/**
 * The flags that apply to the transaction.
 */
public class TransactionFlags {

	@Json(name = "Offline")
    private String offline;

	@Json(name = "ReceiptPrinted")
    private String receiptPrinted;

	@Json(name = "CardEntry")
    private CardEntryType cardEntry;

	@Json(name = "CommsMethod")
    private CommsMethodType commsMethod;

	@Json(name = "Currency")
    private CurrencyStatus currency;

	@Json(name = "PayPass")
    private PayPassStatus payPass;

	@Json(name = "UndefinedFlag6")
    private String undefinedFlag6;

	@Json(name = "UndefinedFlag7")
    private String undefinedFlag7;

	  /**
     * Indicates the transaction connectivity state.
     * 
     * @return String
     */
    public String getOffline() {
        return offline;
    }

    /**
     * Sets the offline.
     * 
     * @param offline The String value of offline.
     */
    public void setOffline(String offline) {
        this.offline = offline;
    }

    /**
     * Indicates if the transaction receipt was printed.
     * 
     * @return String
     */
    public String getReceiptPrinted() {
        return receiptPrinted;
    }

    /**
     * Sets the receiptPrinted.
     * 
     * @param receiptPrinted The String value of receiptPrinted.
     */
    public void setReceiptPrinted(String receiptPrinted) {
        this.receiptPrinted = receiptPrinted;
    }

    /**
     * The type of card entry.
     * 
     * @return CardEntryType
     */
    public CardEntryType getCardEntry() {
        return cardEntry;
    }

    /**
     * Sets the cardEntryType.
     * 
     * @param cardEntry The CardEntryType value.
     */
    public void setCardEntry(CardEntryType cardEntry) {
        this.cardEntry = cardEntry;
    }

    /**
     * Type of comms method.
     * 
     * @return CommsMethodType
     */
    public CommsMethodType getCommsMethod() {
        return commsMethod;
    }

    /**
     * Sets the commsMethod.
     * 
     * @param commsMethod The CommsMethodType value.
     */
    public void setCommsMethod(CommsMethodType commsMethod) {
        this.commsMethod = commsMethod;
    }

    /**
     * Indicates the status of currency.
     * 
     * @return CurrencyStatus
     */
    public CurrencyStatus getCurrency() {
        return currency;
    }

    /**
     * Sets the currency.
     * 
     * @param currency The CurrencyStatus value.
     */
    public void setCurrency(CurrencyStatus currency) {
        this.currency = currency;
    }

    /**
     * Indicates the status of pay pass.
     * 
     * @return PayPassStatus
     */
    public PayPassStatus getPayPass() {
        return payPass;
    }

    /**
     * Sets the payPass.
     * 
     * @param payPass The PayPassStatus value.
     */
    public void setPayPass(PayPassStatus payPass) {
        this.payPass = payPass;
    }

    /**
     * Indicates the undefined flag6.
     * 
     * @return String
     */
    public String getUndefinedFlag6() {
        return undefinedFlag6;
    }

    /**
     * Sets the undefinedFlag6.
     * 
     * @param undefinedFlag6 The String value of undefinedFlag6.
     */
    public void setUndefinedFlag6(String undefinedFlag6) {
        this.undefinedFlag6 = undefinedFlag6;
    }

    /**
     * Indicates the undefined flag7.
     * 
     * @return String
     */
    public String getUndefinedFlag7() {
        return undefinedFlag7;
    }

    /**
     * Sets the undefinedFlag7.
     * 
     * @param undefinedFlag7 The String value of undefinedFlag7.
     */
    public void setUndefinedFlag7(String undefinedFlag7) {
        this.undefinedFlag7 = undefinedFlag7;
    }
}