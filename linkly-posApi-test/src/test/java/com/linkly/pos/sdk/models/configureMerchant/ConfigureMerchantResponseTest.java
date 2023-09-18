package com.linkly.pos.sdk.models.configureMerchant;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;

class ConfigureMerchantResponseTest {

    @Test
    void should_deserialize_success() throws IOException {
        String json = "{\"Merchant\":\"merchant1\",\"Success\":false}";
        ConfigureMerchantResponse response = MoshiUtil.fromJson(json,
            ConfigureMerchantResponse.class);
        assertEquals("merchant1", response.getMerchant());
    }
}