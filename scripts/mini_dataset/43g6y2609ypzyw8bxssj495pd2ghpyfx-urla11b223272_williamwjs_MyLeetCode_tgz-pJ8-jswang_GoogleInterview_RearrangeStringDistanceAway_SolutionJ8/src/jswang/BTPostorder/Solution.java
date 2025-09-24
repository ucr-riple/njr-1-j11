package jswang.BTPostorder;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by JiashuoWang on 10/16/14.
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

public class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> rs = new LinkedList<Integer>();
        Stack<TreeNode> st = new Stack<TreeNode>();

        if (root == null) return rs;
        else st.push(root);
        while (!st.isEmpty()) {
            TreeNode tmp = st.pop();
            if (tmp.left == null && tmp.right == null)
                rs.add(tmp.val);
            if (tmp.left != null) {
                TreeNode tmpp = tmp.left;
                tmp.left = null;
                st.push(tmp);
                st.push(tmpp);
            }
            else if (tmp.right != null) {
                TreeNode tmpp = tmp.right;
                tmp.right = null;
                st.push(tmp);
                st.push(tmpp);
            }
        }

        return rs;
    }
}
