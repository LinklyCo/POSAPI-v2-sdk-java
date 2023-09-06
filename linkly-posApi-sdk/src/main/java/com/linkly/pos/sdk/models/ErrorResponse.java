package com.linkly.pos.sdk.models;

import com.linkly.pos.sdk.models.enums.ErrorSource;
import com.squareup.moshi.Json;

/**
 * Error response handler
 */
public class ErrorResponse {

    public ErrorResponse(ErrorSource source, Integer httpStatusCode, String message,
        Exception exception) {
        super();
        this.source = source;
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.exception = exception;
    }
    
    public ErrorResponse() { }

    private ErrorSource source;
    private Integer httpStatusCode;
    private String message;

    @Json(ignore = true)
    private Exception exception;

    /**
     * Source of the error.
     * 
     * @return ErrorSource
     */
    
    public ErrorSource getSource() {
        return source;
    }

    /**
     * Sets the source of the error.
     * 
     * @param source The ErrorSource value of the response.
     */
    public void setSource(ErrorSource source) {
        this.source = source;
    }

    /**
     * HTTP error code of API response. Will only be present if {@link ErrorSource#API}
     * 
     * @return Integer
     */
    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    /**
     * Sets the HTTP error code of API response.
     * 
     * @param httpStatusCode The Integer value of HTTP error code.
     */
    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    /**
     * Error message.
     * 
     * @return String
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the error message.
     * 
     * @param message The String value of error message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Exception raised in SDK. Will only be present if {@link ErrorSource#Internal}
     * 
     * @return Exception
     */
    public Exception getException() {
        return exception;
    }

    /**
     * Sets the exception.
     * 
     * @param exception The Exception value.
     */
    public void setException(Exception exception) {
        this.exception = exception;
    }
}
