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
 * The base on-off-tree implementation which is used for {@link AbstractGenericOnOffListTreeNode}
 * and {@link AbstractGenericOnOffKeyListTreeNode}
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 * @param <N>
 */
public class OnOffTreeNodeBase<N> implements OnOffTreeNodeInterface<N> {

	private N returnObject = null;

	private boolean isNodeIgnored = false;
	private boolean isNodeHidden = false;
	private boolean isChildNodesHidden = false;
	private boolean forceNodeVisible = false;


	/**
	 * 
	 * 
	 * @param returnObject
	 */
	public OnOffTreeNodeBase(N returnObject) {
		this.returnObject = returnObject;
	}


	@Override
	public N forceNodeVisible(boolean force) {
		forceNodeVisible = force;
		return returnObject;
	}

	@Override
	public boolean forceNodeVisible(OnOffTreeNodeModifier modifier) {
		return forceNodeVisible;
	}

	@Override
	public N ignoreNode(boolean ignore) {
		isNodeIgnored = ignore;
		return returnObject;
	}

	@Override
	public boolean isNodeIgnored(OnOffTreeNodeModifier modifier) {
		return isNodeIgnored;
	}

	@Override
	public N hideNode(boolean hide) {
		isNodeHidden = hide;
		return returnObject;
	}

	@Override
	public boolean isNodeHidden(OnOffTreeNodeModifier modifier) {
		return isNodeHidden;
	}

	@Override
	public N hideChildNodes(boolean hide) {
		isChildNodesHidden = hide;
		return returnObject;
	}

	@Override
	public boolean isChildNodesHidden(OnOffTreeNodeModifier modifier) {
		return isChildNodesHidden;
	}


}
