package ru.drunkard.utility;

public class BFSNodeInfo {
    public Point predecessor;
    public Integer pathLength = Integer.MAX_VALUE;

    public BFSNodeInfo(Point predecessor, Integer pathLength) {
        this.predecessor = predecessor;
        this.pathLength = pathLength;
    }
}
