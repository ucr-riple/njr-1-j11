package ch.zhaw.regularLanguages.helpers;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Logger {
	private List<Tuple<Date, String>> log;
	
	private boolean verbose;
	private long start;
	private PrintWriter writer = null;
	
	public Logger(String filePath, boolean verbose){
		this.start = System.currentTimeMillis();
		this.verbose = verbose;
		
		try {
			writer = new PrintWriter(filePath, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.log = new LinkedList<Tuple<Date, String>>();
	}
	
	public void log(String entry){
		Tuple<Date, String> t = new Tuple<Date, String>(new Date(), entry);
		log.add(t);
		
		if(writer != null){
			writer.println(t);
		}
		
		if(verbose){
			System.out.println(t);
		}
	}
	
	public void finish(){
		Tuple<Date, String> t = new Tuple<Date, String>(new Date(), new String("logging finished. (runtime: " + (System.currentTimeMillis()-start) + "ms)"));
		log.add(t);
		if(writer != null){
			writer.println(t);
		}
		writer.close();
	}
	
	@Override
	protected void finalize(){
		writer.close();
	}
}
