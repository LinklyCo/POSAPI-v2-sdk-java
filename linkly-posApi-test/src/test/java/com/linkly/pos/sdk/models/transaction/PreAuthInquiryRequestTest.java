package com.linkly.pos.sdk.models.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.Constants;
import com.linkly.pos.sdk.exception.InvalidArgumentException;
import com.linkly.pos.sdk.models.enums.TxnType;

class PreAuthInquiryRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        PreAuthInquiryRequest request = new PreAuthInquiryRequest("rfn");
        request.setPanSource(null);
        request.setPan("invalid pan");
        request.setDateExpiry("12");
        request.setTrack2("invalid track2");
        request.setAccountType(null);
        request.setRrn("invalid rrn");

        InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("txnRef: Must not be empty., "
            + "panSource: Enum null not found in the list: [PinPad, PosKeyed, PosSwiped, Internet, "
            + "TeleOrder, Moto, CustomerPresent, RecurringTransaction, Installment]., "
            + "pan: Length must be 20 chars., "
            + "dateExpiry: Must be in format MMYY. Entered value: 12., "
            + "track2: Length must be 40 chars., "
            + "accountType: Enum null not found in the list: [Default, Cheque, Credit, Savings, "
            + "Unknown]., rrn: Length must be 12 chars.", exception.getMessage());
    }

    @Test
    void should_return_messages_ifPosRequestEmpty() {
        PreAuthInquiryRequest request = new PreAuthInquiryRequest("rfn");
        request.setTxnRef("1234567");
        request.setMerchant(null);
        request.setApplication(null);
        request.setReceiptAutoPrint(null);
        request.setRfn("test rfn");
        InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("merchant: Must not be empty., application: Must not be empty.,"
            + " receiptAutoPrint: Enum null not found in the list: [POS, PinPad, Both].", exception
                .getMessage());
    }

    @Test
    void should_not_return_messages_ifNotEmpty() {
        PreAuthInquiryRequest request = new PreAuthInquiryRequest("rfn");
        request.setTxnRef("1234567");

        assertEquals("rfn", request.getPurchaseAnalysisData()
            .get(Constants.PurchaseAnalysisData.RFN));
        assertEquals(TxnType.PreAuthInquiry, request.getTxnType());
        request.validate();
    }

    @Test
    void should_return_messages_ifRfnEmpty() {
        PreAuthInquiryRequest request = new PreAuthInquiryRequest(null);
        request.setTxnRef("1234567");

        InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("RFN does not exist in map.", exception
            .getMessage());
    }

}
