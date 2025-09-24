package logfilegen.allconverters;

import logfilegen.allmodels.Log;
import logfilegen.allmodels.Record;

public class LogConverter {
	private Log log;
	private final String NewL = System.getProperty("line.separator");
	
	public LogConverter(Log log){
		this.log = log;
	}
	
	public String convertToString(){
		StringBuilder builder = new StringBuilder();
		for(Record record: log.getRecords()){
			builder.append(record.toString());
			builder.append(NewL);
		}
		
		return builder.toString();
	}
}
