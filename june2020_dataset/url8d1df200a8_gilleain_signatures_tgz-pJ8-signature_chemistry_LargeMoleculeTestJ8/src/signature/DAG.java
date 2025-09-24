package signature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A directed acyclic graph that is the core data structure of a signature. It
 * is the DAG that is canonized by sorting its layers of nodes.
 * 
 * @author maclean
 *
 */
public class DAG implements Iterable<List<DAG.Node>> {
    
    /**
     * The direction up and down the DAG. UP is from leaves to root.
     *
     */
    public enum Direction { UP, DOWN };
	
	/**
	 * A node of the directed acyclic graph
	 *
	 */
	public class Node implements VisitableDAG {
		
		/**
		 * The index of the vertex in the graph. Note that for signatures that
		 * cover only part of the graph (with a height less than the diameter)
		 * this index may have to be mapped to the original index 
		 */
		public final int vertexIndex;
		
		/**
		 * The parent nodes in the DAG
		 */
		public final List<Node> parents;
		
		/**
		 * The child nodes in the DAG
		 */
		public final List<Node> children;
		
		/**
		 * What layer this node is in
		 */
		public final int layer;
		
		/**
		 * Labels for the edges between this node and the parent nodes
		 */
		public final Map<Integer, Integer> edgeColors;
		
		/**
		 * The final computed invariant, used for sorting children when printing
		 */
		public int invariant;
		
		/**
		 * Make a Node that refers to a vertex, in a layer, and with a label.
		 * 
		 * @param vertexIndex the graph vertex index
		 * @param layer the layer of this Node
		 */
		public Node(int vertexIndex, int layer) {
			this.vertexIndex = vertexIndex;
			this.layer = layer;
			this.parents = new ArrayList<Node>();
			this.children = new ArrayList<Node>();
			this.edgeColors = new HashMap<Integer, Integer>();
		}
	
        public void addParent(Node node) {
			this.parents.add(node);
		}
		
		public void addChild(Node node) {
			this.children.add(node);
		}
		
		public void addEdgeColor(int partnerIndex, int edgeColor) {
            this.edgeColors.put(partnerIndex, edgeColor);
        }
		
		public void accept(DAGVisitor visitor) {
		    visitor.visit(this);
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
		    StringBuffer parentString = new StringBuffer();
		    parentString.append('[');
		    for (Node parent : this.parents) {
		        parentString.append(parent.vertexIndex).append(',');
		    }
		    if (parentString.length() > 1) {
		        parentString.setCharAt(parentString.length() - 1, ']');
		    } else {
		        parentString.append(']');
		    }
		    StringBuffer childString = new StringBuffer();
		    childString.append('[');
            for (Node child : this.children) {
                childString.append(child.vertexIndex).append(',');
            }
            if (childString.length() > 1) {
                childString.setCharAt(childString.length() - 1, ']');    
            } else {
                childString.append(']');
            }
            
            return vertexIndex + " " 
                  + " (" + parentString + ", " + childString + ")";
		}

	}
	
	/**
	 * An arc of the directed acyclic graph.
	 *
	 */
	public class Arc {
		
		public final int a;
		
		public final int b;
		
		public Arc(int a, int b) {
			this.a = a;
			this.b = b;
		}
		
		public boolean equals(Object other) {
			if (other instanceof Arc) {
				Arc o = (Arc) other;
				return (this.a == o.a && this.b == o.b)
					|| (this.a == o.b && this.b == o.a);
			} else {
				return false;
			}
		}
	}
	
	/**
	 * Comparator for nodes based on String labels.
	 *
	 */
	public class NodeStringLabelComparator implements Comparator<Node> {
	    
	    /**
	     * The labels for vertices.
	     */
	    public String[] vertexLabels;
	    
	    public NodeStringLabelComparator(String[] vertexLabels) {
	        this.vertexLabels = vertexLabels;
	    }

        public int compare(Node o1, Node o2) {
            String o1s = this.vertexLabels[o1.vertexIndex];
            String o2s = this.vertexLabels[o2.vertexIndex];
            int c = o1s.compareTo(o2s);
            if (c == 0) {
                if (o1.invariant < o2.invariant) {
                    return -1;
                } else if (o1.invariant > o2.invariant) {
                    return 1;
                } else {
                    return 0;
                }
            } else {
                return c;
            }
        }
	}
	
