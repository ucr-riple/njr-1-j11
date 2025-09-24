package ru.drunkard.field;

import ru.drunkard.utility.Point;

import java.util.ArrayList;
import java.util.List;

public class RectField extends BasicField {

    public RectField(int width, int height) {
        super(width, height);
    }

    @Override
    public List<Point> getAllNeighbours(Point p) {
        List<Point> neighbours = new ArrayList<>();
        Point left = new Point(p.x - 1, p.y);
        tryAddValidNeighbour(left, neighbours);
        Point right = new Point(p.x + 1, p.y);
        tryAddValidNeighbour(right, neighbours);
        Point upper = new Point(p.x, p.y - 1);
        tryAddValidNeighbour(upper, neighbours);
        Point lower = new Point(p.x, p.y + 1);
        tryAddValidNeighbour(lower, neighbours);
        return neighbours;
    }
}