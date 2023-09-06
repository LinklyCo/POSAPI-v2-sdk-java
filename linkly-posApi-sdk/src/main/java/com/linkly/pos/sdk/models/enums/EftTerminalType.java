package com.linkly.pos.sdk.models.enums;

/**
 * Indicates the EFT terminal hardware type.
 */
public enum EftTerminalType {

    /**
     * Ingenico NPT 710 PIN pad terminal.
     */
    IngenicoNPT710("IngenicoNPT710"),

    /**
     * Ingenico NPT PX328 PIN pad terminal.
     */
    IngenicoPX328("IngenicoPX328"),

    /**
     * Ingenico NPT i5110 PIN pad terminal.
     */
    Ingenicoi5110("Ingenicoi5110"),

    /**
     * Ingenico NPT i3070 PIN pad terminal.
     */
    Ingenicoi3070("Ingenicoi3070"),

    /**
     * Sagem PIN pad terminal.
     */
    Sagem("Sagem"),

    /**
     * Verifone PIN pad terminal.
     */
    Verifone("Verifone"),

    /**
     * Keycorp PIN pad terminal.
     */
    Keycorp("Keycorp"),

    /**
     * Linkly's Virtual PIN pad.
     */
    LinklyVirtualPinPad("PCEFTPOSVirtualPinpad"),

    /**
     * Unknown PIN pad terminal.
     */
    Unknown("Unknown");

    private String value;

    /**
     * Private constructor for the EftTerminalType enum.
     * 
     * @param value The value associated with the eft terminal type.
     */
    private EftTerminalType(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with the eft terminal type.
     * 
     * @return String The value representing the eft terminal type.
     */
    public String getValue() {
        return value;
    }
}
