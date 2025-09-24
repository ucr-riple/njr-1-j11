package GUI;

import java.awt.event.MouseEvent;

import Defs.GlobalParameters;
import Game.*;
import Shirage.Shire;

public class PopupShire extends PopupAbstract {
	private Shire curShire;
	private TableSlidePanel infoboxes[] = new TableSlidePanel[5];
	private int[][] selectedVGs = {{}, {0, 1, 2, 3, 4}, {}, {0, 1, 2, 3, 4}, {}};
	public static final int ENVIRONMENT = 0;
	public static final int POPULATION = 1;
	public static final int BUILDINGS = 2;
	public static final int MARKETS = 3;
	public static final int HISTORY = 4;
	
	public PopupShire(GUImain P) {
		super(P);

		INFO[ENVIRONMENT] = "ENVIRONMENT";
		INFO[POPULATION] = "POPULATION";
		INFO[BUILDINGS] = "BUILDINGS";
		INFO[MARKETS] = "MARKETS";
		INFO[HISTORY] = "HISTORY";
		
		selectedVGs[POPULATION] = new int[] {0,GlobalParameters.SHOW_CREED?5:1,4,6,2};
		selectedVGs[MARKETS] = new int[] {VarGetter.GOODNAME,VarGetter.BID,VarGetter.ASK,
				VarGetter.LAST,VarGetter.STAVG,VarGetter.LTAVG,
				VarGetter.BIDSZ,VarGetter.ASKSZ};
		
		for(int i = 0; i < infoboxes.length; i++) {
			infoboxes[i] = new TableSlidePanel(this, i);
			info.add(infoboxes[i], INFO[i]);
			slider.addCon(INFO[i]);
		}
	}
	
	public void setBounds(int x, int y, int w, int h) {
		super.setBounds(x,y,w,h);
		h = namebox.getHeight()+2*w/3;
		slider.setBounds(0,h, w, 25);
		h = slider.getY()+slider.getHeight();
		sp.setBounds(0, h, w, getHeight()-h);
		slider.refresh();
	}
	
	public Shire getShire() {return curShire;}

	@Override
    public void load() {if (curShire != null) {loadShire(curShire);}}
    public void loadShire(Shire s) {
    	if (s == null) {return;}
    	curShire = s;
    	namebox.setNomen(curShire.getName());
    	for (int i = 0; i < infoboxes.length; i++) {infoboxes[i].redefineShire();}
    	initialized = true;
    	this.refreshAll();
    }
    
    public void selectVG(int plc, int vg) {selectedVGs[curTab][plc] = vg;}
    public int selectedVGLength(int t) {return selectedVGs[t].length;}
    public int getVG(int t, int plc) {return selectedVGs[t][plc];}
    
    @Override
	protected void hideUnhideStuff() {
		super.hideUnhideStuff();
		infoboxes[0].setVisible(vizible);
	}
    
    public void mouseClicked(MouseEvent e) {
    	if (hideUnhide(e.getY())) {return;}
    	if (vizible) {
    		loadShire(AGPmain.TheRealm.getRandClan().myShire());
    	}
    }
}
