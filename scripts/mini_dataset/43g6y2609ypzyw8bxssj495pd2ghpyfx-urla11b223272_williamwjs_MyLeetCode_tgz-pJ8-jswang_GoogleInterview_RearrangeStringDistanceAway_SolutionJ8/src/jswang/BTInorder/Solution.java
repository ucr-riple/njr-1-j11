package jswang.BTInorder;

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

class MorrisSolution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> rs = new LinkedList<Integer>();
        if (root == null) return rs;
        TreeNode tmp = root;

        while (tmp != null) {
            if (tmp.left == null) {
                rs.add(tmp.val);
                tmp = tmp.right;
            }
            else {
                TreeNode tmpp = tmp.left;
                while (tmpp.right != null && tmpp.right != tmp) tmpp = tmpp.right;
                if (tmpp.right == null) {
                    tmpp.right = tmp;
                    tmp = tmp.left;
                }
                if (tmpp.right == tmp) {
                    tmpp.right = null;
                    rs.add(tmp.val);
                    tmp = tmp.right;
                }
            }
        }

        return rs;
    }
}

//With Stack
public class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> rs = new LinkedList<Integer>();
        Stack<TreeNode> st = new Stack<TreeNode>();

        if (root == null) return rs;
        else st.push(root);
        while (!st.isEmpty()) {
            TreeNode tmp = st.pop();
            if (tmp.left == null) rs.add(tmp.val);
            if (tmp.right != null) st.push(tmp.right);
            if (tmp.left != null) {
                TreeNode tmpp = tmp.left;
                tmp.left = null;
                tmp.right = null;
                st.push(tmp);
                st.push(tmpp);
            }
        }

        return rs;
    }
}
