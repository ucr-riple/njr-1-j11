package jswang.GoogleInterview.NestedInteger;

import java.util.List;

/**
 * Created by willwjs on 10/13/15.
 * reverse sum
 第一题的第一层level是1，此题第一题的level是最大level，然后每层递减。
 */
public class Step2Solution {
    public int reverseSum(List<NestedInteger> input) {
        return getSum(input, getDepth(input));
    }

    private int getDepth(List<NestedInteger> input) {
        int depth = 0;
        for (NestedInteger n : input) {
            if (!n.isInteger()) {
                depth = Math.max(depth, getDepth(n.getList()));
            }
        }
        return depth + 1;
    }

    private int getSum(List<NestedInteger> input, int level) {
        if (input.size() == 0) return 0;
        int sum = 0;
        for (NestedInteger n : input) {
            if (n.isInteger()) {
                sum += n.getInteger() * level;
            } else {
                sum += getSum(n.getList(), level - 1);
            }
        }
        return sum;
    }
}
