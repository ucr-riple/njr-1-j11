package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P025Test extends AbstractProblemTestBase<P025> {

    public P025Test() {
        super(new P025());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int numberOfDigits = 3;
        this.getProblem().setNumberOfDigits(numberOfDigits);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 12;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 4782;
        assertEquals(expected, returned);
    }
}
