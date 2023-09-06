package com.linkly.pos.sdk.service.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@SuppressWarnings("unchecked")
class HttpAsyncExecutorTest {

    private static final String CONTENT_TYPE_KEY = "Content-Type";
    private static final String CONTENT_TYPE_VALUE = "application/json";
    private static final String AUTH_HEADER_KEY = "Authorization";

    private AsyncHttpClient httpClient = mock(AsyncHttpClient.class);
    private BoundRequestBuilder requestBuilder = mock(BoundRequestBuilder.class);

    private ListenableFuture<Response> listenableFuture = mock(ListenableFuture.class);
    private CompletableFuture<Response> completableFuture = mock(CompletableFuture.class);
    private Response response = mock(Response.class);

    @BeforeEach
    public void beforeEach() {
        reset(httpClient, requestBuilder, listenableFuture, completableFuture, response);
    }

    @Test
    void test_get_noAuth() {
        String endpoint = "test.com/get";
        HttpAsyncExecutor executor = new HttpAsyncExecutor(httpClient);

        when(httpClient.prepareGet(endpoint)).thenReturn(requestBuilder);
        when(requestBuilder.execute()).thenReturn(listenableFuture);
        when(listenableFuture.toCompletableFuture()).thenReturn(completableFuture);
        when(completableFuture.join()).thenReturn(response);

        executor.get(endpoint, null);

        verify(httpClient, times(1)).prepareGet(endpoint);
        verify(requestBuilder, times(1)).addHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
    }

    @Test
    void test_get_withAuth() {
        String endpoint = "test.com/get";
        HttpAsyncExecutor executor = new HttpAsyncExecutor(httpClient);

        when(httpClient.prepareGet(endpoint)).thenReturn(requestBuilder);
        when(requestBuilder.execute()).thenReturn(listenableFuture);
        when(listenableFuture.toCompletableFuture()).thenReturn(completableFuture);
        when(completableFuture.join()).thenReturn(response);

        executor.get(endpoint, "auth-key");

        verify(httpClient, times(1)).prepareGet(endpoint);
        verify(requestBuilder, times(1)).addHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
        verify(requestBuilder, times(1)).addHeader(AUTH_HEADER_KEY, "auth-key");
    }

    @Test
    void test_post_withAuth() {

        String endpoint = "test.com/get";
        HttpAsyncExecutor executor = new HttpAsyncExecutor(httpClient);

        when(httpClient.preparePost(endpoint)).thenReturn(requestBuilder);
        when(requestBuilder.setBody(Mockito.anyString())).thenReturn(requestBuilder);
        when(requestBuilder.execute()).thenReturn(listenableFuture);
        when(listenableFuture.toCompletableFuture()).thenReturn(completableFuture);
        when(completableFuture.join()).thenReturn(response);

        executor.post(endpoint, "auth-key", "{\"test\": 1}");

        verify(httpClient, times(1)).preparePost(endpoint);
        verify(requestBuilder, times(1)).setBody("{\"test\": 1}");
        verify(requestBuilder, times(1)).addHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
        verify(requestBuilder, times(1)).addHeader(AUTH_HEADER_KEY, "auth-key");
    }
}
