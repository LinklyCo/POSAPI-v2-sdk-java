package com.linkly.pos.sdk.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ApiServiceEnpointTest {

    @Test
    void should_return_apiServiceEndpoints() {
        ApiServiceEndpoint endpoint = new ApiServiceEndpoint("http://host.auth.com",
            "http://host.pos.com");
        assertEquals("http://host.auth.com", endpoint.getAuthApiHost());
        assertEquals("http://host.pos.com", endpoint.getPosApiHost());
    }

    @Test
    void should_return_exception_for_invalidAuthApiEndpoint() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new ApiServiceEndpoint("http://host.auth.com/",
                "http://host.pos.com");
        });
        assertEquals("authApiHost must be absolute and not contain path", exception.getMessage());
    }

    @Test
    void should_return_exception_for_invalidPosApiEndpoint() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new ApiServiceEndpoint("http://host.auth.com",
                "http://host.pos.com/");
        });
        assertEquals("posApiHost must be absolute and not contain path", exception.getMessage());
    }
}
