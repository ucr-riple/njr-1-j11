package logic;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class OrTest {
	
	private static final Proposition p = new Atom("p");
	private static final Proposition q = new Atom("q");

	@Test
	public void simplify_() {
		Proposition sut = new Or(new And(p, p), new And(q, q)).simplify();
		Proposition expected = new Or(p, q);

		assertThat(sut.simplify(), is(expected)); 
	}

	@Test
	public void simplify_nest() {
		Proposition sut = new Or(new And(p, p), new And(p, p)).simplify();
		Proposition expected = p;

		assertThat(sut.simplify(), is(expected)); 
	}
	
}
