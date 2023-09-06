package com.linkly.pos.sdk.adapters.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.TxnType;
import com.squareup.moshi.JsonAdapter;

class TxnTypeAdapterTest {

    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);

    @Test
    void should_deserialize_txnTypeEnum() {
        TestClass testClass = new TestClass();
        String content = adapter.toJson(testClass);
        assertEquals("{\"type1\":\"P\",\"type10\":\"L\",\"type11\":\"N\",\"type12\":\"I\","
            + "\"type13\":\" \",\"type14\":\"Unknown\",\"type2\":\"C\",\"type3\":\"R\",\"type4\":"
            + "\"D\",\"type5\":\"A\",\"type6\":\"E\",\"type7\":\"U\",\"type8\":\"Q\",\"type9\":"
            + "\"O\"}", content);
    }

    @Test
    void should_serialize_txnTypeEnum() throws IOException {
        String content = "{\"type1\":\"P\",\"type10\":\"L\",\"type11\":\"N\",\"type12\":\"I\","
            + "\"type13\":\" \",\"type14\":\"Unknown\",\"type2\":\"C\",\"type3\":\"R\","
            + "\"type4\":\"D\",\"type5\":\"A\",\"type6\":\"E\",\"type7\":\"U\",\"type8\":"
            + "\"Q\",\"type9\":\"O\"}";
        TestClass testClass = adapter.fromJson(content);
        assertTrue(testClass.getType1().equals(TxnType.Purchase));
        assertTrue(testClass.getType2().equals(TxnType.Cash));
        assertTrue(testClass.getType3().equals(TxnType.Refund));
        assertTrue(testClass.getType4().equals(TxnType.Deposit));
        assertTrue(testClass.getType5().equals(TxnType.PreAuth));
        assertTrue(testClass.getType6().equals(TxnType.PreAuthExtend));
        assertTrue(testClass.getType7().equals(TxnType.PreAuthTopUp));
        assertTrue(testClass.getType8().equals(TxnType.PreAuthCancel));
        assertTrue(testClass.getType9().equals(TxnType.PreAuthPartialCancel));
        assertTrue(testClass.getType10().equals(TxnType.PreAuthComplete));
        assertTrue(testClass.getType11().equals(TxnType.PreAuthInquiry));
        assertTrue(testClass.getType12().equals(TxnType.Void));
        assertTrue(testClass.getType13().equals(TxnType.NotSet));
        assertTrue(testClass.getType14().equals(TxnType.Unknown));
    }

    private static class TestClass {
        private TxnType type1 = TxnType.Purchase;
        private TxnType type2 = TxnType.Cash;
        private TxnType type3 = TxnType.Refund;
        private TxnType type4 = TxnType.Deposit;
        private TxnType type5 = TxnType.PreAuth;
        private TxnType type6 = TxnType.PreAuthExtend;
        private TxnType type7 = TxnType.PreAuthTopUp;
        private TxnType type8 = TxnType.PreAuthCancel;
        private TxnType type9 = TxnType.PreAuthPartialCancel;
        private TxnType type10 = TxnType.PreAuthComplete;
        private TxnType type11 = TxnType.PreAuthInquiry;
        private TxnType type12 = TxnType.Void;
        private TxnType type13 = TxnType.NotSet;
        private TxnType type14 = TxnType.Unknown;

        public TxnType getType1() {
            return type1;
        }

        public TxnType getType2() {
            return type2;
        }

        public TxnType getType3() {
            return type3;
        }

        public TxnType getType4() {
            return type4;
        }

        public TxnType getType5() {
            return type5;
        }

        public TxnType getType6() {
            return type6;
        }

        public TxnType getType7() {
            return type7;
        }

        public TxnType getType8() {
            return type8;
        }

        public TxnType getType9() {
            return type9;
        }

        public TxnType getType10() {
            return type10;
        }

        public TxnType getType11() {
            return type11;
        }

        public TxnType getType12() {
            return type12;
        }

        public TxnType getType13() {
            return type13;
        }

        public TxnType getType14() {
            return type14;
        }

    }

}
