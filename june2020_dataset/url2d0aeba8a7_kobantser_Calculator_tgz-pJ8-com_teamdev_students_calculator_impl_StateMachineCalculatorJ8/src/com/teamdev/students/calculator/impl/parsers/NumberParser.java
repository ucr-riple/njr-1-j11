package com.teamdev.students.calculator.impl.parsers;

import com.teamdev.students.calculator.EvaluationException;
import com.teamdev.students.calculator.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

public class NumberParser implements MathExpressionParser {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(NumberParser.class);

    private final NumberFormat numberFormat = DecimalFormat.getNumberInstance(Locale.US);

    @Override
    public EvaluationCommand parse(EvaluationContext context) {
        LOGGER.info("Inside NumberParser.");
        final MathExpressionReader expressionReader = context.getExpressionReader();
        final String mathExpression = expressionReader.getMathExpression();
        final int index = expressionReader.getIndex();

        final ParsePosition parsePosition = new ParsePosition(index);
        final Number number = numberFormat.parse(mathExpression, parsePosition);

        if (parsePosition.getErrorIndex() == -1) {
            LOGGER.info("NumberParser is accepted.");
            expressionReader.setIndex(parsePosition.getIndex());

            return new EvaluationCommand() {
                @Override
                public void evaluate(EvaluationStack stack) throws EvaluationException {
                    stack.pushNumber(new BigDecimal(number.doubleValue()));
                }
            };
        }

        return null;
    }
}
