package tagging_hypergraph;

import static org.junit.Assert.*;

import hypergraph.HypergraphProto.Hyperedge;
import hypergraph.HypergraphProto.Hypergraph;
import hypergraph.HypergraphProto.Vertex;

import java.io.File;
import java.util.List;
import java.util.Map;

import learning.InputReader;
import learning.LearningExample;

import org.junit.Test;

public class TaggingHypergraphGeneratorTest {
	
	private Hypergraph hypergraph;
	
	public TaggingHypergraphGeneratorTest() {
		Map<String, Double> initialWeights = InputReader.readWeights(new File("data/tag.model"));
		List<LearningExample> examples = InputReader.readExample(
				new File("data/gene.train"), initialWeights);
		
		LearningExample example = examples.get(0);
		this.hypergraph = 
				TaggingHypergraphGenerator.buildTaggingHypergraph(example.getTokens(), initialWeights);
		
		for (String token : example.getTokens()) {
			System.out.print("\"" + token + "\", ");
		}
		System.out.println(example.getTokens() + " " + example.getTagSequence());
		
	}

	@Test
	public void testBuildTaggingHypergraph_testVertices() {
		// Testing vertices
		for (Hyperedge edge : hypergraph.getEdgesList()) {
			System.out.println("Edge id: " + edge.getId() + " Parent: " + edge.getParentId());
			Vertex parent = hypergraph.getVertices(edge.getParentId());
			if (! parent.getInEdgeList().contains(edge.getId())) {
				System.out.println("Edge " + edge.getId() + " is screwed up! - parent does not contain it in incoming");
			} 
			List<Integer> childrenIds = edge.getChildrenIdsList();
			for (int childId : childrenIds) {
				Vertex child = hypergraph.getVertices(childId);
				if (! child.getOutEdgeList().contains(edge.getId())) {
					System.out.println(edge.getId() + " is screwed up! - children do not contain it in outgoing");
				}
			}
			System.out.println();
		}
	}
	
	@Test
	public void testBuildTaggingHypergraph_testEdges() {
		for (Vertex vertex : hypergraph.getVerticesList()) {
			List<Integer> inEdges = vertex.getInEdgeList();
			for (int inEdgeId : inEdges) {
				Hyperedge inEdge = hypergraph.getEdges(inEdgeId);
				if (inEdge.getParentId() != vertex.getId()) {
					System.out.println(vertex.getId() + " is screwed up!");
				}
			}
			List<Integer> outEdgeIds = vertex.getOutEdgeList();
			for (int outEdgeId : outEdgeIds) {
				Hyperedge outEdge = hypergraph.getEdges(outEdgeId);
				if (! outEdge.getChildrenIdsList().contains(vertex.getId())) {
					System.out.println(vertex.getId() + " is screwed up!");
				}
			}
		}
	}

}
