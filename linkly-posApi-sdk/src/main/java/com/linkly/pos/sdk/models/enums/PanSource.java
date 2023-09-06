package com.linkly.pos.sdk.models.enums;

/**
 * Indicates the source of the customer card details.
 */
public enum PanSource {

    /**
     * PIN pad will read card using internal card reader.
     */
    PinPad(" "),

    /**
     * Manual Entry of card details using DateExpiry and Pan properties.
     */
    PosKeyed("K"),

    /**
     * Track2 property contains application supplied card details read directly form a magnetic
     * stripe reader (MSR).
     */
    PosSwiped("S"),

    /**
     * Internet originated transaction.
     */
    Internet("0"),

    /**
     * Telephone originated transaction.
     */
    TeleOrder("1"),

    /**
     * Mail order originated transaction.
     */
    Moto("2"),

    /**
     * Cardholder present transaction.
     */
    CustomerPresent("3"),

    /**
     * Recurring transaction.
     */
    RecurringTransaction("4"),

    /**
     * Installment.
     */
    Installment("5");

    private String value;
    
    /**
     * Private constructor for the PanSource enum.
     * 
     * @param value The value associated with the pan source.
     */
    private PanSource(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with the pan source.
     * 
     * @return String The value representing the pan source.
     */
    public String getValue() {
        return value;
    }
}
