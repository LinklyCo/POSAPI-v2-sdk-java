package com.linkly.pos.sdk.adapters;

import java.util.UUID;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

/**
 * Converts {@link UUID} to {@link String} representation and vice versa.
 */
public class UUIDAdapter {

    /**
     * Converts {@link UUID} to {@link String} representation.
     * 
     * @param value
     *            java UUID value to be converted.
     * @return String value of UUID
     */
    @ToJson
    public String toJson(UUID value) {
        return value.toString();
    }

    /**
     * Converts json UUID {@link String} representation to {@link UUID}.
     * 
     * @param value
     *            string representation value to be converted.
     * @return {@link UUID} converted from String
     */
    @FromJson
    public UUID fromJson(String value) {
        return UUID.fromString(value);
    }
}
