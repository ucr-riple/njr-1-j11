package com.teamdev.students.calculator.impl.parsers;

import com.teamdev.students.calculator.EvaluationException;
import com.teamdev.students.calculator.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndOfExpressionParser implements MathExpressionParser {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(EndOfExpressionParser.class);

    @Override
    public EvaluationCommand parse(EvaluationContext context) {
        LOGGER.info("Inside EndOfExpressionParser.");
        final MathExpressionReader expressionReader = context.getExpressionReader();

        if (expressionReader.endOfExpression()) {
            LOGGER.info("EndOfExpressionParser is accepted.");
            return new EvaluationCommand() {
                @Override
                public void evaluate(EvaluationStack stack) throws EvaluationException {

                    if (!stack.getParenthesisStack().isEmpty() ||
                            !stack.getFunctionParenthesisStack().isEmpty()) {

                        throw new EvaluationException("Closing bracket expected",
                                expressionReader.getIndex());
                    }

                    stack.popAllOperators();
                }
            };
        }

        return null;
    }
}