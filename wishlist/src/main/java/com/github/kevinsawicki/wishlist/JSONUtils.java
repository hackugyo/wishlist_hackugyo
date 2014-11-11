package com.github.kevinsawicki.wishlist;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class JSONUtils {
    private JSONUtils() {

    }

    public static JSONObject toJSONObject(HashMap<String, String> params) {
        JSONObject postParams = (params == null ? null : new JSONObject(params));
        if (postParams != null) {
            try {
                postParams = stringToBoolean(postParams);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return postParams;
    }

    private static JSONObject stringToBoolean(JSONObject postParams) throws JSONException {
        @SuppressWarnings("rawtypes")
        Iterator keys = postParams.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            if (!postParams.isNull(key)) {
                Object value = postParams.get(key);
                if ((value instanceof String)) {
                    if (StringUtils.isSame("false", (String) value)) {
                        postParams.put(key, false);
                    } else if (StringUtils.isSame("true", (String) value)) {
                        postParams.put(key, true);
                    }
                }
            } else {
                LogUtils.e("This key has null value: " + key);
            }
        }
        return postParams;
    }
}
