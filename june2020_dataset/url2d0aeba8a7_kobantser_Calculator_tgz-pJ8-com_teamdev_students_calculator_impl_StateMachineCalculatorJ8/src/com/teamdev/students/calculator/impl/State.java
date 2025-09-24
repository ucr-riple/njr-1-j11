package com.teamdev.students.calculator.impl;

public enum State {
    START,
    NUMBER,
    BINARY_OPERATOR,
    LEFT_PARENTHESIS,
    RIGHT_PARENTHESIS,
    FUNCTION,
    FUNCTION_LEFT_PARENTHESIS,
    FUNCTION_RIGHT_PARENTHESIS,
    ARGUMENT_SEPARATOR,
    FINISH
}