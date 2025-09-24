package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P003Test extends AbstractProblemTestBase<P003> {

    public P003Test() {
        super(new P003());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final long aMaxValue = 13195L;
        this.getProblem().setNumberValue(aMaxValue);

        // Act
        long returnedMaxPrimeFactor = Integer.valueOf(this.getProblem().run());

        // Assert
        final long expectedMaxPrimeFactor = 29;
        assertEquals(expectedMaxPrimeFactor, returnedMaxPrimeFactor);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returnedMaxPrimeFactor = Integer.valueOf(this.getProblem().run());

        // Assert
        final long expectedMaxPrimeFactor = 6857L;
        assertEquals(expectedMaxPrimeFactor, returnedMaxPrimeFactor);
    }
}
