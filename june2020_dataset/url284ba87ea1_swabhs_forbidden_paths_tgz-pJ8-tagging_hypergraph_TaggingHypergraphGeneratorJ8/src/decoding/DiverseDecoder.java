package decoding;

import hypergraph.HypergraphProto.Hypergraph;

import java.util.ArrayList;
import java.util.List;

import semiring.Derivation;


public class DiverseDecoder extends Decoder {

	private List<List<Integer>> allowedPaths;
	
	
	public DiverseDecoder() {
		allowedPaths = new ArrayList<List<Integer>>();
	}
	@Override
	public String getKBestPaths(List<Derivation> rootDerivations, Hypergraph h) {
		
		String debugString = "";
		for (Derivation kthBest: rootDerivations) {
			//System.out.println(allowedPaths.size());
			List<Result> results = new ArrayList<Result>();
			List<Integer> edgesInPath = new ArrayList<Integer>();
			getPath(kthBest, edgesInPath, results, h);
			if (isADiversePath(edgesInPath)) {
				System.out.println(rootDerivations.indexOf(kthBest));
				debugString += getDebugString(results);
			}
		}
		return debugString;
	}
	
	/*private boolean isADiversePath(List<Result> results) {
		List<Integer> path = getVertexIds(results);
		// For the best hyperpath, which is always allowed
		if (allowedPaths.size() == 0) {
			allowedPaths.add(path);
			System.out.println("Edges in path: " + path.size());
			return true;
		}
		
		// Check for more than 2 edge overlaps
		for (List<Integer> allowedPath : allowedPaths) {
			List<Integer> pathCopy = new ArrayList<Integer>();
			pathCopy.addAll(path);
			pathCopy.removeAll(allowedPath);
			
			if (pathCopy.size() <= 2) { // Different by only one edge
				return false;
			}
		}
		allowedPaths.add(path);
		return true;
	}*/
	
	private List<Integer> getVertexIds(List<Result> results) {
		List<Integer> vertices = new ArrayList<Integer>();
		for (Result result: results) {
			vertices.add(result.getVertex().getId());
		}
		return vertices;
	}
	
	private boolean isADiversePath(List<Integer> path) {
		// For the best hyperpath, which is always allowed
		if (allowedPaths.size() == 0) {
			allowedPaths.add(path);
			System.out.println("Edges in path: " + path.size());
			return true;
		}
		
		// Check for more than 2 edge overlaps
		for (List<Integer> allowedPath : allowedPaths) {
			List<Integer> pathCopy = new ArrayList<Integer>();
			pathCopy.addAll(path);
			pathCopy.removeAll(allowedPath);
			
			if (pathCopy.size() <= 2) { // Different by only one edge
				return false;
			}
		}
		allowedPaths.add(path);
		return true;
	}
	
}
