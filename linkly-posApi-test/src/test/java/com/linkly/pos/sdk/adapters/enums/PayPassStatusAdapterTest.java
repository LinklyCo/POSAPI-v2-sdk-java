package com.linkly.pos.sdk.adapters.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.PayPassStatus;
import com.squareup.moshi.JsonAdapter;

class PayPassStatusAdapterTest {

    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);

    @Test
    void should_deserialize_payPassStatusEnum() {
        TestClass testClass = new TestClass();
        String content = adapter.toJson(testClass);
        assertEquals("{\"type1\":\" \",\"type2\":\"1\",\"type3\":\"0\",\"type4\":\"Unknown\"}",
            content);
    }

    @Test
    void should_serialize_payPassStatusEnum() throws IOException {
        String content = "{\"type1\":\" \",\"type2\":\"1\",\"type3\":\"0\",\"type4\":\"Unknown\"}";
        TestClass testClass = adapter.fromJson(content);
        assertTrue(testClass.getType1().equals(PayPassStatus.NotSet));
        assertTrue(testClass.getType2().equals(PayPassStatus.PayPassUsed));
        assertTrue(testClass.getType3().equals(PayPassStatus.PayPassNotUsed));
        assertTrue(testClass.getType4().equals(PayPassStatus.Unknown));
    }

    private static class TestClass {
        private PayPassStatus type1 = PayPassStatus.NotSet;
        private PayPassStatus type2 = PayPassStatus.PayPassUsed;
        private PayPassStatus type3 = PayPassStatus.PayPassNotUsed;
        private PayPassStatus type4 = PayPassStatus.Unknown;

        public PayPassStatus getType1() {
            return type1;
        }

        public PayPassStatus getType2() {
            return type2;
        }

        public PayPassStatus getType3() {
            return type3;
        }

        public PayPassStatus getType4() {
            return type4;
        }

    }
}
