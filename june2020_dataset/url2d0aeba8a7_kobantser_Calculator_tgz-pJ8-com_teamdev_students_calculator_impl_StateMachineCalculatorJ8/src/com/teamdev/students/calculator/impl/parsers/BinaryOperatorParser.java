package com.teamdev.students.calculator.impl.parsers;

import com.teamdev.students.calculator.EvaluationException;
import com.teamdev.students.calculator.impl.*;
import com.teamdev.students.calculator.impl.BinaryOperatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinaryOperatorParser implements MathExpressionParser {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(BinaryOperatorParser.class);

    @Override
    public EvaluationCommand parse(EvaluationContext context) {
        LOGGER.info("Inside BinaryOperatorParser.");
        final MathExpressionReader expressionReader = context.getExpressionReader();
        final BinaryOperatorFactory factory = context.getBinaryOperatorFactory();

        final String remainingExpression = expressionReader.getRemainingExpression();

        for (String presentation : factory.getAllOperators()) {
            if (remainingExpression.startsWith(presentation)) {
                LOGGER.info("BinaryOperatorParser is accepted.");
                final BinaryOperator binaryOperator = factory.create(presentation);
                expressionReader.incrementIndex(presentation.length());

                return new EvaluationCommand() {
                    @Override
                    public void evaluate(EvaluationStack stack) throws EvaluationException {
                        stack.pushOperator(binaryOperator);
                    }
                };
            }
        }

        return null;
    }
}
