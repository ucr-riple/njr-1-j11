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

import ch.thn.util.tree.core.AbstractGenericKeyListTreeNode;

/**
 * OnOff tree node: Allows a node to be hidden or ignored, allows the nodes children
 * to be hidden and has the functionality to force a node to be visible.<br />
 * <br />
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public abstract class AbstractGenericOnOffKeyListTreeNode<K, V, N extends AbstractGenericOnOffKeyListTreeNode<K, V, N>>
extends AbstractGenericKeyListTreeNode<K, V, N> implements OnOffTreeNodeInterface<N> {

	private OnOffTreeNodeBase<N> base = null;

	/**
	 * 
	 * 
	 * @param key
	 * @param value
	 */
	public AbstractGenericOnOffKeyListTreeNode(K key, V value) {
		super(key, value);
		base = new OnOffTreeNodeBase<>(internalGetThis());
	}


	@Override
	public N forceNodeVisible(boolean force) {
		return base.forceNodeVisible(force);
	}

	@Override
	public boolean forceNodeVisible(OnOffTreeNodeModifier modifier) {
		return base.forceNodeVisible(modifier);
	}

	@Override
	public N ignoreNode(boolean ignore) {
		return base.ignoreNode(ignore);
	}

	@Override
	public boolean isNodeIgnored(OnOffTreeNodeModifier modifier) {
		return base.isNodeIgnored(modifier);
	}

	@Override
	public N hideNode(boolean hide) {
		return base.hideNode(hide);
	}

	@Override
	public boolean isNodeHidden(OnOffTreeNodeModifier modifier) {
		return base.isNodeHidden(modifier);
	}

	@Override
	public N hideChildNodes(boolean hide) {
		return base.hideChildNodes(hide);
	}

	@Override
	public boolean isChildNodesHidden(OnOffTreeNodeModifier modifier) {
		return base.isChildNodesHidden(modifier);
	}

}
