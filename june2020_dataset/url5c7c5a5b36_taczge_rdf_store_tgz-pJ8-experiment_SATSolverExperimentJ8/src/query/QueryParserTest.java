package query;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import query.primitive.XPO;
import query.primitive.XYZ;
import core.Resource;

public class QueryParserTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		String expression = "?x,p,o.?y,?x,?z.";
		Query expected = new Query(
				new XPO(Variable.of("x"), Resource.of("p"), Resource.of("o")),
				new XYZ(Variable.of("y"), Variable.of("x"), Variable.of("z")));
		
		assertThat(QueryParser.parse(expression), is(expected));
	}
	
	@Test
	public void parse_blank() throws Exception {
		String expression = "   ?x, p, o  .  ?y  ,  ?x , ?z  .  ";
		Query expected = new Query(
				new XPO(Variable.of("x"), Resource.of("p"), Resource.of("o")),
				new XYZ(Variable.of("y"), Variable.of("x"), Variable.of("z")));
		
		assertThat(QueryParser.parse(expression), is(expected));
		
	}

}
