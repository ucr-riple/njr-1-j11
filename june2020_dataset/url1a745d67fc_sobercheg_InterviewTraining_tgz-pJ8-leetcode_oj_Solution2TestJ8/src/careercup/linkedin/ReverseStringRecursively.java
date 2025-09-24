package careercup.linkedin;

/**
 * Created by Sobercheg on 12/17/13.
 */
public class ReverseStringRecursively {

    public String reverse(String str) {
        if (str == null) throw new IllegalArgumentException("Cannot reverse null");

        // base case
        if (str.length() <= 1) return str;

        char start = str.charAt(0);
        char end = str.charAt(str.length() - 1);
        String middle = str.substring(1, str.length() - 1);
        return end + reverse(middle) + start;
    }

    public static void main(String[] args) {
        System.out.println(new ReverseStringRecursively().reverse("a"));
        System.out.println(new ReverseStringRecursively().reverse("ab"));
        System.out.println(new ReverseStringRecursively().reverse("My mathematics"));

    }
}
