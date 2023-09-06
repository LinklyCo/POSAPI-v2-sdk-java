package com.linkly.pos.sdk.service.impl.testData;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.ResponseType;
import com.linkly.pos.sdk.models.settlement.SettlementRequest;
import com.linkly.pos.sdk.models.settlement.SettlementResponse;
import com.squareup.moshi.Types;

public final class SettlementMock {

    public static SettlementRequest settlementRequest() {
        return new SettlementRequest();
    }

    public static String settlementResponseContent() {
        SettlementResponse response = new SettlementResponse();
        response.setSettlementData("Settlement Data");
        response.setResponseType(ResponseType.Settlement);
        response.setMerchant("00");
        Type type = Types.newParameterizedType(List.class, SettlementResponse.class);

        return MoshiUtil.getAdapter(type).toJson(Arrays.asList(response));
    }
}
