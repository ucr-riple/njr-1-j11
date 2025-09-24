/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.moves;

import ee.ea.pkg1.SpecifiedSpecial;
import ee.ea.pkg1.types.Psychic;


/**
 * Write a description of class Psystrike here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Psystrike extends SpecifiedSpecial
{
    // instance variables - replace the example below with your own
   //REMEMBER THIS MOVE INFLICTS BURN ALWAYS

    /**
     * Constructor for objects of class Psystrike
     */
    public Psystrike()
    {
       basepower = 100;
       accuracy = 100;
       type = "Psychic";
       primary = new Psychic();
       name = "psystrike";
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

