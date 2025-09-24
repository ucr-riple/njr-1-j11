package com.teamdev.students.calculator.impl.operations;

import java.math.BigDecimal;
import java.math.MathContext;

public class PlusBinaryOperator extends AbstractBinaryOperator {

    public PlusBinaryOperator() {
        super(Priority.LOW, Associativity.LEFT);
    }

    @Override
    public BigDecimal calculate(BigDecimal leftOperand, BigDecimal rightOperand) {
        return leftOperand.add(rightOperand, MathContext.DECIMAL32);
    }
}
