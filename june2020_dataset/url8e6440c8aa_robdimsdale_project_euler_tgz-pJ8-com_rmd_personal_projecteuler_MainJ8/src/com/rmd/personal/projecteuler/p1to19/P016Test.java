package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P016Test extends AbstractProblemTestBase<P016> {

    public P016Test() {
        super(new P016());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int power = 15;
        this.getProblem().setPower(power);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 26;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 1366L;
        assertEquals(expected, returned);
    }
}
