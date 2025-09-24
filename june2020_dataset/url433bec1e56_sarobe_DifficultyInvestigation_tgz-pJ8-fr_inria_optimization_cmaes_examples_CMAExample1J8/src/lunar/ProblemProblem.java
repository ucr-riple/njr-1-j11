package lunar;

import com.sun.xml.internal.bind.v2.model.runtime.RuntimeNonElementRef;
import common.Params;
import common.math.Vector2d;
import controller.ShipController;
import entity.spaceship.Spaceship;
import main.MainView;
import main.ParamEnums;
import main.StatsVisualiser;
import strategy.CMAHandler;
import strategy.IStrategy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProblemProblem implements IProblem<double[]> {

    public int runIndex;
    public ParamEnums param; // which parameter is being examined?

    public List<LunarTerrain> lts;
    public double comparisonValue;

    public double valueRange = 10;
    public double thrustLimit = 20;
    public double lunarGravity = 10;
    public double friction = 1.0;
    public int randomSeed = 1000;
    public int numLandingPads = 1;
    public int landingPadSize = 5;
    public double survivableVelocity = 15;
    public double startingFuel = 10000;
    public double proximityWeight = 1.0;
    public double velocityWeight = 1.0;
    public double fuelWeight = 0.0;
    public double angleWeight = 0.0;

    public ProblemProblem(int runIndex, ParamEnums param) {
        this.runIndex = runIndex;
        this.param = param;
        lts = new ArrayList<LunarTerrain>();
    }

    public int nDim() {
        return 13;
    }

    public double fitness(double[] x) {
        setUpValues(x);

        double score = 0;

        // calculate values based on 1000 random samples
        LunarProblem lp = new LunarProblem();
        double randMeanFitness = 0;
        double randStdDevFitness = 0;
        double M2 = 0;
        for(int j = 0; j<1000; j++) {
            double fitness =  lp.fitness(getRandomIndividual(lp.nDim()));
            double delta = fitness - randMeanFitness;
            randMeanFitness = randMeanFitness + (delta/(j+1));
            M2 = M2 + delta*(fitness - randMeanFitness);
        }
        randStdDevFitness = Math.sqrt(M2/1000);

        // if a problem has an init std dev of 0 it is likely intractable and not worth solving
        if(randStdDevFitness == 0) {
            score = Double.POSITIVE_INFINITY;
        } else {
            // get evolved solutions
            for(int i=0; i<3; i++) {
                lp = new LunarProblem();
                CMAHandler cma = new CMAHandler(lp, 0, 0, Params.valueRange, Params.popSize);

                while((!cma.hasCompleted())) {
                    cma.run();
                }
                cma.finish();

                double endBestFitness = cma.getBestRecentFitness();
                double subScore = ((endBestFitness - randMeanFitness)/randStdDevFitness);

                score += subScore;
            }
            score /= 3;
        }



        return score;
    }

    private void setUpValues(double[] x) {
        valueRange = Math.abs(x[0]) * 10;
        thrustLimit = Math.abs(x[1]) * 10;
        lunarGravity = Math.abs(x[2]) * 10;
        friction = Math.min(Math.abs(x[3]), 1.0);
        randomSeed = (int)Math.abs(x[4]);
        numLandingPads = (int)Math.abs(x[5]);
        landingPadSize = (int)Math.abs(x[6]);
        survivableVelocity = Math.abs(x[7]) * 10;
        startingFuel = Math.abs(x[8]) * 10000;
        proximityWeight = Math.abs(x[9]);
        velocityWeight = Math.abs(x[10]);
        fuelWeight = Math.abs(x[11]);
        angleWeight = Math.abs(x[12]);

        Params.valueRange =
                ((param == ParamEnums.ALL_PARAMS) || (param == ParamEnums.VALUE_RANGE)) ? valueRange
                        : Params.valueRangeDefault;
        Params.thrustLimit =
                ((param == ParamEnums.ALL_PARAMS) || (param == ParamEnums.THRUST)) ? thrustLimit
                        : Params.thrustLimitDefault;
        Params.lunarGravity =
                ((param == ParamEnums.ALL_PARAMS) || (param == ParamEnums.GRAVITY)) ? lunarGravity
                        : Params.lunarGravityDefault;
        Params.friction =
                ((param == ParamEnums.ALL_PARAMS) || (param == ParamEnums.FRICTION)) ? friction
                        : Params.frictionDefault;
        Params.randomSeed =
                ((param == ParamEnums.ALL_PARAMS) || (param == ParamEnums.RANDOM_SEED)) ? randomSeed
                        : Params.randomSeedDefault;
        Params.numLandingPads =
                ((param == ParamEnums.ALL_PARAMS) || (param == ParamEnums.NUM_PADS)) ? numLandingPads
                        : Params.numLandingPadsDefault;
        Params.landingPadSize =
                ((param == ParamEnums.ALL_PARAMS) || (param == ParamEnums.PAD_SIZE)) ? landingPadSize
                        : Params.landingPadSizeDefault;
        Params.survivableVelocity =
                ((param == ParamEnums.ALL_PARAMS) || (param == ParamEnums.SURVIVE_VEL)) ? survivableVelocity
                        : Params.survivableVelocityDefault;
        Params.startingFuel =
                ((param == ParamEnums.ALL_PARAMS) || (param == ParamEnums.FUEL)) ? startingFuel
                        : Params.startingFuelDefault;
        Params.proximityWeight =
                ((param == ParamEnums.ALL_PARAMS) || (param == ParamEnums.W_PROXIMITY)) ? proximityWeight : 1.0;
        Params.velocityWeight =
                ((param == ParamEnums.ALL_PARAMS) || (param == ParamEnums.W_VELOCITY)) ? velocityWeight : 1.0;
        Params.fuelWeight =
                ((param == ParamEnums.ALL_PARAMS) || (param == ParamEnums.W_FUEL)) ? fuelWeight : 0.0;
        Params.angleWeight =
                ((param == ParamEnums.ALL_PARAMS) || (param == ParamEnums.W_ANGLE)) ? angleWeight : 1.0;

//        Params.numEvals = 10000;

        if(MainView.mainView != null) {
            MainView mainView = MainView.mainView;
            mainView.cp.updateValues();
        }
    }


    // USED TO PRINT OUT THE VALUE FOR PROBLEMS BEING RUN IN THE GRAPHICAL VIEW
    public static double evaluateWithCurrentParams(double bestFitness) {
        double score = 0;

        // calculate values based on 1000 random samples
        LunarProblem lp = new LunarProblem();
        double randMeanFitness = 0;
        double randStdDevFitness = 0;
        double M2 = 0;
        for(int j = 0; j<1000; j++) {
            double fitness =  lp.fitness(getRandomIndividual(lp.nDim()));
            double delta = fitness - randMeanFitness;
            randMeanFitness = randMeanFitness + (delta/(j+1));
            M2 = M2 + delta*(fitness - randMeanFitness);
        }
        randStdDevFitness = Math.sqrt(M2/1000);

        score = ((bestFitness - randMeanFitness)/randStdDevFitness);
        return score;
    }

    public static double[] getRandomIndividual(int dim) {
        Random r = new Random();
        double[] individual = new double[dim];
        for(int i =0; i<individual.length; ++i) {
            individual[i] = r.nextGaussian() * Params.valueRange;
        }
        return individual;
    }

    public double[] getInstance(double[] x) {
        return x;
    }

    public void demonstrationInit(List<Spaceship> ships, List<ShipController> conts, double[][] pop) {
        // work
        initialiseTerrains(pop);
    }

    public void demonstrate(List<Spaceship> ships, List<ShipController> conts, double[][] pop) {
        // determine if the population has changed
        // if so, redo the lunar terrains
        if(pop[0][0] != comparisonValue) {
            initialiseTerrains(pop);
        }
    }

    public void visualiseExtraInformation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for(LunarTerrain lt : lts) {
            lt.draw(g2d, 0.15f);
        }
    }

    private void initialiseTerrains(double[][] pop) {
        lts = new ArrayList<LunarTerrain>();
        for(double[] x : pop) {
            LunarTerrain lt = new LunarTerrain(Params.numPoints, (int)Math.abs(x[5]), (int)Math.abs(x[4]), Params.flatLandscape, Math.abs(x[2]) * 10, Math.min(Math.abs(x[3]), 1.0), Math.abs(x[7]) * 10);
            lts.add(lt);
        }
        comparisonValue = pop[0][0];
    }
}