package com.linkly.pos.sdk.models.queryCard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.AccountType;

class QueryCardResponseTest {

    @Test
    void should_deserialize_success() throws IOException {
        String json = "{\"AccountType\":\"3\",\"CardName\":\"testcard\",\"IsTrack1Available\":"
            + "true,\"IsTrack2Available\":true,\"IsTrack3Available\":true,\"Merchant\":"
            + "\"merchant1\",\"Success\":false,\"Track1\":\"track1\",\"Track2\":\"track2\","
            + "\"Track3\":\"track3\"}";

        QueryCardResponse response = MoshiUtil.fromJson(json, QueryCardResponse.class);

        assertEquals("merchant1", response.getMerchant());
        assertEquals(true, response.isTrack1Available());
        assertEquals(true, response.isTrack2Available());
        assertEquals(true, response.isTrack3Available());
        assertEquals("track1", response.getTrack1());
        assertEquals("track2", response.getTrack2());
        assertEquals("track3", response.getTrack3());
        assertEquals("testcard", response.getCardName());
        assertEquals(AccountType.Savings, response.getAccountType());
    }

}
