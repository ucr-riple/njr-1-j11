package ru.drunkard.movestrategies;

import ru.drunkard.field.GameField;
import ru.drunkard.utility.BFSNodeInfo;
import ru.drunkard.utility.Point;

import java.util.*;

public class ShortestPathMoveStrategy implements IDirectedMoveStrategy {
    private Stack<Point> latestPath = new Stack<>();

    public Point nextPosition(Point start, Point target, GameField field) {
        if(latestPathIsUpToDate(field) || recountPath(start, target, field)) {
            return latestPath.pop();
        }
        return start;
    }

    private boolean latestPathIsUpToDate(GameField field) {
        if(latestPath.isEmpty()) {
            return false;
        }
        for(Point p : latestPath) {
            if(!field.sectorIsEmpty(p.x, p.y)) {
                return false;
            }
        }
        return true;
    }

    private boolean recountPath(Point start, Point target, GameField field) {
        Map<Point, BFSNodeInfo> nodesInfo = new HashMap<>();
        nodesInfo.put(start, new BFSNodeInfo(start, 0));
        Queue<Point> elems = new LinkedList<>();
        elems.add(start);
        while (!elems.isEmpty()) {
            Point current = elems.remove();
            List<Point> neighbours = field.getFreeNeighbours(current, target);
            for(Point neighbour : neighbours) {
                BFSNodeInfo neighbourInfo = nodesInfo.get(neighbour);
                BFSNodeInfo currentInfo = nodesInfo.get(current);
                if(neighbourInfo == null || neighbourInfo.pathLength > currentInfo.pathLength + 1) {
                    nodesInfo.put(neighbour, new BFSNodeInfo(current, currentInfo.pathLength + 1));
                    elems.add(neighbour);
                }
            }
        }
        restorePath(nodesInfo, target);
        return !latestPath.isEmpty();
    }

    private void restorePath(Map<Point, BFSNodeInfo> nodesInfo, Point target) {
        latestPath.clear();
        Point current = target;
        BFSNodeInfo currentInfo = nodesInfo.get(current);
        if(currentInfo == null || currentInfo.predecessor == null) {
            return;
        }
        while (!currentInfo.predecessor.equals(current)) {
            latestPath.push(current);
            current = currentInfo.predecessor;
            currentInfo = nodesInfo.get(current);
        }
    }
}
