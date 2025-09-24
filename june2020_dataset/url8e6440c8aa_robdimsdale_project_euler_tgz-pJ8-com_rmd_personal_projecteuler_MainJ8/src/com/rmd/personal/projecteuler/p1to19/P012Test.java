package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class P012Test extends AbstractProblemTestBase<P012> {

    public P012Test() {
        super(new P012());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int numberOfDivisors = 5;
        this.getProblem().setNumberOfDivisors(numberOfDivisors);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 28;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 76576500L;
        assertEquals(expected, returned);
    }

    @Test
    @Ignore
    public void runReturnsCorrectlyForExtraHard() {
        // Act
        final int numberDivisors = 3000;
        this.getProblem().setNumberOfDivisors(numberDivisors);
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 102774672000L;
        assertEquals(expected, returned);
    }
}
