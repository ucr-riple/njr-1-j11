package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P011Test extends AbstractProblemTestBase<P011> {

    public P011Test() {
        super(new P011());
    }

    @Test
    public void runReturnsCorrectlyForProvided() {
        // Arrange
        final int startColumn = 8;
        final int endColumn = 8;
        final int startRow = 6;
        final int endRow = 6;

        this.getProblem().setStartColumn(startColumn);
        this.getProblem().setEndColumn(endColumn);
        this.getProblem().setStartRow(startRow);
        this.getProblem().setEndRow(endRow);
        this.getProblem().clearAllPossibleDirections();
        this.getProblem().addPossibleDirection(P011.Direction.DIAGONAL_DOWN_RIGHT);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 1788696L;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 70600674L;
        assertEquals(expected, returned);
    }
}
