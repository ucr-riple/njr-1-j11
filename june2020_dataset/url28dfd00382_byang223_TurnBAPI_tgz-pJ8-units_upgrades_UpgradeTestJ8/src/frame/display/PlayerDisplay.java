package frame.display;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import modes.selections.Selections;
import player.Player;

/**
 * Displays player statistics
 * @author bryanyang
 *
 */
public class PlayerDisplay extends Display {

	private Player myPlayer;

	public PlayerDisplay(BufferedImage i, int x, int y) {
		super(i);
		super.setLocation(x, y);
		
	}
	
	public PlayerDisplay(Player p, BufferedImage i, int x, int y, int dx, int dy){
		this(i, x, y);
		super.setSize(dx, dy);
		myBackground.resize(super.getWidth(), super.getHeight());
		myPlayer = p;
		
	}

	@Override
	public void render(Graphics2D g) {

		myBackground.render(g, getXLocation(), getYLocation());

		if(Selections.getCurrentPlayer()==myPlayer)
			super.getFont().drawString(g, myPlayer.getPlayerName().toUpperCase() + "*", getXLocation(), getYLocation());
		else
			super.getFont().drawString(g, myPlayer.getPlayerName().toUpperCase(), getXLocation(), getYLocation());
		
        if (myPlayer.getPlayerExperience() != null) {
            int exp = myPlayer.getPlayerExperience().getData();
            super.getFont().drawString(g, "EXP: "+exp, getXLocation(), getYLocation() + 20);
        }
        
        if (myPlayer.getPlayerLevel() != null) {
            int lvl = myPlayer.getPlayerLevel().getData();
            super.getFont().drawString(g, "LVL: "+lvl, getXLocation(), getYLocation() + 40);
        }
        if (myPlayer.getPlayerKills() != null) {
            int kill = myPlayer.getPlayerKills().getKillCount();
            super.getFont().drawString(g, "KILL: "+ kill, getXLocation(), getYLocation() + 60);
        }

	}


}
