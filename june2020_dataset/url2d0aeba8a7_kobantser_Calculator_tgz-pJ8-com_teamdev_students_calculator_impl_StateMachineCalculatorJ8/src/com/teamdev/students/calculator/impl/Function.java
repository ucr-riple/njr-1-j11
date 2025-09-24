package com.teamdev.students.calculator.impl;

import java.math.BigDecimal;

public interface Function {
    BigDecimal calculate(BigDecimal... arguments);
}
