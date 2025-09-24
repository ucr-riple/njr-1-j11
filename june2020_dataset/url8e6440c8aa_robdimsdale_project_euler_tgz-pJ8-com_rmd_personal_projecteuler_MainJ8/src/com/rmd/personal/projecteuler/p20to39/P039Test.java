package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P039Test extends AbstractProblemTestBase<P039> {

    public P039Test() {
        super(new P039());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int start = 120;
        final int count = 120;
        this.getProblem().setStart(start);
        this.getProblem().setEnd(count);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 120;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 840;
        assertEquals(expected, returned);
    }
}
