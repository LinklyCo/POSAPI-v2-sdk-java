package com.linkly.pos.sdk.models.reprintReceipt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ReprintReceiptRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        ReprintReceiptRequest request = new ReprintReceiptRequest();
        request.setReprintType(null);
        assertEquals("[reprintType: Enum null not found in the list: [GetLast, Reprint].]", request
            .validate()
            .toString());
    }

    @Test
    void should_not_returnMessages_ifNotEmpty() {
        ReprintReceiptRequest request = new ReprintReceiptRequest();
        assertEquals(0, request.validate().size());
    }

    @Test
    void should_return_messages_ifParentEmpty() {
        ReprintReceiptRequest request = new ReprintReceiptRequest();
        request.setMerchant(null);
        request.setApplication(null);
        request.setReceiptAutoPrint(null);
        assertEquals("[merchant: Must not be empty., application: Must not be empty.,"
            + " receiptAutoPrint: Enum null not found in the list: [POS, PinPad, Both].]", request
                .validate().toString());
    }

}
