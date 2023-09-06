package com.linkly.pos.sdk.common;

import static com.linkly.pos.sdk.common.StringUtil.*;
import static java.text.MessageFormat.format;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

/**
 * Utility class provides methods and regular expression patterns for data validation.
 */
public final class ValidatorUtil {

    private static final String CARD_EXPIRY_REGEX = "(0[1-9]|1[0-2])[0-9][0-9]$";
    private static final String NOT_NULL_MESSAGE = "{0}: Must not be null.";
    private static final String NOT_EMPTY_MESSAGE = "{0}: Must not be empty.";
    private static final String ENUM_NOT_FOUND = "{0}: Enum {1} not found in the list: {2}.";
    private static final String LENGTH_NOT_EQUAL = "{0}: Length must be {1} chars.";
    private static final String MAX_LENGTH_EXCEED = "{0}: Max length must not exceed {1}.";
    private static final String NOT_MATCH = "{0}: Must be in format 'MMYY'. Entered value: {1}.";
    private static final String BETWEEN__EQUAL_NUM_RANGE =
        "{0}: Must be between or equal to {1} and {2}. Entered value: {3}";
    private static final String BETWEEN_NUM_RANGE =
        "{0}: Must be between {1} and {2}. Entered value: {3}";
    private static final String GREATER_THAN = "{0}: Must be greater than {1}. Entered value: {2}";
    private static final String GREATER_THAN_OR_EQUAL = "{0}: Must be greater than or equal to "
        + "{1}. Entered value: {2}";
    private static final String NOT_IN_MAP = "{0} does not exist in map.";

    /**
     * Validates the the provided value is not null.
     * 
     * @param value The string for checking.
     * @param fieldName The field name of the string for checking.
     * @return A validation error message if the value is null, otherwise returns 'null'.
     */
    public static String notNull(String value, String fieldName) {
        if (value == null) {
            return format(NOT_NULL_MESSAGE, fieldName);
        }
        return null;
    }

    /**
     * Validates the the provided value is not null or contains only white spaces.
     * 
     * @param value The string for checking.
     * @param fieldName The field name of the string for checking.
     * @return A validation error message if the value is null or contains only white spaces, otherwise returns 'null'.
     */
    public static String notEmpty(String value, String fieldName) {
        if (StringUtil.isNullOrWhiteSpace(value)) {
            return format(NOT_EMPTY_MESSAGE, fieldName);
        }
        return null;
    }

    /**
     * Validates the the provided UUID is not null.
     * 
     * @param uuid The unique identifier for checking.
     * @param fieldName The field name of the string for checking.
     * @return A validation error message if the value is null, otherwise returns 'null'.
     */
    public static String notEmpty(UUID uuid, String fieldName) {
        if (uuid == null) {
            return format(NOT_EMPTY_MESSAGE, fieldName);
        }
        return null;
    }

    /**
     * Validates the the provided value is not null and is present within the provided enum class.
     * 
     * @param enumType The enum class to find the provided value.
     * @param value The enum value to lookup.
     * @param field The field name of the string for checking.
     * @return A validation error message if the value is null or if it is not found, otherwise returns 'null'.
     */
    public static String isInEnum(Class<?> enumType, Enum<?> value, String field) {
        boolean inEnum = false;
        Object[] enumArr = enumType.getEnumConstants();
        if (value == null) {
            return format(ENUM_NOT_FOUND, field, value, Arrays.toString(enumArr));
        }
        if (enumArr != null) {
            for (Object enumValue : enumArr) {
                if (value.name().equals(enumValue.toString())) {
                    inEnum = true;
                    break;
                }
            }
        }
        return inEnum ? null : format(ENUM_NOT_FOUND, field, value, Arrays.toString(enumArr));

    }

    /**
     * Validates the that the provided Integer value is greater than the specified number.
     * 
     * @param value The Integer value to be checked.
     * @param field The field name of the string for checking.
     * @param num The number the value should be greater than.
     * @return A validation error message if the value is not greater than the specified number, otherwise returns 'null'.
     */
    public static String greaterThan(int value, String fieldName, int num) {
        if (value <= num) {
            return format(GREATER_THAN, fieldName, num, value);
        }
        return null;
    }

