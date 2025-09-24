package decoding;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import hypergraph.BaseTest;
import hypergraph.HypergraphProto.Vertex;

import org.junit.Test;

import semiring.Derivation;

public class Viterbi3Test extends BaseTest {
	
	private Viterbi3 v;
	
	public Viterbi3Test() {
		super();
		v = new Viterbi3(10);
	}

	@Test
	public void testInitialize() {
		
	}

	@Test
	public void testRun() {
		Map<Integer, List<Derivation>> derSet = v.run(h);
		Decoder decoder = new Decoder();
		System.out.println(decoder.getKBestPaths(derSet.get(h.getVerticesCount() - 1), h));
	}

}
