package com.linkly.pos.sdk.models.configureMerchant;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ConfigureMerchantRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        ConfigureMerchantRequest request = new ConfigureMerchantRequest();
        assertEquals("[catId: Must not be empty., caId: Must not be empty.]",
            request.validate().toString());
    }

    @Test
    void should_not_returnMessages_ifNotEmpty() {
        ConfigureMerchantRequest request = new ConfigureMerchantRequest();
        request.setCaId("123");
        request.setCatId("1234");
        assertEquals(0, request.validate().size());
    }

    @Test
    void should_return_messages_ifParentAndClassEmpty() {
        ConfigureMerchantRequest request = new ConfigureMerchantRequest();
        request.setMerchant(null);
        request.setApplication(null);
        request.setReceiptAutoPrint(null);
        assertEquals("[catId: Must not be empty., caId: Must not be empty., merchant: Must not "
            + "be empty., application: Must not be empty., receiptAutoPrint: Enum null not found in the list: "
            + "[POS, PinPad, Both].]", request.validate().toString());
    }

}
