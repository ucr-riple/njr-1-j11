package logic;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class NotTest {
	
	private static final Proposition p = new Atom("p");

	@Test
	public void simplify_() {
		Proposition sut = new Not( new Or(p, p) );
		Proposition expected = new Not( p );
		
		assertThat(sut.simplify(), is(expected));
	}
	
	@Test
	public void simplify_nest() {
		Proposition sut = new Not( new Not( new Or(p, p)) );
		
		assertThat(sut.simplify(), is(p));
	}

}
