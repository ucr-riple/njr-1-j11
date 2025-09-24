package nl.rug.peerbox.ui;

import nl.rug.peerbox.logic.Context;
import nl.rug.peerbox.logic.Peerbox;
import nl.rug.peerbox.logic.filesystem.PeerboxFile;
import nl.rug.peerbox.logic.filesystem.VFSListener;
import nl.rug.peerbox.logic.filesystem.VirtualFileSystem;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class FileListView extends Composite implements DisposeListener,
		VFSListener, SelectionListener {

	private final Font title;
	private final Color foreground;
	private final Context peerbox;
	private final Composite content;
	private ScrolledComposite scrollable;
	private static final Logger logger = Logger
			.getLogger(FileListView.class);

	public FileListView(Composite c) {

		super(c, SWT.NONE);
		addDisposeListener(this);
		this.peerbox = Peerbox.getInstance();
		this.peerbox.getVirtualFilesystem().addVFSListener(this);

		Display display = Display.getCurrent();
		title = new Font(display, "Arial", 13, SWT.NORMAL);
		foreground = new Color(display, 75, 75, 75);
		setFont(title);
		setForeground(foreground);

		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.drawText("Shared Files", 20, 15);
				e.gc.dispose();
			}
		});

		GridLayout layout = new GridLayout();
		layout.marginTop = 40;
		layout.marginLeft = 20;
		layout.numColumns = 1;
		setLayout(layout);


		scrollable = new ScrolledComposite(this, SWT.V_SCROLL | SWT.H_SCROLL);
		scrollable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		scrollable.setLayout(new GridLayout(1, true));
		content = new Composite(scrollable, SWT.NONE);
		content.setSize(400, 400);
		content.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		content.setLayout(new GridLayout(1, true));

		scrollable.setContent(content);
		scrollable.pack();
		Rectangle r = scrollable.getClientArea();
		content.setSize(content.computeSize(r.width, SWT.DEFAULT));
		scrollable.addControlListener(new ControlListener() {

			@Override
			public void controlResized(ControlEvent arg0) {
				Rectangle r = scrollable.getClientArea();
				content.setSize(content.computeSize(r.width - 10, SWT.DEFAULT));
			}

			@Override
			public void controlMoved(ControlEvent arg0) {
			}
		});

		VirtualFileSystem vfs = Peerbox.getInstance().getVirtualFilesystem();
		for (PeerboxFile file : vfs.getFileList()) {
			GridData textData = new GridData();
			textData.grabExcessHorizontalSpace = true;
			textData.horizontalAlignment = GridData.FILL;
			FileView fv = new FileView(content);
			fv.setModel(file);
			fv.setLayoutData(textData);
		}

		content.layout();
		
		Button request = new Button(this, SWT.FLAT);
		request.setText("Request");
		request.addSelectionListener(this);
		request.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));

	}

	@Override
	public void widgetDisposed(DisposeEvent de) {
		this.peerbox.getVirtualFilesystem().removeVFSListener(this);
		title.dispose();
		foreground.dispose();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent se) {
	}

	@Override
	public void widgetSelected(SelectionEvent se) {
		peerbox.getVirtualFilesystem().refresh();
	}

	@Override
	public void updated(final PeerboxFile f) {
	}

	@Override
	public void added(final PeerboxFile f) {
		final FileListView parent = this;
		this.getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				GridData textData = new GridData();
				textData.grabExcessHorizontalSpace = true;
				textData.horizontalAlignment = GridData.FILL;
				FileView fv = new FileView(parent.content);
				fv.setModel(f);
				fv.setLayoutData(textData);

				content.layout();
				Rectangle r = scrollable.getClientArea();
				content.setSize(content.computeSize(r.width, SWT.DEFAULT));
			}
		});

	}

	@Override
	public void deleted(final PeerboxFile f) {
		final FileListView sfv = this;
		this.getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				for (Control c : sfv.content.getChildren()) {

					if (c instanceof FileView) {
						FileView fv = (FileView) c;
						if (fv.getModel().equals(f)) {
							fv.dispose();
						}
					}
				}
				content.layout();
				Rectangle r = scrollable.getClientArea();
				content.setSize(content.computeSize(r.width, SWT.DEFAULT));

			}
		});
	}

}