	/**
     * Comparator for nodes based on Integer labels.
     *
     */
    public class NodeIntegerLabelComparator implements Comparator<Node> {
        
        /**
         * The labels for vertices.
         */
        public int[] vertexLabels;
        
        public NodeIntegerLabelComparator(int[] vertexLabels) {
            this.vertexLabels = vertexLabels;
        }

        public int compare(Node o1, Node o2) {
            int o1n = this.vertexLabels[o1.vertexIndex];
            int o2n = this.vertexLabels[o2.vertexIndex];
            int c = (o1n == o2n)? 0 :(o1n < o2n? -1 : 1);
            if (c == 0) {
                if (o1.invariant < o2.invariant) {
                    return -1;
                } else if (o1.invariant > o2.invariant) {
                    return 1;
                } else {
                    return 0;
                }
            } else {
                return c;
            }
        }
    }
	
	/**
	 * Used to sort nodes, it is public so that the AbstractVertexSignature
	 * can use it 
	 */
	public Comparator<Node> nodeComparator;
	
	/**
	 * The layers of the DAG
	 */
	private List<List<Node>> layers;
	
	/**
	 * The counts of parents for vertices  
	 */
	private int[] parentCounts;
	
	/**
     * The counts of children for vertices  
     */
    private int[] childCounts;
	
	private Invariants invariants;
	
	/**
	 * Convenience reference to the nodes of the DAG
	 */
	private List<DAG.Node> nodes;
	
	/**
	 * A convenience record of the number of vertices
	 */
	private int vertexCount;
	
    /**
     * Create a DAG from a graph, starting at the root vertex.
     * 
     * @param rootVertexIndex the vertex to start from
     * @param graphVertexCount the number of vertices in the original graph
     */
	public DAG(int rootVertexIndex, int graphVertexCount) {
		this.layers = new ArrayList<List<Node>>();
		this.nodes = new ArrayList<Node>();
		List<Node> rootLayer = new ArrayList<Node>();
		Node rootNode = new Node(rootVertexIndex, 0);
		rootLayer.add(rootNode);
		this.layers.add(rootLayer);
		this.nodes.add(rootNode);
		
		this.vertexCount = 1;
		this.parentCounts = new int[graphVertexCount];
		this.childCounts = new int[graphVertexCount];
	}
	
	public Iterator<List<Node>> iterator() {
		return layers.iterator();
	}
	
	public List<DAG.Node> getRootLayer() {
	    return this.layers.get(0);
	}
	
	public DAG.Node getRoot() {
		return this.layers.get(0).get(0);
	}
	
	public Invariants copyInvariants() {
	    return (Invariants) this.invariants.clone();
	}
	
	/**
	 * Initialize the invariants, assuming that the vertex count for the
	 * signature is the same as the length of the label array.
	 */
	public void initializeWithStringLabels(String[] vertexLabels) {
	    vertexCount = vertexLabels.length;
	    this.invariants = new Invariants(vertexCount, nodes.size());
	    
        List<InvariantIntStringPair> pairs = 
            new ArrayList<InvariantIntStringPair>();
        for (int i = 0; i < vertexCount; i++) {
            String l = vertexLabels[i];
            int p = parentCounts[i];
            pairs.add(new InvariantIntStringPair(l, p, i));
        }
        Collections.sort(pairs);
        
        if (pairs.size() == 0) return;
        
        nodeComparator = new NodeStringLabelComparator(vertexLabels);
        int order = 1;
        InvariantIntStringPair first = pairs.get(0);
        invariants.setVertexInvariant(first.getOriginalIndex(), order);
        for (int i = 1; i < pairs.size(); i++) {
            InvariantIntStringPair a = pairs.get(i - 1);
            InvariantIntStringPair b = pairs.get(i);
            if (!a.equals(b)) {
                order++;
            }
            invariants.setVertexInvariant(b.getOriginalIndex(), order);
        }
    }
	
