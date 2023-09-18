package com.linkly.pos.sdk.common;

/**
 * Utility class used to determine the nature of HTTP responses based on the returned status code.
 */
public final class HttpStatusCodeUtil {

    /**
     * Checks whether the status code indicates a successful response.
     * Success status code: greater than or equal to 200 OR less than or equal to 299
     * 
     * @param statusCode
     *            The HTTP status code to check.
     * @return boolean true if success else false
     */
    public static boolean isSuccess(int statusCode) {
        return statusCode >= 200 && statusCode <= 299;
    }

    /**
     * Checks whether the status code indicates an early hint response.
     * Too early status code: 425
     * 
     * @param statusCode
     *            The HTTP status code to check.
     * @return true if too early else false
     */
    public static boolean tooEarly(int statusCode) {
        return statusCode == 425;
    }
}
