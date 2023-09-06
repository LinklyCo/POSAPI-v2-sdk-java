package com.linkly.pos.sdk.adapters.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.NetworkType;
import com.squareup.moshi.JsonAdapter;

class NetworkTypeAdapterTest {

    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);

    @Test
    void should_deserialize_Enum() {
        TestClass testClass = new TestClass();
        String content = adapter.toJson(testClass);
        assertEquals("{\"type1\":\"1\",\"type2\":\"2\",\"type3\":\" \"}", content);
    }

    @Test
    void should_serialize_Enum() throws IOException {
        String content = "{\"type1\":\"1\",\"type2\":\"2\",\"type3\":\" \"}";
        TestClass testClass = adapter.fromJson(content);
        assertTrue(testClass.getType1().equals(NetworkType.Leased));
        assertTrue(testClass.getType2().equals(NetworkType.Dialup));
        assertTrue(testClass.getType3().equals(NetworkType.Unknown));
    }

    private static class TestClass {
        private NetworkType type1 = NetworkType.Leased;
        private NetworkType type2 = NetworkType.Dialup;
        private NetworkType type3 = NetworkType.Unknown;

        public NetworkType getType1() {
            return type1;
        }

        public NetworkType getType2() {
            return type2;
        }

        public NetworkType getType3() {
            return type3;
        }
    }
}