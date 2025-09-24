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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;


/**
 * This is a {@link Iterator} which can be used to travel forward through the whole tree
 * (or downward when picturing the tree like the following tree examples).<br />
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
 * 
 * Hint: When starting to iterate the tree at node "Child 0" for example, the iteration
 * will go through the whole tree like in a collection ("Child 0.1" -> "Child 1" -> ...). If
 * the iteration should only be within the sub-tree (branch) of "Child 0", use the constructor
 * with the <code>subtreeOnly</code> flag.<br />
 * 
 * 
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 * 
 * @param <N> The type of tree to iterate through. Has to be a tree extending {@link CollectionTreeNodeInterface}
 * 
 * @param <N>
 * @param <I>
 */
public abstract class AbstractGenericTreeIterator<N extends CollectionTreeNodeInterface<?, N>, I extends Iterator<N>>
implements Iterator<N> {

	protected N startNode = null;
	private N nextNodeCache = null;
	protected N lastReturned = null;

	protected LinkedList<I> iterators = null;

	protected boolean subtreeOnly = false;
	private boolean restOfTree = false;

	/**
	 * An iterator which iterates through the whole tree that follows the given
	 * starting node
	 * 
	 * @param toIterate The starting node
	 */
	public AbstractGenericTreeIterator(N toIterate) {
		this(toIterate, false);
	}

	/**
	 * An iterator which either iterates through the whole that follows the given
	 * starting node, or only through the sub-tree (branch) of the given starting
	 * node.
	 * 
	 * @param toIterate The starting node
	 * @param subtreeOnly Only iterates through the sub tree of the given start node.
	 */
	public AbstractGenericTreeIterator(N toIterate, boolean subtreeOnly) {
		this.nextNodeCache = toIterate;
		this.subtreeOnly = subtreeOnly;
		this.startNode = toIterate;

		iterators = new LinkedList<>();

	}

	/**
	 * This method has to return the right iterator. It can be implemented in
	 * extending classes which then also returns the right iterator in this
	 * {@link AbstractGenericTreeIterator} class.
	 * 
	 * @param node
	 * @return
	 */
	protected abstract I getIterator(Collection<N> collection);

	/**
	 * Moves the iterator to the position of the given node. This method
	 * can be implemented in extending classes because for example if the extending
	 * class uses a list iterator, the positioning can be done with much better
	 * performance.
	 * 
	 * @param collection
	 * @param node
	 * @return
	 */
	protected abstract I positionIterator(Collection<N> collection, N node);

	@Override
	public boolean hasNext() {
		return internalNext(true, false) != null;
	}

	@Override
	public N next() {
		return internalNext(false, false);
	}

	/**
	 * Returns the next node without moving the cursor forward.
	 * 
	 * @return
	 */
	public N peekNext() {
		return internalNext(true, false);
	}

	@Override
	public void remove() {
		if (lastReturned == null) {
			throw new IllegalStateException("This call can only be made once per call " +
					"to next or previous. It can be made only if add(E) has not " +
					"been called after the last call to next or previous.");
		}

		lastReturned.removeNode();
		lastReturned = null;
	}


	/**
	 * 
	 * 
	 * @param peek
	 * @param upwards
	 * @return
	 */
	private N internalNext(boolean peek, boolean upwards) {
		if (nextNodeCache != null) {
			if (peek) {
				return nextNodeCache;
			} else {
				lastReturned = nextNodeCache;
				nextNodeCache = null;
				return lastReturned;
			}
		}

		if (!upwards && !lastReturned.isLeafNode()) {
			//There are children -> iterate them first

			iterators.addLast(getIterator(lastReturned.getChildNodes()));
		}
		//else:
		//There are no children -> continue with next sibling


		//If the iterator list is empty, it means all iterators have been removed
		//by going upwards the branch after reaching the branch end.
		if (iterators.size() == 0) {
			if (subtreeOnly || startNode.isRootNode() || restOfTree) {
				//Stop after finishing the tree

				//Reset the flag for the next time
				restOfTree = false;

				if (peek) {
					return null;
				} else {
					throw new NoSuchElementException("Reached the end of the tree");
				}
			} else {
				//Finished the sub-tree, but subTreeOnly was set to false
				//-> initialize the rest on top of the sub-tree
				iterators.addAll(initBranchIterators(startNode, lastReturned.getRootNode()));
				restOfTree = true;
			}
		}

		N ret = null;

		try {
			if (peek) {
				nextNodeCache = iterators.peekLast().next();
				ret = nextNodeCache;
			} else {
				lastReturned = iterators.peekLast().next();
				ret = lastReturned;
			}
		} catch (NoSuchElementException e) {
			//Reached the end of the branch
			iterators.removeLast();
			return internalNext(peek, true);
		}

		return ret;
	}

	/**
	 * Initializes and positions all iterators for the given branch, from the
	 * start node up to the end node
	 * 
	 * @param startNode
	 * @param endNode
	 * @return A list of the branch iterators, from top (the first iterator in the
	 * list) to bottom (the last iterator in the list)
	 */
	protected LinkedList<I> initBranchIterators(N startNode, N endNode) {
		LinkedList<I> newIterators = new LinkedList<>();

		if (!startNode.isRootNode()) {
			//Initialize all iterators
			N childNode = startNode;
			N parentNode = childNode.getParentNode();

			//Go up from start node to end node
			do {
				I childIterator = positionIterator(parentNode.getChildNodes(), childNode);

				newIterators.addFirst(childIterator);
				childNode = parentNode;
				parentNode = childNode.getParentNode();
			} while (parentNode != null && childNode != endNode);
		}

		return newIterators;
	}

}
