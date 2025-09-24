package darwInvest.news;

import darwInvest.data.Ticker;

/**
 * The News Trainer interface will be implemented by any
 * class that is used to receive training information from
 * a set of news articles
 * @author Kevin Dolan
 */
public interface NewsTrainer {

	/**
	 * Return a new NewsAnalyzer object that reflects
	 * training on the set of tickers provided
	 * @param ticker  the ticker to train
	 * @param begin	  the beginning of the training period
	 * @param end	  the end of the training period
	 * @return		  a NewsAnalyzer that reflects the training data
	 */
	public NewsAnalyzer train(Ticker ticker, long begin, long end);
	
}
