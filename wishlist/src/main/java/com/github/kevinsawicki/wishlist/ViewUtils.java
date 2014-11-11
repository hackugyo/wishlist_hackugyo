/*
 * Copyright 2012 Kevin Sawicki <kevinsawicki@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.kevinsawicki.wishlist;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SeekBar;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Utilities for working with the {@link View} class
 */
public class ViewUtils {

    /**
     * Set visibility of given view to be gone or visible
     * <p>
     * This method has no effect if the view visibility is currently invisible
     *
     * @param view
     * @param gone
     * @return view
     */
    public static <V extends View> V setGone(final V view, final boolean gone) {
        if (view != null)
            if (gone) {
                if (GONE != view.getVisibility())
                    view.setVisibility(GONE);
            } else {
                if (VISIBLE != view.getVisibility())
                    view.setVisibility(VISIBLE);
            }
        return view;
    }

    /**
     * Set visibility of given view to be invisible or visible
     * <p>
     * This method has no effect if the view visibility is currently gone
     *
     * @param view
     * @param invisible
     * @return view
     */
    public static <V extends View> V setInvisible(final V view,
                                                  final boolean invisible) {
        if (view != null)
            if (invisible) {
                if (INVISIBLE != view.getVisibility())
                    view.setVisibility(INVISIBLE);
            } else {
                if (VISIBLE != view.getVisibility())
                    view.setVisibility(VISIBLE);
            }
        return view;
    }

    /**
     * Increases the hit rect of a view. This should be used when an icon is small and cannot be easily tapped on.
     * Source: http://stackoverflow.com/a/1343796/5210
     * @param amount The amount of dp's to be added to all four sides of the view hit purposes.
     * @param delegate The view that needs to have its hit rect increased.
     */
    public static void increaseHitRectBy(final int amount, final View delegate) {
        increaseHitRectBy(amount, amount, amount, amount, delegate);
    }

    /**
     * Increases the hit rect of a view. This should be used when an icon is small and cannot be easily tapped on.
     * Source: http://stackoverflow.com/a/1343796/5210
     * @param top The amount of dp's to be added to the top for hit purposes.
     * @param left The amount of dp's to be added to the left for hit purposes.
     * @param bottom The amount of dp's to be added to the bottom for hit purposes.
     * @param right The amount of dp's to be added to the right for hit purposes.
     * @param delegate The view that needs to have its hit rect increased.
     */
    public static void increaseHitRectBy(final int top, final int left, final int bottom, final int right, final View delegate) {
        final View parent = (View) delegate.getParent();
        if (parent != null && delegate.getContext() != null) {
            parent.post(new Runnable() {
                // Post in the parent's message queue to make sure the parent
                // lays out its children before we call getHitRect()
                public void run() {
                    final float densityDpi = delegate.getContext().getResources().getDisplayMetrics().densityDpi;
                    final Rect r = new Rect();
                    delegate.getHitRect(r);
                    r.top -= transformToDensityPixel(top, densityDpi);
                    r.left -= transformToDensityPixel(left, densityDpi);
                    r.bottom += transformToDensityPixel(bottom, densityDpi);
                    r.right += transformToDensityPixel(right, densityDpi);
                    parent.setTouchDelegate(new TouchDelegate(r, delegate));
                }
            });
        }
    }

    public static int transformToDensityPixel(int regularPixel, DisplayMetrics displayMetrics) {
        return transformToDensityPixel(regularPixel, displayMetrics.densityDpi);
    }

    public static int transformToDensityPixel(int regularPixel, float densityDpi) {
        return (int) (regularPixel * densityDpi);
    }

    private ViewUtils() {

    }

    /**
     * {@link View#setBackgroundDrawable(Drawable)}
     * がdeprecatedになったので，SDK_INTによって処理を変えます．
     *
     * @param view
     * @param drawable
     * @return 第1引数のView
     */
    @SuppressWarnings({ "deprecation", "javadoc" })
    @SuppressLint("NewApi")
    public static View setBackgroundDrawable(View view, Drawable drawable) {
        if (drawable == null) {
            view.setBackgroundResource(0);
            return view;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
        return view;
    }



    public static View setBackgroundBitmap(View view, Bitmap bitmap) {
        if (view == null) return null;
        BitmapDrawable drawable = new BitmapDrawable(view.getResources(), bitmap);
        return setBackgroundDrawable(view, drawable);
    }

    /**
     * 
     * @deprecated 毎回リフレクションするため、これをListViewから投げると重すぎる
     * @param view
     * @param drawable
     * @return View
     * @throws InvocationTargetException
     */
    public static View setBackgroundDrawableWithThrowable(View view, Drawable drawable) throws InvocationTargetException {
        if (drawable == null) {
            view.setBackgroundResource(0);
            return view;
        }

        String methodName = (Build.VERSION.SDK_INT >= 16 ? "setBackground" : "setBackgroundDrawable");
        Method setBackground;
        try {
            Class<?> partypes[] = new Class[1];
            partypes[0] = Drawable.class;
            setBackground = ImageView.class.getMethod(methodName, partypes);
            setBackground.invoke(view, new Object[] { drawable });
        } catch (SecurityException e) {
            exceptionLog(e, methodName);
        } catch (NoSuchMethodException e) {
            exceptionLog(e, methodName);
        } catch (IllegalArgumentException e) {
            exceptionLog(e, methodName);
        } catch (IllegalAccessException e) {
            exceptionLog(e, methodName);
        }
        return view;
    }

    private static void exceptionLog(Exception e, String string) {
        LogUtils.w("ImageView#" + string + "(Drawable) isn't available in this devices api");
    }

    /**
     * 指定したビュー階層内の{@link Drawable}をクリアします． {@link Bitmap#recycle}
     * 
     * @param view
     */
    public static final void cleanupView(View view) {
        cleanupViewWithImage(view);
        cleanUpOnClickListeners(view);
        cleanUpAdapter(view);
        // 再帰的に処理
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            int size = vg.getChildCount();
            for (int i = 0; i < size; i++) {
                cleanupView(vg.getChildAt(i));
            }
        }
    }

