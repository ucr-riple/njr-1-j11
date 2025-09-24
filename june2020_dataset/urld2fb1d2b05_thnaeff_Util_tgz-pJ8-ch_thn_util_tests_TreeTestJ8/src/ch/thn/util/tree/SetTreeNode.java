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
import java.util.Set;

import ch.thn.util.tree.core.AbstractGenericSetTreeNode;

/**
 * A tree node which has its children stored in a {@link Set}
 * 
 * 
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class SetTreeNode<V>
extends AbstractGenericSetTreeNode<V, SetTreeNode<V>> {

	/**
	 * 
	 * 
	 * @param comparator
	 * @param value
	 */
	public SetTreeNode(Comparator<? super SetTreeNode<V>> comparator, V value) {
		super(comparator, value);
	}

	/**
	 * 
	 * 
	 * @param value
	 */
	public SetTreeNode(V value) {
		super(value);
	}

	@Override
	public SetTreeNode<V> nodeFactory(V value) {
		return new SetTreeNode<>(value);
	}

	@Override
	public SetTreeNode<V> nodeFactory(SetTreeNode<V> node) {
		return new SetTreeNode<>(node.getNodeValue());
	}

	@Override
	public SetTreeNode<V> internalGetThis() {
		return this;
	}


}
