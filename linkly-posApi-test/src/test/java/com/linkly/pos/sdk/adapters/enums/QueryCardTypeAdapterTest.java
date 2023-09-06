package com.linkly.pos.sdk.adapters.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.QueryCardType;
import com.squareup.moshi.JsonAdapter;

public class QueryCardTypeAdapterTest {
    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);

    @Test
    void should_deserialize_queryCardTypeEnum() {
        TestClass testClass = new TestClass();
        String content = adapter.toJson(testClass);
        assertEquals("{\"type1\":\"0\",\"type2\":\"1\",\"type3\":\"5\",\"type4\":"
            + "\"7\",\"type5\":\"8\"}", content);
    }

    @Test
    void should_serialize_queryCardTypeEnum() throws IOException {
        String content = "{\"type1\":\"0\",\"type2\":\"1\",\"type3\":\"5\",\"type4\":\"7\","
            + "\"type5\":\"8\"}";
        TestClass testClass = adapter.fromJson(content);
        assertTrue(testClass.getType1().equals(QueryCardType.ReadCard));
        assertTrue(testClass.getType2().equals(QueryCardType.ReadCardAndSelectAccount));
        assertTrue(testClass.getType3().equals(QueryCardType.SelectAccount));
        assertTrue(testClass.getType4().equals(QueryCardType.PreSwipe));
        assertTrue(testClass.getType5().equals(QueryCardType.PreSwipeSpecial));
    }

    private static class TestClass {
        private QueryCardType type1 = QueryCardType.ReadCard;
        private QueryCardType type2 = QueryCardType.ReadCardAndSelectAccount;
        private QueryCardType type3 = QueryCardType.SelectAccount;
        private QueryCardType type4 = QueryCardType.PreSwipe;
        private QueryCardType type5 = QueryCardType.PreSwipeSpecial;

        public QueryCardType getType1() {
            return type1;
        }

        public QueryCardType getType2() {
            return type2;
        }

        public QueryCardType getType3() {
            return type3;
        }

        public QueryCardType getType4() {
            return type4;
        }

        public QueryCardType getType5() {
            return type5;
        }

    }
}
