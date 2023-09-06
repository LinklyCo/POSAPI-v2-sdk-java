package com.linkly.pos.sdk.service.impl.testData;

import java.time.LocalDateTime;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.AuthToken;
import com.linkly.pos.sdk.models.authentication.TokenResponse;

public final class AuthTokenMock {

	private static final String TEST_TOKEN = "testvalidtoken";
    public static final AuthToken authToken() {
        return new AuthToken(TEST_TOKEN, LocalDateTime.now());
    }

    public static final String authTokenContent() {
        return MoshiUtil.getAdapter(AuthToken.class).toJson(authToken());
    }

    public static final String tokenResponseContent() {
        TokenResponse token = new TokenResponse();
        token.setToken(TEST_TOKEN);
        token.setExpirySeconds(1800);
        return MoshiUtil.getAdapter(TokenResponse.class).toJson(token);
    }
}
