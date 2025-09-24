package jswang.ValidBST;

/**
 * Created by willwjs on 2/21/15.
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

public class Solution {
    public boolean isValidBST(TreeNode root) {
        if (root == null) return true;
        return check(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean check(TreeNode root, long min, long max) {
        if (root == null) return true;
        if (root.val <= min || root.val >= max) return false;
        return check(root.left, min, root.val) && check(root.right, root.val, max);
    }

    public static void main (String[] args) {
        Solution sol = new Solution();
        TreeNode root = new TreeNode(1);
        TreeNode ln = new TreeNode(1);
        root.left = ln;
        System.out.println(sol.isValidBST(root));
    }
}