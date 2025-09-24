package jswang.BTPreorder;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by JiashuoWang on 10/15/14.
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

class SolutionStack {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> rs = new LinkedList<Integer>();
        Stack<TreeNode> st = new Stack<TreeNode>();

        if (root == null) return rs;
        else st.push(root);
        while (!st.isEmpty()) {
            TreeNode tmp = st.pop();
            rs.add(tmp.val);
            if (tmp.right != null) st.push(tmp.right);
            if (tmp.left != null) st.push(tmp.left);
        }

        return rs;
    }
}

//Recursive
public class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> rs = new LinkedList<Integer>();
        if (root == null) return rs;
        rs.add(root.val);
        rs.addAll(preorderTraversal(root.left));
        rs.addAll(preorderTraversal(root.right));
        return rs;
    }
}
