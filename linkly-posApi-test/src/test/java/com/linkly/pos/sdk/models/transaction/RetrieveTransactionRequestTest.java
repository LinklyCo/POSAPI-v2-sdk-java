package com.linkly.pos.sdk.models.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.exception.InvalidArgumentException;
import com.linkly.pos.sdk.models.enums.ReferenceType;

class RetrieveTransactionRequestTest {

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @Test
    void shuld_return_message_ifEmpty() {
        RetrieveTransactionRequest request = new RetrieveTransactionRequest();
        request.setReferenceType(null);
        InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("referenceType: Enum null not found in the list: [ReferenceNo, RRN]., "
            + "reference: Must not be empty.", exception.getMessage());
    }

    @Test
    void shuld_not_return_message_ifNotEmpty() {
        RetrieveTransactionRequest request = new RetrieveTransactionRequest();
        request.setReference("13234");
        request.validate();
    }

    @Test
    void should_deserialize_success() {
        RetrieveTransactionRequest request = new RetrieveTransactionRequest();
        request.setReferenceType(ReferenceType.RRN);
        request.setReference("123456");

        String json = MoshiUtil.getAdapter(RetrieveTransactionRequest.class).toJson(request);
        assertTrue(json.contains("\"reference\":\"123456\""));
        assertTrue(json.contains("\"referenceType\":\"RRN\""));
    }
}
