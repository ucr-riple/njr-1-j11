package leetcode;

/**
 * Created by sobercheg on 12/24/13.
 * http://leetcode.com/2010/03/first-on-site-technical-interview.html
 */

public class TreeSiblingPointers {
    static class SiblingNode {
        int data;
        SiblingNode left;
        SiblingNode right;
        SiblingNode nextSibling;

        SiblingNode(int data) {
            this.data = data;
        }

        SiblingNode(int data, SiblingNode left, SiblingNode right) {
            this(data);
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "{" + data + "}->" + nextSibling;
        }
    }

    public static void connect(SiblingNode root) {
        if (root == null) return;

        SiblingNode currentNode = root;

        while (currentNode != null) {
            if (currentNode.left != null && currentNode.right != null) {
                currentNode.left.nextSibling = currentNode.right;
                if (currentNode.nextSibling != null) {
                    currentNode.right.nextSibling = currentNode.nextSibling.left;
                }
            }
            currentNode = currentNode.nextSibling;
        }

        connect(root.left);

    }

    public static void main(String[] args) {
        SiblingNode root = new SiblingNode(10,
                new SiblingNode(20,
                        new SiblingNode(21), new SiblingNode(22)), new SiblingNode(30, new SiblingNode(31), new SiblingNode(32)));
        connect(root);
        System.out.println(root);
        System.out.println(root.left);
        System.out.println(root.left.left);
        System.out.println(root.left.right);
        System.out.println(root.right);
        System.out.println(root.right.left);
        System.out.println(root.right.right);

    }
}
