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
        String json = "{\"Caid\":\"12\",\"Catid\":\"13\",\"Date\":\"2023-01-13T01:01:00Z\","
            + "\"PinPadVersion\":\"1.0\",\"Stan\":15,\"Success\":false}";

        LogonResponse response = MoshiUtil.fromJson(json, LogonResponse.class);
        assertEquals(response.getCaId(), "12");
        assertEquals(response.getCatId(), "13");
        assertTrue(new DateAdapter().fromJson("2023-01-13T01:01:00Z").isEqual(response.getDate()));
        assertEquals(response.getPinPadVersion(), "1.0");
        assertEquals(response.getStan(), 15);
    }
}
