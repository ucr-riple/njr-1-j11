package be.demmel.fun.jgwcaconstants.bll.clean;

import be.demmel.fun.jgwcaconstants.bll.GWCAOperations;
import java.io.IOException;

public class QuestLogPanel {
    private final GWCAOperations gwcaOperations;
    
    public QuestLogPanel(GWCAOperations gwcaOperations) {
        this.gwcaOperations = gwcaOperations;
    }
    
    /**
     * @return the quest id of the currently active quest
     * @throws IOException 
     */
    public int getActiveQuest() throws IOException {
        return this.gwcaOperations.questActive();
    }
    
    /**
     * Abandons the specified quest.
     * @param questId
     * @throws IOException 
     */
    public void abandonQuest(int questId) throws IOException {
       this.gwcaOperations.questAbandon(questId);
    }
    
    /**
     * @param questId (or -1 for active quest)
     * @return the X,Y coordinates of the marker
     * @throws IOException 
     */
    public float[] getQuestCoords(int questId) throws IOException {
        return this.gwcaOperations.questCoords(questId);
    }
    
    /**
     * @param questId  (or -1 for active quest)
     * @return  the quest id and the current log state
     * @throws IOException 
     */
    public int[] questCheck(int questId) throws IOException {
        return this.gwcaOperations.questCheck(questId);
    }
}
