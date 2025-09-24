package darwInvest.data.gathering.dataSource;

import java.util.Calendar;
import java.util.GregorianCalendar;

import darwInvest.data.Ticker;
import darwInvest.data.utility.Serializer;

/**
 * Generates data of constant slope, from
 * January 1st, 2000 to December 31, 2000,
 * incremented by the slope for every hour
 * @author Kevin Dolan
 */
public class ConstantSlope extends DataSource {

	private double slope;
	
	public ConstantSlope (double slope, Serializer serializer) {
		super("Constant-Slope", serializer);
		this.slope = slope;
	}
	
	@Override
	public void gatherData() {
		Ticker ticker = new Ticker("Slope-"+slope);
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(2000, Calendar.JANUARY, 1, 0, 0);
		long timeStart = cal.getTimeInMillis();
		cal.set(2001, Calendar.JANUARY, 1, 0, 0);
		long timeEnd = cal.getTimeInMillis();
		double open = 10.;
		double close = 10. + slope;
		double high = 10 + 2 * slope;
		double low = 10 - 2 * slope;
		double volume = 1000;
		for(long time = timeStart; time < timeEnd; time += 3600000) {
			ticker.addData(time, new double[] {open, high, low, close, volume});
			open += slope;
			close += slope;
			high += slope;
			low += slope;
			volume += Math.random() * 50 - 25;
		}
		addTicker(ticker);
	}

}
