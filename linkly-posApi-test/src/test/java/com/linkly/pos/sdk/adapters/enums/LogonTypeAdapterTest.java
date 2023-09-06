package com.linkly.pos.sdk.adapters.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.LogonType;
import com.squareup.moshi.JsonAdapter;

class LogonTypeAdapterTest {

    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);

    @Test
    void should_deserialize_logonTypeEnum() {
        TestClass testClass = new TestClass();
        String content = adapter.toJson(testClass);
        assertEquals("{\"type1\":\" \",\"type2\":\"4\",\"type3\":\"5\",\"type4\":\"6\",\"type5\":"
            + "\"7\",\"type6\":\"8\",\"type7\":\"1\"}", content);
    }

    @Test
    void should_serialize_logonTypeEnum() throws IOException {
        String content =
            "{\"type1\":\" \",\"type2\":\"4\",\"type3\":\"5\",\"type4\":\"6\",\"type5\":"
                + "\"7\",\"type6\":\"8\",\"type7\":\"1\"}";
        TestClass testClass = adapter.fromJson(content);
        assertTrue(testClass.getType1().equals(LogonType.Standard));
        assertTrue(testClass.getType2().equals(LogonType.RSA));
        assertTrue(testClass.getType3().equals(LogonType.TmsFull));
        assertTrue(testClass.getType4().equals(LogonType.TmsParams));
        assertTrue(testClass.getType5().equals(LogonType.TmsSoftware));
        assertTrue(testClass.getType6().equals(LogonType.Logoff));
        assertTrue(testClass.getType7().equals(LogonType.Diagnostics));
    }

    private static class TestClass {
        private LogonType type1 = LogonType.Standard;
        private LogonType type2 = LogonType.RSA;
        private LogonType type3 = LogonType.TmsFull;
        private LogonType type4 = LogonType.TmsParams;
        private LogonType type5 = LogonType.TmsSoftware;
        private LogonType type6 = LogonType.Logoff;
        private LogonType type7 = LogonType.Diagnostics;

        public LogonType getType1() {
            return type1;
        }

        public LogonType getType2() {
            return type2;
        }

        public LogonType getType3() {
            return type3;
        }

        public LogonType getType4() {
            return type4;
        }

        public LogonType getType5() {
            return type5;
        }

        public LogonType getType6() {
            return type6;
        }

        public LogonType getType7() {
            return type7;
        }

    }

}