	public void initializeWithIntLabels(int[] vertexLabels) {
	    vertexCount = vertexLabels.length;
        this.invariants = new Invariants(vertexCount, nodes.size());
        
        List<InvariantIntIntPair> pairs = new ArrayList<InvariantIntIntPair>();
        for (int i = 0; i < vertexCount; i++) {
            int l = vertexLabels[i];
            int p = parentCounts[i];
            pairs.add(new InvariantIntIntPair(l, p, i));
        }
        Collections.sort(pairs);
        
        if (pairs.size() == 0) return;
        
        nodeComparator = new NodeIntegerLabelComparator(vertexLabels);
        int order = 1;
        InvariantIntIntPair first = pairs.get(0);
        invariants.setVertexInvariant(first.getOriginalIndex(), order);
        for (int i = 1; i < pairs.size(); i++) {
            InvariantIntIntPair a = pairs.get(i - 1);
            InvariantIntIntPair b = pairs.get(i);
            if (!a.equals(b)) {
                order++;
            }
            invariants.setVertexInvariant(b.getOriginalIndex(), order);
        }
	}
	
    public void setColor(int vertexIndex, int color) {
	    this.invariants.setColor(vertexIndex, color);
	}
	
	public int occurences(int vertexIndex) {
	    int count = 0;
	    for (Node node : nodes) {
	        if (node.vertexIndex == vertexIndex) {
	            count++;
	        }
	    }
	    return count;
	}
	
	public void setInvariants(Invariants invariants) {
//	    this.invariants = invariants;
	    this.invariants.colors = invariants.colors.clone();
	    this.invariants.nodeInvariants = invariants.nodeInvariants.clone();
	    this.invariants.vertexInvariants = invariants.vertexInvariants.clone();
	}
	
	/**
	 * Create and return a DAG.Node, while setting some internal references to
	 * the same data. Does not add the node to a layer.
	 * 
	 * @param vertexIndex the index of the vertex in the original graph
	 * @param layer the index of the layer
	 * @return the new node 
	 */
	
	public DAG.Node makeNode(int vertexIndex, int layer) {
        DAG.Node node = new DAG.Node(vertexIndex, layer);
        this.nodes.add(node);
        return node;
    }
	
	/**
	 * Create and return a DAG.Node, while setting some internal references to
     * the same data. Note: also adds the node to a layer, creating it if 
     * necessary.
     * 
	 * @param vertexIndex the index of the vertex in the original graph
     * @param layer the index of the layer
     * @return the new node
	 */
	public DAG.Node makeNodeInLayer(int vertexIndex, int layer) {
        DAG.Node node = this.makeNode(vertexIndex, layer);
        if (layers.size() <= layer) {
          this.layers.add(new ArrayList<DAG.Node>());
        }
        this.layers.get(layer).add(node);
        return node;
    }
	
	public void addRelation(DAG.Node childNode, DAG.Node parentNode) {
	    childNode.parents.add(parentNode);
	    parentCounts[childNode.vertexIndex]++;
	    childCounts[parentNode.vertexIndex]++;
	    parentNode.children.add(childNode);
	}
	
	public int[] getParentsInFinalString() {
	    int[] counts = new int[vertexCount];
	    getParentsInFinalString(
	            counts, getRoot(), null,  new ArrayList<DAG.Arc>());
	    return counts;
	}
	
	private void getParentsInFinalString(int[] counts, DAG.Node node,
            DAG.Node parent, List<DAG.Arc> arcs) {
	    if (parent != null) {
	        counts[node.vertexIndex]++;
	    }
	    Collections.sort(node.children, nodeComparator);
	    for (DAG.Node child : node.children) {
            DAG.Arc arc = new Arc(node.vertexIndex, child.vertexIndex);
            if (arcs.contains(arc)) {
                continue;
            } else {
                arcs.add(arc);
                getParentsInFinalString(counts, child, node, arcs);
            }
        }
	          
	}
	
	 /**
     * Count the occurrences of each vertex index in the final signature string.
     * Since duplicate DAG edges are removed, this count will not be the same as
     * the simple count of occurrences in the DAG before printing.
     *  
     * @return
     */
    public int[] getOccurrences() {
        int[] occurences = new int[vertexCount];
        getOccurences(occurences, getRoot(), null, new ArrayList<DAG.Arc>());
        return occurences;
    }
    
