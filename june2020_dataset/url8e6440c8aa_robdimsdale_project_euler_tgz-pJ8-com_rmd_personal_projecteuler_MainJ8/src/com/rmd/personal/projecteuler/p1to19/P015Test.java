package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.AbstractProblemTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class P015Test extends AbstractProblemTestBase<P015> {

    public P015Test() {
        super(new P015());
    }

    @Test
    public void runReturnsCorrectlyFor1x1() {
        // Arrange
        final int gridSize = 1;
        this.getProblem().setGridSizeX(gridSize);
        this.getProblem().setGridSizeY(gridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 2;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor1x2() {
        // Arrange
        final int xGridSize = 1;
        final int yGridSize = 2;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 3;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor1x3() {
        // Arrange
        final int xGridSize = 1;
        final int yGridSize = 3;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 4;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor1x4() {
        // Arrange
        final int xGridSize = 1;
        final int yGridSize = 4;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 5;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor1x5() {
        // Arrange
        final int xGridSize = 1;
        final int yGridSize = 5;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 6;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor2x2() {
        // Arrange
        final int xGridSize = 2;
        final int yGridSize = 2;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 6;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor2x3() {
        // Arrange
        final int xGridSize = 2;
        final int yGridSize = 3;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 10;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor2x4() {
        // Arrange
        final int xGridSize = 2;
        final int yGridSize = 4;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 15;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor2x5() {
        // Arrange
        final int xGridSize = 2;
        final int yGridSize = 5;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 21;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor3x3() {
        // Arrange
        final int xGridSize = 3;
        final int yGridSize = 3;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 20;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor3x4() {
        // Arrange
        final int xGridSize = 3;
        final int yGridSize = 4;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 35;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor3x5() {
        // Arrange
        final int xGridSize = 3;
        final int yGridSize = 5;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 56;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor4x4() {
        // Arrange
        final int xGridSize = 4;
        final int yGridSize = 4;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 70;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor4x5() {
        // Arrange
        final int xGridSize = 4;
        final int yGridSize = 5;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 126;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor5x5() {
        // Arrange
        final int xGridSize = 5;
        final int yGridSize = 5;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 252;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor6x6() {
        // Arrange
        final int xGridSize = 6;
        final int yGridSize = 6;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 924;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor7x7() {
        // Arrange
        final int xGridSize = 7;
        final int yGridSize = 7;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 3432;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor8x8() {
        // Arrange
        final int xGridSize = 8;
        final int yGridSize = 8;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 12870;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor9x9() {
        // Arrange
        final int xGridSize = 9;
        final int yGridSize = 9;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 48620;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor10x10() {
        // Arrange
        final int xGridSize = 10;
        final int yGridSize = 10;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 184756;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor11x11() {
        // Arrange
        final int xGridSize = 11;
        final int yGridSize = 11;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 705432;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor12x12() {
        // Arrange
        final int xGridSize = 12;
        final int yGridSize = 12;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 2704156;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor13x13() {
        // Arrange
        final int xGridSize = 13;
        final int yGridSize = 13;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 10400600;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor14x14() {
        // Arrange
        final int xGridSize = 14;
        final int yGridSize = 14;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 40116600;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor15x15() {
        // Arrange
        final int xGridSize = 15;
        final int yGridSize = 15;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 155117520;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor16x16() {
        // Arrange
        final int xGridSize = 16;
        final int yGridSize = 16;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 601080390;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor17x17() {
        // Arrange
        final int xGridSize = 17;
        final int yGridSize = 17;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 2333606220L;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor18x18() {
        // Arrange
        final int xGridSize = 18;
        final int yGridSize = 18;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 9075135300L;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyFor19x19() {
        // Arrange
        final int xGridSize = 19;
        final int yGridSize = 19;
        this.getProblem().setGridSizeX(xGridSize);
        this.getProblem().setGridSizeY(yGridSize);

        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 35345263800L;
        assertEquals(expected, returned);
    }

    @Test
    public void runReturnsCorrectlyForDefault() {
        // Act
        long returned = Long.valueOf(this.getProblem().run());

        // Assert
        final long expected = 137846528820L;
        assertEquals(expected, returned);
    }
}
