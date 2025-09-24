package com.teamdev.students.calculator.impl.parsers;

public enum MathExpressionSymbols {
    LEFT_PARENTHESIS('('),
    RIGHT_PARENTHESIS(')'),
    ARGUMENT_SEPARATOR(';');

    private final char symbol;

    MathExpressionSymbols(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() { return symbol; }
}
