package com.linkly.pos.sdk.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.squareup.moshi.JsonAdapter;

class BooleanToBitStringTest {

    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);

    @Test
    void should_deserialize_BoolToBitString() {
        TestClass testClass = new TestClass(true, false, true);
        String content = adapter.toJson(testClass);
        assertEquals(
            "{\"isConvertedFalse\":\"0\",\"isConvertedTrue\":\"1\",\"isNotConverted\":true}",
            content);
    }

    @Test
    void should_serialize_BoolToBitString() throws IOException {
        String json =
            "{\"isConvertedFalse\":\"0\",\"isConvertedTrue\":\"1\",\"isNotConverted\":true}";
        TestClass testClass = adapter.fromJson(json);
        assertEquals(true, testClass.isConvertedTrue());
        assertEquals(false, testClass.isConvertedFalse());
        assertEquals(true, testClass.isNotConverted());
    }

    private static class TestClass {

        @BoolToBitString
        private boolean isConvertedTrue;

        @BoolToBitString
        private boolean isConvertedFalse;

        private boolean isNotConverted;

        public TestClass(boolean isConvertedTrue, boolean isConvertedFalse,
            boolean isNotConverted) {
            super();
            this.isConvertedTrue = isConvertedTrue;
            this.isConvertedFalse = isConvertedFalse;
            this.isNotConverted = isNotConverted;
        }

        public boolean isConvertedTrue() {
            return isConvertedTrue;
        }

        public boolean isConvertedFalse() {
            return isConvertedFalse;
        }

        public boolean isNotConverted() {
            return isNotConverted;
        }
    }

}
