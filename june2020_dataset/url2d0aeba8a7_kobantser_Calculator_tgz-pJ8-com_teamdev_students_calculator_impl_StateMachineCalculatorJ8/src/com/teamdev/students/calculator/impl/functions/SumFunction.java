package com.teamdev.students.calculator.impl.functions;

import java.math.BigDecimal;

public class SumFunction extends AbstractFunction {
    public SumFunction() {
        super(1, Integer.MAX_VALUE);
    }

    @Override
    public BigDecimal calculate(BigDecimal... arguments) {
        BigDecimal result = BigDecimal.ZERO;

        for (int i = 0; i < arguments.length; i++) {
            result = result.add(arguments[i]);
        }

        return result;
    }
}
