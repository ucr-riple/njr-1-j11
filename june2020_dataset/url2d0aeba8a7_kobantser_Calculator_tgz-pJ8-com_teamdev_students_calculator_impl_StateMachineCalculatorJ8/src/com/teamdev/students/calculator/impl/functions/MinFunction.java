package com.teamdev.students.calculator.impl.functions;

import java.math.BigDecimal;

public class MinFunction extends AbstractFunction {
    public MinFunction() {
        super(1, Integer.MAX_VALUE);
    }

    @Override
    public BigDecimal calculate(BigDecimal... arguments) {
        BigDecimal min = arguments[0];

        for (int i = 1; i < arguments.length; i++) {
            if (min.compareTo(arguments[i]) > 0) {
                min = arguments[i];
            }
        }

        return min;
    }
}
