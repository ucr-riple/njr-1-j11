package GUI.TextDisplay;

import java.awt.event.MouseEvent;

import Game.*;
import Shirage.Shire;

public class ShireString extends ClickableString {
	private Shire shire;
	public ShireString(Shire s, Papyrus P) {super(s.getName(), P);   shire = s;}
	

    public void mousePressed(MouseEvent e) {
    	AGPmain.mainGUI.SM.loadShire(shire);
    }
	
}
