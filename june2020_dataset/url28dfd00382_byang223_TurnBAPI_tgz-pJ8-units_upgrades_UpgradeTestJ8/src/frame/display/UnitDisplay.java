package frame.display;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import modes.selections.Selections;
import units.Unit;

/**
 * Displays selected unit statistics
 * @author bryanyang
 *
 */
public class UnitDisplay extends Display {

	public UnitDisplay(BufferedImage i, int x, int y) {
		super(i);
		super.setLocation(x, y);

	}

	public UnitDisplay(BufferedImage i, int x, int y, int dx, int dy) {
		this(i, x, y);
		super.setSize(dx, dy);
		myBackground.resize(super.getWidth(), super.getHeight());

	}

	public void render(Graphics2D g) {

		myBackground.render(g, super.getXLocation(), super.getYLocation());

		Unit s = Selections.getSelectedUnit();

		if (s != null) {
			super.getFont().drawString(g, s.unitName().toUpperCase(), super.getXLocation(), super.getYLocation());

		}

	}

}
