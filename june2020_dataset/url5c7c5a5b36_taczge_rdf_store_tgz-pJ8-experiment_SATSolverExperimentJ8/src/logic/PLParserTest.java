package logic;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class PLParserTest {
	
	private static final Proposition p = new Atom("p");
	
	@Test
	public void parse_atom() throws Exception {
		assertThat(PLParser.parse("p"), is(p));
	}
	
	@Test
	public void parse_not() throws Exception {
		Proposition expected = new Not(p);
		
		assertThat(PLParser.parse("(not p)"), is(expected));
	}

	@Test
	public void parse_and() {
		Proposition expected = new And(p, p);
		
		assertThat(PLParser.parse("(and p p)"), is(expected));
	}

	@Test
	public void parse_or() {
		Proposition expected = new Or(p, p);
		
		assertThat(PLParser.parse("(or p p)"), is(expected));
	}
	
	@Test
	public void parse_1() throws Exception {
		String exp = "(and (not p) (not p))";
		Proposition expected = new And( new Not(p), new Not(p));
		
		assertThat(PLParser.parse(exp), is(expected));
	}
	
	@Test
	public void parse_2() throws Exception {
		String exp = "(and (not p) (or p (and p (not p)))";
		Proposition expected = new And( new Not(p), new Or(p, new And(p, new Not(p))) );
		
		assertThat(PLParser.parse(exp), is(expected));
	}

	@Test(expected = IllegalArgumentException.class)
	public void err_1() {
		PLParser.parse("(p)");
	}
	
	@Test
	public void err_2() throws Exception {
		String exp = "(and (not p) (or p (and p (not p";
		Proposition expected = new And( new Not(p), new Or(p, new And(p, new Not(p))) );
		
		assertThat(PLParser.parse(exp), is(expected));
	}

}
