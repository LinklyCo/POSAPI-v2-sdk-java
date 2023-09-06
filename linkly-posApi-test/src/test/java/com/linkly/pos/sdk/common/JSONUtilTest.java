package com.linkly.pos.sdk.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class JSONUtilTest {

    @Test
    void should_wrap_json() {
        assertEquals("{ \"request\":{\"value\": \"test value\"}}",
            JSONUtil.wrapRequest("request", "{\"value\": \"test value\"}"));
    }
}