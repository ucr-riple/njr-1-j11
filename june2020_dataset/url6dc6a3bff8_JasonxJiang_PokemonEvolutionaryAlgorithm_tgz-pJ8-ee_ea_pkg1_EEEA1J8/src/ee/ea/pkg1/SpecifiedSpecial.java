package ee.ea.pkg1;

import ee.ea.pkg1.types.Type;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * Abstract class specifiedSpecial - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class SpecifiedSpecial implements SpecialAttackingMove
{
    protected Type primary;
    protected int basepower;
    protected int accuracy;
    protected String type;
    protected String name;
    public  boolean hits()
    {
        int random = (int) (Math.random() *100);
        
        return random<=accuracy;
        
    }
    
    public boolean criticalHit()
    {
        int random = (int) (Math.random() *17);
        return random ==1;
    }
    public int inflictSpecialDamage(int finalSpa, int foeSdf, int level, double Stab, double typeModifier, int randomNumber, int critModifier)
    {
        int damage = (int) (((((2 * level / 5 + 2) * finalSpa * basepower / foeSdf) / 50) + 2) * Stab * typeModifier * randomNumber / 100) * critModifier;
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
    
    public String showName()
    {
        return name;
    }

}
