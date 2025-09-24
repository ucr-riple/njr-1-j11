/*
 ******************************************************************
Copyright (c) 2001, Jeff Martin, Tim Bacon
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

 * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above
      copyright notice, this list of conditions and the following
      disclaimer in the documentation and/or other materials provided
      with the distribution.
 * Neither the name of the xmlunit.sourceforge.net nor the names
      of its contributors may be used to endorse or promote products
      derived from this software without specific prior written
      permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.

 ******************************************************************
 */

package xpathParser.test;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLTestCase;

/**
 * Example XMLUnit XMLTestCase code Demonstrates use of:<br />
 * <ul>
 * <li>XMLTestCase: assertXMLEqual(), assertXMLNotEqual(), assertXpathExists(),
 * assertXpathNotExists(), assertXpathEvaluatesTo(), assertXpathsEqual(),
 * assertXpathsNotEqual(), assertNodeTestPasses()</li>
 * <li>Diff: similar(), identical()</li>
 * <li>DetailedDiff: getAllDifferences()</li>
 * <li>DifferenceListener: use with Diff class,
 * IgnoreTextAndAttributeValuesDifferenceListener implementation</li>
 * <li>ElementQualifier: use with Diff class, ElementNameAndTextQualifier
 * implementation</li>
 * <li>Transform: constructors, getResultDocument(), use with Diff class</li>
 * <li>Validator: constructor, isValid()</li>
 * <li>TolerantSaxDocumentBuilder and HTMLDocumentBuilder usage</li>
 * <li>NodeTest: CountingNodeTester and custom implementations</li>
 * <li>XMLUnit static methods: buildDocument(), buildControlDocument(),
 * buildTestDocument(), setIgnoreWhitespace()</li>
 * </ul>
 * <br />
 * Examples and more at <a
 * href="http://xmlunit.sourceforge.net"/>xmlunit.sourceforge.net</a>
 */
public class XMLTest extends XMLTestCase {
	private DocumentBuilder dBuilder;

	public XMLTest(String name) throws ParserConfigurationException {
		super(name);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(false);
		this.dBuilder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();

		// Document document = dBuilder.parse(docFile);
	}

 

	public void testA1Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_A1.out";
		String myTestXML = "test/FT_alphabet_A1.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}
	




