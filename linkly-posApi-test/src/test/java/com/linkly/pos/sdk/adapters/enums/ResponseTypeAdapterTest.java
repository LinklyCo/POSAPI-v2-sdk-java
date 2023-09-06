package com.linkly.pos.sdk.adapters.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.ResponseType;
import com.squareup.moshi.JsonAdapter;

class ResponseTypeAdapterTest {

    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);

    @Test
    void should_deserialize_responseTypeEnum() {
        TestClass testClass = new TestClass();
        String content = adapter.toJson(testClass);
        assertEquals("{\"type1\":\"logon\",\"type10\":\"Unknown\",\"type2\":\"status\","
            + "\"type3\":\"display\",\"type4\":\"receipt\",\"type5\":\"configuremerchant\","
            + "\"type6\":\"querycard\",\"type7\":\"reprintreceipt\",\"type8\":\"transaction\","
            + "\"type9\":\"settlement\"}", content);
    }

    @Test
    void should_serialize_responseTypeEnum() throws IOException {
        String content = "{\"type1\":\"logon\",\"type10\":\"Unknown\",\"type2\":\"status\","
            + "\"type3\":\"display\",\"type4\":\"receipt\",\"type5\":\"configuremerchant\","
            + "\"type6\":\"querycard\",\"type7\":\"reprintreceipt\",\"type8\":\"transaction\","
            + "\"type9\":\"settlement\"}";
        TestClass testClass = adapter.fromJson(content);
        assertTrue(testClass.getType1().equals(ResponseType.Logon));
        assertTrue(testClass.getType2().equals(ResponseType.Status));
        assertTrue(testClass.getType3().equals(ResponseType.Display));
        assertTrue(testClass.getType4().equals(ResponseType.Receipt));
        assertTrue(testClass.getType5().equals(ResponseType.ConfigureMerchant));
        assertTrue(testClass.getType6().equals(ResponseType.QueryCard));
        assertTrue(testClass.getType7().equals(ResponseType.ReprintReceipt));
        assertTrue(testClass.getType8().equals(ResponseType.Transaction));
        assertTrue(testClass.getType9().equals(ResponseType.Settlement));
        assertTrue(testClass.getType10().equals(ResponseType.Unknown));
    }

    private static class TestClass {
        private ResponseType type1 = ResponseType.Logon;
        private ResponseType type2 = ResponseType.Status;
        private ResponseType type3 = ResponseType.Display;
        private ResponseType type4 = ResponseType.Receipt;
        private ResponseType type5 = ResponseType.ConfigureMerchant;
        private ResponseType type6 = ResponseType.QueryCard;
        private ResponseType type7 = ResponseType.ReprintReceipt;
        private ResponseType type8 = ResponseType.Transaction;
        private ResponseType type9 = ResponseType.Settlement;
        private ResponseType type10 = ResponseType.Unknown;

        public ResponseType getType1() {
            return type1;
        }

        public ResponseType getType2() {
            return type2;
        }

        public ResponseType getType3() {
            return type3;
        }

        public ResponseType getType4() {
            return type4;
        }

        public ResponseType getType5() {
            return type5;
        }

        public ResponseType getType6() {
            return type6;
        }

        public ResponseType getType7() {
            return type7;
        }

        public ResponseType getType8() {
            return type8;
        }

        public ResponseType getType9() {
            return type9;
        }

        public ResponseType getType10() {
            return type10;
        }
    }

}
