package common;

import common.math.Vector2d;

import java.util.Random;

/**
 * Created by Samuel Roberts
 * on 01/02/12
 */
public class Params {
    public static int screenWidth = 1000;
    public static int screenHeight = 900;

    public static int worldWidth = 600;
    public static int worldHeight = 800;

    public static int delay = 20;
    public static double dt = delay / 1000.0;
    public static Random rand = new Random();

    public static final String dataDirectory = "data/";

    public static final String[] strategies = {"CMA-ES", "T-HOO", "(T-HOO)"};

    public static double bracketedRange = 1E-2;

    public static int numEvals = 1000;
    public static int numEvalsMin = 1000;
    public static int numEvalsMax = 500000;

    public static int popSize = 10;
    public static int popSizeMin = 10;
    public static int popSizeMax = 100;

    public static double valueRange = 10;
    public static double valueRangeDefault = 10;
    public static double valueRangeMin = 1;
    public static double valueRangeMax = 100;

    public static double shipRadius = 10;
    public static double shipMass = 1;
    public static double shipMoment = 10;

    public static int maxActions = 4;

    public static double thrustLimit = 20;
    public static double thrustLimitDefault = 20;
    public static double thrustLimitMin = 1;
    public static double thrustLimitMax = 100;

    public static double uprightAngularTolerance = Math.PI/2;
    public static double uprightAngularToleranceMin = 0;
    public static double uprightAngularToleranceMax = Math.PI;

    public static double lunarGravity = 10;
    public static double lunarGravityDefault = 10;
    public static double lunarGravityMin = 0;
    public static double lunarGravityMax = 50;

    public static double friction = 1.0;
    public static double frictionDefault = 1.0;
    public static double frictionMin = 0.0;
    public static double frictionMax = 1.0;

    public static int randomSeed = 104353;
    public static int randomSeedDefault = 104353;
    public static int randomSeedMin = 0;
    public static int randomSeedMax = Integer.MAX_VALUE;

    public static int numLandingPads = 1;
    public static int numLandingPadsDefault = 1;
    public static int numLandingPadsMin = 1;
    public static int numLandingPadsMax = 10;

    public static int landingPadSize = 5;
    public static int landingPadSizeDefault = 5;
    public static int landingPadSizeMin = 1;
    public static int landingPadSizeMax = 20;

    public static double survivableVelocity = 15;
    public static double survivableVelocityDefault = 15;
    public static double survivableVelocityMin = 0;
    public static double survivableVelocityMax = 100;

    public static double startingFuel = 10000;
    public static double startingFuelDefault = 10000;
    public static double startingFuelMin = 1000;
    public static double startingFuelMax = 100000;

    public static int steps = 400;
    public static int stepsMin = 100;
    public static int stepsMax = 1000;

    public static boolean flatLandscape = false;

    public static Vector2d startingPoint = new Vector2d(200, 200);
    public static Vector2d landingFacing = new Vector2d(0, 1);

    public static int numPoints = 100;

    public static double proximityWeight = 1.0;
    public static double velocityWeight = 1.0;
    public static double fuelWeight = 0.0;
    public static double angleWeight = 1.0;

    public static double weightMin = 0.0;
    public static double weightMax = 10.0;


    public static void resetParams() {
        valueRange = valueRangeDefault;
        thrustLimit = thrustLimitDefault;
        lunarGravity = lunarGravityDefault;

    }
}
