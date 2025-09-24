package logfilegen;

import java.io.IOException;
import java.text.ParseException;

import logfilegen.allmodels.Log;

public class StartMePlease {

	public static void main(String[] args) throws IOException, ParseException{
		int size = 30;
		String file = "LogFile.log";
		
		
		LogFileGen logFileGen = new LogFileGen();
		logFileGen.gen(file,size);
		
		Parser parser = new Parser();
		Log parserLog = parser.parser(file);
		
		Analytic analytic = new Analytic();
		System.out.println(analytic.txtAnalizator(file));
		System.out.print("end");
	}

}
