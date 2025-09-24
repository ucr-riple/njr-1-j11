package xscript.executils.console;

import java.awt.Color;
import java.awt.Font;
import java.io.InputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class Console extends JFrame{
	
	private static final long serialVersionUID = -3055955089190283534L;

	private JWrappableTextPane textPane;
	
	private JScrollPane scrollPane;
	
	private PrintStream out;
	
	private PrintStream err;
	
	private InputStream in;
	
	private SimpleAttributeSet errorAttrs;
    
    private SimpleAttributeSet inputAttrs;
    
    private SimpleAttributeSet defaultAttrs;
	
    private ConsoleIO io;
    
	public Console(String title) {
		super(title);
		errorAttrs = new SimpleAttributeSet();
        StyleConstants.setForeground(errorAttrs, new Color(180, 0, 0));
        inputAttrs = new SimpleAttributeSet();
        StyleConstants.setForeground(inputAttrs, new Color(51, 102, 0));
        defaultAttrs = new SimpleAttributeSet();
		DefaultStyledDocument sd = new DefaultStyledDocument();
		io = new ConsoleIO(inputAttrs);
		sd.setDocumentFilter(new ConsoleDocumentFilter(io));
		textPane = new JWrappableTextPane(sd);
		textPane.setLineWrap(true);
		textPane.setBorder(null);
		textPane.setBackground(Color.BLACK);
		textPane.setForeground(Color.WHITE);
		textPane.setCaretColor(Color.WHITE);
		textPane.setSelectedTextColor(Color.BLACK);
		textPane.setSelectionColor(Color.WHITE);
		io.textPane = textPane;
		textPane.setFont(new Font(Font.MONOSPACED, 0, 12));
		scrollPane = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(null);
		setContentPane(scrollPane);
		setSize(600, 300);
		out = getOutWithAttrs(defaultAttrs);
		err = getOutWithAttrs(errorAttrs);
		in = new ConsoleInputStream(io);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationByPlatform(true);
		setVisible(true);
	}

	public PrintStream getOut() {
		return out;
	}

	public PrintStream getErr() {
		return err;
	}

	public InputStream getIn() {
		return in;
	}
	
	public PrintStream getOutWithAttrs(AttributeSet attrs){
		return new PrintStream(new ConsoleOutputStream(io, attrs));
	}
	
}