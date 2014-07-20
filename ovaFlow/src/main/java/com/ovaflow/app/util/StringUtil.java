package com.ovaflow.app.util;

/**
 * Created by ArthurXu on 19/07/2014.
 */
public class StringUtil {

    public static boolean hasValue(String str) {
        return str != null && !str.isEmpty();
    }

    public static boolean hasValue(String... str) {
        for (String s : str)
            if (!hasValue(s))
                return false;
        return true;
    }
}
