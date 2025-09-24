package darwInvest.news.knn;

import java.util.LinkedList;
import java.util.List;

import org.ujmp.core.*;
import darwInvest.data.NewsEvent;
import darwInvest.data.Ticker;
import darwInvest.news.NewsAnalyzer;
import darwInvest.news.tfidf.Index;
import darwInvest.news.utility.*;

/**
 * The tf.idf analyzer takes the inverted index from the
 * tf.idf trainer and computes a term frequency / inverse
 * document frequency matrix to use to create a weighted
 * average of document similarity
 * 
 * @author Kevin Dolan, Jae Yong Sung
 */
public class KnnAnalyzer implements NewsAnalyzer{

	private Ticker ticker;
	private List<NewsEvent> news;
	private Index index;
	private Matrix matrix;
	private Tokenizer tokenizer;
	private int k;
	private boolean avg;
	
	public KnnAnalyzer(Ticker ticker, List<NewsEvent> news, Index index, int k, boolean avg) {
		this.ticker = ticker;
		this.news = news;
		this.index = index;
		this.k = k;
		this.avg = avg;
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
		
		LinkedList<Double> bestValues = new LinkedList<Double>();
		LinkedList<Integer> bestIndexes = new LinkedList<Integer>();
		
		for(int i = 0; i < similarity.getRowCount(); i++) {
			double value = similarity.getAsDouble(i,0);
			
			int j = 0;
			for(; j < bestValues.size(); j++)
				if(bestValues.get(j) > value)
					break;
			
			bestValues.add(j, value);
			bestIndexes.add(j, i);
			
			if(bestValues.size() > k) {
				bestValues.remove(0);
				bestIndexes.remove(0);
			}
		}
		
		if(avg) {
			double sum = 0;
			for(int index : bestIndexes)
				sum += delta.getAsDouble(0,index);
			
			return sum / k;
		}
		else {
			int numPos = 0;
			for(int index : bestIndexes)
				if(delta.getAsDouble(0,index) > 0)
					numPos++;
			double prop = numPos / (double) k;
			return 2 * prop - 1;
		}
	}

}
