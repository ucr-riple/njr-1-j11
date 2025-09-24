package com.test.fx.charaudit;

import java.util.Map;

public interface CharAuditor {
	public String sortChars(String input);

	public Map<Character, Integer> getCharCounts(String input);

	public String getFirstNSortedChars(String input, int topN);

	public String getUniqueCharsSortedByPrevalence(String input);
}
