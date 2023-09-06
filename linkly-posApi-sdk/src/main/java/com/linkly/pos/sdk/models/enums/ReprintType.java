package com.linkly.pos.sdk.models.enums;

/**
 * Receipt re-print mode.
 */
public enum ReprintType {

    /**
     * Get the last receipt.
     */
    GetLast("2"),

    /**
     * Re-print the last receipt.
     */
    Reprint("1");

    private String value;
    
    /**
     * Private constructor for the InputType enum.
     * 
     * @param value The value associated with the input type.
     */
    private ReprintType(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with the input type.
     * 
     * @return String The value representing the input type.
     */
    public String getValue() {
        return value;
    }
}
