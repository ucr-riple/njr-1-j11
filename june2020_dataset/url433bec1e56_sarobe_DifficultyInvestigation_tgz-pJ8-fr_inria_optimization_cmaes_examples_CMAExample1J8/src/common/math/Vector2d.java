package common.math;

/* Created by sarobe 
* at 16:28:31 on 22-Jan-2010.
*/
public class Vector2d {
    public double x;
    public double y;

    public Vector2d() {
        x = 0;
        y = 0;
    }

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d(Vector2d vec) {
        x = vec.x;
        y = vec.y;
    }

    public Vector2d add(Vector2d vec) {
        x += vec.x;
        y += vec.y;
        return this;
    }

    public Vector2d add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2d add(Vector2d vec, double scl) {
        x += vec.x * scl;
        y += vec.y * scl;
        return this;
    }

    public Vector2d copy() {
        return new Vector2d(this);
    }

    public double dist(Vector2d vec) {
        double dx = x - vec.x;
        double dy = y - vec.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    public double mag() {
        return Math.sqrt(x*x + y*y);
    }

    public Vector2d mul(double scl) {
        x *= scl;
        y *= scl;
        return this;
    }

    public Vector2d rotate(double theta) { // radians
        double rotx = (x * Math.cos(theta)) - (y * Math.sin(theta));
        double roty = (x * Math.sin(theta)) + (y * Math.cos(theta));
        x = rotx;
        y = roty;
        return this;
    }

    public double theta() {
        return Math.atan2(y, x);
    }

    public double scalarProduct(Vector2d vec) {
        return (x * vec.x) + (y * vec.y);
    }

    public Vector2d set(Vector2d vec) {
        x = vec.x;
        y = vec.y;
        return this;
    }

    public Vector2d set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public boolean equals(Vector2d vec) {
        return (x == vec.x) && (y == vec.y);
    }

    public double sqDist(Vector2d vec) {
        double dx = x - vec.x;
        double dy = y - vec.y;
        return dx*dx + dy*dy;
    }

    static public double sqr(double num) {
        return num * num;
    }

    public Vector2d subtract(Vector2d vec) {
        x -= vec.x;
        y -= vec.y;
        return this;
    }

    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    public Vector2d wrap(double width, double height) {
        x = (x + width) % width;
        y = (y + height) % height;
        return this;
    }

    public Vector2d wrapHorizontal(double width) {
        x = (x + width) % width;
        return this;
    }

    public Vector2d limit(double width, double height) {
        if(x < 0) {
            x = 0;
        } else if(x > width) {
            x = width;
        }
        if(y < 0) {
            y = 0;
        } else if(y > height) {
            y = height;
        }
        return this;
    }

    public void zero() {
        x = 0;
        y = 0;
    }

    public double angBetween(Vector2d other) {
        return Math.acos(scalarProduct(other) / (mag() * other.mag()));
    }
    
    public double determinant(Vector2d other) {
        return x * other.y - other.x * y;
    }

    public Vector2d normalise() {
        double mag = mag();
        if(mag != 0) {
            x /= mag;
            y /= mag;
        }
        return this;
    }
}
