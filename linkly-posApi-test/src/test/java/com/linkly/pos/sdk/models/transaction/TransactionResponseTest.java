package com.linkly.pos.sdk.models.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.AccountType;
import com.linkly.pos.sdk.models.enums.CardEntryType;
import com.linkly.pos.sdk.models.enums.CommsMethodType;
import com.linkly.pos.sdk.models.enums.CurrencyStatus;
import com.linkly.pos.sdk.models.enums.PayPassStatus;
import com.linkly.pos.sdk.models.enums.ReceiptType;
import com.linkly.pos.sdk.models.enums.TxnType;
import com.linkly.pos.sdk.models.receipt.ReceiptResponse;

class TransactionResponseTest {
    @Test
    void should_deserialize_success() throws IOException {
        String json = "{\"accountType\":\"2\","
            + "\"amtCash\":10,\"amtPurchase\":10,\"amtTip\":10,\"authCode\":1,"
            + "\"availableBalance\":100,\"balanceReceived\":true,\"caid\":\"02\","
            + "\"cardName\":\"card name\",\"catid\":\"01\",\"clearedFundsBalance\":100,"
            + "\"date\":\"2023-01-01T01:01:01Z\",\"dateExpiry\":\"0125\",\"dateSettlement\":"
            + "\"2023-01-01T01:01:01Z\",\"merchant\":\"001\",\"pan\":\"pan\","
            + "\"purchaseAnalysisData\":{\"SUR\":\"10\",\"AMT\":\"10\","
            + "\"RFN\":\"rfn\"},\"RRN\":\"rrn\",\"receipts\":"
            + "[{\"isPrePrint\":true,\"receiptText\":[\"receipt text 1\","
            + "\"receipt text 2\"],\"type\":\"M\"}],\"stan\":12345,"
            + "\"success\":false,\"track2\":\"track2\",\"txnFlags\":{\"cardEntry\":"
            + "\"C\",\"commsMethod\":\"1\",\"currency\":\"1\",\"offline\":\"not offline\","
            + "\"payPass\":\"0\",\"receiptPrinted\":\"true\",\"undefinedFlag6\":"
            + "\"not defined 6\",\"undefinedFlag7\":\"not defined 7\"},\"txnRef\":"
            + "\"123456789\",\"txnType\":\"C\"}";

        TransactionResponse response = MoshiUtil.fromJson(json, TransactionResponse.class);
        
        validateTransactionResponse(response);
    }
    
    void validateTransactionResponse(TransactionResponse response) {
        assertEquals(TxnType.Cash, response.getTxnType());
        assertEquals("001", response.getMerchant());
        assertEquals("card name", response.getCardName());
        assertEquals("rrn", response.getRrn());
        assertEquals(LocalDateTime.of(2023, 01, 01, 01, 01, 01), response.getDateSettlement());
        assertEquals(10, response.getAmountCash());
        assertEquals(10, response.getAmount());
        assertEquals(10, response.getAmountTip());
        assertEquals(001, response.getAuthCode());
        assertEquals("123456789", response.getTxnRef());
        assertEquals("pan", response.getPan());
        assertEquals("0125", response.getDateExpiry());
        assertEquals("track2", response.getTrack2());
        assertEquals("rfn", response.getRfn());
        assertEquals(AccountType.Credit, response.getAccountType());
        assertEquals(true, response.isBalanceReceived());
        assertEquals(100, response.getAvailableBalance());
        assertEquals(100, response.getClearedFundsBalance());
        assertEquals(LocalDateTime.of(2023, 01, 01, 01, 01, 01), response.getDate());
        assertEquals("01", response.getCatId());
        assertEquals("02", response.getCaId());
        assertEquals(12345, response.getStan());
        assertEquals(10, response.getAmountTotal());
        assertEquals(10, response.getAmountSurcharge());

        TransactionFlags txnFlags = response.getTxnFlags();
        assertEquals("not offline", txnFlags.getOffline());
        assertEquals("true", txnFlags.getReceiptPrinted());
        assertEquals(CardEntryType.Contactless, txnFlags.getCardEntry());
        assertEquals(CommsMethodType.P66, txnFlags.getCommsMethod());
        assertEquals(CurrencyStatus.Converted, txnFlags.getCurrency());
        assertEquals(PayPassStatus.PayPassNotUsed, txnFlags.getPayPass());
        assertEquals("not defined 6", txnFlags.getUndefinedFlag6());
        assertEquals("not defined 7", txnFlags.getUndefinedFlag7());

        ReceiptResponse receiptResponse = response.getReceipts().get(0);
        assertEquals(ReceiptType.Merchant, receiptResponse.getType());
        assertEquals("receipt text 1", receiptResponse.getReceiptText().get(0));
        assertEquals("receipt text 2", receiptResponse.getReceiptText().get(1));
        assertEquals(true, receiptResponse.isPrePrint());
    }
}