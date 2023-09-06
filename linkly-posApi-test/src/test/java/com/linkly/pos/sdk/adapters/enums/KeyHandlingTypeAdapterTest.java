package com.linkly.pos.sdk.adapters.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.KeyHandlingType;
import com.squareup.moshi.JsonAdapter;

class KeyHandlingTypeAdapterTest {

    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);

    @Test
    void should_deserialize_keyHandlingTypeEnum() {
        TestClass testClass = new TestClass();
        String content = adapter.toJson(testClass);
        assertEquals("{\"type1\":\"1\",\"type2\":\"2\",\"type3\":\" \"}", content);
    }

    @Test
    void should_serialize_keyHandlingTypeEnum() throws IOException {
        String content = "{\"type1\":\"1\",\"type2\":\"2\",\"type3\":\" \"}";
        TestClass testClass = adapter.fromJson(content);
        assertTrue(testClass.getType1().equals(KeyHandlingType.SingleDes));
        assertTrue(testClass.getType2().equals(KeyHandlingType.TripleDes));
        assertTrue(testClass.getType3().equals(KeyHandlingType.Unknown));
    }

    private static class TestClass {
        private KeyHandlingType type1 = KeyHandlingType.SingleDes;
        private KeyHandlingType type2 = KeyHandlingType.TripleDes;
        private KeyHandlingType type3 = KeyHandlingType.Unknown;

        public KeyHandlingType getType1() {
            return type1;
        }

        public KeyHandlingType getType2() {
            return type2;
        }

        public KeyHandlingType getType3() {
            return type3;
        }

    }

}
