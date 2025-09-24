package graph;

import java.util.Iterator;

import util.Debug;
import util.Label;
import util.Pair;

public class AdjacencyMatrix {
	private int numEdges;
	private int domainSize;
	private int numMultiEdges;
	private int[] edges;
	private static final int CELL_SIZE = 6;
	private int numVertices;
	private int[] vertices;
	private int startVertex;

	/**
	 * Make a new Adjacency matrix with a given number of vertices. All vertices MUST have edges added to them during construction
	 * 
	 * @param n
	 *            The number of vertices
	 */
	public AdjacencyMatrix(int n) {
		numEdges = 0;
		numMultiEdges = 0;
		domainSize = n;
		numVertices = n;
		edges = new int[(int) Math.ceil(n * n * CELL_SIZE / 32.0)];

		vertices = new int[n];
		startVertex = 0;
		for (int i = 0; i < n - 1; i++) {
			vertices[i] = i + 1;
		}
		vertices[n - 1] = -1;

	}

	/**
	 * Deep clone an existing AdjacencyMatrix. The two are independent and identical
	 * 
	 * @param g
	 *            The AdjacencyMatrix to clone
	 */
	public AdjacencyMatrix(AdjacencyMatrix g) {
		numEdges = g.numEdges;
		numMultiEdges = g.numMultiEdges;
		domainSize = g.domainSize;
		edges = new int[g.edges.length];

		for (int i = 0; i < edges.length; i++) {
			edges[i] = g.edges[i];
		}

		numVertices = g.numVertices;
		startVertex = g.startVertex;
		vertices = new int[g.vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = g.vertices[i];
		}
	}

	/**
	 * The range of values that are vertices
	 * 
	 * @return The number of vertex there are
	 */
	public int domainSize() {
		return domainSize;
	}

	/**
	 * Vertex iterator
	 * 
	 * @return
	 */
	public Iterable<Integer> vertices() {
		return new VertexIterable();
	}

	/**
	 * Iterator of edges from v
	 * 
	 * @param v
	 *            The source of the edges
	 * @return Iterator overthe edges from v
	 */
	public Iterable<Pair<Integer, Integer>> edges(int v) {
		return new EdgeIterable(v);
	}

	public boolean equals(Object o) {
		if (o instanceof AdjacencyMatrix) {
			return this.equals((AdjacencyMatrix) o);
		}
		return false;
	}

	public boolean equals(AdjacencyMatrix a) {
		if (a.numEdges != this.numEdges) {
			return false;
		}
		if (a.numMultiEdges != this.numMultiEdges) {
			return false;
		}
		for (int i = 0; i < this.edges.length; i++) {
			if (this.edges[i] != a.edges[i]) {
				return false;
			}
		}
		return true;
	}

	public int numVertices() {
		return numVertices;
	}

	public int numEdges() {
		return numEdges;
	}

	public int numUnderlyingEdges() {
		return numEdges - numMultiEdges;
	}

	/**
	 * Return the degree of a vertex
	 * 
	 * @param vertex
	 * @return
	 */
	public int numEdges(int vertex) {
		int count = 0;
		for (Pair<Integer, Integer> i : edges(vertex)) {
			count += i.second();
		}
		return count;
	}

	/**
	 * Return the number of vertices that this vertex is connected to. This is the same as the degree except that multiedges are only counted as 1
	 * rather than their actual number
	 * 
	 * @param vertex
	 * @return
	 */
	public int numUnderlyingEdges(int vertex) {
		int count = 0;
		for (Pair<Integer, Integer> i : edges(vertex)) {
			count++;
		}
		return count;
	}

	/**
	 * Returns the number of edges between from and to
	 * 
	 * @param from
	 *            The source vertex
	 * @param to
	 *            Destination vertex
	 * @return number of edges between them
	 */
	public int numEdges(int from, int to) {
		long startBit = domainSize * CELL_SIZE * from + CELL_SIZE * to;
		int startInt = (int) (startBit / 32);
		int index = (int) (startBit % 32);
		boolean overflow = (index + CELL_SIZE) > 32;
		if (overflow) {
			long v = ((0xFFFFFFFFL &  edges[startInt])) | ((0xFFFFFFFFL & edges[startInt + 1]) << 32);
			int m = (1 << CELL_SIZE) - 1;
			long ret = (v >> index) & m;
			return (int) ret;
		} else {
			int v = edges[startInt];
			int m = (1 << CELL_SIZE) - 1;
			int ret = (v >> index) & m;
			return ret;
		}
	}

