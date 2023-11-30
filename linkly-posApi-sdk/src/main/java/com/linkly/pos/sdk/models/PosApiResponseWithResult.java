package com.linkly.pos.sdk.models;

/**
 * Response type for requests which have a response code.
 */
public class PosApiResponseWithResult extends PosApiResponse {

    protected boolean success;
    protected String responseCode;
    protected String responseText;

    /**
     * Indicates if the request was successful.
     * 
     * @return boolean
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success.
     * 
     * @param success The boolean value of success.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * The response code of the request. Will vary depending on the type of request. In most cases you can
     * ignore this value and use Success in your business logic.
     * 
     * @return String
     */
    public String getResponseCode() {
        return responseCode;
    }

    /**
     * Sets the response code of the request.
     * 
     * @param responseCode The String value of responseCode.
     */
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * The response text for the response code. Will vary depending on the type of request. For some
     * responses it may be appropriate to display this directly to the POS user.
     * 
     * @return String
     */
    public String getResponseText() {
        return responseText;
    }
    /**
     * Sets the responseText.
     * 
     * @param responseText The String value of responseText.
     */
    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }
}
