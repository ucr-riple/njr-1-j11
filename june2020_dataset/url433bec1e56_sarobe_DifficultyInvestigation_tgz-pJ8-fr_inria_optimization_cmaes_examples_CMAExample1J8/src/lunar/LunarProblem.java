package lunar;

import common.Params;
import common.math.Vector2d;
import controller.ShipController;
import entity.spaceship.Spaceship;
import main.MainView;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LunarProblem implements IProblem<double[]> {

    public LunarTerrain lt;
    public Map<Spaceship, Double> shipFuels;

    public LunarProblem() {
        lt = new LunarTerrain(Params.numPoints, Params.numLandingPads, Params.randomSeed, Params.flatLandscape);
    }

    public int nDim() {
        return 3 * Params.maxActions;
    }

    public double fitness(double[] x) {
        double score = Double.POSITIVE_INFINITY;

        Spaceship ship = new Spaceship();
        ship.pos.set(Params.startingPoint);

        ShipController cont = new ShipController(ship, x);

        int steps = 0;
        double fuel = Params.startingFuel;
        while(steps < Params.steps) { // && fuel > 0) {
            cont.think();

            ship.vel.add(0, Params.lunarGravity * Params.dt);
            ship.update();
//            ship.pos.wrapHorizontal(Params.worldWidth);

            // subtract fuel
            fuel -= ship.thrustForce;

            // check for collision
            if(lt.isShipColliding(ship)) {
                score = scoreTerminalState(ship, lt, fuel);
                steps = Params.steps;
            }
            steps++;
        }

        if(score == Double.POSITIVE_INFINITY) {
            // ship never landed, use backup fitness measure
            // backup fitness measure is vertical distance from landing, multiplied by 1000
            // the velocity for crashing into the landscape tends to vary within the range 0 - 1000,
            // which is the reason for this constant
            score = Math.abs(ship.pos.y - lt.getHeightAtX(ship.pos.x)) * 100;
        }

        return score;
    }


    static public double scoreTerminalState(Spaceship ship, LunarTerrain lt, double fuel) {
        double score = 0;
        // SCORE LANDING PAD PROXIMITY
//        double dist1 = Math.abs(ship.pos.x - lt.getNearestSafeLandingPoint(ship.pos).x); // conventional distance
//        double dist2 = Params.screenWidth - dist1; // wrapped distance
//        double dist = Math.min(dist1, dist2); // closest of the two distances
        double dist = Math.abs(ship.pos.x - lt.getNearestSafeLandingPoint(ship.pos).x);
        // convert distance into a value between 0 and 1 by dividing by the maximum possible distance
        double distScore = dist; // the highest possible horizontal distance
        // SCORE VELOCITY
        double vel = ship.vel.mag() - Params.survivableVelocity;
        if(vel < 0) vel = 0;
        double velScore = vel;
        // SCORE FUEL
        double fuelSpent = Params.startingFuel - fuel;
        double fuelScore = fuelSpent;
        // SCORE ANGLE
        Vector2d shipHeading = new Vector2d(0, 1);
        shipHeading.rotate(ship.rot);
        double angDiff = shipHeading.angBetween(Params.landingFacing);
        double angScore = angDiff;

        double weighTotal = Params.proximityWeight + Params.velocityWeight + Params.fuelWeight + Params.angleWeight;
        score = (distScore * Params.proximityWeight) + (velScore * Params.velocityWeight) + (fuelScore * Params.fuelWeight) + (angScore * Params.angleWeight);


        return score;
    }

    public double[] getInstance(double[] x) {
        return x;
    }

    public void demonstrationInit(List<Spaceship> ships, List<ShipController> conts, double pop[][]) {
        shipFuels = new HashMap<Spaceship, Double>();
        for(int i = 0; i < pop.length; i++) {
            double[] s = pop[i];
            Spaceship ship = new Spaceship();
            ships.add(ship);
            conts.add(new ShipController(ship, s));
            ship.pos.set(Params.startingPoint);
            shipFuels.put(ship, Params.startingFuel);
        }
    }

    public void demonstrate(List<Spaceship> ships, List<ShipController> conts, double[][] pop) {
        for(ShipController sc : conts) {
            if(sc.getShip().isAlive())
                sc.think();
        }

        synchronized (MainView.class) {
            for(Spaceship s : ships) {
                double fuel = shipFuels.get(s);
                s.vel.add(0, Params.lunarGravity * Params.dt);
                s.update();
                s.pos.wrapHorizontal(Params.screenWidth);

                // subtract fuel
                fuel -= s.thrustForce;
                shipFuels.put(s, fuel);

                if(lt.isShipColliding(s) && s.label == "") {
                    double score = scoreTerminalState(s, lt, fuel);
                    System.out.println("ship hit! velocity on impact: " + s.vel.mag() + ", score: " + score);
                    s.kill();
                    s.label(String.format("%.0f", score));
                }
            }
        }
    }

    public void visualiseExtraInformation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        lt.draw(g2d);
    }
}
