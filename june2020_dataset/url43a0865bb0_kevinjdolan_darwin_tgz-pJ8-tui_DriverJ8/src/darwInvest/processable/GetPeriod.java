package darwInvest.processable;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import darwInvest.environment.StrategyParameters;
import darwin.population.Node;
import darwin.problemObject.Processable;

public class GetPeriod extends Processable<Double>{

	public static final int MONTH = 0;
	public static final int DAY_OF_MONTH = 1;
	public static final int DAY_OF_WEEK = 2;
	public static final int DAY_OF_WEEK_IN_MONTH = 3;
	public static final int AM_PM = 4;
	public static final int HOUR_OF_DAY = 5;
	public static final int MINUTE = 6;
	public static final int SEASON = 7;
	public static final int FISCAL_QUARTER = 8;
	
	private int which;
	
	public GetPeriod(int which) {
		this.which = which;
	}
	
	@Override
	public Double getValue(Object parameters, Node[] childNodes) {
		StrategyParameters now = (StrategyParameters) parameters;
		
		double value = (Double) childNodes[0].getValue(parameters);
		long time = now.getTime(value);
		
		Date date = new Date(time);
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		if(which == MONTH)
			return cal.get(Calendar.MONTH) / 12.;
		else if(which == DAY_OF_MONTH)
			return cal.get(Calendar.DAY_OF_MONTH) / 31.;
		else if(which == DAY_OF_WEEK)
			return cal.get(Calendar.DAY_OF_WEEK) / 7.;
		else if(which == DAY_OF_WEEK_IN_MONTH)
			return cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) / 5.;
		else if(which == AM_PM)
			return (double) cal.get(Calendar.AM_PM);
		else if(which == HOUR_OF_DAY)
			return cal.get(Calendar.HOUR_OF_DAY) / 24.;
		else if(which == MINUTE)
			return cal.get(Calendar.MINUTE) / 60.;
		else if(which == SEASON) {
			int month = cal.get(Calendar.MONTH);
			int day = month * 100 + cal.get(DAY_OF_MONTH);
			if(day <= 221)
				return 0.;
			else if(day <= 521)
				return .25;
			else if(day <= 823)
				return .5;
			else if(day <= 1121)
				return .75;
			else
				return 0.;
		}
		else {
			int month = cal.get(Calendar.MONTH);
			int day = month * 100 + cal.get(DAY_OF_MONTH);
			if(day <= 231)
				return .25;
			else if(day <= 530)
				return .5;
			else if(day <= 830)
				return .75;
			else
				return 0.;
		}
	}
	
	@Override
	public Processable<Double> clone() {
		return new GetPeriod(which);
	}
	
	@Override
	protected boolean equalsValue(Processable<?> other) {
		GetPeriod con = (GetPeriod) other;
		return con.getWhich() == which;
	}

	public double getWhich() {
		return which;
	}
	
	public String toString() {
		return "GetPeriod(" + which + ")";
	}
}
