package com.linkly.pos.sdk.models.enums;

/**
 * The Pay Pass status of the transaction.
 */
public enum PayPassStatus {

    /**
     * Pay Pass conversion status was not set by the PIN pad.
     */
    NotSet(" "),

    /**
     * Pay Pass was used in the transaction.
     */
    PayPassUsed("1"),

    /**
     * Pay Pass was not used in the transaction.
     */
    PayPassNotUsed("0"),

    /**
     * This value is returned when an unexpected value was deserialised. Rather than throwing a
     * deserialisation exception it us up to the end-user how they want to handle the error.
     */
    Unknown("Unknown");

    private String value;
    
    /**
     * Private constructor for the PayPassStatus enum.
     * 
     * @param value The value associated with the pay pass status.
     */
    private PayPassStatus(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with the pay pass status.
     * 
     * @return String The value representing the pay pass status.
     */
    public String getValue() {
        return value;
    }
}
