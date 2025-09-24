package net.jcores.script.util.console;

import static net.jcores.jre.CoreKeeper.$;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import net.jcores.jre.interfaces.functions.F0;

/**
 * We use this console to output what has been printed on the actual console ...
 * 
 * @author Ralf Biedert
 */
public class JCoresConsole extends JFrame {
	/** */
	private static final long serialVersionUID = -6393982993147415420L;

	/** Logging area */
	protected JTextArea textArea;

	/** Scroll pane */
	protected JScrollPane scrollPane;

	/** The title / banner for our window */
	protected String banner;

	/**
	 * Constructs a console.
	 * 
	 * @param title
	 */
	public JCoresConsole(String title) {
		this.banner = title;
		
		setTitle(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 400);

		this.textArea = new JTextArea();
		this.textArea.setBackground(Color.BLACK);
		this.textArea.setForeground(Color.LIGHT_GRAY);
		this.textArea.setEditable(false);
		this.textArea.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.scrollPane = new JScrollPane(this.textArea);
		this.scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(this.scrollPane, BorderLayout.CENTER);

		// Redirect the std. streams.
		redirectSystemStreams();

		setVisible(true);
	}

	public void addTerminationHook(final Thread thread) {
		$.sys.manyTimes(new F0() {
			boolean warned = false;

			@Override
			public void f() {
				if (!thread.isAlive() && !this.warned) {
					System.out.println("[Terminated]");
					this.warned = true;
				}
			}
		}, 500);
	}

	/**
	 * Shamelessly stolen from
	 * http://unserializableone.blogspot.com/2009/01/redirecting
	 * -systemout-and-systemerr-to.html
	 */
	void updateTextArea(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JCoresConsole.this.textArea.append(text);
			}
		});
	}

	/**
	 * Shamelessly stolen from
	 * http://unserializableone.blogspot.com/2009/01/redirecting
	 * -systemout-and-systemerr-to.html
	 */
	private void redirectSystemStreams() {
		OutputStream out = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				updateTextArea(String.valueOf((char) b));
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				updateTextArea(new String(b, off, len));
			}

			@Override
			public void write(byte[] b) throws IOException {
				write(b, 0, b.length);
			}
		};

		System.setOut(new PrintStream(out, true));
		System.setErr(new PrintStream(out, true));
	}
}
