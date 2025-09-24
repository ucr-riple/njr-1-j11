package com.rmd.personal.projecteuler;

import org.junit.Test;

import static org.junit.Assert.*;

public class P050Test extends AbstractProblemTestBase<P050> {

    public P050Test() {
        super(new P050());
    }

    @Test
    public void runReturnsCorrectlyForLessThan100() {
        // Arrange
        final int max = 100;
        this.getProblem().setMax(max);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 41L;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForLessThan1000() {
        // Arrange
        final int max = 1000;
        this.getProblem().setMax(max);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 953L;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 997651L;
        assertEquals(expected, returned);
    }
}
