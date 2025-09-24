package strategy;

import lunar.IProblem;
import ssamot.mcts.ucb.optimisation.ContinuousProblem;

public class ContinuousProblemWrapper extends ContinuousProblem {

    IProblem problem;
    boolean negateFitness = false;

    public ContinuousProblemWrapper(IProblem problem, boolean negateFitness) {
        this.problem = problem;
        this.negateFitness = negateFitness;
    }

    public double getFtarget() {
        return 0;
    }

    public double evaluate(double[] x) {
        double score = problem.fitness(x);
        if(negateFitness) score *= -1;
        return score;
    }
}
