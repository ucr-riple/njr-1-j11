package jswang.GoogleInterview.NestedInteger;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by willwjs on 10/13/15.
 * level 遍历输出。。。跟二叉树的levelOrderOutput一样。
 */
public class Step3Solution {
    public List<List<Integer>> levelOrder(List<NestedInteger> input) {
        LinkedList<List<NestedInteger>> queue = new LinkedList<>();
        LinkedList<List<NestedInteger>> nextQueue = new LinkedList<>();
        List<List<Integer>> lists = new LinkedList<>();
        queue.addLast(input);
        while (!queue.isEmpty()) {
            List<Integer> list = new LinkedList<>();
            while (!queue.isEmpty()) {
                List<NestedInteger> cur = queue.removeFirst();
                for (NestedInteger n : cur) {
                    if (n.isInteger()) {
                        list.add(n.getInteger());
                    } else {
                        nextQueue.addLast(n.getList());
                    }
                }
            }
            lists.add(list);
            LinkedList<List<NestedInteger>> tmp = queue;
            queue = nextQueue;
            nextQueue = queue;
        }
        return lists;
    }
}
