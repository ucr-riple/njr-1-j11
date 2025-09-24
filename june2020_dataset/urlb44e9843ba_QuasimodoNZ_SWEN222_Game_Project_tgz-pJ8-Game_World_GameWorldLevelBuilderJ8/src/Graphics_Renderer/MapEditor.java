package Graphics_Renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MapEditor {
	private JFrame frame;
	private JComponent drawing;
	private int windowSize = 800;
	private JComboBox<ImageLayer> imageLayers;
	private ImageLayer layerCreated = null;
	List<List<JTextField>> textFields = null;

	private double dx = 0;
	private double dy = 0;

	private Point prevMousePos = null;

	public MapEditor() {
		setupInterface();
	}

	@SuppressWarnings("serial")
	private void setupInterface() {
		// Set up a window .
		frame = new JFrame("Graphics Example");
		frame.setSize(windowSize, windowSize);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		drawing = new JComponent() {
			@Override
			protected void paintComponent(Graphics g) {
				redraw(g);
			}
		};

		drawing.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point nextMousePos = e.getPoint();
				if (prevMousePos != null) {
					double xDiff = nextMousePos.getX() - prevMousePos.getX();
					double yDiff = nextMousePos.getY() - prevMousePos.getY();
					dy += yDiff;
					dx += xDiff;
					drawing.repaint();

				}

				prevMousePos = e.getPoint();
			}
		});

		drawing.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				prevMousePos = e.getPoint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				prevMousePos = null;
			}
		});

		final JPanel panel = new JPanel();

		// Initilises the combo box for the lighting rig
		imageLayers = new JComboBox<ImageLayer>();
		imageLayers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		// Adds the button for creating a new light in the current model
		final JButton newLayer = new JButton("new");
		newLayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				final JDialog creator = layerCreator();
				if (layerCreated != null) {
					drawing.repaint();
					imageLayers.addItem(layerCreated);
					layerCreated = null;
				}
			}

		});

		// Adds the button for editing the currently selected light in the combo
		// box
		final JButton editLayer = new JButton("edit");
		editLayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				IDsEditor();
			}
		});

		// Adds the button for deleting the currently selected light in the
		// combo box
		JButton deleteLayer = new JButton("delete");
		deleteLayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (imageLayers.getSelectedItem() != null) {
					imageLayers.removeItem(imageLayers.getSelectedItem());
				}
			}

		});

		// Adds the button for deleting the currently selected light in the
		// combo box
		JButton repaintButton = new JButton("repaint");
		repaintButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				drawing.repaint();
			}

		});
		// Adds the button for deleting the currently selected light in the
		// combo box
		JButton createFile = new JButton("output to file");
		createFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createOutputFile();
			}
		});
		panel.add(imageLayers);
		panel.add(newLayer);
		panel.add(editLayer);
		panel.add(deleteLayer);
		panel.add(repaintButton);

		panel.add(createFile);

		frame.add(drawing, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.NORTH);
		frame.setVisible(true);
	}

	private JDialog layerCreator() {
		// Creates the dialog and adds the panel and its grid to go in it.
		final JDialog dialog = new JDialog(frame, "Layer Creator");
		dialog.setLocation(200, 350);
		dialog.setModal(true);
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints grid = new GridBagConstraints();
		grid.insets = new Insets(5, 5, 5, 5);

		// Creates the text fields and the submit button
		final JTextField tilesetFilename = new JTextField(10);
		final JTextField numberOfRows = new JTextField(4);
		final JTextField numberOfColumns = new JTextField(4);

		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					int numOfRow = Integer.parseInt(numberOfRows.getText()
							.trim());
					int numOfCol = Integer.parseInt(numberOfColumns.getText()
							.trim());

					int[][] ids = new int[numOfRow][numOfCol];
					for (int i = 0; i < ids.length; i++)
						for (int j = 0; j < ids[i].length; j++)
							ids[i][j] = -1;

					layerCreated = new ImageLayer(tilesetFilename.getText(),
							ids);
				} catch (NumberFormatException exception) {
					// If text was entered or there is something wrong with the
					// user input, nothing happens
					layerCreated = null;
				}
				// Needs to dispose of the dialog upon submitting
				dialog.dispose();
			}
		});

		// Adds the vector label and the x, y and z text fields for the vector
		grid.gridwidth = 2;
		grid.gridx = 0;
		grid.gridy = 0;
		panel.add(new JLabel("Tileset filename"), grid);
		grid.gridx = 2;
		panel.add(tilesetFilename, grid);

		grid.gridwidth = 1;
		grid.gridx = 0;
		grid.gridy = 1;
		panel.add(new JLabel("Rows: "), grid);
		grid.gridx = 1;
		panel.add(numberOfRows, grid);
		grid.gridx = 2;
		panel.add(new JLabel("Columns: "), grid);
		grid.gridx = 3;
		panel.add(numberOfColumns, grid);

		// Adds the submit button
		grid.gridwidth = 4;
		grid.gridx = 0;
		grid.gridy = 2;
		panel.add(submit, grid);

		dialog.add(panel);
		dialog.pack();
		dialog.setVisible(true);
		return dialog;
	}

	private JDialog IDsEditor() {
		final ImageLayer selectedItem;
		if (imageLayers.getSelectedItem() == null) {
			System.out.println("Selected layer item is null");
			return null;
		}
		selectedItem = (ImageLayer) imageLayers.getSelectedItem();
		// Creates the dialog and adds the panel and its grid to go in it.
		final JDialog dialog = new JDialog(frame, "Layer Editor");
		dialog.setLocation(200, 350);
		dialog.setModal(true);

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints grid = new GridBagConstraints();
		grid.insets = new Insets(0, 0, 0, 0);

		textFields = new ArrayList<List<JTextField>>();
		for (int y = 0; y < selectedItem.getIDs().length; y++) {

			List<JTextField> row = new ArrayList<JTextField>();
			for (int x = 0; x < selectedItem.getIDs()[0].length; x++) {
				final JTextField ID = new JTextField(2);
				ID.setText(String.format("%d", selectedItem.getIDs()[y][x]));
				row.add(ID);
				ID.addFocusListener(new java.awt.event.FocusAdapter() {
					@Override
					public void focusGained(java.awt.event.FocusEvent evt) {
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								ID.selectAll();
							}
						});
					}
				});
			}
			textFields.add(row);
		}

		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				for (int i = 0; i < selectedItem.getIDs().length; i++)
					for (int j = 0; j < ((ImageLayer) (imageLayers
							.getSelectedItem())).getIDs()[0].length; j++) {
						selectedItem.getIDs()[i][j] = Integer
								.parseInt(textFields.get(i).get(j).getText());
					}
				// Needs to dispose of the dialog upon submitting
				dialog.dispose();
			}
		});
		grid.gridwidth = 1;
		grid.gridheight = 1;
		for (int y = 0; y < selectedItem.getIDs().length; y++)
			for (int x = 0; x < selectedItem.getIDs()[y].length; x++) {
				grid.gridx = x;
				grid.gridy = y;
				panel.add(textFields.get(y).get(x), grid);
			}

		// Adds the submit button
		grid.gridwidth = textFields.get(0).size() / 3;
		grid.gridx = textFields.get(0).size() / 3;
		grid.gridy = textFields.size() + 1;
		panel.add(submit, grid);

		dialog.add(panel);
		dialog.pack();
		dialog.setVisible(true);
		return dialog;
	}

	private void redraw(Graphics g) {
		g.translate((int) dx, (int) dy);

		// Draws each layer on the image
		for (int i = 0; i < imageLayers.getItemCount(); i++) {
			g.drawImage(imageLayers.getItemAt(i).toImage(), 0, 0, null);
		}
		System.out.printf("Graphics translated - dx: %f, dy: %f\n\n", dx, dy);

	}

	private void createOutputFile() {
		// Prepare browse dialog
		JFileChooser fc = new JFileChooser();
		fc.setFileHidingEnabled(false);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setApproveButtonText("Open");

		// Show dialog
		int rt = fc.showOpenDialog(frame); // someframe is JFrame
		if (rt == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile(); // Do anything u want with this
												// file
			BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter(file));

				for (int i = 0; i < imageLayers.getItemCount(); i++) {
					writer.write(imageLayers.getItemAt(i).toString());
					writer.newLine();
					writer.write("{");
					for (int y = 0; y < imageLayers.getItemAt(i).getIDs().length; y++) {
						writer.write("{");
						for (int x = 0; x < imageLayers.getItemAt(i).getIDs()[y].length; x++) {
							writer.write(String.format("%d", imageLayers
									.getItemAt(i).getIDs()[y][x]));
							if (x < imageLayers.getItemAt(i).getIDs()[y].length - 1)
								writer.write(",");
						}
						writer.write("}");
						if (y < imageLayers.getItemAt(i).getIDs().length - 1)
							writer.write(",");
					}
					writer.write("}");
					writer.newLine();
					writer.newLine();
					writer.newLine();
					writer.newLine();

				}
				writer.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MapEditor window = new MapEditor();

	}
}
