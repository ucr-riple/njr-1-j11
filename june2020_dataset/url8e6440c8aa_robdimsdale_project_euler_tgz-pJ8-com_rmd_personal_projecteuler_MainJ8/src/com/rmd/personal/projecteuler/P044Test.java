package com.rmd.personal.projecteuler;

import org.junit.Test;

import static org.junit.Assert.*;

public class P044Test extends AbstractProblemTestBase<P044> {

    public P044Test() {
        super(new P044());
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 5482660;
        assertEquals(expected, returned);
    }
}
