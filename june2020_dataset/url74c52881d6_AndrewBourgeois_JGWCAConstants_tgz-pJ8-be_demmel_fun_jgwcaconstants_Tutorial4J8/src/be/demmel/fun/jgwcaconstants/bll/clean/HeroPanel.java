package be.demmel.fun.jgwcaconstants.bll.clean;

import be.demmel.fun.jgwcaconstants.bll.GWCAOperations;
import java.io.IOException;

public class HeroPanel {
    private final GWCAOperations gwcaOperations;
    private final TitlesTab titlesTab;
    private final FactionTab factionTab;

    public HeroPanel(GWCAOperations gwcaOperations) {
        this.gwcaOperations = gwcaOperations;
        this.titlesTab = new TitlesTab(gwcaOperations);
        this.factionTab = new FactionTab(gwcaOperations);
    }
    
    /**
     * @return how many experience points that you have
     */
    public int getExperience() throws IOException {
        return this.gwcaOperations.getExperience();
    }

    public FactionTab getFactionTab() {
        return factionTab;
    }

    public TitlesTab getTitlesTab() {
        return titlesTab;
    }
}
