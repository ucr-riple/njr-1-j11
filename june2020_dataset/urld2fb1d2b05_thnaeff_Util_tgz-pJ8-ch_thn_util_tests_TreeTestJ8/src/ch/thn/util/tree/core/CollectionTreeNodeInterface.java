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

import ch.thn.util.tree.TreeIterator;
import ch.thn.util.tree.TreeNodeListener;

/**
 * This is the base interface for any tree node
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 * @param <V> The type of the node value
 * @param <N> The type of a tree node
 */
public interface CollectionTreeNodeInterface<V, N extends CollectionTreeNodeInterface<V, N>>
extends Iterable<N> {

	/**
	 * Creates a new node which contains the given value
	 * 
	 * @param value
	 * @return
	 */
	public abstract N nodeFactory(V value);

	/**
	 * Creates a new node which contains the value (and key) of the given node
	 * 
	 * @param node
	 * @return
	 */
	public abstract N nodeFactory(N node);

	/**
	 * Adds the given listener to the list of tree node listeners
	 * 
	 * @param l
	 */
	public void addTreeNodeListener(TreeNodeListener<N> l);

	/**
	 * Removes the given listener from the list of tree node listeners
	 * 
	 * @param l
	 */
	public void removeTreeNodeListener(TreeNodeListener<N> l);

	/**
	 * Returns an unmodifiable view of the child nodes
	 * 
	 * @return
	 */
	public Collection<N> getChildNodes();


	/**
	 * Adds the given node as child node.<br />
	 * Since the given node will be added as child, its parent node will be modified
	 * and set to the current node.
	 * 
	 * @param node
	 * @return
	 * @throws TreeNodeError If the given node already has a parent node set (which
	 * means it is from another tree)
	 */
	public N addChildNode(N node);

	/**
	 * Creates a new node with the key and/or value of the given node and adds the new
	 * node as child node
	 * 
	 * @param node
	 * @return
	 */
	public N addChildNodeCopy(N node);

	/**
	 * Adds all the given child nodes
	 * 
	 * @param nodes
	 * @return
	 */
	public boolean addChildNodes(Collection<N> nodes);

	/**
	 * Removes all child nodes from this node
	 * 
	 */
	public void removeChildNodes();

	/**
	 * Returns the number of child nodes
	 * 
	 * @return
	 */
	public int getChildNodesCount();


	/**
	 * Returns the parent of this node. If this node is the tree head, <code>null</code>
	 * is returned.
	 * 
	 * @return
	 */
	public N getParentNode();

	/**
	 * Returns the head node of this tree.
	 * 
	 * @return
	 */
	public N getHeadNode();

	/**
	 * Returns the value of this node.
	 * 
	 * @return
	 */
	public V getNodeValue();

	/**
	 * Sets the value of this node
	 * 
	 * @param value
	 */
	public void setNodeValue(V value);

	/**
	 * Adds a new child node with the given value
	 * 
	 * @param value
	 * @return
	 */
	public N addChildNode(V value);

	/**
	 * Removes this node. The root node can not be removed.
	 * 
	 * @return
	 */
	public boolean removeNode();

	/**
	 * Removes the given node from the children
	 * 
	 * @param node
	 * @return
	 */
	public boolean removeChildNode(N node);

	/**
	 * Replaces this node with a new node which has the given value
	 * 
	 * @param value
	 * @return
	 */
	public N replaceNode(V value);

	/**
	 * Replaces this node with the given node
	 * 
	 * @param newNode
	 * @return
	 */
	public N replaceNode(N newNode);

	/**
	 * Returns the root node of this tree. The root node is the head node of the tree.
	 * 
	 * @return
	 */
	public N getRootNode();

	/**
	 * Checks if this node is a head node. A head node is the top node of a tree
	 * (without a parent node).
	 * 
	 * @return
	 */
	public boolean isRootNode();

	/**
	 * Checks if this node is a leaf node. A leaf node is a node without children.
	 * 
	 * @return
	 */
	public boolean isLeafNode();

	/**
	 * Returns the depth of this node
	 * 
	 * @return
	 */
	public int getNodeDepth();

	/**
	 * Returns an iterator over the nodes of this tree.
	 * 
	 */
	@Override
	public TreeIterator<N> iterator();

	/**
	 * Returns an iterator over the nodes of this tree.
	 * 
	 * 
	 * @param subtreeOnly If <code>true</code>, it only iterates through the sub
	 * tree/branch of the current node (including the current node as root node of the
	 * sub tree).
	 * @return
	 */
	public TreeIterator<N> iterator(boolean subtreeOnly);

}
