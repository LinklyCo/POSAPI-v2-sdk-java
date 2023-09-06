package com.linkly.pos.sdk.models.enums;

/**
 * Defines the input type requested by the pinpad.
 */
public enum InputType {

	/**
     * Input type indicating no specific request.
     */
    None("0"),

	/**
     * Input type indicating normal input request.
     */
    Normal("1"),

	/**
     * Input type indicating amount input request.
     */
    Amount("2"),

	/**
     * Input type indicating decimal input request.
     */
    Decimal("3"),

	/**
     * Input type indicating password entry request.
     */
    Password("4"),

    /**
     * This value is returned when an unexpected value was deserialised. Rather than throwing a
     * deserialisation exception it us up to the end-user how they want to handle the error.
     */
    Unknown("Unknown");

    private String value;

    /**
     * Private constructor for the InputType enum.
     * 
     * @param value The value associated with the input type.
     */
    private InputType(String value) {
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