	public void testA2Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_A2.out";
		String myTestXML = "test/FT_alphabet_A2.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testA3Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_A3.out";
		String myTestXML = "test/FT_alphabet_A3.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testA4Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_A4.out";
		String myTestXML = "test/FT_alphabet_A4.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testA5Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_A5.out";
		String myTestXML = "test/FT_alphabet_A5.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testA6Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_A6.out";
		String myTestXML = "test/FT_alphabet_A6.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testA7Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_A7.out";
		String myTestXML = "test/FT_alphabet_A7.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testA8Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_A8.out";
		String myTestXML = "test/FT_alphabet_A8.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testA9Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_A9.out";
		String myTestXML = "test/FT_alphabet_A9.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testA10Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_A10.out";
		String myTestXML = "test/FT_alphabet_A10.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testA11Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_A11.out";
		String myTestXML = "test/FT_alphabet_A11.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testA12Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_A12.out";
		String myTestXML = "test/FT_alphabet_A12.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testP1Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_P1.out";
		String myTestXML = "test/FT_alphabet_P1.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testP2Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_P2.out";
		String myTestXML = "test/FT_alphabet_P2.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testP3Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_P3.out";
		String myTestXML = "test/FT_alphabet_P3.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testP4Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_P4.out";
		String myTestXML = "test/FT_alphabet_P4.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testP5Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_P5.out";
		String myTestXML = "test/FT_alphabet_P5.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testP6Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_P6.out";
		String myTestXML = "test/FT_alphabet_P6.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testP7Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_P7.out";
		String myTestXML = "test/FT_alphabet_P7.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testP8Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_P8.out";
		String myTestXML = "test/FT_alphabet_P8.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testP9Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_P9.out";
		String myTestXML = "test/FT_alphabet_P9.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testP10Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_P10.out";
		String myTestXML = "test/FT_alphabet_P10.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testP11Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_P11.out";
		String myTestXML = "test/FT_alphabet_P11.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testP12Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_P12.out";
		String myTestXML = "test/FT_alphabet_P12.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testT1Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_T1.out";
		String myTestXML = "test/FT_alphabet_T1.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testT2Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_T2.out";
		String myTestXML = "test/FT_alphabet_T2.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testT3Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_T3.out";
		String myTestXML = "test/FT_alphabet_T3.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testT4Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_T4.out";
		String myTestXML = "test/FT_alphabet_T4.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testT5Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_T5.out";
		String myTestXML = "test/FT_alphabet_T5.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testT6Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_T6.out";
		String myTestXML = "test/FT_alphabet_T6.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testT7Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_T7.out";
		String myTestXML = "test/FT_alphabet_T7.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testO1Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_O1.out";
		String myTestXML = "test/FT_alphabet_O1.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testO2Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_O2.out";
		String myTestXML = "test/FT_alphabet_O2.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testO3Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_O3.out";
		String myTestXML = "test/FT_alphabet_O3.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testO4Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_O4.out";
		String myTestXML = "test/FT_alphabet_O4.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testO5Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_O5.out";
		String myTestXML = "test/FT_alphabet_O5.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testO6Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_O6.out";
		String myTestXML = "test/FT_alphabet_O6.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testO7Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_O7.out";
		String myTestXML = "test/FT_alphabet_O7.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testO8Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_O8.out";
		String myTestXML = "test/FT_alphabet_O8.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testO9Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_O9.out";
		String myTestXML = "test/FT_alphabet_O9.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF1Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F1.out";
		String myTestXML = "test/FT_alphabet_F1.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF2Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F2.out";
		String myTestXML = "test/FT_alphabet_F2.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF3Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F3.out";
		String myTestXML = "test/FT_alphabet_F3.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF4Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F4.out";
		String myTestXML = "test/FT_alphabet_F4.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF5Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F5.out";
		String myTestXML = "test/FT_alphabet_F5.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF6Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F6.out";
		String myTestXML = "test/FT_alphabet_F6.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF7Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F7.out";
		String myTestXML = "test/FT_alphabet_F7.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF8Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F8.out";
		String myTestXML = "test/FT_alphabet_F8.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF9Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F9.out";
		String myTestXML = "test/FT_alphabet_F9.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF10Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F10.out";
		String myTestXML = "test/FT_alphabet_F10.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF11Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F11.out";
		String myTestXML = "test/FT_alphabet_F11.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF12Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F12.out";
		String myTestXML = "test/FT_alphabet_F12.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF13Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F13.out";
		String myTestXML = "test/FT_alphabet_F13.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF14Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F14.out";
		String myTestXML = "test/FT_alphabet_F14.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF15Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F15.out";
		String myTestXML = "test/FT_alphabet_F15.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF16Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F16.out";
		String myTestXML = "test/FT_alphabet_F16.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF17Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F17.out";
		String myTestXML = "test/FT_alphabet_F17.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF18Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F18.out";
		String myTestXML = "test/FT_alphabet_F18.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF19Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F19.out";
		String myTestXML = "test/FT_alphabet_F19.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF21Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F21.out";
		String myTestXML = "test/FT_alphabet_F21.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF22Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F22.out";
		String myTestXML = "test/FT_alphabet_F22.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF23Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F23.out";
		String myTestXML = "test/FT_alphabet_F23.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF24Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F24.out";
		String myTestXML = "test/FT_alphabet_F24.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF25Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F25.out";
		String myTestXML = "test/FT_alphabet_F25.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}

	public void testF26Identical() throws Exception {
		String myControlXML = "control/FT_alphabet_F26.out";
		String myTestXML = "test/FT_alphabet_F26.out";
		Diff myDiff = new Diff(dBuilder.parse(myControlXML),
				dBuilder.parse(myTestXML));
		assertTrue("pieces of XML are similar " + myDiff, myDiff.similar());
		assertTrue("but are they identical? " + myDiff, myDiff.identical());
	}


}