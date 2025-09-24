package com.rmd.personal.projecteuler;

import org.junit.Test;

import static org.junit.Assert.*;

public class P042Test extends AbstractProblemTestBase<P042> {

    public P042Test() {
        super(new P042());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int startIndex = 1441;
        this.getProblem().setStartIndex(startIndex);
        this.getProblem().setEndIndex(startIndex);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 1;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 162;
        assertEquals(expected, returned);
    }
}
