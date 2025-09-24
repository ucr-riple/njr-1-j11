package com.rmd.personal.projecteuler;

import org.junit.Test;

import static org.junit.Assert.*;

public class P041Test extends AbstractProblemTestBase<P041> {

    public P041Test() {
        super(new P041());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int max = 2143;
        this.getProblem().setMax(max);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 2143;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 7652413;
        assertEquals(expected, returned);
    }
}
