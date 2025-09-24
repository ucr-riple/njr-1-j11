package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P029Test extends AbstractProblemTestBase<P029> {

    public P029Test() {
        super(new P029());
    }

    @Test
    public void runReturnsCorrectlyForMaxAB2() {
        // Arrange
        final int maxAB = 2;
        this.getProblem().setMaxAB(maxAB);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 1;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForMaxAB3() {
        // Arrange
        final int maxAB = 3;
        this.getProblem().setMaxAB(maxAB);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 4;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForMaxAB4() {
        // Arrange
        final int maxAB = 4;
        this.getProblem().setMaxAB(maxAB);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 8;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int maxAB = 5;
        this.getProblem().setMaxAB(maxAB);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 15;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForMaxAB6() {
        // Arrange
        final int maxAB = 6;
        this.getProblem().setMaxAB(maxAB);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 23;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 9183;
        assertEquals(expected, returned);
    }
}
