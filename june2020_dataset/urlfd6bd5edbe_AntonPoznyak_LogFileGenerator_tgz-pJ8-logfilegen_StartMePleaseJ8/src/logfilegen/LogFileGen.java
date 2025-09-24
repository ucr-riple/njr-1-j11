package logfilegen;

import java.io.IOException;

import logfilegen.allgenerators.LogGen;
import logfilegen.allmodels.Log;
import logfilegen.forFile.WriterLog;

public class LogFileGen {
	public void gen(String path, int n) throws IOException{
		Log log = new LogGen().generate(n);
		
		WriterLog writerLog = new WriterLog();
		writerLog.record(log, path);
	}
}
