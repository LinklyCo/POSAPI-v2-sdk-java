package com.linkly.pos.sdk.adapters;

import java.util.UUID;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

/**
 * Converts Unique Identifier to json string representation and vice versa.
 */
public class UUIDAdapter {
	
    /**
    * Converts java UUID to json string representation.
    * 
    * @param value java UUID value to be converted.
    */
    @ToJson
    public String toJson(UUID value) {
        return value.toString();
    }

    /**
    * Converts json UUID string representation to java UUID.
    * 
    * @param value string representation value to be converted.
    */
    @FromJson
    public UUID fromJson(String value) {
        return UUID.fromString(value);
    }
}
