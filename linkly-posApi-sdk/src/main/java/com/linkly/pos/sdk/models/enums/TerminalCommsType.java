package com.linkly.pos.sdk.models.enums;

/**
 * PIN pad terminal communication option.
 */
public enum TerminalCommsType {

    /**
     * Cable link communications.
     */
    Cable("0"),

    /**
     * Intrared link communications.
     */
    Infrared("1"),

    /**
     * Unknown link communications.
     */
    Unknown(" ");

    private String value;
    
    /**
     * Private constructor for the TerminalCommsType enum.
     * 
     * @param value The value associated with the terminal comms type.
     */
    private TerminalCommsType(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with the terminal comms type.
     * 
     * @return String The value representing the terminal comms type.
     */
    public String getValue() {
        return value;
    }
}
