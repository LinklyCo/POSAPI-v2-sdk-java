package com.linkly.pos.sdk.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PosApiRequestTest {

    @Test
    void should_return_messages_ifParentPosApiRequestEmpty() {
        PosApiRequest request = new PosApiRequest();
        request.setMerchant(null);
        request.setApplication(null);
        request.setReceiptAutoPrint(null);
        assertEquals("[merchant: Must not be empty., application: Must not be empty.,"
            + " receiptAutoPrint: Enum null not found in the list: [POS, PinPad, Both].]", request
                .validate()
                .toString());
    }

    @Test
    void should_not_returnMessages_ifNotEmpty() {
        PosApiRequest request = new PosApiRequest();
        assertEquals(0, request.validate().size());
    }
}
