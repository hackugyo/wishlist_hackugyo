package com.github.kevinsawicki.wishlist;

import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayUtils {

    /**
     * ArrayListインスタンスそのものは変更せず，中身だけをinitialArrayで完全に上書きします．
     * 
     * @param targetArray
     * @param initialArray
     * @return targetArray
     */
    public static <T> ArrayList<T> initArrayListWith(ArrayList<T> targetArray, ArrayList<T> initialArray) {
        if (targetArray == null) {
            targetArray = new ArrayList<T>();
        }
        // 同じインスタンスだった場合，前者をclearしてから後者を入れると，けっきょくぜんぶclearされてしまうので．
        if (targetArray == initialArray) return targetArray;

        targetArray.clear();
        if (initialArray != null) Iterables.addAll(targetArray, initialArray);
        return targetArray;
    }

    /**
     * 第1引数に第2引数を連結します．第1引数は破壊的に更新されます（インスタンスの参照を変えません）．
     * 
     * @param targetArray
     * @param initialArray
     * @return 第1引数と同じインスタンス
     */
    public static <T> List<T> concatList(List<T> targetArray, List<T> initialArray) {
        if (targetArray == null) {
            targetArray = new ArrayList<T>();
        }
        if (initialArray != null) Iterables.addAll(targetArray, initialArray);
        return targetArray;
    }

    /**
     * 
     * @param first
     * @param second
     * @return 結合した配列（引数の両方ともnullでない場合のみ）．どちらか片方でもnullであれば，null
     */
    public static <T> T[] concat(T[] first, T[] second) {
        if (first == null || second == null) return null;
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static boolean containTrue(boolean[] array) {
        for (boolean value : array) {
            if (value) return true;
        }
        return false;
    }

    /**
     * {@link Arrays#asList(Object...)}にint[]を渡した場合，期待したとおりのArrayListにならないので，<br>
     * 代わりにこのメソッドを使ってください．
     * 
     * @see <a
     *      href="http://d.hatena.ne.jp/idesaku/20081021/1224600600">参考ページ</a>
     * @param intArray
     * @return ArrayList
     */
    public static ArrayList<Integer> asList(int[] intArray) {
        if (intArray == null) return null;
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i : intArray) {
            list.add(i);
        }
        return list;
    }

    /**
     * {@link Arrays#asList(Object...)}にlong[]を渡した場合，期待したとおりのArrayListにならないので，<br>
     * 代わりにこのメソッドを使ってください．
     * 
     * @see <a
     *      href="http://d.hatena.ne.jp/idesaku/20081021/1224600600">参考ページ</a>
     * @param longArray
     * @return ArrayList
     */
    public static ArrayList<Long> asList(long[] longArray) {
        if (longArray == null) return null;
        ArrayList<Long> list = new ArrayList<Long>();
        for (long i : longArray) {
            list.add(i);
        }
        return list;
    }

    /**
     * {@link Arrays#asList(Object...)}にdouble[]を渡した場合，期待したとおりのArrayListにならないので，<br>
     * 代わりにこのメソッドを使ってください．
     * 
     * @see <a
     *      href="http://d.hatena.ne.jp/idesaku/20081021/1224600600">参考ページ</a>
     * @param doubleArray
     * @return ArrayList
     */
    public static ArrayList<Double> asList(double[] doubleArray) {
        if (doubleArray == null) return null;
        ArrayList<Double> list = new ArrayList<Double>();
        for (double i : doubleArray) {
            list.add(i);
        }
        return list;
    }

    /**
     * {@link Arrays#asList(Object...)}にfloat[]を渡した場合，期待したとおりのArrayListにならないので，<br>
     * 代わりにこのメソッドを使ってください．
     * 
     * @see <a
     *      href="http://d.hatena.ne.jp/idesaku/20081021/1224600600">参考ページ</a>
     * @param floatArray
     * @return ArrayList
     */
    public static ArrayList<Float> asList(float[] floatArray) {
        if (floatArray == null) return null;
        ArrayList<Float> list = new ArrayList<Float>();
        for (float i : floatArray) {
            list.add(i);
        }
        return list;
    }

    /**
     * {@link Arrays#asList(Object...)}
     * にboolean[]を渡した場合，期待したとおりのArrayListにならないので，<br>
     * 代わりにこのメソッドを使ってください．
     * 
     * @see <a
     *      href="http://d.hatena.ne.jp/idesaku/20081021/1224600600">参考ページ</a>
     * @param booleanArray
     * @return ArrayList
     */
    public static ArrayList<Boolean> asList(boolean[] booleanArray) {
        if (booleanArray == null) return null;
        ArrayList<Boolean> list = new ArrayList<Boolean>();
        for (boolean i : booleanArray) {
            list.add(i);
        }
        return list;
    }

    /**
     * {@link ArrayUtils#asList(int[])}を定義したので，可変長引数の
     * {@link Arrays#asList(Object...)} を使わずにすむよう，<br>
     * 同内容メソッドを定義しています．
     * 
     * @param array
     * @return ArrayList
     */
    public static <T> ArrayList<T> asList(T[] array) {
        if (array == null) return null;
        return new ArrayList<T>(Arrays.asList(array));
    }

    /**
     * Arrayの末尾にaddします
     * @param current
     * @param queueId
     * @return addされた新たなArray
     */
    public static <T> T[] add(T[] current, T queueId) {
        ArrayList<T> asList = asList(current);
        asList.add(queueId);
        T[] result = current.clone();
        return asList.toArray(result);
    }
    

    public static <T> T[] remove(T[] current, T queueId) {
        ArrayList<T> asList = asList(current);
        asList.remove(queueId);
        T[] result = current.clone();
        return asList.toArray(result);
    }

    /**
     * リストがnullまたは空であるか判定します。
     * @param list
     * @return
     */
    public static boolean isEmpty(List<?> list) {
        return (list == null || list.isEmpty());
    }

    /**
     * リストがnullまたは空でないことを判定します。
     * @param list
     * @return
     */
    public static boolean any(List<?> list) {
        return (list != null && !list.isEmpty());
    }



}
