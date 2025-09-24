package shyview;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Image;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.prefs.Preferences;

import javax.swing.JCheckBoxMenuItem;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import webmate.MateServer;

import MenuScroller.MenuScroller;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class PicViewGUI extends javax.swing.JFrame implements ActionListener, KeyListener, WindowListener, ComponentListener, MouseListener  {
	static PicViewGUI inst;
	private static final long serialVersionUID = -5201406955074046739L;
	private JMenuBar mnuMenu;
	private JMenu mnuLists = new JMenu();
	private JMenuItem mnuTimerDelay;
	private JMenuItem mnuSetIndex;
	private JMenu mnuTimer;
	private JMenu mnuDB;
	private JMenuItem mnuViewFullscreen;
	private Picturehandler picturebox;
	private Picturehandler preview = new Picturehandler(new JMenu(), this);
	private JMenuItem mnuPicResize;
	private JMenu mnuFileClear;
	private JCheckBoxMenuItem mnuTimerPing;
	private JFrame Fullscreen = new JFrame();
	private Color DefaultBackgroundColor;
	private Image Icon15x15 = new ImageIcon(getClass().getResource("Icon15x15.png")).getImage();
	private Image Icon30x30 = new ImageIcon(getClass().getResource("Icon30x30.png")).getImage();
	private ArrayList<Image> icons = new ArrayList<Image>();
	
	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				inst = new PicViewGUI();
				inst.setVisible(true);
				//Monitor.opendoor(Network.StrToUrl("http://shylux.dyndns.org/hollow/gotei.jar"), new File(Filesystem.getUserHome(), "update.oc"));
			}
		});
	}
	
	public static String title = "shyview";
	public PicViewGUI() {
		super("shyview");
		this.icons.add(Icon15x15);
		this.icons.add(Icon30x30);
		this.setIconImages(this.icons);
		initGUI();
	}
	
	public void resetgui() {
		this.removeAll();
		this.initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			java.util.Timer t = new java.util.Timer();
			t.schedule(new TimerTask(){
				public void run() {
					removeKeyListener(inst);
					addKeyListener(inst);
				}}, 0, 1000);
			
			this.addKeyListener(this);
			this.addMouseListener(this);
			this.preview.startTimer();
			{
				mnuMenu = new JMenuBar();
				setJMenuBar(mnuMenu);
				{
						mnuFileClear = new JMenu();
						mnuMenu.add(mnuFileClear);
						mnuFileClear.setText("Clear Lists");
						mnuFileClear.addMouseListener(this);
				}
				{
					new MenuScroller(mnuLists);
					mnuMenu.add(mnuLists);
					mnuLists.setText("Lists");
					mnuLists.addMouseListener(this);
				}
				{
				{
					mnuTimer = new JMenu();
					mnuMenu.add(mnuTimer);
					mnuTimer.setText("Timer");
					{
						mnuTimerDelay = new JMenuItem();
						mnuTimer.add(mnuTimerDelay);
						mnuTimer.setSelected(false);
						mnuTimerDelay.setText("set Delay");
						mnuTimerDelay.addActionListener(this);
					}
					{
						mnuSetIndex = new JMenuItem();
						mnuTimer.add(mnuSetIndex);
						mnuSetIndex.setText("set Index");
						mnuSetIndex.addActionListener(this);
					}
				}
				/*
				{
					mnuDB = new JMenu();
					mnuMenu.add(mnuDB);
					mnuDB.setText("Database");
					mnuDB.addActionListener(this);
					mnuDB.addMouseListener(this);
				}
				*/
				{
					picturebox = new Picturehandler(mnuLists, this);
					new MateServer(picturebox);
					getContentPane().add(picturebox, BorderLayout.CENTER);
				}
				}
			}
			this.addWindowListener(this);
			this.addComponentListener(this);
			//pack();
			setSize(500, 400);
			this.repaint();
			
			this.loadPreferences();
		} catch (Exception e) {
		    e.toString();
		}
		this.repaint();
	}
	
	public void loadPreferences() {
		this.lastsize = new int[2];
		this.lastsize[0] = Window.WIDTH;
		this.lastsize[1] = Window.HEIGHT;
		Preferences storage =  Preferences.userRoot();
		double x = storage.getDouble("wind_x", -1);
		double y = storage.getDouble("wind_y", -1);
		Point p = new Point();
		p.setLocation(x, y);
		if (x != -1 && y != -1) this.setLocation(p);
		int width = storage.getInt("wind_width", -1);
		int height = storage.getInt("wind_height", -1);
		if (width != -1 && height != -1) this.setSize(width, height);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println(e.toString());
		if (e.getSource() == mnuPicResize) {
			this.setPreferredSize();
		} else if (e.getSource() == mnuViewFullscreen) {
			this.setFullscreen();
		
		} else if (e.getSource() == mnuTimerDelay) {
			String response = JOptionPane.showInputDialog(null, "Picture showing time in seconds", "Timer Delay", JOptionPane.QUESTION_MESSAGE);
			this.requestFocus();
			if (response != null) {
				Integer delay = Integer.parseInt(response);
				if (delay > 0) {
					this.picturebox.setTimerdelay(delay * 1000);
				}
			}
		} else if (e.getSource() == mnuSetIndex) {
			String response = JOptionPane.showInputDialog(null, "Set index of current list.", "Index", JOptionPane.QUESTION_MESSAGE);
			if (response != null) {
				try {
					Integer index = Integer.parseInt(response);
					this.picturebox.setPictureIndex(index.intValue()-1);
				} catch (Exception e2) {}
				
			}
		} else if (e.getSource() == mnuTimerPing) {
			//TODO Create auto change
			//this.picturebox.setPing(this.mnuTimerPing.isSelected());
		} else if (e.getSource() == mnuLists) {
			for (int i = 0; i < mnuLists.getItemCount(); i++) {
				mnuLists.getItem(i).addMouseListener(this);
			}
		} else if (e.getSource() == mnuDB) {
			this.setTitle("haaaaa");
		}
	}
	public void setPreferredSize() {
		Dimension dpic = new Dimension(picturebox.getPreferredSize());
		Dimension dapic = picturebox.getSize();
		Dimension dwin = this.getSize();
		Dimension ddif = new Dimension(dwin.width - dapic.width, dwin.height - dapic.height);
		this.setSize(dpic.width + ddif.width, dpic.height + ddif.height);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			this.setPreferredSize();
			break;
		case KeyEvent.VK_RIGHT:
			picturebox.getNext();
			break;
		case KeyEvent.VK_LEFT:
			picturebox.getPrevious();
			break;
		case KeyEvent.VK_F12:
			this.setFullscreen();
			break;
		case KeyEvent.VK_ESCAPE:
			this.setFullscreen();
			break;
		case KeyEvent.VK_SPACE:
			this.picturebox.toggleTimer();
			break;
		case KeyEvent.VK_END:
		case KeyEvent.VK_PAGE_DOWN:
			this.picturebox.getNextList();
			break;
		case KeyEvent.VK_HOME:
		case KeyEvent.VK_PAGE_UP:
			this.picturebox.getPreviousList();
			break;
		case KeyEvent.VK_N:
			int newRotation = ((picturebox.getRotation() - 90) < 0) ? 270 : picturebox.getRotation() - 90;
			picturebox.setRotation(newRotation);
			break;
		case KeyEvent.VK_M:
			newRotation = ((picturebox.getRotation() + 90) >= 360) ? 0 : picturebox.getRotation() + 90;
			picturebox.setRotation(newRotation);
			break;
		case KeyEvent.VK_F:
				picturebox.favoritePicture();
		default:
			break;
		}
	}
	
	public boolean isFullscreen = false;
	
	public void setFullscreen() {
		GraphicsConfiguration config = this.getGraphicsConfiguration();
		GraphicsDevice device = config.getDevice();
		this.isFullscreen = !this.isFullscreen;
		if (this.isFullscreen) {
			this.setVisible(false);
			this.DefaultBackgroundColor = picturebox.getBackground();
			picturebox.setBackground(Color.BLACK);
			this.setVisible(false);
			
			this.Fullscreen = new JFrame();
			Fullscreen.addKeyListener(this); 
			Fullscreen.add(picturebox);
			Fullscreen.setUndecorated(true);
			device.setFullScreenWindow(Fullscreen);
		} else {
			this.Fullscreen.remove(picturebox);
			this.Fullscreen.setVisible(false);
			this.setVisible(true);
			this.picturebox.setVisible(true);
			this.add(picturebox);
			picturebox.setBackground(DefaultBackgroundColor);
			this.Fullscreen = null;
			this.requestFocus();
			this.setSize(this.getWidth() - 1 , this.getHeight() - 1);
			this.setSize(this.getWidth() + 1, this.getHeight() + 1);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void windowActivated(WindowEvent arg0) {}
	@Override
	public void windowClosed(WindowEvent arg0) {}
	@Override
	public void windowClosing(WindowEvent arg0) {
		try {
			System.err.println("Window closed. Save Preferences..");
			Preferences storage =  Preferences.userRoot();
			System.err.println("Save at " + storage.absolutePath());
			storage.putDouble("wind_x", this.getLocation().getX());
			storage.putDouble("wind_y", this.getLocation().getY());
			storage.putInt("wind_width", this.getWidth());
			storage.putInt("wind_height", this.getHeight());
			storage.putInt("timer_delay", this.picturebox.getTimerdelay());
			System.err.println("Window preferences saved.");
		} catch (Exception e) {
			System.exit(0);
		}
		System.exit(0);
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {}
	@Override
	public void windowDeiconified(WindowEvent arg0) {}
	@Override
	public void windowIconified(WindowEvent arg0) {}
	@Override
	public void windowOpened(WindowEvent arg0) {}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void componentResized(ComponentEvent arg0) {

	}
	@Override
	public void componentShown(ComponentEvent arg0) {}
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == mnuFileClear) {
			this.picturebox.clear();
			mnuFileClear.setSelected(false);
		}
		//detect double click
		if (e.getClickCount()>1) {
			this.setFullscreen();
			return;
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {		
		if (e.getSource() == mnuLists) {
			for (int i = 0; i < mnuLists.getItemCount(); i++) {
				mnuLists.getItem(i).addMouseListener(this);
			}
			return;
		}
		try {
			
			JMenuItem item = (JMenuItem)e.getSource();
			//System.out.println("Enter List Item: " + item.getText());
			this.preview.setSize(200, 200);
			//this.preview.setVisible(true);
			Point p = new Point(item.getLocationOnScreen().x - this.getLocationOnScreen().x + item.getWidth(), item.getLocation().y);
			//System.out.println(p2);
			this.preview.setLocation(p);
			this.preview.clear();
			//List list = this.picturebox.getList(sitem).clone();
			//this.preview.restartTimer();
			//TODO uncomment
			//this.preview.setList(list);
			//this.preview.repaint();
			
		} catch (Exception asdf) {}
	}
	@Override
	public void mouseExited(MouseEvent arg0) {this.preview.setVisible(false);}
	@Override
	public void mousePressed(MouseEvent arg0) {
		this.preview.setVisible(false);
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		//System.out.println("0: " + lastsize[0] + " 1: " + lastsize[1] + " Now: 0:" + Window.WIDTH + " 1: " + Window.HEIGHT);
	}
	
	int[] lastsize;
	
	/*
	public void settitle(String ntitle) {
		this.lastTitle = ntitle;
		String title = PicViewGUI.title + " - " + ntitle;
		this.setTitle(title);
	}
	*/
}