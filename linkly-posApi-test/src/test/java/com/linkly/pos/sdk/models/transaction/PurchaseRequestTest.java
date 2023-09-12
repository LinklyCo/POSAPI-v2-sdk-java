package com.linkly.pos.sdk.models.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.models.enums.PanSource;
import com.linkly.pos.sdk.models.enums.TxnType;

class PurchaseRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        PurchaseRequest request = new PurchaseRequest(0, 0);
        request.setPanSource(null);
        request.setPan("invalid pan");
        request.setDateExpiry("12");
        request.setTrack2("invalid track2");
        request.setAccountType(null);
        request.setRrn("invalid rrn");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("txnRef: Must not be empty., "
            + "panSource: Enum null not found in the list: [PinPad, PosKeyed, PosSwiped, "
            + "Internet, TeleOrder, Moto, CustomerPresent, RecurringTransaction, "
            + "Installment]., pan: Length must be 20 chars., "
            + "dateExpiry: Must be in format MMYY. Entered value: 12., "
            + "track2: Length must be 40 chars., "
            + "accountType: Enum null not found in the list: [Default, Cheque, Credit, "
            + "Savings, Unknown]., "
            + "rrn: Length must be 12 chars.", exception.getMessage());
    }

    @Test
    void should_not_returnMessages_ifNotEmpty() {
        PurchaseRequest request = new PurchaseRequest(10, 10);
        request.setTxnRef("1234567");
        assertEquals(request.getTxnType(), TxnType.Purchase);
        request.validate();
    }

    @Test
    void should_returnMessages_ifAmountEmpty() {
        PurchaseRequest request = new PurchaseRequest(0, 10);
        request.setTxnRef("1234567");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("amount: Must be between or equal to 1 and 999,999,999. Entered value: 0",
            exception.getMessage());
    }

    @Test
    void should_return_messages_ifParentPosApiRequestEmpty() {
        PurchaseRequest request = new PurchaseRequest(10, 10);
        request.setTxnRef("1234567");
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
    void should_return_messages_ifPanSource_isPosKeyed() {
        PurchaseRequest request = new PurchaseRequest(10, 10);
        request.setTxnRef("1234567");
        request.setPanSource(PanSource.PosKeyed);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("pan: Must not be empty., dateExpiry: Must not be empty.", exception
            .getMessage());
    }

    @Test
    void should_return_messages_ifPanSource_isPosSwiped() {
        PurchaseRequest request = new PurchaseRequest(10, 10);
        request.setTxnRef("1234567");
        request.setPanSource(PanSource.PosSwiped);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("track2: Must not be empty.", exception.getMessage());
    }

}
