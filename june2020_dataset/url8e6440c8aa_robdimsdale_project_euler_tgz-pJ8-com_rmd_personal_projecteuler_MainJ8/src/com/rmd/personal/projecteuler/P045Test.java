package com.rmd.personal.projecteuler;

import org.junit.Test;

import static org.junit.Assert.*;

public class P045Test extends AbstractProblemTestBase<P045> {

    public P045Test() {
        super(new P045());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int startIndex = 1;
        this.getProblem().setStartIndex(startIndex);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 40755;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 1533776805;
        assertEquals(expected, returned);
    }
}
