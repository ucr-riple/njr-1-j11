package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P037Test extends AbstractProblemTestBase<P037> {

    public P037Test() {
        super(new P037());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int startPrime = 3797;
        final int count = 1;
        this.getProblem().setStartPrime(startPrime);
        this.getProblem().setCount(count);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 3797;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 748317;
        assertEquals(expected, returned);
    }
}
