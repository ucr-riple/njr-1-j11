package attribute;

import units.Unit;

/**Adds health to a unit, pass in constructors the starting health and the unit itself.  Must pass in the unit because Unit is destroyed when health reaches zero.
 * @author Matthew
 *
 */
public class AttributeHealth extends Attribute<Integer,Integer> {

    private int healthPoints;
    
    public AttributeHealth(Unit achiever,int startingHealth) {
        super(achiever);
        healthPoints = startingHealth;
    }

    private int HP;

    public int getHP() {
        return healthPoints;
    }

    public void decrementHP(int amount) {
        healthPoints -= amount;
    }

    public void setHP(int hitpoints) {
        healthPoints = hitpoints;
    }

    public void increaseHP(int amount) {
        healthPoints += amount;
    }

   
    @Override
    public void refresh() {
        // TODO Auto-generated method stub

    }

    @Override
    public String name() {
        return "Health";
    }

    @Override
    public void augmentDataTemplate(Integer dataElement) {
        super.setData(super.getData()+dataElement);
    }
}
