package com.linkly.pos.sdk.models.result;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class ResultRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        ResultRequest request = new ResultRequest(null);
        assertEquals("[sessionId: Must not be empty.]", request.validate()
            .toString());
    }

    @Test
    void should_not_returnMessages_ifNotEmpty() {
        ResultRequest request = new ResultRequest(UUID.randomUUID());
        assertEquals(0, request.validate().size());
    }

}
