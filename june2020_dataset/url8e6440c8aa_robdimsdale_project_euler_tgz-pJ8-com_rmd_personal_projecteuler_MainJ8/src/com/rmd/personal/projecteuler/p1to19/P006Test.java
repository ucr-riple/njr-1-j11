package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P006Test extends AbstractProblemTestBase<P006> {

    public P006Test() {
        super(new P006());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int maxNumber = 10;
        this.getProblem().setMaxNumber(maxNumber);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 2640L;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 25164150L;
        assertEquals(expected, returned);
    }
}
