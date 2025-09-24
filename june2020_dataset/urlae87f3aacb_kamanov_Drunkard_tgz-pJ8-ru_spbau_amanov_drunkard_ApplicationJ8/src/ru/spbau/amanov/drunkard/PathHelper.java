package ru.spbau.amanov.drunkard;
import ru.spbau.amanov.drunkard.GameObjects.Empty;

import java.util.*;

/**
 * Class provides functions to find path on the game field.
 *
 * @author  Karim Amanov
 */
public class PathHelper {

    /**
     * Class constructor.
     * @param f game field.
     */
    public PathHelper(AbstractField f) {
        field = f;
    }

    /**
     * Get next position.
     * @param from from postion.
     * @param to to position.
     * @return next position.
     */
    public Position getNextPos(Position from, Position to) {
        field.initFieldObjects();
        Position pos = BFS(from, to);

        if (pos == null) {
            return null;
        } else if (pos.equals(from)) {
            return to;
        }

        Position out = pos;
        while (!pos.equals(from)) {
            out = pos;
            pos = field.getObject(pos).getPrev();
        }
        return out;
    }

    /**
     * Generate random move.
     * @param initPos
     * @return
     */
    public Position generateRandomMove(Position initPos) {
        List<Position> positions = field.getPossibleSteps(initPos);
        int ind = randomGenerator.nextInt(positions.size());
        return positions.get(ind);
    }

    private Position BFS(Position from, Position to) {
        Queue<Position> queue = new ArrayDeque<Position>();
        queue.add(from);
        Position pos;
        while (!queue.isEmpty()) {
            pos = queue.poll();
            List<Position> adjList = getAdjPos(pos, to);
            if (isPathFound) {
                isPathFound = false;
                return pos;
            }

            for(Position p : adjList) {
                if (!field.getObject(p).isVisited()) {
                    field.getObject(p).setVisited(true);
                    field.getObject(p).setPrev(pos);
                    queue.add(p);
                }
            }
            field.getObject(pos).setVisited(true);
        }
        return null;
    }

    private List<Position> getAdjPos(Position pos, Position to) {
        List<Position> ls = field.getPossibleSteps(pos);
        List<Position> out = new LinkedList<Position>();
        for (Position p : ls) {
            if (p.equals(to)) {
                isPathFound = true;
                return out;
            }

            if (field.getObject(p) instanceof Empty) {
                out.add(p);
            }
        }
        return out;
    }

    private Random randomGenerator = new Random();
    private boolean isPathFound = false;
    private AbstractField field;
}
