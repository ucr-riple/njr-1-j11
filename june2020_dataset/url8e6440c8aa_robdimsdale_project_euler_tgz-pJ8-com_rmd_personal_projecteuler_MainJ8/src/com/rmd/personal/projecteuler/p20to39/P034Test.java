package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P034Test extends AbstractProblemTestBase<P034> {

    public P034Test() {
        super(new P034());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int start = 144;
        final int end = 146;
        this.getProblem().setStart(start);
        this.getProblem().setEnd(end);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 145;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 40730;
        assertEquals(expected, returned);
    }
}
