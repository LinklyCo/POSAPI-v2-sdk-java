package com.linkly.pos.sdk.models.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.Constants;
import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.exception.InvalidArgumentException;
import com.linkly.pos.sdk.models.enums.TxnType;

class RefundRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        RefundRequest request = new RefundRequest(0, "rfn");
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
        RefundRequest request = new RefundRequest(10, "rfn");
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
        RefundRequest request = new RefundRequest(10, "rfn");
        request.setTxnRef("1234567");
        request.setRfn("test rfn");

        assertEquals(request.getPurchaseAnalysisData()
            .get(Constants.PurchaseAnalysisData.RFN), "test rfn");
        assertEquals(request.getTxnType(), TxnType.Refund);
        request.validate();
    }

    @Test
    void should_returnMessages_ifAmountEmpty() {
        RefundRequest request = new RefundRequest(0, "rfn");
        request.setTxnRef("1234567");
        request.setRfn("test rfn");
        InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("amount: Must be between 0 and 999,999,999. Entered value: 0",
            exception.getMessage());
    }

    @Test
    void should_deserialize_success() {
        RefundRequest request = new RefundRequest(10, "rfn");
        request.setTxnRef("1234567");

        String json = MoshiUtil.getAdapter(RefundRequest.class).toJson(request);
        assertTrue(json.contains("\"txnRef\":\"1234567\""));
        assertTrue(json.contains("\"AmtPurchase\":10"));
    }
}
