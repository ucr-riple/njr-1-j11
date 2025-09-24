package com.teamdev.students.calculator;

import java.math.BigDecimal;

public interface MathExpressionCalculator {

    BigDecimal evaluate(String mathExpression) throws EvaluationException;
}
