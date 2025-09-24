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

/**
 * 
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 * @param <V>
 * @param <N>
 */
public abstract class AbstractGenericListTreeNode<V, N extends AbstractGenericListTreeNode<V, N>>
extends AbstractGenericCollectionTreeNode<V, N, List<N>>
implements ListTreeNodeInterface<V, N> {

	private int formerNodeIndex = -1;

	/**
	 * 
	 * @param value
	 */
	public AbstractGenericListTreeNode(V value) {
		super(new LinkedList<N>(), value);
	}

	@Override
	public List<N> getChildNodes() {
		return Collections.unmodifiableList(internalGetChildren());
	}

	@Override
	public N addChildNodeAt(int index, N node) {
		if (node.getParentNode() != null) {
			throw new TreeNodeError("The node already has a parent node set (which " +
					"means it is from another tree)");
		}

		//Throws an index out of bounds exception if the given index is not valid
		internalGetChildren().add(index, node);
		node.internalSetParentNode(internalGetThis());
		return node;
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
