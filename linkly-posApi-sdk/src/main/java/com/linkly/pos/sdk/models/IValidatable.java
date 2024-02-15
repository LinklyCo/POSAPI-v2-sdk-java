package com.linkly.pos.sdk.models;

/**
 * Requires an implementer to provide a method to validate itself
 */
public interface IValidatable {

    /**
     * Requests are validated before it will be sent to Linkly REST endpoints
     * 
     */
    void validate();
}