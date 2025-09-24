package com.teamdev.students.calculator.impl.operations;

import java.math.BigDecimal;

public class PowerBinaryOperator extends AbstractBinaryOperator {

    public PowerBinaryOperator() {
        super(Priority.HIGH, Associativity.RIGHT);
    }

    @Override
    public BigDecimal calculate(BigDecimal leftOperand, BigDecimal rightOperand) {
        return new BigDecimal(Math.pow(leftOperand.doubleValue(), rightOperand.doubleValue()));
    }
}
