package com.linkly.pos.sdk.models.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.models.enums.PanSource;
import com.linkly.pos.sdk.models.enums.TxnType;

class CashRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        CashRequest request = new CashRequest(0);
        request.setPanSource(null);
        request.setPan("invalid pan");
        request.setDateExpiry("12");
        request.setTrack2("invalid track2");
        request.setAccountType(null);
        request.setRrn("invalid rrn");

        assertEquals(
            "[amountCash: Must be between or equal to 1 and 999,999,999. Entered value: 0, "
                + "txnRef: Must not be empty., "
                + "panSource: Enum null not found in the list: [PinPad, PosKeyed, PosSwiped, "
                + "Internet, TeleOrder, Moto, CustomerPresent, RecurringTransaction, "
                + "Installment]., pan: Length must be 20 chars., "
                + "dateExpiry: Must be in format MMYY. Entered value: 12., "
                + "track2: Length must be 40 chars., "
                + "accountType: Enum null not found in the list: [Default, Cheque, Credit, "
                + "Savings, Unknown]., "
                + "rrn: Length must be 12 chars.]", request.validate().toString());
    }

    @Test
    void should_not_returnMessages_ifNotEmpty() {
        CashRequest request = new CashRequest(10);
        request.setTxnRef("1234567");
        assertEquals(request.getTxnType(), TxnType.Cash);
        assertEquals(0, request.validate().size());
    }

    @Test
    void should_return_messages_ifParentPosApiRequestEmpty() {
        CashRequest request = new CashRequest(10);
        request.setTxnRef("1234567");
        request.setMerchant(null);
        request.setApplication(null);
        request.setReceiptAutoPrint(null);
        assertEquals("[merchant: Must not be empty., application: Must not be empty.,"
            + " receiptAutoPrint: Enum null not found in the list: [POS, PinPad, Both].]", request
                .validate()
                .toString());
    }

    @Test
    void should_return_messages_ifPanSource_isPosKeyed() {
        CashRequest request = new CashRequest(10);
        request.setTxnRef("1234567");
        request.setPanSource(PanSource.PosKeyed);

        assertEquals("[pan: Must not be empty., dateExpiry: Must not be empty.]", request.validate()
            .toString());
    }

    @Test
    void should_return_messages_ifPanSource_isPosSwiped() {
        CashRequest request = new CashRequest(10);
        request.setTxnRef("1234567");
        request.setPanSource(PanSource.PosSwiped);

        assertEquals("[track2: Must not be empty.]", request.validate()
            .toString());
    }
}
