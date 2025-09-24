package semiring;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class KBestSemiringSmartTest {
	
	List<List<Derivation>> derivationsSet;
	KBestSemiringSmart kbest;
	
	public KBestSemiringSmartTest() {
		List<Derivation> d1 = new ArrayList<Derivation>();
		d1.add(new Derivation(null, 10.0, null));
		d1.add(new Derivation(null, 9.0, null));
		d1.add(new Derivation(null, 2.0, null));
		
		List<Derivation> d2 = new ArrayList<Derivation>();
		d2.add(new Derivation(null, 10.0, null));
		d2.add(new Derivation(null, 8.0, null));
		d2.add(new Derivation(null, 7.0, null));
		
		List<Derivation> d3 = new ArrayList<Derivation>();
		d3.add(new Derivation(null, 10.0, null));
		d3.add(new Derivation(null, 6.0, null));
		d3.add(new Derivation(null, 4.0, null));
		
		derivationsSet = new ArrayList<List<Derivation>>();
		derivationsSet.add(d1);
		derivationsSet.add(d2);
		derivationsSet.add(d3);

	}

	@Test
	public void testMultiply() {
		List<Double> expected = Arrays.asList(1000.0, 900.0, 800.0, 720.0, 700.0, 630.0, 600.0,
				540.0, 480.0, 432.0, 420.0, 400.0, 378.0, 360.0, 320.0, 288.0, 280.0, 252.0, 200.0,
				160.0, 140.0, 120.0, 96.0, 84.0, 80.0, 64.0, 56.0);
		
		kbest = new KBestSemiringSmart(29);
		List<Derivation> actualD = kbest.multiply(derivationsSet);
		
		List<Double> actual = new ArrayList<Double>();
		for (int i = 0; i < actualD.size(); i++) {
			actual.add(actualD.get(i).getScore());
		}
		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void testMultiply_WhenOnlyOneSubDerivation() {
		List<Derivation> d4 = new ArrayList<Derivation>();
		d4.add(new Derivation(null, 1.0, null));
		List<List<Derivation>> dSet = new ArrayList<List<Derivation>>();
		dSet.add(d4);
		kbest = new KBestSemiringSmart(3);
		List<Derivation> actualD = kbest.multiply(dSet);
		
		List<Double> actual = new ArrayList<Double>();
		for (int i = 0; i < actualD.size(); i++) {
			actual.add(actualD.get(i).getScore());
		}
		System.out.println(actual);
	}

	@Test
	public void testMultiply_WhenKIsMoreThanNumCandidates() {
		List<Double> expected = Arrays.asList(1000.0, 900.0, 800.0, 720.0, 700.0, 630.0, 600.0,
				540.0, 480.0, 432.0, 420.0, 400.0, 378.0, 360.0, 320.0, 288.0, 280.0, 252.0, 200.0,
				160.0, 140.0, 120.0, 96.0, 84.0, 80.0, 64.0, 56.0);
		
		kbest = new KBestSemiringSmart(39);
		List<Derivation> actualD = kbest.multiply(derivationsSet);
		
		List<Double> actual = new ArrayList<Double>();
		for (int i = 0; i < actualD.size(); i++) {
			actual.add(actualD.get(i).getScore());
		}
		assertTrue(expected.equals(actual));
		
	}
}
