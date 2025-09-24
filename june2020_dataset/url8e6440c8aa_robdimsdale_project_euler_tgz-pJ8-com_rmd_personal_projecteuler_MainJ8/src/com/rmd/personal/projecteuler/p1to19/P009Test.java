package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P009Test extends AbstractProblemTestBase<P009> {

    public P009Test() {
        super(new P009());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int tripletSum = 12;
        this.getProblem().setTripletSum(tripletSum);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 3 * 4 * 5;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 31875000L;
        assertEquals(expected, returned);
    }
}
