package GUI;

import GUI.TextDisplay.Papyrus;

public class ScrollSlidePanel extends ASlidePanel {
	
	public ScrollSlidePanel(PopupAbstract P, Papyrus[] S) {
		super(P);
		scroll = S;
		//setScrolls();
	}
	public void redefineClan() {
		setScrolls();
	}

	
}