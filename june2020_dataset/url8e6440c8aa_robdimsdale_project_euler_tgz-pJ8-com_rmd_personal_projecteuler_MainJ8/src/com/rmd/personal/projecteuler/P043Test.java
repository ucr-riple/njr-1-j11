package com.rmd.personal.projecteuler;

import org.junit.Test;

import static org.junit.Assert.*;

public class P043Test extends AbstractProblemTestBase<P043> {

    public P043Test() {
        super(new P043());
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 16695334890L;
        assertEquals(expected, returned);
    }
}
