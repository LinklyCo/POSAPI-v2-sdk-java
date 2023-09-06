package com.linkly.pos.sdk.models.sendKey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class SendKeyRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        SendKeyRequest request = new SendKeyRequest();
        request.setData("exceed limit test data. exceed limit test data. exceed limit test data."
            + "exceed limit test data. exceed limit test data. exceed limit test data. "
            + "exceed limit test data. exceed limit test data. exceed limit test data.");
        assertEquals("[sessionId: Must not be empty., key: Must not be empty., data: Max length"
            + " must not exceed 60.]", request.validate().toString());
    }

    @Test
    void should_not_returnMessages_ifNotEmpty() {
        SendKeyRequest request = new SendKeyRequest();
        request.setSessionId(UUID.randomUUID());
        request.setKey("1");
        request.setData("test data");
        assertEquals(0, request.validate().size());
    }

    @Test
    void should_return_messages_ifParentEmpty() {
        SendKeyRequest request = new SendKeyRequest();
        request.setSessionId(UUID.randomUUID());
        request.setKey("1");
        request.setData("test data");
        request.setMerchant(null);
        request.setApplication(null);
        request.setReceiptAutoPrint(null);
        assertEquals("[merchant: Must not be empty., application: Must not be empty.,"
            + " receiptAutoPrint: Enum null not found in the list: [POS, PinPad, Both].]", request
                .validate()
                .toString());
    }

}
