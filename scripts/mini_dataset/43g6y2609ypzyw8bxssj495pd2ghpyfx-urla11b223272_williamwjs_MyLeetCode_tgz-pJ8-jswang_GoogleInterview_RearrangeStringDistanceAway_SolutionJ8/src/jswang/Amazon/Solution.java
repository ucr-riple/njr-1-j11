package jswang.Amazon;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by willwjs on 2/7/15.
 */
public class Solution {

    public static void main (String[] args) {
        Set set = new HashSet();
        Random random = new Random();
        while (set.size() != 900) {
            int num = random.nextInt(1000) + 1;
            if (!set.contains(num)) {
                set.add(num);
            }
        }
        System.out.print(set);
    }
}
