package com.linkly.pos.sdk.models.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PairingRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        PairingRequest request = new PairingRequest();
        assertEquals("[username: Must not be empty., pairCode: Must not be empty.,"
            + " username: Must not be empty.]",
            request.validate().toString());
    }

    @Test
    void should_not_returnMessages_ifNotEmpty() {
        PairingRequest request = new PairingRequest();
        request.setPairCode("12345");
        request.setUsername("username");
        request.setPassword("password");
        assertEquals(0, request.validate().size());
    }

}
