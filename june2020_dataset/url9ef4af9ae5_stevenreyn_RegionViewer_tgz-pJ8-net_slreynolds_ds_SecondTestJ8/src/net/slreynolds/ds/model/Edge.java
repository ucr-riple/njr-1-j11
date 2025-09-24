
package net.slreynolds.ds.model;


/**
 *
 */
final public class Edge extends Named {
    
    final private GraphPoint _from;
    final private GraphPoint _to;
    
    public Edge(NamedID ID, GraphPoint from, GraphPoint to) {
        super(ID);
        _from = from;
        _to = to;
        
    }
        
    public Edge(NamedID ID, String name, GraphPoint from, GraphPoint to) {
        super(ID, name);
        _from = from;
        _to = to;
        
    }
    
    @Override
    public Edge removeAttr(String key) {
        return (Edge)super.removeAttr(key);
    }
    
    @Override
    public Edge putAttr(String key,Object value) {
        return (Edge)super.putAttr(key,value);
    }    
        
    public GraphPoint getFrom() {
        return _from;
    }
    
    public GraphPoint getTo() {
        return _to;
    }

    // ---------- Generated --------------------
    
    @Override
    public String toString() {
        return "Edge{" + "name=" + getName() + ", from=" + _from + ", to=" + _to + 
                + ' ' + attrToString() + '}';
    }

      
    
}
