package com.linkly.pos.sdk.service.impl.testData;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.PosApiResponse;
import com.linkly.pos.sdk.models.enums.ResponseType;
import com.squareup.moshi.Types;

public final class ResultRequestMock {

    public static String resultRequestResponseContent() {
        Map<String, String> map = new HashMap<>();
        map.put("testkey", "Test Value");

        PosApiResponse response = new PosApiResponse();
        response.setResponseType(ResponseType.Logon);
        response.setPurchaseAnalysisData(map);

        Type type = Types.newParameterizedType(List.class, PosApiResponse.class);

        return MoshiUtil.getAdapter(type).toJson(Arrays.asList(response));
    }
}
