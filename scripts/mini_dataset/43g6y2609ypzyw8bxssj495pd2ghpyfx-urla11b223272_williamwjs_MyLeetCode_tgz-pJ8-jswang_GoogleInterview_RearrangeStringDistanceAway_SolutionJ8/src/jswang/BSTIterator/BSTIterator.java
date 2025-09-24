package jswang.BSTIterator;

import java.util.LinkedList;

/**
 * Created by willwjs on 5/13/15.
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

public class BSTIterator {

    LinkedList<NodeWrapper> stack = new LinkedList<NodeWrapper>();

    private class NodeWrapper {
        TreeNode node;
        boolean visited;
        NodeWrapper(TreeNode node) {
            this.node = node;
            visited = false;
        }
    }

    public BSTIterator(TreeNode root) {
        if (root != null) {
            NodeWrapper rootwrapper = new NodeWrapper(root);
            stack.addLast(rootwrapper);
        }
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return stack.size() != 0;
    }

    /** @return the next smallest number */
    public int next() {
        NodeWrapper tmp = stack.removeLast();
        while (!tmp.visited) {
            if (tmp.node.right != null) {
                NodeWrapper rightwrapper = new NodeWrapper(tmp.node.right);
                stack.addLast(rightwrapper);
            }
            tmp.visited = true;
            stack.addLast(tmp);
            if (tmp.node.left != null) {
                NodeWrapper leftwrapper = new NodeWrapper(tmp.node.left);
                stack.addLast(leftwrapper);
            }
            tmp = stack.removeLast();
        }
        return tmp.node.val;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        BSTIterator i = new BSTIterator(root);
        while (i.hasNext())
            System.out.println(i.next());
    }
}

/**
 * Your BSTIterator will be called like this:
 * BSTIterator i = new BSTIterator(root);
 * while (i.hasNext()) v[f()] = i.next();
 */