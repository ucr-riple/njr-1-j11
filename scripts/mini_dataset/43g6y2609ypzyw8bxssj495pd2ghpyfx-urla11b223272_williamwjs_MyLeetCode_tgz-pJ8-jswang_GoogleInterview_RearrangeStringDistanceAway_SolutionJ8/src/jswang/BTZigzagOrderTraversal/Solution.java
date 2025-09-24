package jswang.BTZigzagOrderTraversal;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by willwjs on 2/23/15.
 */

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

public class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> lists = new LinkedList<List<Integer>>();
        if (root == null)
            return lists;
        List<Integer> list;

        LinkedList<TreeNode> back_in_back_out = new LinkedList<TreeNode>();
        LinkedList<TreeNode> front_in_front_out = new LinkedList<TreeNode>();

        front_in_front_out.addFirst(root);
        while (!back_in_back_out.isEmpty() || !front_in_front_out.isEmpty()) {
            if (!front_in_front_out.isEmpty()) {
                list = new LinkedList<Integer>();
                while (!front_in_front_out.isEmpty()) {
                    TreeNode tmp = front_in_front_out.getFirst();
                    front_in_front_out.removeFirst();
                    list.add(tmp.val);
                    if (tmp.left != null)
                        back_in_back_out.addLast(tmp.left);
                    if (tmp.right != null)
                        back_in_back_out.addLast(tmp.right);
                }
                lists.add(list);
            }

            if (!back_in_back_out.isEmpty()) {
                list = new LinkedList<Integer>();
                while (!back_in_back_out.isEmpty()) {
                    TreeNode tmp = back_in_back_out.getLast();
                    back_in_back_out.removeLast();
                    list.add(tmp.val);
                    if (tmp.right != null)
                        front_in_front_out.addFirst(tmp.right);
                    if (tmp.left != null)
                        front_in_front_out.addFirst(tmp.left);
                }
                lists.add(list);
            }
        }
        return lists;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        List<List<Integer>> lists = sol.zigzagLevelOrder(new TreeNode(1));
    }
}
