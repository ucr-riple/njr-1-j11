
package net.slreynolds.ds.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
final public class NodeArray extends GraphPoint {

	private final boolean _inlineValues;
    private final GraphPoint[] _elements;
    
    public NodeArray(NamedID ID, String name, int length, int generation, boolean inlineValues) {
        super(ID, name, generation);
        if (length < 0) {
            throw new IllegalArgumentException("length must be non-negative");
        }
        _elements = inlineValues ? new GraphPoint[length] : null;
        _inlineValues = inlineValues;
    }

    @SuppressWarnings("unchecked")
	public List<GraphPoint> getElements() {
        return _inlineValues ? a2l(_elements) : (List<GraphPoint>)Collections.EMPTY_LIST;
    }
    
    private static <T> List<T> a2l(T[] ta) {
    	List<T> ts = new ArrayList<T>();
    	for (T t : ta) {
    		ts.add(t);
    	}
    	return ts;
    }

    @Override
    public boolean areValuesInlined() {
    	return _inlineValues;
    }
    
    public void set(int i, GraphPoint gp) {
         _elements[i] = gp;
    }
    
    public int getLength() {
        return _inlineValues ? _elements.length : 0;
    }
    
    @Override
    public NodeArray putAttr(String key, Object value) {
        return (NodeArray) super.putAttr(key, value);
    }

    @Override
    public NodeArray removeAttr(String key) {
        return (NodeArray) super.removeAttr(key);
    }
    
    @Override
    final public boolean isNode() {return false;}
    @Override
    final public boolean isArray() { return true; }
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder("NodeArray [");
    	if (this.hasAttr(Named.CLASS)) {
    		sb.append( this.getAttr(Named.CLASS));
    		sb.append(' ');
    	}
        if (_elements != null) {
        	sb.append("# elements=" + getLength());
        }
        sb.append(']');
        return sb.toString();
    }

}
