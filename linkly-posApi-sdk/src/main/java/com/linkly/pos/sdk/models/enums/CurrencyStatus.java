package com.linkly.pos.sdk.models.enums;

/**
 * The currency conversion status for the transaction.
 */
public enum CurrencyStatus {

    /**
     * Currency conversion status was not set by the PIN pad.
     */
    NotSet(" "),

    /**
     * Transaction amount was processed in Australian Dollars.
     */
    AUD("0"),

    /**
     * Transaction amount was currency converted.
     */
    Converted("1"),

    /**
     * This value is returned when an unexpected value was deeserialised. Rather than throwing a
     * deserialisation exception it us up to the end-user how they want to handle the error.
     */
    Unknown("Unknown");

    private String value;

    /**
     * Private constructor for the CurrencyStatus enum.
     * 
     * @param value The value associated with the currency status.
     */
    private CurrencyStatus(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with the currency status.
     * 
     * @return String The value representing the currency status.
     */
    public String getValue() {
        return value;
    }
}
