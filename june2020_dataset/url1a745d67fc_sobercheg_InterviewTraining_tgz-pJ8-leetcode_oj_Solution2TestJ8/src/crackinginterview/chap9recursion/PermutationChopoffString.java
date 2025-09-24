package crackinginterview.chap9recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sobercheg on 11/10/13.
 */
public class PermutationChopoffString {

    public List<String> permut(String str, int len) {

        // base case
        if (len == 1) return new ArrayList<String>(Arrays.asList(new String[]{str.substring(0, 1)}));

        // chop off last char and stick in into all positions

        List<String> prevPermut = permut(str, len - 1);
        char ch = str.charAt(len - 1);
        List<String> newPermut = new ArrayList<String>();

        for (String perm : prevPermut) {
            for (int i = 0; i < len; i++) {
                newPermut.add(perm.substring(0, i) + ch + perm.substring(i, len - 1));
            }
        }

        return newPermut;
    }

    public static void main(String[] args) {
        PermutationChopoffString permutation = new PermutationChopoffString();
        System.out.println(permutation.permut("abcd", 4));
    }
}
