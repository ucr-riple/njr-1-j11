package com.rmd.personal.projecteuler;

import org.junit.Test;

import static org.junit.Assert.*;

public class P049Test extends AbstractProblemTestBase<P049> {

    public P049Test() {
        super(new P049());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int start = 13;
        final int end = 1487;
        this.getProblem().setStart(start);
        this.getProblem().setEnd(end);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 148748178147L;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 296962999629L;
        assertEquals(expected, returned);
    }
}
