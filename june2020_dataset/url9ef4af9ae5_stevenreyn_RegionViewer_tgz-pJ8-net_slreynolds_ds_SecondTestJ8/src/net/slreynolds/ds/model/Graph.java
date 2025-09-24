
package net.slreynolds.ds.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a region of objects as a graph with nodes (GraphPoint's) and
 * edges between them.
 *
 */
final public class Graph { 
	
    final private List<GraphPoint> _points;
    
    private GraphPoint _primaryNode;
    
    public Graph() {
        _points = new ArrayList<GraphPoint>();
    }
    
    public List<GraphPoint> getGraphPoints() {
        return Collections.unmodifiableList(_points);
    }

    public GraphPoint getPrimaryGraphPoint() {
        return _primaryNode;
    }
    
    public Graph setPrimaryGraphPoint(GraphPoint n) {
        _primaryNode = n;
        add(n);
        return this;
    }
    
    public Graph add(GraphPoint n) {
        if (!_points.contains(n)) {
            _points.add(n);
        }
        return this;
    }
  
    public Graph addEdge(GraphPoint from, GraphPoint to) {
       
        return addEdge(Named.EMPTY_NAME,from,to);
    }
        
    public Graph addEdge(String name,GraphPoint from, GraphPoint to) {
        
        add(from);
        add(to);
        
        if (!from.hasEdgeTo(to)) {
            Edge e = new Edge(NamedIDGenerator.next(), name,from,to);
            from.add(e);
        }
        
        return this;
    }
    
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("Graph [nodes=[");    
        nodesToString(sb, _points);
        sb.append("\n\t, primaryNode=" + _primaryNode + "]");
        return sb.toString();
    }

    private void nodesToString(StringBuffer sb, List<GraphPoint> nodes) {
        int nodeNum = 0;
        for (GraphPoint node: nodes) {
            nodeNum++;
            if (nodeNum > 1) {
                sb.append("\n\t, ");
            }
            sb.append(node.toString());
        }
    }
    
    // ----------------- Generated Code ----------------------
  
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_points == null) ? 0 : _points.hashCode());
        result = prime * result
                + ((_primaryNode == null) ? 0 : _primaryNode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Graph other = (Graph) obj;
        if (_points == null) {
            if (other._points != null)
                return false;
        } else if (!_points.equals(other._points))
            return false;
        if (_primaryNode == null) {
            if (other._primaryNode != null)
                return false;
        } else if (!_primaryNode.equals(other._primaryNode))
            return false;
        return true;
    }
        
}
