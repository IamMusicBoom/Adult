package com.wma.adult.user;

public class StringUtils {

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0 || "null".equalsIgnoreCase(String.valueOf(str));
    }
}
