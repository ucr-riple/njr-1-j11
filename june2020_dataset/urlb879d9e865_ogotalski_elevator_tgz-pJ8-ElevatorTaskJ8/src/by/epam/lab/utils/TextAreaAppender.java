package by.epam.lab.utils;

import javax.swing.JTextArea;

import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

public class TextAreaAppender extends WriterAppender {
	
	private JTextArea textArea = null;
	
	
	public TextAreaAppender(JTextArea textArea) {
		super();
		this.textArea = textArea;
		this.setThreshold(Level.INFO);
		final String PATTERN = " %m%n";
		this.setLayout(new PatternLayout(PATTERN));
	}
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
	public synchronized void append(LoggingEvent loggingEvent) {
		String message = this.layout.format(loggingEvent);
        textArea.append(message);
        final int length = textArea.getText().length();
        textArea.setCaretPosition(length);
		
	}
}
