package com.miguel.sxl;

import java.util.ArrayList;

/**
 * A class containing useful utility functions.
 * 
 * @author Miguel Muscat
 */
public class Utils {

	/**
	 * Escapes all escapable characters in a string.
	 * 
	 * @param str The string to iterate through and escape character
	 * @return A String, similar to the argument but with all appropriate character pairs escaped.
	 * @throws Exception Throws an Exception if an invalid escaped character is found in the string.
	 */
	public static String sanitizeString(String str) throws Exception {
		String newStr = "";
		boolean escape = false;
		
		for( int i = 0; i < str.length(); i++ ) {
			char c = str.charAt(i);
			
			if ( escape ) {
				switch( c ) {
					case '\\': newStr += "\\"; break;
					case '\"': newStr += "\""; break;
					case 'n': newStr += "\n"; break;
					case 'r': newStr += "\r"; break;
					case 'f': newStr += "\f"; break;
					case 'b': newStr += "\b"; break;
					case 't': newStr += "\t"; break;
					default: throw new Exception("Invalid escape character in string.");
				}
				escape = false;
			}
			else if ( c == '\\' ) {
				escape = true;
			} else {
				newStr += c;
			}
		}
		return newStr;
	}
	
	
	
	public static String generateFunctionScopeName(String fnName, ArrayList<SXLParam> params) {
		String scopeName = fnName + "(";
		for( SXLParam param : params ) {
			scopeName += param.type.getType() + ",";
		}
		if ( scopeName.endsWith(",") ) {
			scopeName = scopeName.substring(0, scopeName.length() - 1);
		}
		return scopeName + ")";
	}
	
	public static String generateFunctionName(String fnName, ArrayList<SXLValue> args) {
		String scopeName = fnName + "(";
		for( SXLValue arg : args ) {
			scopeName += arg.getType() + ",";
		}
		if ( scopeName.endsWith(",") ) {
			scopeName = scopeName.substring(0, scopeName.length() - 1);
		}
		return scopeName + ")";
	}
	
}
