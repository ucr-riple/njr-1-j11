package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P030Test extends AbstractProblemTestBase<P030> {

    public P030Test() {
        super(new P030());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int digits = 4;
        this.getProblem().setDigits(digits);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 19316;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 443839;
        assertEquals(expected, returned);
    }
}
