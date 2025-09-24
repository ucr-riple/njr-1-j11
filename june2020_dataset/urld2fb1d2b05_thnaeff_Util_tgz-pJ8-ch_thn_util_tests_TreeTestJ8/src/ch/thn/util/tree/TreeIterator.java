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

import java.util.Collection;
import java.util.Iterator;

import ch.thn.util.tree.core.AbstractGenericTreeIterator;
import ch.thn.util.tree.core.CollectionTreeNodeInterface;

/**
 * 
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 * @param <N>
 */
public class TreeIterator<N extends CollectionTreeNodeInterface<?, N>> 
	extends AbstractGenericTreeIterator<N, Iterator<N>> {

	/**
	 * Iterates through the sub tree (including the given start node).<br />
	 * <br />
	 * <i>Note: </i> This iterator only supports iterating through the sub tree 
	 * of the given start node. The reason is that positioning the iterator after 
	 * the sub tree is finished is impossible in a collection/set if there are 
	 * duplicates of an object among the siblings.
	 * 
	 * @param toIterate
	 */
	public TreeIterator(N toIterate) {
		super(toIterate, true);
	}
	
	
	@Override
	protected Iterator<N> getIterator(Collection<N> collection) {
		return collection.iterator();
	}

	@Override
	protected Iterator<N> positionIterator(Collection<N> collection, N node) {
		throw new UnsupportedOperationException("Positioning the iterator for a collection/set is not possible (there might be duplicate objects among the siblings)");
		
//		Iterator<N> iterator = collection.iterator();
//		
//		//Move the iterator to the right position
//		while (iterator.hasNext()) {
//			if (iterator.next() == node) {
//				break;
//			}
//		}
//		
//		return iterator;
	}
	
}
