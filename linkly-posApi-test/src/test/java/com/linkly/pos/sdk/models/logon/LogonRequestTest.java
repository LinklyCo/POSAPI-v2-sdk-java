package com.linkly.pos.sdk.models.logon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LogonRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        LogonRequest request = new LogonRequest();
        request.setLogonType(null);
        assertEquals("[logonType: Enum null not found in the list: [Standard, RSA, TmsFull, "
            + "TmsParams, TmsSoftware, Logoff, Diagnostics].]", request.validate().toString());
    }

    @Test
    void should_not_returnMessages_ifNotEmpty() {
        LogonRequest request = new LogonRequest();
        assertEquals(0, request.validate().size());
    }

    @Test
    void should_return_messages_ifParentEmpty() {
        LogonRequest request = new LogonRequest();
        request.setMerchant(null);
        request.setApplication(null);
        request.setReceiptAutoPrint(null);
        assertEquals("[merchant: Must not be empty., application: Must not be empty.,"
            + " receiptAutoPrint: Enum null not found in the list: [POS, PinPad, Both].]", request
                .validate().toString());
    }

}
