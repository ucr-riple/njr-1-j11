package controller;

import entity.spaceship.Spaceship;


public class ShipController implements IController {

    public Spaceship ship; // the ship this controller controls

    protected double[] actionSequence;
    protected int timestepsTilNextAction = 0;
    protected int currActionIndex = 0;

    public ShipController(Spaceship ship, double[] actionSequence) {
        this.ship = ship;
        this.actionSequence = actionSequence;
    }

    public void think() {

        ship.thrust(actionSequence[currActionIndex]);
        ship.spin(actionSequence[currActionIndex+1]);

        timestepsTilNextAction++;
        if(timestepsTilNextAction > actionSequence[currActionIndex + 2]) {
            timestepsTilNextAction = 0;
            currActionIndex += 3;
            if(currActionIndex >= actionSequence.length) {
                currActionIndex = 0; // wrap around
            }
        }
    }

    public Spaceship getShip() {
        return ship;
    }

    public double getCurrentThrust() {
        return actionSequence[currActionIndex];
    }
}