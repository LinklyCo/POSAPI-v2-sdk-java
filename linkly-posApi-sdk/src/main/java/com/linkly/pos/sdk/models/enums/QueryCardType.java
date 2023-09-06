package com.linkly.pos.sdk.models.enums;

/**
 * Indicates the type of logon to perform.
 */
public enum QueryCardType {

    /**
     * Read card only
     */
    ReadCard("0"),

    /**
     * Read card + select account
     */
    ReadCardAndSelectAccount("1"),

    /**
     * Select account only
     */
    SelectAccount("5"),

    /**
     * Pre-swipe
     */
    PreSwipe("7"),

    /**
     * Pre-swipe special
     */
    PreSwipeSpecial("8");

    private String value;
    
    /**
     * Private constructor for the QueryCardType enum.
     * 
     * @param value The value associated with the query card type.
     */
    private QueryCardType(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with the query card type.
     * 
     * @return String The value representing the query card type.
     */
    public String getValue() {
        return value;
    }
}
