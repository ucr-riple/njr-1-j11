package learning;

import java.util.List;

/**
 * A single data point for learning
 * @author swabha
 *
 */
public class LearningExample {

	private List<String> tokens;
	
	//private Hypergraph sentenceGraph;
	
	private List<String> tagSequence;

	public LearningExample(
			List<String> tokens, List<String> tagSequence) {
			//List<String> tokens, Hypergraph sentenceGraph, List<String> tagSequence) {
		this.tokens = tokens;
		//this.sentenceGraph = sentenceGraph;
		this.tagSequence = tagSequence;
	}
	
	/*public Hypergraph getSentenceGraph() {
		return sentenceGraph;
	}*/
	
	public List<String> getTokens() {
		return tokens;
	}

	public List<String> getTagSequence() {
		return tagSequence;
	}
}
