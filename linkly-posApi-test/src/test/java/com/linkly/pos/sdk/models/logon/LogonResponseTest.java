package com.linkly.pos.sdk.models.logon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.adapters.DateAdapter;
import com.linkly.pos.sdk.common.MoshiUtil;

class LogonResponseTest {

    @Test
    void should_deserialize_success() throws IOException {
        String json = "{\"caid\":\"12\",\"catid\":\"13\",\"date\":\"2023-01-13T01:01:00Z\","
            + "\"pinPadVersion\":\"1.0\",\"stan\":15,\"success\":false}";

        LogonResponse response = MoshiUtil.fromJson(json, LogonResponse.class);
        assertEquals("12", response.getCaId());
        assertEquals("13", response.getCatId());
        assertTrue(new DateAdapter().fromJson("2023-01-13T01:01:00Z").isEqual(response.getDate()));
        assertEquals("1.0", response.getPinPadVersion());
        assertEquals(15, response.getStan());
    }
}
