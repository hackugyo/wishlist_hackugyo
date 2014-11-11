package com.github.kevinsawicki.wishlist;

import java.math.BigDecimal;

/**
 * 数字 ユーティリティクラス.
 * 
 * @author User
 */
public final class MathUtils {

    /**
     * 設定されたスケールまでで，小数点以下を切り捨てます．
     * 
     * @param value
     * @param newScale
     *            scale of the result returned.
     * @return 小数点newScale桁以下を切り捨てたdouble
     */
    public static Double floor(Double value, int newScale) {
        return BigDecimal.valueOf(value).setScale(newScale, BigDecimal.ROUND_FLOOR).doubleValue();
    }

    /**
     * 上digists桁までで切り捨てます．
     * 
     * @param target
     * @param digits
     */
    public static Long cutDownWithoutUpperDigits(Long target, int digits) {
        Long result = Long.valueOf(target);
        int place = result.toString().length();

        if (place <= digits) return result;
        BigDecimal d = BigDecimal.valueOf(result / Math.pow(10, place - digits));
        d = d.setScale(0, BigDecimal.ROUND_HALF_UP);
        result = (long) (d.intValue() * Math.pow(10, place - digits));
        return result;
    }

    /**
     * 最大桁数が 1〜6桁 そのまま 7〜9桁 3桁削除 10〜12桁 6桁削除 13桁 9桁削除
     * 
     * @param target
     */
    public static Long trimDigits(long target, int maxDigits) {
        return (long) (target / getRoundedDownInFactorialOfThousands(maxDigits));
    }

    public static int getRatio(Long target) {
        int len = new StringBuilder().append(Math.abs(target)).length();
        int ratio = getRoundedDownInFactorialOfThousands(len).intValue();
        return ratio;
    }

    /**
     * 数値の桁数から，1000の階乗に2段階切り下げます． たとえば，2,000,000（7桁）の場合，2段階切り下げて，1000を返します．
     * 
     * @param len
     * @return 1,000の階乗
     */
    private static Double getRoundedDownInFactorialOfThousands(int len) {
        if (len <= 6) return 1d; // 実際はmaxDigits <= 3でもOK
        return Math.pow(1000, (len - 4) / 3);
    }
}
