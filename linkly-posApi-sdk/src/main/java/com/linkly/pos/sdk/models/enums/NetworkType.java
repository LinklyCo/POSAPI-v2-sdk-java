package com.linkly.pos.sdk.models.enums;

/**
 * Type of PIN pad network connection to bank.
 */
public enum NetworkType {

    /**
     * Leased line bank connection.
     */
    Leased("1"),

    /**
     * Dial-up bank connection.
     */
    Dialup("2"),

    /**
     * Unknown bank connection.
     */
    Unknown(" ");

    private String value;
    
    /**
     * Private constructor for the NetworkType enum.
     * 
     * @param value The value associated with the network type.
     */
    private NetworkType(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with the network type.
     * 
     * @return String The value representing the network type.
     */
    public String getValue() {
        return value;
    }
}
