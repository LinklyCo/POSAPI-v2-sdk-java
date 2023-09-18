package com.linkly.pos.sdk.models.reprintReceipt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.ReprintType;

class ReprintReceiptRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        ReprintReceiptRequest request = new ReprintReceiptRequest();
        request.setReprintType(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("reprintType: Enum null not found in the list: [GetLast, Reprint].", exception
            .getMessage());
    }

    @Test
    void should_not_returnMessages_ifNotEmpty() {
        new ReprintReceiptRequest().validate();
    }

    @Test
    void should_return_messages_ifParentEmpty() {
        ReprintReceiptRequest request = new ReprintReceiptRequest();
        request.setMerchant(null);
        request.setApplication(null);
        request.setReceiptAutoPrint(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("merchant: Must not be empty., application: Must not be empty., "
            + "receiptAutoPrint: Enum null not found in the list: [POS, PinPad, Both].",
            exception.getMessage());
    }

    @Test
    void should_deserialize_success() {
        ReprintReceiptRequest request = new ReprintReceiptRequest();
        request.setReprintType(ReprintType.Reprint);

        String json = MoshiUtil.getAdapter(ReprintReceiptRequest.class).toJson(request);
        assertTrue(json.contains("\"reprintType\":\"1\""));
    }

}
