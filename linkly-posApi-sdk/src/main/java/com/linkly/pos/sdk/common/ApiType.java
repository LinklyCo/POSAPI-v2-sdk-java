package com.linkly.pos.sdk.common;

/**
 * Used to indicate if a request should be directed towards the POS or auth API.
 */
public enum ApiType {
	
	/**
     * Represents an API used for authentication purposes.
     */
    AUTH,
    
    /**
     * Represents an API used for point-of-sale operations.
     */
    POS
}
