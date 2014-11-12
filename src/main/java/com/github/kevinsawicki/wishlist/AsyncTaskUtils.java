package com.github.kevinsawicki.wishlist;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;

import java.util.concurrent.RejectedExecutionException;

/**
 * 
 * @author hackugyo
 * 
 */
public class AsyncTaskUtils {

    /**
     * {@link AsyncTask#execute(Object...)}は，API level 11以降ではシリアルに実行されます．<br>
     * （複数個登録した場合，1つ終わらないと次が開始されない）<br>
     * このメソッドを介して呼んだ場合，パラレルに実行されます．<br>
     * 
     * @param task
     * @param params
     * @see <a
     *      href="http://daichan4649.hatenablog.jp/entry/20120125/1327467103">参考ページ</a>
     */
    @SuppressLint("NewApi")
    public static <T> void executeTask(AsyncTask<T, ?, ?> task, T... params) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
            } else {
                task.execute(params);
            }
        } catch (RejectedExecutionException e) {
            LogUtils.d("Pool is full. Retrying...");
            executeTask(task, params);
        } catch (IllegalStateException e) {
            LogUtils.w("This Task may be already running. " + e.toString());
        }
    }
}
