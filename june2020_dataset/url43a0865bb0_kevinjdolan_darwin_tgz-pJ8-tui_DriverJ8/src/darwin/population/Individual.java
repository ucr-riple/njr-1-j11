package darwin.population;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import darwin.nodeFilter.Filter;

/**
 * Store a set of trees that can be processed by the application
 * @author Kevin Dolan
 */
public class Individual implements Serializable {

	private static final long serialVersionUID = 4447678956604137679L;
	Node[] trees;
	Class<?>[] treeTypes;
	
	/**
	 * Instantiate a new blank individual
	 * @param treeTypes the return types of the trees
	 */
	public Individual(Class<?>[] treeTypes) {
		this.treeTypes = treeTypes;
		this.trees = new Node[treeTypes.length];
	}
	
	/**
	 * Instantiate a new individual with given trees
	 * @param trees the array of trees
	 */
	public Individual(Node[] trees) {
		this.trees = trees;
		treeTypes = new Class<?>[trees.length];
		for(int i = 0; i < trees.length; i++)
			treeTypes[i] = trees[i].getReturnType();		
	}
	
	/**
	 * @return the number of trees
	 */
	public int getNumTrees() {
		return trees.length;
	}
	
	/**
	 * Get the expected type of the tree
	 * @param index the index of the tree to access
	 * @return 		the expected return type of the tree
	 */
	public Class<?> getTreeType(int index) {
		return treeTypes[index];
	}
	
	/**
	 * Get a tree
	 * @param index the index of the tree to return
	 * @return		the Node object
	 */
	public Node getTree(int index) {
		return trees[index];
	}
	
	/**
	 * Set a tree
	 * @param index the index of the tree to set
	 * @param tree  the Node object to set
	 * @throws TypeMismatchException
	 */
	public void setTree(int index, Node tree) throws TypeMismatchException {
		if(treeTypes[index].isAssignableFrom(tree.getReturnType())) {
			trees[index] = tree;
			trees[index].setGrandParent(this, index);
		}
		else
			throw new TypeMismatchException();
	}
	
	/**
	 * @return the number of nodes in this individual
	 */
	public int getComplexity() {
		int sum = 0;
		for(int i = 0; i < trees.length; i++)
			sum += trees[i].getSize(true);
		return sum;
	}
	
	/**
	 * Check if this node is equal to the other
	 * @param other the other node
	 * @return		true if it is the case
	 */
	public boolean equals(Individual other) {
		for(int i = 0; i < trees.length; i++)
			if(! (trees[i].equals(other.getTree(i)) ))
				return false;
		return true;
	}
	
	public Individual clone() {
		Individual individual = new Individual(treeTypes);
		for(int i = 0; i < treeTypes.length; i++)
			try {
				Node clonedTree = trees[i].cloneSubtree();
				individual.setTree(i, clonedTree);
			}
			catch (TypeMismatchException e) {
				//Logically inaccessible
				e.printStackTrace();
				return null;
			}
		return individual;
	}
	
	/**
	 * Apply a filter to all trees in this individual
	 * @param filter the filter to apply
	 * @return		 the list of accepted nodes
	 */
	public List<Node> filterTrees(Filter filter) {
		ArrayList<Node> list = new ArrayList<Node>();
		for(Node node : trees)
			list.addAll(node.filterSubtree(filter, true));
		return list;
	}
	
	public String toString() {
		String s = "";
		for(int i = 0; i < trees.length; i++)
			s += "Tree #" + i + ": " + trees[i];
		return s;
	}

}