package com.rmd.personal.projecteuler;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractProblem18Problem67 implements Problem {

    private static final boolean DEFAULT_PROVIDED = false;

    private boolean provided;

    private Map<Integer, int[]> triangle;

    public AbstractProblem18Problem67() {
        this.setProvided(DEFAULT_PROVIDED);
        this.triangle = new HashMap<Integer, int[]>();
    }

    protected Map<Integer, int[]> getTriangle() {
        return this.triangle;
    }

    public boolean isProvided() {
        return provided;
    }

    public void setProvided(boolean provided) {
        this.provided = provided;
    }

    @Override
    public String run() {
        if (this.isProvided()) {
            this.populateProvidedTriangle();
        } else  {
            this.populateTriangle();
        }

        long sum = this.getMaxTriangleSum();

        return String.valueOf(sum);
    }

    private void populateProvidedTriangle() {
        this.getTriangle().put(0, new int[]{3});
        this.getTriangle().put(1, new int[]{7, 4});
        this.getTriangle().put(2, new int[]{2, 4, 6});
        this.getTriangle().put(3, new int[]{8, 5, 9, 3});
    }

    protected abstract void populateTriangle();

    protected long getMaxTriangleSum() {
        int lines = this.getTriangle().size();
        for (int i = lines - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                this.getTriangle().get(i)[j]
                        += Math.max(this.getTriangle().get(i + 1)[j], this.getTriangle().get(i + 1)[j + 1]);
            }
        }
        return this.getTriangle().get(0)[0];
    }
}
