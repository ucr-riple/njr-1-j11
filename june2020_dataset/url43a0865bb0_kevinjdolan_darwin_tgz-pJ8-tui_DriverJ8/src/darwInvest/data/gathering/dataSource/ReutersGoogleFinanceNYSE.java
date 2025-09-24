package darwInvest.data.gathering.dataSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import darwInvest.data.NewsEvent;
import darwInvest.data.Ticker;
import darwInvest.data.gathering.dataSource.reuters.URLExploreJob;
import darwInvest.data.gathering.dataSource.reuters.URLExploreJobInput;
import darwInvest.data.gathering.dataSource.reuters.URLExploreJobOutput;
import darwInvest.data.utility.DefaultSerializer;
import darwInvest.data.utility.Serializer;
import darwin.work.BusyException;
import darwin.work.Job;
import darwin.work.JobRunner;
import darwin.work.threadPool.ThreadPool;

public class ReutersGoogleFinanceNYSE extends DataSource {
	private String source;
	private int nostories;
	
	public ReutersGoogleFinanceNYSE(Serializer serializer, Date startdate, Date enddate, int numberStories) {
		super("ReutersGoogleFinanceNYSE", serializer, startdate, enddate);
		nostories = numberStories;
	}
	
	public void gatherData() {
		File target = new File("StockData/" + source);
		target.mkdir();
		File listoftickers = new File("RawData/" + super.getName() + "/tickers.txt");
		Scanner s = null;
		try {
			s = new Scanner(new BufferedReader(new FileReader(listoftickers)));
			s.useDelimiter("[\n\r]+");
			GoogleFinanceManual csvreader = new GoogleFinanceManual(new DefaultSerializer());
			while(s.hasNext()) {
				String currentstock = s.next();
				Ticker ticker = new Ticker(currentstock);
				String companyname = ticker.lookupCompanyname();
				String reuters = "http://www.reuters.com/search?blob=%22" + webize(companyname) +  "%22";
				//(new Thread(new ParallelReader(ticker,reuters,nostories,startdate,enddate))).start();
				readInReuters(ticker, reuters, nostories);
				String companyid = ticker.lookupGooglecompanyid();
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(startdate);
				String startmonth = csvreader.MonthtoGoogle(cal);
				String startday = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
				String startyear = String.valueOf(cal.get(Calendar.YEAR));
				cal.setTime(enddate);
				String endmonth = csvreader.MonthtoGoogle(cal);
				String endday = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
				String endyear = String.valueOf(cal.get(Calendar.YEAR));
				String longurl = "http://www.google.com/finance/historical?cid=" + companyid + "&startdate=" 
				+ startmonth + "+" + startday + "%2C+" + startyear + "&enddate=" + endmonth + "+" + endday + "%2C+"
				+ endyear + "&output=csv";
				URL googlecsv = new URL(longurl);
				System.out.println(currentstock + ":" + longurl);
				BufferedReader googlecsvread = new BufferedReader(new InputStreamReader(googlecsv.openStream()));
				ticker.addData(csvreader.readIn(googlecsvread));
				if (googlecsvread != null) {
					googlecsvread.close();
				}
				addTicker(ticker);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}
	
	private void readInReuters(Ticker ticker, String reuters, int nostories) {
		Boolean reallydone = false;
		int storycounter = 0;
		int pagenumber = 1;
		
		JobRunner runner = new ThreadPool(4);
		Job job = new URLExploreJob();
		
		//First build the job list
		while (!reallydone && storycounter < nostories) {
			try {
				String nexturl = reuters.concat("&pn=" + pagenumber);
				URL reutersurl = new URL(nexturl);
				BufferedReader reutersreader = new BufferedReader(new InputStreamReader(reutersurl.openStream()));
				System.out.println(ticker.getSymbol() + ":" + nexturl);
				Scanner s3 = new Scanner(reutersreader);
				s3.useDelimiter("[\n\r]+");
				Boolean done = false;
				while(s3.hasNext() && !done && storycounter < nostories) {
					String next = s3.next();
					if(next.contains("<div class=\"secondaryContent\">")) {
						done = true;
					}
					if(next.contains("<div class=\"searchResult\">")) {
						String extractor = s3.next();
						String extractor2 = extractor.substring(extractor.indexOf("href"));
						String title = extractor2.substring(extractor2.indexOf('>')+1,extractor2.indexOf('<'));
						URL exploreurl = new URL(extractor2.substring(extractor2.indexOf('\"')+1,extractor2.indexOf('>')-1) + "?sp=true");
						s3.next();
						String dateextractor = s3.next().substring(2);
						String da = dateextractor.substring(dateextractor.indexOf('>')+1,dateextractor.indexOf('<'));
						Date date = dconv(da);
						
						//String content = URLExplorer(exploreurl);
						//ticker.addNews(new NewsEvent(title, content, date.getTime()));
						
						URLExploreJobInput input = new URLExploreJobInput();
						input.exploreurl = exploreurl;
						input.title = title;
						input.time = date.getTime();
						runner.addJob(job, input);
						
						if (date.before(startdate)) {
							reallydone = true;
							break;
						}
						storycounter++;
					}
				}
				pagenumber++;
				if (s3 != null) {
					s3.close();
				}
				if (reutersreader != null) {
					reutersreader.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//Execute the jobs and gather the results
		try {
			List<Object> result = runner.dispatch();
			for(Object out : result) {
				URLExploreJobOutput output = (URLExploreJobOutput) out;
				ticker.addNews(new NewsEvent(output.title, output.content, output.time));
			}
		} catch (BusyException e) {
			e.printStackTrace();
		}
		
	}	
	
	private String webize(String name) {
		char[] arr = name.toCharArray();
		for(int i = 0; i < arr.length; i++) {
			if (arr[i] == ' ') {
				arr[i] = '+';
			}
		}
		return new String(arr);
	}

	private Date dconv(String date) {
		GregorianCalendar cal = new GregorianCalendar();
		GoogleFinanceManual csvreader = new GoogleFinanceManual(new DefaultSerializer());
		int day = 0, month = 0, year = 0;
		String mon = null;
		if (date.length() == 15) {
			day = Integer.parseInt(date.substring(9, 10));
			mon = date.substring(5, 8);
			year = Integer.valueOf((date.substring(11)));
		}
		if (date.length() == 16) {
			day = Integer.parseInt(date.substring(9, 11));
			mon = date.substring(5, 8);
			year = Integer.valueOf(date.substring(12));
		}
		month = csvreader.GoogletoMonth(mon);
		cal.set(year, month, day);
		return cal.getTime();
	}
}
