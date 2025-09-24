package logfilegen.allgenerators.record;

import java.util.GregorianCalendar;

import logfilegen.allmodels.record.Date;

public class DateGen {
	public Date generate(){
		GregorianCalendar date = new GregorianCalendar();
		return new Date(date);
	}
}
