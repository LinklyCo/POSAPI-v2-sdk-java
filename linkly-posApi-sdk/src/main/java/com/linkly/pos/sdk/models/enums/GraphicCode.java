package com.linkly.pos.sdk.models.enums;

/**
 * Defines the graphic code returned in the response results determining the type of graphics to display.
 */
public enum GraphicCode {
   
	/**
     * Graphic code indicating processing.
     */
    Processing("0"),

	/**
     * Graphic code indicating verifying.
     */
    Verify("1"),

	/**
     * Graphic code indicating a question is presented.
     */
    Question("2"),

	/**
     * Graphic code indicating card processing.
     */
    Card("3"),

	/**
     * Graphic code indicating account processing.
     */
    Account("4"),

	/**
     * Graphic code indicating PIN input.
     */
    PIN("5"),
    
	/**
     * Graphic code indicating finished.
     */
    Finished("6"),

	/**
     * Graphic code indicating no process.
     */
    None(" "),

    /**
     * This value is returned when an unexpected value was deserialised. Rather than throwing a
     * deserialisation exception it us up to the end-user how they want to handle the error.
     */
    Unknown("Unknown");

    private String value;

    /**
     * Private constructor for the GraphicCode enum.
     * 
     * @param value The value associated with the graphic code.
     */
    private GraphicCode(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with the graphic code.
     * 
     * @return String The value representing the graphic code.
     */
    public String getValue() {
        return value;
    }
}
