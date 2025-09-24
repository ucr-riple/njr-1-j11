package nl.rug.peerbox.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

public class LogView extends Composite implements DisposeListener,
		TailerListener {

	private final Font title;
	private final Color foreground;
	private final static Logger logger = Logger.getLogger(LogView.class);
	private final Text logText;

	public LogView(Composite c) {
		super(c, SWT.NONE);
		GridLayout gridLayout = new GridLayout(1, true);
		gridLayout.marginTop = 40;
		gridLayout.marginLeft = 20;
		setLayout(gridLayout);
		Display display = Display.getCurrent();
		title = new Font(display, "Arial", 13, SWT.NORMAL);
		foreground = new Color(display, 75, 75, 75);
		setFont(title);
		setForeground(foreground);

		logText = new Text(this, SWT.V_SCROLL | SWT.BORDER | SWT.H_SCROLL);
		logText.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true,
				true));
		logText.setEditable(false);
		addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				e.gc.drawText("Log File", 20, 15);
				e.gc.dispose();
			}
		});

		try {
			File logFile = new File("peerbox.log");
			BufferedReader reader = new BufferedReader(new FileReader(logFile));
			String line;
			while ((line = reader.readLine()) != null) {
				if (logText.getText().isEmpty()) {
					logText.setText(line.replaceAll("^\\s+", ""));
				} else {
					logText.setText(logText.getText()
							+ System.getProperty("line.separator")
							+ line.replaceAll("^\\s+", ""));
				}
			}
			reader.close();
			Tailer tailer = new Tailer(logFile, this, 1000, true);
			Thread thread = new Thread(tailer);
			thread.setDaemon(true);
			thread.start();
		} catch (Exception e) {
			System.out.println(e);
		}

		Button clear = new Button(this, SWT.FLAT);
		clear.setText("Clear");
		clear.setLayoutData(new GridData(GridData.END, GridData.CENTER, false,
				false));
		clear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				logText.setText("");
			}
		});

	}

	@Override
	public void widgetDisposed(DisposeEvent arg0) {
		title.dispose();
		foreground.dispose();
	}

	@Override
	public void fileNotFound() {
		logger.error("Log file not found");

	}

	@Override
	public void fileRotated() {
		logger.info("Log file rotated");
	}

	@Override
	public void handle(final String text) {
		this.getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				if (!logText.isDisposed()) {
					if (logText.getText().isEmpty()) {
						logText.setText(text.replaceAll("^\\s+", ""));
					} else {
						logText.setText(logText.getText()
								+ System.getProperty("line.separator")
								+ text.replaceAll("^\\s+", ""));
					}
				}
			}
		});
	}

	@Override
	public void handle(Exception e) {
		logger.error(e);
	}

	@Override
	public void init(Tailer t) {
	}

}
