package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P024Test extends AbstractProblemTestBase<P024> {

    public P024Test() {
        super(new P024());
    }

    @Test
    public void runReturnsCorrectlyFor3digits4th() {
        // Arrange
        final int numberOfDigits = 3;
        final int desiredPermutationIndex = 4;
        this.getProblem().setNumberOfDigits(numberOfDigits);
        this.getProblem().setDesiredPermutationIndex(desiredPermutationIndex);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 120;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor4digits19th() {
        // Arrange
        final int numberOfDigits = 4;
        final int desiredPermutationIndex = 19;
        this.getProblem().setNumberOfDigits(numberOfDigits);
        this.getProblem().setDesiredPermutationIndex(desiredPermutationIndex);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 3012;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 2783915460L;
        assertEquals(expected, returned);
    }
}
