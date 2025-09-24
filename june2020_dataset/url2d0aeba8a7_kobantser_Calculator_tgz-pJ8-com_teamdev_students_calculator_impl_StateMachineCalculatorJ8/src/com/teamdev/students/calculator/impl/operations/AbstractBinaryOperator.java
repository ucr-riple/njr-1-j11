package com.teamdev.students.calculator.impl.operations;

import com.teamdev.students.calculator.impl.BinaryOperator;

public abstract class AbstractBinaryOperator implements BinaryOperator {
    private Priority priority;
    private Associativity associativity;

    static enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    static enum Associativity {
        LEFT,
        RIGHT
    }

    public AbstractBinaryOperator(Priority priority, Associativity associativity) {
        this.priority = priority;
        this.associativity = associativity;
    }

    @Override
    public int compareTo(BinaryOperator o) {
        return priority.compareTo( ((AbstractBinaryOperator) o).priority );
    }

    public boolean isLeftAssociative() {
        return associativity == Associativity.LEFT;
    }
}
