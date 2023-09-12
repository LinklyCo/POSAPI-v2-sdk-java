package com.linkly.pos.sdk.models.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.Constants;
import com.linkly.pos.sdk.models.enums.PanSource;
import com.linkly.pos.sdk.models.transaction.surcharge.FixedSurcharge;
import com.linkly.pos.sdk.models.transaction.surcharge.PercentageSurcharge;
import com.linkly.pos.sdk.models.transaction.surcharge.SurchargeOptions;
import com.linkly.pos.sdk.models.transaction.surcharge.SurchargeRule;

class TransactionRequestTest {

    @Test
    void should_return_messages_ifEmpty() {
        TransactionRequest request = new TransactionRequest();
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
        TransactionRequest request = new TransactionRequest();
        request.setTxnRef("1234567");

        byte[] tipOptions = new byte[] { 1, 24, 5 };
        request.setTipOptions(new TippingOptions(tipOptions));

        request.setTipAmount(10);
        request.setProductLevelBlock(true);

        SurchargeRule rule1 = new FixedSurcharge("bin123", 5);
        SurchargeRule rule2 = new PercentageSurcharge("bin12", 10);
        SurchargeOptions options = new SurchargeOptions();
        options.add(rule1);
        options.add(rule2);
        request.setSurchargeOptions(options);

        assertEquals("[01,24,05]", request.getPurchaseAnalysisData()
            .get(Constants.PurchaseAnalysisData.TPO));
        assertEquals("10", request.getPurchaseAnalysisData()
            .get(Constants.PurchaseAnalysisData.TIP));
        assertEquals("1", request.getPurchaseAnalysisData()
            .get(Constants.PurchaseAnalysisData.PLB));
        assertEquals("[{\"b\":\"bin123\",\"t\":\"$\",\"v\":5},{\"b\":\"bin12\",\"v\":10}]",
            request.getPurchaseAnalysisData().get(Constants.PurchaseAnalysisData.SC2));

        assertEquals(request.getTxnType(), null);
        request.validate();
    }

    @Test
    void should_return_messages_ifParentPosApiRequestEmpty() {
        CashRequest request = new CashRequest(10);
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
        TransactionRequest request = new TransactionRequest();
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
        TransactionRequest request = new TransactionRequest();
        request.setTxnRef("1234567");
        request.setPanSource(PanSource.PosSwiped);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            request.validate();
        });
        assertEquals("track2: Must not be empty.", exception.getMessage());
    }
}