	public int numMultiedges() {
		return numMultiEdges;
	}

	public boolean isMultiGraph() {
		return numMultiEdges > 0;
	}

	/**
	 * Remove a vertex. This vertex is now guaranteed to have no edges to or from it It is also removed from the list of vertices
	 * 
	 * @param v
	 *            The vertex to clear
	 */
	public void clear(int v) {
		// Remove v from the list of vertices
		if (startVertex == v) {
			startVertex = vertices[v];
		} else {
			int i = startVertex;
			while (vertices[i] != v) {
				i = vertices[i];
				if (i == -1) {
					return;
				}
			}
			vertices[i] = vertices[v];
		}
		vertices[v] = -1;
		numVertices--;

		// first delete the edges that don't start at you
		// Now delete the columns
		for (int i = 0; i < domainSize; i++) {
			int num = numEdges(i, v);
			if (num != 0) {
				numEdges -= num;
				numMultiEdges -= (num - 1);
				setValue(i, v, 0);
			}
		}

		// Now, clear all edges involving v
		// first the row This is done long ways as this might be faster as it
		// can write an int at a time
		long startBit = domainSize * CELL_SIZE * v;
		long endBit = domainSize * CELL_SIZE * (v + 1);

		int startInt = (int) (startBit / 32);
		int endInt = (int) (endBit / 32);
		int endIndex = (int) (endBit % 32);
		int startIndex = (int) (startBit % 32);

		if (startInt == endInt) {
			// If the whole row is contain within the current int
			int m = 0xFFFFFFFF << (domainSize * CELL_SIZE);
			m |= (1 << startIndex) - 1;
			edges[startInt] &= m;
		} else {
			// clear the starting int
			if (startIndex != 0) {
				// Need to only clear the END of the starting int
				int m = (1 << startIndex) - 1;
				edges[startInt] &= m;
				startInt++;
			}
			while (startInt < endInt) {
				edges[startInt++] = 0;
			}
			// Clear as much of the last index as needed
			if (endIndex != 0) {
				int m = 0xFFFFFFFF << endIndex;
				edges[endInt] &= m;
			}

		}

	}

	private void setValue(int from, int to, int val) {
		setValue(from,to, val, domainSize,edges);
	}

	/**
	 * Add c many of an edge from from to to
	 * 
	 * @param from
	 * @param to
	 * @param c
	 * @return True if the edge already exists
	 */
	public boolean addEdge(int from, int to, int c) {
		numEdges += c;

		int num = this.numEdges(from, to);
		if (num == 0) {
			numMultiEdges += c - 1;
		} else {
			numMultiEdges += c;
		}

		c += num;

		setValue(from, to, c);
		if (from != to) {
			setValue(to, from, c);
		}

		return num != 0;
	}

	/**
	 * Add an undirected edge from from to to
	 * 
	 * @param from
	 *            Source
	 * @param to
	 *            Sink
	 * @return True if the edge already exists
	 */
	public boolean addEdge(int from, int to) {
		return addEdge(from, to, 1);
	}

	public boolean removeEdge(int from, int to, int c) {

		int i = numEdges(from, to);
		if (i == 0) {
			return false;
		}
		if (i > c) {
			// this is a multi-edge, so decrement count.
			numMultiEdges -= c;
			numEdges -= c;
			setValue(from, to, i - c);
			if (from != to) {
				setValue(to, from, i - c);
			}
		} else {
			// set to zero
			numEdges -= i;
			numMultiEdges -= (i - 1);
			setValue(from, to, 0);
			if (from != to) {
				setValue(to, from, 0);
			}
		}
		return true;
	}

	public int removeAllEdges(int from, int to) {
		// remove all edges "from--to"
		int r = numEdges(from, to);
		if (r != 0) {
			numEdges -= r;
			numMultiEdges -= (r - 1);
			setValue(from, to, 0);
			if (from != to) {
				setValue(to, from, 0);
			}
		}

		return r;
	}

