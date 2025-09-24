package be.demmel.fun.jgwcaconstants.bll.clean;

import be.demmel.fun.jgwcaconstants.bll.GWCAOperations;
import be.demmel.fun.jgwcaconstants.bll.SkillBarSkillSlot;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkillSlot {
    
    private static final Logger LOG = LoggerFactory.getLogger(SkillSlot.class);
    private final SkillBarSkillSlot position;
    private final GWCAOperations gwcaOperations;

    public SkillSlot(GWCAOperations gwcaOperations, SkillBarSkillSlot position) {
        this.gwcaOperations = gwcaOperations;
        this.position = position;
    }
    
    /**
     * @param skillSlot: the SkillSlot to check
     * @return: the timestamp at which the skill will be recharged, or 0 if the skill is recharged
     * @throws IOException 
     */
    public int getRecharge() throws IOException {        
        return this.gwcaOperations.getSkillRecharge(position);
    }
    
    /**
     * @param skillSlot: the SkillSlot to check
     * @return: the amount of points the given skill slot has
     * @throws IOException 
     */
    public int getSkillAdrenaline() throws IOException {
        return this.gwcaOperations.getSkillAdrenaline(position);
    }
    
    /**
     * OBSOLETE
     * @param skillSlot: the SkillSlot to check
     * @return: the skill ID slotted in the given skill slot
     * @throws IOException 
     */
    public int getSkillId() throws IOException {
        return this.gwcaOperations.getSkillbarSkillId(position);
    }
    
    /**
     * @param skillSlot
     * @param skillId
     * @throws IOException 
     */
    public void setSkill(int skillId) throws IOException {
       this.gwcaOperations.setSkillbarSkill(position, skillId);
    }
    
    /**
     * Works on all skills including attack skills. Remember that some skills that target yourself may require that specified
     * @param skillSlot
     * @param targetId or -1 for the current target, -2 for yourself
     * @throws IOException 
     */
    public void useSkill(int targetId) throws IOException {
       this.gwcaOperations.useSkill(position, targetId);
    }
    
    /**-
     * @param targetId
     * @param delay
     * @throws IOException 
     */
    public void useSkillEx(int targetId, int delay) throws IOException {
        this.gwcaOperations.useSkillEx(position, targetId, delay);
    }
    
    /**
     * @return the amount of milliseconds before the skill will be recharged
     * @throws IOException 
     */
    public int getSkillRechargeTimeLeft() throws IOException {
        return this.gwcaOperations.getSkillRechargeTimeLeft(position);
    }
}
