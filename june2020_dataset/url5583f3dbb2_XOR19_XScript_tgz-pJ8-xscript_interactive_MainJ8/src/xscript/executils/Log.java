package xscript.executils;

import java.io.PrintWriter;
import java.util.IllegalFormatException;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;

public class Log implements DiagnosticListener<String>{

	public enum Kind{
		NOTICE,
		WARNING,
		ERROR
	}
	
	private PrintWriter errWriter;

	private PrintWriter warnWriter;

	private PrintWriter noticeWriter;
	
	private Kind last;
	
	private Localizer lang;
	
	public Log(PrintWriter errWriter, PrintWriter warnWriter, PrintWriter noticeWriter, Localizer lang){
		this.errWriter = errWriter;
		this.warnWriter = warnWriter;
		this.noticeWriter = noticeWriter;
		this.lang = lang;
	}
	
	public Log(Localizer lang){
		this(new PrintWriter(System.err), new PrintWriter(System.err), new PrintWriter(System.out), lang);
	}
	
	public void println(){
		checkLast(Kind.NOTICE);
		noticeWriter.println();
	}
	
	public void println(String key, Object...args) {
		rawprintln(localize(key, args));
	}
	
	public void print(String key, Object...args) {
		rawprint(localize(key, args));
	}

	public void rawprintln(String message, Object...args) {
		rawprintln(format(message, args));
	}
	
	public void rawprint(String message, Object...args) {
		rawprint(format(message, args));
	}
	
	public void rawprintln(String message) {
		checkLast(Kind.NOTICE);
		rawprintln(noticeWriter, message);
	}
	
	public void rawprint(String message) {
		checkLast(Kind.NOTICE);
		rawprint(noticeWriter, message);
	}

	public String localize(String key){
		return lang.localize(key);
	}
	
	public String localize(String key, Object...args){
		return lang.localize(key, args);
	}
	
	public PrintWriter getWriter(Kind kind){
		switch(kind){
		case ERROR:
			return errWriter;
		case WARNING:
			return warnWriter;
		case NOTICE:
		default:
			return noticeWriter;
		}
	}
	
	public void rawprintln(Kind kind, String message) {
		checkLast(kind);
		rawprintln(getWriter(kind), message);
	}
	
	public void rawprint(Kind kind, String message) {
		checkLast(kind);
		rawprint(getWriter(kind), message);
	}
	
	private void checkLast(Kind kind){
		if(last!=kind){
			if(last!=null){
				getWriter(last).flush();
			}
			last = kind;
		}
	}
	
	public static void rawprintln(PrintWriter writer, String msg) {
		int nl;
        while ((nl = msg.indexOf('\n')) != -1) {
            writer.println(msg.substring(0, nl));
            msg = msg.substring(nl+1);
        }
        if (msg.length() != 0) writer.println(msg);
	}
	
	public static void rawprint(PrintWriter writer, String msg) {
		int nl;
        while ((nl = msg.indexOf('\n')) != -1) {
            writer.println(msg.substring(0, nl));
            msg = msg.substring(nl+1);
        }
        if (msg.length() != 0) writer.print(msg);
	}
	
	@Override
	public void report(Diagnostic<? extends String> diagnostic) {
		String message;
		Kind kind;
		switch(diagnostic.getKind()){
		case ERROR:
			kind = Kind.ERROR;
			break;
		case MANDATORY_WARNING:
		case WARNING:
			kind = Kind.WARNING;
			break;
		case NOTE:
		case OTHER:
		default:
			kind = Kind.NOTICE;
			break;
		}
		message = diagnostic.getMessage(null);
		rawprintln(kind, message);
	}

    public static String format(String message, Object...args){
    	try{
    		return String.format(message, args);
    	}catch(IllegalFormatException e){
    		return message;
    	}
    }

	public void flush() {
		checkLast(null);
	}
	
}
