package com.linkly.pos.sdk.service.impl.testData;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.ResponseType;
import com.linkly.pos.sdk.models.reprintReceipt.ReprintReceiptRequest;
import com.linkly.pos.sdk.models.reprintReceipt.ReprintReceiptResponse;
import com.squareup.moshi.Types;

public final class ReprintReceiptMock {

    public static ReprintReceiptRequest request() {
        return new ReprintReceiptRequest();
    }

    public static String reprintReceiptResponseContent() {
        ReprintReceiptResponse response = new ReprintReceiptResponse();
        response.setMerchant("01");
        response.setReceiptText(Arrays.asList("Official Receipt"));
        response.setResponseType(ResponseType.ReprintReceipt);

        Type type = Types.newParameterizedType(List.class, ReprintReceiptResponse.class);

        return MoshiUtil.getAdapter(type).toJson(Arrays.asList(response));
    }
}
