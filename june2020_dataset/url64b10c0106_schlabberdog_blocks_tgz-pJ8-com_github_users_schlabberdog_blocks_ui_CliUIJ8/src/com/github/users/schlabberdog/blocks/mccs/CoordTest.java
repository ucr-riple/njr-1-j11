package com.github.users.schlabberdog.blocks.mccs;

import org.junit.Test;
import static org.junit.Assert.*;

public class CoordTest {

	@Test
	public void testOrderOfArguments() {
		Coord c = new Coord(5,7);
		assertEquals(c.x,5);
		assertEquals(c.y,7);
	}

	@Test
	public void testEquals() {
		//positiver fall
		{
			Coord a = new Coord(3,9);
			Coord b = new Coord(3,9);

			assertTrue(a.equals(b));
		}
		//einfacher negativer fall
		{
			Coord a = new Coord(3,9);
			Coord b = new Coord(9,3);

			assertFalse(a.equals(b));
		}
	}
}
