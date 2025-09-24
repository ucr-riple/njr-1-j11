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

import java.util.Comparator;

import ch.thn.util.tree.core.AbstractGenericKeySetTreeNode;

/**
 * A node with its child nodes in an unordered set.
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 * @param <K>
 * @param <V>
 */
public class KeySetTreeNode<K, V>
extends AbstractGenericKeySetTreeNode<K, V, KeySetTreeNode<K, V>> {

	/**
	 * 
	 * 
	 * @param keyComparator
	 * @param valueComparator
	 * @param key
	 * @param value
	 */
	public KeySetTreeNode(Comparator<? super K> keyComparator,
			Comparator<? super KeySetTreeNode<K, V>> valueComparator, K key, V value) {
		super(keyComparator, valueComparator, key, value);
	}

	/**
	 * 
	 * 
	 * @param key
	 * @param value
	 */
	public KeySetTreeNode(K key, V value) {
		super(key, value);
	}

	@Override
	public KeySetTreeNode<K, V> nodeFactory(K key, V value) {
		return new KeySetTreeNode<>(key, value);
	}

	@Override
	public KeySetTreeNode<K, V> nodeFactory(V value) {
		return new KeySetTreeNode<>(null, value);
	}

	@Override
	public KeySetTreeNode<K, V> nodeFactory(KeySetTreeNode<K, V> node) {
		return new KeySetTreeNode<>(node.getNodeKey(), node.getNodeValue());
	}

	@Override
	protected KeySetTreeNode<K, V> internalGetThis() {
		return this;
	}



}
