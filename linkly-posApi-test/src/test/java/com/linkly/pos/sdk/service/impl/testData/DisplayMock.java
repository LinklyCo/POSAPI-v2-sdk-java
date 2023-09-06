package com.linkly.pos.sdk.service.impl.testData;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.display.DisplayResponse;
import com.linkly.pos.sdk.models.enums.ResponseType;
import com.squareup.moshi.Types;

public final class DisplayMock {

    public static DisplayResponse displayResponse(String displayText) {
        DisplayResponse response = new DisplayResponse();
        response.setResponseType(ResponseType.Display);
        response.setDisplayText(Arrays.asList(displayText));
        return response;
    }

    public static String displayResponseContent(String displayText) {
        List<DisplayResponse> responses = Arrays.asList(displayResponse(displayText));
        Type type = Types.newParameterizedType(List.class, DisplayResponse.class);
        return MoshiUtil.getAdapter(type).toJson(responses);
    }
}
