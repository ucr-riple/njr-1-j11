package logic;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class AndTest {
	
	private static final Proposition p = new Atom("p");
	private static final Proposition q = new Atom("q");

	@Test
	public void simplify_() {
		Proposition sut = new And(new Or(p, p), new Or(q, q)).simplify();
		Proposition expected = new And(p, q);

		assertThat(sut.simplify(), is(expected)); 
	}

	@Test
	public void simplify_nest() {
		Proposition sut = new And(new Or(p, p), new Or(p, p)).simplify();
		Proposition expected = p;

		assertThat(sut.simplify(), is(expected)); 
	}

}
