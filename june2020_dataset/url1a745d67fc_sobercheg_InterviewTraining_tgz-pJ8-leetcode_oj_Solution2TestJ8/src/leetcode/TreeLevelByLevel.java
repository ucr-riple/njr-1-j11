package leetcode;

import common.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Sobercheg on 12/2/13.
 */
public class TreeLevelByLevel {

    public static void print(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(root);
        TreeNode sentinel = new TreeNode(-1);
        q.offer(sentinel);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (q.isEmpty()) break;
            if (node != sentinel) {
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
                System.out.print(node.data + " ");
            } else {
                System.out.println();
                q.offer(sentinel);
            }
        }
    }

    public static void printWithSpaces(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        Queue<Integer> indent = new LinkedList<Integer>();
        int counter1 = 0;
        int counter2 = 0;
        q.offer(root);
        int initialIndent = 80;
        indent.offer(initialIndent);
        int prevInd = 0;
        counter2++;
        int level = 1;

        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            int ind = indent.poll();
            System.out.print(String.format("%" + (ind - prevInd + 2) + "s", "{" + node + "}"));
            prevInd = ind;

            if (node.left != null) {
                counter1++;
                q.offer(node.left);
                indent.offer(ind - initialIndent / (level + 1) + 4);
            }

            if (node.right != null) {
                counter1++;
                q.offer(node.right);
                indent.offer(ind + initialIndent / (level + 1) - 4);
            }

            counter2--;
            if (counter2 == 0) {
                System.out.println();
                counter2 = counter1;
                counter1 = 0;
                prevInd = 0;
                level++;
            }
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        TreeNode left = new TreeNode(3);
        TreeNode right = new TreeNode(8);
        TreeNode rightLeft = new TreeNode(7);
        root.right = right;
        root.left = left;
        right.left = rightLeft;
        TreeLevelByLevel.print(root);
        TreeLevelByLevel.printWithSpaces(root);

    }
}
