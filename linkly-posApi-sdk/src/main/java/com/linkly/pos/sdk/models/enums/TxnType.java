package com.linkly.pos.sdk.models.enums;

/**
 * Type of transaction to be performed.
 */
public enum TxnType {

    /**
     * Purchase transaction.
     */
    Purchase("P"),

    /**
     * Cash out only transaction.
     */
    Cash("C"),

    /**
     * Refund transaction.
     */
    Refund("R"),

    /**
     * Deposit transaction.
     */
    Deposit("D"),

    /**
     * Pre-authorisation.
     */
    PreAuth("A"),

    /**
     * Renew the expiry date of an existing pre-authorisation.
     */
    PreAuthExtend("E"),

    /**
     * Top-up (increase) a pre-authorisation amount.
     */
    PreAuthTopUp("U"),

    /**
     * Cancel (void) a pre-authorisation.
     */
    PreAuthCancel("Q"),

    /**
     * Reduce a pre-authorisation amount.
     */
    PreAuthPartialCancel("O"),

    /**
     * Complete a pre-authorisation.
     */
    PreAuthComplete("L"),

    /**
     * Perform an inquiry or verify the amount of a pre-authorisation.
     */
    PreAuthInquiry("N"),

    /**
     * Void transaction.
     */
    Void("I"),

    /**
     * Transaction type was not set by the PIN pad.
     */
    NotSet(" "),

    /**
     * This value is returned when an unexpected value was deserialised. Rather than throwing a
     * deserialisation exception it us up to the end-user how they want to handle the error. In theory the
     * POS should never receive a {@link TxnType} it did not initiate so this value should never be
     * received.
     */
    Unknown("Unknown");

    private String value;
    
    /**
     * Private constructor for the InpTxnTypeutType enum.
     * 
     * @param value The value associated with the transaction type.
     */
    private TxnType(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with the transaction type.
     * 
     * @return String The value representing the transaction type.
     */
    public String getValue() {
        return value;
    }
}
