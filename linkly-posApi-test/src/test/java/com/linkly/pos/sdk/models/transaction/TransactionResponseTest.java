package com.linkly.pos.sdk.models.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;

class TransactionResponseTest {

    @Test
    void should_deserialize_success() throws IOException {

        String json = "{\"accountType\":\"2\","
            + "\"amtCash\":10,\"amtPurchase\":10,\"amtTip\":10,\"authCode\":1,"
            + "\"availableBalance\":100,\"balanceReceived\":true,\"caid\":\"02\","
            + "\"cardName\":\"card name\",\"catid\":\"01\",\"clearedFundsBalance\":100,"
            + "\"date\":\"2023-01-01T01:01:01Z\",\"dateExpiry\":\"0125\",\"dateSettlement\":"
            + "\"2023-01-01T01:01:01Z\",\"merchant\":\"001\",\"pan\":\"pan\","
            + "\"purchaseAnalysisData\":{\"sur\":\"10\",\"amt\":\"10\","
            + "\"rfn\":\"rfn\"},\"rrn\":\"rrn\",\"receipts\":"
            + "[{\"isPrePrint\":true,\"receiptText\":[\"receipt text 1\","
            + "\"receipt text 2\"],\"type\":\"M\"}],\"stan\":12345,"
            + "\"success\":false,\"track2\":\"track2\",\"txnFlags\":{\"cardEntry\":"
            + "\"C\",\"commsMethod\":\"1\",\"currency\":\"1\",\"offline\":\"not offline\","
            + "\"payPass\":\"0\",\"receiptPrinted\":\"true\",\"undefinedFlag6\":"
            + "\"not defined 6\",\"undefinedFlag7\":\"not defined 7\"},\"txnRef\":"
            + "\"123456789\",\"txnType\":\"C\"}";


        TransactionResponse response = MoshiUtil.fromJson(json, TransactionResponse.class);

        assertEquals(LocalDateTime.of(2023, 01, 01, 01, 01, 01), response.getDate());
        assertEquals(LocalDateTime.of(2023, 01, 01, 01, 01, 01), response.getDateSettlement());
        
        String parsedResponse = MoshiUtil.getAdapter(TransactionResponse.class).toJson(response)
        		.replaceAll("01/01/2023 01:01:01 AM", "")
        		.replace("\"amountSurcharge\":0,", "")
        		.replace("\"amountTotal\":0,", "");

        json = json.replaceAll("2023-01-01T01:01:01Z", "");
        
        assertEquals(parsedResponse.length(), json.length());
    }
}
