package darwin.problemObject;

import darwin.nodeFilter.Filterable;

/**
 * Interface to be implemented by problem-specific application
 * for the creation of some new node type
 * @author Kevin Dolan
 *
 */
public abstract class NodeType implements Filterable {

	public int getHeight(boolean inclusive) {
		if(inclusive)
			return 1;
		else
			return 0;
	}
	
	public int getDepth(boolean inclusive) {
		if(inclusive)
			return 1;
		else
			return 0;
	}
	
	public int getSize(boolean inclusive) {
		if(inclusive)
			return 1;
		else
			return 0;
	}
	
	public int getArity() {
		return getChildTypes().length;
	}
	
	public Class<?> getChildType(int index) {
		return getChildTypes()[index];
	}
	
	public NodeType getNodeType() {
		return this;
	}
	
	public boolean isMutable() {
		return false;
	}
	
	public boolean equals(NodeType other) {
		return this.getClass().equals(other.getNodeType().getClass());
	}
	
	/**
	 * @return the Processable object of this node type
	 */
	public abstract Processable<?> getProcessable();
	
	/**
	 * @return an array of the child types
	 */
	public abstract Class<?>[] getChildTypes();
}
