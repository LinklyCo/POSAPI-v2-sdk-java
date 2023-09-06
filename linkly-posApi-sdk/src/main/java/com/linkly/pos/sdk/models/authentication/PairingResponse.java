package com.linkly.pos.sdk.models.authentication;

import com.linkly.pos.sdk.models.IBaseResponse;

/**
 * Response to a PairingRequest
 */
public class PairingResponse implements IBaseResponse {

    private String secret;

    /**
     * Non-expiring secret the POS uses to request an auth token via {@link TokenRequest}
     * 
     * @return String
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Sets the secret.
     * 
     * @param secret The String value of secret.
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }
}
