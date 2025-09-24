package com.teamdev.students.calculator.impl.operations;

import java.math.BigDecimal;
import java.math.MathContext;

public class DivideBinaryOperator extends AbstractBinaryOperator {

    public DivideBinaryOperator() {
        super(Priority.MEDIUM, Associativity.LEFT);
    }

    @Override
    public BigDecimal calculate(BigDecimal leftOperand, BigDecimal rightOperand) {
        if (BigDecimal.ZERO.compareTo(rightOperand) == 0) {
            throw new IllegalStateException("Division by zero.");
        }
        return leftOperand.divide(rightOperand, MathContext.DECIMAL32);
    }
}

