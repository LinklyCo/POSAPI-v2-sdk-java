package com.linkly.pos.sdk.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.ResponseType;

class PosApiResponseTest {

    @Test
    void should_deserialize_success() throws IOException {
        String json = "{\"PurchaseAnalysisData\":{\"key\":\"value\"},\"ResponseType\":\"logon\"}";
        PosApiResponse response = MoshiUtil.fromJson(json, PosApiResponse.class);

        assertEquals(ResponseType.Logon, response.getResponseType());
        assertEquals("value", response.getPurchaseAnalysisData().get("key"));

    }
}
