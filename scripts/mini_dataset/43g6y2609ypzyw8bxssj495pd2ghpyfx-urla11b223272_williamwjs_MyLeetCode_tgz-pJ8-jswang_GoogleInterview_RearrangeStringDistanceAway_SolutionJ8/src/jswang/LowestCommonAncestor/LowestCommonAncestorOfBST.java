package jswang.LowestCommonAncestor;

/**
 * Created by willwjs on 7/20/15.
 */
public class LowestCommonAncestorOfBST {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (p.val > q.val) {
            return lowestCommonAncestor(root, q, p);
        }
        if (p.val == root.val) {
            return p;
        }
        if (q.val == root.val) {
            return q;
        }
        if (p. val < root.val && root.val < q.val) {
            return root;
        }
        if (q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        }
        if (p.val > root.val) {
            return lowestCommonAncestor(root.right, p, q);
        }
        return null;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        TreeNode left = new TreeNode(1);
        TreeNode right = new TreeNode(3);
        root.left = left;
        root.right = right;
        System.out.println(new LowestCommonAncestorOfBST().lowestCommonAncestor(root, left, right).val);
    }
}