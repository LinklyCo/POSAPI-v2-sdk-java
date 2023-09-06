package com.linkly.pos.sdk.common;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class HttpStatusCodeUtilTest {

    @Test
    void should_return_success_for_httpStatusCode() {
        assertTrue(HttpStatusCodeUtil.isSuccess(200));
        assertTrue(HttpStatusCodeUtil.isSuccess(201));
        assertTrue(HttpStatusCodeUtil.isSuccess(204));
    }

    @Test
    void should_return_tooEarly_for_httpStatusCode() {
        assertTrue(HttpStatusCodeUtil.tooEarly(425));
    }

}
