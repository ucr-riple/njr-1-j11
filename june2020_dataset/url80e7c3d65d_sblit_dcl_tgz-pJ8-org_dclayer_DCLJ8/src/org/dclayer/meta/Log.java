package org.dclayer.meta;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * logging class used for message output
 */
public class Log {
	
	/**
	 * log levels
	 */
	private enum Level {
		DEBUG, MSG, WARNING, ERROR, FATAL
	};
	
	public static interface Output {
		public void println(String string);
	}
	
	private static class IgnoreEntry {
		Level belowLevel;
		Class[] reversePath;
		public IgnoreEntry(Level belowLevel, Class... reversePath) {
			this.belowLevel = belowLevel;
			this.reversePath = reversePath;
		}
	}
	
	public static final IgnoreEntry[] IGNORE = new IgnoreEntry[] {
		// specify reversed paths here (e.g. { InterserviceChannel.class, DCLService.class, DCL.class })
		// if the end of a log message's path matches one of the arrays below reversed, the message is printed only if
		// the log level of the message is equal to or higher than the level specified here
		
////		new IgnoreEntry(Level.DEBUG, InterserviceChannel.class), // show all log messages from InterserviceChannel instances only
//		new IgnoreEntry(Level.WARNING, UDPSocket.class),
//		new IgnoreEntry(Level.WARNING, FlowControl.class),
//		new IgnoreEntry(Level.WARNING, ResendPacketQueue.class),
//		new IgnoreEntry(Level.WARNING, PacketBackupCollection.class),
//		new IgnoreEntry(Level.WARNING, DiscontinuousBlockCollection.class),
//		new IgnoreEntry(Level.MSG, Slot.class),
//		new IgnoreEntry(Level.MSG, Channel.class),
//		new IgnoreEntry(Level.MSG, Link.class),
	};
	
	private static final Output OUTPUT = new Output() {
		@Override
		public void println(String string) {
			System.out.println(string);
		}
	};
	
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
	
	private static final HashMap<HierarchicalLevel, Output> outputs = new HashMap<>();
	
	public static void setOutput(HierarchicalLevel hierarchicalLevel, Output output) {
		outputs.put(hierarchicalLevel, output);
	}

	/**
	 * returns the stack trace of an Exception as String
	 * @param e the Exception
	 * @return the stack trace of the given Exception as String
	 */
	public static String getStackTraceAsString(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.flush();
		return sw.toString();
	}
	
	private static Output inflatePath(HierarchicalLevel hierarchicalLevel, LinkedList<HierarchicalLevel> levels) {
		
		Output output = OUTPUT;
		
		while(hierarchicalLevel != null) {
			
			Output o = outputs.get(hierarchicalLevel);
			if(o != null) output = o;
			
			levels.addFirst(hierarchicalLevel);
			hierarchicalLevel = hierarchicalLevel.getParentHierarchicalLevel();
			
		}
		
		return output;
		
	}
	
	private static String buildPath(LinkedList<HierarchicalLevel> levels) {
		StringBuilder stringBuilder = new StringBuilder();
		for(HierarchicalLevel level : levels) {
			stringBuilder.append("/");
			stringBuilder.append(level.toString());
		}
		return stringBuilder.toString();
	}
	
	private static boolean ignore(Level l, HierarchicalLevel hierarchicalLevel) {
		paths: for(IgnoreEntry ignoreEntry : IGNORE) {
			HierarchicalLevel hl = hierarchicalLevel;
			for(Class c : ignoreEntry.reversePath) {
				if(hl == null || !c.isAssignableFrom(hl.getClass())) continue paths;
				hl = hl.getParentHierarchicalLevel();
			}
			return l.ordinal() < ignoreEntry.belowLevel.ordinal();
		}
		return false;
	}
	
	private static void log(Level l, HierarchicalLevel hierarchicalLevel, String format, Object... args) {
		if(ignore(l, hierarchicalLevel)) return;
		LinkedList<HierarchicalLevel> levels = new LinkedList<>();
		Output output = inflatePath(hierarchicalLevel, levels);
		synchronized (output) {
			output.println(String.format("%s [%s] %s: %s",
					DATE_FORMAT.format(Calendar.getInstance().getTime()),
					l.name(),
					buildPath(levels),
					String.format(format, args)));
		}
	}
	
	public static void debug(HierarchicalLevel hierarchicalLevel, String format, Object... args) {
		log(Level.DEBUG, hierarchicalLevel, format, args);
	}
	
	public static void msg(HierarchicalLevel hierarchicalLevel, String format, Object... args) {
		log(Level.MSG, hierarchicalLevel, format, args);
	}
	
	public static void warning(HierarchicalLevel hierarchicalLevel, String format, Object... args) {
		log(Level.WARNING, hierarchicalLevel, format, args);
	}
	
	public static void exception(HierarchicalLevel hierarchicalLevel, Exception e, String format, Object... args) {
		log(Level.ERROR, hierarchicalLevel, format + ": " + getStackTraceAsString(e), args);
	}
	
	public static void exception(HierarchicalLevel hierarchicalLevel, Exception e) {
		log(Level.ERROR, hierarchicalLevel, "%s", getStackTraceAsString(e));
	}
	
}
