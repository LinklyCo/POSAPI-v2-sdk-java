package com.linkly.pos.sdk.adapters.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.SettlementType;
import com.squareup.moshi.JsonAdapter;

class SettlementTypeAdapterTest {

    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);

    @Test
    void should_deserialize_settlementTypeEnum() {
        TestClass testClass = new TestClass();
        String content = adapter.toJson(testClass);
        assertEquals("{\"type1\":\"S\",\"type2\":\"P\",\"type3\":\"L\",\"type4\":\"U\","
            + "\"type5\":\"H\",\"type6\":\"I\",\"type7\":\"M\",\"type8\":\"F\",\"type9\":\"D\"}",
            content);
    }

    @Test
    void should_serialize_settlementTypeEnum() throws IOException {
        String content = "{\"type1\":\"S\",\"type2\":\"P\",\"type3\":\"L\",\"type4\":\"U\","
            + "\"type5\":\"H\",\"type6\":\"I\",\"type7\":\"M\",\"type8\":\"F\",\"type9\":\"D\"}";
        TestClass testClass = adapter.fromJson(content);
        assertTrue(testClass.getType1().equals(SettlementType.Settlement));
        assertTrue(testClass.getType2().equals(SettlementType.PreSettlement));
        assertTrue(testClass.getType3().equals(SettlementType.LastSettlement));
        assertTrue(testClass.getType4().equals(SettlementType.SummaryTotals));
        assertTrue(testClass.getType5().equals(SettlementType.SubShiftTotals));
        assertTrue(testClass.getType6().equals(SettlementType.DetailedTransactionListing));
        assertTrue(testClass.getType7().equals(SettlementType.StartCash));
        assertTrue(testClass.getType8().equals(SettlementType.StoreAndForwardTotals));
        assertTrue(testClass.getType9().equals(SettlementType.DailyCashStatement));
    }

    private static class TestClass {
        private SettlementType type1 = SettlementType.Settlement;
        private SettlementType type2 = SettlementType.PreSettlement;
        private SettlementType type3 = SettlementType.LastSettlement;
        private SettlementType type4 = SettlementType.SummaryTotals;
        private SettlementType type5 = SettlementType.SubShiftTotals;
        private SettlementType type6 = SettlementType.DetailedTransactionListing;
        private SettlementType type7 = SettlementType.StartCash;
        private SettlementType type8 = SettlementType.StoreAndForwardTotals;
        private SettlementType type9 = SettlementType.DailyCashStatement;

        public SettlementType getType1() {
            return type1;
        }

        public SettlementType getType2() {
            return type2;
        }

        public SettlementType getType3() {
            return type3;
        }

        public SettlementType getType4() {
            return type4;
        }

        public SettlementType getType5() {
            return type5;
        }

        public SettlementType getType6() {
            return type6;
        }

        public SettlementType getType7() {
            return type7;
        }

        public SettlementType getType8() {
            return type8;
        }

        public SettlementType getType9() {
            return type9;
        }

    }

}
