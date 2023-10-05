package com.linkly.pos.sdk.models.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.exception.InvalidArgumentException;

class PairingRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        PairingRequest request = new PairingRequest();
        Integer.parseInt("1");

        InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> {
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

    @Test
    void should_deserialize_success() {
        PairingRequest request = new PairingRequest();
        request.setPairCode("12345");
        request.setUsername("username");
        request.setPassword("password");

        String json = MoshiUtil.getAdapter(PairingRequest.class).toJson(request);
        assertEquals("{\"pairCode\":\"12345\",\"password\":\"password\",\"username\":\"username\"}",
            json);
    }

}
