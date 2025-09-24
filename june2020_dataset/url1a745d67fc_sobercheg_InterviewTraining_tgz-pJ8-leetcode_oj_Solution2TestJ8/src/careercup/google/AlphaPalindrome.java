package careercup.google;

/**
 * Created by Sobercheg on 12/11/13.
 * <p/>
 * The class provides functionality to say if a given string is a palindrome ignoring non-alphanumeric characters.
 * Examples:
 * #ab___b!a - true
 * #a#b##a - true
 * ba - false
 * #a# - true
 * #! - false
 */
public class AlphaPalindrome {

    public boolean isPalindrome(String str) {
        if (str == null || str.isEmpty()) return false;

        int leftIndex = -1;
        int rightIndex = str.length();
        boolean hasGoodChars = false;

        while (true) {
            leftIndex = findNextGoodChar(str, leftIndex + 1);
            rightIndex = findPreviousGoodChar(str, rightIndex - 1);

            // one of indices reached an end of the string: not a palindrome
            if (leftIndex * rightIndex < 0) return false;

            // no more good chars to scan: palindrome OR no alphanumeric chars at all
            if (leftIndex < 0 && rightIndex < 0) {
                return hasGoodChars;
            }

            // next and previous "good" characters are not equal: NOT a palindrome
            if (str.charAt(leftIndex) != str.charAt(rightIndex)) {
                return false;
            } else {
                hasGoodChars = true;
            }
        }
    }

    private int findNextGoodChar(String str, int leftIndex) {
        for (int i = leftIndex; i < str.length(); i++) {
            if (isGoodChar(str.charAt(i))) return i;
        }

        return -1;
    }

    private int findPreviousGoodChar(String str, int rightIndex) {
        for (int i = rightIndex; i >= 0; i--) {
            if (isGoodChar(str.charAt(i))) return i;
        }

        return -1;
    }

    private boolean isGoodChar(char ch) {
        if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'z')) return true;
        return false;
    }

    public static void main(String[] args) {
        AlphaPalindrome palindrome = new AlphaPalindrome();

        assertEquals("", palindrome.isPalindrome("#ab__b!a"), true);
        assertEquals("", palindrome.isPalindrome("#a#b##a"), true);
        assertEquals("", palindrome.isPalindrome("ba"), false);
        assertEquals("", palindrome.isPalindrome("#a#"), true);
        assertEquals("", palindrome.isPalindrome("a"), true);
        assertEquals("", palindrome.isPalindrome("#"), false);
    }

    private static void assertEquals(String message, boolean expected, boolean actual) {
        if (expected != actual) throw new IllegalStateException(message);
    }
}
