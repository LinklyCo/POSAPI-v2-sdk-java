package com.linkly.pos.sdk.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Types;

public class MoshiUtilTest {

    @Test
    void should_getAdapter_forClass() {
        JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);
        assertEquals("JsonAdapter(com.linkly.pos.sdk.common.MoshiUtilTest$TestClass).nullSafe()",
            adapter.toString());
    }

    @Test
    void should_getAdapter_forType() {
        Type type = Types.newParameterizedType(List.class, TestClass.class);
        JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(type);
        assertEquals("JsonAdapter(com.linkly.pos.sdk.common.MoshiUtilTest$TestClass).nullSafe()"
            + ".collection().nullSafe()", adapter.toString());
    }

    @Test
    void should_serialize_forClass() throws IOException {
        String testClassContent = "{\"str\": \"123\"}";
        TestClass testClass = MoshiUtil.fromJson(testClassContent, TestClass.class);
        assertEquals(testClass.getStr(), "123");
    }

    @Test
    void should_serialize_forType() throws IOException {
        String testClassContent = "[{\"str\":\"123\"},{\"str\":\"1234\"}]";
        Type type = Types.newParameterizedType(List.class, TestClass.class);
        List<TestClass> testClasses = MoshiUtil.fromJson(testClassContent, type);
        assertEquals(testClasses.size(), 2);
        assertEquals(testClasses.get(0).getStr(), "123");
        assertEquals(testClasses.get(1).getStr(), "1234");
    }

    private static class TestClass {
        private String str;

        public String getStr() {
            return str;
        }

    }
}
