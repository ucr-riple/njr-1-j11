package darwInvest.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * The ticker object contains the information related to a
 * single stock
 * @author Kevin Dolan, Andrew Perrault
 */
public class Ticker implements Serializable {

	private static final long serialVersionUID = -6597515698703903356L;
	private String symbol;
	private TreeMap<Long, double[]> data;
	private TreeMap<Long, List<NewsEvent>> news;
	private double[] minData, maxData;
	private String companyname;
	private String googlecompanyid;
	private int newsCount;
	
	public Ticker(String symbol) {
		this.symbol = symbol;
		data = new TreeMap<Long, double[]>();
		news = new TreeMap<Long, List<NewsEvent>>();
		newsCount = 0;
	}
	
	/**
	 * Add a piece of data to the ticker history
	 * @param time  the time of the data point
	 * @param point the point to be added
	 */
	public void addData(long time, double[] point) {
		data.put(time, point);
		
		if(minData == null) {
			minData = new double[point.length];
			maxData = new double[point.length];
			for(int i = 0; i < point.length; i++) {
				minData[i] = point[i];
				maxData[i] = point[i];
			}
		}
		else {
			for(int i = 0; i < point.length; i++) {
				minData[i] = Math.min(minData[i], point[i]);
				maxData[i] = Math.max(maxData[i], point[i]);
			}
		}
	}
	
	/**
	 * Add a piece of data to the ticker history
	 * @param date  the date object of the data point
	 * @param point the point to be added
	 */
	public void addData(Date date, double[] point) {
		long time = date.getTime();
		addData(time, point);
	}
	
	/**
	 * Adds a collection of data to the ticker history
	 * @param newdata  the new collection of data
	 */
	public void addData(Map<Long, double[]> newdata) {
		Set<Entry<Long, double[]>> entries = newdata.entrySet();
		for(Entry<Long, double[]> entry : entries)
			addData(entry.getKey(), entry.getValue());
	}
	
	/**
	 * Add a piece of news to the ticker history
	 * @param time  the time of the data point
	 * @param story the news story object to add
	 */
	public void addNews(NewsEvent story) {
		List<NewsEvent> list = news.get(story.getDate());
		if(list == null) {
			list = new ArrayList<NewsEvent>();
			news.put(story.getDate(), list);
		}
		list.add(story);
		newsCount++;
	}
	
	/**
	 * @return the symbol of this ticker
	 */
	public String getSymbol() {
		return symbol;
	}
	
	/**
	 * @return the number of data points
	 */
	public int getNumberDataPoints() {
		return data.size();
	}
	
	/**
	 * @return the number of news stories
	 */
	public int getNumberNewsEvents() {
		return newsCount;
	}
	
	/**
	 * @return the appropriate range of entries
	 */
	@SuppressWarnings("unchecked")
	private Entry<Long, ?>[] getRange(TreeMap<Long, ?> data, long time) {
		if(time > data.lastKey()) {
			Entry<Long, ?> entry = data.lastEntry();
			return new Entry[] { entry, entry };
		}
		if(time < data.firstKey()) {
			Entry<Long, ?> entry = data.firstEntry();
			return new Entry[] { entry, entry };
		}
		return new Entry[] { data.floorEntry(time), data.ceilingEntry(time) };
	}
	
	/**
	 * Determine the (potentially interpolated) price point of a given time
	 * @param time  the time to get the value for
	 * @param index the index of the price point to access
	 * @return 	 	the interpolated price value
	 */
	public double getData(long time, int index) {
		Entry<Long, ?>[] result = getRange(data, time);
		
		double[] value;
		double range;
		
		long x1 = result[0].getKey();
		value = (double[]) result[0].getValue();
		double y1 = value[index];
		
		long x2 = result[1].getKey();
		value = (double[]) result[1].getValue();
		double y2 = value[index];
		
		range = x2 - x1;
		if(range == 0)
			return y1;
		
		double position = time - x1;
		double proportion = position / range;
		
		range = y2 - y1;
		
		return proportion * range + y1;
	}
	
	/**
	 * Get the instantaneous price of a given time
	 * @param time the time to access
	 * @return	   a price somewhere between the high and low
	 */
	public double getPrice(long time) {
		double high = getData(time, 1);
		double low = getData(time, 2);
		double range = high - low;
		double offset = Math.random() * range;
		return low + offset;
	}
	
	/**
	 * Normalize the data to be between the min and max
	 * for the particular data field
	 * @param which the index of the data field
	 * @param data  the value of the data
	 * @return		a number (preferably) [0,1]
	 */
	public double normalizeData(int which, double data) {
		double diff = data - minData[which];
		double range = maxData[which] - minData[which];
		return diff / range;
	}
	
