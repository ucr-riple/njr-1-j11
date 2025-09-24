package jswang.GoogleInterview.TournamentTree;

/**
 * Created by willwjs on 10/12/15.
 * Tournament tree 找secMin;

 Tournament tree 的定义是parent 是孩子node的最小值， 如下例 return 5

        2
      /   \
   2        7
 /  \    | \
 5    2  8  7
 */
public class Solution {
    public int findSecondMin(TreeNode root) {
        if (root == null || root.left == null || root.right == null)
            throw new RuntimeException();
        return helper(root, root.val);
    }

    private int helper(TreeNode node, int firstMin) {
        if (node.val != firstMin)
            return node.val;
        if (node.left == null && node.right == null)
            return Integer.MAX_VALUE;
        int left = helper(node.left, firstMin);
        int right = helper(node.right, firstMin);
        return Math.min(left, right);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(1);
        TreeNode right = new TreeNode(3);
        root.left = left;
        root.right = right;
        TreeNode leftl = new TreeNode(1);
        TreeNode leftr = new TreeNode(2);
        left.left = leftl;
        left.right = leftr;
        TreeNode rightl = new TreeNode(3);
        TreeNode rightr = new TreeNode(4);
        right.left = rightl;
        right.right = rightr;
        System.out.println(new Solution().findSecondMin(root));
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}