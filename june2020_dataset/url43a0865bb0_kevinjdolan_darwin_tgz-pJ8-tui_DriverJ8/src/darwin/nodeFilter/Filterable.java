package darwin.nodeFilter;

import darwin.problemObject.NodeType;

/**
 * An interface to filter something representing a node
 * @author Kevin Dolan
 */
public interface Filterable {
	
	/**
	 * @return the arity of the node
	 */
	public int getArity();
	
	/**
	 * @return the return type of the node
	 */
	public Class<?> getReturnType();
	
	/**
	 * @param index the index of the child type to access
	 * @return		the expected type of the node
	 */
	public Class<?> getChildType(int index);
	
	/**
	 * @return a Class object representing the node type
	 */
	public NodeType getNodeType();
	
	/**
	 * @param inclusive whether or not to include this node
	 * @return 			the depth of the tree anchored at this node
	 */
	public int getDepth(boolean inclusive);
	
	/**
	 * @param inclusive whether or not to include this node
	 * @return 			the height of the tree anchored at this node
	 */
	public int getHeight(boolean inclusive);
	
	/**
	 * @param inclusive whether or not to include this node
	 * @return 			the size of the tree anchored at this node
	 */
	public int getSize(boolean inclusive);
	
	/**
	 * @return true if this node has a mutable value associated with it
	 */
	public boolean isMutable();
}