	/**
	 * Remove an edge (only one in the case of a multi edge)
	 * 
	 * @param from
	 *            Source
	 * @param to
	 *            Sink
	 * @return
	 */
	public boolean removeEdge(int from, int to) {
		return removeEdge(from, to, 1);
	}

	public void remove(AdjacencyMatrix g) {
		boolean done = false;
		for (int i : g.vertices()) {
			done = false;
			out: while (!done) {
				for (Pair<Integer, Integer> j : g.edges(i)) {
					if (i >= j.first()) {
						if (removeEdge(i, j.first(), j.second())) {
							continue out;
						}
					}
				}
				done = true;
			}
		}
	}

	// Ok, this implementation is seriously inefficient!
	// could use an indirection trick here as one solution?
	//
	// POST: vertex 'from' remains, whilst vertex 'to' is removed
	void contractEdge(int from, int to) {
		if (from == to) {
			throw new RuntimeException("cannot contract a loop!");
		}
		for (Pair<Integer, Integer> i : edges(to)) {
			if (i.first() == to) {
				// is self loop
				addEdge(from, from, i.second());
			} else {
				addEdge(from, i.first(), i.second());
			}
		}
		
		clear(to);
		removeEdge(from, from,1);
	}

	public String toString() {
		StringBuilder ss = new StringBuilder();
		for (int i : vertices()) {
			for (Pair<Integer, Integer> e : edges(i)) {
				if (e.first() >= i || Debug.debug) {
					ss = ss.append(i).append(" -> ").append(e.first()).append(" x").append(e.second()).append('\n');
				}
			}
		}

		return ss.toString();
	}

	public static void setValue(int from, int to, int val, int domainSize, int []vals) {
		long startBit = domainSize * CELL_SIZE * from + CELL_SIZE * to;

		int startInt = (int) (startBit / 32);
		int startIndex = (int) (startBit % 32);

		boolean overflow = startIndex + CELL_SIZE > 32;
		if (overflow) {
			int leftOver = CELL_SIZE - 32 + startIndex;

			// First clear the value
			int maskFirst = (1 << startIndex) - 1;
			int maskSecond = ~((1 << leftOver) - 1);

			vals[startInt] &= maskFirst;
			vals[startInt + 1] &= maskSecond;

			// Write the value
			maskFirst = (val << startIndex);
			maskSecond = (val) >> (32 - startIndex);

			vals[startInt] |= maskFirst;
			vals[startInt + 1] |= maskSecond;

		} else {
			// first clear the value
			int mask = (((1 << (32 - startIndex - CELL_SIZE)) - 1) << (startIndex + CELL_SIZE)) | ((1 << startIndex) - 1);
			vals[startInt] &= mask;

			// Write the value
			mask = (val << startIndex);
			vals[startInt] |= mask;
		}
	}
	
	public int[] label(Label l){
		int[] vals = new int[edges.length];
		for(int i = 0 ; i < l.newDomain(); i++){
			for(int j = 0 ; j< l.newDomain(); j++){
				setValue(i,j,this.numEdges(l.oldName(i), l.oldName(j)),domainSize, vals);
			}
		}
		return vals;
	}
	
	public int hashCode(int[] l) {
		return Hash.hashcode(l);
	}

	private class VertexIterable implements Iterable<Integer> {

		@Override
		public Iterator<Integer> iterator() {
			return new VertexIterator(AdjacencyMatrix.this.vertices);
		}

	}

	private class EdgeIterable implements Iterable<Pair<Integer, Integer>> {
		private int vertex;

		public EdgeIterable(int v) {
			this.vertex = v;
		}

		@Override
		public Iterator<Pair<Integer, Integer>> iterator() {
			return new EdgeIterator(AdjacencyMatrix.this.edges, vertex);
		}

	}

	public class EdgeIterator implements Iterator<Pair<Integer, Integer>> {
		private int[] edges;
		private Pair<Integer, Integer> next;

		long startBit;
		long endBit;

		long currBit;
		int dest;

		public EdgeIterator(int[] edges, int vertex) {
			this.edges = edges;
			startBit = domainSize * CELL_SIZE * vertex;
			endBit = domainSize * CELL_SIZE * (vertex + 1);

			dest = 0;
			currBit = startBit;
			findNextEdge();
		}

