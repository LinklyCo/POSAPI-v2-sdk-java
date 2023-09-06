package com.linkly.pos.sdk.adapters.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.ReprintType;
import com.squareup.moshi.JsonAdapter;

public class ReprintTypeAdapterTest {
    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);

    @Test
    void should_deserialize_reprintTypeEnum() {
        TestClass testClass = new TestClass();
        String content = adapter.toJson(testClass);
        assertEquals("{\"type1\":\"2\",\"type2\":\"1\"}", content);
    }

    @Test
    void should_serialize_reprintTypeEnum() throws IOException {
        String content = "{\"type1\":\"2\",\"type2\":\"1\"}";
        TestClass testClass = adapter.fromJson(content);
        assertTrue(testClass.getType1().equals(ReprintType.GetLast));
        assertTrue(testClass.getType2().equals(ReprintType.Reprint));
    }

    private static class TestClass {
        private ReprintType type1 = ReprintType.GetLast;
        private ReprintType type2 = ReprintType.Reprint;

        public ReprintType getType1() {
            return type1;
        }

        public ReprintType getType2() {
            return type2;
        }

    }
}
