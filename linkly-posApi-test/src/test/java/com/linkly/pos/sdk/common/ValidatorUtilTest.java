package com.linkly.pos.sdk.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.linkly.pos.sdk.models.enums.AccountType;
import com.linkly.pos.sdk.models.enums.LogonType;

class ValidatorUtilTest {

    @Test
    void should_returnMessage_ifStrNull() {
        String str = ValidatorUtil.notNull(null, "field1");
        assertEquals("field1: Must not be null.", str);
    }

    @Test
    void should_not_returnMessage_ifStrNotNull() {
        String str = ValidatorUtil.notNull("", "field1");
        assertNull(str);
    }

    @Test
    void should_returnMessage_ifStrEmpty() {
        String str = ValidatorUtil.notEmpty(" ", "field1");
        assertEquals("field1: Must not be empty.", str);
    }

    @Test
    void should_not_returnMessage_ifNotStrEmpty() {
        String str = ValidatorUtil.notEmpty(" test ", "field1");
        assertNull(str);
    }

    @Test
    void should_returnMessage_ifUUIDEmpty() {
        UUID uuid = null;
        String str = ValidatorUtil.notEmpty(uuid, "field1");
        assertEquals("field1: Must not be empty.", str);
    }

    @Test
    void should_not_returnMessage_ifNotUUIDEmpty() {
        UUID uuid = UUID.randomUUID();
        String str = ValidatorUtil.notEmpty(uuid, "field1");
        assertNull(str);
    }

    @Test
    void should_returnMessage_ifNotInEnum() {
        String str = ValidatorUtil.isInEnum(AccountType.class, LogonType.Logoff, "field1");
        assertEquals("field1: Enum Logoff not found in the list: [Default, Cheque, Credit, "
            + "Savings, Unknown].", str);
    }

    @Test
    void should_not_returnMessage_ifInEnum() {
        String str = ValidatorUtil.isInEnum(AccountType.class, AccountType.Credit, "field1");
        assertNull(str);
    }

    @Test
    void should_returnMessage_ifLessThanLimit() {
        int limit = 10;
        String str = ValidatorUtil.greaterThan(10, "field1", limit);
        assertEquals("field1: Must be greater than 10. Entered value: 10", str);
    }

    @Test
    void should_not_returnMessage_ifGreaterThanLimit() {
        int limit = 10;
        String str = ValidatorUtil.greaterThan(11, "field1", limit);
        assertNull(str);
    }

    @Test
    void should_returnMessage_ifLessOrEqualThanLimit() {
        int limit = 11;
        String str = ValidatorUtil.greaterThanOrEqual(10, "field1", limit);
        assertEquals("field1: Must be greater than or equal to 11. Entered value: 10", str);
    }

    @Test
    void should_not_returnMessage_ifGreaterThanOrEqualThanLimit() {
        int limit = 11;
        String str = ValidatorUtil.greaterThanOrEqual(11, "field1", limit);
        assertNull(str);
    }

    @Test
    void should_returnMessage_ifNotEqualLength() {
        int exactLength = 5;
        String str = ValidatorUtil.length("test12", "field1", exactLength);
        assertEquals("field1: Length must be 5 chars.", str);
    }

    @Test
    void should_not_returnMessage_ifEqualLength() {
        int exactLength = 5;
        String str = ValidatorUtil.length("test1", "field1", exactLength);
        assertNull(str);
    }

    @Test
    void should_returnMessage_ifNotBetweenRange_inclusive() {
        int low = 1;
        int high = 5;
        String str = ValidatorUtil.inclusiveBetween(6, "field1", low, high);
        assertEquals("field1: Must be between or equal to 1 and 5. Entered value: 6", str);
    }

    @Test
    void should_not_returnMessage_ifBetweenRange_inclusive() {
        int low = 1;
        int high = 5;
        String str = ValidatorUtil.inclusiveBetween(5, "field1", low, high);
        assertNull(str);
    }

    @Test
    void should_returnMessage_ifNotBetweenRange_exclusive() {
        int low = 1;
        int high = 5;
        String str = ValidatorUtil.exclusiveBetween(5, "field1", low, high);
        assertEquals("field1: Must be between 1 and 5. Entered value: 5", str);
    }

    @Test
    void should_not_returnMessage_ifBetweenRange_exclusive() {
        int low = 1;
        int high = 5;
        String str = ValidatorUtil.exclusiveBetween(4, "field1", low, high);
        assertNull(str);
    }

    @Test
    void should_returnMessage_ifMoreThanMaxLength() {
        int max = 5;
        String str = ValidatorUtil.maxLength("test51", "field1", max);
        assertEquals("field1: Max length must not exceed 5.", str);
    }

    @Test
    void should_not_returnMessage_ifNotMoreThanMaxLength() {
        int max = 5;
        String str = ValidatorUtil.maxLength("test5", "field1", max);
        assertNull(str);
    }

    @Test
    void should_returnMessage_ifNotValidExpiryDate() {
        String str = ValidatorUtil.expiryDate("12251", "field1");
        assertEquals("field1: Must be in format MMYY. Entered value: 12251.", str);
    }

    @Test
    void should_not_returnMessage_ifValidExpiryDate() {
        String str = ValidatorUtil.expiryDate("0123", "field1");
        assertNull(str);
    }

    @Test
    void should_returnMessage_ifNotInMap() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "key value1");
        String str = ValidatorUtil.hasValue(map, "key2");
        assertEquals("key2 does not exist in map.", str);
    }

    @Test
    void should_not_returnMessage_ifInMap() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "key value1");
        String str = ValidatorUtil.hasValue(map, "key1");
        assertNull(str);
    }
}