		/**
		 * Goes through and sets next to be the next available edge if there is one
		 */
		private void findNextEdge() {
			while (true) {
				if (dest == domainSize) {
					next = null;
					return;
				}

				int startInt = (int) (currBit / 32);
				int index = (int) (currBit % 32);
				boolean overflow = (index + CELL_SIZE) > 32;

				int ret;
				if (overflow) {
					long v = ((0xFFFFFFFFL &  edges[startInt])) | ((0xFFFFFFFFL & edges[startInt + 1]) << 32);
					int m = (1 << CELL_SIZE) - 1;
					ret = (int) ((v >> index) & m);
				} else {
					int v = edges[startInt];
					int m = (1 << CELL_SIZE) - 1;
					ret = (int) ((v >> index) & m);
				}

				currBit += CELL_SIZE;
				if (ret != 0) {
					next = new Pair<Integer, Integer>(dest++, ret);
					return;
				}
				dest += 1;
			}
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public Pair<Integer, Integer> next() {
			Pair<Integer, Integer> ret = next;
			findNextEdge();
			return ret;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Remove not implemented");

		}

	}

	private class VertexIterator implements Iterator<Integer> {
		private int[] vertices;
		private int nextIndex;

		public VertexIterator(int[] vertices) {
			this.vertices = vertices;
			if (numVertices == 0) {
				nextIndex = -1;
			} else {
				nextIndex = startVertex;
			}
		}

		@Override
		public boolean hasNext() {
			return nextIndex != -1;
		}

		@Override
		public Integer next() {
			int ret = nextIndex;
			nextIndex = vertices[nextIndex];
			return ret;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Remove not implemented");
		}

	}

