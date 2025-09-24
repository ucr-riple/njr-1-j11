package careercup.google;

/**
 * Created by Sobercheg on 11/17/13.
 */
public class Brackets {
    // from http://stackoverflow.com/questions/727707/finding-all-combinations-of-well-formed-brackets
    public void brackets(int n) {
        for (int i = 1; i <= n; i++) {
            brackets("", 0, 0, i);
        }
    }

    private void brackets(String output, int open, int close, int pairs) {
        if ((open == pairs) && (close == pairs)) {
            System.out.println(output);
        } else {
            if (open < pairs)
                brackets(output + "(", open + 1, close, pairs);
            if (close < open)
                brackets(output + ")", open, close + 1, pairs);
        }
    }

    public static void main(String[] args) {
        Brackets brackets = new Brackets();
        brackets.brackets(3);

    }
}
