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
import java.util.HashSet;
import java.util.Set;

import ch.thn.util.tree.TreeIterator;
import ch.thn.util.tree.TreeNodeEvent;
import ch.thn.util.tree.TreeNodeListener;

/**
 * 
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 * @param <V>
 * @param <N>
 * @param <C>
 */
public abstract class AbstractGenericCollectionTreeNode<V, N extends AbstractGenericCollectionTreeNode<V, N, C>, C extends Collection<N>>
implements CollectionTreeNodeInterface<V, N>, Iterable<N>  {

	private C children = null;

	private N parent = null;
	private N formerParent = null;

	private V value = null;

	private Set<TreeNodeListener<N>> listeners = null;

	/**
	 * 
	 * 
	 * @param childrenCollection
	 * @param value
	 */
	public AbstractGenericCollectionTreeNode(C childrenCollection, V value) {
		this.value = value;
		this.children = childrenCollection;

		listeners = new HashSet<>();
	}

	/**
	 * <i><b>For internal use only!</b></i>
	 * 
	 * @return
	 */
	protected abstract N internalGetThis();

	/**
	 * 
	 * 
	 * @return
	 */
	protected C internalGetChildren() {
		return children;
	}

	/**
	 * 
	 * 
	 * @param childrenCollection
	 */
	protected void internalSetChildrenCollection(C childrenCollection) {
		this.children = childrenCollection;
	}

	/**
	 * <i><b>For internal use only!</b></i>
	 * 
	 * @param parent
	 */
	protected void internalSetParentNode(N parent) {
		this.parent = parent;
	}


	@Override
	public void addTreeNodeListener(TreeNodeListener<N> l) {
		listeners.add(l);
	}

	@Override
	public void removeTreeNodeListener(TreeNodeListener<N> l) {
		listeners.remove(l);
	}

	/**
	 * <b>Note:</b> This is a dummy method, since a general collection does not
	 * have an index. It exists here for firing listeners. It has to be overridden
	 * in any list tree node.<br />
	 * <br />
	 * The index of this node before the node has been removed from a tree. <br />
	 * This index is only valid immediately after a node has been removed! The index
	 * is not reset when the node is re-added to a tree.
	 * 
	 * @return
	 */
	protected int getFormerNodeIndex() {
		return -1;
	}

	/**
	 * Fire listeners for removed single node
	 * 
	 * @param removed
	 */
	protected void fireNodeRemoved(N removed) {
		for (TreeNodeListener<N> l : listeners) {
			l.nodeRemoved(new TreeNodeEvent<N>(internalGetThis(), removed, removed.getFormerParentNode(), removed.getFormerNodeIndex()));
		}
	}

	/**
	 * Fire listeners for added single node
	 * 
	 * @param added
	 */
	protected void fireNodeAdded(N added) {
		for (TreeNodeListener<N> l : listeners) {
			l.nodeAdded(new TreeNodeEvent<N>(internalGetThis(), added));
		}
	}

	/**
	 * Fire listeners for replaced node
	 * 
	 * @param oldNode
	 * @param newNode
	 */
	private void fireNodeReplaced(N newNode) {
		for (TreeNodeListener<N> l : listeners) {
			//The current node is the old node
			l.nodeReplaced(new TreeNodeEvent<N>(internalGetThis(), newNode, internalGetThis(), getFormerParentNode(), getFormerNodeIndex()));
		}
	}

	/**
	 * Fire listeners for value change in node
	 * 
	 * @param changed
	 */
	protected void fireNodeValueChanged(N changed) {
		for (TreeNodeListener<N> l : listeners) {
			l.nodeValueChanged(new TreeNodeEvent<N>(internalGetThis(), changed));
		}
	}


	@Override
	public N addChildNode(N node) {
		return internalAddChildNode(node, true);
	}

	/**
	 * 
	 * 
	 * @param node
	 * @param notify
	 * @return
	 */
	protected N internalAddChildNode(N node, boolean notify) {
		if (node.getParentNode() != null) {
			throw new TreeNodeError("The node already has a parent node set (which " +
					"means it is from another tree)");
		}

		if (children.add(node)) {
			node.internalSetParentNode(internalGetThis());

			if (notify) {
				fireNodeAdded(node);
			}

			return node;
		}

		return null;
	}

	@Override
	public N addChildNodeCopy(N node) {
		return addChildNode(nodeFactory(node));
	}

	@Override
	public boolean addChildNodes(Collection<N> nodes) {
		boolean ret = false;

		for (N node : nodes) {
			addChildNode(node);
			ret = true;
		}

		return ret;
	}

	@Override
	public void removeChildNodes() {
		//Preserve node info here, because internalRemoveChildNodes can not
		//do it because it is used in switchNodes.
		for (N node : children) {
			node.preserveNodeInfo();
		}

		internalRemoveChildNodes(true);
	}

	/**
	 * 
	 * 
	 * @param notify
	 */
	protected void internalRemoveChildNodes(boolean notify) {
		//The children might or might not be in an ordered list. Create an
		//ordered list here in whatever order the children are.
		ArrayList<N> tempChildren = new ArrayList<>(children);

		//Remove children in reverse order with the last one first.
		//This is important because when removing
		//the first child, the indexes of all the following children change.
		for (int i = tempChildren.size() - 1; i >= 0; i--) {
			internalRemoveChildNode(tempChildren.get(i), notify);
		}
	}

	/**
	 * Preserves node information so that the position of this node can be
	 * determined after the node has been removed.
	 * 
	 */
	protected void preserveNodeInfo() {
		formerParent = getParentNode();
	}

	/**
	 * The parent of this node before the node has been removed from a tree. <br />
	 * This value is only valid immediately after a node has been removed! The parent
	 * is not reset when the node is re-added to a tree.
	 * 
	 * @return
	 */
	protected N getFormerParentNode() {
		return formerParent;
	}

	@Override
	public int getChildNodesCount() {
		return children.size();
	}

	@Override
	public N getParentNode() {
		return parent;
	}

	@Override
	public N getHeadNode() {
		if (parent == null) {
			return internalGetThis();
		}

		return parent.getHeadNode();
	}

	@Override
	public V getNodeValue() {
		return value;
	}

	@Override
	public void setNodeValue(V value) {
		this.value = value;
		fireNodeValueChanged(internalGetThis());
	}

	@Override
	public N addChildNode(V value) {
		return addChildNode(nodeFactory(value));
	}

	@Override
	public boolean removeNode() {
		if (isRootNode()) {
			return false;
		}

		return getParentNode().removeChildNode(internalGetThis());
	}

	@Override
	public boolean removeChildNode(N node) {
		node.preserveNodeInfo();
		return internalRemoveChildNode(node, true);
	}

	/**
	 * 
	 * @param node
	 * @param notify
	 * @return
	 */
	protected boolean internalRemoveChildNode(N node, boolean notify) {
		node.internalSetParentNode(null);

		boolean ret = children.remove(node);

		if (notify) {
			fireNodeRemoved(node);
		}

		return ret;
	}


	@Override
	public N replaceNode(V value) {
		return replaceNode(nodeFactory(value));
	}

	@Override
	public N replaceNode(N newNode) {
		if (isRootNode()) {
			throw new TreeNodeError("Root node can not be replaced");
		}

		Collection<N> tempChildren = new ArrayList<>(children);

		removeChildNodes();

		//Transfer all child nodes to the new node
		for (N childNode : tempChildren) {
			//Add all child nodes to new parent.
			newNode.addChildNode(childNode);
		}

		preserveNodeInfo();

		//Switch nodes
		if (getParentNode() != null) {
			getParentNode().switchChildNodes(internalGetThis(), newNode);
		}

		fireNodeReplaced(newNode);

		return newNode;
	}

	/**
	 * Replaces the old node with the new node.
	 * 
	 * @param oldNode
	 * @param newNode
	 */
	protected void switchChildNodes(N oldNode, N newNode) {
		ArrayList<N> tempChildren = new ArrayList<>(children);

		int index = tempChildren.indexOf(oldNode);

		tempChildren.remove(index);
		tempChildren.add(index, newNode);

		internalRemoveChildNodes(false);

		for (N node : tempChildren) {
			internalAddChildNode(node, false);
		}

	}

	@Override
	public N getRootNode() {
		if (parent == null) {
			return internalGetThis();
		}

		return parent.getRootNode();
	}

	@Override
	public boolean isRootNode() {
		return parent == null;
	}

	@Override
	public boolean isLeafNode() {
		return getChildNodesCount() == 0;
	}

	@Override
	public int getNodeDepth() {
		if (parent == null) {
			return 0;
		}

		return parent.getNodeDepth() + 1;
	}

	@Override
	public TreeIterator<N> iterator() {
		return new TreeIterator<N>(internalGetThis());
	}

	@Override
	public TreeIterator<N> iterator(boolean subtreeOnly) {
		throw new UnsupportedOperationException("Collection/Set trees can only be iterated through the sub tree of the current node.");
	}

	@Override
	public String toString() {
		return value == null ? null : value.toString();
	}


}
