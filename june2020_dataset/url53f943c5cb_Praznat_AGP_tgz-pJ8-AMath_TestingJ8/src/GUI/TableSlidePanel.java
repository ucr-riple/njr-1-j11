package GUI;

import GUI.TextDisplay.Papyrus;
import Game.VarGetter;


public class TableSlidePanel extends ASlidePanel {
	VarGetter[] baseVG;
	int type;
	public TableSlidePanel(PopupAbstract P, int t) {
		super(P);
		type = t;
		if (parent().initialized()) {redefineShire();}
	}
	public void redefineShire() {
		if (type == PopupShire.ENVIRONMENT) {scroll = Papyrus.shiredescS(parent());}
		else if (type == PopupShire.POPULATION) {scroll = Papyrus.shirepopS(parent());}
		else if (type == PopupShire.MARKETS) {scroll = Papyrus.shiremktS(parent());}
		else {scroll = Papyrus.shirepopS(parent());}
		setScrolls();
	}
}
