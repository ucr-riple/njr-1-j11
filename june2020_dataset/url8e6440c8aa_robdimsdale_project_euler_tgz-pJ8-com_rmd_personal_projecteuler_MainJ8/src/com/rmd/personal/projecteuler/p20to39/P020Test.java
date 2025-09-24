package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P020Test extends AbstractProblemTestBase<P020> {

    public P020Test() {
        super(new P020());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int n = 10;
        this.getProblem().setN(n);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 27;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 648;
        assertEquals(expected, returned);
    }
}
