package com.linkly.pos.sdk.models.settlement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.models.reprintReceipt.ReprintReceiptRequest;

class SettlementRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        SettlementRequest request = new SettlementRequest();
        request.setSettlementType(null);
        assertEquals(
            "[settlementType: Enum null not found in the list: [Settlement, PreSettlement, "
                + "LastSettlement, SummaryTotals, SubShiftTotals, DetailedTransactionListing, "
                + "StartCash, StoreAndForwardTotals, DailyCashStatement].]", request.validate()
                    .toString());
    }

    @Test
    void should_not_returnMessages_ifNotEmpty() {
        ReprintReceiptRequest request = new ReprintReceiptRequest();
        assertEquals(0, request.validate().size());
    }

    @Test
    void should_return_messages_ifParentEmpty() {
        SettlementRequest request = new SettlementRequest();
        request.setMerchant(null);
        request.setApplication(null);
        request.setReceiptAutoPrint(null);
        assertEquals("[merchant: Must not be empty., application: Must not be empty.,"
            + " receiptAutoPrint: Enum null not found in the list: [POS, PinPad, Both].]", request
                .validate()
                .toString());
    }

}
