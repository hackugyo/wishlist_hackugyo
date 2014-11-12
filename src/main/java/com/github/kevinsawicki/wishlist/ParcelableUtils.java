package com.github.kevinsawicki.wishlist;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ParcelableUtils {
    /**
     * Parcelableで，ArrayListを読む際には，このメソッドを利用してください．<br>
     * 単純にキャストしてもだめです．<br>
     * 
     * @param result
     * @param parcelableArray
     * @see <a
     *      href="http://stackoverflow.com/questions/10071502/how-to-read-write-array-of-parcelable-objects-android">参考ページ</a>
     * @return ArrayList
     */
    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> readParcelableArray(ArrayList<T> result, Parcelable[] parcelableArray) {

        if (parcelableArray != null) {
            if (result == null) result = new ArrayList<T>();
            result.clear();
            for (int i = 0; i < parcelableArray.length; ++i) {
                result.add((T) parcelableArray[i]);
            }
        } else {
            result = null;
        }
        return result;
    }

    /**
     * {@link Parcel#writeLong(long)}は{@literal null}を受け入れないので，<br>
     * フラグを追加します．<br>
     * 
     * @see <a href="http://stackoverflow.com/a/13980112/2338047">参考ページ</a>，<br>
     *      {@link Parcel#writeValue(Object)}のソース
     * @param out
     * @param value
     */
    @SuppressWarnings("javadoc")
    public static void writeLong(Parcel out, Long value) {
        out.writeInt(value == null ? 0 : 1); // フラグ
        out.writeLong(value == null ? Long.MIN_VALUE : value);
    }

    /**
     * {@link #writeLong(Parcel, Long)}を使って書き出したLongを読み出します．<br>
     * 
     * @param in
     * @return value or null
     */
    public static Long readLong(Parcel in) {
        int flag = in.readInt();
        long value = in.readLong();
        return (flag == 0 ? null : value);
    }

    /**
     * {@link Parcel#writeInt(int)}は{@literal null}を受け入れないので，<br>
     * フラグを追加します．<br>
     * 
     * @param out
     * @param value
     */
    public static void writeInt(Parcel out, Integer value) {
        out.writeInt(value == null ? 0 : 1); // フラグ
        out.writeInt(value == null ? Integer.MIN_VALUE : value);
    }

    /**
     * {@link #writeInt(Parcel, Integer)}を使って書き出したIntegerを読み出します．<br>
     * 
     * @param in
     * @return value or null
     */
    public static Integer readInt(Parcel in) {
        int flag = in.readInt();
        int value = in.readInt();
        return (flag == 0 ? null : value);
    }

    /**
     * Booleanを書き込みます．
     * 
     * @param out
     * @param value
     */
    public static void writeBoolean(Parcel out, Boolean value) {
        out.writeInt(value == null ? 0 : 1); // フラグ
        out.writeInt(value == null ? Integer.MIN_VALUE : (value ? 1 : 0));
    }

    /**
     * {@link #writeBoolean(Parcel, Boolean)}を使って書き出したBooleanを読み出します．<br>
     * 
     * @param in
     * @return value or null
     */
    public static Boolean readBoolean(Parcel in) {
        int flag = in.readInt();
        int value = in.readInt();
        return (flag == 0 ? null : (value == 1));
    }

}
