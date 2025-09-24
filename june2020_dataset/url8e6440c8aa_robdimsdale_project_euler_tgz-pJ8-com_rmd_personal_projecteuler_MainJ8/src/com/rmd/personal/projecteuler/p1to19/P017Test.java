package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P017Test extends AbstractProblemTestBase<P017> {

    public P017Test() {
        super(new P017());
    }

    @Test
    public void runReturnsCorrectlyForProvided1To5() {
        // Arrange
        final int end = 5;
        this.getProblem().setEnd(end);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 19;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForProvided342() {
        // Arrange
        final int start = 342;
        this.getProblem().setStart(start);
        this.getProblem().setEnd(start);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 23;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForProvided115() {
        // Arrange
        final int start = 115;
        this.getProblem().setStart(start);
        this.getProblem().setEnd(start);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 20;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 21124;
        assertEquals(expected, returned);
    }
}
