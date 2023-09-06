package com.linkly.pos.sdk.models.queryCard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class QueryCardRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        QueryCardRequest request = new QueryCardRequest();
        request.setQueryCardType(null);
        assertEquals("[queryCardType: Enum null not found in the list: [ReadCard, "
            + "ReadCardAndSelectAccount, SelectAccount, PreSwipe, PreSwipeSpecial].]", request
                .validate().toString());
    }

    @Test
    void should_not_returnMessages_ifNotEmpty() {
        QueryCardRequest request = new QueryCardRequest();
        assertEquals(0, request.validate().size());
    }

    @Test
    void should_return_messages_ifParentEmpty() {
        QueryCardRequest request = new QueryCardRequest();
        request.setMerchant(null);
        request.setApplication(null);
        request.setReceiptAutoPrint(null);
        assertEquals("[merchant: Must not be empty., application: Must not be empty.,"
            + " receiptAutoPrint: Enum null not found in the list: [POS, PinPad, Both].]", request
                .validate().toString());
    }

}
