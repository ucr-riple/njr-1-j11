package jswang.BinaryTreeRightSideView;

/**
 * Created by willwjs on 5/12/15.
 */

import java.util.LinkedList;
import java.util.List;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

public class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> list = new LinkedList<Integer>();
        if (root == null) return list;
        list.add(root.val);
        findRightWithHeight(list, root, 1);
        return list;
    }

    private void findRightWithHeight(List<Integer> list, TreeNode node, int height) {
        ++height;
        if (node.right != null) {
            if (list.size() < height)
                list.add(node.right.val);
            findRightWithHeight(list, node.right, height);
        }
        if (node.left != null) {
            if (list.size() < height)
                list.add((node.left.val));
            findRightWithHeight(list, node.left, height);
        }
    }
}