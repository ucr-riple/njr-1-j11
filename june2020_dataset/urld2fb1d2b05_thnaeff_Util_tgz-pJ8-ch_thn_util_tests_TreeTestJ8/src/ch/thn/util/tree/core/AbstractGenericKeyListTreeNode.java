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


import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ch.thn.util.tree.ListTreeIterator;

import com.google.common.collect.LinkedListMultimap;

/**
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public abstract class AbstractGenericKeyListTreeNode<K, V, N extends AbstractGenericKeyListTreeNode<K, V, N>>
extends AbstractGenericMapTreeNode<K, V, N, List<N>>
implements ListTreeNodeInterface<V, N> {

	/**
	 * The node index before the node got deleted. Needed for JTree TreeModel for example
	 * to notify which node has been removed.
	 */
	private int formerNodeIndex = -1;

	/**
	 * 
	 * 
	 * @param key
	 * @param value
	 */
	public AbstractGenericKeyListTreeNode(K key, V value) {
		super(null, key, value);

		//It somehow does not work to use LinkedListMultimap.create() in the constructor
		//-> workaround
		LinkedListMultimap<K, N> childrenMap = LinkedListMultimap.create();
		internalSetChildrenMap(childrenMap);

		//Sets the internal children collection after creating the map, since
		//the values of the map are the children
		internalSetChildrenCollection((List<N>)internalGetMap().values());
	}


	/**
	 * <b><i>Hint:</i></b> Since the backing map only supports adding new nodes at the
	 * end, this method copies the values, clears the map, and rebuilds the map
	 * with the new node included at the desired index. This might not be performing
	 * very well (especially for large maps).
	 * 
	 */
	@Override
	public N addChildNodeAt(int index, N node) {
		if (node.getParentNode() != null) {
			throw new TreeNodeError("The node already has a parent node set (which " +
					"means it is from another tree)");
		}

		//A new list of the values since the list of the map will be modified
		List<N> values = new LinkedList<N>(internalGetChildren());

		if (index < 0 || index > values.size()) {
			throw new IndexOutOfBoundsException("Index " + index +
					" is out of bounds (max. " + (values.size() - 1) + ")");
		}

		//Just clear the map, don't disconnect any children from their parent
		internalGetMap().clear();

		int count = 0;
		for (N value : values) {
			if (count == index) {
				//Add the new node at its position
				internalGetMap().put(node.getNodeKey(), node);
				node.internalSetParentNode(internalGetThis());
			}

			internalGetMap().put(value.getNodeKey(), value);
			count++;
		}

		fireNodeAdded(node);

		return node;
	}

	@Override
	public List<N> getChildNodes() {
		return Collections.unmodifiableList(internalGetChildren());
	}

	/**
	 * 
	 * 
	 * @param key
	 * @param index
	 * @return
	 */
	public N getChildNode(K key, int index) {
		return internalGetChildren(key).get(index);
	}


	@Override
	public N addChildNodeCopyAt(int index, N node) {
		return addChildNodeAt(index, nodeFactory(node.getNodeValue()));
	}

	@Override
	public boolean addChildNodesAt(int index, Collection<N> nodes) {
		return internalGetChildren().addAll(index, nodes);
	}

	@Override
	public N addChildNodeAt(int index, V value) {
		return addChildNodeAt(index, nodeFactory(value));
	}

	@Override
	public N getChildNode(int index) {
		return internalGetChildren().get(index);
	}

	@Override
	public N getFirstChildNode() {
		if (getChildNodesCount() == 0) {
			return null;
		}

		return getChildNode(0);
	}

	@Override
	public N getLastChildNode() {
		if (getChildNodesCount() == 0) {
			return null;
		}

		return getChildNode(getChildNodesCount() - 1);
	}

	@Override
	public N getFirstSibling() {
		if (isRootNode()) {
			return internalGetThis();
		}

		return getParentNode().getChildNode(0);
	}

	@Override
	public N getLastSibling() {
		if (isRootNode()) {
			return internalGetThis();
		}

		return getParentNode().getChildNode(getChildNodesCount() - 1);
	}

	@Override
	public N getNextSibling() {
		if (isLastNode()) {
			return null;
		}

		return getParentNode().getChildNode(getNodeIndex() + 1);
	}

	@Override
	public N getPreviousSibling() {
		if (isFirstNode()) {
			return null;
		}

		return getParentNode().getChildNode(getNodeIndex() - 1);
	}

	@Override
	public N removeChildNode(int index) {
		N node = internalGetChildren().get(index);
		if (node == null) {
			return null;
		}

		node.preserveNodeInfo();

		internalGetChildren().remove(index);

		//Disconnect child from its parent node
		node.internalSetParentNode(null);

		fireNodeRemoved(node);

		return node;
	}

	@Override
	public boolean removeChildNode(N node) {
		return super.removeChildNode(node);
	}

	@Override
	public boolean removeNode() {
		return super.removeNode();
	}

	@Override
	public void removeChildNodes() {
		super.removeChildNodes();
	}

	@Override
	protected void preserveNodeInfo() {
		super.preserveNodeInfo();
		formerNodeIndex = getNodeIndex();
	}

	/**
	 * The index of this node before the node has been removed from a tree. <br />
	 * This index is only valid immediately after a node has been removed! The index
	 * is not reset when the node is re-added to a tree.
	 * 
	 * @return
	 */
	@Override
	protected int getFormerNodeIndex() {
		return formerNodeIndex;
	}

	@Override
	public int getNodeIndex() {
		if (getParentNode() == null) {
			return 0;
		}

		return getParentNode().getChildNodeIndex(internalGetThis());
	}

	@Override
	public int getChildNodeIndex(N node) {
		return internalGetChildren().indexOf(node);
	}

	@Override
	public boolean isFirstNode() {
		return getNodeIndex() == 0;
	}

	@Override
	public boolean isLastNode() {
		if (isRootNode()) {
			//There is only one head node
			return true;
		}

		return getNodeIndex() == getParentNode().getChildNodesCount() - 1;
	}


	@Override
	public ListTreeIterator<N> listIterator() {
		return new ListTreeIterator<N>(internalGetThis());
	}

	@Override
	public ListTreeIterator<N> listIterator(boolean subtreeOnly) {
		return new ListTreeIterator<N>(internalGetThis(), subtreeOnly);
	}

}
