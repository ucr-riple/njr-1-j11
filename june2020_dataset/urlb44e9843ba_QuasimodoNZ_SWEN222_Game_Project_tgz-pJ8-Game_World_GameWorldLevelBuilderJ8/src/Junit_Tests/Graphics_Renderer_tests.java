package Junit_Tests;

/**
 *
 */

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Game_World.BaseLocations;
import Game_World.Location;
import Graphics_Renderer.GraphicsRenderer;
import Graphics_Renderer.Tileset;

/**
 * @author riddelbenj
 *
 */
public class Graphics_Renderer_tests {

	/********** Setting up a canvas for the tests to be drawn on **************/

	private static int windowSize = 800;
	private JFrame frame;
	private JComponent drawing;
	private Image toBeDrawn = null;
	private String action = null;

	private double dx = 0;
	private double dy = 0;

	private Point prevMousePos = null;

	Tileset tilesForInventory;

	Location room;

	private int x = 0;
	private int y = 0;
	private int i = 0;

	@SuppressWarnings("serial")
	public Graphics_Renderer_tests() {
		tilesForInventory = new Tileset("Images/tilesets/420tiles.png", 34);

		room = BaseLocations.getCredits();// Class_Examples.getRoomWithWall();
		// Class_Examples.getAlterRoom();
		// //
		// Class_Examples.getEmptyLocation();

		frame = new JFrame("Adventure Game");
		frame.setSize(windowSize, windowSize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(new ImageIcon("Images/testIcon.gif").getImage());

		// Adds the canvas
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
				System.out.printf("Mouse is at (%d,%d)\n",
						(int) (-dx + e.getPoint().x),
						(int) (-dy + e.getPoint().y));
				prevMousePos = null;
			}
		});

		JPanel panel = new JPanel();
		JButton getTileIntInt = new JButton("getTile(int, int)");
		getTileIntInt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				action = null;

				// This tests the primary method and will loop though the whole
				// image
				toBeDrawn = tilesForInventory.getTile(x, y);
				drawing.repaint();
				x++;
				if (x >= tilesForInventory.getColumns()) {
					y++;
					x = 0;
				}
				if (y >= tilesForInventory.getRows())
					y = 0;
			}
		});
		JButton getTileInt = new JButton("getTile(int)");
		getTileInt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				action = null;
				toBeDrawn = tilesForInventory.getTile(i);
				drawing.repaint();
				if (i < tilesForInventory.getColumns()
						* tilesForInventory.getRows())
					i++;
				else
					i = 0;
			}
		});

		JButton drawInventory = new JButton(
				"drawInventory(Graphics, Container)");
		drawInventory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toBeDrawn = null;
				action = "inventory";
				drawing.repaint();
			}
		});

		JButton combineTiles = new JButton("Room.draw(Graphics)");
		combineTiles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toBeDrawn = null;
				action = "drawRoom";
				drawing.repaint();
			}
		});

		panel.add(getTileIntInt);
		panel.add(getTileInt);
		panel.add(drawInventory);
		panel.add(combineTiles);

		frame.add(drawing, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.SOUTH);
		frame.setVisible(true);
	}

	private void redraw(Graphics g) {
		g.translate((int) dx, (int) dy);

		if (toBeDrawn == null) {

			if (action != null && action.equals("inventory"))
				GraphicsRenderer.drawInventory(g, Class_Examples.getChest());
			else if (action != null && action.equals("drawRoom"))
				room.draw(g);
		} else
			g.drawImage(toBeDrawn, 0, 0, null);
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Graphics_Renderer_tests obj = new Graphics_Renderer_tests();
	}
}
