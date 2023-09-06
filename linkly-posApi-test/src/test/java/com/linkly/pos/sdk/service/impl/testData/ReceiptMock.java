package com.linkly.pos.sdk.service.impl.testData;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.ResponseType;
import com.linkly.pos.sdk.models.receipt.ReceiptResponse;
import com.squareup.moshi.Types;

public final class ReceiptMock {

    public static ReceiptResponse receiptResponse() {
        ReceiptResponse response = new ReceiptResponse();
        response.setResponseType(ResponseType.Receipt);
        response.setReceiptText(Arrays.asList("Receipt"));
        return response;
    }

    public static String receiptResponseContent() {
        List<ReceiptResponse> responses = Arrays.asList(receiptResponse());
        Type type = Types.newParameterizedType(List.class, ReceiptResponse.class);
        return MoshiUtil.getAdapter(type).toJson(responses);
    }
}
