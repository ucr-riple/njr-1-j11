package jswang.LargestNumber;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by willwjs on 5/12/15.
 * 1.通过交换比较大小,重新定义比较器
 * 2.一定要注意越界问题
 * 3.注意数字不能以0开头
 */
public class Solution {
    public String largestNumber(int[] nums) {
        String[] strings = new String[nums.length];
        for (int i = 0; i < nums.length; ++i)
            strings[i] = "" + nums[i];
        Arrays.sort(strings, new cmp()); // 1
        String res = "";
        for (String i : strings)
            res += i;
        int index = 0;
        while(index < res.length() && res.charAt(index) == '0') index++; // 2
        if (index == res.length()) return "0"; // 3
        return res.substring(index, res.length());
    }

    public static void main(String[] args) {
        System.out.println(new Solution().largestNumber(new int[]{0, 0}));
    }
}

class cmp implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        String c1 = o1 + o2;
        String c2 = o2 + o1;
        return c2.compareTo(c1);
    }
}