package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P002Test extends AbstractProblemTestBase<P002> {

    public P002Test() {
        super(new P002());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int aMaxValue = 90;
        this.getProblem().setMaxValue(aMaxValue);

        // Act
        int returnedSum = Integer.valueOf(this.getProblem().run());

        // Assert
        final int expectedSum = 44;
        assertEquals(expectedSum, returnedSum);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        int returnedSum = Integer.valueOf(this.getProblem().run());

        // Assert
        final int expectedSum = 4613732;
        assertEquals(expectedSum, returnedSum);
    }
}