	public static void main(String args[]) {
		int nedges = 3;
		AdjacencyMatrix g = new AdjacencyMatrix(nedges);

		System.out.println("-----Adding one of each edge");
		for (int i = 0; i < nedges; i++) {
			for (int j = i; j < nedges; j++) {
				g.addEdge(i, j);
			}
		}

		if (g.numEdges != 6) {
			throw new RuntimeException("Adding edge failed - numEdges");
		}
		if (g.numMultiEdges != 0) {
			throw new RuntimeException("Adding edge failed - numMultiEdge");
		}
		String s = g.toString();
		String target = "0 -> 0 x1\n0 -> 1 x1\n0 -> 2 x1\n1 -> 1 x1\n1 -> 2 x1\n2 -> 2 x1\n";
		if (!s.equals(target)) {
			throw new RuntimeException("Adding edge failed - toString");
		}

		for (int i = 0; i < nedges; i++) {
			for (int j = 0; j < nedges; j++) {
				if (1 != g.numEdges(i, j)) {
					throw new RuntimeException("Adding edge failed - Normal Reading");
				}
			}
		}

		System.out.println("-----Adding one of each edge");
		for (int i = 0; i < nedges; i++) {
			for (int j = i; j < nedges; j++) {
				g.addEdge(i, j);
			}
		}

		if (g.numEdges != 12) {
			throw new RuntimeException("Adding edge failed - numEdges");
		}
		if (g.numMultiEdges != 6) {
			throw new RuntimeException("Adding edge failed - numMultiEdge");
		}
		for (int i = 0; i < nedges; i++) {
			for (int j = 0; j < nedges; j++) {
				if (2 != g.numEdges(i, j)) {
					throw new RuntimeException("Adding edge failed - Normal Reading");
				}
			}
		}

		s = g.toString();
		target = "0 -> 0 x2\n0 -> 1 x2\n0 -> 2 x2\n1 -> 1 x2\n1 -> 2 x2\n2 -> 2 x2\n";
		if (!s.equals(target)) {
			throw new RuntimeException("Adding edge failed - toString");
		}

		System.out.println("-----Remove one from each edges");
		for (int i = 0; i < nedges; i++) {
			for (int j = i; j < nedges; j++) {
				g.removeEdge(i, j);
			}
		}

		if (g.numEdges != 6) {
			throw new RuntimeException("Remove edge failed - numEdges");
		}
		if (g.numMultiEdges != 0) {
			throw new RuntimeException("Remove edge failed - numMultiEdge");
		}
		s = g.toString();
		target = "0 -> 0 x1\n0 -> 1 x1\n0 -> 2 x1\n1 -> 1 x1\n1 -> 2 x1\n2 -> 2 x1\n";
		if (!s.equals(target)) {
			throw new RuntimeException("Remove edge failed - toString");
		}

		for (int i = 0; i < nedges; i++) {
			for (int j = 0; j < nedges; j++) {
				if (1 != g.numEdges(i, j)) {
					throw new RuntimeException("Remove edge failed - Normal Reading");
				}
			}
		}

		System.out.println("-----Remove vertex 0");
		g.clear(0);
		if (g.numEdges != 3) {
			System.err.println("Adding edge failed - numEdges");
		}
		if (g.numMultiEdges != 0) {
			System.err.println("Adding edge failed - numMultiEdge");
		}
		s = g.toString();
		target = "1 -> 1 x1\n1 -> 2 x1\n2 -> 2 x1\n";
		if (!s.equals(target)) {
			System.err.println("Adding edge failed - toString");
		}

		for (int i = 0; i < nedges; i++) {
			for (int j = 0; j < nedges; j++) {
				if (((i == 0 || j == 0) && 0 != g.numEdges(i, j)) || (i != 0 && j != 0 && 1 != g.numEdges(i, j))) {
					System.err.println("Adding edge failed - Normal Reading");
				}
			}
		}

		System.out.println("-----Testing clones");
		AdjacencyMatrix g2 = new AdjacencyMatrix(g);
		if (g.numEdges != g2.numEdges) {
			System.err.println("Clone numedges failed");
		}
		if (g.numMultiEdges != g2.numMultiEdges) {
			System.err.println("Clone numMultiEdges failed");
		}
		if (g.hashCode() != g2.hashCode()) {
			System.err.println("Clone hashCode failed");
		}
		if (!g.equals(g2)) {
			System.err.println("Clone equals failed");
		}

		System.out.println("-----Removing 1 and 2 from G2");
		g2.clear(1);
		g2.clear(2);

		if (g2.numEdges != 0) {
			System.err.println("clear failed - numEdges");
		}
		if (g2.numMultiEdges != 0) {
			System.err.println("clear failed - numMultiEdge");
		}
		s = g2.toString();
		target = "";
		if (!s.equals(target)) {
			System.err.println("clear failed - toString");
		}
		for (int i = 0; i < nedges; i++) {
			for (int j = 0; j < nedges; j++) {
				if (0 != g2.numEdges(i, j)) {
					System.err.println("clear - Normal Reading");
				}
			}
		}

		if (g.numEdges != 3) {
			System.err.println("Adding edge failed - numEdges");
		}
		if (g.numMultiEdges != 0) {
			System.err.println("Adding edge failed - numMultiEdge");
		}
		s = g.toString();
		target = "1 -> 1 x1\n1 -> 2 x1\n2 -> 2 x1\n";
		if (!s.equals(target)) {
			System.err.println("Adding edge failed - toString");
		}

		for (int i = 0; i < nedges; i++) {
			for (int j = 0; j < nedges; j++) {
				if (((i == 0 || j == 0) && 0 != g.numEdges(i, j)) || (i != 0 && j != 0 && 1 != g.numEdges(i, j))) {
					System.err.println("Adding edge failed - Normal Reading");
				}
			}
		}

		System.out.println("-----Adding 4, removing 3 and adding 5 from 1 -- 2");
		g.addEdge(1, 2, 4);
		g.removeEdge(1, 2, 3);
		g.addEdge(1, 2, 5);

		s = g.toString();
		target = "1 -> 1 x1\n1 -> 2 x7\n2 -> 2 x1\n";
		if (!s.equals(target)) {
			System.err.println("Adding edge failed - toString");
		}

		for (int i = 0; i < nedges; i++) {
			for (int j = 0; j < nedges; j++) {
				if (g.numEdges(i, j) != g.numEdges(j, i)) {
					System.err.println("Adding edge failed - Normal Reading");
				}
			}
		}

		if (g.numEdges != 9) {
			System.err.println("clear failed - numEdges");
		}
		if (g.numMultiEdges != 6) {
			System.err.println("clear failed - numMultiEdge");
		}

		System.out.println("-----Clearing 2");
		g.clear(2);

		s = g.toString();
		target = "1 -> 1 x1\n";
		if (!s.equals(target)) {
			System.err.println("Adding edge failed - toString");
		}
		if (g.numEdges != 1) {
			System.err.println("clear failed - numEdges");
		}
		if (g.numMultiEdges != 0) {
			System.err.println("clear failed - numMultiEdge");
		}

		System.out.println("-----Removing 2 from 1 -- 1");
		g.removeEdge(1, 1, 2);
		if (g.numEdges != 0) {
			System.err.println("clear failed - numEdges");
		}
		if (g.numMultiEdges != 0) {
			System.err.println("clear failed - numMultiEdge");
		}

		s = g.toString();
		target = "";
		if (!s.equals(target)) {
			System.err.println("Clear - toString");
		}

		for (int i = 0; i < nedges; i++) {
			for (int j = 0; j < nedges; j++) {
				if (g.numEdges(i, j) != 0) {
					System.err.println("clear failed - Normal Reading");
				}
			}
		}

		System.out.println("-----Making k3 with pairs");
		g = new AdjacencyMatrix(3);

		for (int i = 0; i < nedges; i++) {
			for (int j = i; j < nedges; j++) {
				g.addEdge(i, j, 2);
			}
		}

		if (g.numEdges != 12) {
			System.err.println("Adding edge failed - numEdges");
		}
		if (g.numMultiEdges != 6) {
			System.err.println("Adding edge failed - numMultiEdge");
		}
		for (int i = 0; i < nedges; i++) {
			for (int j = 0; j < nedges; j++) {
				if (2 != g.numEdges(i, j)) {
					System.err.println("Adding edge failed - Normal Reading");
				}
			}
		}

		s = g.toString();
		target = "0 -> 0 x2\n0 -> 1 x2\n0 -> 2 x2\n1 -> 1 x2\n1 -> 2 x2\n2 -> 2 x2\n";
		if (!s.equals(target)) {
			System.err.println("Adding edge failed - toString");
			System.err.println(s);
		}
		
		System.out.println("-----Testing Contraction (1,0)");
		g.contractEdge(0, 1);
		if(g.numEdges != 11){
			throw new RuntimeException("contraction " + g.numEdges);
		}
		if(g.numMultiEdges != 8){
			throw new RuntimeException();
		}
		
		s = g.toString();
		target = "0 -> 0 x5\n0 -> 2 x4\n2 -> 2 x2\n";
		if (!s.equals(target)) {
			System.err.println("Adding edge failed - toString");
			System.err.println(s);
		}
		
		for (int i = 0; i < nedges; i++) {
			for (int j = 0; j < nedges; j++) {
				if (g.numEdges(j,i) != g.numEdges(i, j)) {
					System.err.println("Adding edge failed - Normal Reading");
				}
			}
		}
		
		System.out.println("-----Removing Cycles");
		int count = 0;
		for(int i : g.vertices()){
			count += g.removeAllEdges(i, i);
		}
		
		if(count != 7){
			throw new RuntimeException();
		}
		
		if(g.numEdges != 4){
			throw new RuntimeException();
		}
		if(g.numMultiEdges != 3){
			throw new RuntimeException();
		}
		
		s = g.toString();
		target = "0 -> 2 x4\n";
		if (!s.equals(target)) {
			System.err.println("Adding edge failed - toString");
			System.err.println(s);
		}
		
		for (int i = 0; i < nedges; i++) {
			for (int j = 0; j < nedges; j++) {
				if (g.numEdges(j,i) != g.numEdges(i, j)) {
					System.err.println("Adding edge failed - Normal Reading");
				}
			}
		}
		
		System.out.println("-----Testing Contraction (2,0)");
		g.contractEdge(0, 2);
		if(g.numEdges != 3){
			throw new RuntimeException("contraction " + g.numEdges);
		}
		if(g.numMultiEdges != 2){
			throw new RuntimeException();
		}
		
		s = g.toString();
		target = "0 -> 0 x3\n";
		if (!s.equals(target)) {
			System.err.println("Adding edge failed - toString");
			System.err.println(s);
		}
		
		for (int i = 0; i < nedges; i++) {
			for (int j = 0; j < nedges; j++) {
				if (g.numEdges(j,i) != g.numEdges(i, j)) {
					System.err.println("Adding edge failed - Normal Reading");
				}
			}
		}
		
	}
}
