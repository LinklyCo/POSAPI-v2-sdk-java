package com.linkly.pos.sdk.models.enums;

/**
 * Type of logon to perform.
 */
public enum LogonType {

    /**
     * Standard EFT logon to the bank.
     */
    Standard(" "),

    /**
     * Standard EFT logon to the bank.
     * <remarks>Not supported by all PIN pads.</remarks>
     */
    RSA("4"),

    /**
     * Standard EFT logon to the bank.
     * Remarks: Not supported by all PIN pads.
     */
    TmsFull("5"),

    /**
     * Standard EFT logon to the bank.
     * Remarks: Not supported by all PIN pads.
     */
    TmsParams("6"),

    /**
     * Standard EFT logon to the bank.
     * Remarks: Not supported by all PIN pads.
     */
    TmsSoftware("7"),

    /**
     * Standard EFT log off to the bank.
     */
    Logoff("8"),

    /**
     * Enables diagnostics.
     */
    Diagnostics("1");

    private String value;
    
    /**
     * Private constructor for the LogonType enum.
     * 
     * @param value The value associated with the logon type.
     */
    private LogonType(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with the logon type.
     * 
     * @return String The value representing the logon type.
     */
    public String getValue() {
        return value;
    }
}