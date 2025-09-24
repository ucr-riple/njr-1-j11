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
package ch.thn.util.tree;

import java.util.EventObject;

import ch.thn.util.tree.core.CollectionTreeNodeInterface;

/**
 * 
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 * @param <V>
 * @param <N>
 */
public class TreeNodeEvent<N extends CollectionTreeNodeInterface<?, N>> extends EventObject {
	private static final long serialVersionUID = -7895991400838880597L;

	private N node = null;
	private N oldNode = null;
	private N parentOfRemoved = null;

	private int nodeIndexOfRemoved = 0;

	/**
	 * 
	 * 
	 * @param source The source node
	 * @param nodes Affected node
	 */
	public TreeNodeEvent(N source, N node) {
		this(source, node, null, null, -1);
	}

	/**
	 * 
	 * 
	 * @param source The source node
	 * @param node Affected node
	 * @param parentOfRemoved The parent node of the affected node. This is mainly needed
	 * for when a node is removed so that its parent can be accessed to determine
	 * the former location.
	 * @param nodeIndexOfRemoved The node index of the affected node. This is mainly needed
	 * for when a node is removed so that its position can be accessed to determine
	 * the former location.
	 */
	public TreeNodeEvent(N source, N node, N parentOfRemoved, int nodeIndexOfRemoved) {
		this(source, node, null, parentOfRemoved, nodeIndexOfRemoved);
	}

	/**
	 * For {@link TreeNodeListener#nodeReplaced(TreeNodeEvent)} events
	 * 
	 * @param source The source node
	 * @param node Affected node
	 * @param oldNode The old node
	 * @param parentOfRemoved The parent node of the affected node. This is mainly needed
	 * for when a node is removed so that its parent can be accessed to determine
	 * the former location.
	 * @param nodeIndexOfRemoved The node index of the affected node. This is mainly needed
	 * for when a node is removed so that its position can be accessed to determine
	 * the former location.
	 */
	public TreeNodeEvent(N source, N node, N oldNode, N parentOfRemoved, int nodeIndexOfRemoved) {
		super(source);
		this.node = node;
		this.oldNode = oldNode;
		this.parentOfRemoved = parentOfRemoved;
		this.nodeIndexOfRemoved = nodeIndexOfRemoved;
	}

	/**
	 * Returns the affected node (if a single node was affected, <code>null</code> if
	 * no node or multiple nodes are affected).<br />
	 * 
	 * @return
	 */
	public N getNode() {
		return node;
	}

	/**
	 * If the event is {@link TreeNodeListener#nodeReplaced(TreeNodeEvent)},
	 * this method returns the node which was in place previously. Use
	 * {@link #getNodes()} to get the new node which has been put in place for the
	 * old one.
	 * 
	 * @return
	 */
	public N getOldNode() {
		return oldNode;
	}

	/**
	 * Returns the parent node of the node which has been removed.<br />
	 * <br />
	 * If this is a node removed/replaced event, the returned parent is the former parent
	 * before the node was removed.
	 * 
	 * @return
	 */
	public N getParentNodeOfRemoved() {
		return parentOfRemoved;
	}

	/**
	 * Returns the node index of the source node which has been removed.<br />
	 * <br />
	 * If this is a node removed/replaced event, the returned index is the former index
	 * before the node was removed.
	 * 
	 * @return
	 */
	public int getNodeIndexOfRemoved() {
		return nodeIndexOfRemoved;
	}


}
