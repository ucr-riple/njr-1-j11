package decoding;

import hypergraph.HypergraphProto.Hypergraph;

import java.util.ArrayList;
import java.util.List;

import semiring.Derivation;
import decoding.Decoder.Result;

public class TaggingDecoder extends Decoder {
	
	public List<List<String>> getKBestTagSequences(
			List<Derivation> rootDerivations, Hypergraph h) {
		
		List<List<String>> allTagSequences = new ArrayList<List<String>>();
		for (Derivation kthBest: rootDerivations) {
			List<Result> results = new ArrayList<Result>();
			List<Integer> edgesInPath = new ArrayList<Integer>();
			getPath(kthBest, edgesInPath, results, h);
			allTagSequences.add(getTagSequence(results));
		}
		return allTagSequences;
	}
	
	/**
	 * Returns a single sequence of tags
	 * @param results
	 * @return
	 */
	protected List<String> getTagSequence(List<Result> results) {
		List<String> tags = new ArrayList<String>();
		for (Result result : results){
			tags.add(result.getVertex().getName());
		}		
		return tags;
	}

}
