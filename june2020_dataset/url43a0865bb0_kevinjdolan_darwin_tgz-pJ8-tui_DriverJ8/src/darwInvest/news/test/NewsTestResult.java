package darwInvest.news.test;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * The NewsTestResult class aggregates a series of test results
 * @author Kevin Dolan
 */
public class NewsTestResult {
		
	private static final long serialVersionUID = -3794312210741864413L;
	
	private TreeMap<Integer,TreeMap<String,ArrayList<Point2D.Double>>>
		result = new TreeMap<Integer,TreeMap<String,ArrayList<Point2D.Double>>>();
	
	/**
	 * Put a value to this result set
	 * @param key		  the key value to sort by
	 * @param ticker	  the ticker this refers to
	 * @param accuracy	  the test accuracy
	 * @param correlation the test correlation coefficient
	 */
	public void put(int key, String ticker, double accuracy, double correlation) {
		TreeMap<String,ArrayList<Point2D.Double>> posting = 
			result.get(key);
		if(posting == null) {
			posting = new TreeMap<String,ArrayList<Point2D.Double>>();
			result.put(key, posting);
		}
		
		ArrayList<Point2D.Double> list = posting.get(ticker);
		
		if(list == null) {
			list = new ArrayList<Point2D.Double>();
			posting.put(ticker, list);
		}
		
		list.add(new Point2D.Double(accuracy, correlation));
	}
	
	/**
	 * @param label    the label to apply to the first column
	 * @param accuracy true if this is an accuracy table
	 * @return 		   the csv output of this file
	 */
	public String output(String label, boolean accuracy) {
		String s = label;
		
		Set<Entry<Integer,TreeMap<String,ArrayList<Point2D.Double>>>> 
			entries = result.entrySet();
		
		Entry<Integer,TreeMap<String,ArrayList<Point2D.Double>>> fst = 
			entries.iterator().next();
		TreeMap<String,ArrayList<Point2D.Double>> fstVal = fst.getValue();
		Set<String> tickers = fstVal.keySet();
		for(String ticker : tickers)
			s += "," + ticker;
		s += ",All\n";
		
		for(Entry<Integer,TreeMap<String,ArrayList<Point2D.Double>>>
			entry : entries) {
			
			s += entry.getKey();
			
			Collection<ArrayList<Point2D.Double>> values = entry.getValue().values();
			double bigSum = 0;
			for(ArrayList<Point2D.Double> value : values) {
				double sum = 0;
				for(Point2D.Double point : value) {
					if(accuracy)
						sum += point.x;
					else
						sum += point.y;
				}
				double avg = sum / value.size();
				s += "," + avg;
				bigSum += avg;
			}
			double bigAvg = bigSum / values.size();
			s += "," + bigAvg + "\n";
		}
		
		return s;
	}
}