package com.linkly.pos.sdk.adapters.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.InputType;
import com.squareup.moshi.JsonAdapter;

class InputTypeAdapterTest {

    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);

    @Test
    void should_deserialize_inputTypeEnum() {
        TestClass testClass = new TestClass();
        String content = adapter.toJson(testClass);
        assertEquals("{\"type1\":\"0\",\"type2\":\"1\",\"type3\":\"2\",\"type4\":\"3\",\"type5\":"
            + "\"4\",\"type6\":\"Unknown\"}", content);
    }

    @Test
    void should_serialize_inputTypeEnum() throws IOException {
        String content = "{\"type1\":\"0\",\"type2\":\"1\",\"type3\":\"2\",\"type4\":\"3\","
            + "\"type5\":\"4\",\"type6\":\"Unknown\"}";
        TestClass testClass = adapter.fromJson(content);
        assertTrue(testClass.getType1().equals(InputType.None));
        assertTrue(testClass.getType2().equals(InputType.Normal));
        assertTrue(testClass.getType3().equals(InputType.Amount));
        assertTrue(testClass.getType4().equals(InputType.Decimal));
        assertTrue(testClass.getType5().equals(InputType.Password));
        assertTrue(testClass.getType6().equals(InputType.Unknown));
    }

    private static class TestClass {
        private InputType type1 = InputType.None;
        private InputType type2 = InputType.Normal;
        private InputType type3 = InputType.Amount;
        private InputType type4 = InputType.Decimal;
        private InputType type5 = InputType.Password;
        private InputType type6 = InputType.Unknown;

        public InputType getType1() {
            return type1;
        }

        public InputType getType2() {
            return type2;
        }

        public InputType getType3() {
            return type3;
        }

        public InputType getType4() {
            return type4;
        }

        public InputType getType5() {
            return type5;
        }

        public InputType getType6() {
            return type6;
        }

    }

}
