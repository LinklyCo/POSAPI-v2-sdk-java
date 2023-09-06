package com.linkly.pos.sdk.adapters.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.StatusType;
import com.squareup.moshi.JsonAdapter;

class StatusTypeAdapterTest {

    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);

    @Test
    void should_deserialize_statusTypeEnum() {
        TestClass testClass = new TestClass();
        String content = adapter.toJson(testClass);
        assertEquals("{\"type1\":\"0\",\"type2\":\"1\",\"type3\":\"2\",\"type4\":\"3\","
            + "\"type5\":\"4\",\"type6\":\"5\"}", content);
    }

    @Test
    void should_serialize_statusTypeEnum() throws IOException {
        String content = "{\"type1\":\"0\",\"type2\":\"1\",\"type3\":\"2\",\"type4\":\"3\","
            + "\"type5\":\"4\",\"type6\":\"5\"}";
        TestClass testClass = adapter.fromJson(content);
        assertTrue(testClass.getType1().equals(StatusType.Standard));
        assertTrue(testClass.getType2().equals(StatusType.TerminalAppInfo));
        assertTrue(testClass.getType3().equals(StatusType.AppCpat));
        assertTrue(testClass.getType4().equals(StatusType.AppNameTable));
        assertTrue(testClass.getType5().equals(StatusType.Undefined));
        assertTrue(testClass.getType6().equals(StatusType.Preswipe));
    }

    private static class TestClass {
        private StatusType type1 = StatusType.Standard;
        private StatusType type2 = StatusType.TerminalAppInfo;
        private StatusType type3 = StatusType.AppCpat;
        private StatusType type4 = StatusType.AppNameTable;
        private StatusType type5 = StatusType.Undefined;
        private StatusType type6 = StatusType.Preswipe;

        public StatusType getType1() {
            return type1;
        }

        public StatusType getType2() {
            return type2;
        }

        public StatusType getType3() {
            return type3;
        }

        public StatusType getType4() {
            return type4;
        }

        public StatusType getType5() {
            return type5;
        }

        public StatusType getType6() {
            return type6;
        }

    }

}
