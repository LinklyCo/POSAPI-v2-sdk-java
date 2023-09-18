package com.linkly.pos.sdk.models.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;

class PairingResponseTest {

    @Test
    void should_serialize_success() throws IOException {
        String json = "{\"secret\":\"12345678910111\"}";
        PairingResponse response = MoshiUtil.fromJson(json, PairingResponse.class);
        assertEquals("12345678910111", response.getSecret());
    }
}