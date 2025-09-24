package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P035Test extends AbstractProblemTestBase<P035> {

    public P035Test() {
        super(new P035());
    }

    @Test
    public void runReturnsCorrectlyForProvidedUpTo100() {
        // Arrange
        final int start = 2;
        final int end = 100;
        this.getProblem().setStart(start);
        this.getProblem().setEnd(end);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 13;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForProvided197() {
        // Arrange
        final int start = 197;
        final int end = 197;
        this.getProblem().setStart(start);
        this.getProblem().setEnd(end);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 3;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 55;
        assertEquals(expected, returned);
    }
}
