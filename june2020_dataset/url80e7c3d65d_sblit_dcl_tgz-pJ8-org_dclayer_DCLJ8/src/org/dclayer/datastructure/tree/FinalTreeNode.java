package org.dclayer.datastructure.tree;

import org.dclayer.net.Data;

/**
 * a tree node with no child nodes
 * @param <T> the type of the value
 */
public class FinalTreeNode<T> extends TreeNode<T> {
	
	/**
	 * the key for the value
	 */
	private Data key;
	/**
	 * the value
	 */
	private T value;

	@Override
	public T get(Data key) {
		return this.key.equals(key) ? value : null;
	}

	@Override
	public T getClosest(Data key, int direction) {
		return value;
	}

	@Override
	public void put(Data key, T value) {
		this.key = key.copy();
		this.value = value;
	}

	@Override
	public boolean isFinal() {
		return true;
	}

	@Override
	public Data getFinalKey() {
		return key;
	}

	@Override
	public T getFinalValue() {
		return value;
	}

	@Override
	public T remove(Data key) {
		return null;
	}

	@Override
	public TreeNode<T>[] getChildren() {
		return null;
	}

	@Override
	public String toString() {
		return String.format("FinalTreeNode(key=%s, value=%s)", key.toString(), value);
	}
	
}
