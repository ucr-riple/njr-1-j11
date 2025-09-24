package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P027Test extends AbstractProblemTestBase<P027> {

    public P027Test() {
        super(new P027());
    }

    @Test
    public void runReturnsCorrectlyForProvided1and41() {
        // Arrange
        final int maxA = 2;
        final int maxB = 42;
        this.getProblem().setMaxA(maxA);
        this.getProblem().setMaxB(maxB);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = -41;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForProvided79and1601() {
        // Arrange
        final int maxA = 80;
        final int maxB = 1602;
        this.getProblem().setMaxA(maxA);
        this.getProblem().setMaxB(maxB);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = -126479;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = -59231;
        assertEquals(expected, returned);
    }
}
