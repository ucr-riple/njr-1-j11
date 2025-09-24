package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P036Test extends AbstractProblemTestBase<P036> {

    public P036Test() {
        super(new P036());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int start = 585;
        final int end = 585;
        this.getProblem().setStart(start);
        this.getProblem().setEnd(end);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 585;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 872187;
        assertEquals(expected, returned);
    }
}
