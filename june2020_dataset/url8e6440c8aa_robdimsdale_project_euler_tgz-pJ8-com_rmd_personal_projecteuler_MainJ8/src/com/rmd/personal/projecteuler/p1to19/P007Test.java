package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class P007Test extends AbstractProblemTestBase<P007> {

    public P007Test() {
        super(new P007());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int primeIndex = 6;
        this.getProblem().setDesiredPrimeNumberIndex(primeIndex);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 13L;
        assertEquals(expected, returned);
    }

    @Test
    @Ignore
    public void runReturnsCorrectlyForPrimeIndex8() {
        // Arrange
        final int primeIndex = 8;
        this.getProblem().setDesiredPrimeNumberIndex(primeIndex);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 19L;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 104743L;
        assertEquals(expected, returned);
    }
}
