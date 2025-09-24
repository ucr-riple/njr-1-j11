package User_Interface;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;
import javax.swing.text.DefaultCaret;
import Client_Server.AGClient;
import Game_World.Avatar;
import Game_World.ExternalDoor;
import Game_World.GameWorld;
import Game_World.InternalDoor;
import Game_World.Altar;
import Game_World_Objects.Chest;
import Game_World_Objects.Food;
import Game_World_Objects.Key;
import Game_World_Objects.Relic;
import Game_World_Objects.Torch;
import Graphics_Renderer.GraphicsRenderer;
import Junit_Tests.Class_Examples;
import Object_Interfaces.Containable;
import Object_Interfaces.Container;
import Object_Interfaces.GameObject;

/**
 * This class is used to display the main graphical user interface for the game.
 * This class interacts with GraphicsRenderer to display the current state of a
 * game, as well as communicate with AGClient to keep the local state up to date
 * with the server state.
 *
 * @author Alex Candler, 300257532, candlealex
 *
 */
public class ApplicationWindow implements MouseListener, KeyListener,
		FocusListener {

	// Component sizes
	private static int windowHeight = 710;
	private static int windowWidth = 840;
	private static int toolBarHeight = 210;
	private static int renderWindowWidth = 1600;
	private static int renderWindowHeight = windowHeight - toolBarHeight;
	private Dimension renderWindowSize;

	// Frame, Panels and Components
	private ApplicationWindow appWin = this;
	private CustomFrame frame;
	private JPanel startPanel;
	private CustomPanel joinPanel;
	private JPanel gamePanel;
	private CustomPanel toolbarPanel;
	private JTextArea chatArea;
	private JTextArea infoText;
	private JPanel gameRendererPanel;
	private JPanel interactionPanel;
	private JPanel toolbarContent;
	private JPanel objectContents;
	private JPanel newInteractionPanel;
	private JProgressBar healthBar;

	// Networked Objects
	private AGClient client;
	private GameWorld gameWorld;
	private static GameObject selectedObject;
	private Avatar avatar;
	private int uid;
	private String name;
	private boolean isWon = false;

	// Image locations
	private String joinButtonFile = "Images/AppWindow/Buttons/Join_Game";
	private String sJoinButtonFile = "Images/AppWindow/Buttons/Join_Small";
	private String launchButtonFile = "Images/AppWindow/Buttons/Launch_Server";
	private String quitButtonFile = "Images/AppWindow/Buttons/Quit";
	private String smallPanelFile = "Images/AppWindow/Panel_Small";
	private String backButtonFile = "Images/AppWindow/Buttons/Back";
	private String toolBarFile = "Images/AppWindow/Toolbar";

	// Sound locations
	private final String song1 = "Sounds/An Adventure Awaits.wav";

	/**
	 * ApplicationWindow constructor
	 */
	public ApplicationWindow() {
		// Setup frame
		MediaPlayer.playWelcome();
		frame = new CustomFrame(windowWidth, windowHeight);
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				renderWindowSize = frame.getBounds().getSize();
				if (gameRendererPanel != null) {
					gameRendererPanel.setPreferredSize(new Dimension(
							renderWindowSize.width - 20,
							renderWindowSize.height - 280));
					frame.repaint();
				}
			}
		});
		frame.addMouseListener(this);
		frame.addKeyListener(this);
		frame.addFocusListener(this);
		// Adds all of the components to the frame
		setupWindow();
		frame.setVisible(true);

		this.gameWorld = null;
	}

	/**
	 * Creates all of the components to be added to the frame
	 */
	public void setupWindow() {
		// Add starting panel
		setupStartPanel();
		startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
		frame.add(startPanel);

		// Setup game panel
		setupGamePanel();

		// Setup join panel
		setupJoinPanel();
	}

	/**
	 * Sets up the initial panel that displays a "Join Game", Launch Server
	 * Window" and "Quit"
	 */
	private void setupStartPanel() {
		// Creates a new transparent panel
		startPanel = new JPanel();
		startPanel.setOpaque(false);

		// Sets up Join Button
		CustomButton joinButton = new CustomButton(joinButtonFile) {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				setAction("Normal");
				frame.remove(startPanel);
				frame.add(joinPanel);
				frame.revalidate();
				frame.repaint();
			}
		};

		// Sets up Launch Server Button
		CustomButton serverButton = new CustomButton(launchButtonFile) {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				setAction("Normal");
				new ServerWindow();
				repaint();
			}
		};

		// Sets up Quit Button
		CustomButton quitButton = new CustomButton(quitButtonFile) {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				setAction("Normal");
				frame.dispose();
				repaint();
			}
		};

		// Adds the buttons to the panel (with transparent spacers for layout)
		startPanel.add(Box.createRigidArea(new Dimension(0, 150)));
		startPanel.add(joinButton);
		startPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		startPanel.add(serverButton);
		startPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		startPanel.add(quitButton);
	}

	/**
	 * Sets up the panel for the player to enter the port and IP of the server
	 * and the name they want for there player
	 */
	private void setupJoinPanel() {
		// Setup background
		joinPanel = new CustomPanel(smallPanelFile);
		joinPanel.setOpaque(false);

		// Setup foreground
		JPanel joinPanelContent = new JPanel();
		joinPanelContent.setOpaque(false);
		joinPanelContent.setLayout(new BoxLayout(joinPanelContent,
				BoxLayout.Y_AXIS));

		// Add components to foreground
		JLabel label = new JLabel("Enter Server Details");

		// Add a text field for the port number
		JLabel portLabel = new JLabel("Port:");
		final JTextField port = new JTextField(6);

		// Add a text field for the IP
		JLabel ipLabel = new JLabel("IP:");
		final JTextField ip = new JTextField(16);
		ip.setText("localhost");

		// Add a text field for the players name
		JLabel nameLabel = new JLabel("Player Name:");
		final JTextField nameField = new JTextField(16);

		// Setup Join Button in joinPanel
		CustomButton join = new CustomButton(sJoinButtonFile) {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				try {

					// Check if the name is 3 or more characters long
					if (nameField.getText().length() >= 3) {
						name = nameField.getText();
						client = new AGClient(ip.getText(),
								Integer.parseInt(port.getText()), name, appWin);

						// start the music
						// AudioInputStream audioInputStream = AudioSystem
						// .getAudioInputStream(new File(song1));
						// Clip clip = AudioSystem.getClip();
						// clip.open(audioInputStream);
						// clip.start();
						MediaPlayer.stopCurrent();
						MediaPlayer.playMain();
					}

					// Invalid Name
					else {
						JOptionPane.showMessageDialog(frame,
								"Please enter a valid player name!",
								"Name error", JOptionPane.OK_OPTION);
						return;
					}
				}

				// Invalid Port number
				catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(frame,
							"Please enter a valid port number!", "Port error",
							JOptionPane.OK_OPTION);
					return;
				}

				// Invalid IP address
				catch (Exception e) {
					JOptionPane.showMessageDialog(frame,
							"Please enter a valid IP address!",
							"IP Address error", JOptionPane.OK_OPTION);
					e.printStackTrace();
					return;
				}

				// Changes the button look to normal
				setAction("Normal");

				// Removes the joinPanel and replaces it with gamePanel
				frame.remove(joinPanel);
				frame.add(gamePanel);
				frame.revalidate();

				// Sets the focus to the frame so key presses are registered
				frame.requestFocusInWindow();

				// Draws all of the new changes
				frame.repaint();
			}
		};

		// Setup a back button in joinPanel
		CustomButton back = new CustomButton(backButtonFile) {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				setAction("Normal");
				frame.remove(joinPanel);
				frame.add(startPanel);
				frame.revalidate();
				frame.repaint();
			}
		};

		// Add all of the components to the content panel (use transparent
		// spacers for layout)
		joinPanelContent.add(Box.createRigidArea(new Dimension(0, 25)));
		joinPanelContent.add(label);
		joinPanelContent.add(portLabel);
		joinPanelContent.add(port);
		joinPanelContent.add(ipLabel);
		joinPanelContent.add(ip);
		joinPanelContent.add(nameLabel);
		joinPanelContent.add(nameField);
		joinPanelContent.add(Box.createRigidArea(new Dimension(0, 2)));
		joinPanelContent.add(join);

		// Adds the content panel to the joinPanel
		joinPanel.add(back);
		joinPanel.add(joinPanelContent);
	}

	/**
	 * Sets up a panel for displaying the current game (displayed using GraphicsRenderer)
	 * as well as adds a toolbar.
	 */
	private void setupGamePanel() {
		// Create a transparent panel to hold all of the game components
		gamePanel = new JPanel();
		gamePanel.setOpaque(false);
		gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));

		// Creates a panel for where the Graphics Renderer will draw the current
		// game
		gameRendererPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

				// Draw the game area
				GraphicsRenderer.redraw(g, new Point(
						renderWindowSize.width / 2, 300), client.getWorld(),
						name);
			}
		};
		gameRendererPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (avatar == null) {
					return;
				}

				// Relative to the center of the renderWindow
				int relativeX = e.getX() - (renderWindowSize.width / 2);
				int relativeY = e.getY() - 300;
				int gameCenterX = 0;
				int gameCenterY = 0;

				gameCenterX = (int) avatar.getRect().getCenterX();
				gameCenterY = (int) avatar.getRect().getCenterY();

				int gameX = gameCenterX + relativeX;
				int gameY = gameCenterY + relativeY;
				selectedObject = client.getGameObject(gameX, gameY);
				setInteractionPanel("Renderer");
			}
		});

		gameRendererPanel.setPreferredSize(new Dimension(renderWindowWidth,
				renderWindowHeight));
		gameRendererPanel.setOpaque(false);

		// Setup the tool bar
		setupToolBar();

		// Setup the server info area
		infoText = new JTextArea(5, 20);
		infoText.setOpaque(false);
		infoText.setEditable(false);
		infoText.setForeground(Color.red);

		// Add the components to the gamePanel
		gamePanel.add(gameRendererPanel);
		gamePanel.add(toolbarPanel);
		gamePanel.add(infoText);
	}

	/**
	 * Creates the tool bar for game panel.
	 * Toolbar includes a health bar, inventory, chat area, interaction area and an option object contents
	 */
	private void setupToolBar() {
		// Create a transparent panel to hold the toolbar content
		toolbarPanel = new CustomPanel(toolBarFile);
		toolbarPanel.setOpaque(false);

		// Create a transparent panel for the toolbar panel to hold
		toolbarContent = new JPanel();
		toolbarContent.setOpaque(false);

		// Setup the health bar
		JPanel healthPanel = new JPanel();
		healthPanel.setOpaque(false);
		healthPanel.setLayout(new BoxLayout(healthPanel, BoxLayout.Y_AXIS));

		healthBar = new JProgressBar(0, 100);
		healthBar.setOrientation(SwingConstants.VERTICAL);
		healthBar.setString("Health");
		healthBar.setStringPainted(true);
		healthBar.setUI(new BasicProgressBarUI() {
			protected Color getSelectionBackground() {
				return Color.black;
			}

			protected Color getSelectionForeground() {
				return Color.black;
			}
		});

		// Setup the inventory
		JPanel inventoryPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.translate(0, 25);
				if (avatar != null) {
					GraphicsRenderer.drawInventory(g, avatar);
				}
				// GraphicsRenderer.drawInventory(g, chest);
			}
		};

		// Respond to an item being selected
		inventoryPanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// Left Click on item
				if (e.getButton() == 1) {
					int inventoryX = e.getX();
					int inventoryY = e.getY() - 25;
					int i = 0;
					// Find which item was selected
					for (int y = 0; y < (38 * 4); y = y + 38) {
						for (int x = 0; x < (38 * 4); x = x + 38) {
							if (x <= inventoryX && inventoryX <= (x + 38)
									&& y <= inventoryY
									&& inventoryY <= (y + 38)) {
								selectedObject = client.getInventoryItem(i);
								setInteractionPanel("Inventory");
								return;
							}
							i++;
						}
					}
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		inventoryPanel.setOpaque(false);
		inventoryPanel.setPreferredSize(new Dimension(150, 175));

		// Setup a chatPanel to hold the chatArea (displays text) and chatBox
		// (sends text)
		final JPanel chatPanel = new JPanel();
		chatPanel.setOpaque(false);
		chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));

		// Setup a chatArea for where the chat text will be displayed
		chatArea = new JTextArea(10, 20);

		// Make the chatArea scroll
		JScrollPane textSP = new JScrollPane(chatArea);
		textSP.setPreferredSize(new Dimension((toolBarHeight / 2),
				toolBarHeight / 2));

		// Change the text inside the chatArea
		chatArea.setForeground(Color.black);
		chatArea.setBackground(Color.white);
		Font font = new Font("Verdana", Font.BOLD, 10);
		chatArea.setFont(font);
		chatArea.setLineWrap(true);
		chatArea.setEditable(false);

		// Setup a chatBox for where you type and send the chat
		final JTextField chatBox = new JTextField(16);
		chatBox.setPreferredSize(new Dimension((toolBarHeight / 2) + 20, 20));
		chatBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Sends chat message to all the other charAreas
				JTextField textField = (JTextField) e.getSource();
				String text = textField.getText();
				try {
					// Sents text to the client
					client.sendChat(name, text);
					appendChatArea(name, text);

					// Empties the chatBox
					chatBox.setText("");

					// Sets the focus to the frame for mouse clicks
					frame.requestFocusInWindow();
				} catch (IOException e1) {
					return;
				}
			}

		});

		// Scrolls to the latest text all the time
		DefaultCaret caret = (DefaultCaret) chatArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		// Add an panel to hold all of the interaction buttons
		interactionPanel = new JPanel();
		TitledBorder title = BorderFactory
				.createTitledBorder("Selected Object");
		title.setTitleJustification(TitledBorder.CENTER);
		interactionPanel.setBorder(title);
		interactionPanel.setOpaque(false);
		interactionPanel.setPreferredSize(new Dimension(200, 150));

		// Add healthBar to healthPanel
		healthPanel.add(Box.createRigidArea(new Dimension(0, 25)));
		healthPanel.add(healthBar);

		// Add components to the chatPanel
		chatPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		chatPanel.add(textSP);
		chatPanel.add(chatBox);

		// Adds components to the toolbarContent
		toolbarContent.add(healthPanel);
		toolbarContent.add(inventoryPanel);
		toolbarContent.add(chatPanel);
		toolbarContent.add(interactionPanel);

		// Adds the toolbar content to the toolbarPanel
		toolbarPanel.add(toolbarContent);

	}

	/**
	 * Append text to the chatArea
	 *
	 * @param Text
	 *            to be appended
	 */
	public void appendChatArea(String name, String text) {
		chatArea.append(name + ": " + text + "\n");
	}

	/**
	 * Changes the interaction panel according to the selected object.
	 * This method is called when an object is selected from either the renderer or the inventory.
	 * Buttons are displayed in the panel depending on the type of the selected object.
	 * A label of the selected object is also displayed
	 * 
	 * @param origin is equal to "Renderer" if the object was selected from the renderPanel
	 * Or if it is equal to "Inventory" if it was from the inventoryPanel.
	 */
	public void setInteractionPanel(String origin) {
		if (interactionPanel != null) {
			// Creates a new panel to replace interactionPanel
			newInteractionPanel = new JPanel();
			TitledBorder title = BorderFactory
					.createTitledBorder("Selected Object");
			title.setTitleJustification(TitledBorder.CENTER);
			newInteractionPanel.setBorder(title);
			newInteractionPanel.setOpaque(false);
			newInteractionPanel.setPreferredSize(new Dimension(200, 150));

			if (objectContents != null) {
				toolbarContent.remove(objectContents);
			}

			// Adds buttons according to the selected item
			if (selectedObject != null) {

				JLabel description = new JLabel(selectedObject.getDescription());
				description.setPreferredSize(new Dimension(180, 20));
				description.setHorizontalAlignment(SwingConstants.CENTER);
				newInteractionPanel.add(description);

				// Item is a container
				if (selectedObject instanceof Container
						&& !(selectedObject instanceof Avatar)) {

					// Add an open item button
					JButton open = new JButton("Open Item");
					newInteractionPanel.add(open);

					open.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// Setup the inventory
							if (objectContents != null) {
								toolbarContent.remove(objectContents);
							}

							objectContents = new JPanel() {
								@Override
								public void paintComponent(Graphics g) {
									super.paintComponent(g);
									g.translate(0, 25);
									GraphicsRenderer.drawInventory(g,
											((Container) selectedObject));
								}
							};
							objectContents.setOpaque(false);
							objectContents.setPreferredSize(new Dimension(150,
									175));
							toolbarContent.add(objectContents);
							toolbarContent.revalidate();
							frame.requestFocusInWindow();
							frame.repaint();
						}
					});

					// Add a move all items to inventory button
					if (origin.equals("Renderer")) {
						JButton move = new JButton(
								"Move All Items To Inventory");
						newInteractionPanel.add(move);

						move.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								client.moveAllToInventory(selectedObject);

								if (objectContents != null) {
									toolbarContent.remove(objectContents);
								}
								newInteractionPanel.removeAll();
								toolbarContent.revalidate();
								frame.requestFocusInWindow();
								frame.repaint();
							}
						});
					}

				}

				// Item is food
				if (selectedObject instanceof Food) {

					// Add an eat button
					if (origin.equals("Inventory")) {
						JButton eat = new JButton("Eat");
						newInteractionPanel.add(eat);

						eat.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								// Setup the inventory
								client.eatObject(selectedObject);
								newInteractionPanel.removeAll();
								toolbarContent.revalidate();
								frame.requestFocusInWindow();
								frame.repaint();
							}
						});
					}

					frame.requestFocusInWindow();
				}

				// Item is a containable
				if (selectedObject instanceof Containable) {

					// Add a move item to inventory button
					if (origin.equals("Renderer")) {
						JButton move = new JButton("Move Item To Inventory");
						newInteractionPanel.add(move);

						move.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								boolean hasTorch = false;
								boolean hasKey = false;
								for (GameObject gObject : avatar.getItems()) {
									if (gObject instanceof Torch) {
										hasTorch = true;
									}
									if (gObject instanceof Key) {
										hasKey = true;
									}
								}
								if ((selectedObject instanceof Torch && hasTorch)
										|| (selectedObject instanceof Key && hasKey)) {
								} else {
									client.pickupObject(selectedObject);
								}
								newInteractionPanel.removeAll();
								toolbarContent.revalidate();
								frame.requestFocusInWindow();
								frame.repaint();
							}
						});
					}

					// Add a drop item button
					if (origin.equals("Inventory")) {
						JButton drop = new JButton("Drop Item");
						newInteractionPanel.add(drop);

						drop.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								// Setup the inventory
								client.dropObject(selectedObject);
								newInteractionPanel.removeAll();
								toolbarContent.revalidate();
								frame.requestFocusInWindow();
								frame.repaint();
							}
						});
					}

				}

				// Item is an external door
				if (selectedObject instanceof ExternalDoor) {

					JButton externalDoor = new JButton("Leave Location");
					newInteractionPanel.add(externalDoor);

					externalDoor.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							client.moveThroughDoor((ExternalDoor) selectedObject);
							newInteractionPanel.removeAll();
							frame.repaint();
							frame.requestFocusInWindow();
						}
					});
				}

				// Item is an internal door
				if (selectedObject instanceof InternalDoor) {

					final InternalDoor door = (InternalDoor) selectedObject;

					// Add a open/close door button
					final JButton internalDoor = new JButton(
							door.isLocked() ? "Open Door" : "Close Door");
					internalDoor.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (door.isLocked()) {
								for (GameObject gObject : avatar.getItems()) {
									if (gObject instanceof Key) {
										client.openDoor(door);
										break;
									}
								}
							} else {
								for (GameObject gObject : avatar.getItems()) {
									if (gObject instanceof Key) {
										client.closeDoor(door);
										break;
									}
								}
							}
							newInteractionPanel.removeAll();
							toolbarContent.revalidate();
							frame.requestFocusInWindow();
							frame.repaint();
						}
					});
					newInteractionPanel.add(internalDoor);

					frame.requestFocusInWindow();
				}

				// Item is an internal door
				if (selectedObject instanceof Altar) {

					// Add an add piller button
					JButton addRelic = new JButton("Add Relic");
					newInteractionPanel.add(addRelic);

					addRelic.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							client.addRelicsToAltar();
							newInteractionPanel.removeAll();
							toolbarContent.revalidate();
							frame.requestFocusInWindow();
							frame.repaint();
						}
					});

				}

			} else {
			}

			toolbarContent.remove(interactionPanel);
			interactionPanel = newInteractionPanel;
			toolbarContent.add(interactionPanel);

			toolbarContent.revalidate();
			toolbarContent.repaint();
		}
	}

	/**
	 * Responds to a key press from the user and sends it to the client
	 */
	@Override
	public void keyPressed(KeyEvent ev) {
		if (ev.getSource() == frame) {
			if (client != null) {
				try {
					client.sendAction(name, ev);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else if (ev.getSource() == chatArea) {
		}
	}

	/**
	 * Responds to a mouse released event from the user and sends it to the client
	 */
	@Override
	public void mouseReleased(MouseEvent ev) {
		// Sends x and y to the client
		if (client != null) {
			try {
				client.sendAction(name, ev);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** Unused Method */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/** Unused Method */
	@Override
	public void keyReleased(KeyEvent e) {
	}

	/** Unused Method */
	@Override
	public void focusGained(FocusEvent e) {
	}

	/** Unused Method */
	@Override
	public void focusLost(FocusEvent e) {
	}

	/** Unused Method */
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	/** Unused Method */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	/** Unused Method */
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	/** Unused Method */
	@Override
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * For writing network information to the to the ApplicationWindow.
	 * E.g. The ping to the server
	 * @return The text area for the text to be written to
	 */
	public JTextArea getInfoText() {
		return this.infoText;
	}

	/**
	 * For writing to the chat console
	 * @return The text area for with network chat on it
	 */
	public JTextArea getChatConsole() {
		return this.chatArea;
	}

	/**
	 * Updates ApplicationWindow with the most resent version of of GameWorld as well as Avatar. 
	 * Also calls repaint so the the GraphicsRenderer can draw the latest version of the GameWorld.
	 * 
	 * @param The most recent version of GameWorld
	 */
	public void redraw(GameWorld world) {
		this.gameWorld = world;
		for (Avatar a : gameWorld.getAvatars()) {
			if (a.getName().equals(name)) {
				this.avatar = a;
			}
		}

		if (avatar != null) {
			int health = avatar.getHealth();
			healthBar.setValue(health);
			if (health > 75) {
				healthBar.setForeground(Color.green);
			} else if (health > 50) {
				healthBar.setForeground(Color.yellow);
			} else if (health > 25) {
				healthBar.setForeground(Color.orange);
			} else {
				healthBar.setForeground(Color.red);
			}
		}

		if (gameWorld.isWin()) {
			if (isWon) {
				frame.repaint();
				return;
			}
			MediaPlayer.stopCurrent();
			MediaPlayer.playWinning();
			chatArea.append("Player " + (world.getWinner() + 1)
					+ " has won the game!\n");
			isWon = true;
		}

		frame.repaint();
	}

	/**
	 * Unused Method
	 */
	public int getUid() {
		return uid;
	}

	/**
	 * Unused Method
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}

	/**
	 * Returns the name of the player linked to this ApplicationWindow
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Main Method that starts ApplicationWindow
	 */
	public static void main(String[] args) {
		new ApplicationWindow();
	}

}
