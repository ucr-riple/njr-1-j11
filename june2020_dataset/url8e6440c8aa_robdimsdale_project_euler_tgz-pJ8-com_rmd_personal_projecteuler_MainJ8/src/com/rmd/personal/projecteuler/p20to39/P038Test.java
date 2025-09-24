package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P038Test extends AbstractProblemTestBase<P038> {

    public P038Test() {
        super(new P038());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int start = 192;
        final int count = 192;
        this.getProblem().setStart(start);
        this.getProblem().setEnd(count);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 192384576;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 932718654;
        assertEquals(expected, returned);
    }
}
