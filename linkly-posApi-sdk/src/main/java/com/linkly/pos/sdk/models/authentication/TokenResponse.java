package com.linkly.pos.sdk.models.authentication;

import com.linkly.pos.sdk.models.IBaseResponse;

/**
 * Response to a {@link TokenRequest}. Used internally for automatic token generation
 */
public class TokenResponse implements IBaseResponse {

    private String token;
    private long expirySeconds;

    /**
     * Transient token used for future requests to the PIN pad.
     * 
     * @return String
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the token.
     * 
     * @param token The String value of token.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Time (in seconds) the token is valid for.
     * 
     * @return long
     */
    public long getExpirySeconds() {
        return expirySeconds;
    }

    /**
     * Sets the expirySeconds.
     * 
     * @param expirySeconds The long value of expirySeconds.
     */
    public void setExpirySeconds(long expirySeconds) {
        this.expirySeconds = expirySeconds;
    }

}
