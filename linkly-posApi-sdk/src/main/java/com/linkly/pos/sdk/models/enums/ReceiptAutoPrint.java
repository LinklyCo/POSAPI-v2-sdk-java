package com.linkly.pos.sdk.models.enums;

/**
 * Indicates how receipts from the PIN pad will be handled.
 */
public enum ReceiptAutoPrint {

    /**
     * Return all receipts to the POS in a receipt event.
     */
    POS("0"),

    /**
     * Print all receipts from the PIN pad printer.
     */
    PinPad("9"),

    /**
     * Print all merchant/signature receipts from the PIN pad printer, return all other receipts to the
     * POS in the transaction/logon/settlement response
     */
    Both("7");

    private String value;
    
    /**
     * Private constructor for the ReceiptAutoPrint enum.
     * 
     * @param value The value associated with receipt auto print.
     */
    private ReceiptAutoPrint(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with receipt auto print.
     * 
     * @return String The value representing the receipt auto print.
     */
    public String getValue() {
        return value;
    }
}
