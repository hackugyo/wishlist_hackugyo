
package com.github.kevinsawicki.wishlist;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppUtils {
    private static Boolean sIsDebuggable = null;

    /**
     * デバッグモードかどうかを取得します.
     * 
     * @return true の場合はデバッグモード（apkをインストールして実行したものではないということ，実行モードは関係ない）
     */
    public static boolean isDebuggable(final Context context) {
        if (sIsDebuggable == null) {
            sIsDebuggable = isDebuggableImpl(context);
        }
        return sIsDebuggable;
    }

    private static boolean isDebuggableImpl(final Context context) {
        PackageManager pm;
        try {
            pm = context.getPackageManager();
        } catch (NullPointerException e) {
            // contextが取得できないということはアプリが起動していないのにこのメソッドが呼ばれたということなので，
            // テスト実行中と見なす．Debuggableとする
            return true;
        }
        ApplicationInfo info;
        try {
            info = pm.getApplicationInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            return true;
        }
        if ((info.flags & ApplicationInfo.FLAG_DEBUGGABLE) == ApplicationInfo.FLAG_DEBUGGABLE) {
            return true;
        }
        return false;
    }

    public static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
}
