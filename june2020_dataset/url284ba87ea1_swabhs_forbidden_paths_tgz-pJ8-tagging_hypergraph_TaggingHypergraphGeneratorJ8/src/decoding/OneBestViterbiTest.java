package decoding;

import hypergraph.BaseTest;
import hypergraph.HypergraphProto.Hyperedge;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import semiring.Derivation;

public class OneBestViterbiTest extends BaseTest {
	
	private OneBestViterbi v;

	public OneBestViterbiTest() {
		super();
		v = new OneBestViterbi();
	}
	
	@Test
	public void testRun() {			
		Derivation actual = v.run(h);
		Decoder decoder = new Decoder();
		System.out.println(decoder.getKBestPaths(Arrays.asList(actual), h));
		/*Hyperedge e = actual.getE();
		
		while (e!= null) {
			int vertexId = e.getChildrenIds(0);
			System.out.println(h.getVertices(vertexId).getName());
			System.out.println(actual.get(vertexId).getScore());
			e = actual.get(vertexId).getE();
		}
		System.out.println("Score of sentence: " + Math.log(actual.get(actual.size() - 1).getScore()));*/
	}

}
