package com.linkly.pos.sdk.models.enums;

/**
 * EFTPOS settlement types.
 */
public enum SettlementType {

    /**
     * Perform a settlement on the terminal.
     * Remarks: Can only be performed once per day.
     */
    Settlement("S"),

    /**
     * Perform a pre-settlement on the terminal.
     * Remarks: Not supported by all PIN pads.
     */
    PreSettlement("P"),

    /**
     * Perform a last settlement on the terminal.
     * Remarks: Not supported by all PIN pads.
     */
    LastSettlement("L"),

    /**
     * Perform a summary totals on the terminal.
     * Remarks: Not supported by all PIN pads.
     */
    SummaryTotals("U"),

    /**
     * Perform a shift/sub totals on the terminal.
     * Remarks: Not supported by all PIN pads.
     */
    SubShiftTotals("H"),

    /**
     * Perform a transaction listing on the terminal.
     * Remarks: Not supported by all PIN pads.
     */
    DetailedTransactionListing("I"),

    /**
     * Start cash
     * Remarks: Not supported by all PIN pads.
     */
    StartCash("M"),

    /**
     * SAF report
     * Remarks: Not supported by all PIN pads.
     */
    StoreAndForwardTotals("F"),

    /**
     * Daily cash
     * Remarks: StGeorge agency only.
     */
    DailyCashStatement("D");

    private String value;
    
    /**
     * Private constructor for the SettlementType enum.
     * 
     * @param value The value associated with the settlement type.
     */
    private SettlementType(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with the settlement type.
     * 
     * @return String The value representing the settlement type.
     */
    public String getValue() {
        return value;
    }
}
