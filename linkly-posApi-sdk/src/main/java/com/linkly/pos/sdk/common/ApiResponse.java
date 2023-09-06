package com.linkly.pos.sdk.common;

/**
 * Response object containing the HTTP response code and response body.
 */
public class ApiResponse {

    private boolean success;
    private int statusCode;
    private String body;

    /**
     * Initialise a new response.
     * 
     * @param success Whether the response was successful.
     * @param statusCode HTTP status code of the response.
     * @param body Body of the response.
     */
    public ApiResponse(boolean success, int statusCode, String body) {
        super();
        this.success = success;
        this.statusCode = statusCode;
        this.body = body;
    }

    /**
     * Whether the response was successful.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Set the success of the response.
     * 
     * @param success The boolean value of the success.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Status code of the response.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Set the status code of the response.
     * 
     * @param statusCode The int value of status code.
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Body of the response.
     * 
     * @return String
     */
    public String getBody() {
        return body;
    }

    /**
     * Set the body of the response.
     * 
     * @param body The String value of body.
     */
    public void setBody(String body) {
        this.body = body;
    }
}
