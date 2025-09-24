package nz.net.initial3d.test;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import nz.net.initial3d.util.DisplayWindow;

public class DisplayWindowTest {

	public static void main(String[] args) throws Exception {

		DisplayWindow win = DisplayWindow.create(800, 600);
		win.setVisible(true);

		TestListener tl = new TestListener();

		win.addKeyListener(tl);
		win.addMouseListener(tl);
		win.addMouseMotionListener(tl);
		win.addMouseWheelListener(tl);


	}

	private static class TestListener implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			System.out.println("Mouse wheel moved");
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			System.out.println("Mouse dragged");
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			System.out.println("Mouse moved");
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("Mouse clicked");
		}

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("Mouse pressed");
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println("Mouse released");
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			System.out.println("Mouse entered");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			System.out.println("Mouse exited");
		}

		@Override
		public void keyTyped(KeyEvent e) {
			System.out.println("Key typed: " + e.getKeyChar());
		}

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("Key pressed: " + e.getKeyCode());
		}

		@Override
		public void keyReleased(KeyEvent e) {
			System.out.println("Key released: " + e.getKeyCode());
		}

	}

}
