package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P018Test extends AbstractProblemTestBase<P018> {

    public P018Test() {
        super(new P018());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        this.getProblem().setProvided(true);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 23;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 1074;
        assertEquals(expected, returned);
    }
}
