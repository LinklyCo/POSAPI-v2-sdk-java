package com.linkly.pos.sdk.adapters.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.ReceiptType;
import com.squareup.moshi.JsonAdapter;

public class ReceiptTypeAdapterTest {
    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);

    @Test
    void should_deserialize_receiptTypeEnum() {
        TestClass testClass = new TestClass();
        String content = adapter.toJson(testClass);
        assertEquals("{\"type1\":\"L\",\"type2\":\"C\",\"type3\":\"M\",\"type4\":\"S\","
            + "\"type5\":\"R\",\"type6\":\"Unknown\"}", content);
    }

    @Test
    void should_serialize_receiptTypeEnum() throws IOException {
        String content = "{\"type1\":\"L\",\"type2\":\"C\",\"type3\":\"M\",\"type4\":\"S\","
            + "\"type5\":\"R\",\"type6\":\"Unknown\"}";
        TestClass testClass = adapter.fromJson(content);
        assertTrue(testClass.getType1().equals(ReceiptType.Logon));
        assertTrue(testClass.getType2().equals(ReceiptType.Customer));
        assertTrue(testClass.getType3().equals(ReceiptType.Merchant));
        assertTrue(testClass.getType4().equals(ReceiptType.Settlement));
        assertTrue(testClass.getType5().equals(ReceiptType.ReceiptText));
        assertTrue(testClass.getType6().equals(ReceiptType.Unknown));
    }

    private static class TestClass {

        private ReceiptType type1 = ReceiptType.Logon;
        private ReceiptType type2 = ReceiptType.Customer;
        private ReceiptType type3 = ReceiptType.Merchant;
        private ReceiptType type4 = ReceiptType.Settlement;
        private ReceiptType type5 = ReceiptType.ReceiptText;
        private ReceiptType type6 = ReceiptType.Unknown;

        public ReceiptType getType1() {
            return type1;
        }

        public ReceiptType getType2() {
            return type2;
        }

        public ReceiptType getType3() {
            return type3;
        }

        public ReceiptType getType4() {
            return type4;
        }

        public ReceiptType getType5() {
            return type5;
        }

        public ReceiptType getType6() {
            return type6;
        }

    }
}
