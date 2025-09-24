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
import java.util.List;

import ch.thn.util.tree.ListTreeIterator;

/**
 * 
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 * @param <V>
 * @param <N>
 */
public interface ListTreeNodeInterface<V, N extends ListTreeNodeInterface<V, N>>
extends CollectionTreeNodeInterface<V, N> {

	@Override
	public List<N> getChildNodes();

	/**
	 * 
	 * 
	 * @param index
	 * @param node
	 * @return
	 */
	public N addChildNodeAt(int index, N node);

	/**
	 * 
	 * 
	 * @param index
	 * @param node
	 * @return
	 */
	public N addChildNodeCopyAt(int index, N node);

	/**
	 * 
	 * 
	 * @param index
	 * @param nodes
	 * @return
	 */
	public boolean addChildNodesAt(int index, Collection<N> nodes);

	/**
	 * 
	 * 
	 * @param index
	 * @param value
	 * @return
	 */
	public N addChildNodeAt(int index, V value);

	/**
	 * 
	 * 
	 * @param index
	 * @return
	 */
	public N getChildNode(int index);

	/**
	 * 
	 * 
	 * @return
	 */
	public N getFirstChildNode();

	/**
	 * 
	 * 
	 * @return
	 */
	public N getLastChildNode();

	/**
	 * 
	 * 
	 * @return
	 */
	public N getFirstSibling();

	/**
	 * 
	 * 
	 * @return
	 */
	public N getLastSibling();

	/**
	 * 
	 * 
	 * @return
	 */
	public N getNextSibling();

	/**
	 * 
	 * 
	 * @return
	 */
	public N getPreviousSibling();

	/**
	 * 
	 * 
	 * @param index
	 * @return
	 */
	public N removeChildNode(int index);

	/**
	 * 
	 * 
	 * @return
	 */
	public int getNodeIndex();

	/**
	 * 
	 * 
	 * @param node
	 * @return
	 */
	public int getChildNodeIndex(N node);

	/**
	 * 
	 * 
	 * @return
	 */
	public boolean isFirstNode();

	/**
	 * 
	 * 
	 * @return
	 */
	public boolean isLastNode();


	/**
	 * 
	 * 
	 * @return
	 */
	public ListTreeIterator<N> listIterator();

	/**
	 * 
	 * 
	 * @param subtreeOnly If <code>true</code>, it only iterates through the sub
	 * tree of the current node (including the current node as root node of the
	 * sub tree).
	 * @return
	 */
	public ListTreeIterator<N> listIterator(boolean subtreeOnly);

}
