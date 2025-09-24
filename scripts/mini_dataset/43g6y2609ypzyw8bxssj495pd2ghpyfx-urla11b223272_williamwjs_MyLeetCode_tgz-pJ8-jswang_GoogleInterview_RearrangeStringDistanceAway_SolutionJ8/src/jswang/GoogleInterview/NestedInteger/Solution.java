package jswang.GoogleInterview.NestedInteger;

import java.util.List;

/**
 * Created by willwjs on 10/13/15.
 * 计算sum = sum(value * level)
 */
public class Solution {
    public int depthSum (List<NestedInteger> input) {
        return getSum(input, 1);
    }

    private int getSum(List<NestedInteger> input, int level) {
        if (input.size() == 0) return 0;
        int sum = 0;
        for (NestedInteger n : input) {
            if (n.isInteger()) {
                sum += n.getInteger() * level;
            } else {
                sum += getSum(n.getList(), level + 1);
            }
        }
        return sum;
    }
}
