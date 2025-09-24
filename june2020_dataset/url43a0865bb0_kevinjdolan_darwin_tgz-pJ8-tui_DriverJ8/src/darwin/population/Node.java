package darwin.population;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import darwin.nodeFilter.Filter;
import darwin.nodeFilter.Filterable;
import darwin.problemObject.NodeType;
import darwin.problemObject.Processable;

/**
 * Darwin-internal representation of a single node of a tree
 * @author Kevin Dolan
 */
public class Node implements Serializable, Filterable {
	
	private static final long serialVersionUID = -1550106950846205458L;
	
	private NodeType nodeType;
	private Class<?> returnType;
	private Class<?>[] childTypes;
	private Node[] childNodes;
	private Processable<?> processable;
	private int arity;
	
	private Node parent;
	private int birthOrder;
	
	private Individual grandParent;
	private int family;
	
	/**
	 * Instantiate a new node of a given type
	 * @param nodeType	  the problem-specific type of this node
	 */
	public Node(NodeType nodeType) {
		this.childTypes = nodeType.getChildTypes();
		arity = childTypes.length;
		childNodes = new Node[arity];
		this.returnType = nodeType.getReturnType();
		this.processable = nodeType.getProcessable();
		this.nodeType = nodeType;
	}
	
	/**
	 * Instantiate a new node of a given type, with processable predefined
	 * @param nodeType	  the problem-specific type of this node
	 */
	public Node(NodeType nodeType, Processable<?> processable) {
		this.childTypes = nodeType.getChildTypes();
		arity = childTypes.length;
		childNodes = new Node[arity];
		this.returnType = nodeType.getReturnType();
		this.processable = processable;
		this.nodeType = nodeType;
	}
	
	/**
	 * @return number of child nodes
	 */
	public int getArity() {
		return arity;
	}
	
	/**
	 * @return the problem-specific type of this node
	 */
	public NodeType getNodeType() {
		return nodeType;
	}
	
	/**
	 * @return the processable object of this node
	 */
	public Processable<?> getProcessable() {
		return processable;
	}
	
	/**
	 * @return the value calculated by this node
	 */
	public Object getValue(Object parameters) {
		return processable.getValue(parameters, childNodes);
	}
	
	/**
	 * @return the return type of this node
	 */
	public Class<?> getReturnType() {
		return returnType;
	}
	
	/**
	 * @param  birthOrder the index of the child you want
	 * @return 			  reference to the child node
	 */
	public Node getChildNode(int birthOrder) {
		return childNodes[birthOrder];
	}
	
	/**
	 * @return get the array of child nodes
	 */
	public Node[] getChildNodes() {
		return childNodes;
	}
	
	/**
	 * @param  birthOrder the index of the child type you want
	 * @return 			  the class object of the return type of the child
	 */
	public Class<?> getChildType(int birthOrder) {
		return childTypes[birthOrder];
	}
	
	/**
	 * @return the array of child types
	 */
	public Class<?>[] getChildTypes() {
		return childTypes;
	}
	
	/**
	 * @return the parent of this Node
	 */
	public Node getParent() {
		return parent;
	}
	
	/**
	 * @return the index of this node with respect to its parent
	 */
	public int getBirthOrder() {
		return birthOrder;
	}
	
	/**
	 * Determine what type was expected by this nodes placement
	 * @return a class object representing return type
	 */
	public Class<?> getExpectedType() {
		Class<?> expectedType;
		if(parent != null)
			expectedType = parent.getChildType(birthOrder);
		else
			expectedType = grandParent.getTreeType(family);
		return expectedType;
	}
	
	/**
	 * Set the parent and birth order of this node to reflect the tree
	 * @param birthOrder index of childNodes array to which this node is assigned
	 * @param parent	 reference to the parent node
	 */
	public void setParent(int birthOrder, Node parent) {
		this.parent = parent;
		this.birthOrder = birthOrder;
	}
	
	/**
	 * Set the child node at the given index
	 * @param birthOrder the index of the node to be set
	 * @param node		 the node to set
	 * @throws TypeMismatchException if the node is of wrong type
	 */
	public void setChildNode(int birthOrder, Node node) throws TypeMismatchException {
		if(!childTypes[birthOrder].isAssignableFrom(node.getReturnType()))
			throw new TypeMismatchException();
		childNodes[birthOrder] = node;
		node.setParent(birthOrder, this);
	}
	
	/**
	 * Set the processable object
	 * @param processable what to set the processable to
	 */
	public void setProcessable(Processable<?> processable) {
		this.processable = processable;
	}
	
