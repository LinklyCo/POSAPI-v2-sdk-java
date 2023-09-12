package com.linkly.pos.sdk.models.configureMerchant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ConfigureMerchantRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        ConfigureMerchantRequest request = new ConfigureMerchantRequest();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
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

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("merchant: Must not be empty., application: Must not be empty., "
            + "receiptAutoPrint: Enum null not found in the list: [POS, PinPad, Both].",
            exception.getMessage());
    }

}
