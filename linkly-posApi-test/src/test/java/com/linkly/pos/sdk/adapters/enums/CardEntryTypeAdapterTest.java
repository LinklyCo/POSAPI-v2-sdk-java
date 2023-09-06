package com.linkly.pos.sdk.adapters.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.CardEntryType;
import com.squareup.moshi.JsonAdapter;

class CardEntryTypeAdapterTest {

    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);

    @Test
    void should_deserialize_cardEntryTypeEnum() {
        TestClass testClass = new TestClass();
        String content = adapter.toJson(testClass);
        assertEquals(
            "{\"type1\":\" \",\"type2\":\"0\",\"type3\":\"S\",\"type4\":\"K\",\"type5\":\"B\","
                + "\"type6\":\"E\",\"type7\":\"C\"}", content);
    }

    @Test
    void should_serialize_cardEntryTypeEnum() throws IOException {
        String content =
            "{\"type1\":\" \",\"type2\":\"0\",\"type3\":\"S\",\"type4\":\"K\",\"type5\":\"B\",\"type6\":\"E\",\"type7\":\"C\"}";
        TestClass testClass = adapter.fromJson(content);
        assertTrue(testClass.getType1().equals(CardEntryType.NotSet));
        assertTrue(testClass.getType2().equals(CardEntryType.Unknown));
        assertTrue(testClass.getType3().equals(CardEntryType.Swiped));
        assertTrue(testClass.getType4().equals(CardEntryType.Keyed));
        assertTrue(testClass.getType5().equals(CardEntryType.BarCode));
        assertTrue(testClass.getType6().equals(CardEntryType.ChipCard));
        assertTrue(testClass.getType7().equals(CardEntryType.Contactless));

    }

    private static class TestClass {
        private CardEntryType type1 = CardEntryType.NotSet;
        private CardEntryType type2 = CardEntryType.Unknown;
        private CardEntryType type3 = CardEntryType.Swiped;
        private CardEntryType type4 = CardEntryType.Keyed;
        private CardEntryType type5 = CardEntryType.BarCode;
        private CardEntryType type6 = CardEntryType.ChipCard;
        private CardEntryType type7 = CardEntryType.Contactless;

        public CardEntryType getType1() {
            return type1;
        }

        public CardEntryType getType2() {
            return type2;
        }

        public CardEntryType getType3() {
            return type3;
        }

        public CardEntryType getType4() {
            return type4;
        }

        public CardEntryType getType5() {
            return type5;
        }

        public CardEntryType getType6() {
            return type6;
        }

        public CardEntryType getType7() {
            return type7;
        }

    }

}
