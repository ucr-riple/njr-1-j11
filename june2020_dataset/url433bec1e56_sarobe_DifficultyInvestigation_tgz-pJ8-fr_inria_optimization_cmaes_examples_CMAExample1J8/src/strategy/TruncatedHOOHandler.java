package strategy;

import common.Params;
import gnuplot.Grapher;
import lunar.IProblem;
import ssamot.mcts.ucb.optimisation.HOOOptimiser;

import java.util.Arrays;

public class TruncatedHOOHandler implements IStrategy {

    public IProblem problem;
    private ContinuousProblemWrapper wrapper;
    private HOOOptimiser hoo;
    private int funcEvals = 0;
    private int numIters = 0;
    private int runIndex;

    public TruncatedHOOHandler(IProblem problem, int runIndex) {
        this.problem = problem;
        wrapper = new ContinuousProblemWrapper(problem, true);
        this.runIndex = runIndex;
        init();
    }

    public TruncatedHOOHandler(IProblem problem, int runIndex, double[] solution, double range) {
        this.problem = problem;
        wrapper = new ContinuousProblemWrapper(problem, true);
        this.runIndex = runIndex;
        init(solution, range);
    }

    public void init() {
        hoo = new HOOOptimiser(wrapper, problem.nDim(), Params.numEvals, -Params.valueRange, Params.valueRange, 1);
    }

    public void init(double[] solution, double range) {
        double[] min = new double[solution.length];
        double[] max = new double[solution.length];

        for(int i =0; i < solution.length; i++) {
            min[i] = solution[i] - range;
            max[i] = solution[i] + range;
        }

        hoo = new HOOOptimiser(wrapper, problem.nDim(), Params.numEvals, min, max, 1);

    }

    public void run() {
        if(!hasCompleted()) {

            hoo.runForSim(1);

            try {
                Grapher.writeGenData(runIndex, numIters, hoo.getBestValue(), 0, hoo.getBestSample());
            } catch (Exception e) {
                System.out.println("Unsuccessful attempt to write graphing data! : " + e.getMessage());
            }

            // print to system output what the best action is so far
            int outmod = 20;
            if (getIterations() % (15 * outmod) == 1)
                System.out.println("---------------------------");
            if (getIterations() % outmod == 1)
                System.out.println(getIterations() + ":\t" + hoo.getBestValue());

            numIters++;
        }
    }

    public void finish() {
        // output result!
        System.out.println("Terminated after alloted number of iterations");
        System.out.println("Best fitness value at end of run: " + hoo.getBestValue());
        System.out.println("Best solution at end of run: " + Arrays.toString(hoo.getBestSample()));
    }

    public double[][] getPopulation() {
        // return a few duplicates of the best
        double[][] population = new double[5][];
        for(int i = 0; i<population.length; i++) {
            population[i] = hoo.getBestSample();
        }

        return population;
    }

    public boolean hasCompleted() {
        return getIterations() >= Params.numEvals;
    }

    public long getIterations() {
        return numIters;
    }

    public int getFuncEvals() {
        return funcEvals;
    }

    public double[] getBestRecentSolution() {
        return hoo.getBestSample();
    }

    public double getBestRecentFitness() {
        return hoo.getBestValue();
    }
}
