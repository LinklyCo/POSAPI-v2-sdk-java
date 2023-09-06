package com.linkly.pos.sdk.service.impl.testData;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.ResponseType;
import com.linkly.pos.sdk.models.logon.LogonResponse;
import com.squareup.moshi.Types;

public class LogonMock {

    public static LogonResponse response() {
        LogonResponse response = new LogonResponse();
        response.setResponseType(ResponseType.Logon);
        response.setSuccess(true);
        response.setResponseText("Logon Success");

        return response;
    }

    public static String logonResponseContent() {
        List<LogonResponse> responses = Arrays.asList(response());
        Type type = Types.newParameterizedType(List.class, LogonResponse.class);
        return MoshiUtil.getAdapter(type).toJson(responses);
    }
}
