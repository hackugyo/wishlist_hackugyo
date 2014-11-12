package com.github.kevinsawicki.wishlist;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

public class DisplayUtils {
    private DisplayUtils() {

    }

    public static Display getDisplay(Context context) {
        if (context == null) return null;
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    }

    @SuppressWarnings("deprecation")
    public static int getDisplayWidth(Display display) {
        int displayWidth = 0;
        if (Build.VERSION.SDK_INT >= 14) {
            Point size = new Point();
            display.getSize(size);
            displayWidth = size.x;
        } else {
            displayWidth = display.getWidth();
        }
        return displayWidth;
    }


    /**
     * {@link Display#getHeight()} がdeprecatedになったので，SDK_INTによって処理を変えます．
     *
     * @param display
     * @return displayの幅（displayがnullのとき0)
     */
    @SuppressWarnings({"deprecation", "javadoc"})
    public static int getDisplayHeight(Display display) {
        if (display == null) return 0;
        Point outSize = new Point();
        try {
            // test for new method to trigger exception
            @SuppressWarnings("rawtypes")
            Class pointClass = Class.forName("android.graphics.Point");
            Method newGetSize = Display.class.getMethod("getSize", new Class[]{pointClass});

            // no exception, so new method is available, just use it
            newGetSize.invoke(display, outSize);
        } catch (Exception ex) {
            // new method is not available, use the old ones
            outSize.y = display.getHeight();
        }
        return outSize.y;
    }

    /**
     * @param display
     * @return density (引数がnullの場合0f);
     */
    public static float getDisplayDensity(Display display) {
        if (display == null) return 0f;
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.density;
    }

    /**
     * dpからpxの値に変更します。
     *
     * @param dp
     * @return pxに変換した値
     */
    public static int dpToPixel(final Context context, int dp) {
        //return  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, Dhc.getContext().getResources().getDisplayMetrics());
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale);
    }

    /**
     * pxからdpの値に変換します．
     *
     * @param pixel
     * @return dpに変換した値
     */
    public static int pixelToDp(final Context context, int pixel) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pixel / scale);
    }

}