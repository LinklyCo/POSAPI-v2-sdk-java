package com.linkly.pos.sdk.models.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PairingRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        PairingRequest request = new PairingRequest();
        Integer.parseInt("1");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("username: Must not be empty., pairCode: Must not be empty.,"
            + " username: Must not be empty.", exception.getMessage());
    }

    @Test
    void should_not_returnMessages_ifNotEmpty() {
        PairingRequest request = new PairingRequest();
        request.setPairCode("12345");
        request.setUsername("username");
        request.setPassword("password");
        request.validate();
    }

}
