package com.teamdev.students.calculator.impl;

import java.math.BigDecimal;

public interface BinaryOperator extends Comparable<BinaryOperator> {
    BigDecimal calculate(BigDecimal leftOperand, BigDecimal rightOperand);
}

