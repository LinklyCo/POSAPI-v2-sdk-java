package com.linkly.pos.sdk.models.logon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.exception.InvalidArgumentException;
import com.linkly.pos.sdk.models.enums.LogonType;

class LogonRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        LogonRequest request = new LogonRequest();
        request.setLogonType(null);
        InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("logonType: Enum null not found in the list: [Standard, RSA, TmsFull, "
            + "TmsParams, TmsSoftware, Logoff, Diagnostics].", exception.getMessage());
    }

    @Test
    void should_not_returnMessages_ifNotEmpty() {
        new LogonRequest().validate();
    }

    @Test
    void should_return_messages_ifParentEmpty() {
        LogonRequest request = new LogonRequest();
        request.setMerchant(null);
        request.setApplication(null);
        request.setReceiptAutoPrint(null);
        InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("merchant: Must not be empty., application: Must not be empty.,"
            + " receiptAutoPrint: Enum null not found in the list: [POS, PinPad, Both].",
            exception.getMessage());
    }

    @Test
    void should_deserialize_success() {
        LogonRequest request = new LogonRequest();
        request.setLogonType(LogonType.TmsParams);

        String json = MoshiUtil.getAdapter(LogonRequest.class).toJson(request);
        assertTrue(json.contains("\"logonType\":\"6\""));
    }
}
