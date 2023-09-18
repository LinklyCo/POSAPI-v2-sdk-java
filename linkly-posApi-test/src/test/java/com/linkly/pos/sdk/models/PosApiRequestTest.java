package com.linkly.pos.sdk.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;

class PosApiRequestTest {

    @Test
    void should_return_messages_ifParentPosApiRequestEmpty() {
        PosApiRequest request = new PosApiRequest();
        request.setMerchant(null);
        request.setApplication(null);
        request.setReceiptAutoPrint(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("merchant: Must not be empty., application: Must not be empty.,"
            + " receiptAutoPrint: Enum null not found in the list: [POS, PinPad, Both].", exception
                .getMessage());
    }

    @Test
    void should_not_returnMessages_ifNotEmpty() {
        PosApiRequest request = new PosApiRequest();
        request.validate();
    }

    @Test
    void should_deserialize_success() {
        Map<String, String> pad = new HashMap<>();
        pad.put("key", "value");

        UUID uuid = UUID.randomUUID();
        PosApiRequest request = new PosApiRequest();
        request.setCutReceipt(true);
        request.setPosName("pos name");
        request.setPosVersion(uuid.toString());
        request.setPosId(uuid);
        request.setPurchaseAnalysisData(pad);

        String json = MoshiUtil.getAdapter(PosApiRequest.class).toJson(request);
        assertEquals("{\"application\":\"00\",\"cutReceipt\":\"1\",\"merchant\":\"00\","
            + "\"posId\":\"" + uuid.toString() + "\",\"posName\":\"pos name\","
            + "\"posVersion\":\"" + uuid.toString() + "\","
            + "\"purchaseAnalysisData\":{\"key\":\"value\"},\"receiptAutoPrint\":\"0\"}", json);
    }
}
