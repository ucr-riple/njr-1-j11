package darwInvest.data.gathering;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import darwInvest.data.gathering.dataSource.*;
import darwInvest.data.index.*;
import darwInvest.data.utility.DefaultSerializer;
import darwInvest.data.utility.Serializer;

/**
 * The driver class is the runnable class for gathering data
 * @author Andrew Perrault, Kevin Dolan
 */
public class Driver {
	
	public static void main(String[] args) {
		Serializer serializer = new DefaultSerializer();
		
		GregorianCalendar cal = new GregorianCalendar();
		//Set the date parameters for online data gathering
		cal.set(2000, Calendar.JANUARY, 1);
		Date startdate = cal.getTime();
		cal.set(2009, Calendar.DECEMBER, 31);
		Date enddate = cal.getTime();
		//Select the data source
		DataSource source = new ProquestGoogleFinanceNYSE(serializer, startdate, enddate);
		
		System.out.println("Gathering data from " + source.getName() + "...");
		source.gatherData();
		System.out.println("Data gathering complete.");
		
		System.out.println("Writing index...");
		source.writeTickerIndex();
		System.out.println("Index written.  All operations completed.");
		
		TickerIndex index = source.getTickerIndex();
		System.out.println("Data Source Summary:");
		
		System.out.println(index);
		
		//Ticker ticker = index.getTickerFile("C");
		//ticker.getNews(firstTime, lastTime);
		
	}
	
}
