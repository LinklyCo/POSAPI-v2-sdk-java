package com.linkly.pos.sdk.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.linkly.pos.sdk.models.enums.NetworkType;

/**
 * Utility class that provides methods to perform enum lookups or creation.
 */
public class EnumLookup {

    private static Map<String, Map<String, Object>> enumMap = new HashMap<>();

    /**
     * Finds the equivalent enum value for the provided String representation of the enum name.
     * 
     * @param enumType Enum class that contains the value being looked up.
     * @param findValue string representation of the enum constant value.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T findEnumValue(Class<T> enumType, String findValue) {
        Map<String, Object> map = findOrStore(enumType);
        return (T) map.get(findValue);
    }
    
    /**
     * Finds the equivalent enum value for the provided String representation of the enum name.
     * 
     * @param enumType Enum class that contains the value being looked up.
     * @param findValue string representation of the enum constant value.
     * @param defaultVal default value if findValue is not present
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T findEnumValue(Class<T> enumType, String findValue, String defaultVal) {
        Map<String, Object> map = findOrStore(enumType);
        Object val = map.get(findValue);
        if(val == null) {
        	return (T) map.get(defaultVal);
        }
        return (T) val;
    }

    /**
     * Tries to find the enum values within the stored map, else stores the enum values.
     * 
     * @param enumType Enum class to be searched within the store enum map.
     */
    private static Map<String, Object> findOrStore(Class<?> enumType) {
        try {
            String typeName = enumType.getSimpleName();
            Map<String, Object> map = enumMap.get(typeName);
            if (map == null) {
                map = new HashMap<>();
                Object[] enumConstants = enumType.getEnumConstants();
                for (Object o : enumConstants) {
                    Method method = o.getClass().getMethod("getValue");
                    String value = (String) method.invoke(o);
                    if (value == null) {
                        value = (String) o;
                    }
                    map.put(value, o);
                }
                enumMap.put(typeName, map);
            }
            return map;
        }
        catch (Exception e) {
            throw new IllegalArgumentException(enumType.getSimpleName()
                + " must have getValue() getter method");
        }
    }
}
