package jswang.GoogleInterview.SerializeNAryTree;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by willwjs on 9/17/15.
 * Serialize an N-ary Tree 将树存到文件里，要求可以还原
 * http://www.1point3acres.com/bbs/thread-140228-1-1.html
 * 8.26 onsite
 */
public class Solution {
    public String serializeTree(TreeNode root) {
        String res = "";
        if (root == null) return res;
        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.addLast(root);
        while (!stack.isEmpty()) {
            TreeNode tmp = stack.removeLast();
            if (tmp == null) {
                res += "/ ";
                continue;
            }
            res += (tmp.val + " ");
            stack.addLast(null);
            for (int i = tmp.children.size() - 1; i >= 0 ; --i) {
                stack.addLast(tmp.children.get(i));
            }
        }
        return res;
    }

    public TreeNode deserializeTree(String s) {
        String[] serial = s.split(" ");
        TreeNode root = new TreeNode(Integer.parseInt(serial[0]));
        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.addLast(root);
        int i = 1;
        while (!stack.isEmpty() && i < serial.length) {
            TreeNode parent = stack.getLast();
            if (serial[i].equals("/")) {
                stack.removeLast();
                ++i;
                continue;
            }
            TreeNode child = new TreeNode(Integer.parseInt(serial[i]));
            parent.children.add(child);
            stack.addLast(child);
            ++i;
        }
        return root;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode tmp1 = new TreeNode(2);
        root.children.add(tmp1);
        TreeNode tmp2 = new TreeNode(3);
        tmp1.children.add(tmp2);
        tmp2 = new TreeNode(4);
        tmp1.children.add(tmp2);
        tmp1 = new TreeNode(5);
        tmp2.children.add(tmp1);
        tmp1 = new TreeNode(6);
        root.children.add(tmp1);
        tmp1 = new TreeNode(7);
        root.children.add(tmp1);
        tmp2 = new TreeNode(8);
        tmp1.children.add(tmp2);
        tmp2 = new TreeNode(9);
        tmp1.children.add(tmp2);
        tmp2 = new TreeNode(10);
        tmp1.children.add(tmp2);
        tmp2 = new TreeNode(11);
        tmp1.children.add(tmp2);

        Solution solution = new Solution();
        String serializationRes = solution.serializeTree(root);
        System.out.println(serializationRes);

        TreeNode deserializationRes = solution.deserializeTree(serializationRes);
        System.out.println(deserializationRes);
    }
}

class TreeNode {
    int val;
    List<TreeNode> children;
    TreeNode(int val) {
        this.val = val;
        children = new LinkedList<>();
    }
}
