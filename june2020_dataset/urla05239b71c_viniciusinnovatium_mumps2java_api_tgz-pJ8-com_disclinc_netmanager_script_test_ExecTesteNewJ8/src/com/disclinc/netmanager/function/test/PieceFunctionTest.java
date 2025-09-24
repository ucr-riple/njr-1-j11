package com.disclinc.netmanager.function.test;

import static mLibrary.mFnc.$piece;
import static mLibrary.mFnc.$setpiece;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PieceFunctionTest {

	private final Object DELIMITER = "%--%";
	private final Object STRING_TEST = "InterSystems" + DELIMITER + "One Memorial Drive"
			+ DELIMITER + "Cambridge" + DELIMITER + "MA 02142";
	private final Object to = 2;
	private final Object from = 4;


	@Test
	public void testPieceDefault() {
		assertEquals("Fail to function split default", "InterSystems",
				$piece(STRING_TEST, DELIMITER));
	}

	@Test
	public void testPieceOneElement() {
		assertEquals("Fail to function split one element",
				"One Memorial Drive", $piece(STRING_TEST, DELIMITER, to));
	}

	@Test
	public void testPieceIntervalElements() {
		
		
		assertEquals("Fail to function split interval elements",
				"One Memorial Drive" + DELIMITER + "Cambridge" + DELIMITER
						+ "MA 02142",
				$piece(STRING_TEST, DELIMITER, to, from));
	}

	@Test
	public void testPieceConcatenation() {
		assertEquals("Fail to function split elements on concatanation", "MA",
				$piece($piece(STRING_TEST, DELIMITER, from), " "));
	}
	
	@Test
	public void testSettingValueToPiecePosition() {
		String string = "InterSystems" + DELIMITER + "NOVA STRING AQUI"
				+ DELIMITER + "Cambridge" + DELIMITER + "MA 02142";
		
		assertEquals("Fail on setting a new value to a elements of the string pieced", string,
				$setpiece(STRING_TEST, DELIMITER, to, "NOVA STRING AQUI"));
	}
	
	@Test
	public void testSettingValueToNullPiecePosition() {
		assertEquals("Fail on setting a new value to a null string", DELIMITER+"NOVA STRING AQUI", $setpiece(null, DELIMITER, to, "NOVA STRING AQUI"));
	}
	
	@Test
	public void testSettingNullValueToPiecePosition() {
		String string = "InterSystems" + DELIMITER + DELIMITER + "Cambridge" + DELIMITER + "MA 02142";
		
		assertEquals("Fail on setting a new value to a null string", string, $setpiece(STRING_TEST, DELIMITER, to, null));
	}
}


