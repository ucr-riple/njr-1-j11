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

import ch.thn.util.tree.onoff.core.AbstractGenericOnOffKeyListTreeNode;

/**
 * OnOff tree node: Allows a node to be hidden or ignored, allows the nodes children
 * to be hidden and has the functionality to force a node to be visible.<br />
 * <br />
 * A node with child nodes whose order is maintained.
 * 
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class OnOffKeyListTreeNode<K, V> extends AbstractGenericOnOffKeyListTreeNode<K, V, OnOffKeyListTreeNode<K, V>> {


	/**
	 * 
	 * 
	 * @param key
	 * @param value
	 */
	public OnOffKeyListTreeNode(K key, V value) {
		super(key, value);
	}

	@Override
	public OnOffKeyListTreeNode<K, V> nodeFactory(V value) {
		return new OnOffKeyListTreeNode<>(null, value);
	}

	@Override
	public OnOffKeyListTreeNode<K, V> nodeFactory(OnOffKeyListTreeNode<K, V> node) {
		return new OnOffKeyListTreeNode<>(node.getNodeKey(), node.getNodeValue());
	}

	@Override
	public OnOffKeyListTreeNode<K, V> nodeFactory(K key, V value) {
		return new OnOffKeyListTreeNode<>(key, value);
	}

	@Override
	public OnOffKeyListTreeNode<K, V> internalGetThis() {
		return this;
	}

}
