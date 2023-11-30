package com.linkly.pos.sdk.adapters.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.PanSource;
import com.squareup.moshi.JsonAdapter;

class PanSourceAdapterTest {

    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);

    @Test
    void should_deserialize_panSourceEnum() {
        TestClass testClass = new TestClass();
        String content = adapter.toJson(testClass);
        assertEquals("{\"type1\":\" \",\"type2\":\"K\",\"type3\":\"S\",\"type4\":\"0\","
            + "\"type5\":\"1\",\"type6\":\"2\",\"type7\":\"3\",\"type8\":\"4\",\"type9\":\"5\"}",
            content);
    }

    @Test
    void should_serialize_panSourceEnum() throws IOException {
        String content = "{\"type1\":\" \",\"type2\":\"K\",\"type3\":\"S\",\"type4\":\"0\","
            + "\"type5\":\"1\",\"type6\":\"2\",\"type7\":\"3\",\"type8\":\"4\",\"type9\":\"5\"}";
        TestClass testClass = adapter.fromJson(content);
        assertEquals(PanSource.PinPad, testClass.getType1());
        assertEquals(PanSource.PosKeyed, testClass.getType2());
        assertEquals(PanSource.PosSwiped, testClass.getType3());
        assertEquals(PanSource.Internet, testClass.getType4());
        assertEquals(PanSource.TeleOrder, testClass.getType5());
        assertEquals(PanSource.Moto, testClass.getType6());
        assertEquals(PanSource.CustomerPresent, testClass.getType7());
        assertEquals(PanSource.RecurringTransaction, testClass.getType8());
        assertEquals(PanSource.Installment, testClass.getType9());
    }

    private static class TestClass {
        private PanSource type1 = PanSource.PinPad;
        private PanSource type2 = PanSource.PosKeyed;
        private PanSource type3 = PanSource.PosSwiped;
        private PanSource type4 = PanSource.Internet;
        private PanSource type5 = PanSource.TeleOrder;
        private PanSource type6 = PanSource.Moto;
        private PanSource type7 = PanSource.CustomerPresent;
        private PanSource type8 = PanSource.RecurringTransaction;
        private PanSource type9 = PanSource.Installment;

        public PanSource getType1() {
            return type1;
        }

        public PanSource getType2() {
            return type2;
        }

        public PanSource getType3() {
            return type3;
        }

        public PanSource getType4() {
            return type4;
        }

        public PanSource getType5() {
            return type5;
        }

        public PanSource getType6() {
            return type6;
        }

        public PanSource getType7() {
            return type7;
        }

        public PanSource getType8() {
            return type8;
        }

        public PanSource getType9() {
            return type9;
        }

    }

}
