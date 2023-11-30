package com.linkly.pos.sdk.adapters.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.CommsMethodType;
import com.squareup.moshi.JsonAdapter;

class CommsMethodTypeAdapterTest {

    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);

    @Test
    void should_deserialize_commsMethodTypeEnum() {
        TestClass testClass = new TestClass();
        String content = adapter.toJson(testClass);
        assertEquals("{\"type1\":\" \",\"type2\":\"0\",\"type3\":\"1\",\"type4\":\"2\",\"type5\":"
            + "\"3\"}", content);
    }

    @Test
    void should_serialize_commsMethodTypeEnum() throws IOException {
        String content =
            "{\"type1\":\" \",\"type2\":\"0\",\"type3\":\"1\",\"type4\":\"2\",\"type5\":\"3\"}";
        TestClass testClass = adapter.fromJson(content);
        assertEquals(CommsMethodType.NotSet, testClass.getType1());
        assertEquals(CommsMethodType.Unknown, testClass.getType2());
        assertEquals(CommsMethodType.P66, testClass.getType3());
        assertEquals(CommsMethodType.Argen, testClass.getType4());
        assertEquals(CommsMethodType.X25, testClass.getType5());
    }

    private static class TestClass {
        private CommsMethodType type1 = CommsMethodType.NotSet;
        private CommsMethodType type2 = CommsMethodType.Unknown;
        private CommsMethodType type3 = CommsMethodType.P66;
        private CommsMethodType type4 = CommsMethodType.Argen;
        private CommsMethodType type5 = CommsMethodType.X25;

        public CommsMethodType getType1() {
            return type1;
        }

        public CommsMethodType getType2() {
            return type2;
        }

        public CommsMethodType getType3() {
            return type3;
        }

        public CommsMethodType getType4() {
            return type4;
        }

        public CommsMethodType getType5() {
            return type5;
        }

    }

}
