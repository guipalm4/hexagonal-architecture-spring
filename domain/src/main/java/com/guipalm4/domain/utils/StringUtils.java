package com.guipalm4.domain.utils;

public final class StringUtils {

    private StringUtils() {
    }

    public static String removeChars(final String str) {
        return str.replaceAll("[^0-9]", "");
    }
    public static boolean isEmpty(final String value) {
        return value == null || value.isEmpty() || value.trim().isEmpty();
    }
}


