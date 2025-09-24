package jswang.CountCompleteTreeNodes;

/**
 * Created by willwjs on 6/8/15.
 * 如果从某节点一直向左的高度 = 一直向右的高度, 那么以该节点为root的子树一定是满二叉树.
 * 而满二叉树的节点数,可以用公式算出2^h - 1.
 * 如果高度不相等,
 * 则递归调用 return countNode(left) + countNode(right) + 1.
 * 复杂度为O(h^2)
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

public class Solution {
    public int countNodes(TreeNode root) {
        if (root == null)
            return 0;
        int height = leftHeight(root);
        if (height == rightHeight(root))
            return (int) (Math.pow(2, height) - 1);
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    private int leftHeight(TreeNode root) {
        int height = 1;
        while(root.left != null) {
            root = root.left;
            height++;
        }
        return height;
    }

    private int rightHeight(TreeNode root) {
        int height = 1;
        while(root.right != null) {
            root = root.right;
            height++;
        }
        return height;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode tmp = new TreeNode(2);
        root.left = tmp;
        System.out.print(new Solution().countNodes(root));
    }
}