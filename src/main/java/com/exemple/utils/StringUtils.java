package com.exemple.utils;

public class StringUtils {

    public static final String VALUE_REGEX = "^[А-Яа-яЁё]+$";
    public static boolean isNotEmpty(String str) {
        return str != null && !str.isBlank();
    }

    public static String normalize(String str) {
        if (str == null) return null;
        return str.strip().toLowerCase();
    }

    public static String clean(String str) {
        return str == null ? null : str.strip().toLowerCase();
    }

    public static boolean matchesRegex(String str, String regex) {
        return isNotEmpty(str) && str.matches(regex);
    }

    public static boolean checkValue(String str) {
        return str.matches(VALUE_REGEX) && !str.isBlank();
    }
}
