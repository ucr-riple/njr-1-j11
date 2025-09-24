package leetcode;

import common.TreeNode;

/**
 * Created by sobercheg on 12/22/13.
 */
public class TreeFromInPreOrder {
    /*
         ______7______
       /              \
    __10__          ___2
   /      \        /
   4       3      _8
            \    /
             1  11


   preorder = {7,10,4,3,1,2,8,11}
   inorder = {4,10,3,1,7,11,8,2}
     */

    class MutableInt {
        int i;

        MutableInt(int i) {
            this.i = i;
        }
    }

    public TreeNode buildInorderPreorder(int[] pre, int[] in) {
        return buildInorderPreorder(pre, new MutableInt(0), pre.length - 1, in, 0, in.length - 1);
    }

    public TreeNode buildInorderPreorder(int[] pre, MutableInt preStart, int preEnd, int[] in, int inStart, int inEnd) {
        if (preStart.i > preEnd || inStart > inEnd) return null;

        TreeNode root = new TreeNode(pre[preStart.i]);
        int inPos = 0;
        for (int i = 0; i < in.length; i++) {
            if (in[i] == pre[preStart.i]) {
                inPos = i;
                break;
            }
        }
        preStart.i++;
        TreeNode left = buildInorderPreorder(pre, preStart, preEnd, in, inStart, inPos - 1);
        TreeNode right = buildInorderPreorder(pre, preStart, preEnd, in, inPos + 1, inEnd);
        root.left = left;
        root.right = right;
        return root;
    }

    public static void main(String[] args) {
        TreeFromInPreOrder treeFromInPreOrder = new TreeFromInPreOrder();
        TreeNode root = treeFromInPreOrder.buildInorderPreorder(
                new int[]{7, 10, 4, 3, 1, 2, 8, 11},
                new int[]{4, 10, 3, 1, 7, 11, 8, 2});

        TreeLevelByLevel.printWithSpaces(root);

    }

}
