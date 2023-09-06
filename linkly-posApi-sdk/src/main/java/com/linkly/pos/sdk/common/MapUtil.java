package com.linkly.pos.sdk.common;

import java.util.Map;

/**
 * Utility class containing methods that work with java 'Map'.
 */
public final class MapUtil {

    /**
     * Retrieves the associated value within the provided map given the provided key.
     * If the key is not present, null will be returned by default.
     * 
     * @param map The map to retrieve associated value from.
     * @param key The key to lookup within the provided map.
     */
    public static String getValueOrDefault(Map<String, String> map, String key) {
        String val = map.get(key);
        return val != null ? val : null;
    }

}
