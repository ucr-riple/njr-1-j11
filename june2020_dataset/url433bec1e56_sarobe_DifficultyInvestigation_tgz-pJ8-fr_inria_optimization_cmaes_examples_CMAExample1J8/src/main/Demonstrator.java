package main;

import common.Params;
import controller.ShipController;
import entity.spaceship.Spaceship;
import lunar.IProblem;
import strategy.IStrategy;

import java.util.ArrayList;
import java.util.List;

public class Demonstrator implements Runnable {

    IProblem problem;
    IStrategy handler;
    SpaceshipVisualiser sv;
    boolean runDemo;

    public Demonstrator(IProblem problem, IStrategy handler, SpaceshipVisualiser sv) {
        this.problem = problem;
        this.handler = handler;
        this.sv = sv;
    }

    public void run() {
        // Create some ships from the population
        List<Spaceship> ships = new ArrayList<Spaceship>();
        List<ShipController> conts = new ArrayList<ShipController>();
        double[][] pop = handler.getPopulation();

        // set up graphical elements

        problem.demonstrationInit(ships, conts, pop);

        runDemo = true;
        sv.startDemo(ships, problem);
        // MAIN DEMONSTRATION LOOP
        try {
            while(runDemo) {
                problem.demonstrate(ships, conts, pop);
                sv.repaint();
                sv.tick();
                Thread.sleep(Params.delay);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stopDemo() {
        runDemo = false;
    }
}
