package jswang.TwoSum;

import java.util.HashMap;

/**
 * Created by willwjs on 11/15/14.
 * 可以简化去掉注释掉的部分
 */
public class Solution {
    public int[] twoSum(int[] numbers, int target) {
        HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
        /*for (int i = 0; i < numbers.length; i++) {
            hashMap.put(numbers[i], i);
        }*/

        for (int i = 0; i < numbers.length; i++) {
            if (hashMap.containsKey(target - numbers[i])) {
                return new int[] {hashMap.get(target - numbers[i]) + 1, i + 1};
            }
            hashMap.put(numbers[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    public static void main (String[] args) {
        int[] num = {3, 2, 4};
        Solution sol = new Solution();
        for (int i : sol.twoSum(num, 6))
            System.out.print(i + " ");
        //System.out.println(sol.twoSum(num, 6)[0] + " " + sol.twoSum(num, 6)[1]);
    }
}
