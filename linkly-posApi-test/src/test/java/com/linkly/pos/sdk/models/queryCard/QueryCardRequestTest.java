package com.linkly.pos.sdk.models.queryCard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class QueryCardRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        QueryCardRequest request = new QueryCardRequest();
        request.setQueryCardType(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("queryCardType: Enum null not found in the list: [ReadCard, "
            + "ReadCardAndSelectAccount, SelectAccount, PreSwipe, PreSwipeSpecial].", exception
                .getMessage());
    }

    @Test
    void should_not_returnMessages_ifNotEmpty() {
        new QueryCardRequest().validate();
    }

    @Test
    void should_return_messages_ifParentEmpty() {
        QueryCardRequest request = new QueryCardRequest();
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

}
