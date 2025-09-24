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
package ch.thn.util.tree.onoff.core;

/**
 * OnOff tree node: Allows a node to be hidden or ignored, allows the nodes children
 * to be hidden and has the functionality to force a node to be visible.<br />
 * <br />
 * 
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public interface OnOffTreeNodeInterface<N> {

	/**
	 * Can be used to overwrite all the states set with {@link #ignoreNode(boolean)},
	 * {@link #hideNode(boolean)} and {@link #hideChildNodes(boolean)}. If
	 * <code>force</code> is set to <code>true</code>, all those states are
	 * ignored.
	 * 
	 * @param force
	 * @return
	 */
	public N forceNodeVisible(boolean force);

	/**
	 * 
	 * 
	 * @param modifier
	 * @return
	 */
	public boolean forceNodeVisible(OnOffTreeNodeModifier modifier);

	/**
	 * An ignored node is skipped when traveling through the tree. This means that
	 * all its child nodes will appear as child nodes of its parent node.
	 * 
	 * @param ignore
	 * @return
	 */
	public N ignoreNode(boolean ignore);

	/**
	 * 
	 * 
	 * @param modifier
	 * @return
	 */
	public boolean isNodeIgnored(OnOffTreeNodeModifier modifier);

	/**
	 * A hidden node does not show up in the tree, which also means that all
	 * its child nodes are not visible in the tree
	 * 
	 * @param hide
	 * @return
	 */
	public N hideNode(boolean hide);

	/**
	 * 
	 * 
	 * @param modifier
	 * @return
	 */
	public boolean isNodeHidden(OnOffTreeNodeModifier modifier);

	/**
	 * If this node's child nodes are hidden, they do not appear in the tree
	 * 
	 * @param hide
	 * @return
	 */
	public N hideChildNodes(boolean hide);

	/**
	 * 
	 * 
	 * @param modifier
	 * @return
	 */
	public boolean isChildNodesHidden(OnOffTreeNodeModifier modifier);

}
