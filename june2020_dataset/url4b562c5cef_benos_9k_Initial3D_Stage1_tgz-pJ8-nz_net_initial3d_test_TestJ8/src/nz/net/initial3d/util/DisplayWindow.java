package nz.net.initial3d.util;

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * JFrame specialised for game-oriented active rendering. Mostly threadsafe.
 *
 * @author Ben Allen
 *
 */
public class DisplayWindow extends JFrame implements DisplayTarget {

	private static final long serialVersionUID = 1L;

	// misc
	private static final long FR_SAMPLE_HISTORY = 1000;
	private static final Point point_zero = new Point(0, 0);
	private final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

	// display
	private final Lock lock_display = new ReentrantLock();
	private Canvas canvas;
	private BufferStrategy bs;
	private long t_lastbad = 0;
	private final List<Long> ftimes = new LinkedList<Long>();
	private final List<Object> display_text = new ArrayList<Object>();

	// draw control
	private volatile boolean draw_crosshair = false;
	private volatile boolean draw_fps = true;
	private volatile boolean draw_text = true;

	// TODO check ranges of key / button ids

	// key data
	private final Lock lock_keys = new ReentrantLock();
	private final boolean[] activekeys = new boolean[1024];

	// mouse data
	private final Lock lock_mouse = new ReentrantLock();
	private final boolean[] activebuttons = new boolean[16];
	private volatile int mousetravelx = 0;
	private volatile int mousetravely = 0;
	private volatile int mousescrollclicks = 0;
	private volatile int mousex = 0;
	private volatile int mousey = 0;
	private volatile boolean mousecaptured = false;
	private volatile boolean prev_mousecaptured = false;

	// cursor to change to when making it visible
	private volatile Cursor oldcursor = Cursor.getDefaultCursor();
	// invisible cursor
	private final Cursor blankcursor;

	// mechanism for moving the mouse cursor
	private final Robot robot;

	// event dispatch control
	private volatile boolean events_synchronous = false;
	private final BlockingQueue<AWTEvent> event_queue = new LinkedBlockingQueue<AWTEvent>();

	static {
		// hack to fix awful flicker on resize (well, make it slightly less awful)
		System.setProperty("sun.awt.noerasebackground", "true");
	}

	protected DisplayWindow(int w, int h) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		canvas = new Canvas();
		setLayout(new BorderLayout());
		add(canvas, BorderLayout.CENTER);
		canvas.setPreferredSize(new Dimension(w, h));
		canvas.setFocusable(false);

		setIgnoreRepaint(true);
		canvas.setIgnoreRepaint(true);
		pack();

		canvas.createBufferStrategy(2);
		bs = canvas.getBufferStrategy();

