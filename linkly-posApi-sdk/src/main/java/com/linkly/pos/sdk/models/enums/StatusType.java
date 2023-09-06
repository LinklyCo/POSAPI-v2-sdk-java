package com.linkly.pos.sdk.models.enums;

/**
 * Indicates the requested status type.
 */
public enum StatusType {

    /**
     * Request the EFT status from the PIN pad.
     */
    Standard("0"),

    /**
     * Not supported by all PIN pads.
     */
    TerminalAppInfo("1"),

    /**
     * Not supported by all PIN pads.
     */
    AppCpat("2"),

    /**
     * Not supported by all PIN pads.
     */
    AppNameTable("3"),

    Undefined("4"),

    /**
     * Not supported by all PIN pads.
     */
    Preswipe("5");

    private String value;
    
    /**
     * Private constructor for the StatusType enum.
     * 
     * @param value The value associated with the status type.
     */
    private StatusType(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with the status type.
     * 
     * @return String The value representing the status type.
     */
    public String getValue() {
        return value;
    }
}