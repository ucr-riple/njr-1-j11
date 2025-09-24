package darwInvest.news.tfidf;

import java.util.List;
import darwInvest.data.NewsEvent;
import darwInvest.news.utility.*;

/**
 * The index builder provides a means of building the inverted index 
 * This was originally written for CS4300, and modified significantly
 * for use with darwInvest
 * 
 * @author Kevin Dolan, Jae Yong Sung
 */
public class IndexBuilder {

	/**
	 * Return the inverted index from the documents
	 * @param news		the array of file URLs to load
	 * @return			the index object
	 */
	public static Index buildIndex(List<NewsEvent> news) {
		Index index = new Index();
		
		Tokenizer tokenizer = new Tokenizer();
		tokenizer.addNormalizer(new LowerCase());
		tokenizer.addNormalizer(new NonAlphabet());
		tokenizer.addNormalizer(new StopWords());
		tokenizer.addNormalizer(new Stemming());
		
		for(NewsEvent event : news) {
			String content = event.getContent();
			
			int id = index.addDocument();
			
			List<String> tokens = tokenizer.tokenize(content);
			for(String token : tokens)
				index.addInstance(id, token);
		}
		
		return index;
	}
}