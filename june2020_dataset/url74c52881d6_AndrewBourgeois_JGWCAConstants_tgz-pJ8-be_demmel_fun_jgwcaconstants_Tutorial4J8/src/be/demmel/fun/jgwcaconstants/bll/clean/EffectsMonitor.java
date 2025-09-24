package be.demmel.fun.jgwcaconstants.bll.clean;

import be.demmel.fun.jgwcaconstants.bll.GWCAOperations;
import java.io.IOException;

public class EffectsMonitor {
    private final GWCAOperations gwcaOperations;

    public EffectsMonitor(GWCAOperations gwcaOperations) {
        this.gwcaOperations = gwcaOperations;
    }
    
    /**
     * @return the number of effects currently on you
     */
    public int getEffectCount() throws IOException {
        return this.gwcaOperations.getEffectCount();
    }
    
    /**
     * 
     * @param skillId
     * @return If the effect exists on you, the id of the effect is returned.
     * @throws IOException 
     */
    public int getEffect(int skillId) throws IOException {
        return this.gwcaOperations.getEffect(skillId);
    }
    
    /**
     * 
     * @param effectIndex (�-based)
     * @return the skill ID of the specified effect
     * @throws IOException 
     */
    public int getEffectByIndex(int effectIndex) throws IOException {
        return this.gwcaOperations.getEffectByIndex(effectIndex);
    }
    
    /**
     * 
     * @param skillId
     * @return  If the effect exists on you, the total duration and the current time left of the effect is returned as floats in milliseconds.
     * @throws IOException 
     */
    public int[] getEffectDuration(int skillId) throws IOException {
        return this.gwcaOperations.getEffectDuration(skillId);
    }
}
