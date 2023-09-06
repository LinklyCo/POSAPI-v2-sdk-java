package com.linkly.pos.sdk.adapters;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonQualifier;
import com.squareup.moshi.ToJson;

@JsonQualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
public @interface BoolToBitString {
	
	/**
	 * Safely converts boolean values to json bit string representation and vice versa.
	 */
    public static class BoolToIntAdapter {
        
    	/**
         * Safely converts java booleans to json bit string representation.
         * 
         * @param value boolean value to be converted.
         */
    	@ToJson
        public String toJson(@BoolToBitString boolean value) {
            return value ? "1" : "0";
        }

        /**
         * Safely converts json bit string representation to java booleans.
         * 
         * @param value string representation value to be converted.
         */
        @FromJson
        @BoolToBitString
        public boolean toJson(String value) {
            if (value == null) {
                return false;
            }
            return value.equals("1") ? true : false;
        }
    }
}
