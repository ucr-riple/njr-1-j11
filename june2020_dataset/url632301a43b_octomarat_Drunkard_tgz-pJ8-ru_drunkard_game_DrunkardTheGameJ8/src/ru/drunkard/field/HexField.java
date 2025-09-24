package ru.drunkard.field;

import ru.drunkard.utility.Point;

import java.util.ArrayList;
import java.util.List;

public class HexField extends BasicField {

    public HexField(int width, int height) {
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
        if(inOddRow(p.y)) {
            Point topleft = new Point(p.x - 1, p.y + 1);
            tryAddValidNeighbour(topleft, neighbours);
            Point bottomleft = new Point(p.x - 1, p.y - 1);
            tryAddValidNeighbour(bottomleft, neighbours);
        } else {
            Point topright = new Point(p.x + 1, p.y + 1);
            tryAddValidNeighbour(topright, neighbours);
            Point bottomright = new Point(p.x + 1, p.y - 1);
            tryAddValidNeighbour(bottomright, neighbours);
        }
        return neighbours;
    }

    private boolean inOddRow(int y) { return y % 2 != 0; }
}
