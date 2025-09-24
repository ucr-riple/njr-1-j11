package com.teamdev.students.calculator.impl;

import com.teamdev.students.calculator.impl.operations.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BinaryOperatorFactory {
    private final Map<String, BinaryOperator> operators = new HashMap<String, BinaryOperator>() {{
        put("+", new PlusBinaryOperator());
        put("/", new DivideBinaryOperator());
        put("-", new MinusBinaryOperator());
        put("*", new MultiplyBinaryOperator());
        put("^", new PowerBinaryOperator());
    }};

    public BinaryOperator create(String operatorPresentation) {
        return operators.get(operatorPresentation);
    }

    public Set<String> getAllOperators() {
        return operators.keySet();
    }
}
