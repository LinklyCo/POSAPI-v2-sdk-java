package com.linkly.pos.sdk.models;

import com.linkly.pos.sdk.service.impl.PosApiService;

/**
 * {@link PosApiService} options.
 */
public class PosApiServiceOptions {

    private int asyncRequestTimeout = 10;

    /**
     * How long should an async request wait for a response before timing out. There should not be
     * any
     * reason to change this value.
     * 
     * @return int
     */
    public int getAsyncRequestTimeout() {
        return asyncRequestTimeout;
    }
}
