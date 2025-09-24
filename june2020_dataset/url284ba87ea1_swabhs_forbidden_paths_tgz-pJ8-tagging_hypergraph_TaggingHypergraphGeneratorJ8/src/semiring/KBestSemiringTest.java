package semiring;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * TODO(swabha): Add tests for smaller lists, when sum of lists might not add to k and all that
 * @author swabha
 *
 */
public class KBestSemiringTest {

	List<Derivation> d1;
	List<Derivation> d2;
	List<Derivation> d3;
	
	private KBestSemiring kbest;
	
	public KBestSemiringTest() {
		super();
		d1 = new ArrayList<Derivation>();
		d1.add(new Derivation(null, 0.6, null));
		d1.add(new Derivation(null, 0.5, null));
		d1.add(new Derivation(null, 0.1, null));
		
		d2 = new ArrayList<Derivation>();
		d2.add(new Derivation(null, 0.9, null));
		d2.add(new Derivation(null, 0.4, null));
		d2.add(new Derivation(null, 0.0, null));
		
		d3 = new ArrayList<Derivation>();
		d3.add(new Derivation(null, 0.7, null));
		d3.add(new Derivation(null, 0.6, null));
		d3.add(new Derivation(null, 0.1, null));
		
		kbest = new KBestSemiring(3);
	}
	
	@Test
	public void testAdd() {
		List<Double> expectedScores = Arrays.asList(0.9, 0.6, 0.5);
		List<Derivation> actual = kbest.add(d1, d2);
		
		List<Double> actualScores = new ArrayList<Double>();
		for (Derivation d : actual) {
			actualScores.add(d.getScore());
		}
		assertTrue(expectedScores.equals(actualScores));
	}
	
	@Test
	public void testMultiply() {
		List<Double> expectedScores = Arrays.asList(0.378, 0.324, 0.315);;
		
		List<List<Derivation>> derivations =  new ArrayList<List<Derivation>>();
		derivations.add(d1);
		derivations.add(d2);
		derivations.add(d3);
		List<Derivation> actual = (kbest.multiply(derivations));
		List<Double> actualScores = new ArrayList<Double>();
		for (Derivation d : actual) {
			actualScores.add(d.getScore());
		}
		assertTrue(expectedScores.equals(actualScores));
	}
}
