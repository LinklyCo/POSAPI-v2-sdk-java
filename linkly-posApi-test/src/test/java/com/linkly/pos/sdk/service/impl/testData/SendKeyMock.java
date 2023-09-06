package com.linkly.pos.sdk.service.impl.testData;

import java.util.UUID;

import com.linkly.pos.sdk.models.sendKey.SendKeyRequest;

public final class SendKeyMock {

    public static SendKeyRequest request() {
        SendKeyRequest request = new SendKeyRequest();
        request.setKey("0");
        request.setData("test data");
        request.setSessionId(UUID.randomUUID());
        return request;
    }
}
