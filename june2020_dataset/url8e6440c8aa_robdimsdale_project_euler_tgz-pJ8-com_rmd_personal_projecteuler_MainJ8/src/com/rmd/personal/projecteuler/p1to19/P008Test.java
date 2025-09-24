package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P008Test extends AbstractProblemTestBase<P008> {

    public P008Test() {
        super(new P008());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int primeIndex = 4;
        this.getProblem().setNumberOfConsecutiveDigits(primeIndex);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 9 * 9 * 8 * 9;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 40824L;
        assertEquals(expected, returned);
    }
}
