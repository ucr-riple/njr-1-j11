package main;

import gnuplot.Grapher;
import lunar.IProblem;
import strategy.IStrategy;

public class Runner implements Runnable {

    IProblem problem;
    IStrategy handler;
    StatsVisualiser statsVisualiser;
    int runIndex;
    boolean running;

    public Runner(int runIndex, IProblem problem, IStrategy handler) {
        this.runIndex = runIndex;
        this.problem = problem;
        this.handler = handler;
        running = true;
        System.out.println("Problem dim: " + problem.nDim());
    }


    public Runner(int runIndex, IProblem problem, IStrategy handler, StatsVisualiser sv) {
        this(runIndex, problem, handler);
        this.statsVisualiser = sv;
    }

    public void run() {
        int addResultInterval = 1;

        while ((!handler.hasCompleted() && running)) {
            handler.run();
            if (handler.getIterations() % addResultInterval == 0) {
                if(statsVisualiser != null) {
                    statsVisualiser.addResult(handler.getBestRecentFitness());
                    statsVisualiser.repaint();
                }
            }
        }
        handler.finish();
        System.out.println("Function evaluations: " + handler.getFuncEvals());
        running = false;
    }

    public void end() {
        running = false;
    }

    public boolean hasEnded() {
        return running;
    }
}
