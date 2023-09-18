package com.linkly.pos.sdk.models.result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;

class ResultRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        ResultRequest request = new ResultRequest(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("sessionId: Must not be empty.", exception.getMessage());
    }

    @Test
    void should_not_returnMessages_ifNotEmpty() {
        new ResultRequest(UUID.randomUUID()).validate();
    }

    @Test
    void should_deserialize_success() {
        UUID uuid = UUID.randomUUID();
        ResultRequest request = new ResultRequest(uuid);

        String json = MoshiUtil.getAdapter(ResultRequest.class).toJson(request);
        assertTrue(json.contains("\"sessionId\":\"" + uuid.toString() + "\""));
    }
}