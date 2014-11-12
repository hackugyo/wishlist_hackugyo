package com.github.kevinsawicki.wishlist;

import android.util.Log;

import timber.log.Timber;

/**
 * Log出力クラス. android:debuggable が false の場合はログを出力しない
 * 基本的に、アプリケーションでのログ出力はこのクラスを使う
 * 
 * @author User
 */
public final class LogUtils {

    private static final int DEPTH = 3;

    /**
     * デバッグログを出力します.
     * 
     * @param msg
     *            デバッグログ
     */
    public static void d(CharSequence msg) {
        Timber.d(getLogForm(Thread.currentThread().getStackTrace()) + msg);
    }

    public static void i(CharSequence msg) {
        Timber.i(getLogForm(Thread.currentThread().getStackTrace()) + msg);
    }

    /**
     * エラーログを出力します.
     * 
     * @param msg
     *            エラーログ
     */
    public static void e(CharSequence msg) {
        Timber.e(getLogForm(Thread.currentThread().getStackTrace()) + msg);
    }


    /**
     * エラーログを出力します.
     * 
     * @param msg
     *            エラーログ
     * @param e
     *            例外
     */
    public static void e(CharSequence msg, Throwable e) {
        Timber.e(e,  getLogForm(Thread.currentThread().getStackTrace()) + msg);
    }

    /**
     * ワーニングログを出力します.
     * 
     * @param msg
     *            ワーニングログ
     */
    public static void w(CharSequence msg) {
        Timber.w(getLogForm(Thread.currentThread().getStackTrace()) + msg);
    }

    /**
     * Verboseログを出力します.
     * 
     * @param msg
     *            Verboseログ
     */
    public static void v(CharSequence msg) {
        Timber.v(getLogForm(Thread.currentThread().getStackTrace()) + msg);
    }

    /**
     * ログ出力した箇所のメソッド情報に加え，そのメソッドを呼び出したメソッドの情報も表示します．
     * 
     * @param logLevel
     * @param maxSteps
     * @param msg
     */
    public static void withCaller(int logLevel, int maxSteps, CharSequence msg) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        if (stackTrace.length <= DEPTH || stackTrace == null) return;
        String message = getLogForm(stackTrace[3]) + msg;
        maxSteps = Math.min(DEPTH + maxSteps, stackTrace.length - 1);
        for (int step = (DEPTH +1); step <= maxSteps; step++) {
            message = StringUtils.build(//
                    message, StringUtils.getCRLF(),//
                    "at ", getLogForm(stackTrace[step])//
                    );
        }
        switch (logLevel) {
            case Log.VERBOSE:
                Timber.v(message);
                break;
            case Log.DEBUG:
                Timber.d(message);
                break;
            case Log.INFO:
                Timber.i(message);
                break;
            case Log.WARN:
                Timber.w(message);
                break;
            case Log.ERROR:
                Timber.e(message);
                break;
            default:
                break;
        }
    }

    /**
     * ログのヘッダ情報を整形します.
     * 
     * @param elements
     *            実行中のメソッド情報
     * @return ヘッダ情報
     */
    private static String getLogForm(StackTraceElement[] elements) {
        if (elements.length <= DEPTH || elements == null) return "";
        return getLogForm(elements[DEPTH]);
    }

    private static String getLogForm(StackTraceElement element) {

        StringBuilder sb = new StringBuilder();
        try {
            String file = element.getFileName();
            String method = element.getMethodName();
            int line = element.getLineNumber();
            sb.append(StringUtils.ellipsizeMiddle(file.replace(".java", ""), 25, true));
            sb.append("#").append(StringUtils.ellipsize(method, 18, true));
            sb.append("() [").append(String.format("%1$04d", line)).append("] : ");
        } catch (NullPointerException ignore) {
            // ignore. return blank string.
            // リリースビルドでは，elements[3]がnullになるようなので，ここで握りつぶしておく．
        }
        return sb.toString();
    }
}
