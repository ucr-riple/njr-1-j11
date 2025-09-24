package com.rmd.personal.projecteuler.p1to19;

import com.rmd.personal.projecteuler.Problem;

import java.util.HashMap;
import java.util.Map;

public class P015 implements Problem {

    private static final int DEFAULT_GRID_SIZE = 20;

    private int gridSizeX;
    private int gridSizeY;

    private Map<XYPair, Long> routesAvailableMappedByXY;

    public P015() {
        this.setGridSizeX(DEFAULT_GRID_SIZE);
        this.setGridSizeY(DEFAULT_GRID_SIZE);
        routesAvailableMappedByXY = new HashMap<XYPair, Long>();
    }

    public int getGridSizeX() {
        return gridSizeX;
    }

    public int getGridSizeY() {
        return gridSizeY;
    }

    public void setGridSizeX(int gridSizeX) {
        this.gridSizeX = gridSizeX;
    }

    public void setGridSizeY(int gridSizeY) {
        this.gridSizeY = gridSizeY;
    }

    private Map<XYPair, Long> getRoutesAvailableMappedByXY() {
        return routesAvailableMappedByXY;
    }

    @Override
    public String getDescription() {
        return "Starting in the top left corner of a 2×2 grid, and only being able to move to the right and down,\n"
                + " there are exactly 6 routes to the bottom right corner.\n\n"
                + "How many such routes are there through a 20×20 grid?";
    }

    @Override
    public String run() {
        long routesAvailable = this.numberOfRoutes(this.getGridSizeX(), this.getGridSizeY());
        return String.valueOf(routesAvailable);
    }

    private long numberOfRoutes(int x, int y) {

        if (x == 0 && y == 0) {
            return 0;
        }

        if (x == 0 || y == 0) {
            return 1;
        }

        if (x == 1) {
            return y + 1;
        }

        if (y == 1) {
            return x + 1;
        }

        // m is the bigger of the x and y, unless they're equal in which case m = n.
        int m = x > y ? x : y;
        int n = x > y ? y : x;

        XYPair xyPair = new XYPair(m, n);
        if (this.getRoutesAvailableMappedByXY().containsKey(xyPair)) {
            return this.getRoutesAvailableMappedByXY().get(xyPair);
        } else {
            long currentSum = 0;
            for (int i = 0; i <= n; i++) {
                currentSum += this.numberOfRoutes(m - 1, n - i);
            }
            this.getRoutesAvailableMappedByXY().put(xyPair, currentSum);
            return currentSum;
        }
    }

    private static class XYPair {
        private int x;
        private int y;

        private int getX() {
            return x;
        }

        private int getY() {
            return y;
        }

        public XYPair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }

            if (!(other instanceof XYPair)) {
                return false;
            }

            XYPair otherXy = (XYPair) other;

            return this.getX() == otherXy.getX() && this.getY() == otherXy.getY()
                    || this.getX() == otherXy.getY() && this.getY() == otherXy.getX();

        }

        @Override
        public int hashCode() {
            return x * x * y * y;
        }
    }
}
