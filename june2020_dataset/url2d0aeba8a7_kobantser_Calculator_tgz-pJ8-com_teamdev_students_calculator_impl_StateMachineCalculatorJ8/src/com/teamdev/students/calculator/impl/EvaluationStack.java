package com.teamdev.students.calculator.impl;

import com.teamdev.students.calculator.impl.operations.AbstractBinaryOperator;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;

public class EvaluationStack {
    private final Deque<BigDecimal> operandStack = new ArrayDeque<>();
    private final Deque<BinaryOperator> operatorStack = new ArrayDeque<>();
    private final Deque<Integer> parenthesisStack = new ArrayDeque<>();

    private final Deque<Function> functionStack = new ArrayDeque<>();
    private final Deque<Integer> functionArgumentCountStack = new ArrayDeque<>();
    private final Deque<Integer> functionParenthesisStack = new ArrayDeque<>();
    private final Deque<Integer> functionOperatorStack = new ArrayDeque<>();

    public Deque<Integer> getParenthesisStack() {
        return parenthesisStack;
    }

    public Deque<BigDecimal> getOperandStack() {
        return operandStack;
    }

    public Deque<BinaryOperator> getOperatorStack() {
        return operatorStack;
    }

    public Deque<Function> getFunctionStack() {
        return functionStack;
    }

    public Deque<Integer> getFunctionArgumentCountStack() {
        return functionArgumentCountStack;
    }

    public Deque<Integer> getFunctionParenthesisStack() {
        return functionParenthesisStack;
    }

    public void executeBinaryOperator() {
        BigDecimal secondOperand = operandStack.pop();
        BigDecimal firstOperand = operandStack.pop();
        BinaryOperator binaryOperator = operatorStack.pop();
        BigDecimal result = binaryOperator.calculate(firstOperand, secondOperand);

        operandStack.push(result);
    }

    public void executeFunction() {
        Function function = functionStack.pop();
        int numberOfArguments = functionArgumentCountStack.pop();
        BigDecimal[] arguments = new BigDecimal[numberOfArguments];

        for (int i = 0; i < arguments.length; i++) {
            arguments[i] = operandStack.pop();
        }

        BigDecimal result = function.calculate(arguments);
        operandStack.push(result);
    }

    public void pushOperator(BinaryOperator operator) {

        while (isOperatorOnTheTop() && ((operatorStack.peek().compareTo(operator) > 0) ||
                (operatorStack.peek().compareTo(operator) > -1 &&
                        ((AbstractBinaryOperator) operator).isLeftAssociative()))) {

            executeBinaryOperator();
        }

        operatorStack.push(operator);
    }

    public void popAllOperators() {
        while (!operatorStack.isEmpty()) {
            executeBinaryOperator();
        }
    }

    public void pushLeftParenthesis() {
        parenthesisStack.push(operatorStack.size());
    }

    public void pushRightParenthesis() {
        final int previousOperatorStackSize = parenthesisStack.pop();
        while (previousOperatorStackSize < operatorStack.size()) {
            executeBinaryOperator();
        }
    }

    public void pushFunctionLeftParenthesis() {
        functionArgumentCountStack.push(1);
        functionParenthesisStack.push(parenthesisStack.size());
        functionOperatorStack.push(operatorStack.size());
    }

    public void pushFunctionRightParenthesis() {
        functionParenthesisStack.pop();

        evaluateFunctionArgument();
        functionOperatorStack.pop();

        executeFunction();
    }

    public void pushNumber(BigDecimal number) {
        operandStack.push(number);
    }

    public void pushFunction(Function function) {
        functionStack.push(function);
    }

    public void pushArgumentSeparator() {
        final int argumentCount = functionArgumentCountStack.pop();
        functionArgumentCountStack.push(argumentCount + 1);

        evaluateFunctionArgument();
    }

    public void evaluateFunctionArgument() {
        while (functionOperatorStack.peek() < operatorStack.size()) {
            executeBinaryOperator();
        }
    }

    public boolean isOperatorOnTheTop() {
        if (operatorStack.isEmpty()) {
            return false;
        } else if (parenthesisStack.isEmpty()) {
            return true;
        } else if (parenthesisStack.peek() < operatorStack.size()) {
            return true;
        }

        return false;
    }
}

