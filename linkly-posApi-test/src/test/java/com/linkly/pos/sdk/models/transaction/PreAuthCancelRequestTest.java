package com.linkly.pos.sdk.models.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.Constants;
import com.linkly.pos.sdk.models.enums.TxnType;

class PreAuthCancelRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        PreAuthCancelRequest request = new PreAuthCancelRequest(null);
        request.setPanSource(null);
        request.setPan("invalid pan");
        request.setDateExpiry("12");
        request.setTrack2("invalid track2");
        request.setAccountType(null);
        request.setRrn("invalid rrn");

        assertEquals("[RFN does not exist in map., "
            + "txnRef: Must not be empty., "
            + "panSource: Enum null not found in the list: [PinPad, PosKeyed, PosSwiped, Internet, "
            + "TeleOrder, Moto, CustomerPresent, RecurringTransaction, Installment]., "
            + "pan: Length must be 20 chars., "
            + "dateExpiry: Must be in format MMYY. Entered value: 12., "
            + "track2: Length must be 40 chars., "
            + "accountType: Enum null not found in the list: [Default, Cheque, Credit, Savings, "
            + "Unknown]., rrn: Length must be 12 chars.]", request.validate().toString());
    }

    @Test
    void should_return_messages_ifPosRequestEmpty() {
        PreAuthCancelRequest request = new PreAuthCancelRequest("test rfn");
        request.setTxnRef("1234567");
        request.setMerchant(null);
        request.setApplication(null);
        request.setReceiptAutoPrint(null);
        assertEquals("[merchant: Must not be empty., application: Must not be empty.,"
            + " receiptAutoPrint: Enum null not found in the list: [POS, PinPad, Both].]", request
                .validate().toString());
    }

    @Test
    void should_not_return_messages_ifNotEmpty() {
        PreAuthCancelRequest request = new PreAuthCancelRequest("test rfn");
        request.setTxnRef("1234567");

        assertEquals(request.getPurchaseAnalysisData()
            .get(Constants.PurchaseAnalysisData.RFN), "test rfn");
        assertEquals(request.getTxnType(), TxnType.PreAuthCancel);
        assertEquals(0, request.validate().size());
    }
}
