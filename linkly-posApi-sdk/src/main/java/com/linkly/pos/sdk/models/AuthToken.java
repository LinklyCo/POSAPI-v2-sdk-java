package com.linkly.pos.sdk.models;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Authentication token and expiry.
 */
public class AuthToken {

    public AuthToken(String token, LocalDateTime expiry) {
        this.token = token;
        this.expiry = expiry;
    }

    private String token;
    private LocalDateTime expiry;

    /**
     * Token required for bearer authentication in mock POS API requests.
     * 
     * @return String
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the token for bearer authentication.
     * 
     * @param token
     *            The String value of token.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Expiry date of token.
     * 
     * @return LocalDateTime
     */
    public LocalDateTime getExpiry() {
        return expiry;
    }

    /**
     * Sets the expiry date of token.
     * 
     * @param expiry
     *            The LocalDateTime value of API request.
     */
    public void setExpiry(LocalDateTime expiry) {
        this.expiry = expiry;
    }

    /**
     * Returns true if the token is due to expire in the next 5 minutes or if it has already
     * expired.
     * 
     * @return boolean
     */
    public boolean isExpiringSoon() {
        return LocalDateTime.now().isAfter(expiry.minus(5, ChronoUnit.MINUTES));
    }
}