package com.linkly.pos.sdk.service.impl.testData;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.ResponseType;
import com.linkly.pos.sdk.models.status.PinPadOptionFlags;
import com.linkly.pos.sdk.models.status.StatusRequest;
import com.linkly.pos.sdk.models.status.StatusResponse;
import com.squareup.moshi.Types;

public final class StatusMock {

    public static StatusRequest statusRequest() {
        return new StatusRequest();
    }

    public static String statusResponseContent() {
        StatusResponse response = new StatusResponse();
        response.setResponseType(ResponseType.Status);
        response.setAiic("TESTAIIC");
        response.setOptionsFlags(new PinPadOptionFlags());
        Type type = Types.newParameterizedType(List.class, StatusResponse.class);

        return MoshiUtil.getAdapter(type).toJson(Arrays.asList(response));
    }
}