    private void getOccurences(int[] occurences, DAG.Node node,
            DAG.Node parent, List<DAG.Arc> arcs) {
        occurences[node.vertexIndex]++;
        Collections.sort(node.children, nodeComparator);
        for (DAG.Node child : node.children) {
            DAG.Arc arc = new Arc(node.vertexIndex, child.vertexIndex);
            if (arcs.contains(arc)) {
                continue;
            } else {
                arcs.add(arc);
                getOccurences(occurences, child, node, arcs);
            }
        }
    }
	
	public List<InvariantInt> getInvariantPairs(int[] parents) {
	    List<InvariantInt> pairs = new ArrayList<InvariantInt>();
	    for (int i = 0; i < this.vertexCount; i++) {
	        if (invariants.getColor(i) == -1
	                && parents[i] >= 2) {
	            pairs.add(
	                    new InvariantInt(
	                            invariants.getVertexInvariant(i), i));
	        }
	    }
	    Collections.sort(pairs);
	    return pairs;
	}
	
	public int colorFor(int vertexIndex) {
		return this.invariants.getColor(vertexIndex);
	}
	
	public void accept(DAGVisitor visitor) {
	    this.getRoot().accept(visitor);
	}

	public void addLayer(List<Node> layer) {
		this.layers.add(layer);
	}
	
	public List<Integer> createOrbit(int[] parents) {
	    
	    // get the orbits
	    Map<Integer, List<Integer>> orbits = 
	        new HashMap<Integer, List<Integer>>();
	    for (int j = 0; j < vertexCount; j++) {
	        if (parents[j] >= 2) {
	            int invariant = invariants.getVertexInvariant(j);
	            List<Integer> orbit;
	            if (orbits.containsKey(invariant)) {
	                orbit = orbits.get(invariant);
	            } else {
	                orbit = new ArrayList<Integer>();
	                orbits.put(invariant, orbit);
	            }
	            orbit.add(j);
	        }
	    }
	    
//	    System.out.println("Orbits " + orbits);
	    
	    // find the largest orbit
	    if (orbits.isEmpty()) {
	        return new ArrayList<Integer>();
	    } else {
	        List<Integer> maxOrbit = null;
	        List<Integer> invariants = new ArrayList<Integer>(orbits.keySet());
	        Collections.sort(invariants);
	        
	        for (int invariant : invariants) {
	            List<Integer> orbit = orbits.get(invariant);
	            if (maxOrbit == null || orbit.size() > maxOrbit.size()) { 
	                maxOrbit = orbit;
	            }
	        }
	        return maxOrbit;
	    }
	}
	
	public void computeVertexInvariants() {
	    Map<Integer, int[]> layerInvariants = new HashMap<Integer, int[]>();
	    for (int i = 0; i < this.nodes.size(); i++) {
	        DAG.Node node = this.nodes.get(i);
	        int j = node.vertexIndex;
	        int[] layerInvariantsJ;
	        if (layerInvariants.containsKey(j)) {
	            layerInvariantsJ = layerInvariants.get(j);
	        } else {
	            layerInvariantsJ = new int[this.layers.size()];
	            layerInvariants.put(j, layerInvariantsJ);
	        }
	        layerInvariantsJ[node.layer] = invariants.getNodeInvariant(i); 
	    }
	    
	    List<InvariantArray> invariantLists = new ArrayList<InvariantArray>();
	    for (int i : layerInvariants.keySet()) {
	        InvariantArray invArr = new InvariantArray(layerInvariants.get(i), i); 
	        invariantLists.add(invArr);
	    }
	    Collections.sort(invariantLists);
	    
	    int order = 1;
	    int first = invariantLists.get(0).originalIndex;
	    invariants.setVertexInvariant(first, 1);
	    for (int i = 1; i < invariantLists.size(); i++) {
	        InvariantArray a = invariantLists.get(i - 1);
	        InvariantArray b = invariantLists.get(i);
	        if (!a.equals(b)) {
	            order++;
	        }
	        invariants.setVertexInvariant(b.originalIndex, order);
	    }

	}
	
