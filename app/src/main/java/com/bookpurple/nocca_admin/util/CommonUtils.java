package com.bookpurple.nocca_admin.util;

/*
 * Written by Gaurav Sharma on 2020-02-01.
 */
public class CommonUtils {

    public static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
}
