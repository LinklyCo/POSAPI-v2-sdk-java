package com.linkly.pos.sdk.models;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class AuthTokenTest {

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @Test
    void should_validateToken_notExpiringSoon() {
        LocalDateTime ldt = LocalDateTime.now().plus(21600, ChronoUnit.SECONDS);
        AuthToken token = new AuthToken("test token", ldt);
        assertFalse(token.isExpiringSoon());
    }

    @Test
    void should_validateToken_isExpiringSoon() {
        LocalDateTime ldt = LocalDateTime.now().plus(1, ChronoUnit.SECONDS);
        AuthToken token = new AuthToken("test token", ldt);
        assertTrue(token.isExpiringSoon());
    }

}
