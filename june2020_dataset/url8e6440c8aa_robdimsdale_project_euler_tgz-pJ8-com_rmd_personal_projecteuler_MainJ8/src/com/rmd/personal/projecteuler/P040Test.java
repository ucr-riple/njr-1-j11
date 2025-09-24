package com.rmd.personal.projecteuler;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class P040Test extends AbstractProblemTestBase<P040> {

    public P040Test() {
        super(new P040());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int max = 20;
        final Set<Integer> indices = new HashSet<Integer>();
        indices.add(12);
        this.getProblem().setMax(max);
        this.getProblem().setIndices(indices);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 1;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 210;
        assertEquals(expected, returned);
    }
}
