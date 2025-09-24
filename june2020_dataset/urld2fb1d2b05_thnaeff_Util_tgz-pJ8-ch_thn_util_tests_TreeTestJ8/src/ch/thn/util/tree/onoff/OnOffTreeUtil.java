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
package ch.thn.util.tree.onoff;

import java.util.Iterator;
import java.util.LinkedList;

import ch.thn.util.tree.TreeUtil;
import ch.thn.util.tree.core.AbstractGenericListTreeNode;
import ch.thn.util.tree.core.CollectionTreeNodeInterface;
import ch.thn.util.tree.onoff.core.OnOffTreeNodeInterface;
import ch.thn.util.tree.onoff.core.OnOffTreeNodeModifier;

/**
 * OnOff tree node: Allows a node to be hidden or ignored, allows the nodes children
 * to be hidden and has the functionality to force a node to be visible.<br />
 * <br />
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class OnOffTreeUtil {


	/**
	 * Makes a copy of the tree given with node. It takes care of hidden/invisible
	 * nodes and returns a tree (or multiple trees) which does not contain any such nodes.<br />
	 * <br />
	 * If both <code>considerIgnoredNodes</code> and <code>considerHiddenNodes</code>
	 * are set to <code>false</code>, it behaves the same as {@link TreeUtil#copyTree(AbstractGenericListTreeNode)}
	 * and just makes a copy of the given tree (copyTree might be a little faster
	 * though since many checks are not needed).<br />
	 * <br />
	 * If the first (head) tree node with contains multiple child nodes is set to
	 * ignored, the conversion results in multiple trees since each child node
	 * will be the head of a new tree.
	 * 
	 * @param node
	 * @param considerIgnoredNodes If <code>true</code>, ignored nodes are not
	 * added to the resulting tree (but their children are added).
	 * @param considerHiddenNodes If <code>true</code>, hidden nodes (and their
	 * child nodes) or hidden child nodes are not added to the resulting tree
	 * @return One or more trees which are a result of the given On-Off-Tree
	 */
	public static <N extends CollectionTreeNodeInterface<?, N> & OnOffTreeNodeInterface<N>>
	LinkedList<N> convertToSimpleTree(N node, boolean considerIgnoredNodes,
			boolean considerHiddenNodes) {
		return convertToSimpleTree(node, considerIgnoredNodes, considerHiddenNodes, null);
	}

	/**
	 * Makes a copy of the tree given with node. It takes care of hidden/invisible
	 * nodes and returns a tree (or multiple trees) which does not contain any such nodes.<br />
	 * <br />
	 * If both <code>considerIgnoredNodes</code> and <code>considerHiddenNodes</code>
	 * are set to <code>false</code>, it behaves the same as {@link TreeUtil#copyTree(AbstractGenericListTreeNode)}
	 * and just makes a copy of the given tree (copyTree might be a little faster
	 * though since many checks are not needed).<br />
	 * <br />
	 * If the first (head) tree node with contains multiple child nodes is set to
	 * ignored, the conversion results in multiple trees since each child node
	 * will be the head of a new tree.
	 * 
	 * @param node
	 * @param considerIgnoredNodes If <code>true</code>, ignored nodes are not
	 * added to the resulting tree (but their children are added).
	 * @param considerHiddenNodes If <code>true</code>, hidden nodes (and their
	 * child nodes) or hidden child nodes are not added to the resulting tree
	 * @param modifier
	 * @return One or more trees which are a result of the given On-Off-Tree
	 */
	public static <N extends CollectionTreeNodeInterface<?, N> & OnOffTreeNodeInterface<N>>
	LinkedList<N> convertToSimpleTree(N node, boolean considerIgnoredNodes,
			boolean considerHiddenNodes, OnOffTreeNodeModifier modifier) {

		LinkedList<N> newTrees = new LinkedList<N>();

		if (node.isNodeIgnored(modifier)) {
			//First node is ignored -> Search for next non ignored nodes and
			//make a tree of each non ignored node

			Iterator<N> iterator = node.getChildNodes().iterator();

			while (iterator.hasNext()) {
				N n = iterator.next();

				if (!n.isNodeIgnored(modifier)) {
					N newNode = n.nodeFactory(n);
					OnOffTreeUtil.buildChildNodes(newNode, n,
							considerIgnoredNodes, considerHiddenNodes, modifier);
					newTrees.add(newNode);
				}

			}

		} else {
			N newNode = node.nodeFactory(node);
			OnOffTreeUtil.buildChildNodes(newNode, node,
					considerIgnoredNodes, considerHiddenNodes, modifier);
			newTrees.add(newNode);
		}

		return newTrees;
	}


	/**
	 * 
	 * 
	 * @param targetNode Add the wrappedOldNode children here
	 * @param sourceNode Take the children from here
	 * @param considerIgnoredNodes
	 * @param considerHiddenNodes
	 * @param modifier
	 */
	private static <N extends CollectionTreeNodeInterface<?, N> & OnOffTreeNodeInterface<N>> void buildChildNodes(
			N targetNode, N sourceNode,
			boolean considerIgnoredNodes, boolean considerHiddenNodes,
			OnOffTreeNodeModifier modifier) {

		if (sourceNode.isLeafNode()
				|| considerHiddenNodes && sourceNode.isChildNodesHidden(modifier)) {
			//Skip if there are no child nodes or if the child nodes should not
			//be added
			return;
		}

		Iterator<N> childNodesIterator = sourceNode.getChildNodes().iterator();

		while (childNodesIterator.hasNext()) {

			N sourceChildNode = childNodesIterator.next();

			if (!sourceChildNode.forceNodeVisible(modifier)
					&& considerHiddenNodes && sourceChildNode.isNodeHidden(modifier)) {
				//Skip if the node should not be printed but do not skip
				//if the node is forced to be added
				continue;
			}

			if (considerIgnoredNodes && sourceChildNode.isNodeIgnored(modifier)) {
				//If the child node is invisible, add its child nodes to its parent
				//node
				OnOffTreeUtil.buildChildNodes(targetNode, sourceChildNode,
						considerIgnoredNodes, considerHiddenNodes, modifier);
				continue;
			} else {
				//Add a new child node with the data of the source child node
				N newTargetChildNode = targetNode.addChildNodeCopy(sourceChildNode);

				//Add all children of the source child node to the new child node
				OnOffTreeUtil.buildChildNodes(newTargetChildNode, sourceChildNode,
						considerIgnoredNodes, considerHiddenNodes, modifier);
				continue;
			}

		}

	}



}
