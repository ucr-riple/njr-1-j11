package logfilegen;

import java.text.ParseException;

import logfilegen.allmodels.Log;
import logfilegen.allparsers.LogPars;
import logfilegen.forFile.Reader;

public class Parser {
	public Log parser(String path) throws ParseException{
		Reader file = new Reader(path);
		String logStr = file.read();
		LogPars logPars = new LogPars();
		
		
		return logPars.parser(logStr);
	}
}
