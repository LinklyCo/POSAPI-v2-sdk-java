package com.linkly.pos.sdk.service;

import org.asynchttpclient.Response;

/**
 * Interface for asynchronously execution of HTTP request.
 */
public interface IHttpAsyncExecutor {

	/**
	 * Execute a GET HTTP request
	 * 
	 * @param uri The String value of uri.
	 * @param auth The String value of auth.
	 * @return Response
	 */
    Response get(String uri, String auth);

    /**
     * Execute a POST HTTP request.
     * 
     * @param uri The String value of uri.
	 * @param auth The String value of auth.
     * @param body The String value of body.
     * @return Response
     */
    Response post(String uri, String auth, String body);

    /**
     * Execute a POST HTTP request.
     * 
     * @param uri The String value of uri.
	 * @param auth The String value of auth.
     * @return Response
     */
    Response post(String uri, String body);
}
