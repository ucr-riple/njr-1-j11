package be.demmel.fun.jgwcaconstants.bll.clean;

import be.demmel.fun.jgwcaconstants.bll.GWCAOperations;
import java.io.IOException;

public class TitlesTab {
    private final GWCAOperations gwcaOperations;

    public TitlesTab(GWCAOperations gwcaOperations) {
        this.gwcaOperations = gwcaOperations;
    }
    
    /**
     * @return how many treasure title points that you have
     */
    public int getTitleTreasure() throws IOException {
        return this.gwcaOperations.getTitleTreasure();
    }
    
    /**
     * @return how many lucky title points that you have
     */
    public int getTitleLucky() throws IOException {
        return this.gwcaOperations.getTitleLucky();
    }
    
    /**
     * @return how many unlucky title points that you have
     */
    public int getTitleUnlucky() throws IOException {
        return this.gwcaOperations.getTitleUnlucky();
    }
    
    /**
     * @return how many wisdom title points that you have
     */
    public int getTitleWisdom() throws IOException {
        return this.gwcaOperations.getTitleWisdom();
    }
    
    /**
     * @return how many gamer title points that you have
     */
    public int getTitleGamer() throws IOException {
        return this.gwcaOperations.getTitleGamer();
    }
    
    /**
     * @return how many sunspear title points that you have
     */
    public int getTitleSunspear() throws IOException {
        return this.gwcaOperations.getTitleSunspear();
    }
    
    /**
     * @return how many lightbringer title points that you have
     */
    public int getTitleLightbringer() throws IOException {
        return this.gwcaOperations.getTitleLightbringer();
    }
    
    /**
     * @return how many vanguard title points that you have
     */
    public int getTitleVanguard() throws IOException {
        return this.gwcaOperations.getTitleVanguard();
    }
    
    /**
     * @return how many norn title points that you have
     */
    public int getTitleNorn() throws IOException {
        return this.gwcaOperations.getTitleNorn();
    }
    
    /**
     * @return how many asura title points that you have
     */
    public int getTitleAsura() throws IOException {
        return this.gwcaOperations.getTitleAsura();
    }
    
    /**
     * @return how many deldrimor title points that you have
     */
    public int getTitleDeldrimor() throws IOException {
        return this.gwcaOperations.getTitleDeldrimor();
    }
    
    /**
     * @return how many north mastery title points that you have
     */
    public int getTitleNorthMastery() throws IOException {
        return this.gwcaOperations.getTitleNorthMastery();
    }
    
    /**
     * @return how many drunkard title points that you have
     */
    public int getTitleDrunkard() throws IOException {
        return this.gwcaOperations.getTitleDrunkard();
    }
    
    /**
     * @return how many sweet title points that you have
     */
    public int getTitleSweet() throws IOException {
        return this.gwcaOperations.getTitleSweet();
    }
    
    /**
     * @return how many party title points that you have
     */
    public int getTitleParty() throws IOException {
        return this.gwcaOperations.getTitleParty();
    }
    
    /**
     * @return how many commander title points that you have
     */
    public int getTitleCommander() throws IOException {
        return this.gwcaOperations.getTitleCommander();
    }
}
