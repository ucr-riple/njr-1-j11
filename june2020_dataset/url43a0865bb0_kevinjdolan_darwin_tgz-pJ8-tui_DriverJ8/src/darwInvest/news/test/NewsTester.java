package darwInvest.news.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TreeMap;
import darwInvest.data.DataLoader;
import darwInvest.data.NewsEvent;
import darwInvest.data.Ticker;
import darwInvest.data.index.TickerIndex;
import darwInvest.data.utility.DefaultSerializer;
import darwInvest.news.NewsAnalyzer;
import darwInvest.news.NewsTrainer;
import darwInvest.news.knn.KnnTrainer;

/**
 * The News Tester tests the news analysis methods 
 * @author Kevin Dolan
 */
public class NewsTester {
	
	private static String testName = "KNN-50-VOTE";
	
	private static String[] tickers = new String[] {
		"C",
		"PFE",
		"RSH"
	};
	
	private static long[] timeFrames = new long[] {
		259200000L,
		604800000L,
		1209600000L,
		2629743830L
	};
	
	private static NewsTrainer trainer = new KnnTrainer(50, false);
	
	private static long step = 86400000L;

	public static void main(String[] args) {	
		File file = new File("TestResults/"+testName);
		file.mkdir();
		
		TickerIndex[] indexes = DataLoader.getDataSources(new DefaultSerializer());
		TickerIndex index = indexes[3];
		
		for(int t = 0; t < tickers.length; t++) {
			System.out.println("Processing TICKER:" + tickers[t]);
			Ticker ticker = index.getTickerFile(tickers[t]);
			
			for(int trainStart = 2000; trainStart <= 2007; trainStart++) {
				for(int trainEnd = trainStart; trainEnd <= 2008; trainEnd++) {
					GregorianCalendar cal = new GregorianCalendar();
					
					cal.set(trainStart, Calendar.JANUARY, 1);
					Date startTrain = cal.getTime();
					cal.set(trainEnd, Calendar.DECEMBER, 31);
					Date endTrain = cal.getTime();
					
					System.out.println("Training from " + startTrain + " to " + endTrain);
					NewsAnalyzer analyzer = trainer.train(ticker, startTrain.getTime(), endTrain.getTime());
					
					int year = trainEnd + 1;
					cal.set(year, Calendar.JANUARY, 1);
					Date startTest = cal.getTime();
					cal.set(year, Calendar.DECEMBER, 31);
					Date endTest = cal.getTime();
					
					System.out.println("Gathering news from the year " + year);
					List<NewsEvent> events = ticker.getNews(startTest.getTime(), endTest.getTime());
					
					for(int f = 0; f < timeFrames.length; f++) {
						long forecast = timeFrames[f];
						
						System.out.println("Caching forecasts of " + forecast + "ms...");
						TreeMap<Long,List<Double>> cache = new TreeMap<Long,List<Double>>();
						for(NewsEvent event : events) {
							double score = analyzer.score(event, forecast);
							
							List<Double> list = cache.get(event.getDate());
							if(list == null) {
								list = new ArrayList<Double>();
								cache.put(event.getDate(), list);
							}
							list.add(score);
						}
						
						for(int l = 0; l < timeFrames.length; l++) {
							long lookback = timeFrames[l];
							
							System.out.println("Using a lookback of " + lookback + "ms.");
							String result = "";
							double numTries = 0;
							double numRight = 0;
							for(long point = startTest.getTime() + lookback;
								point < endTest.getTime(); point += step) {
								
								Collection<List<Double>> lists =
									cache.subMap(point - lookback, true, point, true).values();
								
								double count = 0;
								double total = 0;
								for(List<Double> list : lists) {
									for(Double score : list) {
										count++;
										total += score;
									}
								}
								
								if(count != 0) {								
									double score = total / count;
									
									double priceMove = ticker.getPrice(point + forecast) 
										- ticker.getPrice(point);
									
									boolean right = false;
									if(priceMove * score > 0)
										right = true;
									
									numTries++;
									if(right)
										numRight++;
									
									String row = point + "," + score + "," + priceMove + "," + right;
									result += row + "\n";
								}
								
							}
							
							System.out.println("Proportion correct: " + (numRight / numTries));
							String filename = tickers[t] + "_" + trainStart + "_" + year + "_" + l + "_" + f + ".csv";
							try {
						        BufferedWriter out = new BufferedWriter(new 
						        		FileWriter("TestResults/"+testName+"/"+filename));
						        out.write(result);
						        out.close();
						    } catch (IOException e) {
						    	e.printStackTrace();
						    }
						}
					}
				}
			}
		}
	}
}
