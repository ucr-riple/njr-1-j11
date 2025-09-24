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
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import ch.thn.util.tree.core.AbstractGenericListTreeNode;
import ch.thn.util.tree.core.AbstractGenericTreeIterator;
import ch.thn.util.tree.core.ListTreeNodeInterface;
import ch.thn.util.tree.core.TreeNodeError;


/**
 * This is a {@link ListIterator} which can be used to travel through the whole tree, forward 
 * and backward (or down and up when picturing the tree like the following tree examples). The 
 * whole tree is iterated as a list.<br />
 * The tree
 * <pre>
 * Head
 * ├─ Child 0
 * │  └─ Child 0.1
 * ├─ Child 1
 * │  ├─ Child 1.1
 * │  ├─ Child 1.2
 * </pre>
 * would be iterated through as follows:<br />
 * <pre>
 * 1. Head
 * 2. Child 0
 * 3. Child 0.1
 * 4. Child 1
 * 5. Child 1.1
 * 6. Child 1.2
 * </pre>
 * 
 * The returned index with {@link #nextIndex()} and {@link #previousIndex()} is 
 * the node index which matches the node which would be returned with {@link #next()} 
 * or {@link #previous()}. This index is the index among sibling nodes and not the 
 * index of the whole tree.<br />
 * <br />
 * Hint: When starting to iterate the tree at node "Child 0" for example, the iteration 
 * will go through the whole tree like in a list ("Child 0.1" -> "Child 1" -> ...). If 
 * the iteration should only be within the sub-tree of "Child 0", use the constructor 
 * with the <code>subtreeOnly</code> flag.<br />
 * <br />
 * This iterator has two additional methods: {@link #peekNext()} and {@link #peekPrevious()}. 
 * Calling those methods does not move the cursor forward or backward and they can be 
 * used to retrieve the node which would be returned with a {@link #next()} or {@link #previous()} 
 * call.
 * 
 * 
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 * 
 * @param <N> The type of tree to iterate through. Has to be a tree extending {@link AbstractGenericListTreeNode}
 * 
 */
public class ListTreeIterator<N extends ListTreeNodeInterface<?, N>> 
	extends AbstractGenericTreeIterator<N, ListIterator<N>> implements ListIterator<N> {

	private N previousNodeCache = null;
	
	private boolean cursorBelowLastReturned = false;
	
	
	/**
	 * 
	 * 
	 * @param toIterate
	 */
	public ListTreeIterator(N toIterate) {
		super(toIterate, false);
	}
	
	
	/**
	 * 
	 * 
	 * @param toIterate
	 * @param subtreeOnly
	 */
	public ListTreeIterator(N toIterate, boolean subtreeOnly) {
		super(toIterate, subtreeOnly);
	}

	
	@Override
	protected ListIterator<N> getIterator(Collection<N> collection) {
		//It is known that the collection is a list since the ListTreeNodeInterface 
		//is implemented
		return ((List<N>)collection).listIterator();
	}
	
	@Override
	protected ListIterator<N> positionIterator(Collection<N> collection, N node) {
		//It is known that the collection is a list since the ListTreeNodeInterface 
		//is implemented
		return ((List<N>)collection).listIterator(node.getNodeIndex() + 1);
	}

	@Override
	public boolean hasPrevious() {		
		if (subtreeOnly && lastReturned != null && lastReturned == startNode) {
			return false;
		}
		
		return (internalPrevious(true) != null);
	}


	@Override
	public N previous() {
		//If there is a last returned node and the cursor is below, it means that
		//the last call was a next() call. The definition for next() says 
		//that alternating calls to previous() and next() return the same element 
		//repeatedly
		if (lastReturned != null && cursorBelowLastReturned) {
			cursorBelowLastReturned = false;
			return lastReturned;
		}
		
		if (subtreeOnly && lastReturned != null && lastReturned == startNode) {
			throw new NoSuchElementException("Reached the root (head) of the sub tree");
		}
		
		cursorBelowLastReturned = false;
		return internalPrevious(false);
	}
	

	@Override
	public N next() {
		//If there is a last returned node and the cursor is above, it means that
		//the last call was a previous() call. The definition for next() says 
		//that alternating calls to previous() and next() return the same element 
		//repeatedly
		if (lastReturned != null && !cursorBelowLastReturned) {
			cursorBelowLastReturned = true;
			return lastReturned;
		}
		
		cursorBelowLastReturned = true;
		return super.next();
	}

	@Override
	public int previousIndex() {
		throw new UnsupportedOperationException("Index is not supported yet");
	}
	
	@Override
	public int nextIndex() {
		throw new UnsupportedOperationException("Index is not supported yet");
	}

	@Override
	public void add(N node) {
		throw new UnsupportedOperationException("Adding is not supported yet");
	}

	@Override
	public void set(N node) {
		throw new UnsupportedOperationException("Setting is not supported yet");
	}
	
	/**
	 * Returns the previous node without moving the cursor backwards.
	 * 
	 * @return
	 */
	public N peekPrevious() {
		return internalPrevious(true);
	}
	
	
	
	/**
	 * 
	 * 
	 * @param peek
	 * @return
	 */
	private N internalPrevious(boolean peek) {
		if (previousNodeCache != null) {
			if (peek) {
				return previousNodeCache;
			} else {
				lastReturned = previousNodeCache;
				previousNodeCache = null;
				return lastReturned;
			}
		}
		
		if (lastReturned.isFirstNode()) {
			//The first sibling -> Continue with children of parent
			
			if (lastReturned.isRootNode()) {
				if (peek) {
					return null;
				} else {
					throw new NoSuchElementException("Reached the root (head) of the tree");
				}
			} else if (lastReturned.getParentNode().isRootNode()) {
				//Reached the first sibling under the root -> the previous node is the root node
				if (peek) {
					previousNodeCache = lastReturned.getParentNode();
					return previousNodeCache;
				} else {
					lastReturned = lastReturned.getParentNode();
					return lastReturned;
				}
			}
			
			if (iterators.size() == 0) {
				iterators.addAll(initBranchIterators(lastReturned.getParentNode(), lastReturned.getRootNode()));
			} else {
				//Just remove the last iterator. It will continue with the parent iterator
				iterators.removeLast();
			}
		} else if (!lastReturned.getPreviousSibling().isLeafNode()) {
			//The previous sibling has children -> continue with last child of that branch
			
			N endNode = lastReturned.getPreviousSibling();
			N startNode = TreeUtil.getLastLeafNode(endNode);
			iterators.addAll(initBranchIterators(startNode, endNode));
		} else if (iterators.size() == 0) {
			N endNode = lastReturned.getRootNode();
			N startNode = lastReturned;
			iterators.addAll(initBranchIterators(startNode, endNode));
		}
		//else:
		//There are no children -> continue with previous sibling
		
				
		try {
			if (peek) {
				previousNodeCache = iterators.peekLast().previous();
				return previousNodeCache;
			} else {
				lastReturned = iterators.peekLast().previous();
				return lastReturned;
			}
		} catch (NoSuchElementException e) {
			//Reached the root
			//This should not even happen since checking for the root node is done earlier
			throw new TreeNodeError("Iterator Error");
		}
		
	}

}
