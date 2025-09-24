package jswang.PermutationString;

/**
 * Created by willwjs on 2/25/15.
 */
public class Solution {
    public void permute(String input) {
        int inputLength = input.length();
        boolean[] used = new boolean[inputLength];
        StringBuffer outputString = new StringBuffer();
        char[] in = input.toCharArray();

        doPermute(in, outputString, used, inputLength, 0);

    }

    private void doPermute(char[] in, StringBuffer outputString,
                     boolean[] used, int inputLength, int level) {
        if (level == inputLength) {
            System.out.println(outputString.toString());
            return;
        }

        for (int i = 0; i < inputLength; ++i) {
            if(used[i]) continue;

            outputString.append(in[i]);
            used[i] = true;
            doPermute(in, outputString, used, inputLength, level + 1);
            used[i] = false;
            outputString.setLength(outputString.length() - 1);
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        sol.permute("dog");
    }
}
