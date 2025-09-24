package User_Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;

import Client_Server.AGServer;

/**
 * This class is used to display the graphical user interface for the server.
 * This class interacts with AGServer to start the server and have the option to
 * load from a previous game state, as well as when the server is running to
 * save the game state.
 * 
 * @author Alex Candler, 300257532, candlealex
 * 
 */
public class ServerWindow extends JFrame {

	private static int windowWidth = 400;
	private static int windowHeight = 380;
	private static int consoleHeight = 300;
	private ServerWindow serverWindow = this;
	private static String serverIcon = "Images/serverIcon.png";
	private JPanel consolePanel;
	private JTextArea console;
	private JMenuBar menuBar;
	private JPanel startServerPanel;
	private AGServer serv;
	FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter(
			"xml files (*.xml)", "xml");
	private String gameFile;

	/**
	 * Server Window constructor
	 */
	public ServerWindow() {
		super("Adventure Game Server");
		setupWindow();

		// Make the window visible
		this.setVisible(true);
	}

	/**
	 * Creates all of the components to be added to the window
	 */
	public void setupWindow() {
		// Initialize the window
		setSize(windowWidth, windowHeight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(serverIcon).getImage());

		// Add menu bar
		setupMenuBar();
		setJMenuBar(menuBar);

		// Create a start server panel
		startServerPanel = new JPanel();
		startServerPanel.setLayout(new BoxLayout(startServerPanel,
				BoxLayout.Y_AXIS));
		TitledBorder title = BorderFactory.createTitledBorder("Launch Server");
		title.setTitleJustification(TitledBorder.CENTER);
		startServerPanel.setBorder(title);

		// Create a label for where the port number will go
		JLabel portLabel = new JLabel("Port:");
		final JTextField port = new JTextField(6);
		port.setMaximumSize(new Dimension(100, 20));

		// Create a start server button
		JButton loadButton = new JButton("Load Level");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gameFile = getLoadPath();
			}
		});

		// Create a start server button
		JButton startButton = new JButton("Start Server");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {
					// Checks if a game file has been selected
					if (gameFile == null) {
						serv = new AGServer(Integer.parseInt(port.getText()),
								serverWindow, null);
					} else {
						serv = new AGServer(Integer.parseInt(port.getText()),
								serverWindow, gameFile);
					}
				} catch (NumberFormatException | IOException e) {
					JOptionPane.showMessageDialog(serverWindow,
							"Please enter a valid port number!", "Port error",
							JOptionPane.OK_OPTION);
					return;
				}

				// Changes to the console panel
				remove(startServerPanel);
				add(consolePanel, BorderLayout.SOUTH);
				revalidate();
				repaint();
			}
		});

		setupConsole();

		// Align all components to the center
		portLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		port.setAlignmentX(Component.CENTER_ALIGNMENT);
		loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Adds components to the panel
		startServerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		startServerPanel.add(portLabel);
		startServerPanel.add(port);
		startServerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		startServerPanel.add(loadButton);
		startServerPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		startServerPanel.add(startButton);
		add(startServerPanel);
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

		// Stop Server Item
		JMenuItem stopServerItem = new JMenuItem("Stop Server");
		stopServerItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				console.setText(""); // clear the console text so a new
										// server has fresh output
				remove(consolePanel);
				add(startServerPanel, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});

		// Save State Item
		JMenuItem saveStateItem = new JMenuItem("Save State");
		saveStateItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				String path = getSavePath();
				try {
					serv.saveGame(path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IntrospectionException e) {
					e.printStackTrace();
				}
				System.out.println("Game saved to: " + path);
			}
		});

		// Quit Item
		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {

				console.setText(""); // clear the console text so a new
										// server has fresh output
				serverWindow.dispose();
			}
		});

		// Add menu to frame
		menu.add(stopServerItem);
		menu.add(saveStateItem);
		menu.add(quitItem);
		menuBar.add(menu);
	}

	/**
	 * Sets up the console panel for the server
	 */
	private void setupConsole() {
		// Create a panel for the console
		consolePanel = new JPanel();

		// Create a label to go into the console
		JLabel stopLabel = new JLabel("Server can be stopped from the menu");

		// Create a scrollable text area for the server
		console = new JTextArea(10, 20);
		JScrollPane textSP = new JScrollPane(console);
		textSP.setPreferredSize(new Dimension(windowWidth - 20, consoleHeight));
		console.setEditable(false);

		// Change the text for the text area
		console.setForeground(Color.white);
		console.setBackground(Color.black);
		Font font = new Font("Verdana", Font.BOLD, 12);
		console.setFont(font);
		console.setLineWrap(true);

		// Set the text Area to auto scroll to the latest text
		DefaultCaret caret = (DefaultCaret) console.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		// Add components to the panel
		consolePanel.add(stopLabel);
		consolePanel.add(textSP);
	}

	/**
	 * Displays a file chooser for the user to select a save location
	 * @return the file path to save to
	 */
	public String getSavePath() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(xmlfilter);
		int option = fc.showSaveDialog(null);
		if (option == JFileChooser.APPROVE_OPTION) {
			String path = fc.getSelectedFile().getAbsolutePath();
			return path;
		} else {
			return null;
		}

	}

	/**
	 * Displays a file chooser for the user to select a load location
	 * @return the file path to load from
	 */
	public String getLoadPath() {
		// Setup file chooser
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(xmlfilter);
		fc.setDialogTitle("Select Saved XML File");

		// Open dialog
		int returnVal = fc.showOpenDialog(serverWindow);

		// Load file
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String path = fc.getSelectedFile().getAbsolutePath();
			return path;
		} else {
			return null;
		}
	}

	/**
	 * Adds text to the console window
	 * @param text to be appended
	 */
	public void write(String text) {
		this.console.append(text + "\n");
	}

}
