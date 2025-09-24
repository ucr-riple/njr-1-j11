package mi.legacy.regex;

/**
 * User: goldolphin
 * Time: 2013-04-04 17:58
 */
public class Common {

    public static void main(String[] args) throws InterruptedException {
        Regex regex = new Regex("(ab|a)c$");
        regex.dump();
        Match match = regex.match("abc");
        match.dump();

        match = regex.match("aabc");
        match.dump();

        match = regex.search("aabc");
        match.dump();

        match = regex.match("abcd");
        match.dump();

        System.out.println();
        Thread.sleep(100);
        regex = new Regex("ab*\\0");
        regex.dump();
    }
}
