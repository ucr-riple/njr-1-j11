package utility;

import static org.junit.Assert.*;

import org.junit.Test;

import semiring.Derivation;

public class MaxPriorityQTest {
	
	private MaxPriorityQ q;
	
	public MaxPriorityQTest() {
		q = new MaxPriorityQ();
		q.insert(new Derivation(null, 3.0, null));
		q.insert(new Derivation(null, 5.9, null));
		q.insert(new Derivation(null, 4.1, null));
		q.insert(new Derivation(null, 0.4, null));
		q.insert(new Derivation(null, 7.8, null));
	}

	@Test
	public void testExtractMax() {
		assertEquals(q.extractMax().getScore(), new Double(7.8));
		assertEquals(q.extractMax().getScore(), new Double(5.9));
		assertEquals(q.extractMax().getScore(), new Double(4.1));
		assertEquals(q.extractMax().getScore(), new Double(3.0));
		assertEquals(q.extractMax().getScore(), new Double(0.4));
		assertNull(q.extractMax());
	}
	
	@Test
	public void testcontains() {
		q = new MaxPriorityQ();
		q.insert(new Derivation(null, 3.0, null));
		assertTrue(q.contains(new Derivation(null, 3.0, null)));
	}

}
