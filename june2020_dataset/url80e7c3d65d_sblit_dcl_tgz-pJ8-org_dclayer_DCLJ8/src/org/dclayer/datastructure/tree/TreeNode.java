package org.dclayer.datastructure.tree;

import org.dclayer.net.Data;

/**
 * abstract class for tree nodes
 * @param <T> the type of the value
 */
public abstract class TreeNode<T> {

	/**
	 * returns the value for the given key
	 * @param key the key to return the value for
	 * @return the value for the given key
	 */
	public abstract T get(Data key);
	/**
	 * returns the value for the key that is closest to the given key (might actually even be the given key)
	 * @param key the key to return the value for
	 * @return the value for the key that is closest to the given key
	 */
	public T getClosest(Data key) {
		return getClosest(key, 0);
	}
	/**
	 * returns the value for the key that is closest to the given key (might actually even be the given key)
	 * @param key the key to return the value for
	 * @param direction 1 if at the current offset, the highest possible value should be used, -1 if the lowest should be used and 0 if the closest should be used
	 * @return the value for the key that is closest to the given key
	 */
	public abstract T getClosest(Data key, int direction);
	/**
	 * removes and returns the value for the given key
	 * @param key the key to remove and return the value for
	 * @return the value for the given key
	 */
	public abstract T remove(Data key);
	/**
	 * stores the given value for the given key
	 * @param key the key to store the value for
	 * @param value the value to store for the key
	 */
	public abstract void put(Data key, T value);
	
	/**
	 * returns true if this is a {@link FinalTreeNode}, false if this is a {@link ParentTreeNode}
	 * @return true if this is a {@link FinalTreeNode}, false if this is a {@link ParentTreeNode}
	 */
	public abstract boolean isFinal();
	/**
	 * returns the key of this {@link FinalTreeNode} (null if this is not an instance of {@link FinalTreeNode})
	 * @return the key of this {@link FinalTreeNode} (null if this is not an instance of {@link FinalTreeNode})
	 */
	public abstract Data getFinalKey();
	/**
	 * returns the value of this {@link FinalTreeNode} (null if this is not an instance of {@link FinalTreeNode})
	 * @return the value of this {@link FinalTreeNode} (null if this is not an instance of {@link FinalTreeNode})
	 */
	public abstract T getFinalValue();
	
	/**
	 * returns the children of this {@link ParentTreeNode} (null if this is not an instance of {@link ParentTreeNode})
	 * @return the children of this {@link ParentTreeNode} (null if this is not an instance of {@link ParentTreeNode})
	 */
	public abstract TreeNode<T>[] getChildren();
	public abstract String toString();
	
	/**
	 * returns a String representing this {@link TreeNode} and its children
	 * @param tree true if the representation should be multi-lined, false otherwise
	 * @param level the current level of indentation
	 * @param index the index in the parent's children array
	 * @return a String representing this {@link TreeNode} and its children
	 */
	private final String represent(boolean tree, int level, int index) {
		TreeNode<T>[] children = getChildren();
		StringBuilder b = new StringBuilder();
		String indent = null;
		if(tree) {
			StringBuilder ib = new StringBuilder();
			for(int i = 0; i < level; i++) ib.append("    ");
			indent = ib.toString();
			b.append(indent);
		}
		b.append(index);
		b.append(": ");
		b.append(this.toString());
		if(children != null) {
			int ii = 0;
			for(int i = 0; i < children.length; i++) {
				TreeNode<T> child = children[i];
				if(child == null) continue;
				if(ii++ > 0) b.append(", ");
				else b.append(" {");
				if(tree) b.append("\n");
				b.append(child.represent(tree, level+1, i));
			}
			if(ii > 0) {
				if(tree) b.append("\n").append(indent);
				b.append("}");
			}
		}
		return b.toString();
	}
	
	/**
	 * returns a String representing this {@link TreeNode} and its children
	 * @param tree true if the representation should be multi-lined, false otherwise
	 * @return a String representing this {@link TreeNode} and its children
	 */
	public String represent(boolean tree) {
		return represent(tree, 0, 0);
	}
	
}
