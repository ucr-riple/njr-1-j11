/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1;


/**
 * Write a description of class Field here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Field
{
    private boolean stealthRocks;
    private int numSpikes;
    private int numToxicSpikes;
    private boolean rain;
    private boolean sun;
    private boolean sandstorm;
    private boolean hail;
    private Pokemon fighters [];
    Pokemon p1;
    Pokemon p2;
    /**
     * Constructor for objects of class Field
     */
    public Field(Pokemon p1, Pokemon p2)
    {
        stealthRocks = false;
        numSpikes = 0;
        numToxicSpikes =0;
        rain = false;
        sun = false;
        sandstorm = false;
        hail = false;
        p1 = p1;
        p2 = p2;
    }

    public void SetRain()
    {
        rain = true;
        sun = false;
        sandstorm = false;
        hail = false;
    }
    
    public void setSun()
    {
        rain = false;
        sun = true;
        sandstorm = false;
        hail = false;
    }
    
    public  void setSandstorm()
    {
        rain = false;
        sun = false;
        sandstorm = true;
        hail = false;
    }
    
    public void setHail(){
        rain = false;
        sun = false;
        sandstorm = false;
        hail = true;
    }
    
    public void setSpikes()
    {
        numSpikes++;
    }
   
    public void setToxicSpikes()
    {
        numToxicSpikes ++;
    }
    
    public void setStealthRocks()
    {
        stealthRocks = true;
    }
    
    
}