    /**
     * Viewをきれいにする．（Image・Backgroundなど）<br>
     * {@link Drawable}のrecycleはしていません．
     * 
     * @param view
     * @return 引数のviewを返す
     */
    public static View cleanupViewWithImage(View view) {
        if (view instanceof ImageButton) {
            ImageButton ib = (ImageButton) view;
            ib.setImageDrawable(null);
        } else if (view instanceof ImageView) {
            ImageView iv = (ImageView) view;
            iv.setImageDrawable(null);
        } else if (view instanceof SeekBar) {
            SeekBar sb = (SeekBar) view;
            sb.setProgressDrawable(null);
            sb.setThumb(null);
            // } else if(view instanceof( xxxx )) {  -- 他にもDrawableを使用するUIコンポーネントがあれば追加 
        }
        return setBackgroundDrawable(view, null);
    }

    /**
     * OnClickListenerを解放する．
     * 
     * @param view
     * @see <a
     *      href="http://htomiyama.blogspot.jp/2012/08/androidoutofmemoryerror.html">参考ページ</a>
     */
    @SuppressWarnings({ "rawtypes" })
    private static void cleanUpOnClickListeners(View view) {
        if (view instanceof AdapterView) {
            // AdapterView（ListViewのような）に対しては，setOnClickListener()を呼んではいけない．
            ((AdapterView) view).setOnItemClickListener(null);
            ((AdapterView) view).setOnItemLongClickListener(null);
            ((AdapterView) view).setOnItemSelectedListener(null);
        } else {
            view.setOnClickListener(null);
            view.setOnLongClickListener(null);
            view.setOnTouchListener(null);
        }
    }

    /**
     * Adapterを解放する．
     * 
     * @param view
     * @see <a
     *      href="http://htomiyama.blogspot.jp/2012/08/androidoutofmemoryerror.html">参考ページ</a>
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static void cleanUpAdapter(View view) {
        if (view instanceof AdapterView) {
            try {
                ((AdapterView) view).setAdapter(null);
            } catch (IllegalArgumentException e) {
                LogUtils.v(e.toString()); // CustomなAdapterの場合など，nullをセットすると失敗する場合があるので
            } catch (NullPointerException e) {
                LogUtils.v(e.toString()); // CustomなAdapterの場合など，nullをセットすると失敗する場合があるので
            }
        }
    }

    /**
     * {@link Drawable}をrecycleします．<br>
     * これを使ったら，利用しているViewに対して直後に{@link #cleanupViewWithImage(View)}を呼んでください．<br>
     * 同じbitmapを使っているdrawableが複数存在する場合があるので，注意して呼び出してください．<br>
     * 
     * @param drawable
     */
    public static void recycleDrawable(Drawable drawable) {
        if (drawable == null) return;
        if (!(drawable instanceof BitmapDrawable)) return;
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bitmap = bd.getBitmap();
        if (bitmap != null) bitmap.recycle();
    }

    public static void setActivated(View view, boolean activated) {
        view.setActivated(activated);
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                setActivated(((ViewGroup) view).getChildAt(i), activated);
            }
        }
    }

    /**
     * Call this view's and this view's child's OnClickListener, <br>
     * if it is defined. <br>
     * Performs all normal actions associated with clicking: <br>
     * reporting accessibility event, playing a sound, etc.
     * 
     * @param view
     * @return True there was an assigned OnClickListener that was called, false
     *         otherwise is returned.
     */
    public static boolean performClick(View view) {
        if (view == null) return false;
        boolean result = view.performClick();
        if (!result && (view instanceof ViewGroup)) {
            int count = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < count; i++) {
                result = ((ViewGroup) view).getChildAt(i).performClick();
                if (result) break;
            }
        }
        return result;
    }


    /**
     * HeaderView / FooterViewを追加したListViewのAdapter取得は特殊な処理が必要なので，<br>
     * このメソッド経由で{@link BaseAdapter#notifyDataSetChanged()}すること．
     *
     * @param absListView
     */
    public static void notifyDataSetChanged(AbsListView absListView) {

        BaseAdapter ba = getBaseAdapter(absListView);
        if (ba == null) {
            LogUtils.w("cannot get BaseAdapter");
            return;
        }
        ba.notifyDataSetChanged();
    }

    public static BaseAdapter getBaseAdapter(AbsListView absListView) {
        if (absListView == null) {
            LogUtils.w("cannot get AbsListView.");
            return null;
        }
        ListAdapter la = absListView.getAdapter();
        if (la instanceof HeaderViewListAdapter) {
            la = ((HeaderViewListAdapter) la).getWrappedAdapter();
        }
        return (BaseAdapter) la;
    }
}
