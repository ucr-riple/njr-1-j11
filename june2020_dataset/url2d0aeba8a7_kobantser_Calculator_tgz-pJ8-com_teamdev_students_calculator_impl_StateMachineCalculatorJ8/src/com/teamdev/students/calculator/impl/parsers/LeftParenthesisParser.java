package com.teamdev.students.calculator.impl.parsers;

import com.teamdev.students.calculator.EvaluationException;
import com.teamdev.students.calculator.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.teamdev.students.calculator.impl.parsers.MathExpressionSymbols.*;

public class LeftParenthesisParser implements MathExpressionParser {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(LeftParenthesisParser.class);

    @Override
    public EvaluationCommand parse(EvaluationContext context) {
        LOGGER.info("Inside LeftParenthesisParser.");
        final MathExpressionReader expressionReader = context.getExpressionReader();
        final State previousState = context.getPreviousState();

        if (expressionReader.getCurrentChar() == LEFT_PARENTHESIS.getSymbol() &&
                previousState != State.FUNCTION) {
            LOGGER.info("LeftParenthesisParser is accepted.");
            expressionReader.incrementIndex(1);

            return new EvaluationCommand() {
                @Override
                public void evaluate(EvaluationStack stack) throws EvaluationException {

                    stack.pushLeftParenthesis();
                }
            };
        }

        return null;
    }
}
