package com.rmd.personal.projecteuler.p20to39;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P031Test extends AbstractProblemTestBase<P031> {

    public P031Test() {
        super(new P031());
    }

    @Test
    public void runReturnsCorrectlyFor2() {
        // Arrange
        final int total = 2;
        this.getProblem().setTotal(total);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 2;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor3() {
        // Arrange
        final int total = 3;
        this.getProblem().setTotal(total);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 2;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor5() {
        // Arrange
        final int total = 5;
        this.getProblem().setTotal(total);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 4;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor6() {
        // Arrange
        final int total = 6;
        this.getProblem().setTotal(total);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 5;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor7() {
        // Arrange
        final int total = 7;
        this.getProblem().setTotal(total);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 6;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor8() {
        // Arrange
        final int total = 8;
        this.getProblem().setTotal(total);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 7;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor9() {
        // Arrange
        final int total = 9;
        this.getProblem().setTotal(total);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 8;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 73682;
        assertEquals(expected, returned);
    }
}
