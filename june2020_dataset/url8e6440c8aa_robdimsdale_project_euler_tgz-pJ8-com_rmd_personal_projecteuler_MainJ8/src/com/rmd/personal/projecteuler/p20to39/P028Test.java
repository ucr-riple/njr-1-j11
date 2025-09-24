package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P028Test extends AbstractProblemTestBase<P028> {

    public P028Test() {
        super(new P028());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int squareSize = 5;
        this.getProblem().setSquareSize(squareSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 101;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 669171001;
        assertEquals(expected, returned);
    }
}
