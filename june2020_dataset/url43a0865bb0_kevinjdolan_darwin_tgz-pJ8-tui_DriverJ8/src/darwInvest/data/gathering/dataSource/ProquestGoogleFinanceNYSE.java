package darwInvest.data.gathering.dataSource;

import java.io.*;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import darwInvest.data.NewsEvent;
import darwInvest.data.Ticker;
import darwInvest.data.gathering.dataSource.proquest.*;
import darwInvest.data.utility.*;
import darwin.work.Job;
import darwin.work.JobRunner;
import darwin.work.threadPool.ThreadPool;

/**
 * The GoogleFinanceAuto generates and reads in CSVs for the stocks in
 * GoogleFinanceAuto/tickers.txt.
 * 
 * @author Andrew Perrault, Kevin Dolan
 */

public class ProquestGoogleFinanceNYSE extends DataSource {

	private String source;

	public ProquestGoogleFinanceNYSE(Serializer serializer, Date startdate, Date enddate) {
		super("ProquestGoogleFinanceNYSE", serializer, startdate, enddate);
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

	private Date proquestdconv(String date) {
		GregorianCalendar cal = new GregorianCalendar();
		GoogleFinanceManual csvreader = new GoogleFinanceManual(new DefaultSerializer());
		int day = 0, month = 0, year = 0;
		String mon = null;
		if (date.length() == 11) {
			day = Integer.parseInt(date.substring(4, 5));
			mon = date.substring(0, 3);
			year = Integer.valueOf((date.substring(7)));
		}
		if (date.length() == 12) {
			day = Integer.parseInt(date.substring(4, 6));
			mon = date.substring(0, 3);
			year = Integer.valueOf(date.substring(8));
		}
		if (date.length() == 8) {
			day = 1;
			mon = date.substring(0, 3);
			year = Integer.valueOf(date.substring(4));
		}
		month = csvreader.GoogletoMonth(mon);
		cal.set(year, month, day, 17, 0, 0);
		return cal.getTime();
	}
	
	private String prosearchdconv(Date date) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		int mon = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int year = cal.get(Calendar.YEAR);
		String daystr = null;
		if (day < 10) {
			daystr = "0" + day;
		}
		else daystr = Integer.toString(day);
		String monstr = null;
		if (mon < 10) {
			monstr = "0" + mon;
		}
		else monstr = Integer.toString(mon);
		String yearstr = Integer.toString(year);
		return (monstr +"%2F"+ daystr +"%2F" + yearstr);
	}

	@Override
	public void gatherData() {
		//no of stories to read in, for time's sake.
		int nostories = 1001;
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
				String companyname = s.next();
				Ticker ticker = new Ticker(currentstock);
				System.out.println(companyname);
				String proquest = "http://proquest.umi.com.proxy.library.cornell.edu/pqdweb?DBId=5921&RQT=305&querySyntax=PQ&searchInterface=1&moreOptState=CLOSED&TS=1257203651&h_pubtitle=&h_pmid=&JSEnabled=1&SQ=%22" +
						webize(companyname) +
						"%22&submit=Search&date=RANGE&onDate=&beforeDate=&afterDate=&fromDate=" +
						prosearchdconv(this.startdate) +
						"&toDate=" +
						prosearchdconv(this.enddate) +
						"&pubtitle=&author=&FT=0&AT=news&revType=review&revPos=all&STYPE=all&sortby=REVERSE_CHRON";
				Date firsttry = readIn(ticker,proquest,nostories);
				while(!firsttry.equals(startdate)) {
					String newpro = "http://proquest.umi.com.proxy.library.cornell.edu/pqdweb?DBId=5921&RQT=305&querySyntax=PQ&searchInterface=1&moreOptState=CLOSED&TS=1257203651&h_pubtitle=&h_pmid=&JSEnabled=1&SQ=%22" +
					webize(companyname) +
					"%22&submit=Search&date=RANGE&onDate=&beforeDate=&afterDate=&fromDate=" +
					prosearchdconv(this.startdate) +
					"&toDate=" +
					prosearchdconv(firsttry) +
					"&pubtitle=&author=&FT=0&AT=news&revType=review&revPos=all&STYPE=all&sortby=REVERSE_CHRON";
					firsttry = readIn(ticker,newpro,nostories);
				}
				String companyid = lookupGooglecompanyid(currentstock);
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
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}
	
	public String lookupGooglecompanyid(String symbol) throws IOException {
		try {
			URL google = new URL("http://www.google.com/finance?q=NYSE:" + symbol);
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
			return companyid;
		}
		catch (Exception e) {
			throw new IOException();
		}
	}

