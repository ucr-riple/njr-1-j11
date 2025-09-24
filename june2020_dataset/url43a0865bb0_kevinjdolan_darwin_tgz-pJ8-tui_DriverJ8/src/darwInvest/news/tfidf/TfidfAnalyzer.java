package darwInvest.news.tfidf;

import java.util.List;

import org.ujmp.core.*;
import darwInvest.data.NewsEvent;
import darwInvest.data.Ticker;
import darwInvest.news.NewsAnalyzer;
import darwInvest.news.utility.*;

/**
 * The tf.idf analyzer takes the inverted index from the
 * tf.idf trainer and computes a term frequency / inverse
 * document frequency matrix to use to create a weighted
 * average of document similarity
 * 
 * @author Kevin Dolan, Jae Yong Sung
 */
public class TfidfAnalyzer implements NewsAnalyzer{

	private Ticker ticker;
	private List<NewsEvent> news;
	private Index index;
	private Matrix matrix;
	private Tokenizer tokenizer;
	
	public TfidfAnalyzer(Ticker ticker, List<NewsEvent> news, Index index) {
		this.ticker = ticker;
		this.news = news;
		this.index = index;
		matrix = index.getDocumentIncidenceMatrix();
		
		tokenizer = new Tokenizer();
		tokenizer.addNormalizer(new LowerCase());
		tokenizer.addNormalizer(new NonAlphabet());
		tokenizer.addNormalizer(new StopWords());
		tokenizer.addNormalizer(new Stemming());
	}
	
	@Override
	public double score(NewsEvent event, long forecast) {
		String content = event.getContent();
		
		Matrix pdv = index.getPseudoDocumentVector(tokenizer.tokenize(content));
		Matrix similarity = matrix.mtimes(pdv);
		
		Matrix delta = MatrixFactory.dense(1, news.size());
		for(int i = 0; i < news.size(); i++) {
			NewsEvent comp = news.get(i);
			double price = ticker.getPrice(comp.getDate());
			double nextPrice = ticker.getPrice(comp.getDate() + forecast);
			double change = nextPrice - price;
			delta.setAsDouble(change, 0, i);
		}
		
		double sum = similarity.getValueSum();
		Matrix dot = delta.mtimes(similarity);
		double dotProduct = dot.getAsDouble(0,0);
		return dotProduct / sum;
	}

}
