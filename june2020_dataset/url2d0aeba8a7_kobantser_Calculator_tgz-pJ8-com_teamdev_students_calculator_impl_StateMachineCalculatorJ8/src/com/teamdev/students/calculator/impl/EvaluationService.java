package com.teamdev.students.calculator.impl;

import com.teamdev.students.calculator.EvaluationException;
import com.teamdev.students.calculator.impl.parsers.*;
import com.teamdev.students.fsm.StateAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.teamdev.students.calculator.impl.State.*;

public class EvaluationService implements StateAcceptor<State, EvaluationContext, EvaluationException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EvaluationService.class);

    private final Map<State, MathExpressionParser> parsers = new HashMap<State, MathExpressionParser>() {{
        put(NUMBER, new NumberParser());
        put(BINARY_OPERATOR, new BinaryOperatorParser());
        put(LEFT_PARENTHESIS, new LeftParenthesisParser());
        put(RIGHT_PARENTHESIS, new RightParenthesisParser());
        put(FUNCTION, new FunctionParser());
        put(FUNCTION_LEFT_PARENTHESIS, new FunctionLeftParenthesisParser());
        put(FUNCTION_RIGHT_PARENTHESIS, new FunctionRightParenthesisParser());
        put(ARGUMENT_SEPARATOR, new ArgumentSeparatorParser());
        put(FINISH, new EndOfExpressionParser());
    }};


    @Override
    public boolean acceptState(EvaluationContext context, State possibleState) throws EvaluationException {

        final MathExpressionParser parser = parsers.get(possibleState);

        if (parser == null) {
            LOGGER.error("Parser not found for state: " + possibleState);
            throw new IllegalStateException("Parser not found for state: " + possibleState);
        }

        context.getExpressionReader().skipWhitespaces();

        final EvaluationCommand evaluationCommand = parser.parse(context);
        if (evaluationCommand == null) {
            return false;
        }

        context.setPreviousState(possibleState);

        evaluationCommand.evaluate(context.getEvaluationStack());
        LOGGER.info("Current state: " + context.getPreviousState());
        return true;
    }
}
