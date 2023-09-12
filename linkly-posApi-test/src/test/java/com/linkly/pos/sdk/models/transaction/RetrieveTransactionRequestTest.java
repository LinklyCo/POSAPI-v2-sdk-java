package com.linkly.pos.sdk.models.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class RetrieveTransactionRequestTest {

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @Test
    void shuld_return_message_ifEmpty() {
        RetrieveTransactionRequest request = new RetrieveTransactionRequest();
        request.setReferenceType(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
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

}
