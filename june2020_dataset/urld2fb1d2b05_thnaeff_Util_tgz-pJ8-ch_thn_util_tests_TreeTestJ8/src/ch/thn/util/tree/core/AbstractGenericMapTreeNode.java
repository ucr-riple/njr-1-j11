/**
 *    Copyright 2014 Thomas Naeff (github.com/thnaeff)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package ch.thn.util.tree.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

/**
 * 
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 * @param <K>
 * @param <V>
 * @param <N>
 * @param <C>
 */
public abstract class AbstractGenericMapTreeNode<K, V, N extends AbstractGenericMapTreeNode<K, V, N, C>, C extends Collection<N>>
extends AbstractGenericCollectionTreeNode<V, N, C>
implements MapTreeNodeInterface<K, V, N> {

	private Multimap<K, N> map = null;

	private K key = null;


	/**
	 * 
	 * 
	 * @param childrenMap
	 * @param childrenCollection
	 * @param key
	 * @param value
	 */
	public AbstractGenericMapTreeNode(LinkedListMultimap<K, N> childrenMap, K key, V value) {
		super(null, value);
		this.key = key;
		this.map = childrenMap;
	}

	/**
	 * <i><b>For internal use only!</b></i>
	 * 
	 * @return
	 */
	@Override
	protected abstract N internalGetThis();

	/**
	 * 
	 * 
	 * @param childrenMap
	 */
	protected void internalSetChildrenMap(Multimap<K, N> childrenMap) {
		this.map = childrenMap;
	}

	/**
	 * <i><b>For internal use only!</b></i>
	 * <br />
	 * Sets the key for this node. The key of a node always has to match the key of the
	 * map where the node is added as a child. This means that once a node is added
	 * as a child, the key should not be modified any more!
	 * 
	 * @param key
	 */
	protected void internalSetNodeKey(K key) {
		this.key = key;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	protected Multimap<K, N> internalGetMap() {
		return map;
	}

	/**
	 * 
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected C internalGetChildren(K key) {
		return (C)map.get(key);
	}

	@Override
	public abstract N nodeFactory(K key, V value);

	@Override
	public K getNodeKey() {
		return key;
	}

	@Override
	public Collection<N> getChildNodes(K key) {
		return Collections.unmodifiableCollection(map.get(key));
	}

	@Override
	public Collection<N> getChildNodes() {
		return Collections.unmodifiableCollection(internalGetChildren());
	}

	@Override
	public N addChildNode(N node) {
		if (node.getParentNode() != null) {
			throw new TreeNodeError("The node already has a parent node set (which " +
					"means it is from another tree)");
		}

		return internalAddChildNode(node, true);
	}

	@Override
	protected N internalAddChildNode(N node, boolean notify) {
		map.put(node.getNodeKey(), node);
		node.internalSetParentNode(internalGetThis());

		if (notify) {
			fireNodeAdded(node);
		}

		return node;
	}

	@Override
	public N addChildNode(V value) {
		return internalAddChildNode(nodeFactory(value), true);
	}

	@Override
	public N addChildNode(K key, V value) {
		N node = nodeFactory(key, value);
		node.internalSetParentNode(internalGetThis());
		map.put(key, node);
		fireNodeAdded(node);
		return node;
	}

	@Override
	public N addChildNodeCopy(N node) {
		return internalAddChildNode(nodeFactory(node), true);
	}

	@Override
	public boolean addChildNodes(Collection<N> nodes) {
		for (N node : nodes) {
			internalAddChildNode(node, true);
		}

		return true;
	}

	@Override
	public boolean addChildNodes(Multimap<K, N> nodes) {
		if (map.putAll(nodes)) {
			for (N node : nodes.values()) {
				node.internalSetParentNode(internalGetThis());
				fireNodeAdded(node);
			}

			return true;
		}

		return false;
	}

	@Override
	public Collection<N> removeChildNodes(K key) {
		for (N node : map.get(key)) {
			node.preserveNodeInfo();
		}

		//The removed children might or might not be in an ordered list. Create an
		//ordered list here in whatever order the children are.
		ArrayList<N> tempChildren = new ArrayList<>(map.removeAll(key));

		//Remove children in reverse order with the last one first.
		//This is important because when removing
		//the first child, the indexes of all the following children change.
		for (int i = tempChildren.size() - 1; i >= 0; i--) {
			N node = tempChildren.get(i);
			node.internalSetParentNode(null);
			fireNodeRemoved(node);
		}

		return tempChildren;
	}

	@Override
	public boolean removeChildNode(K key, N node) {
		node.preserveNodeInfo();

		boolean ret = map.remove(key, node);

		node.internalSetParentNode(null);

		fireNodeRemoved(node);

		return ret;
	}

	/**
	 * Replaces this node with a new node which contains the given key and value.
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public N replaceNode(K key, V value) {
		return super.replaceNode(nodeFactory(key, value));
	}

	@Override
	public int getChildNodesCount(K key) {
		return internalGetChildren(key).size();
	}

	@Override
	public Set<K> getChildNodeKeys() {
		return map.keySet();
	}

	@Override
	public boolean hasChildNodes(K key) {
		return map.containsKey(key);
	}

	@Override
	public String toString() {
		return "[" + key + "] " + getNodeValue();
	}

}