	private Date readIn(Ticker ticker, String proquest, int nostories) {
		JobRunner runner = new ThreadPool(10);
		Job job = new URLExploreJob();
		
		CookieManager cookiemonster = new CookieManager();
		CookieHandler.setDefault(cookiemonster);
		Boolean reallydone = false;
		int storycounter = 0;
		int pagenumber = 1;
		Boolean stemfound = false;
		Boolean totalres = false;
		double noresf = 0.0;
		int maxpage = 0;
		String urlstem = null;
		Date earliestdate = new Date(this.enddate.getTime());
		while (!reallydone && storycounter < nostories && pagenumber <= 100 && (!totalres || (pagenumber <= maxpage))) {
			try {
				String nexturl = null;
				if (pagenumber == 1) {
					nexturl = proquest;
				}
				else {
					nexturl = "http://proquest.umi.com" + urlstem + ((pagenumber-1)*10);
				}
				URL proquesturl = new URL(nexturl);
				BufferedReader proquestsearchreader = new BufferedReader(new InputStreamReader(proquesturl.openStream()));
				System.out.println(ticker.getSymbol() + ":" + nexturl);
				Scanner s3 = new Scanner(proquestsearchreader);
				s3.useDelimiter("[\n\r]+");
				Boolean done = false;
				while(s3.hasNext() && !done && storycounter < nostories) {
					String next = s3.next();
					if(next.contains("No documents found for: ")) {
						done = true;
						reallydone = true;
						break;
					}
					if(next.contains("<td class=\"textMedium\" colspan=\"2\"><a href=\"javascript:CheckAll(true)") && stemfound) {
						done = true;
						break;
					}
					if(next.contains("<a class=\'bold\' href=\'")) {
						try {
							String exploreurl = next.substring(next.indexOf("href")+6,next.indexOf('>')-1);
							String extractor = next.substring(next.indexOf('<')+1);
							String title = extractor.substring(extractor.indexOf('>')+1,extractor.indexOf('<'));
							String dextractor = s3.next();
							String dextractor2 = dextractor.substring(dextractor.indexOf("</span>")+7);

							if (dextractor2.indexOf("</span>") != -1) {
								dextractor2 = dextractor2.substring(dextractor2.indexOf("</span>")+7);
							}
							String dextractor3 = dextractor2;
							String datestring = null;
							if (dextractor3.indexOf(':') != -1) {
								datestring = dextractor3.substring(dextractor3.indexOf(':')+2,dextractor3.indexOf('.', dextractor3.indexOf(':')+1));
							}
							else {
								datestring = dextractor3.substring(3,dextractor3.indexOf('.', dextractor3.indexOf(',')+1));
							}
							if (datestring.indexOf('.') != -1) {
								datestring = datestring.substring(0,(datestring.indexOf('.')));
							}
							if (datestring.indexOf('-') != -1) {
								datestring = datestring.substring(datestring.indexOf('-')+1);
							}
							Date date = proquestdconv(datestring);
							if (date.before(earliestdate)) {
								earliestdate = date;
							}
							
							URLExploreJobInput input = new URLExploreJobInput();
							input.title = title;
							input.time = date.getTime();
							input.exploreurl = new URL("http://proquest.umi.com" + exploreurl);
							runner.addJob(job, input);
							System.out.println("Added:" + title +  "@" + date);
							
							storycounter++;
						} catch (Exception e) {
							System.out.println("Bad parse!");
						}
					}
					if(!totalres && next.contains("<!--PAGECOUNT BEGIN-->")) {
						totalres = true;
						next = s3.next();
						String resex = next.substring(next.indexOf("of")+3,next.indexOf("</div>"));
						int nores = Integer.valueOf(resex);
						noresf = (new Integer(nores)).doubleValue();
						double page = Math.floor(noresf / 10.0);
						maxpage = (new Double(page)).intValue() + 1;
					}
					if(!stemfound && next.contains("2</a> &nbsp;")) {
						String stemextractor = next.substring(next.indexOf("<a href=\"")+9,next.indexOf("firstIndex="));
						stemfound = true;
						urlstem = stemextractor + "firstIndex=";
						//System.out.println(urlstem);
					}
				}
				pagenumber++;
				if (s3 != null) {
					s3.close();
				}
				if (proquestsearchreader != null) {
					proquestsearchreader.close();
				}
				System.out.println("Running explorer...");
				List<Object> result = runner.dispatch();
				System.out.println("Explorer complete.");
				for(Object res : result) {
					URLExploreJobOutput output = (URLExploreJobOutput) res;
					ticker.addNews(new NewsEvent(output.title, output.content, output.time));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (noresf > 1000.0) {
			return earliestdate;
		}
		else {
			return startdate;
		}
	}

	

}
