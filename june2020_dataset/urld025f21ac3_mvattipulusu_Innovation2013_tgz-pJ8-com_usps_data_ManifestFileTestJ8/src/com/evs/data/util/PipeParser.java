package com.evs.data.util;

public class PipeParser {

	private String line;
	private int index=0;
	private int hasMoreTokensIndex=0;
	private boolean pipeEncountered = false;
	
	public PipeParser(String line) {
		this.line = line;
	}
	
	public String nextToken() {
		StringBuffer token = new StringBuffer("");

		if (null == line)
			return "";
		
		for (; index < line.length(); ) {
			if ('|' == line.charAt(index)) {
				index++;
				break;
			}
			token.append(line.charAt(index));
			index++;
		}		
		
		return token.toString();		
	}
	
	public int countTokens() {
		int count=1;
		int countIndex=0;

		for (; countIndex < line.length(); countIndex++) {
			if ('|' == line.charAt(countIndex)) {
				count++;
			}
		}		
		
		return count;
	}
	
}
