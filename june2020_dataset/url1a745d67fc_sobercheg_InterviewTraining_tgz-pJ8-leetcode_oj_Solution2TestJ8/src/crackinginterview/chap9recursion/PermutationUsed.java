package crackinginterview.chap9recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sobercheg on 11/10/13.
 */
public class PermutationUsed {

    public void permut(char[] in, char[] out, boolean[] used, int level, List<String> perms) {

        if (level == in.length) {
            perms.add(new String(out));
            return;
        }

        for (int i = 0; i < in.length; i++) {
            if (used[i]) continue;
            out[level] = in[i];
            used[i] = true;
            permut(in, out, used, level + 1, perms);
            used[i] = false;
        }
    }


    public List<String> permut(String in) {
        List<String> permuts = new ArrayList<String>();
        permut(in.toCharArray(), new char[in.length()], new boolean[in.length()], 0, permuts);
        return permuts;
    }

    public static void main(String[] args) {
        PermutationUsed permutation = new PermutationUsed();
        System.out.println(permutation.permut("abcd"));
    }
}
