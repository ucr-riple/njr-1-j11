package darwInvest.news.utility;

/**
 * Get rid of all non-alphabets
 * @author Jae Yong Sung, Kevin Dolan
 */
public class NonAlphabet implements Normalizer {

	@Override
	public String normalize(String string) {
		String result = string.replaceAll("[^a-z]", "");
		if(result.equals(""))
			return null;
		else
			return result;
	}

}
