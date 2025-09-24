package leetcode;

import common.TreeNode;

/**
 * Created by sobercheg on 12/23/13. http://leetcode.com/2010/09/determine-if-binary-tree-is-binary.html
 */
public class TreeBSTTest {
    public static boolean isBSTRange(TreeNode node, int low, int high) {
        if (node == null) return true;
        if (node.data >= low && node.data <= high)
            return isBSTRange(node.left, low, node.data) && isBSTRange(node.right, node.data, high);
        else
            return false;
    }

    public static boolean isBSTInOrder(TreeNode node, int prev) {
        if (node == null) return true;
        boolean isLeftInOrder = isBSTInOrder(node.left, prev);
        if (prev > node.data) return false;
        prev = node.data;
        boolean isRightInOrder = isBSTInOrder(node.right, prev);
        return isLeftInOrder && isRightInOrder;
    }

    public static void main(String[] args) {
        TreeNode bst1 = new TreeNode(5, new TreeNode(2), new TreeNode(10));
        System.out.println(isBSTRange(bst1, Integer.MIN_VALUE, Integer.MAX_VALUE));
        System.out.println(isBSTInOrder(bst1, Integer.MIN_VALUE));

        TreeNode nonBst1 = new TreeNode(10,
                new TreeNode(5), new TreeNode(15,
                new TreeNode(6), new TreeNode(20)));

        System.out.println(isBSTRange(nonBst1, Integer.MIN_VALUE, Integer.MAX_VALUE));
        System.out.println(isBSTInOrder(nonBst1, Integer.MIN_VALUE));

    }

}
