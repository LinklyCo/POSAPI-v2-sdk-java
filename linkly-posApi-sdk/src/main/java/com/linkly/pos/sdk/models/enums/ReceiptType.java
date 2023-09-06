package com.linkly.pos.sdk.models.enums;

/**
 * Type of receipt.
 */
public enum ReceiptType {

    /**
     * Logon receipt.
     */
    Logon("L"),

    /**
     * Customer transaction receipt.
     */
    Customer("C"),

    /**
     * Merchant transaction receipt.
     */
    Merchant("M"),

    /**
     * Settlement receipt - usually contains the signature receipt line and should be printed
     * immediately.
     */
    Settlement("S"),

    /**
     * Receipt text was received. Used internally by components and should never be received.
     */
    ReceiptText("R"),

    /**
     * This value is returned when an unexpected value was deserialised. Rather than throwing a
     * deserialisation exception it us up to the end-user how they want to handle the error.
     */
    Unknown("Unknown");

    private String value;
    
    /**
     * Private constructor for the ReceiptType enum.
     * 
     * @param value The value associated with the receipt type.
     */
    private ReceiptType(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with the receipt type.
     * 
     * @return String The value representing the receipt type.
     */
    public String getValue() {
        return value;
    }
}
