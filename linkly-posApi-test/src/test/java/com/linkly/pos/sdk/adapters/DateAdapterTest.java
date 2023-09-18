package com.linkly.pos.sdk.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.squareup.moshi.JsonAdapter;

class DateAdapterTest {

    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);
    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2023, 01, 13, 01, 01);

    @Test
    void should_deserializeBool_toDateString() {
        TestClass testClass = new TestClass(DATE_TIME);
        String content = adapter.toJson(testClass);
        assertEquals("{\"date\":\"01/13/2023 01:01:00 AM\"}".toUpperCase(), content.toUpperCase());
    }

    @Test
    void should_serializeBool_toLocalDateTime() throws IOException {
        String date = "{\"date\":\"2023-01-13T01:01:00Z\"}";
        TestClass testClass = adapter.fromJson(date);
        assertTrue(testClass.getDate().equals(DATE_TIME));
    }

    private static class TestClass {
        private LocalDateTime date;

        public TestClass(LocalDateTime date) {
            super();
            this.date = date;
        }

        public LocalDateTime getDate() {
            return date;
        }

    }

}
