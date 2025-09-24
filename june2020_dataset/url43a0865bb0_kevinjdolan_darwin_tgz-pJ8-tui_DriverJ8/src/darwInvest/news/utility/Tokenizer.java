package darwInvest.news.utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Split a string into many tokens and apply normalizations
 * @author Kevin Dolan
 */
public class Tokenizer {

	private String delimiter;
	private List<Normalizer> normalizers;
	
	/**
	 * Instantiate a default tokenizer, one that uses the delimiter
	 * of spaces and newlines
	 */
	public Tokenizer() {
		delimiter = "[ \n\r]+";
		normalizers = new ArrayList<Normalizer>();
	}
	
	/**
	 * Instantiate a tokenizer with a custom delimiter
	 * @param delimiter the regular expression to use
	 */
	public Tokenizer(String delimiter) {
		this.delimiter = delimiter;
		normalizers = new ArrayList<Normalizer>();
	}
	
	/**
	 * Add a normalizer to the list of normalizers used
	 * @param normalizer the normalizer to add
	 */
	public void addNormalizer(Normalizer normalizer) {
		normalizers.add(normalizer);
	}
	
	/**
	 * Tokenize and normalize the input string
	 * @param input the string to tokenize
	 * @return		a list of tokens
	 */
	public List<String> tokenize(String input) {
		input = input.replaceAll("<[^<>]*>","");
		String[] rawTokens = input.split(delimiter);
		List<String> tokens = new ArrayList<String>();
		for(String token : rawTokens) {
			for(Normalizer normalizer : normalizers) {
				token = normalizer.normalize(token);
				if(token == null)
					break;
			}
			if(token != null)
				tokens.add(token);
		}
		return tokens;
	}
	
}