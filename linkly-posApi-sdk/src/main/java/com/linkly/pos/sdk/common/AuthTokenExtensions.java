package com.linkly.pos.sdk.common;

import com.linkly.pos.sdk.models.AuthToken;

/**
 * Provides methods to retrieve auth token related information.
 */
public class AuthTokenExtensions {
    
    /**
     * Return the authorisation header for an {@link AuthToken}
     */
    public static String getAuthenticationHeaderValue(AuthToken authToken) {
        return "Bearer " + authToken.getToken();
    }
}
