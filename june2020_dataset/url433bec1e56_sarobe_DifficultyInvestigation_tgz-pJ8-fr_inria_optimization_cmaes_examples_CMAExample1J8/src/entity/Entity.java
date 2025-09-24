package entity;

import common.Params;
import common.math.Vector2d;

import java.awt.*;

/**
 * Created by Samuel Roberts
 * on 27/10/11
 */
public abstract class Entity {
    // mass of the object
    public double mass = 1;
    public double moment = 1;

    // position, velocity, angle and angular velocity
    public Vector2d pos;
    public Vector2d vel;
    public double rot;
    public double rotvel;

    // collision circle radius
    public double radius;

    // state
    protected boolean alive;

    // label
    protected Font labelFont;
    public String label;

    public Entity() {
        this(new Vector2d(), 0);
    }

    public Entity(Vector2d pos, double rot) {
        this(pos, new Vector2d(), rot, 0);
        labelFont = new Font("sans serif", Font.BOLD, 14);
        label = "";
        alive = true;
        radius = 0.1;
    }

    public Entity(Vector2d pos, Vector2d vel, double rot, double rotvel) {
        this.pos = pos;
        this.rot = rot;
        this.vel = vel;
        this.rotvel = rotvel;
    }

    public boolean isAlive() {
        return alive;
    }

    public void reset() {
        pos.x = 0;
        pos.y = 0;
        vel.x = 0;
        vel.y = 0;
        rot = 0;
        rotvel = 0;
        alive = true;
    }
    public void kill() {
        alive = false;
    }

    public abstract void draw(Graphics2D g);

    public void update() {
        if(alive) {
            pos.add(vel, Params.dt);
            rot += rotvel * Params.dt;

            vel.mul(Params.friction);
            rotvel *= Params.friction;
        }
    }

    public void label(String text) {
        label = text;
    }

    public String toString() {
        return label;
    }
}
