/**
 * @author Xuyang Feng
 * @email hugo.fxy@gmail.com
 */

package hugo.util.structure;

import java.util.Iterator;


public class BinaryTree<E extends Comparable<E>> {
	@SuppressWarnings("hiding")
	protected class TreeNode<E extends Comparable<E>> implements Comparable<TreeNode<E>> {
		private TreeNode<E> leftTreeNode;
		private TreeNode<E> rightTreeNode;
		private TreeNode<E> parentTreeNode;
		private E value;
		
		public TreeNode(E value) {
			this(value, null, null, null);
		}
		
		public TreeNode(E value, 
				TreeNode<E> leftTreeNode, TreeNode<E> rightTreeNode, TreeNode<E> parentTreeNode) {
			this.leftTreeNode = leftTreeNode;
			this.rightTreeNode = rightTreeNode;
			this.parentTreeNode = parentTreeNode;
			this.value = value;
		}
		
		public TreeNode<E> getLeftTree() {
			return leftTreeNode;
		}
		
		public TreeNode<E> getRightTree() {
			return rightTreeNode;
		}
		
		public TreeNode<E> getParentNode() {
			return parentTreeNode;
		}
		
		public E getValue() {
			return value;
		}
		
		@Override
		public int compareTo(TreeNode<E> node) {
			return value.compareTo(node.value);
		}
	}
	
	private TreeNode<E> rootTreeNode;
	
	public BinaryTree() {
		rootTreeNode = null;
	}
	
	public void insert(E e) {
		insertAtNode(e, rootTreeNode, null);
	}

	private void insertAtNode(E e, TreeNode<E> current, TreeNode<E> parent) {
		if(current == null) {
			TreeNode<E> newNode = new TreeNode<E>(e);
			if (parent != null) {
				newNode.parentTreeNode = parent;
				if(newNode.compareTo(parent) > 0) parent.rightTreeNode = newNode;
				else parent.leftTreeNode = newNode;
			} else {
				rootTreeNode = newNode;
			}
		}
		else if (e.compareTo(current.value) < 0) {
			insertAtNode(e, current.getLeftTree(), current);
		}
		else if (e.compareTo(current.value) > 0) {
			insertAtNode(e, current.getRightTree(), current);
		}
	}
	
	public void remove(E target) {
		TreeNode<E> targetTreeNode = findNode(target, rootTreeNode);
		if(targetTreeNode == null) return; // target not found
		if(isLeaf(targetTreeNode))	// target node is a leaf
				if(targetTreeNode.getParentNode() == null) rootTreeNode = null; // target is the only node in the tree
				else deleteFromParent(targetTreeNode);// target node is a leaf with parent
		else {	// target node has child
			TreeNode<E> minOrMaxNode; // This is bond to be a leaf node.
			if(targetTreeNode.getRightTree() != null) 
				minOrMaxNode = minNode(targetTreeNode.getRightTree());
			else 
				minOrMaxNode = maxNode(targetTreeNode.getLeftTree());
			targetTreeNode.value = minOrMaxNode.value; // copy the move the minOrMaxNode to the position of the target node 
			deleteFromParent(minOrMaxNode); // Since the minOrMaxNode is a leaf, simply delete from its parent.
		}
	}
	
	protected void deleteFromParent(TreeNode<E> node) {
		if(node == node.getParentNode().leftTreeNode)
			node.getParentNode().leftTreeNode = null;
		else
			node.getParentNode().rightTreeNode = null;
	}
	
	protected boolean isLeaf(TreeNode<E> node) {
		return node.getLeftTree()==null && node.getRightTree()==null;
	}
	
	protected TreeNode<E> minNode(TreeNode<E> beginNode) {
		TreeNode<E> current = beginNode;
		while (current.getLeftTree() != null) {
			current = current.getLeftTree();
		}
		return current;
	}

	protected TreeNode<E> maxNode(TreeNode<E> beginNode) {
		TreeNode<E> current = beginNode;
		while (current.getRightTree() != null) {
			current = current.getRightTree();
		}
		return current;
	}
	
	public E find(E target) {
		return findNode(target, rootTreeNode).value;
	}
	
	protected TreeNode<E> findNode(E target, TreeNode<E> current) {
		if(current == null) return null;
		else if(current.value.compareTo(target) < 0)
			return findNode(target, current.getRightTree());
		else if(current.value.compareTo(target) > 0)
			return findNode(target, current.getLeftTree());
		else 
			return current;
	}
	
	public Iterable<E> BfsTraversalIterable() {
		return new Iterable<E>() {
			@Override
			public Iterator<E> iterator() {
				return new Iterator<E>() {
					Queue<TreeNode<E>> toDoQueue = new Queue<TreeNode<E>>(){{
						push(rootTreeNode);
					}};
					
					@Override
					public boolean hasNext() {
						return !toDoQueue.empty();
					}

					@Override
					public E next() {
						TreeNode<E> currenTreeNode = toDoQueue.pop();
						if(currenTreeNode.getLeftTree() != null) toDoQueue.push(currenTreeNode.getLeftTree());
						if(currenTreeNode.getRightTree() != null) toDoQueue.push(currenTreeNode.getRightTree());
						return currenTreeNode.value;
					}

					@Override
					public void remove() {
						
					}
				};
			}
		};
	}
	
	public Iterable<E> DfsTraversalIterable() {
		return new Iterable<E>() {
			@Override
			public Iterator<E> iterator() {
				return new Iterator<E>() {
					Stack<TreeNode<E>> toDoStack = new Stack<TreeNode<E>>(){{
						push(rootTreeNode);
					}};
					
					@Override
					public boolean hasNext() {
						return !toDoStack.empty();
					}

					@Override
					public E next() {
						TreeNode<E> currenTreeNode = toDoStack.pop();
						if(currenTreeNode.getRightTree() != null) toDoStack.push(currenTreeNode.getRightTree());
						if(currenTreeNode.getLeftTree() != null) toDoStack.push(currenTreeNode.getLeftTree());
						return currenTreeNode.value;
					}

					@Override
					public void remove() {
						
					}
				};
			}
		};
	}
}
