/**
 * @author Xuyang Feng
 * @email hugo.fxy@gmail.com
 */

package hugo.util.structure;

import java.util.Iterator;

@SuppressWarnings("rawtypes")
public class EdgeGraph<E extends Comparable> {
	@SuppressWarnings("hiding")
	protected class Node<E extends Comparable> implements Comparable<Node<E>> {
		private E label;
		private LinkedList<Edge<E>> edges;
		private boolean visited;
		private Node<E> previousNode;

		public Node(E dataE) {
			this.label = dataE;
			edges = new LinkedList<Edge<E>>();
			visited = false;
			previousNode = null;
		}
		
		public void addEdge(Edge<E> edge) {
			edges.addFirst(edge);
		}

		@SuppressWarnings("unchecked")
		public int compareTo(Node<E> o) {
			// TODO Auto-generated method stub
			return label.compareTo(o.label);
		}
		
		public String toString() {
			return label.toString();
		}
	}
	
	@SuppressWarnings("hiding")
	protected class Edge<E extends Comparable> implements Comparable<Edge<E>>{
		private Node<E> toNode;
		
		public Edge(Node<E> to) {
			toNode = to;
		}

		@Override
		public int compareTo(Edge<E> o) {
			return this.toNode.compareTo(o.toNode);
		}
	}
	
	private LinkedList<Node<E>> nodes;
	
	public EdgeGraph() {
		this.nodes = new LinkedList<Node<E>>();
	}
	
	public Node<E> findNode(E label) {
		for (Node<E> node : nodes) {
			if(node.label.equals(label)) return node;
		}
		return null;
	}
	
	public boolean contains(E label) {
		return findNode(label) != null;
	}
	
	public void addNode(E label) {
		nodes.addFirst(new Node<E>(label));
	}
	
	public void addEdge(E fromLabel, E toLabel) {
		Node<E> from = findNode(fromLabel);
		Node<E> to = findNode(toLabel);
		from.addEdge(new Edge<E>(to));
	}
	
	public LinkedList<E> findPathVia(E fromE, E toE, E viaE) {
		LinkedList<E> firstPath = findPath(fromE, viaE);
		LinkedList<E> secondPath = findPath(viaE, toE);
		if(firstPath==null || secondPath==null) return null;
		firstPath.attachToFrontOf(secondPath);
		return firstPath;
	}
	
	public LinkedList<E> findPath(E fromE, E toE) {
		return findPathAvoiding(fromE, toE, null);
	}
	
	public LinkedList<E> findPathAvoiding(E fromE, E toE, E avoidE) {
		clearState();

		Node<E> fromNode = findNode(fromE);
		Node<E> toNode = findNode(toE);
		Node<E> avoidNode = findNode(avoidE);
		if(avoidE != null) avoidNode.visited = true;

		LinkedList<E> pathList = new LinkedList<E>();
		Queue<Node<E>> toDoList = new Queue<Node<E>>();
		toDoList.push(fromNode);
		while (!toDoList.empty()) {
			Node<E> currentNode = toDoList.pop();
			if(currentNode.equals(toNode)) {
				while(currentNode.previousNode != null) {
					pathList.addFirst(currentNode.label);
					currentNode = currentNode.previousNode;
				}
				return pathList;
			}
			else {
				for (Edge<E> edge : currentNode.edges) {
					if(!edge.toNode.visited) {
						edge.toNode.previousNode = currentNode;
						toDoList.push(edge.toNode);
					}
				}
				currentNode.visited = true;
			}
		}
		return null;
	}
	
	
	public void clearState() {
		for (Node<E> node : nodes) {
			node.visited = false;
			node.previousNode = null;
		}
	}
	
	public String show() {
		String output = "";
		for (Node<E> node : nodes) {
			output += "<Node> " + node.toString() + ":\n\t";
			for (Edge<E> edge : node.edges) {
				output += edge.toNode +"  ";
			}
			output += "\n";
		}
		return output;
	}

	public Iterable<E> bfsIteratFrom(E fromE) {
		return new BfsIterable(fromE);
	}
	
	private class BfsIterable implements Iterable<E>{

		private E fromE;
		public BfsIterable(E fromE) {
			this.fromE = fromE;
		}
		
		@Override
		public Iterator<E> iterator() {
			// TODO Auto-generated method stub
			return new BfsIterator(fromE);
		}
		
		private class BfsIterator implements Iterator<E> {
			private Node<E> startNode;
			private Queue<Node<E>> toDoQueue;
			
			public BfsIterator(E fromE) {
				super();
				startNode = findNode(fromE);
				toDoQueue = new Queue<Node<E>>();
				clearState();
				toDoQueue.push(startNode);
			}

			@Override
			public boolean hasNext() {
				return !toDoQueue.empty();
			}

			@Override
			public E next() {
				Node<E> thisNode = toDoQueue.pop();
				thisNode.visited = true;
				for (Edge<E> edge : thisNode.edges) {
					if(!edge.toNode.visited) toDoQueue.push(edge.toNode);
				}
				return thisNode.label;
			}

			@Override
			public void remove() {
				
			}

		}
		
	}
	
	public Iterable<E> dfsIteratFrom(E fromE) {
		return new DfsIterable(fromE);
	}
	
	private class DfsIterable implements Iterable<E> {
		private E fromE;
		public DfsIterable(E fromE) {
			this.fromE = fromE;
		}

		@Override
		public Iterator<E> iterator() {
			// TODO Auto-generated method stub
			return new DfsIterator(fromE);
		}
		
		private class DfsIterator implements Iterator<E> {
			
			private Node<E> startNode;
			private Stack<Node<E>> toDoStack;
			
			public DfsIterator(E fromE) {
				super();
				startNode = findNode(fromE);
				toDoStack = new Stack<Node<E>>();
				clearState();
				toDoStack.push(startNode);
			}

			@Override
			public boolean hasNext() {
				return !toDoStack.empty();
			}

			@Override
			public E next() {
				Node<E> thisNode = toDoStack.pop();
				thisNode.visited = true;
				for (Edge<E> edge : thisNode.edges) {
					if(!edge.toNode.visited) {
						toDoStack.push(edge.toNode);
						edge.toNode.visited = true;
					}
				}
				return thisNode.label;
			}

			@Override
			public void remove() {
				
			}
			
		}
		
	}
	
	
}
