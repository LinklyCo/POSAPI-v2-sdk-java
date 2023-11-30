package com.linkly.pos.sdk.common;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class EnumLookupTest {

    @Test
    void should_lookupEnum_withCorrectValues() {
        TestEnum foundEnum = EnumLookup.findEnumValue(TestEnum.class, "enum2");
        assertEquals(TestEnum.ENUM2, foundEnum);
    }

    @Test
    void should_lookupEnum_notFound() {
        TestEnum foundEnum = EnumLookup.findEnumValue(TestEnum.class, "enum3");
        assertNotEquals(TestEnum.ENUM2, foundEnum);
    }

    @Test
    void should_lookupEnum_throwException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            EnumLookup.findEnumValue(InvalidTestEnum.class, "enum1");
        });
        assertEquals("InvalidTestEnum must have getValue() getter method", exception.getMessage());
    }

    public static enum TestEnum {
        ENUM1("enum1"),
        ENUM2("enum2");

        private String value;

        private TestEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static enum InvalidTestEnum {
        ENUM1("enum1"),
        ENUM2("enum2");

        private String val;

        private InvalidTestEnum(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }
    }
}