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

        String json = "{\"AccountType\":\"2\","
            + "\"AmtCash\":10,\"AmtPurchase\":10,\"AmtTip\":10,\"AuthCode\":1,"
            + "\"AvailableBalance\":100,\"BalanceReceived\":true,\"Caid\":\"02\","
            + "\"CardName\":\"card name\",\"Catid\":\"01\",\"ClearedFundsBalance\":100,"
            + "\"Date\":\"2023-01-01T01:01:01Z\",\"DateExpiry\":\"0125\",\"DateSettlement\":"
            + "\"2023-01-01T01:01:01Z\",\"Merchant\":\"001\",\"Pan\":\"pan\","
            + "\"PurchaseAnalysisData\":{\"SUR\":\"10\",\"AMT\":\"10\","
            + "\"RFN\":\"rfn\"},\"RFN\":\"rfn\",\"RRN\":\"rrn\",\"Receipts\":"
            + "[{\"IsPrePrint\":true,\"ReceiptText\":[\"receipt text 1\","
            + "\"receipt text 2\"],\"ReceiptType\":\"M\"}],\"Stan\":12345,"
            + "\"Success\":false,\"Track2\":\"track2\",\"TxnFlags\":{\"CardEntry\":"
            + "\"C\",\"CommsMethod\":\"1\",\"Currency\":\"1\",\"Offline\":\"not offline\","
            + "\"PayPass\":\"0\",\"ReceiptPrinted\":\"true\",\"UndefinedFlag6\":"
            + "\"not defined 6\",\"UndefinedFlag7\":\"not defined 7\"},\"TxnRef\":"
            + "\"123456789\",\"TxnType\":\"C\"}";

        TransactionResponse response = MoshiUtil.fromJson(json, TransactionResponse.class);
        assertEquals(TxnType.Cash, response.getTxntype());
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
        assertEquals(ReceiptType.Merchant, receiptResponse.getReceiptType());
        assertEquals("receipt text 1", receiptResponse.getReceiptText().get(0));
        assertEquals("receipt text 2", receiptResponse.getReceiptText().get(1));
        assertEquals(true, receiptResponse.isPrePrint());
    }
}
