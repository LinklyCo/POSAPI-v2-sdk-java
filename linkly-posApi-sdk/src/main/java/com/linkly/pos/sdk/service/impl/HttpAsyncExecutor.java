package com.linkly.pos.sdk.service.impl;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.Response;

import com.linkly.pos.sdk.common.StringUtil;
import com.linkly.pos.sdk.service.IHttpAsyncExecutor;

import io.netty.handler.codec.http.HttpMethod;

/**
 * This service is for executing HTTP request asynchronously.
 */
public class HttpAsyncExecutor implements IHttpAsyncExecutor {

    private static final String CONTENT_TYPE_KEY = "Content-Type";
    private static final String CONTENT_TYPE_VALUE = "application/json";
    private static final String AUTH_HEADER_KEY = "Authorization";
    private AsyncHttpClient httpClient;

    /**
     * Initialise a new HttpAsyncExecutor.
     * 
     * @param httpClient The AsyncHttpClient value.
     */
    public HttpAsyncExecutor(AsyncHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response get(String uri, String auth) {
        return asyncRequest(uri, HttpMethod.GET, auth, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response post(String uri, String auth, String body) {
        return asyncRequest(uri, HttpMethod.POST, auth, body);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response post(String uri, String body) {
        return asyncRequest(uri, HttpMethod.POST, null, body);
    }

    /**
     * Initialise and execute the asynchronous request.
     * 
     * @param uri The String value of uri.
     * @param method The HttpMethod value.
     * @param auth The String value of auth.
     * @param body The String value of body.
     * @return Response
     */
    public Response asyncRequest(String uri, HttpMethod method, String auth, String body) {
        BoundRequestBuilder requestBuilder = null;
        if (HttpMethod.GET == method) {
            requestBuilder = httpClient.prepareGet(uri);
        }
        else {
            requestBuilder = httpClient.preparePost(uri)
                .setBody(body);
        }
        requestBuilder.addHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
        if (!StringUtil.isNullOrWhiteSpace(auth)) {
            requestBuilder.addHeader(AUTH_HEADER_KEY, auth);
        }
        return requestBuilder.execute()
            .toCompletableFuture()
            .join();
    }
}
