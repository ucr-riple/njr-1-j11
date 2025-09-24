package frs.hotgammon.tests.preGuiTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import frs.hotgammon.common.BoardImpl;
import frs.hotgammon.framework.Color;
import frs.hotgammon.framework.Location;


public class BoardTests {
	private BoardImpl b;

	@Before
	public void setup() {
		b = new BoardImpl(25);
	}



	@Test
	public void shouldBeAbleToPlaceSameColorOnGivenSquare() {
		assertTrue(b.place(Color.BLACK, Location.R1.ordinal()));
		assertTrue(b.place(Color.BLACK, Location.R1.ordinal()));
	}

	@Test
	public void shouldReturnProperCountForGivenSquare() {
		b.place(Color.BLACK, Location.R1.ordinal());
		b.place(Color.BLACK, Location.R1.ordinal());
		assertEquals(2, b.getSquare(Location.R1.ordinal()).occupants);
	}

	@Test
	public void shouldNotBeAbleToRemovePlayerOfWrongColor() {
		b.place(Color.BLACK, Location.R1.ordinal());
		assertFalse(b.remove(Color.RED, Location.R1.ordinal()));
		assertEquals(1, b.getSquare(Location.R1.ordinal()).occupants);
	}

	@Test
	public void shouldBeAbleToRemovePlayerOfCorrectColor() {
		b.place(Color.BLACK, Location.R1.ordinal());
		assertTrue(b.remove(Color.BLACK, Location.R1.ordinal()));
		assertEquals(0, b.getSquare(Location.R1.ordinal()).occupants);
	}

}
