package User_Interface;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * This class is used to create a custom JFrame that has an image for the
 * background as well as a menu bar.
 * 
 * @author Alex Candler, 300257532, candlealex
 * 
 */
public class CustomFrame extends JFrame {
	private int windowWidth;
	private int windowHeight;
	private static String gameIcon = "Images/gameIcon.png";
	private static String backgroundImage = "src/Images/AppWindow/background_image.png";
	private static String backgroundImage2 = "src/Images/AppWindow/blackBackground.png";
	private JMenuBar menuBar;
	private CustomFrame frame = this;
	private JLabel image;

	public CustomFrame(int wWidth, int wHeight) {
		this.windowWidth = wWidth;
		this.windowHeight = wHeight;
		// Setup Window
		setTitle("Adventure Game");
		setSize(windowWidth, windowHeight + 100);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(gameIcon).getImage());

		// Setup Components
		setBackground(Color.black);
		image = new JLabel(new ImageIcon(backgroundImage2));
		setContentPane(image);

		setLayout(new FlowLayout());

		// Add Menu
		setupMenuBar();
		setJMenuBar(menuBar);
	}

	/**
	 * Creates menu items and adds them to the menu bar
	 */
	private void setupMenuBar() {
		// Create MenuBar
		menuBar = new JMenuBar();
		// Create Menu to go into MenuBar
		JMenu menu = new JMenu("Menu");
		// Create Menu Items to go into a Menu
		// Leave Game
		JMenuItem leaveItem = new JMenuItem("Leave Game");
		leaveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		// Quit Item
		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				frame.dispose();
			}
		});

		// Add menu to frame
		menu.add(leaveItem);
		menu.add(quitItem);
		menuBar.add(menu);
	}
}