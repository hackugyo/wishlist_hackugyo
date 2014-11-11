package com.github.kevinsawicki.wishlist;

import android.annotation.SuppressLint;
import android.content.Context;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文字列 ユーティリティクラス.
 * 
 * @author User
 */
@SuppressLint("DefaultLocale")
public final class StringUtils {
    /** 行頭、行末の BR タグにマッチするパターン. */
    private static final Pattern TRIMBRTAG_PATTERN = Pattern.compile("(^<br\\s*[/]*>|<br\\s*[/]*>$)", Pattern.CASE_INSENSITIVE);
    /** Solr クエリーでエスケープが必要な文字にマッチするパターン. */
    private static final Pattern SOLR_ESCAPE_PATTERN = Pattern.compile("[¥+\\-¥!¥(¥)¥{¥}\\[\\]¥^~¥*¥?:\"]|&{2}|\\|{2}|[\\\\]", Pattern.UNIX_LINES);
    /** 日時指定フォーマット */
    public static final String DATE_PATTERN = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_PATTERN_SHORT = "yyyy/MM/dd";

    /**
     * 文字列が空文字かどうかを取得します. NULL または、空文字の場合は true を、それ以外の場合は false を返します.
     * 
     * @param str
     *            判定する文字列
     * @return NULL または、空文字の場合は true
     */
    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() <= 0) return true;
        return false;
    }

    /**
     * 文字列から、全角・半角スペースを取り除きます。
     * 
     * @param str
     */
    public static String trimSpace(String str) {
        if (str == null) return null;

        return str.replaceAll("　| ", "");
    }

    /**
     * 文字列が空文字かどうかを取得します. NULL または、空文字の場合は false を、それ以外の場合は true を返します.
     * 
     * @param str
     *            判定する文字列
     * @return NULL または、空文字の場合は false
     */
    public static boolean isPresent(CharSequence str) {
        return !isEmpty(str);
    }

    /**
     * 行頭・行末のBRタグを削除します.
     * 
     * @param tag
     *            対象の文字列
     * @return タグ削除後の文字列
     */
    public static String trimBRTag(CharSequence tag) {
        if (tag == null) return null;
        Matcher matcher = TRIMBRTAG_PATTERN.matcher(tag);
        return matcher.replaceAll("");
    }

    /**
     * 先頭の文字を大文字に、それ以降を小文字にした文字列を返します.
     * 
     * @param str
     *            文字列
     * @return 先頭大文字、先頭以外小文字の文字列
     */
    @SuppressLint("DefaultLocale")
    public static String capitalize(CharSequence str) {
        if (isEmpty(str)) return null;
        String tmp = str.toString();
        return tmp.substring(0, 1).toUpperCase() + tmp.substring(1).toLowerCase();
    }

    /**
     * 先頭の文字を小文字にした文字列を返します.
     * 
     * @param str
     *            文字列
     * @return 先頭を小文字にした文字列
     */
    @SuppressLint("DefaultLocale")
    public static String uncapitalize(CharSequence str) {
        if (isEmpty(str)) return null;
        String tmp = str.toString();
        return tmp.substring(0, 1).toLowerCase() + tmp.substring(1);
    }

    /**
     * 文字列のリストを区切り文字で結合して返します.
     * 
     * @param parts
     *            文字列のリスト
     * @param separator
     *            区切り文字
     * @return 結合後の文字列
     */
    public static String join(List<String> parts, String separator) {
        StringBuilder builder = new StringBuilder();
        int index = 0;
        int length = parts.size() - 1;
        for (String str : parts) {
            builder.append(str);
            if (index < length) builder.append(separator);
            index++;
        }
        return builder.toString();
    }

    /**
     * Solrの特殊文字をエスケープして返します.
     * 
     * @param str
     *            クエリー文字列
     * @return エスケープ後の文字列
     */
    public static String escapeSolrQueryString(String str) {
        if (isEmpty(str)) return null;
        Matcher m = SOLR_ESCAPE_PATTERN.matcher(str);
        return m.replaceAll("\\\\$0");
    }

    /**
     * date型をString型に変換します．
     * 
     * @param date
     * @return 変換後の文字列
     */
    public static String getDateStr(final Context context, Date date) {
        return getDateStr(context, date, DATE_PATTERN);
    }

    /**
     * date型をString型に変換します．フォーマットが指定できます．
     * 
     * @param date
     * @param format
     * @return 変換後の文字列
     */
    public static String getDateStr(final Context context, Date date, String format) {
        final SimpleDateFormat sdf = new SimpleDateFormat(format, context.getResources().getConfiguration().locale);
        String term = sdf.format(date);
        return term;
    }

    /**
     * 文字列をjointで結合します．ただし左右どちらかが空文字列の場合，jointは使いません．
     * 
     * @param string1
     * @param string2
     * @param joint
     */
    public static String joinStrings(String string1, String string2, String joint) {
        if (string1 == null) string1 = "";
        if (string2 == null) string2 = "";

        if (StringUtils.isEmpty(string1) || StringUtils.isEmpty(string2)) joint = "";

        StringBuilder sb = new StringBuilder();
        sb.append(string1).append(joint).append(string2);
        return sb.toString();
    }

    /**
     * 文字列を結合します．ただし片方が空文字列の場合，空文字列を返します．
     * 
     * @param string1
     * @param string2
     */
    public static String concatStrings(String string1, String string2) {
        if (StringUtils.isEmpty(string1) || StringUtils.isEmpty(string2)) return "";
        return (string1 + string2);
    }

    /**
     * 文字列をjointで結合します．
     */
    public static String join(ArrayList<String> strings, String joint) {
        if (strings == null) return null;
        joint = StringUtils.nullToEmpty(joint);
        StringBuilder sb = new StringBuilder();
        for (String s : strings) {
            if (sb.length() > 0) sb.append(joint);
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * 与えられた文字列を，半角max文字以下に切り詰めます。切り捨て部分がある場合，"..."を末尾に追加します。
     * 
     * @param detail
     * @param max
     */
    public static String ellipsize(String detail, int max) {
        return ellipsize(detail, max, false);
    }

    /**
     * 与えられた文字列を，半角max文字以下に切り詰めます。切り捨て部分がある場合，"..."を末尾に追加します。
     * 
     * @param input
     * @param max
     * @param padLeft
     *            : trueの場合，末尾に空白を入れて，max文字ちょうどになるようにします．
     */
    public static String ellipsize(String input, int max, boolean padLeft) {
        if (input == null || input.length() < max) {
            return (padLeft ? padLeft(input, max) : input);
        }
        int charactersAfterEllipsis = 0;
        return input.substring(0, max - 3 - charactersAfterEllipsis) + "..." + input.substring(input.length() - charactersAfterEllipsis);
    }

    public static String ellipsizeMiddle(String input, int max, boolean padLeft) {
        if (input == null || input.length() < max) {
            return (padLeft ? padLeft(input, max) : input);
        }
        int charactersAfterEllipsis = max / 2;
        int charactersBeforeEllipsis = max - 3 - charactersAfterEllipsis;
        return input.substring(0, charactersBeforeEllipsis) + "..." + input.substring(input.length() - charactersAfterEllipsis);
    }

    public static String getCRLF() {
        return System.getProperty("line.separator");
    }

    public static boolean isHalfWidth(char c) {
        return (c <= '\u007e') || // 英数字
                (c == '\u00a5') || // \記号
                (c == '\u203e') || // ~記号
                (c >= '\uff61' && c <= '\uff9f'); // 半角カナ
    }

    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    public static String padZeros(int number, int length) {
        return String.format("%1$0" + length + "d", number);
    }

    /**
     * 与えられた引数が候補リストに含まれるかどうかを返します．
     * 
     * @param str
     * @param array
     */
    public static boolean isKnownByList(String str, String[] array) {
        return Arrays.asList(array).contains(str); // containsはequalsで比較するため，別インスタンスのStringも等しいものと判定される
    }

    /**
     * 与えられた文字列が，ホワイトリスト内にある文字列で始まるかかどうかを返します． リストが["aa", "ab"]のとき，"aab"はtrue,
     * "a"はfalse
     * 
     * @param str
     * @param whiteList
     */
    public static boolean isStartedByList(String str, ArrayList<String> whiteList) {
        for (String white : whiteList) {
            if (str.startsWith(white)) return true;
        }
        return false;
    }

    public static boolean isSame(String targ, String comp) {
        if (targ == null) {
            return comp == null;
        } else {
            return targ.equals(comp);
        }
    }

    /**
     * 与えられた文字列が，数値としてふさわしいかどうか判定します．
     * 
     * @return 桁数・最小・最大の制約を満たしたときtrue
     */
    public static boolean validateIntString(String dd, int length, int min, int max) {
        if (dd == null) return false;
        if (dd.length() != length) return false;
        int day;
        try {
            day = Integer.valueOf(dd);
        } catch (NumberFormatException e) {
            return false;
        }
        if (day < min || max < day) return false;
        return true;
    }

    /**
     * StringBuilderにnullを読ませると"null"という文字列になってしまうので，フィルタします．
     * 
     * @param text
     * @return String
     * @see com.google.common.base.Strings#nullToEmpty
     */
    public static String nullToEmpty(String text) {
        return (text == null ? "" : text);
    }

    public static String nullToEmpty(Double text) {
        return (text == null ? "" : String.valueOf(text));
    }

    /**
     * {@link String#valueOf(Object)}はnullを"null"に変えてしまうので，<br>
     * nullの場合はnullのまま返すメソッドを提供します．
     * 
     * @param object
     * @return String or null
     */
    public static String valueOf(Object object) {
        if (object == null) return null;
        return String.valueOf(object);
    }

    public static String map2String(Map<String, String> map) {
        if (map == null) return null;
        StringBuilder sb = new StringBuilder();
        Iterator<Entry<String, String>> itr = map.entrySet().iterator();
        while (itr.hasNext()) {
            Entry<String, String> entry = itr.next();
            if (sb.length() > 0) sb.append("&");
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.toString();
    }

    public static String build(CharSequence... strings) {
        StringBuilder sb = new StringBuilder();
        for (CharSequence s : strings) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static boolean isSame(CharSequence targ, CharSequence comp) {
        if (targ == null) {
            return comp == null;
        } else {
            return targ.equals(comp);
        }
    }

    /**
     * 金額を3桁区切りにして表示します．
     * 
     * @param amount
     * @return 3桁区切りの文字列
     */
    public static String toMoneyString(Integer amount) {
        if (amount == null) return "";
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(amount);
    }

    public static String toMoneyString(String amount) {
        if (amount == null) return "";
        return toMoneyString(Integer.valueOf(amount));
    }

    public static boolean areNotEmpty(String... strings) {
        if (strings == null) return false;
        for (String obj : strings) {
            if (obj == null) return false;
            if (obj.length() == 0) return false;
        }
        return true;
    }
}
