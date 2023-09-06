package com.linkly.pos.sdk.service.impl.testData;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.configureMerchant.ConfigureMerchantRequest;
import com.linkly.pos.sdk.models.configureMerchant.ConfigureMerchantResponse;
import com.linkly.pos.sdk.models.enums.ResponseType;
import com.squareup.moshi.Types;

public final class ConfigureMerchantMock {

    public static ConfigureMerchantRequest request() {
        ConfigureMerchantRequest request = new ConfigureMerchantRequest();
        request.setCaId("123");
        request.setCatId("1234");
        return request;
    }

    public static String configureMerchantResponseContent() {
        ConfigureMerchantResponse response = new ConfigureMerchantResponse();
        response.setResponseType(ResponseType.ConfigureMerchant);
        response.setSuccess(true);
        response.setResponseCode("200");

        Type type = Types.newParameterizedType(List.class, ConfigureMerchantResponse.class);

        return MoshiUtil.getAdapter(type).toJson(Arrays.asList(response));
    }
}
