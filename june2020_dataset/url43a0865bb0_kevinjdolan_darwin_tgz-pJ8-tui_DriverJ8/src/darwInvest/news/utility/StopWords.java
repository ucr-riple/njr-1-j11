package darwInvest.news.utility;

import java.util.HashSet;

/**
 * Get rid of all unnecessary words
 * @author Jae Yong Sung
 */
public class StopWords implements Normalizer {
	private String[] stopWords = {"is","are","am","the","a","an","he","she","i","they",
			"that","in","and","or","to","of","be","with","by","who",
			"about","than","then","from","it","as","on","at","be",
			"by","for","from","has","was","were","will","which"};
	
	private HashSet<String> hs = new HashSet<String>();
	
	@Override
	public String normalize(String string) {
		if(hs.contains(string))
			return null;
		else
			return string;
	}
	
	public StopWords() {
		for(String string:stopWords)
			hs.add(string);
	}

}