	public void updateVertexInvariants() {
	    int[] oldInvariants = new int[vertexCount];
	    boolean invariantSame = true;
	    while (invariantSame) {
	        oldInvariants = invariants.getVertexInvariantCopy();
	        
	        updateNodeInvariants(Direction.UP); // From the leaves to the root
	        
	        // This is needed here otherwise there will be cases where a node 
	        // invariant is reset when the tree is traversed down. 
	        // This is not mentioned in Faulon's paper.
	        computeVertexInvariants(); 
	        
	        updateNodeInvariants(Direction.DOWN); // From the root to the leaves
	        computeVertexInvariants();
	        
	        invariantSame = 
	            checkInvariantChange(
	                    oldInvariants, invariants.getVertexInvariants());
//	        System.out.println(
//	               "invs\t" +
//	               java.util.Arrays.toString(invariants.getVertexInvariants()));
	    }
	    
	    // finally, copy the node invariants into the nodes, for easy sorting
	    for (int i = 0; i < this.nodes.size(); i++) {
	        this.nodes.get(i).invariant = invariants.getNodeInvariant(i);
	    }
	}
	
	public boolean checkInvariantChange(int[] a, int[] b) {
	    for (int i = 0; i < vertexCount; i++) {
	        if (a[i] != b[i]) {
	            return true;
	        }
        }
	    return false;
	}
	
	public void updateNodeInvariants(DAG.Direction direction) {
	    int start, end, increment;
	    if (direction == Direction.UP) {
	        start = this.layers.size() - 1;
            // The root node is not included but it doesn't matter since it
            // is always alone.
	        end = -1; 
	        increment = -1;
	    } else {
	        start = 0;
	        end = this.layers.size();
	        increment = 1;
	    }
	    
        for (int i = start; i != end; i += increment) {
           this.updateLayer(this.layers.get(i), direction);
        }
        
	}
	
	public void updateLayer(List<DAG.Node> layer, DAG.Direction direction) {
	    List<InvariantList> nodeInvariantList = 
            new ArrayList<InvariantList>();
        for (int i = 0; i < layer.size(); i++) {
            DAG.Node layerNode = layer.get(i);
            int x = layerNode.vertexIndex;
            InvariantList nodeInvariant = 
                new InvariantList(nodes.indexOf(layerNode));
            nodeInvariant.add(this.invariants.getColor(x));
            nodeInvariant.add(this.invariants.getVertexInvariant(x));
            
            List<Integer> relativeInvariants = new ArrayList<Integer>();

            // If we go up we should check the children.
            List<DAG.Node> relatives = (direction == Direction.UP) ? 
                    layerNode.children : layerNode.parents;
            for (Node relative : relatives) {
                int j = this.nodes.indexOf(relative);
                int inv = this.invariants.getNodeInvariant(j);
//                System.out.println(layerNode.edgeColors + " getting " + relative.vertexIndex);
                int edgeColor;
                if (direction == Direction.UP) {
                    edgeColor = relative.edgeColors.get(layerNode.vertexIndex);
                } else {
                    edgeColor = layerNode.edgeColors.get(relative.vertexIndex);
                }
//            	relativeInvariants.add(inv * edgeColor);
//                relativeInvariants.add(inv * (edgeColor + 1));
                
                relativeInvariants.add(inv);
                relativeInvariants.add(vertexCount + 1 + edgeColor);
            }
            Collections.sort(relativeInvariants);
            nodeInvariant.addAll(relativeInvariants);
            nodeInvariantList.add(nodeInvariant);
        }
        
        Collections.sort(nodeInvariantList);
//        System.out.println(nodeInvariantList + " for layer " + layer + " " + direction);
        
        int order = 1;
        int first = nodeInvariantList.get(0).originalIndex;
        this.invariants.setNodeInvariant(first, order);
        for (int i = 1; i < nodeInvariantList.size(); i++) {
            InvariantList a = nodeInvariantList.get(i - 1);
            InvariantList b = nodeInvariantList.get(i);
            if (!a.equals(b)) {
                order++;
            }
            this.invariants.setNodeInvariant(b.originalIndex, order);
        }
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (List<Node> layer : this) {
			buffer.append(layer);
			buffer.append("\n");
		}
		return buffer.toString();
	}
}
