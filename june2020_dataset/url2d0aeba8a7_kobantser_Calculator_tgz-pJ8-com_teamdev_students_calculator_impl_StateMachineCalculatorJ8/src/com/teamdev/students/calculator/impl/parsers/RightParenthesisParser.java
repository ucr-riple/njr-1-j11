package com.teamdev.students.calculator.impl.parsers;

import com.teamdev.students.calculator.EvaluationException;
import com.teamdev.students.calculator.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Deque;

import static com.teamdev.students.calculator.impl.parsers.MathExpressionSymbols.RIGHT_PARENTHESIS;

public class RightParenthesisParser implements MathExpressionParser {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(RightParenthesisParser.class);

    @Override
    public EvaluationCommand parse(EvaluationContext context) {
        LOGGER.info("Inside RightParenthesisParser.");
        final MathExpressionReader expressionReader = context.getExpressionReader();
        final Deque<Integer> functionParenthesisStack =
                context.getEvaluationStack().getFunctionParenthesisStack();

        final int parenthesisStackSize =
                context.getEvaluationStack().getParenthesisStack().size();

        if (!expressionReader.endOfExpression() &&
                expressionReader.getCurrentChar() == RIGHT_PARENTHESIS.getSymbol() &&
                (functionParenthesisStack.isEmpty() || functionParenthesisStack.peek() !=
                        parenthesisStackSize)) {
            LOGGER.info("RightParenthesisParser is accepted.");
            expressionReader.incrementIndex(1);

            return new EvaluationCommand() {
                @Override
                public void evaluate(EvaluationStack stack) throws EvaluationException {

                    if (stack.getParenthesisStack().isEmpty()) {
                        throw new EvaluationException("Opening bracket expected",
                                expressionReader.getIndex());
                    }

                    stack.pushRightParenthesis();
                }
            };
        }

        return null;
    }
}