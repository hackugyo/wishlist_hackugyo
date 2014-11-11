package com.github.kevinsawicki.wishlist;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class ConnectionUtils {
    public static boolean isConnected(final Context context) {
        if (AppUtils.isDebuggable(context)) {
            context.enforceCallingOrSelfPermission(//
                    android.Manifest.permission.ACCESS_NETWORK_STATE, "need permission: ACCESS_NETWORK_STATE");
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null) {
            return cm.getActiveNetworkInfo().isConnected();
        }
        return false;
    }
}
