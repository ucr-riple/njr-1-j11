package darwInvest.data.gathering.dataSource;

import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import darwInvest.data.Ticker;
import darwInvest.data.gathering.CSVFilter;
import darwInvest.data.utility.DefaultSerializer;
import darwInvest.data.utility.Serializer;

/**
 * The CSV reader reads manually downloaded .csv files in the format:
 * Date,Open,High,Low,Close,Volume
 * For example:
 * 2-Oct-09,17.23,17.35,16.78,16.84,32069975
 * 
 * @author Andrew Perrault, Kevin Dolan
 */
public abstract class CSVReader extends DataSource {

	private String source;
	
	public CSVReader(String source, Serializer serializer) {
		super(source, serializer);
		this.source = source;
	}

	@Override
	public void gatherData() {
		CSVFilter filter = new CSVFilter();
		File tickers = new File("RawData/" + source);
		File[] tickersa = tickers.listFiles(filter);
		File target = new File("StockData/" + source);
		target.mkdir();
		for(int i = 0; i < tickersa.length; i++) {
			String name = tickersa[i].getName();
			Ticker ticker = new Ticker(name.substring(0, name.indexOf('.')));
			ticker.addData(readIn(tickersa[i]));
			addTicker(ticker);
		}
	}
	
	public Map<Long, double[]> readIn(File source) {
		TreeMap<Long, double[]> data = new TreeMap<Long, double[]>();
		Scanner s = null;
		GoogleFinanceManual gfg = new GoogleFinanceManual(new DefaultSerializer());
		try {
			int count = 0;
			Double high = 0.0;
			Double low = 0.0;
			Double open = 0.0;
			Double close = 0.0;
			Double volume = 0.0;
			String date = null;
			s = new Scanner(new BufferedReader(new FileReader(source)));
			s.useDelimiter(",");
			s.next();
			s.next();
			s.next();
			s.next();
			s.useDelimiter("\\s*\n");
			s.next();
			s.useDelimiter(",");
			while (s.hasNext()) {
				if (count == 0) {
					s.useDelimiter(",");
					date = s.next();
				}
				if (count == 1)
					open = s.nextDouble();
				if (count == 2)
					high = s.nextDouble();
				if (count == 3)
					low = s.nextDouble();
				if (count == 4)
					close = s.nextDouble();
				if (count == 5) {
					s.useDelimiter("\n");
					String vol = s.next();
					volume = (Double.parseDouble(vol.substring(1)));
				}
				count++;
				if (count == 6) {
					int day = 0, month = 0, year = 0;
					String mon = null;
					GregorianCalendar cal = new GregorianCalendar();
					if (date.length() == 10) {
						day = Integer.parseInt(date.substring(1, 3));
						mon = date.substring(4, 7);
						int yr = Integer.valueOf((date.substring(8,10)));
						if (yr > 50) {
							year = Integer.parseInt("19".concat(date.substring(8,
									10)));
						}
						else {
							year = Integer.parseInt("20".concat(date.substring(8,
									10)));
						}
					}
					if (date.length() == 9) {
						day = Integer.parseInt(date.substring(1, 2));
						mon = date.substring(3, 6);
						int yr = Integer.valueOf(date.substring(7, 9));
						if (yr > 50) {
							year = Integer.parseInt("19".concat(date.substring(7,
									9)));
						}
						else {
							year = Integer.parseInt("20".concat(date.substring(7,
									9)));
						}
					}
					month = gfg.GoogletoMonth(mon);
					cal.clear();
					cal.set(year, month, day, 17, 0);
					double[] d = { open, high, low, close, volume };
					data.put(cal.getTimeInMillis(), d);
					count = 0;
				}

			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return data;

	}
	
	public Map<Long, double[]> readIn(BufferedReader buffreader) {
		TreeMap<Long, double[]> data = new TreeMap<Long, double[]>();
		Scanner s = null;
		try {
			int count = 0;
			Double high = 0.0;
			Double low = 0.0;
			Double open = 0.0;
			Double close = 0.0;
			Double volume = 0.0;
			String date = null;
			s = new Scanner(buffreader);
			s.useDelimiter(",");
			s.next();
			s.next();
			s.next();
			s.next();
			s.useDelimiter("\\s*\n");
			s.next();
			s.useDelimiter(",");
			while (s.hasNext()) {
				if (count == 0) {
					s.useDelimiter(",");
					date = s.next();
					}
				if (count == 1)
					open = s.nextDouble();
				if (count == 2)
					high = s.nextDouble();
				if (count == 3)
					low = s.nextDouble();
				if (count == 4)
					close = s.nextDouble();
				if (count == 5) {
					s.useDelimiter("\n");
					String vol = s.next();
					volume = (Double.parseDouble(vol.substring(1)));
					}
				count++;
				if (count == 6) {
					int day = 0, month = 0, year = 0;
					String mon = null;
					GregorianCalendar cal = new GregorianCalendar();
					if (date.length() == 10) {
						day = Integer.parseInt(date.substring(1, 3));
						mon = date.substring(4, 7);
						year = Integer.parseInt("20".concat(date.substring(8,
								10)));
					}
					if (date.length() == 9) {
						day = Integer.parseInt(date.substring(1, 2));
						mon = date.substring(3, 6);
						year = Integer.parseInt("20".concat(date
								.substring(7, 9)));
					}
					if (mon.equals("Jan"))
						month = Calendar.JANUARY;
					else if (mon.equals("Feb"))
						month = Calendar.FEBRUARY;
					else if (mon.equals("Mar"))
						month = Calendar.MARCH;
					else if (mon.equals("Apr"))
						month = Calendar.APRIL;
					else if (mon.equals("May"))
						month = Calendar.MAY;
					else if (mon.equals("Jun"))
						month = Calendar.JUNE;
					else if (mon.equals("Jul"))
						month = Calendar.JULY;
					else if (mon.equals("Aug"))
						month = Calendar.AUGUST;
					else if (mon.equals("Sep"))
						month = Calendar.SEPTEMBER;
					else if (mon.equals("Oct"))
						month = Calendar.OCTOBER;
					else if (mon.equals("Nov"))
						month = Calendar.NOVEMBER;
					else
						month = Calendar.DECEMBER;
					cal.clear();
					cal.set(year, month, day, 17, 0);
					double[] d = { open, high, low, close, volume };
					data.put(cal.getTimeInMillis(), d);
					count = 0;
				}

			}
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return data;

	}
}