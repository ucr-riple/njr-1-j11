/*
 * Created on Jul 7, 2004
 *
 * File: StringUtil.java
 * Release: 17.0 
 * Copyright Notice: Š 2008 Nortel Government Solutions (www.nortelgov.com)
 * 					 All rights reserved.
 * 
 * Author(s): martinc [martinch]
 * 
 * $Revision:   1.4  $
 * $Log:   P:/databases/PostalOne!/archives/PostalOne!/P1_DEV/cfusion.ear/src/CIM/CIMejb/ejbModule/com/usps/postal1/cim/owner/util/StringUtil.java-arc  $
 * 
 *    Rev 1.4   Jul 26 2008 20:19:04   yuj
 * TPR 26079 - Escape PDF/XML meta characters
 * 
 *    Rev 1.2   Apr 14 2008 23:21:12   martinch
 * Dev update for SCRs 23775, 23776; Added methods
 * 
 *    Rev 1.1   Nov 01 2007 17:36:06   martinch
 * Added convenience methods for empty checks and converting a collection into a CSV string
 */
package com.evs.data.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * <p>Miscellaneous collection of utility methods for 
 * <code>java.lang.String</code>.
 * </p> 
 * 
 * @author martinc
 * @version 1.2 - 04/10/2008
 */
public final class StringUtil { // TODO - Refactor: Move to com.usps.postal1.cim.util package or higher

	private StringUtil() {
		// Exists only to defeat instantiation
	}

	/**
	 * Convenience method to return a Collection as a delimited (e.g. CSV) 
	 * String. Useful for toString() implementations.
	 * 
	 * @param col - Collection to convert
	 * @param delimeter - Delimiter to use, defaults to ","
	 * @return <code>String</code>
	 */
	public static String asDelimitedString(Collection col, String delimeter) {
		if (col == null) {
			return null;
		}

		String delim = delimeter;		
		if (delim == null) {
			delim = ",";		
		}

		StringBuffer sb = new StringBuffer();
		Iterator it = col.iterator();
		while (it.hasNext()) {
			sb.append(it.next());
			if (it.hasNext()) {
				sb.append(delim);
			}
		}
		return sb.toString();
	}
	
	/**
	 * Converts a <code>Collection</code> into a string of comma separated 
	 * values (CSV).
	 * 
	 * @param col - The Collection to convert to a CSV string
	 * @return <code>String</code> The CSV string
	 */
	public static String asCSV(Collection col) {
		return asDelimitedString(col, ",");
	}

	// Empty checks
	/**
	 * <p>Checks if a String is empty ("") or null.</p>
	 * 
	 * @param str  - The String to check, may be null
	 * @return <code>true</code> if the String is empty or null, <code>false</code> otherwise
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * <p>Checks if a String is not empty ("") and not null.</p>
	 * 
	 * @param str  - The String to check, may be null
	 * @return <code>true</code> if the String is not empty and not null, <code>false</code> otherwise
	 */
	public static boolean isNotEmpty(String str) {
		return !StringUtil.isEmpty(str);
	}

	/**
	 * Capitalizes the first letter of the given string.
	 * 
	 * @param string
	 * @return <code>String</code> The converted String
	 */
	private String capitalize(String string) {
		return string.length() == 0 ? string : string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
	}
		
	/**
	 * Capitalizes the first letter of each word in the specified string.
	 * 
	 * @param str - The String to convert, may be null
	 * @return <code>String</code> The converted String
	 */
	public static String normalizeCase(String str) {
		
		if (str == null) {
			return null;
		}

		boolean lastWasWhitespace = true;
		char[] buffer = str.toLowerCase().toCharArray();
		StringBuffer sb = new StringBuffer(str.length());
				
		for (int i = 0; i < buffer.length; i++) {
			char c = buffer[i];
			if (Character.isWhitespace(c)) {
				sb.append(c);
				lastWasWhitespace = true;
			} else {
				if (lastWasWhitespace) {
					sb.append(String.valueOf(c).toUpperCase());
				} else {
					sb.append(c);
				}
				lastWasWhitespace = false;
			}
		}

		return sb.toString();
	}

	/**
	 * Escapes all HTML characters in the specified String.
	 * Use this when you are inserting arbitrary text into an HTML document.
	 * 
	 * @see org.apache.struts.util.ResponseUtils.html#filter(java.lang.String)
	 * @param value - The String to escape
	 * @return <code>String</code> The escaped String
	 */
	public static String escapeHTML(String value) {
		
		if (value == null) {
			return null;
		}
		
		char content[] = new char[value.length()];
		value.getChars(0, value.length(), content, 0);
		StringBuffer result = new StringBuffer(content.length + 50);
		
		for (int i = 0; i < content.length; i++) {
			switch (content[i]) {
				case '<' :
					result.append("&lt;");
					break;
				case '>' :
					result.append("&gt;");
					break;
				case '&' :
					result.append("&amp;");
					break;
				case '"' :
					result.append("&quot;");
					break;
				case '\'' :
					result.append("&#39;");
					break;
				default :
					result.append(content[i]);
			}
		}
		
		return result.toString();
	}

	/**
	 * Escapes all PDF XML characters in the specified String.
	 * Use this when you are inserting arbitrary text into an PDF document.
	 */
	public static String escapeXml(String value)
	{
		if( value == null )	return "";
		boolean containsSpecialChar =
				( value.indexOf( '<' ) > -1 )
				|| ( value.indexOf( '>' ) > -1 )
				|| ( value.indexOf( '&' ) > -1 )
				|| ( value.indexOf( '\\' ) > -1 )
				|| ( value.indexOf( '"' ) > -1 )
				|| ( value.indexOf( '\n' ) > -1 )
		;
		if( !containsSpecialChar )	return value;
		char content[] = new char[value.length()];
		value.getChars(0, value.length(), content, 0);
		StringBuffer result = new StringBuffer(content.length + 50);
		for (int i = 0; i < content.length; i++)
		{
			char c = content[i];
			switch ( c )
			{
				case '<' :
					result.append("&lt;");
					break;
				case '>' :
					result.append("&gt;");
					break;
				case '&' :
					result.append("&amp;");
					break;
				case '"' :
					result.append("&quot;");
					break;
				case '\\' :
					result.append("&#x5C;&#x5C;");
					break;
				case '\n' :
					result.append("<br/>");
					break;
				default :
					result.append(c);
			}
		}
		return "<paragraph>"+result.toString()+"</paragraph>";
	}
	
}
