package com.teamdev.students.calculator.impl.parsers;

import com.teamdev.students.calculator.EvaluationException;
import com.teamdev.students.calculator.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.teamdev.students.calculator.impl.parsers.MathExpressionSymbols.LEFT_PARENTHESIS;

public class FunctionLeftParenthesisParser implements MathExpressionParser {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(FunctionLeftParenthesisParser.class);

    @Override
    public EvaluationCommand parse(EvaluationContext context) {
        LOGGER.info("Inside FunctionLeftParenthesisParser.");
        final MathExpressionReader expressionReader = context.getExpressionReader();
        final State previousState = context.getPreviousState();

        if (previousState == State.FUNCTION &&
                expressionReader.getCurrentChar() == LEFT_PARENTHESIS.getSymbol()) {
            LOGGER.info("FunctionLeftParenthesisParser is accepted.");
            expressionReader.incrementIndex(1);

            return new EvaluationCommand() {
                @Override
                public void evaluate(EvaluationStack stack) throws EvaluationException {
                    stack.pushFunctionLeftParenthesis();
                }
            };
        }

        return null;
    }
}
