package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P032Test extends AbstractProblemTestBase<P032> {

    public P032Test() {
        super(new P032());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int start = 39;
        final int end = 186;
        this.getProblem().setStart(start);
        this.getProblem().setEnd(end);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 20682;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 45228;
        assertEquals(expected, returned);
    }
}
