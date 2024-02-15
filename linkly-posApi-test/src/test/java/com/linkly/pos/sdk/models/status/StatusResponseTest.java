package com.linkly.pos.sdk.models.status;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.EftTerminalType;
import com.linkly.pos.sdk.models.enums.KeyHandlingType;
import com.linkly.pos.sdk.models.enums.NetworkType;
import com.linkly.pos.sdk.models.enums.TerminalCommsType;

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
        		+ "\"1\",\"timeout\":5,\"totalMemoryInTerminal\":100000}";

        StatusResponse response = MoshiUtil.fromJson(json, StatusResponse.class);
        
        validateStatusResponse(response);
    }
    
    void validateStatusResponse(StatusResponse response) {
        assertEquals("001", response.getMerchant());
        assertEquals("aiic", response.getAiic());
        assertEquals(2, response.getNii());
        assertEquals("01", response.getCatId());
        assertEquals("02", response.getCaId());
        assertEquals(5, response.getTimeout());
        assertEquals(true, response.isLoggedOn());
        assertEquals("1234567", response.getPinPadSerialNumber());
        assertEquals("1.0", response.getPinPadVersion());
        assertEquals("1234567", response.getBankCode());
        assertEquals("bank description", response.getBankDescription());
        assertEquals("kvc", response.getKvc());
        assertEquals(1, response.getSafCount());
        assertEquals(NetworkType.Leased, response.getNetworkType());
        assertEquals("serial 001.1", response.getHardwareSerial());
        assertEquals("retailer 1", response.getRetailerName());
        assertEquals(50, response.getSafCreditLimit());
        assertEquals(50, response.getSafDebitLimit());
        assertEquals(50, response.getMaxSaf());
        assertEquals(KeyHandlingType.TripleDes, response.getKeyHandlingScheme());
        assertEquals(50, response.getCashOutLimit());
        assertEquals(50, response.getRefundLimit());
        assertEquals("cpat 1.0", response.getCpatVersion());
        assertEquals("table v1", response.getNameTableVersion());
        assertEquals(TerminalCommsType.Infrared, response.getTerminalCommsType());
        assertEquals(0, response.getCardMisreadCount());
        assertEquals(100000, response.getTotalMemoryInTerminal());
        assertEquals(100000, response.getFreeMemoryInTerminal());
        assertEquals(EftTerminalType.Keycorp, response.getEftTerminalType());
        assertEquals(15, response.getNumAppsInTerminal());
        assertEquals(10, response.getNumLinesOnDisplay());
        assertEquals(LocalDateTime.of(2023, 01, 01, 01, 12, 10), response
            .getHardwareInceptionDate());

        var optionFlags = response.getOptionsFlags();
        assertTrue(optionFlags.isTipping());
        assertTrue(optionFlags.isPreAuth());
        assertTrue(optionFlags.isCompletions());
        assertTrue(optionFlags.isCashOut());
        assertTrue(optionFlags.isRefund());
        assertTrue(optionFlags.isBalance());
        assertTrue(optionFlags.isDeposit());
        assertTrue(optionFlags.isVoucher());
        assertTrue(optionFlags.isMoto());
        assertTrue(optionFlags.isAutoCompletion());
        assertTrue(optionFlags.isEfb());
        assertTrue(optionFlags.isEmv());
        assertTrue(optionFlags.isTraining());
        assertTrue(optionFlags.isWithdrawal());
        assertTrue(optionFlags.isStartCash());
    }    
}
