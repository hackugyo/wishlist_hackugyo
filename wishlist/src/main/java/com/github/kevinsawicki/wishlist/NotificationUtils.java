package com.github.kevinsawicki.wishlist;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class NotificationUtils {

    /**
     * HONEYCOMB以降用に，ラージアイコンをセットします．
     * 
     * @param notificationBuilder
     * @return NotificationCompat.Builder
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static NotificationCompat.Builder setLargeIcon(Context context, NotificationCompat.Builder notificationBuilder, int drawableId) {
        Bitmap largeIconBmp = BitmapFactory.decodeResource(context.getResources(), drawableId);
        int height = (int) context.getResources().getDimension(android.R.dimen.notification_large_icon_height);
        int width = (int) context.getResources().getDimension(android.R.dimen.notification_large_icon_width);
        Bitmap largeIcon = Bitmap.createScaledBitmap(largeIconBmp, width, height, false);
        notificationBuilder.setLargeIcon(largeIcon); // 4系用のアイコン
        return notificationBuilder;
    }
}
