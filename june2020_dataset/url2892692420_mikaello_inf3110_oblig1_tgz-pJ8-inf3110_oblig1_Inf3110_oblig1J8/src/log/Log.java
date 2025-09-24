package log;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * This class is for logging the program, more specifically the 
 * pretty printing. It can write to screen, or it can write to 
 * file (or both).
 * 
 * @author mikaello
 */
public class Log {
    private static String logName = "prettyPrint";
    
    private static StringBuilder sb = new StringBuilder();
    
    /**
     * Append a string to the log.
     * @param text string to be appended
     */
    public static void writeLogLine(String text) {
        sb.append("PrettyPrint: ");
        
        // This for-loop is necessary because of texts
        // that exceed one line
        for(int i = 0; i < text.length(); i++) {  
            // Traverse whole string 
            char c = text.charAt(i); 
            if (c != '\n') {
                // Regular character
                sb.append(c);
            } else {
                // New line, then we add PrettyPrint
                sb.append("\nPrettyPrint: ");                
            }
        }

        sb.append("\n");
    }
    
    /**
     * Prints the current log to screen. Does not edit the log, 
     * just printing to screen.
     */
    public static void printLog() {
        System.out.println();
        System.out.println(sb.toString());
    }
    
    /**
     * Writes the whole log to file. Filename is specified by 
     * variable logName. Does not edit log, just writing to file.
     */
    public static void writeLogToFile() {
        try {
            PrintWriter log = new PrintWriter(logName + ".log");
	    log.println(sb.toString());
	    log.close();
	} catch (FileNotFoundException e) {
	    System.err.println("Cannot open log file " + logName + "!");
            System.exit(1);
	}

    }
}
