package com.linkly.pos.sdk.adapters.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.TerminalCommsType;
import com.squareup.moshi.JsonAdapter;

class TerminalCommsTypeAdapterTest {

    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);

    @Test
    void should_deserialize_terminalCommsTypeEnum() {
        TestClass testClass = new TestClass();
        String content = adapter.toJson(testClass);
        assertEquals("{\"type1\":\"0\",\"type2\":\"1\",\"type3\":\" \"}", content);
    }

    @Test
    void should_serialize_terminalCommsTypeEnum() throws IOException {
        String content = "{\"type1\":\"0\",\"type2\":\"1\",\"type3\":\" \"}";
        TestClass testClass = adapter.fromJson(content);
        assertTrue(testClass.getType1().equals(TerminalCommsType.Cable));
        assertTrue(testClass.getType2().equals(TerminalCommsType.Infrared));
        assertTrue(testClass.getType3().equals(TerminalCommsType.Unknown));
    }

    private static class TestClass {
        private TerminalCommsType type1 = TerminalCommsType.Cable;
        private TerminalCommsType type2 = TerminalCommsType.Infrared;
        private TerminalCommsType type3 = TerminalCommsType.Unknown;

        public TerminalCommsType getType1() {
            return type1;
        }

        public TerminalCommsType getType2() {
            return type2;
        }

        public TerminalCommsType getType3() {
            return type3;
        }

    }

}
