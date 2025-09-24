package darwInvest.data.gathering.dataSource;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import darwInvest.data.Ticker;
import darwInvest.data.utility.*;

/**
 * The GoogleFinanceAuto generates and reads in CSVs for the stocks in
 * GoogleFinanceAuto/tickers.txt.
 * 
 * @author Andrew Perrault, Kevin Dolan
 */

public class GoogleFinanceAutoNASDAQ extends DataSource {

private String source;
	
	public GoogleFinanceAutoNASDAQ(Serializer serializer, Date startdate, Date enddate) {
		super("GoogleFinanceAutoNASDAQ", serializer, startdate, enddate);
	}
	
	@Override
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
				addTicker(ticker);
				if (googlecsvread != null) {
					googlecsvread.close();
				}
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

}
