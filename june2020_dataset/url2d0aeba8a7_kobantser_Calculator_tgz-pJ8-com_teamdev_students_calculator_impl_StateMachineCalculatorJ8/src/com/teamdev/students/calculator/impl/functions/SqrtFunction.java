package com.teamdev.students.calculator.impl.functions;

import java.math.BigDecimal;

public class SqrtFunction extends AbstractFunction {
    public SqrtFunction() {
        super(1, 1);
    }

    @Override
    public BigDecimal calculate(BigDecimal... arguments) {
        return new BigDecimal(Math.sqrt(arguments[0].doubleValue()));
    }
}
