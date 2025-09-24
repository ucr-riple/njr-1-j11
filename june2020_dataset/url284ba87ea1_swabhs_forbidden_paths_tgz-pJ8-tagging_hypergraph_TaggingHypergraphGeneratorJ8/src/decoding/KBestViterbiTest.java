package decoding;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import hypergraph.BaseTest;
import hypergraph.HypergraphProto.Vertex;

import org.junit.Test;

import semiring.Derivation;
import semiring.KBestSemiring;
import semiring.KBestSemiringSmart;

public class KBestViterbiTest extends BaseTest {
	
	private KBestViterbi v;
	
	public KBestViterbiTest () {
		v = new KBestViterbi(new KBestSemiring(12));
	}

	@Test
	public void testInitialize() {
		
	}

	@Test
	public void testRun() {
		Map<Integer, List<Derivation>> derSet = v.run(h);
		Decoder decoder = new DiverseDecoder();
		System.out.println(decoder.getKBestPaths(derSet.get(h.getVerticesCount() - 1), h));
	}

}
