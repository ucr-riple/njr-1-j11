package com.rmd.personal.projecteuler;

import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractProblemTestBase<TProblem extends Problem> {

    private TProblem problem;

    protected AbstractProblemTestBase(TProblem problem) {
        this.problem = problem;
    }

    protected TProblem getProblem() {
        return this.problem;
    }

    @Test
    public void descriptionNotNull() {
        // Assert
        assertNotNull(this.getProblem().getDescription());
        assertFalse("".equals(getProblem().getDescription()));
    }
}
