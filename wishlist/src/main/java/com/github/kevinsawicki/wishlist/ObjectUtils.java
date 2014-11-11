package com.github.kevinsawicki.wishlist;

public class ObjectUtils {
    private ObjectUtils() {

    }

    public static boolean areNotNull(Object... objects) {
        if (objects == null) return false;
        for (Object obj : objects) {
            if (obj == null) return false;
        }
        return true;
    }

    public static boolean matchAny(Object target, Object... objects) {
        if (objects == null) return false;
        for (Object obj : objects) {
            if (obj == target) return true;
        }
        return false;
    }
}
