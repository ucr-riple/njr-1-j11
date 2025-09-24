package controllers.utils;

import java.io.FileWriter;
import java.io.IOException;

/**
 * This class specializes in writing logs to the indicated files in the program
 * @author wj
 * @date	created on 2013/07/18
 * @date	modified on 2013/07/18
 *
 */
public class Logger {
	private String logFile;
	/**
	 * Defines if the logs should be writen as addition of the indicated file, or as replacement of the file
	 */
	private boolean append;
	private FileWriter fstream;
	
	private void initialize(){
		try {
			fstream = new FileWriter(logFile, append);
		} catch (IOException e) {
			System.err.println("Log initialization error in file "+logFile);
			e.printStackTrace();
		}
	}
	
	public Logger(String fname, boolean ap){
		logFile = fname;
		append = ap;
		initialize();
	}
	
	public Logger(String fname){
		logFile = fname;
		append = false;
		initialize();
	}

	public <T> void write(String line){
		try {
			fstream.append(line+"\n");
			fstream.flush();
		} catch (IOException e) {
			System.err.println("Log writing error in file "+logFile);
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		Logger l = new Logger("abc", true);
		l.write("abccdefe");
		l.write("line2");
	}
	
}
