package frame.display;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import frame.background.TBBackground;

/**
 * Blank displays mainly used for testing our own background system and layouts.
 * @author bryanyang
 *
 */
public class BlankDisplay extends Display {

	public BlankDisplay(int x, int y, int dx, int dy, String s) {
		super(x, y);
		try {
			myBackground = new TBBackground(ImageIO.read(new File(
			        "resources/TBBackgrounds/" + s.toUpperCase() + ".jpg")));
		} catch (IOException e) {
			System.out.println(e);
		}
		super.setSize(dx, dy);
		myBackground.resize(super.getWidth(), super.getHeight());
	}

	public void render(Graphics2D g) {

		myBackground.render(g, super.getXLocation(), super.getYLocation());

	}

}
