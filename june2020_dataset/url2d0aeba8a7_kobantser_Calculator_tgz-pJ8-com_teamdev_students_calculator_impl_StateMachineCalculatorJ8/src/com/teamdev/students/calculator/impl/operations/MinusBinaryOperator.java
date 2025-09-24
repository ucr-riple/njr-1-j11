package com.teamdev.students.calculator.impl.operations;

import java.math.BigDecimal;
import java.math.MathContext;

public class MinusBinaryOperator extends AbstractBinaryOperator {

    public MinusBinaryOperator() {
        super(Priority.LOW, Associativity.LEFT);
    }

    @Override
    public BigDecimal calculate(BigDecimal leftOperand, BigDecimal rightOperand) {
        return leftOperand.subtract(rightOperand, MathContext.DECIMAL32);
    }
}

