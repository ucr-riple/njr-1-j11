package com.rmd.personal.projecteuler;

import org.junit.Test;

import static org.junit.Assert.*;

public class P046Test extends AbstractProblemTestBase<P046> {

    public P046Test() {
        super(new P046());
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 5777;
        assertEquals(expected, returned);
    }
}
