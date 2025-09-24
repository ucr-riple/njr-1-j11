package query;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import query.primitive.SXY;
import query.primitive.XYO;
import core.Resource;
import core.Triple;

public class QueryTest {
	
	private static final Resource a = Resource.of("a");
	private static final Resource b = Resource.of("b");
	private static final Resource c = Resource.of("c");
	
	private static final Variable x = Variable.of("x");
	private static final Variable y = Variable.of("y");

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void apply_() throws Exception {
		Query sut = new Query(new XYO(x, y, c), new SXY(c, y, x));
		
		Substitution s = new Substitution();
		s.put(x, a);
		s.put(y, b);

		Substitution t = new Substitution();
		t.put(x, b);
		t.put(y, c);
		
		Resolution r = Resolution.of(s, t);
		
		Set<Triple> expected = new HashSet<>();
		expected.add(new Triple(a, b, c));
		expected.add(new Triple(c, b, a));
		expected.add(new Triple(b, c, c));
		expected.add(new Triple(c, c, b));
				
		assertThat(sut.apply(r), is(expected));
	}

}




