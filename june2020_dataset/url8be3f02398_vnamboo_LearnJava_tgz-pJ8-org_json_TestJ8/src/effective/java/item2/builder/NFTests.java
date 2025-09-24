package effective.java.item2.builder;

import static org.junit.Assert.*;

import org.junit.Test;

public class NFTests {

	@Test
	public void testBuilder(){
		NutritionalFacts cola=new NutritionalFacts.Builder(200,10).calories(10).build();
		assertEquals(10,cola.calories);
	}
}
