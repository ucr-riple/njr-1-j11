package nz.net.initial3d.renderer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import nz.net.initial3d.util.DisplayWindow;

public class TestTex {

	public static void main(String[] args) throws Exception {

		Texture2DImpl tex = new Texture2DImpl(256, 256);

		BufferedImage img = ImageIO.read(new File("fractal.jpg"));

		tex.drawImage(img);

		tex.createMipMaps();

		BufferedImage img2 = tex.extractAll();

		final DisplayWindow win = DisplayWindow.create(512, 512);
		win.setVisible(true);
		win.setEventsSynchronous(true);

		win.addText(new Object() {
			@Override
			public String toString() {
				return "" + System.currentTimeMillis();
			}
		});

		win.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					final String s = Thread.currentThread().getName();
					win.addText(s);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

		});

		while (true) {
			win.display(img2);
			Thread.sleep(20);

			if (win.pollKey(KeyEvent.VK_F11)) {
				win.setFullscreen(!win.isFullscreen());
			}

			win.pushEvents();
		}

	}

}
