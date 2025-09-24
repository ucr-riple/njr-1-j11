package nl.rug.peerbox.ui;

import nl.rug.peerbox.logic.Context;
import nl.rug.peerbox.logic.PeerHost;
import nl.rug.peerbox.logic.PeerListener;
import nl.rug.peerbox.logic.Peerbox;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class PeerListView extends Composite implements DisposeListener, PeerListener {

	private final Font title;
	private final Color foreground;
	private final Context ctx;
	private final Composite content;
	private ScrolledComposite scrollable;
	private static final Logger logger = Logger
			.getLogger(FileListView.class);

	public PeerListView(Composite c) {
		super(c, SWT.NONE);
		addDisposeListener(this);
		
		this.ctx = Peerbox.getInstance();
		

		Display display = Display.getCurrent();
		title = new Font(display, "Arial", 13, SWT.NORMAL);
		foreground = new Color(display, 75, 75, 75);
		setFont(title);
		setForeground(foreground);

		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.drawText("Detected Peers", 20, 15);
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

		
		this.ctx.addPeerListener(this);
		for (PeerHost ph : ctx.getPeers()) {
			GridData textData = new GridData();
			textData.grabExcessHorizontalSpace = true;
			textData.horizontalAlignment = GridData.FILL;
			PeerView pv = new PeerView(content);
			pv.setModel(ph);
			pv.setLayoutData(textData);
		}

		content.layout();
		Rectangle r = scrollable.getClientArea();
		content.setSize(content.computeSize(r.width, SWT.DEFAULT));content.layout();

	}

	@Override
	public void widgetDisposed(DisposeEvent de) {
		ctx.removePeerListener(this);
		title.dispose();
		foreground.dispose();
	}




	@Override
	public void updated(final PeerHost ph) {
		final PeerListView parent = this;
		this.getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				GridData textData = new GridData();
				textData.grabExcessHorizontalSpace = true;
				textData.horizontalAlignment = GridData.FILL;
				boolean found = false;
				for (Control c : parent.content.getChildren()) {
					if (c instanceof PeerView) {
						PeerView pv = (PeerView) c;
						if (pv.getModel().getHostID() == ph.getHostID()) {
							pv.setModel(ph);
							 found = true;
							 break;
						}
					}
				}
				if (!found) {
					PeerView pv = new PeerView(parent.content);
					pv.setModel(ph);
					pv.setLayoutData(textData);
				}
				
				content.layout();
				Rectangle r = scrollable.getClientArea();
				content.setSize(content.computeSize(r.width, SWT.DEFAULT));
			}
		});
		
	}

	@Override
	public void deleted(final PeerHost ph) {
		final PeerListView plv = this;
		this.getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				for (Control c : plv.content.getChildren()) {

					if (c instanceof PeerView) {
						PeerView pv = (PeerView) c;
						if (pv.getModel().getHostID() == ph.getHostID()) {
							pv.dispose();
						}
					}
				}
				content.layout();
				Rectangle r = scrollable.getClientArea();
				content.setSize(content.computeSize(r.width, SWT.DEFAULT));

			}
		});
		
	}

	@Override
	public void joined(final PeerHost ph) {
		final PeerListView parent = this;
		this.getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				GridData textData = new GridData();
				textData.grabExcessHorizontalSpace = true;
				textData.horizontalAlignment = GridData.FILL;
				boolean found = false;
				for (Control c : parent.content.getChildren()) {
					if (c instanceof PeerView) {
						PeerView pv = (PeerView) c;
						if (pv.getModel().getHostID() == ph.getHostID()) {
							pv.setModel(ph);
							 found = true;
							 break;
						}
					}
				}
				if (!found) {
					PeerView pv = new PeerView(parent.content);
					pv.setModel(ph);
					pv.setLayoutData(textData);
				}
				
				content.layout();
				Rectangle r = scrollable.getClientArea();
				content.setSize(content.computeSize(r.width, SWT.DEFAULT));
			}
		});
		
	}
	
}
