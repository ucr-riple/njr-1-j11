package com.rmd.personal.projecteuler;

import org.junit.Test;

import static org.junit.Assert.*;

public class P067Test extends AbstractProblemTestBase<P067> {

    public P067Test() {
        super(new P067());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        this.getProblem().setProvided(true);

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
        final long expected = 7273;
        assertEquals(expected, returned);
    }
}
