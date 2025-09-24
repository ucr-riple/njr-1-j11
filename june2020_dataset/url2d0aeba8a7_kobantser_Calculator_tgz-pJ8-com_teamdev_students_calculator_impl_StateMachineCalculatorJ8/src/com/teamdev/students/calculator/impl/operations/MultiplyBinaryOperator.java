package com.teamdev.students.calculator.impl.operations;

import java.math.BigDecimal;
import java.math.MathContext;

public class MultiplyBinaryOperator extends AbstractBinaryOperator {

    public MultiplyBinaryOperator() {
        super(Priority.MEDIUM, Associativity.LEFT);
    }

    @Override
    public BigDecimal calculate(BigDecimal leftOperand, BigDecimal rightOperand) {
        return leftOperand.multiply(rightOperand, MathContext.DECIMAL32);
    }
}

