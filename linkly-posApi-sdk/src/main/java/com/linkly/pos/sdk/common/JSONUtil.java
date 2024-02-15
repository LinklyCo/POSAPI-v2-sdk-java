package com.linkly.pos.sdk.common;

/**
 * Utility class containing methods that work with JSON data.
 */
public final class JSONUtil {

    /**
     * Wraps the provided content with the provided wrapper key.
     * 
     * @param wrapper
     *            the key value to be used to wrap the provided content with.
     * @param content
     *            The content to be wrapped within a JSON object.
     * 
     * @return {@link String} wrapped in a key
     */
    public static String wrapRequest(String wrapper, String content) {
        return "{ \"" + wrapper + "\":" + content + "}";
    }
}