	/**
	 * Replace this node with another
	 * @param node the new node
	 * @throws TypeMismatchException if the node is of wrong type
	 */
	public void replace(Node node) throws TypeMismatchException {
		if(parent != null)
			parent.setChildNode(birthOrder, node);
		else
			grandParent.setTree(family, node);
	}
	
	/**
	 * Replace this node with another, while replacing the other with this
	 * @param node the node to replace this node with
	 * @throws TypeMismatchException
	 */
	public void swap(Node node) throws TypeMismatchException {
		Node me = this;
		Node you = node;
		Node myParent = parent;
		Node yourParent = node.getParent();
		
		int which;
		Individual yourGrandParent = you.getGrandParent();
		if(yourParent != null)
			which = you.getBirthOrder();
		else
			which = you.getFamily();
		
		if(myParent != null) 
			myParent.setChildNode(me.getBirthOrder(), you);
		else
			me.getGrandParent().setTree(me.getFamily(), you);
		
		if(yourParent != null)
			yourParent.setChildNode(which, me);
		else
			yourGrandParent.setTree(which, me);
	}
	
	/**
	 * Get the number of nodes attached to this node
	 * @param inclusive whether to include this node
	 * @return 			an integer count of nodes
	 */
	public int getSize(boolean inclusive) {
		int size;
		if(inclusive)
			size = 1;
		else
			size = 0;
		for(Node node : childNodes)
			size += node.getSize(true);
		return size;
	}
	
	/**
	 * Get the length of the longest downward path to a terminal from this node
	 * @param inclusive whether to include this node
	 * @return 			an integer count of nodes
	 */
	public int getHeight(boolean inclusive) {
		int height = 0;
		for(Node node : childNodes)
			height = Math.max(node.getHeight(true), height);
		if(inclusive)
			height += 1;
		return height;
	}
	
	/**
	 * Get the length of the path from this node to the root
	 * @param inclusive whether to include this node
	 * @return 			an integer count of nodes
	 */
	public int getDepth(boolean inclusive) {
		int depth;
		if(inclusive)
			depth = 1;
		else
			depth = 0;
		if(parent != null)
			return depth + parent.getDepth(true);
		else
			return depth;
	}
	
	/**
	 * @return an identical copy of this Node tree, including all descendants
	 */
	public Node cloneSubtree() {
		try {
			Node head = clone();
			for(int i = 0; i < arity; i++)
				head.setChildNode(i, childNodes[i].cloneSubtree());
			return head;
		} catch(TypeMismatchException e) {
			//Logically inaccessible
			e.printStackTrace();
			return null;
		}
	}
	
	public Node clone() {
		Processable<?> processable = this.processable.clone();
		return new Node(nodeType, processable);
	}
	
	/**
	 * Filter the subtree beginning with this node
	 * @param filter    the filter to be applied to the subtree
	 * @param inclusive whether to include this node
	 * @return			the list of all nodes accepted by the filter
	 */
	public List<Node> filterSubtree(Filter filter, boolean inclusive) {
		ArrayList<Node> list = new ArrayList<Node>();
		if(inclusive && filter.accept(this))
			list.add(this);
		for(Node node : childNodes)
			list.addAll(node.filterSubtree(filter, true));
		return list;
	}
	
	
	/**
	 * Set the grandparent of this node and all descendants
	 * @param grandParent the new grandparent
	 * @param family	  the family number
	 */
	public void setGrandParent(Individual grandParent, int family) {
		this.grandParent = grandParent;
		for(Node node : childNodes)
			node.setGrandParent(grandParent, family);
		this.family = family;
	}
	
	/**
	 * @return reference to the grandparent individual
	 */
	public Individual getGrandParent() {
		return grandParent;
	}
	
	/**
	 * @return the family number
	 */
	public int getFamily() {
		return family;
	}
	
	/**
	 * Determine whether this entire subtree is equal to other
	 * @param other the subtree attached at given node
	 * @return		true if it is the case
	 */
	public boolean equals(Node other) {
		if(!processable.equals(other.getProcessable()))
			return false;
		if(childNodes.length != other.getArity())
			return false;
		for(int i = 0; i < childNodes.length; i++)
			if(!childNodes[i].equals(other.getChildNode(i)))
				return false;
		return true;
	}
	
	@Override
	public boolean isMutable() {
		return nodeType.isMutable();
	}
	
	public String toString() {
		String s = "" + processable;
		for(int i = 0; i < childNodes.length; i++)
			s += " " + childNodes[i];
		return s;
	}
}
