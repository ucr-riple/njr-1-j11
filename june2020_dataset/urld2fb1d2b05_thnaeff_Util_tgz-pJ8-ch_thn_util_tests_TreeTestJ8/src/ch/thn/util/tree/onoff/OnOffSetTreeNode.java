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
import java.util.Set;

import ch.thn.util.tree.onoff.core.AbstractGenericOnOffSetTreeNode;

/**
 * OnOff tree node: Allows a node to be hidden or ignored, allows the nodes children
 * to be hidden and has the functionality to force a node to be visible.<br />
 * <br />
 * A tree node which has its children stored in a {@link Set}
 * 
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class OnOffSetTreeNode<V>
extends AbstractGenericOnOffSetTreeNode<V, OnOffSetTreeNode<V>> {


	/**
	 * 
	 * 
	 * @param comparator
	 * @param value
	 */
	public OnOffSetTreeNode(Comparator<? super OnOffSetTreeNode<V>> comparator, V value) {
		super(comparator, value);
	}

	/**
	 * 
	 * 
	 * @param value
	 */
	public OnOffSetTreeNode(V value) {
		super(value);
	}

	@Override
	public OnOffSetTreeNode<V> nodeFactory(V value) {
		return new OnOffSetTreeNode<>(value);
	}

	@Override
	public OnOffSetTreeNode<V> nodeFactory(OnOffSetTreeNode<V> node) {
		return new OnOffSetTreeNode<>(node.getComparator(), node.getNodeValue());
	}

	@Override
	public OnOffSetTreeNode<V> internalGetThis() {
		return this;
	}

}
