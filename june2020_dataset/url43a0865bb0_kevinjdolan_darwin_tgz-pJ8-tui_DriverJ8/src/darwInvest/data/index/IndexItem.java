package darwInvest.data.index;

import java.io.Serializable;
import java.util.Date;

import darwInvest.data.Ticker;

public class IndexItem implements Serializable {

	private static final long serialVersionUID = 4575290661458977115L;
	private String symbol;
	private int dataPointCount;
	private int newsCount;
	private long firstTime;
	private long lastTime;
	private long firstNews;
	private long lastNews;
	
	public IndexItem(Ticker ticker) {
		symbol = ticker.getSymbol();
		dataPointCount = ticker.getNumberDataPoints();
		newsCount = ticker.getNumberNewsEvents();
		if (dataPointCount != 0) {
			firstTime = ticker.getFirstTime();
			lastTime = ticker.getLastTime();
		}
		if(newsCount != 0) {
			firstNews = ticker.getFirstNews();
			lastNews = ticker.getLastNews();
		}
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @return the dataPointCount
	 */
	public int getDataPointCount() {
		return dataPointCount;
	}

	/**
	 * @return the newsCount
	 */
	public int getNewsCount() {
		return newsCount;
	}

	/**
	 * @return the firstTime
	 */
	public long getFirstTime() {
		return firstTime;
	}

	/**
	 * @return the lastTime
	 */
	public long getLastTime() {
		return lastTime;
	}	
	
	/**
	 * @return the firstNews
	 */
	public long getFirstNews() {
		return firstNews;
	}

	/**
	 * @return the lastNews
	 */
	public long getLastNews() {
		return lastNews;
	}

	public String toString() {
		Date begin = new Date(firstTime);
		Date end = new Date(lastTime);
		Date newsBegin = new Date(firstNews);
		Date newsEnd = new Date(lastNews);
		return "Symbol: " + symbol + 
			"\n\tData Count: " + dataPointCount + " First Point: " + begin + " Last Point: " + end +
			"\n\tNews Count: " + newsCount + " First Point: " + newsBegin + " Last Point: " + newsEnd;
	}
}
