package com.github.kevinsawicki.wishlist;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

public class ImageUtils {

    /**
     * {@link Drawable} を{@link Bitmap}に変換します．{@ink BitmapDrawable}でなくても変換できます．
     * 
     * @param drawable
     * @return bitmap（引数がnullの場合，null）
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) return null;
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        if (width <= 0 || height <= 0) {
            // colorDrawableなどの場合高さは関係ないので，IllegalArgumentExceptionが起きないように適当にセットする．
            width = 1;
            height = 1;
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static byte[] bitmapToByte(Bitmap b) {
        if (b == null) return null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();
        return bitmapdata;
    }

    /**
     * Drawableをbyte[]に変換します（PNG、無圧縮）
     * 
     * @param d
     * @return byte
     */
    public static byte[] drawableToByte(Drawable d) {
        Bitmap bitmap = drawableToBitmap(d);
        return bitmapToByte(bitmap);
    }

}
