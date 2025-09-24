package com.teamdev.students.calculator.impl;

import com.teamdev.students.calculator.EvaluationException;

public interface EvaluationCommand {
    void evaluate(EvaluationStack stack) throws EvaluationException;
}
