package darwInvest.news.utility;

/**
 * The normalizer interface should be implemented by any method
 * of token normalization
 * @author Kevin Dolan
 */
public interface Normalizer {

	/**
	 * Normalize the input string
	 * @param string the string to normalize
	 * @return 		 the normalized version, null if the normalizer
	 * 				 excluding this token
	 */
	public String normalize(String string);
	
}
