package units.interactions;

import java.awt.Graphics2D;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.util.ImageUtil;

import frame.TBComponent;

/**
 * The actual sprite of the button that will be displayed on screen. Uses a
 * provided Image from the InteractionUnitButton.
 * 
 * @author Matthew
 * 
 */
public class ButtonSprite extends Sprite implements TBComponent {
	private int xLoc, yLoc;
	private InteractionUnitButton myInteractionButton;

	public ButtonSprite(InteractionUnitButton myInteraction) {
		myInteractionButton = myInteraction;
		this.setImage(myInteractionButton.buttonImage().getImage());
	}

	public InteractionUnitButton getInteractionButton() {
		return myInteractionButton;
	}

	public void setLocation(int x, int y) {
		xLoc = x;
		yLoc = y;
		super.setLocation(x, y);
	}

	public int getXLocation() {
		return xLoc;
	}

	public int getYLocation() {
		return yLoc;
	}

	public void resize(int dx, int dy) {
		setImage(ImageUtil.resize(this.getImage(), dx, dy));
	}

	public void render(Graphics2D g) {
		super.render(g, xLoc, yLoc);
	}

}
