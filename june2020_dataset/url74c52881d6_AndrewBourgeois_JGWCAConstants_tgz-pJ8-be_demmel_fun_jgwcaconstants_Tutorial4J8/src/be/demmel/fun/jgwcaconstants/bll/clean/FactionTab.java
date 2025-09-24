package be.demmel.fun.jgwcaconstants.bll.clean;

import be.demmel.fun.jgwcaconstants.bll.GWCAOperations;
import java.io.IOException;

public class FactionTab {
    private final GWCAOperations gwcaOperations;

    public FactionTab(GWCAOperations gwcaOperations) {
        this.gwcaOperations = gwcaOperations;
    }
    
    /**
     * @return your character's Balthazar faction
     */
    public int getBalthazarFaction() throws IOException {
        return this.gwcaOperations.getBalthazarFaction();
    }
    
    /**
     * @return your character's Kurzick faction
     */
    public int getKurzickFaction() throws IOException {
        return this.gwcaOperations.getKurzickFaction();
    }
    
    /**
     * @return your character's Luxon faction
     */
    public int getLuxonFaction() throws IOException {
        return this.gwcaOperations.getLuxonFaction();
    }
}
