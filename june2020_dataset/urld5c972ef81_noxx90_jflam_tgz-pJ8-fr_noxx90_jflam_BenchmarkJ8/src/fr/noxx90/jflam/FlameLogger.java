package fr.noxx90.jflam;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.esotericsoftware.minlog.Log.Logger;

public class FlameLogger extends Logger {
	private SimpleDateFormat format;
	private PrintWriter writer;
	
	public FlameLogger() {
		format = new SimpleDateFormat("HH:mm:ss");
		try {
			writer = new PrintWriter("log");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void log (int level, String category, String message, Throwable ex) {
        StringBuilder builder = new StringBuilder(256);
        builder.append(format.format(new Date()));
        
        switch (level) {
		case 5:
			builder.append(" ERROR: ");
			break;
		case 4:
			builder.append("  WARN: ");
			break;
		case 3:
			builder.append("  INFO: ");
			break;
		case 2:
			builder.append(" DEBUG: ");
			break;
		case 1:
			builder.append(" TRACE: ");
			break;
		}
        
        builder.append('[');
        builder.append(category);
        builder.append("] ");
        builder.append(message);
        if (ex != null) {
                StringWriter writer = new StringWriter(256);
                ex.printStackTrace(new PrintWriter(writer));
                builder.append('\n');
                builder.append(writer.toString().trim());
        }
        
        if(level <= 2) {
        	writer.println(builder);        	
        } else {
        	System.out.println(builder);
        }
	}
}
