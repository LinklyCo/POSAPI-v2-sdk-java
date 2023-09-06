package com.linkly.pos.sdk.models.status;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StatusRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        StatusRequest request = new StatusRequest();
        request.setStatusType(null);
        assertEquals(
            "[statusType: Enum null not found in the list: [Standard, TerminalAppInfo, AppCpat, "
                + "AppNameTable, Undefined, Preswipe].]", request.validate().toString());
    }

    @Test
    void should_not_returnMessages_ifNotEmpty() {
        StatusRequest request = new StatusRequest();
        assertEquals(0, request.validate().size());
    }

    @Test
    void should_return_messages_ifParentEmpty() {
        StatusRequest request = new StatusRequest();
        request.setMerchant(null);
        request.setApplication(null);
        request.setReceiptAutoPrint(null);
        assertEquals("[merchant: Must not be empty., application: Must not be empty.,"
            + " receiptAutoPrint: Enum null not found in the list: [POS, PinPad, Both].]", request
                .validate()
                .toString());
    }
}
