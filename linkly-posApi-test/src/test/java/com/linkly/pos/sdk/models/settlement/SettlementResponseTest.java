package com.linkly.pos.sdk.models.settlement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;

class SettlementResponseTest {

    @Test
    void should_deserialize_success() throws IOException {

        String json = "{\"merchant\":\"001\",\"settlementData\":\"settlement data\","
            + "\"success\":false}";
        SettlementResponse response = MoshiUtil.fromJson(json, SettlementResponse.class);
        assertEquals("001", response.getMerchant());
        assertEquals("settlement data", response.getSettlementData());
    }
}