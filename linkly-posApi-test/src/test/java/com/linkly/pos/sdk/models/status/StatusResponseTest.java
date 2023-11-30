package com.linkly.pos.sdk.models.status;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;

class StatusResponseTest {

    @Test
    void should_deserialize_success() throws IOException {
        String json = "{\"aiic\":\"aiic\",\"bankCode\":\"1234567\",\"bankDescription\":"
        		+ "\"bank description\",\"cpatVersion\":\"cpat 1.0\",\"caid\":\"02\","
        		+ "\"cardMisreadCount\":0,\"cashOutLimit\":50,\"catid\":\"01\","
        		+ "\"eftTerminalType\":\"Keycorp\",\"freeMemoryInTerminal\":100000,"
        		+ "\"hardwareInceptionDate\":\"2023-01-01T01:12:10Z\",\"hardwareSerial\":"
        		+ "\"serial 001.1\",\"kvc\":\"kvc\",\"keyHandlingScheme\":\"2\","
        		+ "\"loggedOn\":true,\"maxSAF\":50,\"merchant\":\"001\",\"nii\":2,"
        		+ "\"nameTableVersion\":\"table v1\",\"networkType\":\"1\","
        		+ "\"numAppsInTerminal\":15,\"numLinesOnDisplay\":10,\"optionsFlags\":"
        		+ "{\"autoCompletion\":true,\"balance\":true,\"cashOut\":true,"
        		+ "\"completions\":true,\"deposit\":true,\"efb\":true,\"emv\":"
        		+ "true,\"moto\":true,\"preAuth\":true,\"refund\":true,\"tipping\":"
        		+ "true,\"training\":true,\"startCash\":true,\"transfer\":false,"
        		+ "\"voucher\":true,\"withdrawal\":true},\"pinPadSerialNumber\":"
        		+ "\"1234567\",\"pinPadVersion\":\"1.0\",\"refundLimit\":50,"
        		+ "\"retailerName\":\"retailer 1\",\"safCount\":1,\"safCreditLimit\":"
        		+ "50,\"safDebitLimit\":50,\"success\":false,\"terminalCommsType\":"
        		+ "\"1\",\"timeOut\":5,\"totalMemoryInTerminal\":100000}";

        StatusResponse response = MoshiUtil.fromJson(json, StatusResponse.class);
        assertEquals(LocalDateTime.of(2023, 01, 01, 01, 12, 10), response
        		.getHardwareInceptionDate());
        
        String parsedResponse = MoshiUtil.getAdapter(StatusResponse.class).toJson(response)
        		.replace("01/01/2023 01:12:10 AM", "");
        json = json.replace("2023-01-01T01:12:10Z", "");
        
        assertEquals(parsedResponse.length(), json.length());
    }
}
