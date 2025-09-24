package darwInvest.news;

import darwInvest.data.NewsEvent;

/**
 * The NewsAnalyzer object stands alone with aggregate training
 * data and can be used to score new news articles
 * @author Kevin Dolan
 */
public interface NewsAnalyzer {

	/**
	 * Score a news article according to whatever method is
	 * implemented by this class
	 * @param event    the news article to score
	 * @param forecast the forecast parameter
	 * @return		   the score of the article
	 */
	public double score(NewsEvent event, long forecast);
	
}