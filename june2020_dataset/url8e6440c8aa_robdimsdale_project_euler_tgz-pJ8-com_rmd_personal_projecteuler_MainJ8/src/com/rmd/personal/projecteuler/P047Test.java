package com.rmd.personal.projecteuler;

import org.junit.Test;

import static org.junit.Assert.*;

public class P047Test extends AbstractProblemTestBase<P047> {

    public P047Test() {
        super(new P047());
    }

    @Test
    public void runReturnsCorrectlyForProvided2Digit() {
        // Arrange
        final int primeFactorCount = 2;
        this.getProblem().setDistinctPrimeFactorCount(primeFactorCount);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 14;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForProvided3Digit() {
        // Arrange
        final int primeFactorCount = 3;
        this.getProblem().setDistinctPrimeFactorCount(primeFactorCount);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 644;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 134043;
        assertEquals(expected, returned);
    }
}
