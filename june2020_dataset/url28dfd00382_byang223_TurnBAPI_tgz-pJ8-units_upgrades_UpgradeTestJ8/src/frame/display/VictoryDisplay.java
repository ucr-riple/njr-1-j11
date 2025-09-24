package frame.display;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import modes.selections.Selections;
import player.Player;

/**
 * Displays game status on who wins
 * @author bryanyang
 *
 */
public class VictoryDisplay extends Display {

	public VictoryDisplay(BufferedImage i, int x, int y) {
		super(i);
		super.setLocation(x, y);

	}

	public VictoryDisplay(BufferedImage i, int x, int y, int dx, int dy) {
		this(i, x, y);
		super.setSize(dx, dy);
		myBackground.resize(super.getWidth(), super.getHeight());

	}

	public void render(Graphics2D g) {

		myBackground.render(g, getXLocation(), getYLocation());

		if (!Selections.getGameOver()) {
			super.getFont().drawString(g, "GAME ON", getXLocation(), getYLocation());
		} else {
			Player winner;
			for (Player p : Selections.getPlayerList()) {
			    if (!p.equals(Selections.getCurrentPlayer())) {
			        winner = p;
			        super.getFont().drawString(g,
		                    winner.getPlayerName().toUpperCase() + " WINS!!", getXLocation(),
		                    getYLocation());
			    }
			}
		

		}

	}

}
