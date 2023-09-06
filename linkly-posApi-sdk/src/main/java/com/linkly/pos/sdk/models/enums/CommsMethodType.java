package com.linkly.pos.sdk.models.enums;

/**
 * The communications method used to process the transaction.
 */
public enum CommsMethodType {

    /**
     * Comms method type was not set by the PIN pad.
     */
    NotSet(" "),

    /**
     * Transaction was sent to the bank using an unknown method.
     */
    Unknown("0"),

    /**
     * Transaction was sent to the bank using a P66 modem.
     */
    P66("1"),

    /**
     * Transaction was sent to the bank using an Argent.
     */
    Argen("2"),

    /**
     * Transaction was sent to the bank using an X25.
     */
    X25("3");

    private String value;

    /**
     * Private constructor for the CommsMethodType enum.
     * 
     * @param value The value associated with the comms method type.
     */
    private CommsMethodType(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with the comms method type.
     * 
     * @return String The value representing the comms method type.
     */
    public String getValue() {
        return value;
    }
}
