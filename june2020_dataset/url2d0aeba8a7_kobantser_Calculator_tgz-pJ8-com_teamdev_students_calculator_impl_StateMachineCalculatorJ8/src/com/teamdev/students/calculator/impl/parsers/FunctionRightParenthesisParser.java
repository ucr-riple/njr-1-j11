package com.teamdev.students.calculator.impl.parsers;

import com.teamdev.students.calculator.EvaluationException;
import com.teamdev.students.calculator.impl.*;
import com.teamdev.students.calculator.impl.functions.AbstractFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Deque;

import static com.teamdev.students.calculator.impl.parsers.MathExpressionSymbols.RIGHT_PARENTHESIS;

public class FunctionRightParenthesisParser implements MathExpressionParser {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(FunctionRightParenthesisParser.class);

    @Override
    public EvaluationCommand parse(EvaluationContext context) {
        LOGGER.info("Inside FunctionRightParenthesisParser.");
        final MathExpressionReader expressionReader = context.getExpressionReader();
        final Deque<Integer> functionParenthesisStack =
                context.getEvaluationStack().getFunctionParenthesisStack();

        final int parenthesisStackSize =
                context.getEvaluationStack().getParenthesisStack().size();

        if (!expressionReader.endOfExpression()
                && expressionReader.getCurrentChar() == RIGHT_PARENTHESIS.getSymbol()
                && !functionParenthesisStack.isEmpty()
                && functionParenthesisStack.peek() == parenthesisStackSize) {
            LOGGER.info("FunctionRightParenthesisParser is accepted.");
            expressionReader.incrementIndex(1);

            return new EvaluationCommand() {
                @Override
                public void evaluate(EvaluationStack stack) throws EvaluationException {

                    if (functionParenthesisStack.isEmpty()) {
                        throw new EvaluationException("Function opening bracket expected",
                                expressionReader.getIndex());
                    }

                    final AbstractFunction function =
                            (AbstractFunction) stack.getFunctionStack().peek();
                    final int argumentCount = stack.getFunctionArgumentCountStack().peek();

                    if (argumentCount >  function.getMaxArgumentCount() ||
                            argumentCount < function.getMinArgumentCount()) {

                        throw new EvaluationException("Wrong function argument count",
                                expressionReader.getIndex());
                    }

                    stack.pushFunctionRightParenthesis();
                }
            };
        }

        return null;
    }
}
