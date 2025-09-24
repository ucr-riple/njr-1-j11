package attribute;

import units.Unit;


/**
 * Adds a basic attack command to a unit. Pass in the base damage amount, range of attack, and max attacks per turn to the
 * constructor.
 * 
 * @author Matthew
 * 
 */
public class AttributeAttack extends Attribute<Integer,Integer> {
    
    protected int baseAttackDamage;
    protected int attackRange;
    protected int attacksLeft;
    protected int maxAttacks;

    public AttributeAttack(Unit achiever,int baseDamage, int range, int maximumAttacksPerTurn) {
        super(achiever);
        super.setData(baseDamage);
        baseAttackDamage = baseDamage;
        attackRange = range;
        maxAttacks = maximumAttacksPerTurn;
        attacksLeft=maxAttacks;
    }
    
    public int getAttackDamage(){
        return baseAttackDamage;
    }
    public int getAttackRange() {
        return attackRange;
    }
    public int getMaxAttacks(){
        return maxAttacks;
    }
    public int getAttacksLeft(){
        return attacksLeft;
    }

    public void setAttacksLeft(int attacksleft){
        attacksLeft=attacksleft;
    }
    public void setMaxAttacks(int max){
        maxAttacks=max;
    }
    public void setAttackDamage(int value) {
        baseAttackDamage = value;
    }
    public void decrementAttacksLeft(int value){
        attacksLeft-=value;
}
    
    public void setAttackRange(int value) {
        attackRange = value;
    }

    @Override
    public void refresh() {
        attacksLeft=maxAttacks;
    }

    @Override
    public String name() {
        return "Attack";
    }

    @Override
    public void augmentDataTemplate(Integer dataElement) {
        super.setData(super.getData()+dataElement);
    }


}
