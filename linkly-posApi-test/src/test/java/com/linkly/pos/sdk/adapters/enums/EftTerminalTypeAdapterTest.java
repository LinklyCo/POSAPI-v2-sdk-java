package com.linkly.pos.sdk.adapters.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.EftTerminalType;
import com.squareup.moshi.JsonAdapter;

class EftTerminalTypeAdapterTest {

    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);

    @Test
    void should_deserialize_eftTerminalTypeEnum() {
        TestClass testClass = new TestClass();
        String content = adapter.toJson(testClass);
        assertEquals("{\"type1\":\"IngenicoNPT710\",\"type2\":\"IngenicoPX328\",\"type3\":"
            + "\"Ingenicoi5110\",\"type4\":\"Ingenicoi3070\",\"type5\":\"Sagem\",\"type6\":"
            + "\"Verifone\",\"type7\":\"Keycorp\",\"type8\":\"PCEFTPOSVirtualPinpad\","
            + "\"type9\":\"Unknown\"}", content);
    }

    @Test
    void should_serialize_eftTerminalTypeEnum() throws IOException {
        String content = "{\"type1\":\"IngenicoNPT710\",\"type2\":\"IngenicoPX328\",\"type3\":"
            + "\"Ingenicoi5110\",\"type4\":\"Ingenicoi3070\",\"type5\":\"Sagem\",\"type6\":"
            + "\"Verifone\",\"type7\":\"Keycorp\",\"type8\":\"PCEFTPOSVirtualPinpad\","
            + "\"type9\":\"Unknown\"}";
        TestClass testClass = adapter.fromJson(content);
        assertEquals(EftTerminalType.IngenicoNPT710, testClass.getType1());
        assertEquals(EftTerminalType.IngenicoPX328, testClass.getType2());
        assertEquals(EftTerminalType.Ingenicoi5110, testClass.getType3());
        assertEquals(EftTerminalType.Ingenicoi3070, testClass.getType4());
        assertEquals(EftTerminalType.Sagem, testClass.getType5());
        assertEquals(EftTerminalType.Verifone, testClass.getType6());
        assertEquals(EftTerminalType.Keycorp, testClass.getType7());
        assertEquals(EftTerminalType.LinklyVirtualPinPad, testClass.getType8());
        assertEquals(EftTerminalType.Unknown, testClass.getType9());
    }

    private static class TestClass {
        private EftTerminalType type1 = EftTerminalType.IngenicoNPT710;
        private EftTerminalType type2 = EftTerminalType.IngenicoPX328;
        private EftTerminalType type3 = EftTerminalType.Ingenicoi5110;
        private EftTerminalType type4 = EftTerminalType.Ingenicoi3070;
        private EftTerminalType type5 = EftTerminalType.Sagem;
        private EftTerminalType type6 = EftTerminalType.Verifone;
        private EftTerminalType type7 = EftTerminalType.Keycorp;
        private EftTerminalType type8 = EftTerminalType.LinklyVirtualPinPad;
        private EftTerminalType type9 = EftTerminalType.Unknown;

        public EftTerminalType getType1() {
            return type1;
        }

        public EftTerminalType getType2() {
            return type2;
        }

        public EftTerminalType getType3() {
            return type3;
        }

        public EftTerminalType getType4() {
            return type4;
        }

        public EftTerminalType getType5() {
            return type5;
        }

        public EftTerminalType getType6() {
            return type6;
        }

        public EftTerminalType getType7() {
            return type7;
        }

        public EftTerminalType getType8() {
            return type8;
        }

        public EftTerminalType getType9() {
            return type9;
        }

    }

}
