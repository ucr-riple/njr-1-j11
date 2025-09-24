package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P033Test extends AbstractProblemTestBase<P033> {

    public P033Test() {
        super(new P033());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int numerator = 49;
        final int denominator = 98;
        this.getProblem().setNumeratorStart(numerator);
        this.getProblem().setNumeratorEnd(numerator);
        this.getProblem().setDenominatorEnd(denominator);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 2;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 100;
        assertEquals(expected, returned);
    }
}
