package com.linkly.pos.sdk.service.impl.testData;

import java.io.IOException;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.authentication.PairingRequest;
import com.linkly.pos.sdk.models.authentication.PairingResponse;
import com.squareup.moshi.JsonAdapter;

public final class PairingMock {

    private static final JsonAdapter<PairingRequest> requestAdapter = MoshiUtil.getAdapter(
        PairingRequest.class);
    private static final JsonAdapter<PairingResponse> responseAdapter = MoshiUtil.getAdapter(
        PairingResponse.class);

    public static final PairingRequest request() {

    	PairingRequest request = new PairingRequest();
        request.setPairCode("testcode");
        request.setPassword("p@szw0rd");
        request.setUsername("testuser");

        return request;
    }

    public static final String toJson(PairingRequest request) {
        return requestAdapter.toJson(request);
    }

    public static final PairingResponse response() {
    	PairingResponse response = new PairingResponse();
        response.setSecret("test12345secret");
        return response;
    }

    public static final PairingResponse responseEmptySecret() {
    	PairingResponse response = new PairingResponse();
        response.setSecret("");
        return response;
    }

    public static final String toJson(PairingResponse response) {
        return responseAdapter.toJson(response);
    }

    public static final PairingResponse toObject(String response) {
        try {
            return responseAdapter.fromJson(response);
        }
        catch (IOException e) {
            throw new AssertionError("Failed to serialize response.");
        }
    }
}
