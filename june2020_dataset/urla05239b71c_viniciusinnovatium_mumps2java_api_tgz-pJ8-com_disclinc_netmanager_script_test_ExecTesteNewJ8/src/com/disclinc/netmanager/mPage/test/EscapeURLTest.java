package com.disclinc.netmanager.mPage.test;

import static org.junit.Assert.assertEquals;
import mLibrary.mFnc;
import mLibrary.mPage;

import org.junit.Test;

public class EscapeURLTest {
	@Test
	public void UrlStringThreeParam() {

		String expression = "teste=teste&x=y&ação=caçar";
		
		String expected = "teste%3Dteste%26x%3Dy%26a%C3%A7%C3%A3o%3Dca%C3%A7ar";
		mPage mp = new mPage();
		Object actual = mp.EscapeURL(expression);
		
		assertEquals("Fail on UrlStringThreeParam.", expected, actual);
	}
}
