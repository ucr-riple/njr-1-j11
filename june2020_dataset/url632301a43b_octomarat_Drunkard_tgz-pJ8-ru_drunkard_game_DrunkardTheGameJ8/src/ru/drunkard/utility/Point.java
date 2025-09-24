package ru.drunkard.utility;

public class Point {
    public int x;
    public int y;
    public Point(int x, int y) { this.x = x; this.y = y; }

    @Override
    public boolean equals(Object other) {
        if(other != null && other instanceof Point) {
            return x == ((Point)other).x && y == ((Point)other).y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int p = 31; // prime number
        return x + y * p;
    }

    public static boolean rectCoversPoint(Point topleft, Point bottomright, Point point) {
        return topleft.x <= point.x && point.x <= bottomright.x &&
                topleft.y <= point.y && point.y <= bottomright.y;
    }
}
