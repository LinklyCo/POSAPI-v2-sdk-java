package com.linkly.pos.sdk.adapters.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.ReceiptAutoPrint;
import com.squareup.moshi.JsonAdapter;

class ReceiptAutoPrintAdapterTest {

    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);

    @Test
    void should_deserialize_receiptAutoPrintEnum() {
        TestClass testClass = new TestClass();
        String content = adapter.toJson(testClass);
        assertEquals("{\"type1\":\"0\",\"type2\":\"9\",\"type3\":\"7\"}", content);
    }

    @Test
    void should_serialize_receiptAutoPrintEnum() throws IOException {
        String content = "{\"type1\":\"0\",\"type2\":\"9\",\"type3\":\"7\"}";
        TestClass testClass = adapter.fromJson(content);
        assertTrue(testClass.getType1().equals(ReceiptAutoPrint.POS));
        assertTrue(testClass.getType2().equals(ReceiptAutoPrint.PinPad));
        assertTrue(testClass.getType3().equals(ReceiptAutoPrint.Both));
    }

    private static class TestClass {

        private ReceiptAutoPrint type1 = ReceiptAutoPrint.POS;
        private ReceiptAutoPrint type2 = ReceiptAutoPrint.PinPad;
        private ReceiptAutoPrint type3 = ReceiptAutoPrint.Both;

        public ReceiptAutoPrint getType1() {
            return type1;
        }

        public ReceiptAutoPrint getType2() {
            return type2;
        }

        public ReceiptAutoPrint getType3() {
            return type3;
        }

    }

}
