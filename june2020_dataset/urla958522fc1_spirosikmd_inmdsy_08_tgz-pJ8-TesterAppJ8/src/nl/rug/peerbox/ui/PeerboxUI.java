package nl.rug.peerbox.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class PeerboxUI implements DisposeListener, Listener {

	private final Shell shell;
	private Composite content;

	private Image menubg;
	private Color background;

	private StackLayout contentLayout;
	private PeerListView peers;
	private LogView log;
	private FileListView files;

	private MenuButton logButton;
	private MenuButton filesButton;
	private MenuButton peersButton;
	private MenuButton changeButton;

	public PeerboxUI(Display display, int style) {
		shell = new Shell(display, style);
		init();
	}

	public PeerboxUI(Display display) {
		shell = new Shell(display);
		init();
	}

	private void init() {

		shell.addDisposeListener(this);

		GridLayout layout = new GridLayout();
		layout.numColumns = 5;
		layout.makeColumnsEqualWidth = false;
		layout.marginTop = 0;
		layout.marginLeft = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = 0;

		Display display = Display.getCurrent();
		background = display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);

		menubg = new Image(display, "resources/menu.png");
		shell.setBackground(background);
		shell.setLayout(layout);

		GridData menuData = new GridData();
		menuData.horizontalSpan = 1;
		menuData.grabExcessVerticalSpace = true;
		menuData.verticalAlignment = GridData.FILL;
		menuData.horizontalIndent = 0;
		menuData.widthHint = 101;

		Composite menu = new Composite(shell, SWT.NONE);
		menu.setBackgroundImage(menubg);
		menu.setLayoutData(menuData);

		GridLayout menuLayout = new GridLayout();
		menuLayout.numColumns = 1;
		menuLayout.marginTop = 10;
		menuLayout.marginRight = 1;
		menuLayout.marginWidth = 0;
		menuLayout.verticalSpacing = 0;
		menu.setLayout(menuLayout);

		GridData contentData = new GridData();
		contentData.horizontalAlignment = GridData.FILL;
		contentData.grabExcessHorizontalSpace = true;
		contentData.horizontalSpan = 4;
		contentData.grabExcessVerticalSpace = true;
		contentData.verticalAlignment = GridData.FILL;
		contentData.horizontalIndent = 0;

		content = new Composite(shell, SWT.NONE);
		content.setLayoutData(contentData);
		contentLayout = new StackLayout();
		contentLayout.marginHeight = 10;
		contentLayout.marginWidth = 20;
		content.setLayout(contentLayout);

		log = new LogView(content);
		files = new FileListView(content);
		peers = new PeerListView(content);
		contentLayout.topControl = files;
		content.layout();

		filesButton = new MenuButton(menu, SWT.NONE);
		filesButton.setText("Files");
		filesButton.setLayoutData(createButtonGridData());
		filesButton.addListener(SWT.Selection, this);

		peersButton = new MenuButton(menu, SWT.NONE);
		peersButton.setText("Peers");
		peersButton.setLayoutData(createButtonGridData());
		peersButton.addListener(SWT.Selection, this);
		
		changeButton = new MenuButton(menu, SWT.NONE);
		changeButton.setText("Changes");
		changeButton.setLayoutData(createButtonGridData());
		changeButton.addListener(SWT.Selection, this);

		logButton = new MenuButton(menu, SWT.NONE);
		logButton.setText("Log");
		GridData logButtonData = createButtonGridData();
		logButtonData.verticalAlignment = GridData.END;
		logButtonData.grabExcessVerticalSpace = true;
		logButton.setLayoutData(logButtonData);
		logButton.addListener(SWT.Selection, this);

		shell.pack();
		shell.setSize(600, 400);
		shell.setMinimumSize(new Point(400, 200));

	}

	private GridData createButtonGridData() {
		GridData requestButtonGridData = new GridData();
		requestButtonGridData.horizontalAlignment = GridData.FILL;
		requestButtonGridData.grabExcessHorizontalSpace = true;
		requestButtonGridData.widthHint = 75;
		return requestButtonGridData;
	}

	public Shell getShell() {
		return shell;
	}

	@Override
	public void widgetDisposed(DisposeEvent de) {
		menubg.dispose();
		background.dispose();
	}

	@Override
	public void handleEvent(Event e) {
		Object s = e.widget;
		if (e.type == SWT.Selection) {
			if (s == logButton) {
				logButton.setActive(true);
				filesButton.setActive(false);
				peersButton.setActive(false);
				contentLayout.topControl = log;
			}
			if (s == filesButton) {
				logButton.setActive(false);
				filesButton.setActive(true);
				peersButton.setActive(false);
				contentLayout.topControl = files;
			}
			if (s == peersButton) {
				logButton.setActive(false);
				filesButton.setActive(false);
				peersButton.setActive(true);
				contentLayout.topControl = peers;
			}
			content.layout();
		}
	}

}
