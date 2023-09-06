package com.linkly.pos.sdk.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.squareup.moshi.JsonAdapter;

class UUIDAdapterTest {

    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);
    private static final UUID UUID_VALUE = UUID.randomUUID();

    @Test
    void should_deserialize_uuid() {
        TestClass testClass = new TestClass(UUID_VALUE);
        String content = adapter.toJson(testClass);
        String reference = "{\"uuid\":\"" + UUID_VALUE.toString() + "\"}";
        assertEquals(reference, content);
    }

    @Test
    void should_serialize_uuid() throws IOException {
        String reference = "{\"uuid\":\"" + UUID_VALUE.toString() + "\"}";
        TestClass testClass = adapter.fromJson(reference);
        assertTrue(UUID_VALUE.equals(testClass.getUuid()));
    }

    private static class TestClass {

        private UUID uuid;

        public TestClass(UUID uuid) {
            super();
            this.uuid = uuid;
        }

        public UUID getUuid() {
            return uuid;
        }

    }
}