    /**
     * Validates the that the provided Integer value is greater than or equal to the specified number.
     * 
     * @param value The Integer value to be checked.
     * @param field The field name of the string for checking.
     * @param num The number the value should be greater than or equal to.
     * @return A validation error message if the value is not greater than or equal to the specified number, otherwise returns 'null'.
     */
    public static String greaterThanOrEqual(int value, String fieldName, int num) {
        if (value < num) {
            return format(GREATER_THAN_OR_EQUAL, fieldName, num, value);
        }
        return null;
    }

    /**
     * Validates the that the provided value is greater than the specified number.
     * 
     * @param value The string for checking.
     * @param field The field name of the string for checking.
     * @param length The length the string length should be equal to.
     * @return A validation error message if the value length is not equal to the specified length, otherwise returns 'null'.
     */
    public static String length(String value, String fieldName, int length) {
        if (!isNullOrWhiteSpace(value) && value.length() != length) {
            return format(LENGTH_NOT_EQUAL, fieldName, length);
        }
        return null;
    }

    /**
     * Validates the that the provided Integer value is between the specified min and max or equal to either the specified min or max.
     * 
     * @param value The Integer value to be checked.
     * @param field The field name of the string for checking.
     * @param min The number the value should be greater than or equal to.
     * @param max The number the value should be less than or equal to.
     * @return A validation error message if the value is not between the specified min and max or equal to either the specified min or max, otherwise returns 'null'.
     */
    public static String inclusiveBetween(int value, String fieldName, int min, int max) {
        boolean greaterThanMin = value >= min;
        boolean lessThanMax = value <= max;
        if (!greaterThanMin || !lessThanMax) {
            return format(BETWEEN__EQUAL_NUM_RANGE, fieldName, min, max, value);
        }
        return null;
    }

    /**
     * Validates the that the provided Integer value is between the specified min and max.
     * 
     * @param value The Integer value to be checked.
     * @param field The field name of the string for checking.
     * @param min The number the value should be greater than.
     * @param max The number the value should be less than.
     * @return A validation error message if the value is not between the specified min and max, otherwise returns 'null'.
     */
    public static String exclusiveBetween(int value, String fieldName, int min, int max) {
        boolean greaterThanMin = value > min;
        boolean lessThanMax = value < max;
        if (!greaterThanMin || !lessThanMax) {
            return format(BETWEEN_NUM_RANGE, fieldName, min, max, value);
        }
        return null;
    }

    /**
     * Validates the that the provided String value length does not exceed the specified max length.
     * 
     * @param value The string for checking.
     * @param field The field name of the string for checking.
     * @param length The length the string length should not exceed.
     * @return A validation error message if the string length exceeds the specified max length, otherwise returns 'null'.
     */
    public static String maxLength(String value, String fieldName, int length) {
        if (!isNullOrWhiteSpace(value) && value.length() > length) {
            return format(MAX_LENGTH_EXCEED, fieldName, length);
        }
        return null;
    }

    /**
     * Validates the that the provided value matches the card expiration regular expression pattern.
     * 
     * @param value The string for checking.
     * @param field The field name of the string for checking.
     * @return A validation error message if the string does not match the card expiration pattern, otherwise returns 'null'.
     */
    public static String expiryDate(String value, String fieldName) {
        if (!isNullOrWhiteSpace(value) && !value.matches(CARD_EXPIRY_REGEX)) {
            return format(NOT_MATCH, fieldName, value);
        }
        return null;
    }

    /**
     * Validates the that the provided map contains the specified key.
     * 
     * @param map The map to check the specified key.
     * @param key The key name to check within the provided map.
     * @return A validation error message if the map does not contain the specified key, otherwise returns 'null'.
     */
    public static String hasValue(Map<String, String> map, String key) {
        String value = map.get(key);
        if (value == null) {
            return format(NOT_IN_MAP, key);
        }
        return null;
    }
}
