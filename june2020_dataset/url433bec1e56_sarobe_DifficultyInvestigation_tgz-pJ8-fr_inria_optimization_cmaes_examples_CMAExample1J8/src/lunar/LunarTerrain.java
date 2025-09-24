package lunar;

import common.Params;
import common.math.Vector2d;
import entity.spaceship.Spaceship;

import java.awt.*;

public class LunarTerrain {

    public double[] moonSurface;
    public int[] landingPads;
    private int numPoints;


    // values held purely for other visualisation purposes
    public double gravity = 10;
    public double friction = 1.0;
    public double survivableVelocity = 15;



    public LunarTerrain(int numPoints, int numLandingPads, int seed, boolean flat, double gravity, double friction, double survivableVelocity) {
        this(numPoints, numLandingPads, seed, flat);
        this.gravity = gravity;
        this.friction = friction;
        this.survivableVelocity = survivableVelocity;
    }

    public LunarTerrain(int numPoints, int numLandingPads, int seed, boolean flat) {
        Params.rand.setSeed(seed);

        this.numPoints = numPoints;

        // create surface
        moonSurface = new double[numPoints];
        moonSurface[0] = 7 * Params.worldHeight/8;

        // if the surface is not flat, make it jagged
        if(!flat) {
            for(int i = 1; i < moonSurface.length; i++) {
                moonSurface[i] = moonSurface[i-1] + (Params.rand.nextGaussian() * 30);

                moonSurface[i] = Math.max(Params.worldHeight / 4, moonSurface[i]);
                moonSurface[i] = Math.min(7 * Params.worldHeight / 8, moonSurface[i]);
            }
        } else {
            // else make it flat and low
            for(int i=0; i<moonSurface.length; i++) {
                moonSurface[i] = 7 * Params.worldHeight/8;
            }
        }

        // create landing pads
        landingPads = new int[numLandingPads * 2];
        for(int i = 0; i < landingPads.length; i += 2) {
            // pick a random starting point and length
            int startIndex = Params.rand.nextInt(moonSurface.length);
            int length = Params.landingPadSize;
            double height = moonSurface[startIndex];
            for(int j = startIndex; j < moonSurface.length && j < (startIndex + length); j++) {
                moonSurface[j] = height;
            }
            if(startIndex + length > moonSurface.length) {
                length = (moonSurface.length - startIndex) - 1;
            }
            landingPads[i] = startIndex;
            landingPads[i+1] = length;
        }
    }

    public double getHeightAtX(double x) {
        double xCheck = x/(Params.worldWidth/numPoints);
        int xIndex = (int)xCheck;
        xIndex = Math.min(xIndex, moonSurface.length - 1);
        xIndex = Math.max(xIndex, 0);
        double interpolation = xCheck - xIndex;
        int nextIndex = xIndex + 1;
        if(nextIndex >= moonSurface.length) nextIndex = 0;
        return moonSurface[xIndex] + interpolation * (moonSurface[nextIndex] - moonSurface[xIndex]);
    }

    public Vector2d getNearestSafeLandingPoint(Vector2d point) {
        Vector2d landingPoint = new Vector2d();
        Vector2d bestPoint = new Vector2d();
        double bestDist = Double.MAX_VALUE;

        double xCheck = point.x/(Params.worldWidth/numPoints);
        int xIndex = (int)xCheck;

        for(int i = 0; i < landingPads.length; i += 2) {
            int lowerBound = landingPads[i];
            int upperBound = landingPads[i] + (landingPads[i+1] - 1);
            landingPoint.x = (lowerBound + (landingPads[i+1]/2)) * (Params.worldWidth/moonSurface.length);
            landingPoint.y = moonSurface[lowerBound];
            if(point.dist(landingPoint) < bestDist) {
                bestPoint.set(landingPoint);
                bestDist = point.dist(landingPoint);
            }
        }

        return bestPoint;
    }

    public boolean onLandingPad(Spaceship ship) {
        boolean landed = false;
        if(isShipColliding(ship)) {
            Vector2d point = ship.pos;

            double xCheck = point.x/(Params.worldWidth/numPoints);
            int xIndex = (int)xCheck;

            for(int i = 0; i < landingPads.length; i += 2) {
                int lowerBound = landingPads[i];
                int upperBound = landingPads[i] + (landingPads[i+1] - 1);
                if(xIndex >= lowerBound && xIndex < upperBound) {
                    landed = true;
                    break;
                }
            }
        }
        return landed;
    }

    public boolean isShipColliding(Spaceship ship) {
        // check ship is within bounds
        if(ship.pos.x < 0 || ship.pos.x >= Params.worldWidth) return false;
        // check for collision
        Vector2d point = ship.pos;
        double yValueToTest = getHeightAtX(point.x);
        double shipBottomY = ship.pos.y + ship.radius;
        return shipBottomY >= yValueToTest;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        double xInterval = Params.worldWidth / (double)moonSurface.length;
        for(int i = 0; i < moonSurface.length; i++) {

            g2d.drawLine((int)(i * xInterval), (int)moonSurface[i], (int)((i+1) * xInterval), (int)moonSurface[((i+1)%moonSurface.length)]);
        }
        g2d.setColor(Color.BLUE);
        for(int i = 0; i < landingPads.length; i += 2) {
            g2d.drawLine((int)(landingPads[i] * xInterval), (int)(moonSurface[landingPads[i]]),
                    (int)((landingPads[i] + (landingPads[i+1]-1)) * xInterval), (int)(moonSurface[landingPads[i]]));
        }
    }

    public void draw(Graphics2D g2d, float alpha) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));


        double xInterval = Params.worldWidth / (double)moonSurface.length;
        for(int i = 0; i < moonSurface.length; i++) {
            g2d.setColor(Color.WHITE);
            g2d.drawLine((int)(i * xInterval), (int)moonSurface[i], (int)((i+1) * xInterval), (int)moonSurface[((i+1)%moonSurface.length)]);

            // strength of gravity
            g2d.setColor(Color.GREEN);
            g2d.drawLine((int)(i * xInterval), (int)moonSurface[i], (int)(i * xInterval), (int)(moonSurface[i] + gravity*2));
        }
        for(int i = 0; i < landingPads.length; i += 2) {
            g2d.setColor(Color.BLUE);
            g2d.drawLine((int)(landingPads[i] * xInterval), (int)(moonSurface[landingPads[i]]),
                    (int)((landingPads[i] + (landingPads[i+1]-1)) * xInterval), (int)(moonSurface[landingPads[i]]));

            // survivable velocity
            g2d.setColor(Color.RED);
            g2d.drawLine((int)(landingPads[i] * xInterval), (int)(moonSurface[landingPads[i]]), (int)(landingPads[i] * xInterval), (int)(moonSurface[landingPads[i]] - (survivableVelocity*2)));
        }

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

}
