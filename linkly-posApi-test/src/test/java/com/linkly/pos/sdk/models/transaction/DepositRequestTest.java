package com.linkly.pos.sdk.models.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.exception.InvalidArgumentException;
import com.linkly.pos.sdk.models.enums.TxnType;

class DepositRequestTest {

    @Test
    void should_return_messages_ifCashAndChequeEmpty() {
        DepositRequest request = new DepositRequest(0, 0, 0);
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
    void should_return_messages_ifCashEmpty() {
        DepositRequest request = new DepositRequest(0, 1, 0);
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
    void should_return_messages_ifAllEmpty() {
        DepositRequest request = new DepositRequest(150, 150, 0);
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
    void should_return_messages_ifPosRequestEmpty() {
        DepositRequest request = new DepositRequest(150, 0, 0);
        request.setTxnRef("1234567");
        request.setMerchant(null);
        request.setApplication(null);
        request.setReceiptAutoPrint(null);
        InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("merchant: Must not be empty., application: Must not be empty.,"
            + " receiptAutoPrint: Enum null not found in the list: [POS, PinPad, Both].", exception
                .getMessage());
    }

    @Test
    void should_not_return_messages_ifNotEmpty() {
        DepositRequest request = new DepositRequest(150, 0, 0);
        request.setTxnRef("1234567");
        assertEquals(TxnType.Deposit, request.getTxnType());
        request.validate();
    }

    @Test
    void should_returnMessages_ifAmountEmpty() {
        DepositRequest request = new DepositRequest(0, 0, 0);
        request.setTxnRef("1234567");
        InvalidArgumentException exception = assertThrows(InvalidArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("amountCash: Must be between 1 and 999,999,999. Entered value: 0, "
            + "amountCheque: Must be between 1 and 999,999,999. Entered value: 0", exception
                .getMessage());
    }

    @Test
    void should_deserialize_success() {
        DepositRequest request = new DepositRequest(150, 20, 10);
        request.setTxnRef("1234567");

        String json = MoshiUtil.getAdapter(DepositRequest.class).toJson(request);
        assertTrue(json.contains("\"txnRef\":\"1234567\""));
        assertTrue(json.contains("\"AmtCash\":150"));
        assertTrue(json.contains("\"AmtPurchase\":20"));
        assertTrue(json.contains("\"TotalPurchaseCount\":10"));
    }
}
