package com.linkly.pos.sdk.models.queryCard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.exception.InvalidArgumentException;
import com.linkly.pos.sdk.models.enums.QueryCardType;

class QueryCardRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        QueryCardRequest request = new QueryCardRequest();
        request.setQueryCardType(null);
        InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> {
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
        InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("merchant: Must not be empty., application: Must not be empty.,"
            + " receiptAutoPrint: Enum null not found in the list: [POS, PinPad, Both].", exception
                .getMessage());
    }

    @Test
    void should_deserialize_success() {
        QueryCardRequest request = new QueryCardRequest();
        request.setQueryCardType(QueryCardType.ReadCardAndSelectAccount);

        String json = MoshiUtil.getAdapter(QueryCardRequest.class).toJson(request);
        assertTrue(json.contains("\"queryCardType\":\"1\""));
    }

}
