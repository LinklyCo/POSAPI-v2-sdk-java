package com.linkly.pos.sdk.models.configureMerchant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.exception.InvalidArgumentException;

class ConfigureMerchantRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        ConfigureMerchantRequest request = new ConfigureMerchantRequest();
        InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("catId: Must not be empty., caId: Must not be empty.", exception.getMessage());
    }

    @Test
    void should_not_returnMessages_ifNotEmpty() {
        ConfigureMerchantRequest request = new ConfigureMerchantRequest();
        request.setCaId("123");
        request.setCatId("1234");
        request.validate();
    }

    @Test
    void should_return_messages_ifParentAndClassEmpty() {
        ConfigureMerchantRequest request = new ConfigureMerchantRequest();
        request.setMerchant(null);
        request.setApplication(null);
        request.setReceiptAutoPrint(null);

        InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("merchant: Must not be empty., application: Must not be empty., "
            + "receiptAutoPrint: Enum null not found in the list: [POS, PinPad, Both].",
            exception.getMessage());
    }

    @Test
    void should_deserialize_success() {
        ConfigureMerchantRequest request = new ConfigureMerchantRequest();
        request.setCaId("123");
        request.setCatId("1234");

        String json = MoshiUtil.getAdapter(ConfigureMerchantRequest.class).toJson(request);
        assertTrue(json.contains("\"caId\":\"123\""));
        assertTrue(json.contains("\"catId\":\"1234\""));
    }
}