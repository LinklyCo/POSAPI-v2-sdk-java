package com.linkly.pos.sdk.models.result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;

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

}
