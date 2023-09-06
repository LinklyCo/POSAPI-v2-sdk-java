package com.linkly.pos.sdk.common;

/**
 * Utility class containing methods that work with generic strings.
 */
public final class StringUtil {

    /**
     * Checks whether a string is null or contains only white spaces.
     * 
     * @param str The string for checking.
     * @return 'true' if the string is null or only contains white spaces, otherwise 'false'.
     */
    public static boolean isNullOrWhiteSpace(String str) {
        if (str == null) {
            return true;
        }
        if (str.length() == 0) {
            return true;
        }
        if (str.replaceAll("\\s", "").length() == 0) {
            return true;
        }
        return false;
    }
}
