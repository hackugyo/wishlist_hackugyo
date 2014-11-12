
package com.github.kevinsawicki.wishlist;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Build;
public class AlarmManagerUtils {

    /**
     * Schedule an alarm to be delivered precisely at the stated time.
     * 
     * @see <a
     *      href="http://d.hatena.ne.jp/esmasui/20131103/1383473539">参考ページ</a>
     * @param alarmManger
     * @param type
     * @param triggerAtMillis
     * @param operation
     */
    @SuppressLint("NewApi")
    public static void setExact(AlarmManager alarmManger, int type, long triggerAtMillis, PendingIntent operation) {
        if (Build.VERSION.SDK_INT >= 19) {
            alarmManger.setExact(type, triggerAtMillis, operation);
        } else {
            alarmManger.set(type, triggerAtMillis, operation);
        }
    }
}
