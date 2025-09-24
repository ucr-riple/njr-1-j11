package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P005Test extends AbstractProblemTestBase<P005> {

    public P005Test() {
        super(new P005());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int maxFactor = 10;
        this.getProblem().setMaxFactor(maxFactor);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 2520L;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 232792560L;
        assertEquals(expected, returned);
    }
}
