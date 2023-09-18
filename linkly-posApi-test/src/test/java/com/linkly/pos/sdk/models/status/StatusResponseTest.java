package com.linkly.pos.sdk.models.status;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        String json = "{\"AIIC\":\"aiic\",\"BankCode\":\"1234567\",\"BankDescription\":"
            + "\"bank description\",\"CPATVersion\":\"cpat 1.0\",\"Caid\":\"02\","
            + "\"CardMisreadCount\":0,\"CashoutLimit\":50,\"Catid\":\"01\","
            + "\"EFTTerminalType\":\"Keycorp\",\"FreeMemoryInTerminal\":100000,"
            + "\"HardwareInceptionDate\":\"2023-01-01T01:12:10Z\",\"HardwareSerial\":"
            + "\"serial 001.1\",\"KVC\":\"kvc\",\"KeyHandlingScheme\":\"2\",\"LoggedOn\":"
            + "true,\"MaxSaf\":50,\"Merchant\":\"001\",\"NII\":2,\"NameTableVersion\":"
            + "\"table v1\",\"NetworkType\":\"1\",\"NumAppsInTerminal\":15,"
            + "\"NumLinesOnDisplay\":10,\"OptionsFlags\":{\"\":true,\"AutoCompletion\":"
            + "true,\"Balance\":true,\"CashOut\":true,\"Completions\":true,\"Deposit\":"
            + "true,\"EFB\":true,\"EMV\":true,\"Moto\":true,\"PreAuth\":true,\"Refund\":"
            + "true,\"Tipping\":true,\"Training\":true,\"StartCash\":true,\"Transfer\":false,"
            + "\"Voucher\":true,\"Withdrawal\":true},\"PinPadSerialNumber\":\"1234567\","
            + "\"PinPadVersion\":\"1.0\",\"RefundLimit\":50,\"RetailerName\":\"retailer 1\","
            + "\"SafCount\":1,\"SafCreditLimit\":50,\"SafDebitLimit\":50,\"Success\":false,"
            + "\"TerminalCommsType\":\"1\",\"TimeOut\":5,\"TotalMemoryInTerminal\":100000}";

        StatusResponse response = MoshiUtil.fromJson(json, StatusResponse.class);

        assertEquals("001", response.getMerchant());
        assertEquals("aiic", response.getAiic());
        assertEquals(2, response.getNii());
        assertEquals("01", response.getCatId());
        assertEquals("02", response.getCaId());
        assertEquals(5, response.getTimeOut());
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

        assertTrue(response.getOptionFlags().isTipping());
        assertTrue(response.getOptionFlags().isPreAuth());
        assertTrue(response.getOptionFlags().isCompletions());
        assertTrue(response.getOptionFlags().isCashOut());
        assertTrue(response.getOptionFlags().isRefund());
        assertTrue(response.getOptionFlags().isBalance());
        assertTrue(response.getOptionFlags().isDeposit());
        assertTrue(response.getOptionFlags().isVoucher());
        assertTrue(response.getOptionFlags().isMoto());
        assertTrue(response.getOptionFlags().isAutoCompletion());
        assertTrue(response.getOptionFlags().isEfb());
        assertTrue(response.getOptionFlags().isEmv());
        assertTrue(response.getOptionFlags().isTraining());
        assertTrue(response.getOptionFlags().isWithdrawal());
        assertTrue(response.getOptionFlags().isStartCash());
    }
}
