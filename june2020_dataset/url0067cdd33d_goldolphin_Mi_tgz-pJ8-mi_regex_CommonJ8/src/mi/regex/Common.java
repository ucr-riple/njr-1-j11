package mi.regex;

import java.util.regex.Pattern;

/**
 * User: goldolphin
 * Time: 2013-04-04 17:58
 */
public class Common {

    public static void main(String[] args) throws InterruptedException {
        verify(Pattern.compile("^(a|b)+$").matcher("abb").matches());

        Regex regex = new Regex("(ab|a)c");
        regex.dump();
        System.out.println();

        verify(testMatch(new Regex("(ab|a)c"), "abc"));

        verify(!testMatch(new Regex("(ab|a)c"), "aabc"));

        verify(!testMatch(new Regex("(ab|a)c"), "abcd"));

        verify(testStartWith(new Regex("(ab|a)c"), "abcd"));

        regex = new Regex("a+$");
        regex.dump();
        System.out.println();

        verify(testStartWith(new Regex("(a|b)+$"), "a"));

        verify(testStartWith(new Regex("(a|b)+$"), "ab"));

        verify(!testStartWith(new Regex("(a|b)\1*$"), "abb"));

        System.out.println();
        Thread.sleep(100);
        try {
            regex = new Regex("ab*\\0");
            regex.dump();
            verify(false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println();
        }

        verify(testStartWith(new Regex("[1-3]"), "2"));
        verify(!testStartWith(new Regex("[1-3]"), "4"));
        verify(!testStartWith(new Regex("[^1-3]"), "2"));
        verify(testStartWith(new Regex("[^1-3]{2,4}"), "4^"));
        new Regex("[^1-3a]{2,4}").dump();
    }

    static boolean testMatch(Regex regex, String text) {
        System.out.println(text);
        Match match = regex.match(text);
        match.dump();
        System.out.println();
        return match.succeed();
    }

    static boolean testStartWith(Regex regex, String text) {
        System.out.println(text);
        Match match = regex.startWith(text);
        match.dump();
        System.out.println();
        return match.succeed();
    }

    static void verify(boolean condition) {
        if (!condition) {
            throw new RuntimeException("Verification fails.");
        }
    }
}
