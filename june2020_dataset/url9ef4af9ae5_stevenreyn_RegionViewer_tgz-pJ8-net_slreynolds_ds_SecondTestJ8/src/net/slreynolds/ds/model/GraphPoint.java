
package net.slreynolds.ds.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A graph element that can be an Edge endpoint.
 */
abstract public class GraphPoint extends Named {
   
	private final List<Edge> _neighbors;
    private final int _generation;
    
    public GraphPoint(NamedID ID, int generation) {
        super(ID);
        _neighbors = new ArrayList<Edge>();
        if (generation < 0) {
        	throw new IllegalArgumentException("generation cannot be negative");
        }
        _generation = generation;
    }
    
    public GraphPoint(NamedID ID, String name, int generation) {
        super(ID, name);
        _neighbors = new ArrayList<Edge>();
        if (generation < 0) {
        	throw new IllegalArgumentException("generation cannot be negative");
        }
        _generation = generation;
    }

    public int getGeneration() {
    	return _generation;
    }
    
    public abstract boolean areValuesInlined();
    
    public void add(Edge e) { 
        if (e.getFrom() != this) {
            throw new IllegalArgumentException("Illegal from Node in input edge");
        }
        _neighbors.add(e);
    }


    public List<Edge> getNeighbors() {
        return Collections.unmodifiableList(_neighbors);
    }

    public boolean hasEdge(Edge e) {
        return _neighbors.contains(e);
    }

    public boolean hasEdgeTo(GraphPoint other) {
        for (Edge e : _neighbors) {
            if (e.getTo().equals(other)) {
                return true;
            }
        }
        return false;
    }

    public void remove(Edge e) { 
        _neighbors.remove(e);
    }
    
    public abstract boolean isNode();
    public abstract boolean isArray();
    
   
}
