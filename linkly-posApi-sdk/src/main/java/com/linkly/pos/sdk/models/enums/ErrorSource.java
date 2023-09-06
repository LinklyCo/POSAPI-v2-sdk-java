package com.linkly.pos.sdk.models.enums;

/**
 * Source causing the {@link IPosApiEventListener#Error}  handler to be invoked.
 */
public enum ErrorSource {

    /**
     * Used to indicate {@link PosApiService} was able to receive a response from the API however
     * the HTTP response returned an error code. This could be caused by authentication failure, an
     * invalid request URI, erroneous data within the request, etc.
     */
    API,
    /**
     * This code indicates an exception was caught by {@link PosApiService}. This exception should
     * be less common than API and would most likely be caused by connectivity issues with
     * the API.
     */
    Internal
}
