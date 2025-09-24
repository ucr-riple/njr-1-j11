package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P014Test extends AbstractProblemTestBase<P014> {

    public P014Test() {
        super(new P014());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int startVal = 13;
        this.getProblem().setStartVal(startVal);
        this.getProblem().setEndVal(startVal);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 13L;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 837799L;
        assertEquals(expected, returned);
    }
}
