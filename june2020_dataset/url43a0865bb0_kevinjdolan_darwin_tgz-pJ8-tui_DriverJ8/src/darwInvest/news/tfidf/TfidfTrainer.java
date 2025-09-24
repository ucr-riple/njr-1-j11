package darwInvest.news.tfidf;

import java.util.List;
import darwInvest.data.Ticker;
import darwInvest.news.NewsAnalyzer;
import darwInvest.news.NewsTrainer;
import darwInvest.data.NewsEvent;

/**
 * The tf.idf trainer builds an inverted index
 * of the document corpus and creates a news
 * analyzer
 * 
 * @author Kevin Dolan
 */
public class TfidfTrainer implements NewsTrainer {

	@Override
	public NewsAnalyzer train(Ticker ticker, long begin, long end) {
		List<NewsEvent> news = ticker.getNews(begin, end);
		Index index = IndexBuilder.buildIndex(news);
		return new TfidfAnalyzer(ticker, news, index);
	}

}