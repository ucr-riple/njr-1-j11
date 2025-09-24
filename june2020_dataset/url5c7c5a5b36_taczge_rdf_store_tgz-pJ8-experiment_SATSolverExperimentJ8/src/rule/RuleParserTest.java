package rule;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import query.Query;
import query.Variable;
import query.primitive.XPY;
import query.primitive.XYZ;
import core.Resource;

public class RuleParserTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		String exp = "   ?p , dom , ?c  .  ?s , ?p  , ?o . =>  ?s, tp, ?c.    ";
		
		Query head = new Query( 
				new XPY(Variable.of("p"), Resource.of("dom"), Variable.of("c")),
				new XYZ(Variable.of("s"), Variable.of("p"), Variable.of("o")));
		Query body = new Query(
				new XPY(Variable.of("s"), Resource.of("tp"), Variable.of("c")));
						
		Rule expected = new Rule(head, body);
		
		assertThat(RuleParser.parse(exp), is(expected));
	}

}
