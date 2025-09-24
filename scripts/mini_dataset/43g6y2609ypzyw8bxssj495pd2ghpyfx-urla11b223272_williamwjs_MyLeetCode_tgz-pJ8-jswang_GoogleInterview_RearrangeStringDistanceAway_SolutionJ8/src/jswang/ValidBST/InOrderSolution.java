package jswang.ValidBST;

import java.util.ArrayList;

/**
 * Created by willwjs on 2/22/15.
 */
public class InOrderSolution {
    public boolean isValidBST(TreeNode root) {
        ArrayList<Integer> pre = new ArrayList<Integer>();
        pre.add(null);
        return helper(root, pre);
    }

    private boolean helper(TreeNode root, ArrayList<Integer> pre)
    {
        if(root == null)
            return true;
        boolean left = helper(root.left, pre);
        if(pre.get(0) != null && root.val <= pre.get(0))
            return false;
        pre.set(0, root.val);
        return left && helper(root.right, pre);
    }

    public static void main(String[] args) {
        InOrderSolution sol = new InOrderSolution();
        TreeNode root = new TreeNode(1);
        TreeNode ln = new TreeNode(1);
        root.left = ln;
        System.out.println(sol.isValidBST(root));
    }
}
