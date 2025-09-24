package core;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class TriplesTest {
	
	private Triples sut;
	
	private static final Resource s1 = Resource.of("s1");
	private static final Resource p1 = Resource.of("p1");
	private static final Resource o1 = Resource.of("o1");
	private static final Resource s2 = Resource.of("s2");
	private static final Resource p2 = Resource.of("p2");
	private static final Resource o2 = Resource.of("o2");
	
	private static final Resource x = Resource.of("x");
	private static final Resource y = Resource.of("y");

	@Before
	public void setUp() throws Exception {
		sut = new Triples();
	}

	@Test
	public void contains_returnTrue() throws Exception {
		sut.add(s1, p1, o1);
		sut.add(s2, p2, o2);
		
		assertThat(sut.contains(s1, p1, o1), is(true));
	}

	@Test
	public void contains_returnFalse() throws Exception {
		sut.add(s1, p1, o1);
		
		assertThat(sut.contains(s1, p1, o2), is(false));
	}
	
	@Test
	public void contains_returnFalseWhenAnonymousResourceIsGive() {
		sut.add(s1, p1, o1);
		
		assertThat(sut.contains(s2, p2, o2), is(false));
	}
	
	@Test
	public void listXPO_() throws Exception {
		sut.add(s1, p1, o1);
		sut.add(s2, p1, o1);
		sut.add(s2, p2, o2);
		
		Collection<Triple> expected = new HashSet<>();
		expected.add( new Triple(s1, p1, o1) );
		expected.add( new Triple(s2, p1, o1) );
		
		assertThat(sut.listXPO(p1, o1), is(expected));
	}
	
	@Test
	public void listSXO_() throws Exception {
		sut.add(s1, p1, o1);
		sut.add(s1, p2, o1);
		sut.add(s2, p2, o2);
		
		Collection<Triple> expected = new HashSet<>();
		expected.add( new Triple(s1, p1, o1) );
		expected.add( new Triple(s1, p2, o1) );
		
		assertThat(sut.listSXO(s1, o1), is(expected));
	}

	@Test
	public void listSPX_() throws Exception {
		sut.add(s1, p1, o1);
		sut.add(s1, p1, o2);
		sut.add(s2, p2, o2);
		
		Collection<Triple> expected = new HashSet<>();
		expected.add( new Triple(s1, p1, o1) );
		expected.add( new Triple(s1, p1, o2) );
		
		assertThat(sut.listSPX(s1, p1), is(expected));
	}
	
	@Test
	public void listSXY() throws Exception {
		sut.add(s1, p1, o1);
		sut.add(s1, p2, o2);
		sut.add(s2, p2, o2);
		
		Collection<Triple> expected = new HashSet<>();
		expected.add( new Triple(s1, p1, o1) );
		expected.add( new Triple(s1, p2, o2) );
		
		assertThat(sut.listSXY(s1), is(expected));
	}

	@Test
	public void listXPY() throws Exception {
		sut.add(s1, p1, o1);
		sut.add(s2, p1, o2);
		sut.add(s2, p2, o2);
		
		Collection<Triple> expected = new HashSet<>();
		expected.add( new Triple(s1, p1, o1) );
		expected.add( new Triple(s2, p1, o2) );
		
		assertThat(sut.listXPY(p1), is(expected));
	}

	@Test
	public void listXYO() throws Exception {
		sut.add(s1, p1, o1);
		sut.add(s2, p2, o1);
		sut.add(s2, p2, o2);
		
		Collection<Triple> expected = new HashSet<>();
		expected.add( new Triple(s1, p1, o1) );
		expected.add( new Triple(s2, p2, o1) );
		
		assertThat(sut.listXYO(o1), is(expected));
	}

	@Test
	public void listSXX() throws Exception {
		sut.add(s1, x, x);
		sut.add(s1, y, y);
		sut.add(s1, x, y);
		sut.add(s2, x, x);
		
		Collection<Triple> expected = new HashSet<>();
		expected.add( new Triple(s1, x, x) );
		expected.add( new Triple(s1, y, y) );
		
		assertThat(sut.listSXX(s1), is(expected));
	}

	@Test
	public void listXPX() throws Exception {
		sut.add(x, p1, x);
		sut.add(y, p1, y);
		sut.add(x, p1, y);
		sut.add(x, p2, x);
		
		Collection<Triple> expected = new HashSet<>();
		expected.add( new Triple(x, p1, x) );
		expected.add( new Triple(y, p1, y) );
		
		assertThat(sut.listXPX(p1), is(expected));
	}

	@Test
	public void listXXO() throws Exception {
		sut.add(x, x, o1);
		sut.add(y, y, o1);
		sut.add(x, y, o1);
		sut.add(x, x, o2);
		
		Collection<Triple> expected = new HashSet<>();
		expected.add( new Triple(x, x, o1) );
		expected.add( new Triple(y, y, o1) );
		
		assertThat(sut.listXXO(o1), is(expected));
	}
	
}
