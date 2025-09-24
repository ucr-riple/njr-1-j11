/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.moves;

import ee.ea.pkg1.types.Flying;
import ee.ea.pkg1.SpecifiedSpecial;


/**
 * Write a description of class Hurricane here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hurricane extends SpecifiedSpecial
{
    // instance variables - replace the example below with your own
   //REMEMBER THIS MOVE INFLICTS BURN ALWAYS

    /**
     * Constructor for objects of class Hurricane
     */
    public Hurricane()
    {
       basepower = 120;
       accuracy = 70;
       type = "Flying";
       primary = new Flying();
       name = "hurricane";
    }

    
    public int recoilDamage(int damageDealt)
    {
        return 0;
    }
    
    public int statBoost()
    {
        return 0;
    }
    
    public int statDrop()
    {
        return 0;
    }
    public int recoverHealth()
    {
        return 0;
    }
    public double typeModifier()
    {
        return 0;
    }

}

