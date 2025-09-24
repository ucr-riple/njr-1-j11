package com.teamdev.students.calculator;

public class EvaluationException extends Exception {

    final private int errorIndex;

    public EvaluationException(String message, int errorIndex) {
        super(message + " at index: " + errorIndex + ".");
        this.errorIndex = errorIndex;
    }

    public int getErrorIndex() {
        return errorIndex;
    }
}

