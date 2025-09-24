package com.teamdev.students.calculator.impl.parsers;

import com.teamdev.students.calculator.EvaluationException;
import com.teamdev.students.calculator.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.teamdev.students.calculator.impl.parsers.MathExpressionSymbols.ARGUMENT_SEPARATOR;

public class ArgumentSeparatorParser implements MathExpressionParser {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(ArgumentSeparatorParser.class);

    @Override
    public EvaluationCommand parse(EvaluationContext context) {
        final MathExpressionReader expressionReader = context.getExpressionReader();
        LOGGER.info("Inside ArgumentSeparatorParser.");
        if (!expressionReader.endOfExpression() &&
                expressionReader.getCurrentChar() == ARGUMENT_SEPARATOR.getSymbol()) {
            LOGGER.info("ArgumentSeparatorParser is accepted.");
            expressionReader.incrementIndex(1);

            return new EvaluationCommand() {
                @Override
                public void evaluate(EvaluationStack stack) throws EvaluationException {
                    stack.pushArgumentSeparator();
                }
            };
        }

        return null;
    }
}
