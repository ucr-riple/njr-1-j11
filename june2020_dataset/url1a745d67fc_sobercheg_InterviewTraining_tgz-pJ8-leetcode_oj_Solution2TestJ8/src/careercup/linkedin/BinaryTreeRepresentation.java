package careercup.linkedin;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Sobercheg on 12/18/13.
 * www.careercup.com/question?id=13262681
 * <p/>
 * Consider this string representation for binary trees. Each node is of the form (lr), where l represents the left child and r represents the right child. If l is the character 0, then there is no left child. Similarly, if r is the character 0, then there is no right child. Otherwise, the child can be a node of the form (lr), and the representation continues recursively.
 * For example: (00) is a tree that consists of one node. ((00)0) is a two-node tree in which the root has a left child, and the left child is a leaf. And ((00)(00)) is a three-node tree, with a root, a left and a right child.
 * <p/>
 * Write a function that takes as input such a string, and returns -1 if the string is malformed, and the depth of the tree if the string is well-formed.
 * <p/>
 * For instance:
 * <p/>
 * find_depth('(00)') -> 0
 * find_depth('((00)0)') -> 1
 * find_depth('((00)(00))') -> 1
 * find_depth('((00)(0(00)))') -> 2
 * find_depth('((00)(0(0(00))))') -> 3
 * find_depth('x') -> -1
 * find_depth('0') -> -1
 * find_depth('()') -> -1
 * find_depth('(0)') -> -1
 * find_depth('(00)x') -> -1
 * find_depth('(0p)') -> -1
 */
public class BinaryTreeRepresentation {

    public int getTreeDepth(String expression) {
        if (!expression.startsWith("(")) return -1;
        Queue<Character> queue = new LinkedList<Character>();
        for (char ch : expression.toCharArray()) {
            queue.offer(ch);
        }
        int depth;
        try {
            depth = getTreeDepth(queue, 0, 0) - 1;
        } catch (IllegalArgumentException iae) {
            return -1;
        }
        if (!queue.isEmpty()) depth = -1;
        return depth;
    }

    // Tree = 0
    // Tree = (<Tree><Tree>)
    public int getTreeDepth(Queue<Character> expression, int currentDepth, int maxDepth) {
        // base case
        maxDepth = Math.max(currentDepth, maxDepth);
        char ch = expression.poll();

        if (ch == '0') return maxDepth;
        else if (ch == '(') {
            maxDepth = getTreeDepth(expression, currentDepth + 1, maxDepth);
            maxDepth = Math.max(maxDepth, getTreeDepth(expression, currentDepth + 1, maxDepth));
            ch = expression.poll();
            if (ch != ')') throw new IllegalArgumentException(") expected");
        } else throw new IllegalArgumentException("Illegal char " + ch);

        return maxDepth;
    }

    public static void main(String[] args) {
        System.out.println(new BinaryTreeRepresentation().getTreeDepth("(00)"));
        System.out.println(new BinaryTreeRepresentation().getTreeDepth("((00)0)"));
        System.out.println(new BinaryTreeRepresentation().getTreeDepth("((00)(00))"));
        System.out.println(new BinaryTreeRepresentation().getTreeDepth("((00)(0(00)))"));
        System.out.println(new BinaryTreeRepresentation().getTreeDepth("((00)(0(0(00))))"));
        System.out.println(new BinaryTreeRepresentation().getTreeDepth("x"));
        System.out.println(new BinaryTreeRepresentation().getTreeDepth("0"));
        System.out.println(new BinaryTreeRepresentation().getTreeDepth("()"));
        System.out.println(new BinaryTreeRepresentation().getTreeDepth("(0)"));
        System.out.println(new BinaryTreeRepresentation().getTreeDepth("(00)x"));
        System.out.println(new BinaryTreeRepresentation().getTreeDepth("(0p)"));
    }
}
