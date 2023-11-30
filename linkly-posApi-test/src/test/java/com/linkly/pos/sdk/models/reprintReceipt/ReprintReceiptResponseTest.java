package com.linkly.pos.sdk.models.reprintReceipt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;

class ReprintReceiptResponseTest {

    @Test
    void should_deserialize_success() throws IOException {
        String json = "{\"merchant\":\"001\",\"receiptText\":[\"receipt1\",\"receipt2\"]}";
        ReprintReceiptResponse response = MoshiUtil.fromJson(json, ReprintReceiptResponse.class);
        assertEquals("001", response.getMerchant());
        assertEquals("receipt1", response.getReceiptText().get(0));
        assertEquals("receipt2", response.getReceiptText().get(1));

    }
}
