package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P019Test extends AbstractProblemTestBase<P019> {

    public P019Test() {
        super(new P019());
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 171;
        assertEquals(expected, returned);
    }

    @Test
    public void isLeapYearReturnsTrueForDivisibleBy4() {
        // Arrange
        final int year = 1996;

        // Act
        boolean result = this.getProblem().isLeapYear(year);

        // Assert
        assertTrue(result);
    }

    @Test
    public void isLeapYearReturnsTrueForDivisibleBy400() {
        // Arrange
        final int year = 2000;

        // Act
        boolean result = this.getProblem().isLeapYear(year);

        // Assert
        assertTrue(result);
    }

    @Test
    public void isLeapYearReturnsFalseForDivisibleBy100() {
        // Arrange
        final int year = 1900;

        // Act
        boolean result = this.getProblem().isLeapYear(year);

        // Assert
        assertFalse(result);
    }

    @Test
    public void transitionMonthReturnsFalseFor29FebruaryInLeapYear() {
        // Arrange
        final int day = 29;
        final int month = 2;
        final int year = 1904;

        // Act
        boolean result = this.getProblem().transitionMonth(day, month, year);

        // Assert
        assertFalse(result);
    }

    @Test
    public void transitionMonthReturnsTrueFor29FebruaryInNonYear() {
        // Arrange
        final int day = 29;
        final int month = 2;
        final int year = 1905;

        // Act
        boolean result = this.getProblem().transitionMonth(day, month, year);

        // Assert
        assertTrue(result);
    }

    @Test
    public void transitionMonthReturnsTrueFor31In30DayMonths() {
        // Arrange
        final int year = 1901;
        final int day = 31;

        // Act && Assert
        assertTrue(this.getProblem().transitionMonth(day, 4, year));
        assertTrue(this.getProblem().transitionMonth(day, 6, year));
        assertTrue(this.getProblem().transitionMonth(day, 9, year));
        assertTrue(this.getProblem().transitionMonth(day, 11, year));
    }

    @Test
    public void transitionMonthReturnsFalseFor30In30DayMonths() {
        // Arrange
        final int year = 1901;
        final int day = 30;

        // Act && Assert
        assertFalse(this.getProblem().transitionMonth(day, 4, year));
        assertFalse(this.getProblem().transitionMonth(day, 6, year));
        assertFalse(this.getProblem().transitionMonth(day, 9, year));
        assertFalse(this.getProblem().transitionMonth(day, 11, year));
    }

    @Test
    public void transitionMonthReturnsTrueFor32In31DayMonths() {
        // Arrange
        final int year = 1901;
        final int day = 32;

        // Act && Assert
        assertTrue(this.getProblem().transitionMonth(day, 1, year));
        assertTrue(this.getProblem().transitionMonth(day, 3, year));
        assertTrue(this.getProblem().transitionMonth(day, 5, year));
        assertTrue(this.getProblem().transitionMonth(day, 7, year));
        assertTrue(this.getProblem().transitionMonth(day, 8, year));
        assertTrue(this.getProblem().transitionMonth(day, 10, year));
        assertTrue(this.getProblem().transitionMonth(day, 12, year));
    }

    @Test
    public void transitionMonthReturnsFalseFor31In31DayMonths() {
        // Arrange
        final int year = 1901;
        final int day = 31;

        // Act && Assert
        assertFalse(this.getProblem().transitionMonth(day, 1, year));
        assertFalse(this.getProblem().transitionMonth(day, 3, year));
        assertFalse(this.getProblem().transitionMonth(day, 5, year));
        assertFalse(this.getProblem().transitionMonth(day, 7, year));
        assertFalse(this.getProblem().transitionMonth(day, 8, year));
        assertFalse(this.getProblem().transitionMonth(day, 10, year));
        assertFalse(this.getProblem().transitionMonth(day, 12, year));
    }
}
