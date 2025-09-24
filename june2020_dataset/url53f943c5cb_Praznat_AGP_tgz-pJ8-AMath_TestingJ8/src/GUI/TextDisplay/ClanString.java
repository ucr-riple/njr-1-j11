package GUI.TextDisplay;

import java.awt.event.MouseEvent;

import Game.*;
import Sentiens.Clan;

public class ClanString extends ClickableString {
	private Clan clan;
	public ClanString(Clan c, Papyrus P) {super(c.getNomen(), P);   clan = c;}
	

    public void mousePressed(MouseEvent e) {
    	AGPmain.mainGUI.GM.loadClan(clan);
    }
	
}
