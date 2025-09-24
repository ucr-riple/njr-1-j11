package com.disclinc.netmanager.function.test;

import static org.junit.Assert.assertEquals;
import mLibrary.mContext;
import mLibrary.mFnc;

import org.junit.Test;

public class OrderFunctionTest {

	private mContext m$ = new mContext();

	public OrderFunctionTest() {
		m$.var("b").set(88);
		m$.var("a").set(12);
		m$.var("a", "1").set(6);
		m$.var("a", "1", "amarelo").set(2);
		m$.var("a", "1", "azul").set(4);
		m$.var("a", "1", "branco").set(14);
		m$.var("a", "1", "preto").set(4);
		m$.var("a", "2").set(8);
	}

	@Test
	public void testSearchingVariableValue() {
		assertEquals("Fail on searching local variable value", 88, m$.var("b").get());
		assertEquals("Fail on searching local variable value", 12, m$.var("a").get());
		assertEquals("Fail on searching local variable value", 6, m$.var("a", "1").get());
		assertEquals("Fail on searching local variable value", 2, m$.var("a", "1", "amarelo").get());
		assertEquals("Fail on searching local variable value", 8, m$.var("a", "2").get());
	}

	@Test
	public void testOrderFirstLevel() {
		assertEquals("Fail on first navegation through the first level", "a",
				mFnc.$order(m$.var("")));

		assertEquals("Fail on first navegation through the first level", 1, mFnc.$order(m$.var("a", "")));
		assertEquals("Fail on first navegation through the first level", 2, mFnc.$order(m$.var("a", "1")));
		assertEquals("Fail on first navegation through the first level", 2, mFnc.$order(m$.var("a", "1")));
		assertEquals("Fail on second navegation through the first level", "", mFnc.$order(m$.var("a", "2")));

		assertEquals("Fail on first navegation through the first level", "azul", mFnc.$order(m$.var("a", "1", "amarelo")));
		assertEquals("Fail on first navegation through the first level", "branco", mFnc.$order(m$.var("a", "1", "azul")));
		assertEquals("Fail on first navegation through the first level", "preto", mFnc.$order(m$.var("a", "1", "branco")));
		assertEquals("Fail on first navegation through the first level", "", mFnc.$order(m$.var("a", "1", "preto")));

	}

	@Test
	public void testOrderFirstLevelInverse() {
		assertEquals("Fail on first navegation through the first level", "a", mFnc.$order(m$.var(""), -1));

		assertEquals("Fail on first navegation through the first level", 2, mFnc.$order(m$.var("a", ""), -1));
		assertEquals("Fail on first navegation through the first level", 1, mFnc.$order(m$.var("a", "2"), -1));
		assertEquals("Fail on first navegation through the first level", "", mFnc.$order(m$.var("a", "1"), -1));
	}
	
	@Test
	public void testOrderOfAllPublicVariablesOnMemory(){
		mContext m$ = new mContext();
		m$.var("%x", "1", "2").set(1);
		m$.var("%").set(1);
		m$.var("%g", "2").set(2);
		m$.var("%a", "5", "2").set(4);
		m$.var("e", "6").set(66);
		
		assertEquals("Fail to find first public variable on memory.", "%a", mFnc.$order(m$.var("%")));
		assertEquals("Fail to find first public variable on memory.", "%g", mFnc.$order(m$.var("%a")));
		assertEquals("Fail to find first public variable on memory.", "%x", mFnc.$order(m$.var("%g")));
		assertEquals("Fail to find first public variable on memory.", "e", mFnc.$order(m$.var("%x")));
		assertEquals("Fail to find first public variable on memory.", "", mFnc.$order(m$.var("e")));
		assertEquals("Fail to find first public variable on memory.", "%", mFnc.$order(m$.var("")));
	}
}
