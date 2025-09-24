package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P026Test extends AbstractProblemTestBase<P026> {

    public P026Test() {
        super(new P026());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int maxD = 10;
        this.getProblem().setMaxD(maxD);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 7;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 983;
        assertEquals(expected, returned);
    }
}
