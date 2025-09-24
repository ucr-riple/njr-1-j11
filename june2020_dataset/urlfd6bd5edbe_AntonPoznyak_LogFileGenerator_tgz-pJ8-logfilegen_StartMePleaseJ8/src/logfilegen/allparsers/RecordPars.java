package logfilegen.allparsers;

import java.text.ParseException;

import logfilegen.allmodels.Record;

public class RecordPars {
private ParserIp parserIp = new ParserIp();
	private ParserRequest parserRequest = new ParserRequest();
	private ParserSize parserSize = new ParserSize();
	private ParserStatus parserStatus = new ParserStatus();
	private ParserDate parserDate = new ParserDate();
	
	
	public Record parser(String recordStr) throws ParseException{
		Record record = new Record();
		String[] split= recordStr.split(" - \\[",2);
		record.setIp(parserIp.parser(split[0]));
		
		split = split[1].split("] \"", 2);
		record.setTime(parserDate.parser(split[0]));
		
		split = split[1].split("\" ", 2);
		record.setRequest(parserRequest.parser(split[0]));
		
		split = split[1].split(" ", 2);
		record.setStatus(parserStatus.parser(split[0]));
		
		record.setSize(parserSize.parser(split[1]));
		
		
		return record;
	}
}
