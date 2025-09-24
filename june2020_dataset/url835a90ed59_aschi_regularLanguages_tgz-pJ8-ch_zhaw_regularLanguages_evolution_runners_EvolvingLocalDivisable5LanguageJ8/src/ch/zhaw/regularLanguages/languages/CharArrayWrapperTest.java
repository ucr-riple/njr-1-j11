package ch.zhaw.regularLanguages.languages;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.zhaw.regularLanguages.language.CharArrayWrapper;

public class CharArrayWrapperTest {
	char[] testDataEmpty1 = {};
	char[] testDataEmpty2 = {};
	char[] testData1 = {'a','b','c'};
	char[] testData1_1 = {'a','b','c'};
	
	char[] testData2 = {'c','b','a'};
	
	
	
	CharArrayWrapper caw1 = new CharArrayWrapper(testData1);
	CharArrayWrapper caw2 = new CharArrayWrapper(testData2);
	CharArrayWrapper caw1_1 = new CharArrayWrapper(testData1_1);
	
	CharArrayWrapper cawE1 = new CharArrayWrapper(testDataEmpty1);
	CharArrayWrapper cawE2 = new CharArrayWrapper(testDataEmpty2);
	
	@Test
	public void testGetData() {
		assertTrue(caw1.getData().equals(testData1));
	}

	@Test
	public void testEqualsObject1() {
		assertTrue(caw1.equals(caw1_1));
	}
	
	@Test
	public void testEqualsEmpty(){
		assertTrue(cawE1.equals(cawE2));
	}
	@Test
	public void testEqualsEmptyNotEmpty(){
		assertTrue(!cawE1.equals(caw1));
	}
	
	@Test
	public void testEqualsObject2(){
		assertTrue(!caw1.equals(caw2));
	}
	
	@Test
	public void testToString() {
		assertTrue(caw1.toString().equals("[a,b,c]"));
	}

}
