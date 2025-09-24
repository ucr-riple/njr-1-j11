package leetcode;

import common.TreeNode;

/**
 * Created by sobercheg on 12/23/13.
 * http://leetcode.com/2010/09/saving-binary-search-tree-to-file.html
 */
public class BSTFromPreorder {

    public static TreeNode buildBSTFromPreorder(int low, int high, int[] inorder, int[] index) {
        if (index[0] >= inorder.length) return null;
        int nextValue = inorder[index[0]];
        if (nextValue < low || nextValue > high) return null;

        TreeNode node = new TreeNode(nextValue);
        index[0]++;
        node.left = buildBSTFromPreorder(low, nextValue, inorder, index);
        node.right = buildBSTFromPreorder(nextValue, high, inorder, index);

        return node;
    }

    public static void main(String[] args) {
        int[] preorder = {30, 20, 10, 40, 35, 50};
        int[] index = {0};

        TreeNode node = buildBSTFromPreorder(Integer.MIN_VALUE, Integer.MAX_VALUE, preorder, index);
        TreeLevelByLevel.printWithSpaces(node);
    }
}
