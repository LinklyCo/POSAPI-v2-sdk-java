package com.linkly.pos.sdk.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;

class PosApiResponseWithResultTest {

    @Test
    void should_deserialize_success() throws IOException {
        String json = "{\"ResponseCode\":\"200\",\"ResponseText\":\"success\",\"Success\":true}";
        PosApiResponseWithResult response = MoshiUtil.fromJson(json,
            PosApiResponseWithResult.class);
        assertEquals("200", response.getResponseCode());
        assertEquals("success", response.getResponseText());
        assertEquals(true, response.isSuccess());
    }

}
