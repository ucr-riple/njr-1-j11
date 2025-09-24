/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1;

import ee.ea.pkg1.types.Type;

/**
 * Abstract class physicalAttackingMove - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class SpecifiedPhysical implements physicalAttackingMove
{
    protected Type primary;
    protected int basepower;
    protected String type;
    protected int accuracy;
    protected String name;
    public boolean hits()
    {
        
        int random = (int) (Math.random() * 100);

        return random <= accuracy;
    }
    
    public boolean criticalHit()
    {
        int random = (int) (Math.random() *17);
        return random ==1;
    }
    public int inflictPhysicalDamage(int finalAtk, int foeDef, int level, double Stab, double typeModifier, int randomNumber,int critModifier )
    {
        int damage = (int) (((((2 * level / 5 + 2) * finalAtk * basepower / foeDef) / 50) + 2) * Stab * typeModifier * randomNumber / 100)* critModifier;
        return damage;
    }
    
    public String showType()
    {
        return primary.showType();
    }
    
    public Type getType()
    {
        return primary;
    }
    
    public String showNmae()
    {
        return name;
    }
}
