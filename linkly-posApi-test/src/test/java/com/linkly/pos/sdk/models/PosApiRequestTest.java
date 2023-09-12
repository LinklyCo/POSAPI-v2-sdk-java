package com.linkly.pos.sdk.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PosApiRequestTest {

    @Test
    void should_return_messages_ifParentPosApiRequestEmpty() {
        PosApiRequest request = new PosApiRequest();
        request.setMerchant(null);
        request.setApplication(null);
        request.setReceiptAutoPrint(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("merchant: Must not be empty., application: Must not be empty.,"
            + " receiptAutoPrint: Enum null not found in the list: [POS, PinPad, Both].", exception
                .getMessage());
    }

    @Test
    void should_not_returnMessages_ifNotEmpty() {
        PosApiRequest request = new PosApiRequest();
        request.validate();
    }
}
