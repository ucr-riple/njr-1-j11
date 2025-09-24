package query.primitive;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import query.PrimitiveQuery;
import query.Variable;

public class PrimitiveQueryParserTest {

	@Test
	public void test() {
		PrimitiveQuery expected = new XYZ(
				Variable.of("x"), Variable.of("y"), Variable.of("z"));
		
		assertThat(PrimitiveQueryParser.parse("?x,?y,?z"), is(expected));
	}

	@Test
	public void test_trim() {
		PrimitiveQuery expected = new XYZ(
				Variable.of("x"), Variable.of("y"), Variable.of("z"));
		
		assertThat(PrimitiveQueryParser.parse(" ?x  , ?y , ?z "), is(expected));
	}

	

}
