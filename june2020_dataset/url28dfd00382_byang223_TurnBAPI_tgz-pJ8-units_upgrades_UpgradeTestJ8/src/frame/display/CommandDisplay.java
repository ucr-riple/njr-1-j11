package frame.display;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import modes.selections.Selections;
import units.Unit;
import units.interactions.ButtonSprite;
import units.interactions.ButtonSpriteFactory;

import com.golden.gamedev.object.SpriteGroup;

import frame.TBComponent;
import frame.layout.container.LayoutContainer;
import frame.layout.container.VerticalLayoutContainer;

/**
 * Display selected unit buttons
 * @author bryanyang
 *
 */
public class CommandDisplay extends Display {

	private LayoutContainer lc;

	public CommandDisplay(BufferedImage i, int x, int y) {
		super(i);
		super.setLocation(x, y);

	}

	public CommandDisplay(BufferedImage i, int x, int y, int dx, int dy) {
		this(i, x, y);
		super.setSize(dx, dy);
		lc = new VerticalLayoutContainer(x, y, super.getWidth(), super.getHeight());
		myBackground.resize(super.getWidth(), super.getHeight());

	}

	public void render(Graphics2D g) {

		myBackground.render(g, getXLocation(), getYLocation());
		lc.setLocation(getXLocation(), getYLocation());
		lc.setSize(getWidth(), getHeight());

		Unit s = Selections.getSelectedUnit();

		if (s != null) {

			ButtonSpriteFactory b = new ButtonSpriteFactory(s);

			ArrayList<ButtonSprite> buttons = b.getButtonSpriteList();
			ArrayList<TBComponent> comps = new ArrayList<TBComponent>();

			for (ButtonSprite temp : buttons) {
				comps.add(temp);
			}

			SpriteGroup newgroup = new SpriteGroup("buttons");

			lc.addAll(comps);
			lc.update();

			for (ButtonSprite temp : buttons) {
				temp.render(g);
				newgroup.add(temp);
			}

			Selections.setButtonGroup(newgroup);
		} else {
			Selections.setButtonGroup(null);
		}

	}

}
