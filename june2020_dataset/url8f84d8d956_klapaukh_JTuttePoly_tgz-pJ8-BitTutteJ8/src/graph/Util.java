package graph;

import java.util.ArrayList;
import java.util.BitSet;

import util.Pair;

// NOTES: only works for connected graphs at the moment

public final class Util {
	
	public static boolean isomorphic(Graph g1, Graph g2) {
		// First, apply simple tests
		if (g1.numVertices() != g2.numVertices() || g1.numEdges() != g2.numEdges()) {
			return false;
		}			
		
		// Second, select spine for g2 and partition
		ArrayList<BitSet> g2_partitions = null;
		int g2_partitions_size = Integer.MIN_VALUE;
		int w=0;				
//		for(int i=0;i!=g2.size();++i) {
		for(int i: g2.vertices()) {
			ArrayList<BitSet> partitions = initialPartition(i,g2);
			if(partitions.size() > g2_partitions_size) {
				w = i;
				g2_partitions = partitions;
				g2_partitions_size = g2_partitions.size();
			}
		}
		
		int w_degree = g2.numEdges(w);
		partition(w,g2_partitions,g2);		
		Matrix g2_normalised = normalise(g2_partitions,g2);
		
		// Third, try to find candidates to check off
		
		outer:
//		for(int i=0;i!=g1.size();++i) {
		for(int i: g1.vertices()) {
			ArrayList<BitSet> g1_partitions = initialPartition(i,g1);
			int g1_partitions_size = g1_partitions.size(); 
			
			if(g1_partitions_size > g2_partitions_size) {
				// definitely not isomorphic
				break;
			} else if (g1_partitions_size == g2_partitions_size
					&& g1.numEdges(i) == w_degree) {
				
				// partition the candidate
				partition(i,g1_partitions,g1);
				
				// check partition cardinality
				for(int j=0;j!=g1_partitions.size();++j) {
					BitSet g1_partition = g1_partitions.get(j);
					BitSet g2_partition = g2_partitions.get(j);
					if(g1_partition.cardinality() != g2_partition.cardinality()) {
						// hmmm, yuk
						continue outer;
					}
				}
				
				// normalise and check candidate
				Matrix g1_normalised = normalise(g1_partitions,g1);				
				
				if(g1_normalised.equals(g2_normalised)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static Matrix normalise(ArrayList<BitSet> partitions, Graph g) {
		int[] rpartitions = reversePartitions(partitions,g);
		Matrix weights = new Matrix(partitions.size(),partitions.size());
		
		for(int i=0;i!=partitions.size();++i) {
			BitSet partition = partitions.get(i);
			int rep = partition.nextSetBit(0);			
			
			for(Pair<Integer,Integer> w : g.edges(rep)) {
				int pIndex = rpartitions[w.first()];
				int weight = weights.get(i,pIndex);
				weights.set(weight+1,i,pIndex);
			}			
		}
		
		return weights;
	}
	
	public static void partition(int v, ArrayList<BitSet> partitions, Graph g) {
		boolean changed = true;
		while(changed) {
			changed = false;
			for(int i=0;i!=partitions.size();++i) {
				if(split(i,partitions,g)) {
					changed = true;
					break;
				}
			}
		}		
	}
	
	public static boolean split(int index, ArrayList<BitSet> partitions, Graph g) {
	
		// this algorithm is horrendously inefficient
		
		int[] rpartitions = reversePartitions(partitions,g);
		BitSet partition = partitions.get(index);
		BitSet lowPartition = new BitSet(partition.size());
		BitSet highPartition = new BitSet(partition.size());
		
		// third, split into two groups
		for(int i = 0; i != partitions.size();++i) {
			// second, calculate lowLink and lowWeight
			int lowLink = Integer.MAX_VALUE;
			int lowWeight = Integer.MAX_VALUE;
			
			lowPartition.clear();
			highPartition.clear();
			for (int w = partition.nextSetBit(0); w >= 0; w = partition
					.nextSetBit(w + 1)) {	
				
				int wLowLink = Integer.MAX_VALUE;
				int wLowWeight = Integer.MAX_VALUE;
				
				for (Pair<Integer,Integer> u : g.edges(w)) {
					int pIndex = rpartitions[u.first()];
					if (i <= pIndex && pIndex < wLowLink) {
						wLowLink = pIndex;
						wLowWeight = 1;
					} else if (pIndex == wLowLink) {
						wLowWeight++;
					}
				}

				if (wLowLink < lowLink
						|| (wLowLink == lowLink && wLowWeight < lowWeight)) {
					lowLink = wLowLink;
					lowWeight = wLowWeight;
					highPartition.or(lowPartition);
					lowPartition.clear();
					lowPartition.set(w);
				} else if (wLowLink == lowLink && wLowWeight == lowWeight) {
					lowPartition.set(w);
				} else {
					highPartition.set(w);
				}
			}

			if(!highPartition.isEmpty() && !lowPartition.isEmpty()) {			
				partitions.set(index,lowPartition);
				partitions.add(index+1,highPartition);
				return true;
			}	
		}
		
		return false;
	}
	
	private static final BitSet visited = new BitSet();
	
	public static ArrayList<BitSet> initialPartition(int v, Graph g) {
		// this is basically a breadth-first search from v in g
		visited.clear();
		ArrayList<BitSet> partitions = new ArrayList<BitSet>();
		BitSet init = new BitSet();
		init.set(v);
		visited.set(v);
		partitions.add(init);
		while (visited.cardinality() != g.numVertices()) {
			BitSet partition = partitions.get(partitions.size() - 1);
			BitSet npartition = new BitSet();
			for (int w = partition.nextSetBit(0); w >= 0; w = partition
					.nextSetBit(w + 1)) {
				Iterable<Pair<Integer,Integer>> outEdges = g.edges(w);
				for (Pair<Integer,Integer> u : outEdges) {
					if (!visited.get(u.first())) {
						visited.set(u.first());
						npartition.set(u.first());
					}
				}
			}
			partitions.add(npartition);
		}
		return partitions;
	}

	/**
	 * The reverse partitions map is a map from vertices to the partition they
	 * are in.
	 * 
	 * @param partitions
	 * @param g
	 * @return
	 */
	public static int[] reversePartitions(
			ArrayList<BitSet> partitions, Graph g) {
		int[] rpartitions = new int[g.numVertices()];
		for (int i = 0; i != partitions.size(); ++i) {
			BitSet bs = partitions.get(i);
			for(int w=bs.nextSetBit(0); w>=0; w=bs.nextSetBit(w+1)) {
				rpartitions[w] = i;			
			}			
		}
		return rpartitions;
	}
}
