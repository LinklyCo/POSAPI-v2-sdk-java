package com.linkly.pos.sdk.adapters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

/**
 * Converts date time values to json string representation and vice versa.
 */
public class DateAdapter {

    private static final DateTimeFormatter DTF_FORMAT = DateTimeFormatter.ofPattern(
        "MM/dd/yyyy hh:mm:ss a");
    private static final DateTimeFormatter DTF_PARSE = DateTimeFormatter.ofPattern(
        "yyyy-MM-dd'T'HH:mm:ssX");

    /**
     * Converts date time to json {@link String} representation.
     * 
     * @param value
     *            {@link LocalDateTime} value to be converted.
     * @return {@link String} {@link LocalDateTime} to {@link String}
     */
    @ToJson
    public String toJson(LocalDateTime value) {
        return DTF_FORMAT.format(value);
    }

    /**
     * Converts json {@link String} representation to {@link LocalDateTime}.
     * 
     * @param value
     *            {@link String} representation value to be converted.
     * @return {@link LocalDateTime} {@link String} to LocalDateTime
     */
    @FromJson
    public LocalDateTime fromJson(String value) {
    	try {
    		return LocalDateTime.parse(value, DTF_PARSE);
    	} catch (Exception e) {
    		return LocalDateTime.now();
    	}
    }
}
