package entity.spaceship;

import common.Params;
import common.math.Vector2d;
import entity.Entity;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by Samuel Roberts
 * on 24/10/11
 */
public class Spaceship extends Entity {

    // rendering details!
    public int xpShip[] = {0,16,0,-16};
    public int ypShip[] = {-20,8,-4,8};

    public double thrustForce;

    public Spaceship() {
        super();
        mass = Params.shipMass;
        moment = Params.shipMoment;
        thrustForce = 0;
        radius = Params.shipRadius;
    }

    public void thrust(double force) {
        if(!alive) return;
        // limit thrust
        if(force > Params.thrustLimit) force = Params.thrustLimit;
        else if(force < 0) force = 0;
        thrustForce = force;
        Vector2d thrustDirection = new Vector2d(0, -1);
        thrustDirection.rotate(rot);
        // reuse existing thrust vector to apply force
        thrustDirection.mul(force);
        vel.add(thrustDirection);
    }

    public void spin(double impulse) {
        if(!alive) return;
        rotvel += (impulse /= moment);
    }

    public void draw(Graphics2D g) {
        AffineTransform at = g.getTransform();

        // draw the ship itself
        g.translate(pos.x, pos.y);
        g.rotate(rot);
        g.setColor(Color.WHITE);
        g.fillPolygon(xpShip, ypShip, xpShip.length);
        g.setColor(Color.GRAY);
        g.drawPolygon(xpShip, ypShip, xpShip.length);

        // draw a line to indicate thrust force
        g.setColor(Color.RED);
        g.drawLine(0, 0, 0, ((int)thrustForce));

        // draw label if present
        g.setFont(labelFont);
        g.setColor(Color.BLUE);
        g.drawString(label, -label.length()*3, -10);

        g.setTransform(at);
    }

    public Spaceship copyShip() {
        Spaceship copy = new Spaceship();
        copy.pos = pos.copy();
        copy.rot = rot;
        copy.vel = vel.copy();
        copy.rotvel = rotvel;
        return copy;
    }

    public void kill() {
        vel.zero();
        rotvel = 0;
        alive = false;
    }

    public String toString() {
        return "[Ship] " + label;
    }
}