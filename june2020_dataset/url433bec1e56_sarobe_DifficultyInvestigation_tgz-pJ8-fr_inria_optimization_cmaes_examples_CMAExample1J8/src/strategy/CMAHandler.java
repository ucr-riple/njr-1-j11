package strategy;

import common.Params;
import fr.inria.optimization.cmaes.CMAEvolutionStrategy;
import gnuplot.Grapher;
import lunar.IProblem;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Samuel Roberts
 * on 08/02/12
 */
public class CMAHandler implements IStrategy {

    public IProblem problem;
    public CMAEvolutionStrategy cma;
    private int funcEvals;
    private int runIndex;

    private double[] fitness;
    private double mean;
    private double stddev;
    private int pop;


    public CMAHandler(IProblem problem, int runIndex, double mean, double stddev, int pop) {
        this.problem = problem;
        this.runIndex = runIndex;
        this.mean = mean;
        this.stddev = stddev;
        this.pop = pop;
        init();
    }

    public void init() {
        cma = new CMAEvolutionStrategy();

        cma.setDimension(problem.nDim()); // overwrite some loaded properties
        System.out.println("CMA Dim: " + cma.getDimension());
        cma.setInitialX(mean); // in each dimension, also setTypicalX can be used
//        cma.setInitialStandardDeviation(10.0); // also a mandatory setting
        cma.setInitialStandardDeviation(stddev); // for co-evolution
        // testing handmade seed!
//        double[] testShip = {-16.0, -14.0, 0,
//                              0.0,  0.0, (3*Math.PI)/2,
//                              16.0, -14.0, Math.PI,
//                              10.0,  10.0, (3*Math.PI)/2,
//                             -10.0,  10.0, (3*Math.PI)/2};
//        double[] testShip = {0.0, -18.0, Math.PI/2,
//                             10.0, 0.0, (5*Math.PI)/4,
//                             4.0,  12.0, (3*Math.PI)/2,
//                            -4.0,  12.0, (3*Math.PI)/2,
//                            -10.0, 0.0, (7*Math.PI)/4};
//        double[] testShip = {0.0, -45.0, 0,
//                0.0, -20.0, 0,
//                0.0,  0.0, 0,
//                0.0,  20.0, 0,
//                0.0, 45.0, 0};
//        cma.setInitialX(testShip);

//        cma.options.stopMaxIter = Params.numIters;
        cma.options.stopMaxFunEvals = Params.numEvals;
        cma.options.stopFitness = -100000;
        cma.parameters.setPopulationSize(pop);


        // initialize cma and get fitness array to fill in later
        fitness = cma.init();  // new double[cma.parameters.getPopulationSize()];

        funcEvals = 0;
    }

    public void run() {
        // iteration loop
        if (!hasCompleted()) {

            // --- core iteration step ---
            double[][] pop = cma.samplePopulation(); // get a new population of solutions

            // calculate mean for graphing
            double mean = 0;

            for (int i = 0; i < pop.length; ++i) {    // for each candidate solution i
                // compute fitness/objective value
                funcEvals++;
                double fitnessValue = problem.fitness(pop[i]); // fitness is to be minimized
                fitness[i] = fitnessValue;
                mean += fitnessValue;
            }
            cma.updateDistribution(fitness);         // pass fitness array to update search distribution
            // --- end core iteration step ---

//            cma.writeToDefaultFiles();

            mean /= pop.length;
            try {
                Grapher.writeGenData(runIndex, (int) cma.getCountIter(), cma.getBestRecentFunctionValue(), mean, cma.getBestRecentX());
            } catch (Exception e) {
                System.out.println("Unsuccessful attempt to write graphing data! : " + e.getMessage());
            }

            int outmod = 20;
            if (cma.getCountIter() % (15 * outmod) == 1)
                cma.printlnAnnotation(); // might write file as well
            if (cma.getCountIter() % outmod == 1)
                cma.println();
        }

    }

    public void finish() {
        // evaluate mean value as it is the best estimator for the optimum
        cma.setFitnessOfMeanX(problem.fitness(cma.getMeanX())); // updates the best ever solution

        // final output
//        cma.writeToDefaultFiles(1);
        cma.println();
        cma.println("Terminated due to");
        for (String s : cma.stopConditions.getMessages())
            cma.println("  " + s);
        cma.println("best function value " + cma.getBestFunctionValue()
                + " at evaluation " + cma.getBestEvaluationNumber());
        cma.println("Population size: " + cma.population.length);
        cma.println("Best solution: " + Arrays.toString(cma.getBestSolution().getX()));
    }

    public double[][] getPopulation() {
        // make a copy of the population just to be safe
//        double[][] copypop = new double[cma.population.length][];
//        for(int i = 0; i < cma.population.length; i++) {
//            copypop[i] = new double[cma.population[i].length];
//            for(int j = 0; j < cma.population[i].length; j++) {
//                copypop[i][j] = cma.population[i][j];
//            }
//        }
//        return copypop;
        return cma.population;
    }

    public boolean hasCompleted() {
        return cma.stopConditions.getNumber() != 0;
    }

    public long getIterations() {
        return cma.getCountIter();
    }

    public int getFuncEvals() {
        return funcEvals;
    }

    public double[] getBestRecentSolution() {
        return cma.getBestRecentX();
    }

    public double getBestRecentFitness() {
        return cma.getBestRecentFunctionValue();
    }

    public double getRecentMeanFitness() {
        double[][] pop = cma.samplePopulation();
        double mean = 0;
        for (int i = 0; i < pop.length; ++i) {
            mean += fitness[i];
        }
        return mean;
    }

    public double getRecentStdDevFitness() {
        double[][] pop = cma.samplePopulation();
        double mean = getRecentMeanFitness();
        double stddev = 0;
        for (int i = 0; i < pop.length; ++i) {
            stddev += Math.pow(fitness[i] - mean, 2);
        }
        stddev = Math.sqrt(stddev);
        return stddev;
    }



}
