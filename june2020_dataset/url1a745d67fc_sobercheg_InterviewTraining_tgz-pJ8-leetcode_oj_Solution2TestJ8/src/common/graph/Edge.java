package common.graph;

/**
 * Created by sobercheg on 12/8/13.
 */
public class Edge {
    private int from;
    private int to;
    private double weight;

    public Edge(int from, int to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public double getWeight() {
        return weight;
    }

    public int other(int v) {
        return v == from ? to : from;
    }
}
