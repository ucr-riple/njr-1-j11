package nl.rug.peerbox.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

public class MenuButton extends Canvas implements DisposeListener {
	private boolean active = false;
	private int mouse = 0;
	private boolean hit = false;
	private final Color bg;
	private final Color fg;
	private final Color topLine;
	private final Color bottomLine;
	private final Color activebg;
	private final Color activefg;
	private final Color activeBottomLine;
	private final Color activeTopLine;
	private final Font font;

	private String text = "";
		
	public MenuButton(Composite parent, int style) {
		super(parent, style);

		addDisposeListener(this);
		Display display = Display.getCurrent();
		bg = new Color(display, new RGB(52, 52, 52));
		fg = new Color(display, new RGB(180, 180, 180));
		bottomLine = new Color(display, new RGB(22, 22, 22));
		topLine = new Color(display, new RGB(82, 82, 82));

		activebg = new Color(display, new RGB(32, 32, 32));
		activefg = new Color(display, new RGB(220, 220, 220));
		activeBottomLine = new Color(display, new RGB(22, 22, 22));
		activeTopLine = new Color(display, new RGB(22, 22, 22));

		font = new Font(display, "Arial", 14, SWT.BOLD);
		setFont(font);
		setBackground(bg);
		setForeground(fg);

		this.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				Point textSize = e.gc.textExtent(text);
				if (!active) {
					setBackground(bg);
					gc.setForeground(fg);
					e.gc.drawText(text, (getSize().x - textSize.x) / 2,
							(getSize().y - textSize.y) / 2);
					gc.setForeground(topLine);
					gc.drawLine(0, 0, getSize().x, 0);
					gc.setForeground(bottomLine);
					gc.drawLine(0, getSize().y - 1, getSize().x,
							getSize().y - 1);
				} else {
					setBackground(activebg);
					gc.setForeground(activefg);
					e.gc.drawText(text, (getSize().x - textSize.x) / 2,
							(getSize().y - textSize.y) / 2);
					gc.setForeground(activeTopLine);
					gc.drawLine(0, 0, getSize().x, 0);
					gc.setForeground(activeBottomLine);
					gc.drawLine(0, getSize().y - 1, getSize().x,
							getSize().y - 1);
				}
				gc.dispose();
			}
		});

		this.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				if (!hit)
					return;
				mouse = 2;
				if (e.x < 0 || e.y < 0 || e.x > getBounds().width
						|| e.y > getBounds().height) {
					mouse = 0;
				}
			}
		});

		this.addMouseTrackListener(new MouseTrackAdapter() {
			public void mouseEnter(MouseEvent e) {
				mouse = 1;
			}

			public void mouseExit(MouseEvent e) {
				mouse = 0;
			}
		});
		this.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				hit = true;
				mouse = 2;
			}

			public void mouseUp(MouseEvent e) {
				hit = false;
				mouse = 1;
				if (e.x < 0 || e.y < 0 || e.x > getBounds().width
						|| e.y > getBounds().height) {
					mouse = 0;
				}
				if (mouse == 1)
					notifyListeners(SWT.Selection, new Event());
			}
		});

		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == '\r' || e.character == ' ') {
					Event event = new Event();
					notifyListeners(SWT.Selection, event);
				}
			}
		});
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setActive(boolean active) {
		if (this.active != active) {
			this.active = active;
			redraw();
		}
	}

	public boolean getActive() {
		return active;
	}

	@Override
	public Point computeSize(int whint, int hhint, boolean changed) {
		super.computeSize(whint, hhint, changed);
		Point size = new Point(0, 0);
		if (whint == SWT.DEFAULT) {
			size.x = 75;
		} else {
			size.x = (whint < 50) ? 50 : whint;
		}
		if (hhint == SWT.DEFAULT) {
			size.y = 35;
		} else {
			size.y = (hhint < 25) ? 25 : whint;
		}
		return size;

	}

	@Override
	public void widgetDisposed(DisposeEvent arg0) {
		bg.dispose();
	}

}
