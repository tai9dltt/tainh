package jp.co.ne.cardreader.util;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Checker util
 */
public class CH {

    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    // Only character and digital
    private static final String CHAR_NUM_REGEX = "^[a-zA-Z0-9_]*";

    private static final Pattern CHAR_NUM_PATTERN = Pattern.compile(CHAR_NUM_REGEX);

    // Only half width character
    private static final String NOT_ONLY_HALF_WIDTH_REGEX = ".*[^\\x01-\\x7E].*";

    private static final Pattern NOT_ONLY_HALF_WIDTH_PATTERN = Pattern.compile(NOT_ONLY_HALF_WIDTH_REGEX);


    /**
     * Check list empty
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> boolean isNullOrEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    /**
     * Check string empty
     *
     * @param str
     * @return
     */
    public static <T> boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Check string null or blank
     *
     * @param str
     * @return
     */
    public static <T> boolean isNullOrBlank(String str) {
        return str == null || str.isEmpty() || str.trim().isEmpty();
    }

    /**
     * Check if the given string can be an email address
     *
     * @param str
     * @return
     */
    public static boolean isValidEmail(String str) {
        // Ref. https://howtodoinjava.com/regex/java-regex-validate-email-address/
        return EMAIL_PATTERN.matcher(str).matches();
    }

    /**
     * Check if the given string can be an employee ID
     *
     * @param str
     * @return
     */
    public static boolean isValidAlphaNumeric(String str) {
        // Ref. https://howtodoinjava.com/regex/java-regex-validate-email-address/
        return CHAR_NUM_PATTERN.matcher(str).matches();
    }

    /**
     * Check if the string contains only half width characters
     *
     * @param str
     * @return
     */
    public static boolean isHalfWidth(String str) {
        return !NOT_ONLY_HALF_WIDTH_PATTERN.matcher(str).matches();
    }

    /**
     * Check if 2 strings have the same content (including null check, and eq(null, null) is considered false)
     *
     * @param s1
     * @param s2
     * @return
     */
    public static boolean eq(String s1, String s2) {
        return s1 != null && s1.equals(s2);
    }
}
