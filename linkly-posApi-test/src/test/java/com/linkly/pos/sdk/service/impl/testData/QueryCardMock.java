package com.linkly.pos.sdk.service.impl.testData;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.AccountType;
import com.linkly.pos.sdk.models.enums.ResponseType;
import com.linkly.pos.sdk.models.queryCard.QueryCardResponse;
import com.squareup.moshi.Types;

public final class QueryCardMock {

    public static String queryCardResponseContent() {
        QueryCardResponse response = new QueryCardResponse();
        response.setResponseType(ResponseType.QueryCard);
        response.setCardName("card 001");
        response.setTrack1("track1 value");
        response.setTrack1Available(true);
        response.setAccountType(AccountType.Savings);

        Type type = Types.newParameterizedType(List.class, QueryCardResponse.class);

        return MoshiUtil.getAdapter(type).toJson(Arrays.asList(response));
    }
}
