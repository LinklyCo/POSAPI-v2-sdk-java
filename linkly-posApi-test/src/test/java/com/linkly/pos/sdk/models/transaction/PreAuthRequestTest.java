package com.linkly.pos.sdk.models.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.PanSource;
import com.linkly.pos.sdk.models.enums.TxnType;

class PreAuthRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        PreAuthRequest request = new PreAuthRequest(0);
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
        PreAuthRequest request = new PreAuthRequest(10);
        request.setTxnRef("1234567");
        assertEquals(request.getTxnType(), TxnType.PreAuth);
        request.validate();
    }

    @Test
    void should_return_messages_ifParentPosApiRequestEmpty() {
        PreAuthRequest request = new PreAuthRequest(10);
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
        PreAuthRequest request = new PreAuthRequest(10);
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
        PreAuthRequest request = new PreAuthRequest(10);
        request.setTxnRef("1234567");
        request.setPanSource(PanSource.PosSwiped);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("track2: Must not be empty.", exception.getMessage());
    }

    @Test
    void should_deserialize_success() {
        PreAuthRequest request = new PreAuthRequest(10);
        request.setTxnRef("1234567");

        String json = MoshiUtil.getAdapter(PreAuthRequest.class).toJson(request);
        assertTrue(json.contains("\"txnRef\":\"1234567\""));
        assertTrue(json.contains("\"AmtPurchase\":10"));
    }
}
