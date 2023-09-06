package com.linkly.pos.sdk.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StringUtilTest {

    @Test
    void should_returnTrue_emptyString() {
        assertTrue(StringUtil.isNullOrWhiteSpace(null));
        assertTrue(StringUtil.isNullOrWhiteSpace(""));
        assertTrue(StringUtil.isNullOrWhiteSpace(" "));
    }

    @Test
    void should_returnTrue_notEmptyString() {
        assertFalse(StringUtil.isNullOrWhiteSpace(" test "));
    }
}
