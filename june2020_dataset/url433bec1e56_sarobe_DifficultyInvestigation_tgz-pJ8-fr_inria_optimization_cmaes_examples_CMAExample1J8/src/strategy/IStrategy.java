package strategy;

/**
 * Created by Samuel Roberts, 2012
 */
public interface IStrategy {

    public void init();
    public void run();
    public void finish();
    public double[][] getPopulation();
    public boolean hasCompleted();
    public long getIterations();
    public int getFuncEvals();
    public double[] getBestRecentSolution();
    public double getBestRecentFitness();
}
