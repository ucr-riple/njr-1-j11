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

import java.util.Comparator;

import ch.thn.util.tree.onoff.core.AbstractGenericOnOffKeySetTreeNode;

/**
 * OnOff tree node: Allows a node to be hidden or ignored, allows the nodes children
 * to be hidden and has the functionality to force a node to be visible.<br />
 * <br />
 * A node with its child nodes in an unordered set.
 * 
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class OnOffKeySetTreeNode<K, V> extends AbstractGenericOnOffKeySetTreeNode<K, V, OnOffKeySetTreeNode<K, V>> {

	/**
	 * 
	 * 
	 * @param keyComparator
	 * @param valueComparator
	 * @param key
	 * @param value
	 */
	public OnOffKeySetTreeNode(Comparator<? super K> keyComparator,
			Comparator<? super OnOffKeySetTreeNode<K, V>> valueComparator, K key, V value) {
		super(keyComparator, valueComparator, key, value);
	}

	/**
	 * 
	 * 
	 * @param key
	 * @param value
	 */
	public OnOffKeySetTreeNode(K key, V value) {
		super(key, value);
	}

	@Override
	public OnOffKeySetTreeNode<K, V> nodeFactory(V value) {
		return new OnOffKeySetTreeNode<>(null, value);
	}

	@Override
	public OnOffKeySetTreeNode<K, V> nodeFactory(OnOffKeySetTreeNode<K, V> node) {
		return new OnOffKeySetTreeNode<>(node.getKeyComparator(), node.getValueComparator(), node.getNodeKey(), node.getNodeValue());
	}

	@Override
	public OnOffKeySetTreeNode<K, V> nodeFactory(K key, V value) {
		return new OnOffKeySetTreeNode<>(key, value);
	}

	@Override
	public OnOffKeySetTreeNode<K, V> internalGetThis() {
		return this;
	}

}
