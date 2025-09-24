package com.teamdev.students.calculator.impl;

import com.teamdev.students.calculator.EvaluationException;
import com.teamdev.students.calculator.MathExpressionCalculator;
import com.teamdev.students.fsm.FiniteStateMachine;

import java.math.BigDecimal;

public class StateMachineCalculator extends FiniteStateMachine<State, EvaluationContext, BigDecimal, EvaluationException>
        implements MathExpressionCalculator {

    @Override
    public BigDecimal evaluate(String mathExpression) throws EvaluationException{
        return run(new EvaluationContext(mathExpression));
    }

    @Override
    protected void deadlock(EvaluationContext context, State currentState) throws EvaluationException {
        throw new EvaluationException("Incorrect expression format.",
                context.getExpressionReader().getIndex());
    }

    @Override
    protected BigDecimal finish(EvaluationContext context) {
        return context.getEvaluationStack().getOperandStack().pop();
    }

    public static void main(String[] args) throws Exception {
        final StateMachineCalculator calculator = new StateMachineCalculator();
        final BigDecimal result = calculator.evaluate("min(1;2)");
        System.out.println("result = " + result);
    }
}
