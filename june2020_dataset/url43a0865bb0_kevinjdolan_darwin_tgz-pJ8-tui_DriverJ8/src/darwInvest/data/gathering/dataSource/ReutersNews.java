package darwInvest.data.gathering.dataSource;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import darwInvest.data.NewsEvent;
import darwInvest.data.Ticker;
import darwInvest.data.utility.*;

/**
 * The GoogleFinanceAuto generates and reads in CSVs for the stocks in
 * GoogleFinanceAuto/tickers.txt.
 * 
 * @author Andrew Perrault, Kevin Dolan
 */

public class ReutersNews extends DataSource {

	private String source;

	public ReutersNews(Serializer serializer, Date startdate, Date enddate) {
		super("ReutersNews", serializer, startdate, enddate);
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

	private Boolean inRange(Date startdate, Date enddate, Date date) {
		if(enddate.after(date) && startdate.before(date)) {
			return true;
		}
		else return false;
	}

	@Override
	public void gatherData() {
		//no of stories to read in, for time's sake.
		int nostories = 15;
		File target = new File("StockData/" + source);
		target.mkdir();
		File listoftickers = new File("RawData/" + super.getName() + "/tickers.txt");
		Scanner s = null;
		try {
			s = new Scanner(new BufferedReader(new FileReader(listoftickers)));
			s.useDelimiter("[\n\r]+");
			while(s.hasNext()) {
				String currentstock = s.next();
				Ticker ticker = new Ticker(currentstock);
				String companyname = ticker.lookupCompanyname();
				String reuters = "http://www.reuters.com/search?blob=%22" + webize(companyname) +  "%22";
				readIn(ticker, reuters, nostories);
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

	private void readIn(Ticker ticker, String reuters, int nostories) {
		Boolean reallydone = false;
		int storycounter = 0;
		int pagenumber = 1;
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
						//Test timestamp
						if (inRange(startdate,enddate,date)) {
							String content = URLExplorer(exploreurl);
							ticker.addNews(new NewsEvent(title, content, date.getTime()));
						}
						else if (date.before(startdate)) {
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
	}

	private String URLExplorer(URL exploreurl) {
		BufferedReader reutersreader = null;
		try {
			reutersreader = new BufferedReader(new InputStreamReader(exploreurl.openStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scanner s = new Scanner(reutersreader);
		Boolean done = false;
		Boolean started = false;
		String contents = null;
		s.useDelimiter("[\n\r]+");
		while(s.hasNext() && !done) {
			String next = s.next();
			if(next.contains("quoteProfile.css") && started) {
				done = true;
			}
			else if (started) {
				contents = contents.concat(next);
			}
			else if(next.contains("<span id=\"midArticle_start\">")) {
				contents = new String(s.next());
				started = true;
			}
		}
		if (s != null) {
			s.close();
		}
		if (reutersreader != null) {
			try {
				reutersreader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return contents;
	}

}
