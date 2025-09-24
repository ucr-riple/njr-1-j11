package jswang.reversePolishNotation;

/**
 * Created by Jiashuo Wang on 14-6-9.
 */
public class Main {
    public static void main(String[] args) {
        Solution rpn = new Solution();
        System.out.print(rpn.evalRPN(new String[] {"4", "13", "5", "/", "+"}));
    }
}