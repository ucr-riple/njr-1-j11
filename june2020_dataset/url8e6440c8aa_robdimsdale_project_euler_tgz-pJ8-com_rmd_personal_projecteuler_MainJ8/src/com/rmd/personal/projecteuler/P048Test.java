package com.rmd.personal.projecteuler;

import org.junit.Test;

import static org.junit.Assert.*;

public class P048Test extends AbstractProblemTestBase<P048> {

    public P048Test() {
        super(new P048());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int numberOfDigits = 10;
        final int n = 10;
        this.getProblem().setNumberOfDigits(numberOfDigits);
        this.getProblem().setN(n);

        // Act
        String returned = this.getProblem().run();

        // Assert
        final String expected = "0405071317";
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor2DigitsN6() {
        // Arrange
        final int numberOfDigits = 2;
        final int n = 6;
        this.getProblem().setNumberOfDigits(numberOfDigits);
        this.getProblem().setN(n);

        // Act
        String returned = this.getProblem().run();

        // Assert
        final String expected = "69";
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor3DigitsN6() {
        // Arrange
        final int numberOfDigits = 3;
        final int n = 6;
        this.getProblem().setNumberOfDigits(numberOfDigits);
        this.getProblem().setN(n);

        // Act
        String returned = this.getProblem().run();

        // Assert
        final String expected = "069";
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor4DigitsN6() {
        // Arrange
        final int numberOfDigits = 4;
        final int n = 6;
        this.getProblem().setNumberOfDigits(numberOfDigits);
        this.getProblem().setN(n);

        // Act
        String returned = this.getProblem().run();

        // Assert
        final String expected = "0069";
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        String returned = this.getProblem().run();

        // Assert
        final String expected = "9110846700";
        assertEquals(expected, returned);
    }
}
