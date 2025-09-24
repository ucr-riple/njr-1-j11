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
import java.util.Set;

import com.google.common.collect.Multimap;

/**
 * 
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 * @param <K>
 * @param <V>
 * @param <N>
 */
public interface MapTreeNodeInterface<K, V, N extends MapTreeNodeInterface<K, V, N>>
extends CollectionTreeNodeInterface<V, N>, Iterable<N> {

	/**
	 * 
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract N nodeFactory(K key, V value);

	/**
	 * 
	 * 
	 * @return
	 */
	public K getNodeKey();

	/**
	 * Returns a view of the child nodes which have the given key as an unmodifiable
	 * list
	 * 
	 * @param key
	 * @return
	 */
	public Collection<N> getChildNodes(K key);

	/**
	 * 
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public N addChildNode(K key, V value);

	/**
	 * 
	 * 
	 * @param nodes
	 * @return
	 */
	public boolean addChildNodes(Multimap<K, N> nodes);

	/**
	 * 
	 * 
	 * @param key
	 * @return
	 */
	public Collection<N> removeChildNodes(K key);

	/**
	 * 
	 * 
	 * @param key
	 * @param node
	 * @return
	 */
	public boolean removeChildNode(K key, N node);

	/**
	 * 
	 * 
	 * @param key
	 * @return
	 */
	public int getChildNodesCount(K key);

	/**
	 * Returns a set of all the keys for which one or more child node exist.
	 * 
	 * @return
	 */
	public Set<K> getChildNodeKeys();

	/**
	 * 
	 * 
	 * @param key
	 * @return
	 */
	public boolean hasChildNodes(K key);

}
