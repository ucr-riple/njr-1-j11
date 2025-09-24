package com.teamdev.students.calculator.impl.functions;

import java.math.BigDecimal;

public class MaxFunction extends AbstractFunction {
    public MaxFunction() {
        super(1, Integer.MAX_VALUE);
    }

    @Override
    public BigDecimal calculate(BigDecimal... arguments) {
        BigDecimal max = arguments[0];

        for (int i = 1; i < arguments.length; i++) {
            if (max.compareTo(arguments[i]) < 0) {
                max = arguments[i];
            }
        }

        return max;
    }
}
