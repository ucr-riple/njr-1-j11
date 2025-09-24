package crackinginterview.chap4trees;

import common.TreeNode;

import java.util.Stack;

/**
 * Created by Sobercheg on 12/7/13.
 * Problem 4.9
 */
public class TreePathsSum {

    public void findSumFrom(TreeNode startFrom, TreeNode node, int totalSum, int currentSum, Stack<TreeNode> path) {
        if (node == null) return;
        int updatedSum = currentSum + node.data;
        if (updatedSum == totalSum) {
            System.out.println(String.format("Found sum %d from %s to %s. Path: %s", totalSum, startFrom, node, path));
        }

        path.push(node.left);
        findSumFrom(startFrom, node.left, totalSum, updatedSum, path);
        path.pop();
        path.push(node.right);
        findSumFrom(startFrom, node.right, totalSum, updatedSum, path);
        path.pop();
    }

    public void findAllSums(TreeNode root, int totalSum) {
        if (root == null) return;
        Stack<TreeNode> path = new Stack<TreeNode>();
        path.push(root);
        findSumFrom(root, root, totalSum, 0, path);
        findAllSums(root.left, totalSum);
        findAllSums(root.right, totalSum);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5,
                new TreeNode(3,
                        new TreeNode(2), new TreeNode(7, new TreeNode(3), null)),

                new TreeNode(1,
                        new TreeNode(5), new TreeNode(4)));

        TreePathsSum treePathsSum = new TreePathsSum();
        treePathsSum.findAllSums(root, 8);
        treePathsSum.findAllSums(root, 9);
        treePathsSum.findAllSums(root, 10);
    }
}
