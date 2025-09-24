package logfilegen.allmodels;

import java.util.List;
import java.util.ArrayList;

public class Log {
	private List<Record> records = new ArrayList<Record>();
	
	private final String NewL = System.getProperty("line.separator");
	
	public String getNew(){
		return NewL;
	}
	
	public Log(){}
	
	public Log(List<Record> records){
		this.records = records;
	}

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for(Record record:records){
			builder.append(record.toString());
			builder.append(NewL);
		}
		return builder.toString();
	}
}
