package com.teamdev.students.calculator.impl;

public class MathExpressionReader {
    private final String mathExpression;
    private int index = 0;

    public MathExpressionReader(String mathExpression) {
        this.mathExpression = mathExpression;
    }

    public char getCurrentChar() {
        return mathExpression.charAt(index);
    }

    public String getMathExpression() {
        return mathExpression;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void incrementIndex(int value) {
        index += value;
    }

    public boolean endOfExpression() {
        return index >= mathExpression.length();
    }

    public void skipWhitespaces() {
        while(!endOfExpression() && Character.isWhitespace(getCurrentChar())) {
            index++;
        }
    }

    public String getRemainingExpression() {
        return mathExpression.substring(index);
    }
}
