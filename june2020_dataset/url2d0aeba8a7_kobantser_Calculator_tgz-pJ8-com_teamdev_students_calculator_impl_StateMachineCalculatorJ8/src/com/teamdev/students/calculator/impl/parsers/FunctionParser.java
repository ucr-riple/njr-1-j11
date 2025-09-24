package com.teamdev.students.calculator.impl.parsers;

import com.teamdev.students.calculator.EvaluationException;
import com.teamdev.students.calculator.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FunctionParser implements MathExpressionParser {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(FunctionParser.class);

    @Override
    public EvaluationCommand parse(EvaluationContext context) {
        LOGGER.info("Inside FunctionParser.");
        final MathExpressionReader expressionReader = context.getExpressionReader();
        final FunctionFactory factory = context.getFunctionFactory();

        String remainingExpression = expressionReader.getRemainingExpression();

        for (String presentation : factory.getAllFunctions()) {
            if (remainingExpression.startsWith(presentation)) {
                LOGGER.info("FunctionParser is accepted");
                expressionReader.incrementIndex(presentation.length());
                final Function function = factory.create(presentation);

                return new EvaluationCommand() {
                    @Override
                    public void evaluate(EvaluationStack stack) throws EvaluationException {
                        stack.pushFunction(function);
                    }
                };
            }
        }

        return null;
    }
}
