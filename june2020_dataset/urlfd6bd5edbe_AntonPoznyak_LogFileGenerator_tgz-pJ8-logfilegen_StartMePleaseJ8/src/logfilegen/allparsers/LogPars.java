package logfilegen.allparsers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import logfilegen.allmodels.Log;
import logfilegen.allmodels.Record;

public class LogPars {
	private RecordPars recordPars = new RecordPars();
	
	
	public Log parser(String logStr) throws ParseException{
		List<Record> records = new ArrayList<Record>();
		String[] recordStr2= logStr.split(System.getProperty("line.separator"));
		
		for(String recordStr : recordStr2){
			recordPars = new RecordPars();
			records.add(recordPars.parser(recordStr));
			
		}
		
		
		
		return new Log(records);
	}
}
