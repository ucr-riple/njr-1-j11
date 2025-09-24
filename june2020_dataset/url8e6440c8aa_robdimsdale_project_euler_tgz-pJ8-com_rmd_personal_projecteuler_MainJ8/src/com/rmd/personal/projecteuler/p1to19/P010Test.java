package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P010Test extends AbstractProblemTestBase<P010> {

    public P010Test() {
        super(new P010());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int maxPrime = 10;
        this.getProblem().setMaxPrime(maxPrime);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 17;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 142913828922L;
        assertEquals(expected, returned);
    }
}
