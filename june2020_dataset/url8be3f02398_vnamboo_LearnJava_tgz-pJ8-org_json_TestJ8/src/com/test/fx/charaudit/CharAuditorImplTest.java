package com.test.fx.charaudit;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.test.fx.charaudit.CharAuditor;
import com.test.fx.charaudit.CharAuditorImpl;

public class CharAuditorImplTest {
	String test;
	CharAuditor auditor;
	@Before
	public void setUp(){
		test="dcba";
		auditor=new CharAuditorImpl();
	}
	@Test
	public void testSort(){
		String sorted=auditor.sortChars(test);
		Assert.assertEquals("abcd", sorted);
	}
	
	@Test
	public void testGetFirstNSortedChars(){
		String sorted=auditor.getFirstNSortedChars(test,2);
		Assert.assertEquals("ab", sorted);
	}
	
	@Test
	public void testCharacterCount(){
		test="aaaacccbbvdd";
		Map<Character,Integer> charMap=auditor.getCharCounts(test);
		Assert.assertEquals(5, charMap.size());
		Assert.assertEquals(new Integer(4), charMap.get('a'));
	}
	
	@Test
	public void testOrderPrevalance(){
		test="aaaacccbbvdd";
		String ordered=auditor.getUniqueCharsSortedByPrevalence(test);
		Assert.assertEquals("vbdca", ordered);
	}
}
