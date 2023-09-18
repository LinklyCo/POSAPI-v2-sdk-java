package com.linkly.pos.sdk.models.settlement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.SettlementType;
import com.linkly.pos.sdk.models.reprintReceipt.ReprintReceiptRequest;

class SettlementRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        SettlementRequest request = new SettlementRequest();
        request.setSettlementType(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("settlementType: Enum null not found in the list: [Settlement, PreSettlement, "
            + "LastSettlement, SummaryTotals, SubShiftTotals, DetailedTransactionListing, "
            + "StartCash, StoreAndForwardTotals, DailyCashStatement].", exception.getMessage());
    }

    @Test
    void should_not_returnMessages_ifNotEmpty() {
        new ReprintReceiptRequest().validate();
    }

    @Test
    void should_return_messages_ifParentEmpty() {
        SettlementRequest request = new SettlementRequest();
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
    void should_deserialize_success() {
        SettlementRequest request = new SettlementRequest();
        request.setSettlementType(SettlementType.LastSettlement);
        request.setResetTotals(true);

        String json = MoshiUtil.getAdapter(SettlementRequest.class).toJson(request);
        assertTrue(json.contains("\"settlementType\":\"L\""));
        assertTrue(json.contains("\"resetTotals\":true"));
    }

}
