package careercup.linkedin;

import java.util.LinkedList;

/**
 * Created by Sobercheg on 12/14/13.
 * http://www.careercup.com/question?id=15066892
 * <p/>
 * Write an algorithm to evaluate the Postfix Expression
 */
public class PostfixExpression {

    /**
     * Calculates Reverse Polish notation expressions
     * Examples:
     * 2 2 + returns 4
     * 3 4 - 5 * returns (3 - 4) * 5 = -5
     *
     * @param expression reverse polish expression
     * @return expression result
     */
    public static double calculate(String expression) {
        LinkedList<Integer> operandStack = new LinkedList<Integer>();

        for (char ch : expression.toCharArray()) {
            if (ch == ' ') continue;

            if (isDigit(ch)) {
                operandStack.push(ch - '0');
            } else {
                if (operandStack.size() < 2) {
                    throw new IllegalArgumentException("Invalid expression, not enough operands: " + operandStack);
                }
                int operand1 = operandStack.pop();
                int operand2 = operandStack.pop();
                switch (ch) {
                    case '+':
                        operandStack.push(operand1 + operand2);
                        break;
                    case '-':
                        operandStack.push(operand1 - operand2);
                        break;
                    case '/':
                        operandStack.push(operand1 / operand2);
                        break;
                    case '*':
                        operandStack.push(operand1 * operand2);
                        break;
                    default:
                        throw new IllegalArgumentException(String.format("Invalid expression, unsupported operand: [%s]", ch));
                }
            }
        }

        if (operandStack.size() > 1) throw new IllegalArgumentException("Invalid expression, too many operands");
        if (operandStack.size() < 1) throw new IllegalArgumentException("Invalid expression, too many operators");

        return operandStack.pop();
    }

    private static boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    public static void main(String[] args) {
        System.out.println(PostfixExpression.calculate("23+"));
        System.out.println(PostfixExpression.calculate("5 1 2 + 4 * + 3 -"));
    }
}