	/**
	 * Get a list of all news stories in a given time range
	 * @param firstTime the beginning of the time range
	 * @param lastTime	the end of the time range
	 * @return			the list of news events
	 */
	public List<NewsEvent> getNews(long firstTime, long lastTime) {
		Collection<List<NewsEvent>> collection = 
			(news.subMap(firstTime, true, lastTime,true)).values();
		List<NewsEvent> list = new ArrayList<NewsEvent>();
		for(List<NewsEvent> entry : collection)
			for(NewsEvent event : entry)
				list.add(event);
		return list;
	}
	
	/**
	 * @return the first time of this data set (considering only price information)
	 */
	public long getFirstTime() {
		return data.firstKey();
	}
	
	/**
	 * @return the last time of this data set (considering only price information)
	 */
	public long getLastTime() {
		return data.lastKey();
	}
	
	/**
	 * @return the first time of the news data set
	 */
	public long getFirstNews() {
		return news.firstKey();
	}
	
	/**
	 * @return the last time of the news data set
	 */
	public long getLastNews() {
		return news.lastKey();
	}
	
	/**
	 * Return all times between the given range
	 * @param begin the beginning of the range
	 * @param end   the end of the range
	 * @return      an ordered set of time values
	 */
	public Set<Long> getTimesBetween(long begin, long end) {
		return data.subMap(begin, end).keySet();
	}
	
	/**
	 * Get the lowest time greater than the input time
	 * @param time the time to look for
	 * @return     the ceiling time
	 */
	public long ceilingTime(long time) {
		return data.ceilingKey(time);
	}
	
	/**
	 * Get the greatest time less than the input time
	 * @param time the time to look for
	 * @return     the floor time
	 */
	public long floorTime(long time) {
		return data.floorKey(time);
	}

	public String lookupGooglecompanyid() throws IOException {
		try {
			URL google = new URL("http://www.google.com/finance?q=NASDAQ:" + symbol);
			BufferedReader in = new BufferedReader(new InputStreamReader(google.openStream()));
			Scanner s = new Scanner(in);
			s.useDelimiter("[\n\r]+");
			String companyid = null;
			while(s.hasNext()) {
				String next = s.next();
				if(next.contains("var _companyId")) {
					String extractor = next.substring(17);
					companyid = extractor.substring(0,(extractor.indexOf(';')));
					break;
				}
			}
			if (s != null) {
				s.close();
			}
			if (in != null) {
				in.close();
			}
			this.googlecompanyid = companyid;
			return companyid;
		}
		catch (Exception e) {
			throw new IOException();
		}
	}

	public String lookupCompanyname() throws IOException {
		if (symbol.equals("BAC")) {
			companyname = "Bank of America";
		}
		else if (symbol.equals("F")) {
			companyname = "Ford";
		}
		else if (symbol.equals("WFC")) {
			companyname = "Wells Fargo";
		}
		else if (symbol.equals("MOT")) {
			companyname = "Motorola";
		}
		if (companyname != null) {
			return companyname;
		}
		URL google = new URL("http://www.google.com/finance?q=NASDAQ:" + symbol);
		BufferedReader in = new BufferedReader(new InputStreamReader(google.openStream()));
		Scanner s = new Scanner(in);
		s.useDelimiter("[\n\r]+");
		Boolean cname = false;
		while(s.hasNext()) {
			String next = s.next();
			if (cname) {
				break;
			}
			if(next.contains("<title>") && !cname) {
				String extractor = next.substring(7,next.indexOf("- Google Finance"));
				companyname = IncCorpClean(extractor.substring(0,extractor.lastIndexOf(' ')));
				cname = true;
			}
		}
		if (s != null) {
			s.close();
		}
		if (in != null) {
			in.close();
		}
		return companyname;
	}

	private String IncCorpClean(String unclean) {
		String temp = null;
		if (unclean.indexOf("Inc") != -1) {
			temp = unclean.substring(0, unclean.indexOf("Inc")-1);
		}
		else temp = unclean;
		String temp2 = null;
		if (temp.indexOf("Corp") != -1) {
			temp2=temp.substring(0, temp.indexOf("Corp")-1);
		}
		else temp2=temp;
		String temp3 = null;
		if (temp2.indexOf("Limited") != -1) {
			temp3=temp2.substring(0, temp2.indexOf("Limited")-1);
		}
		else temp3=temp2;
		String temp4 = null;
		if (temp3.indexOf("Ltd") != -1) {
			temp4=temp3.substring(0, temp3.indexOf("Ltd")-1);
		}
		else temp4=temp3;
		String temp5 = null;
		if (temp4.indexOf("Company") != -1) {
			temp5=temp4.substring(0, temp4.indexOf("Company")-1);
		}
		else temp5=temp4;
		return temp5;
	}
	
	public String getCompanyname() {
		return companyname;
	}

	public String getGooglecompanyid() {
		return googlecompanyid;
	}
}
