package logfilegen.allgenerators;

import java.util.List;
import java.util.ArrayList;

import logfilegen.allmodels.Log;
import logfilegen.allmodels.Record;

public class LogGen {
	public Log generate(int n){
		List<Record> records = new ArrayList<Record>();
		RecordGen recordGen = new RecordGen();
		for (int i=0; i<n; i++){
			records.add(recordGen.generate());
		}
		
		
		return new Log(records);
	}
}
