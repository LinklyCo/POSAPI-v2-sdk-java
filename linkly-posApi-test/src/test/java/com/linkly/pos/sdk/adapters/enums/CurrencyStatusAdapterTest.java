package com.linkly.pos.sdk.adapters.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.CurrencyStatus;
import com.squareup.moshi.JsonAdapter;

class CurrencyStatusAdapterTest {

    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);

    @Test
    void should_deserialize_currencyStatusEnum() {
        TestClass testClass = new TestClass();
        String content = adapter.toJson(testClass);
        assertEquals("{\"type1\":\" \",\"type2\":\"0\",\"type3\":\"1\",\"type4\":\"Unknown\"}",
            content);
    }

    @Test
    void should_serialize_currencyStatusEnum() throws IOException {
        String content =
            "{\"type1\":\" \",\"type2\":\"0\",\"type3\":\"1\",\"type4\":\"Unknown\"}";
        TestClass testClass = adapter.fromJson(content);
        assertTrue(testClass.getType1().equals(CurrencyStatus.NotSet));
        assertTrue(testClass.getType2().equals(CurrencyStatus.AUD));
        assertTrue(testClass.getType3().equals(CurrencyStatus.Converted));
        assertTrue(testClass.getType4().equals(CurrencyStatus.Unknown));

    }

    private static class TestClass {
        private CurrencyStatus type1 = CurrencyStatus.NotSet;
        private CurrencyStatus type2 = CurrencyStatus.AUD;
        private CurrencyStatus type3 = CurrencyStatus.Converted;
        private CurrencyStatus type4 = CurrencyStatus.Unknown;

        public CurrencyStatus getType1() {
            return type1;
        }

        public CurrencyStatus getType2() {
            return type2;
        }

        public CurrencyStatus getType3() {
            return type3;
        }

        public CurrencyStatus getType4() {
            return type4;
        }

    }

}