		BufferedImage cursorimg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		blankcursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorimg, new Point(0, 0), "blankcursor");

		try {
			robot = new Robot();
		} catch (AWTException e) {
			// FIXME if robot cannot be instantiated?
			throw new AssertionError(e);
		}

	}

	/** Create a DisplayWindow on the AWT Event Thread, and wait for completion. */
	public static final DisplayWindow create(final int width, final int height) {
		// hackity hack hack hack...
		final DisplayWindow[] win = new DisplayWindow[1];
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				synchronized (win) {
					win[0] = new DisplayWindow(width, height);
					win.notify();
				}
			}
		});
		synchronized (win) {
			while (win[0] == null) {
				try {
					win.wait();
				} catch (InterruptedException e) {
					// do nothing
				}
			}
		}
		return win[0];
	}

	@Override
	public void addMouseListener(MouseListener ml) {
		canvas.addMouseListener(ml);
	}

	@Override
	public void removeMouseListener(MouseListener ml) {
		canvas.removeMouseListener(ml);
	}

	@Override
	public void addMouseMotionListener(MouseMotionListener ml) {
		canvas.addMouseMotionListener(ml);
	}

	@Override
	public void removeMouseMotionListener(MouseMotionListener ml) {
		canvas.removeMouseMotionListener(ml);
	}

	@Override
	public void addMouseWheelListener(MouseWheelListener ml) {
		canvas.addMouseWheelListener(ml);
	}

	@Override
	public void removeMouseWheelListener(MouseWheelListener ml) {
		canvas.removeMouseWheelListener(ml);
	}

	@Override
	public int getDisplayWidth() {
		return canvas.getWidth();
	}

	@Override
	public int getDisplayHeight() {
		return canvas.getHeight();
	}

	@Override
	public void display(BufferedImage bi) {
		lock_display.lock();
		try {
			Graphics g = null;
			try {
				if (bi != null) {
					g = bs.getDrawGraphics();
					g.drawImage(bi, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
					long ct = System.currentTimeMillis();
					long lt = ct;
					long t, dt;
					long maxdt = 0;
					long mindt = FR_SAMPLE_HISTORY;
					double ft = 0, fr = 0;
					int fc = 1;
					Iterator<Long> it = ftimes.iterator();
					while (it.hasNext()) {
						t = it.next();
						if (ct - t > FR_SAMPLE_HISTORY) {
							it.remove();
						} else if (lt < ct) {
							dt = t - lt;
							ft += dt;
							fc++;
							mindt = dt < mindt ? dt : mindt;
							maxdt = dt > maxdt ? dt : maxdt;
						}
						lt = t;
					}
					ft += ct - lt;
					ft /= fc;
					fr = 1000d / ft;
					// g.setColor(Color.MAGENTA);
					// g.drawString(String.format("%05.1f|%03d|%05.1f|%03d|%d", fr,
					// maxdt, ft, mindt, ct - t_lastbad), 5, 15);
					if (draw_fps) {
						g.setColor(Color.WHITE);
						g.drawString(String.format("%.0f", fr), 5, 15);
					}
					if (draw_text) {
						g.setColor(Color.WHITE);
						for (int y = 27, i = 0; i < display_text.size(); y += 12, i++) {
							g.drawString(display_text.get(i).toString(), 5, y);
						}
					}
					if (draw_crosshair) {
						g.setColor(Color.WHITE);
						int ch_x = canvas.getWidth() / 2 - 10;
						int ch_y = canvas.getHeight() / 2 - 10;
						g.fillRect(ch_x, ch_y + 9, 20, 2);
						g.fillRect(ch_x + 9, ch_y, 2, 20);
					}
					if (!bs.contentsLost()) {
						bs.show();
						ct = System.currentTimeMillis();
						ftimes.add(ct);
						if (ct - lt > 33) {
							// System.out.println("Detected frametime = " + (ct - lt) + "ms.");
							t_lastbad = ct;
						}
					} else {
						System.out.println("Frame dropped: Buffer contents lost.");
					}
				}
			} finally {
				if (g != null) {
					g.dispose();
				}
			}
		} finally {
			lock_display.unlock();
		}
	}

	/**
	 * Get the current state of a key.
	 *
	 * @param vk
	 *            The virtual key code of the key to check, per <code>java.awt.KeyEvent</code>
	 * @return True iff the key is down
	 */
	public boolean getKey(int vk) {
		while (!lock_keys.tryLock())
			;
		try {
			return activekeys[vk];
		} finally {
			lock_keys.unlock();
		}
	}

	/**
	 * Get the current state of a key, and if it is down clear its state.
	 *
	 * @param vk
	 *            The virtual key code of the key to check
	 * @return True iff the key is down
	 */
	public boolean pollKey(int vk) {
		while (!lock_keys.tryLock())
			;
		try {
			return activekeys[vk];
		} finally {
			activekeys[vk] = false;
			lock_keys.unlock();
		}
	}

	/**
	 * Get the current state of a mouse button.
	 *
	 * @param button
	 *            The code of the mouse button to check, per <code>java.awt.MouseEvent</code> (although the
	 *            corresponding values are 1, 2 and 3)
	 * @return True iff the key is down
	 */
	public boolean getMouseButton(int button) {
		while (!lock_mouse.tryLock())
			;
		try {
			return activebuttons[button];
		} finally {
			lock_mouse.unlock();
		}
	}

	/**
	 * Get the current state of a mousebutton, and if it is down clear its state.
	 *
	 * @param button
	 *            The code of the mouse button to check, per <code>java.awt.MouseEvent</code> (although the
	 *            corresponding values are 1, 2 and 3)
	 * @return True iff the key is down
	 */
	public boolean pollMouseButton(int button) {
		while (!lock_mouse.tryLock())
			;
		try {
			return activebuttons[button];
		} finally {
			activebuttons[button] = false;
			lock_mouse.unlock();
		}
	}

	/** Determine if this frame is currently fullscreen. */
	public boolean isFullscreen() {
		return gd.getFullScreenWindow() == this;
	}

	/** Set whether this frame should be fullscreen or not. */
	public void setFullscreen(boolean b) {
		lock_display.lock();
		try {
			if (b && gd.isFullScreenSupported()) {
				Rectangle bounds = gd.getDefaultConfiguration().getBounds();
				gd.setFullScreenWindow(this);
				setBounds(bounds);
				canvas.setBounds(bounds);
				canvas.createBufferStrategy(2);
				bs = canvas.getBufferStrategy();
				System.out.println("Going fullscreen, using page flipping: " + bs.getCapabilities().isPageFlipping());
			} else {
				gd.setFullScreenWindow(null);
				canvas.createBufferStrategy(2);
				bs = canvas.getBufferStrategy();
			}
		} finally {
			lock_display.unlock();
		}
	}

	/**
	 * Determine if the mouse is currently 'captured', i.e. locked to the centre of the frame.
	 */
	public boolean isMouseCaptured() {
		while (!lock_mouse.tryLock())
			;
		try {
			return mousecaptured;
		} finally {
			lock_mouse.unlock();
		}
	}

	/**
	 * Set whether the mouse should be 'captured', i.e. locked to the centre of the frame.
	 */
	public void setMouseCapture(boolean b) {
		while (!lock_mouse.tryLock())
			;
		try {
			mousecaptured = b;
			if (b) {
				int centrex = canvas.getWidth() / 2;
				int centrey = canvas.getHeight() / 2;
				Point cloc;
				if (isFullscreen()) {
					// HACK for getLocationOnScreen() not giving 0,0 in
					// fullscreen
					cloc = point_zero;
				} else {
					cloc = canvas.getLocationOnScreen();
				}
				// put the mouse in the centre of the canvas
				robot.mouseMove(cloc.x + centrex, cloc.y + centrey);
			}
		} finally {
			lock_mouse.unlock();
		}
	}

	/**
	 * Gets the previous state of the mouse capture prior to the window losing focus.
	 */
	public boolean getPrevMouseCapture() {
		while (!lock_mouse.tryLock())
			;
		try {
			return this.prev_mousecaptured;
		} finally {
			lock_mouse.unlock();
		}
	}

	/**
	 * Determine if the cursor has been made invisible by a call to <code>setCursorVisible()</code>.
	 *
	 * @return false iff <code>setCursorVisible(false)</code> has been called, and setting a custom cursor is allowed.
	 */
	public boolean isCursorVisible() {
		return getCursor() != blankcursor;
	}

	/**
	 * Set the visibility of the cursor over this frame. The cursor can only be hidden if setting a custom cursor is
	 * allowed.
	 */
	public void setCursorVisible(boolean b) {
		if (b) {
			super.setCursor(oldcursor);
		} else if (!b && getCursor() != blankcursor) {
			super.setCursor(blankcursor);
		}
	}

	@Override
	public void setCursor(Cursor cursor) {
		oldcursor = cursor;
		super.setCursor(cursor);
	}

	/** Get the x-position of the mouse within the content pane of this frame. */
	public int getMouseX() {
		while (!lock_mouse.tryLock())
			;
		try {
			return mousex;
		} finally {
			lock_mouse.unlock();
		}
	}

	/** Get the y-position of the mouse within the content pane of this frame. */
	public int getMouseY() {
		while (!lock_mouse.tryLock())
			;
		try {
			return mousey;
		} finally {
			lock_mouse.unlock();
		}
	}

	/**
	 * Get how much x-travel has been made by the mouse. While the mouse is captured, its travel is accumulated
	 * internally and then depleted up to a specified limit by calling this method.
	 */
	public int pollMouseTravelX(int limit) {
		while (!lock_mouse.tryLock())
			;
		try {
			int mxsign = mousetravelx > 0 ? 1 : mousetravelx < 0 ? -1 : 0;
			if (mousetravelx * mxsign >= limit) {
				mousetravelx -= mxsign * limit;
				return limit;
			} else {
				int t = mousetravelx;
				mousetravelx = 0;
				return t;
			}
		} finally {
			lock_mouse.unlock();
		}
	}

	/**
	 * Like <code>pollMouseTravelX(int)</code> except all the accumulated travel is read and returned.
	 */
	public int pollMouseTravelX() {
		while (!lock_mouse.tryLock())
			;
		try {
			int t = mousetravelx;
			mousetravelx = 0;
			return t;
		} finally {
			lock_mouse.unlock();
		}
	}

	/**
	 * Get how much y-travel has been made by the mouse. While the mouse is captured, its travel is accumulated
	 * internally and then depleted up to a specified limit by calling this method.
	 */
	public int pollMouseTravelY(int limit) {
		while (!lock_mouse.tryLock())
			;
		try {
			int mysign = mousetravely > 0 ? 1 : mousetravely < 0 ? -1 : 0;
			if (mousetravely * mysign >= limit) {
				mousetravely -= mysign * limit;
				return limit;
			} else {
				int t = mousetravely;
				mousetravely = 0;
				return t;
			}
		} finally {
			lock_mouse.unlock();
		}
	}

	/**
	 * Like <code>pollMouseTravelY(int)</code> except all the accumulated travel is read and returned.
	 */
	public int pollMouseTravelY() {
		while (!lock_mouse.tryLock())
			;
		try {
			int t = mousetravely;
			mousetravely = 0;
			return t;
		} finally {
			lock_mouse.unlock();
		}
	}

	/**
	 * Get how much travel has been made by the mouse wheel. The mouse wheel travel is accumulated internally and then
	 * depleted up to a specified limit by calling this method.
	 */
	public int pollMouseWheelClicks(int limit) {
		while (!lock_mouse.tryLock())
			;
		try {
			int mssign = mousescrollclicks > 0 ? 1 : mousescrollclicks < 0 ? -1 : 0;
			if (mousescrollclicks * mssign >= limit) {
				mousescrollclicks -= mssign * limit;
				return limit;
			} else {
				int t = mousescrollclicks;
				mousescrollclicks = 0;
				return t;
			}
		} finally {
			lock_mouse.unlock();
		}
	}

	/**
	 * Like <code>pollMouseWheelClicks(int)</code> except all the accumulated travel is read and returned.
	 */
	public int pollMouseWheelClicks() {
		while (!lock_mouse.tryLock())
			;
		try {
			int t = mousescrollclicks;
			mousescrollclicks = 0;
			return t;
		} finally {
			lock_mouse.unlock();
		}
	}

	public void setCrosshairVisible(boolean b) {
		draw_crosshair = b;
	}

	public boolean isCrosshairVisible(boolean b) {
		return draw_crosshair;
	}

	public void setFPSVisible(boolean b) {
		draw_fps = b;
	}

	public boolean isFPSVisible() {
		return draw_fps;
	}

	public void setTextVisible(boolean b) {
		draw_text = b;
	}

	public boolean isTextVisible() {
		return draw_text;
	}

	public void addText(Object o) {
		lock_display.lock();
		try {
			display_text.add(o);
		} finally {
			lock_display.unlock();
		}
	}

	public void addText(int i, Object o) {
		lock_display.lock();
		try {
			display_text.add(i, o);
		} finally {
			lock_display.unlock();
		}
	}

	public void removeText(Object o) {
		lock_display.lock();
		try {
			display_text.remove(o);
		} finally {
			lock_display.unlock();
		}
	}

	public void removeText(int i) {
		lock_display.lock();
		try {
			display_text.remove(i);
		} finally {
			lock_display.unlock();
		}
	}

	/**
	 * Set whether events should be processed on the AWT event thread as normal (asynchronous), or queued up to be
	 * processed by a call to <code>pushEvents()</code>.
	 * <p>
	 * WARNING: This doesn't seem to break anything, but no guarantees. Use with caution.
	 * </p>
	 *
	 * @param b
	 *            True iff events should be queued
	 */
	public void setEventsSynchronous(boolean b) {
		events_synchronous = b;
	}

	/**
	 * Determine whether events will be processed on the AWT event thread as normal (asynchronous), or queued up to be
	 * processed by a call to <code>pushEvents()</code>.
	 *
	 * @return True iff events will be queued
	 */
	public boolean areEventsSynchronous() {
		return events_synchronous;
	}

	@Override
	protected void processKeyEvent(KeyEvent ke) {
		while (!lock_keys.tryLock())
			;
		try {
			if (ke.getID() == KeyEvent.KEY_PRESSED) activekeys[ke.getKeyCode()] = true;
			if (ke.getID() == KeyEvent.KEY_RELEASED) activekeys[ke.getKeyCode()] = false;
		} finally {
			lock_keys.unlock();
		}
		super.processKeyEvent(ke);
	}

	@Override
	protected void processMouseEvent(MouseEvent me) {
		if (me.getButton() != MouseEvent.NOBUTTON) {
			while (!lock_mouse.tryLock())
				;
			try {
				if (me.getID() == MouseEvent.MOUSE_PRESSED) activebuttons[me.getButton()] = true;
				if (me.getID() == MouseEvent.MOUSE_RELEASED) activebuttons[me.getButton()] = false;
			} finally {
				lock_mouse.unlock();
			}
		}
		super.processMouseEvent(me);
	}

	@Override
	protected void processMouseMotionEvent(MouseEvent me) {
		while (!lock_mouse.tryLock())
			;
		try {
			Point cloc;
			if (isFullscreen()) {
				// HACK for getLocationOnScreen() not giving 0,0 in fullscreen
				cloc = point_zero;
			} else {
				cloc = canvas.getLocationOnScreen();
			}
			mousex = me.getXOnScreen() - cloc.x;
			mousey = me.getYOnScreen() - cloc.y;
			if (mousecaptured) {
				int centrex = canvas.getWidth() / 2;
				int centrey = canvas.getHeight() / 2;
				mousetravelx += (mousex - centrex);
				mousetravely += (mousey - centrey);
				// put the mouse in the centre of the canvas again
				// check if non zero travel to avoid mouse event spam
				if (mousetravelx != 0 || mousetravely != 0) {
					robot.mouseMove(cloc.x + centrex, cloc.y + centrey);
				}
			}
		} finally {
			lock_mouse.unlock();
		}
		super.processMouseMotionEvent(me);
	}

	@Override
	protected void processMouseWheelEvent(MouseWheelEvent me) {
		while (!lock_mouse.tryLock())
			;
		try {
			mousescrollclicks += me.getWheelRotation();
		} finally {
			lock_mouse.unlock();
		}
		super.processMouseWheelEvent(me);
	}

	@Override
	protected void processFocusEvent(FocusEvent e) {
		// automatically release mouse on focus lost and restore when focus gained
		while (!lock_mouse.tryLock())
			;
		try {
			if (e.getID() == FocusEvent.FOCUS_GAINED) {
				DisplayWindow.this.mousecaptured = DisplayWindow.this.prev_mousecaptured;
			} else if (e.getID() == FocusEvent.FOCUS_LOST) {
				DisplayWindow.this.prev_mousecaptured = DisplayWindow.this.mousecaptured;
				DisplayWindow.this.mousecaptured = false;
			}
		} finally {
			lock_mouse.unlock();
		}
		super.processFocusEvent(e);
	}

	@Override
	protected void processEvent(AWTEvent e) {
		if (events_synchronous) {
			// queue event for later processing
			event_queue.add(e);
		} else {
			// process any pending events, then normal behaviour
			pushEvents();
			super.processEvent(e);
		}
	}

	/**
	 * Push all pending events. Event listeners will be called from the thread that calls this method. Events must be
	 * set to synchronous for this method to do anything, however it is not an error to call this method when normal
	 * event dispatch is in use.
	 */
	public void pushEvents() {
		AWTEvent e = null;
		while ((e = event_queue.poll()) != null) {
			try {
				super.processEvent(e);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
