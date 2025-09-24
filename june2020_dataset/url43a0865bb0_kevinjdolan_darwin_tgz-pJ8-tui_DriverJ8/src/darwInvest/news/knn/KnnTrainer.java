package darwInvest.news.knn;

import java.util.List;
import darwInvest.data.Ticker;
import darwInvest.news.NewsAnalyzer;
import darwInvest.news.NewsTrainer;
import darwInvest.news.tfidf.Index;
import darwInvest.news.tfidf.IndexBuilder;
import darwInvest.data.NewsEvent;

/**
 * The knn trainer builds an inverted index
 * of the document corpus and creates a nearest-neighbor
 * news analyzer
 * 
 * @author Kevin Dolan
 */
public class KnnTrainer implements NewsTrainer {

	private int k;
	private boolean avg;
	
	/**
	 * Instantiate a new Knn Trainer
	 * @param k   the k parameter to use
	 * @param avg whether to use the average method or the vote method
	 */
	public KnnTrainer(int k, boolean avg) {
		this.k = k;
		this.avg = avg;
	}
	
	@Override
	public NewsAnalyzer train(Ticker ticker, long begin, long end) {
		List<NewsEvent> news = ticker.getNews(begin, end);
		Index index = IndexBuilder.buildIndex(news);
		return new KnnAnalyzer(ticker, news, index, k, avg);
	}

}