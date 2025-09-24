package com.teamdev.students.calculator.impl;

import com.teamdev.students.calculator.impl.functions.MaxFunction;
import com.teamdev.students.calculator.impl.functions.MinFunction;
import com.teamdev.students.calculator.impl.functions.SqrtFunction;
import com.teamdev.students.calculator.impl.functions.SumFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FunctionFactory {
    private final Map<String, Function> functions = new HashMap<String, Function>() {{
        put("max", new MaxFunction());
        put("min", new MinFunction());
        put("sqrt", new SqrtFunction());
        put("sum", new SumFunction());
    }};

    public Function create(String presentation) {
        return functions.get(presentation);
    }

    public Set<String> getAllFunctions() {
        return functions.keySet();
    }
}
