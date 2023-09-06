package com.linkly.pos.sdk.models.enums;

/**
 * The card entry type of the transaction.
 */
public enum CardEntryType {

    /**
     * Manual entry type was not set by the PIN pad.
     */
    NotSet(" "),

    /**
     * Unknown manual entry type. PIN pad may not support this flag.
     */
    Unknown("0"),

    /**
     * Card was swiped.
     */
    Swiped("S"),

    /**
     * Card number was keyed.
     */
    Keyed("K"),

    /**
     * Card number was read by a bar code scanner.
     */
    BarCode("B"),

    /**
     * Card number was read from a chip card.
     */
    ChipCard("E"),

    /**
     * Card number was read from a contactless reader.
     */
    Contactless("C");

    private String value;

    /**
     * Private constructor for the CardEntryType enum.
     * 
     * @param value The value associated with the card entry type.
     */
    private CardEntryType(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with the card entry type.
     * 
     * @return String The value representing the card entry type.
     */
    public String getValue() {
        return value;
    }
}
