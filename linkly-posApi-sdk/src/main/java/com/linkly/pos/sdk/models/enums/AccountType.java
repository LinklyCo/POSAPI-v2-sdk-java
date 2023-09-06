package com.linkly.pos.sdk.models.enums;

/**
 * Type of transaction account.
 */
public enum AccountType {

    /**
     * Prompt customer for account type.
     */
    Default(" "),

    /**
     * Cheque account type.
     */
    Cheque("1"),

    /**
     * Credit account type.
     */
    Credit("2"),

    /**
     * Savings account type.
     */
    Savings("3"),

    /**
     * This value is returned when an unexpected value was deeserialised. Rather than throwing a
     * deserialisation exception it us up to the end-user how they want to handle the error.
     */
    Unknown("Unknown");

    private String value;

    /**
     * Private constructor for the AccountType enum.
     * 
     * @param value The value associated with the account type.
     */
    private AccountType(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with the account type.
     * 
     * @return String The value representing the account type.
     */
    public String getValue() {
        return value;
    }
}