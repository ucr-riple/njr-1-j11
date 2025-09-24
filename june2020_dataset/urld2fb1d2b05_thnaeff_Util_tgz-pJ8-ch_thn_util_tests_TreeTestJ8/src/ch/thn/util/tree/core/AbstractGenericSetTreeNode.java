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

import java.util.Comparator;

import com.google.common.collect.Multisets;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;

/**
 * 
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 * @param <V>
 * @param <N>
 */
public abstract class AbstractGenericSetTreeNode<V, N extends AbstractGenericSetTreeNode<V, N>> 
	extends AbstractGenericCollectionTreeNode<V, N, SortedMultiset<N>> 
	implements SetTreeNodeInterface<V, N> {

	private Comparator<? super N> comparator = null;
	
	/**
	 * Creates a new set tree node which sorts its values by comparing the toString 
	 * result of two nodes.
	 * 
	 * @param value
	 */
	public AbstractGenericSetTreeNode(V value) {
		this(null, value);
	}
	
	/**
	 * Creates a new set tree node which uses the given comparator to sort the 
	 * nodes
	 * 
	 * @param comparator The comparator to use, or <code>null</code> to use an 
	 * internally created comparator which compares the toString result of two nodes.
	 * @param value
	 */
	public AbstractGenericSetTreeNode(Comparator<? super N> comparator, V value) {
		super(null, value);
		
		if (comparator == null) {
			comparator = new Comparator<N>() {

				@Override
				public int compare(N o1, N o2) {
					return o1.toString().compareTo(o2.toString());
				}
			};
		}
		
		this.comparator = comparator;
		
		internalSetChildrenCollection(TreeMultiset.create(comparator));
	}
	
	/**
	 * Returns the comparator which is used for the sorting
	 * 
	 * @return
	 */
	public Comparator<? super N> getComparator() {
		return comparator;
	}
	
	@Override
	public SortedMultiset<N> getChildNodes() {
		return Multisets.unmodifiableSortedMultiset(internalGetChildren());
	}
	
}
