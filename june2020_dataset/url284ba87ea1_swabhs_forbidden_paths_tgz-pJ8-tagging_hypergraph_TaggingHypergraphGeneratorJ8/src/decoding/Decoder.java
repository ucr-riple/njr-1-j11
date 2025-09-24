package decoding;

import hypergraph.HypergraphProto.Hypergraph;
import hypergraph.HypergraphProto.Vertex;

import java.util.ArrayList;
import java.util.List;

import semiring.Derivation;

public class Decoder {
	
	public class Result {
		
		Vertex vertex;
		Double score;
		
		Result(Vertex vertex, Double score) {
			this.vertex = vertex;
			this.score = score;
		}
		
		protected Vertex getVertex() {
			return vertex;
		}
		
		protected void setVertex(Vertex v) {
			this.vertex = v;
		}
		
		protected Double getScore() {
			return score;
		}
		
		protected void setScore(Double score) {
			this.score = score;
		}
	}
	
	/**
	 * 
	 * @param rootDerivations
	 * @param h
	 * @return
	 */
	public String getKBestPaths(List<Derivation> rootDerivations, Hypergraph h) {
		String debugString = "";
		for (Derivation kthBest: rootDerivations) {
			List<Result> results = new ArrayList<Result>();
			List<Integer> edgesInPath = new ArrayList<Integer>();
			getPath(kthBest, edgesInPath, results, h);
			debugString += getDebugString(results);
		}
		return debugString;
	}
	
	/**
	 * Recursive procedure to get a hyperpath from root to terminal
	 * @param d
	 * @param results
	 * @param h
	 */
	protected void getPath(Derivation d, List<Integer> edgesInPath, List<Result> results, Hypergraph h) {
		// Base Case
		if (d.getE() == null) {
			return;
		} 
		
		for (Derivation subDerivation: d.getSubDerivations()) {
			// 2nd-base case: when subDerivation is a terminal
			if (subDerivation.getE() == null) {
				for (Integer child : d.getE().getChildrenIdsList()) {
					results.add(new Result(h.getVertices(child), 1.0)); 
					// All terminal scores are 1.0
				}
				break;
			}
			// Nominal Case
			getPath(subDerivation, edgesInPath, results, h);
		}
		edgesInPath.add(d.getE().getId());
		results.add(new Result(h.getVertices(d.getE().getParentId()), d.getScore()));
	}
	
	/**
	 * Helper to get a human readable solution
	 * @param results
	 * @return
	 */
	protected String getDebugString(List<Result> results) {
		StringBuilder builder = new StringBuilder();
		for (Result result : results){
			builder.append(result.getVertex().getName());
			builder.append(" ");
			builder.append(result.getScore());
			builder.append(" ");
		}
		builder.append("\n");
		return builder.toString();
	}

}
