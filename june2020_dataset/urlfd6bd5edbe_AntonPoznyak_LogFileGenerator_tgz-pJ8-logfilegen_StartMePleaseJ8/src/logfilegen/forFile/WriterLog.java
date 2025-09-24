package logfilegen.forFile;

import logfilegen.allconverters.LogConverter;
import logfilegen.allmodels.Log;

public class WriterLog {
	public void record(Log log ,String path){
		LogConverter logConv = new LogConverter(log);
		WriterFile fileWriter = new WriterFile(path, logConv.convertToString());
		fileWriter.write();
	}
}
