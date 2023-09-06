package com.linkly.pos.sdk.models.enums;

/**
 * PIN pad terminal key handling scheme.
 */
public enum KeyHandlingType {

    /**
     * Single-DES encryption standard.
     */
    SingleDes("1"),

    /**
     * Triple-DES encryption standard.
     */
    TripleDes("2"),

    /**
     * Unknown encryption standard.
     */
    Unknown(" ");

    private String value;
    
    /**
     * Private constructor for the KeyHandlingType enum.
     * 
     * @param value The value associated with the key handling type.
     */
    private KeyHandlingType(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with the key handling type.
     * 
     * @return String The value representing the key handling type.
     */
    public String getValue() {
        return value;
    }
}
