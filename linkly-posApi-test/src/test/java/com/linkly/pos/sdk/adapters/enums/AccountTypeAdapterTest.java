package com.linkly.pos.sdk.adapters.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.common.MoshiUtil;
import com.linkly.pos.sdk.models.enums.AccountType;
import com.squareup.moshi.JsonAdapter;

class AccountTypeAdapterTest {

    private JsonAdapter<TestClass> adapter = MoshiUtil.getAdapter(TestClass.class);

    @Test
    void should_deserialize_accountTypeEnum() {
        TestClass testClass = new TestClass();
        String content = adapter.toJson(testClass);
        assertEquals("{\"type1\":\" \",\"type2\":\"1\",\"type3\":\"2\","
            + "\"type4\":\"3\",\"type5\":\"Unknown\"}", content);
    }

    @Test
    void should_serialize_accountTypeEnum() throws IOException {
        String content = "{\"accountType1\":\" \",\"accountType2\":\"1\",\"accountType3\":\"2\","
            + "\"accountType4\":\"3\",\"accountType5\":\"Unknown\"}";
        TestClass testClass = adapter.fromJson(content);
        assertTrue(testClass.getType1() == AccountType.Default);
        assertTrue(testClass.getType2() == AccountType.Cheque);
        assertTrue(testClass.getType3() == AccountType.Credit);
        assertTrue(testClass.getType4() == AccountType.Savings);
        assertTrue(testClass.getType5() == AccountType.Unknown);
    }

    private static class TestClass {
        private AccountType type1 = AccountType.Default;
        private AccountType type2 = AccountType.Cheque;
        private AccountType type3 = AccountType.Credit;
        private AccountType type4 = AccountType.Savings;
        private AccountType type5 = AccountType.Unknown;

        public AccountType getType1() {
            return type1;
        }

        public AccountType getType2() {
            return type2;
        }

        public AccountType getType3() {
            return type3;
        }

        public AccountType getType4() {
            return type4;
        }

        public AccountType getType5() {
            return type5;
        }

    }
}
