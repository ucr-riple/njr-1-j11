package be.demmel.fun.jgwcaconstants.bll.clean;

import be.demmel.fun.jgwcaconstants.bll.GWCAOperations;
import be.demmel.fun.jgwcaconstants.bll.Hero;
import be.demmel.fun.jgwcaconstants.bll.HeroMode;
import be.demmel.fun.jgwcaconstants.bll.HeroSlot;
import be.demmel.fun.jgwcaconstants.bll.SkillBarSkillSlot;
import java.io.IOException;

public class PartyPanel {
    private final GWCAOperations gwcaOperations;
    
    public PartyPanel(GWCAOperations gwcaOperations) {
        this.gwcaOperations = gwcaOperations;
    }
    
    /**
     * @throws IOException 
     */
    public void toggleHardMode() throws IOException {
        this.gwcaOperations.toggleHardMode();
    }
    
    /**
     * @throws IOException 
     */
    public void toggleNormalMode() throws IOException {
        this.gwcaOperations.toggleNormalMode();
    }
    
    /**
     * Adds henchman to the party.
     * @param henchmenId according to Party Search list
     * @throws IOException 
     */
    public void addNpc(int henchmenId) throws IOException {
       this.gwcaOperations.addNpc(henchmenId);
    }
    
    /**
     * Kicks henchman from the party.
     * @param henchmenId
     * @throws IOException 
     */
    public void kickNpc(int henchmenId) throws IOException {
       this.gwcaOperations.kickNpc(henchmenId);
    }
    
    /**
     * 
     * @param heroSlot the slot of the hero to check
     * @return whether the hero at the given slot is casting
     * @throws IOException 
     */
    public boolean isHeroCasting(HeroSlot heroSlot) throws IOException {
        return this.gwcaOperations.isHeroCasting(heroSlot);
    }
    
    /**
     * @param skillSlot: the SkillSlot to check
     * @param heroSlot : the HeroSlot to check
     * @return: the timestamp at which the skill will be recharged, or 0 if the skill is recharged
     * @throws IOException 
     */
    public int getHeroSkillRecharge(HeroSlot heroSlot, SkillBarSkillSlot skillSlot) throws IOException {
        return this.gwcaOperations.getHeroSkillRecharge(heroSlot, skillSlot);
    }
    
    /**
     * @param skillSlot: the SkillSlot to check
     * @param heroSlot : the HeroSlot to check
     * @return: the timestamp at which the skill will be recharged, or 0 if the skill is recharged
     * @throws IOException 
     */
    public int getHeroSkillAdrenaline(HeroSlot heroSlot, SkillBarSkillSlot skillSlot) throws IOException {        
        return this.gwcaOperations.getHeroSkillAdrenaline(heroSlot, skillSlot);
    }
    
    /**
     * @param skillSlot: the SkillSlot to check
     * @param heroSlot : the HeroSlot to check
     * @return: the hero's skill ID at the given slots
     * @throws IOException 
     */
    public int getHeroSkillId(HeroSlot heroSlot, SkillBarSkillSlot skillSlot) throws IOException {        
        return this.gwcaOperations.getHeroSkillId(heroSlot, skillSlot);
    }
    
    /**
     * @param skillSlot: the SkillSlot to check
     * @param heroSlot : the HeroSlot to check
     * @return: the hero's agent ID at the given slots
     * @throws IOException 
     */
    public int getHeroAgentId(HeroSlot heroSlot, SkillBarSkillSlot skillSlot) throws IOException {
        return this.gwcaOperations.getHeroAgentId(heroSlot, skillSlot);
    }
    
    /**
     * @param skillId
     * @return
     * @throws IOException 
     */
    public boolean hero1HasBuff(int skillId) throws IOException {
        return this.gwcaOperations.hero1HasBuff(skillId);
    }
    
    /**
     * @param skillId
     * @return
     * @throws IOException 
     */
    public boolean hero2HasBuff(int skillId) throws IOException {
        return this.gwcaOperations.hero2HasBuff(skillId);
    }
    
    /**
     * @param skillId
     * @return
     * @throws IOException 
     */
    public boolean hero3HasBuff(int skillId) throws IOException {
        return this.gwcaOperations.hero3HasBuff(skillId);
    }
    
    /**
     * @param skillSlot
     * @param agentId
     * @throws IOException 
     */
    public void useHero1Skill(SkillBarSkillSlot skillSlot, int agentId) throws IOException {        
        this.gwcaOperations.useHero1Skill(skillSlot, agentId);
    }
    
    /**
     * @param skillSlot
     * @param agentId
     * @throws IOException 
     */
    public void useHero2Skill(SkillBarSkillSlot skillSlot, int agentId) throws IOException {        
        this.gwcaOperations.useHero2Skill(skillSlot, agentId);
    }
    
    /**
     * @param skillSlot
     * @param agentId
     * @throws IOException 
     */
    public void useHero3Skill(SkillBarSkillSlot skillSlot, int agentId) throws IOException {        
        this.gwcaOperations.useHero3Skill(skillSlot, agentId);
    }
    
    /**
     * To unflag, set X and Y to 0x7F800000 (flag reset)
     * @param x
     * @param y
     * @throws IOException 
     */
    public void commandHero1(float x, float y) throws IOException {
       this.gwcaOperations.commandHero1(x, y);
    }
    
    /**
     * To unflag, set X and Y to 0x7F800000 (flag reset)
     * @param x
     * @param y
     * @throws IOException 
     */
    public void commandHero2(float x, float y) throws IOException {
       this.gwcaOperations.commandHero2(x, y);
    }
    
    /**
     * To unflag, set X and Y to 0x7F800000 (flag reset)
     * @param x
     * @param y
     * @throws IOException 
     */
    public void commandHero3(float x, float y) throws IOException {
       this.gwcaOperations.commandHero3(x, y);
    }
    
    /**
     * To unflag, set X and Y to 0x7F800000 (flag reset)
     * @param x
     * @param y
     * @throws IOException 
     */
    public void commandAll(float x, float y) throws IOException {
       this.gwcaOperations.commandAll(x, y);
    }
    
    /**
     * Adds hero to the party.
     * @param hero
     * @throws IOException 
     */
    public void addHero(Hero hero) throws IOException {
       this.gwcaOperations.addHero(hero);
    }
    
    /**
     * Kicks hero from the party.
     * @param heroId
     * @throws IOException 
     */
    public void kickHero(Hero heroId) throws IOException {
       this.gwcaOperations.kickHero(heroId);
    }
    
    /**
     * @param heroIndex
     * @param heroMode
     * @throws IOException 
     */
    public void setHeroMode(int heroIndex, HeroMode heroMode) throws IOException {
       this.gwcaOperations.setHeroMode(heroIndex, heroMode);
    }
    
    /**
     * Set to 0 to un-lock the target of the hero.
     * @param heroIndex
     * @param targetAgentId or -1 for the current target, -2 for yourself
     * @throws IOException 
     */
    public void lockHero(int heroIndex, int targetAgentId) throws IOException {
       this.gwcaOperations.lockHero(heroIndex, targetAgentId);
    }
    
    /**
     * @param heroSlot
     * @param skillSlot
     * @return the amount of milliseconds before the skill will be recharged
     * @throws IOException 
     */
    public int getHeroSkillRechargeTimeLeft(HeroSlot heroSlot, SkillBarSkillSlot skillSlot) throws IOException {
        return this.gwcaOperations.getHeroSkillRechargeTimeLeft(heroSlot, skillSlot);
    }
}
