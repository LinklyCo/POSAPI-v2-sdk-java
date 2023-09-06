package com.linkly.pos.sdk.adapters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

/**
 * Converts date time values to json string representation and vice versa.
 */
public class DateAdapter {
	
	private static final DateTimeFormatter DTF_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
	private static final DateTimeFormatter DTF_PARSE = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");
	
	/**
	    * Converts date time to json string representation.
	    * 
	    * @param value date time value to be converted.
	    */
    @ToJson
    public String toJson(LocalDateTime value) {
        return DTF_FORMAT.format(value);
    }

    /**
     * Converts json date time string representation to java date time.
     * 
     * @param value string representation value to be converted.
     */
    @FromJson
    public LocalDateTime fromJson(String value) {
        return LocalDateTime.parse(value, DTF_PARSE);
    }
}
