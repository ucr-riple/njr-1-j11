/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1;
import ee.ea.pkg1.types.Type;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Write a description of class Pokemon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Pokemon
{
    protected Type primary;
    protected Type secondary; 
    protected int baseHealth;
    protected int baseDefense;
    protected int baseAttack;
    protected int baseSpecialAttack;
    protected int baseSpecialDefense;
    protected int baseSpeed;
    protected double natureAtk;
    protected double natureDef;
    protected double natureSpa;
    protected double natureSdf;
    protected double natureSpeed;
    protected int IVHP;
    protected int IVATK;
    protected int IVDEF;
    protected int IVSPA;
    protected int IVSPD;
    protected int IVSPEED;
    protected int EVHP;
    protected int EVATK;
    protected int EVDEF;
    protected int EVSPA;
    protected int EVSDF;
    protected int EVSPEED;
    protected double statBoostATK;
    protected double statBoostDEF;
    protected double statBoostSPA;
    protected double statBoostSDF;
    protected double statBoostSPEED;
    protected int level;
    protected int finalAtk;
    protected int finalDef;
    protected int finalSpa;
    protected int finalSdf;
    protected int finalHp;
    protected int finalSpeed;
    protected String name;
    protected String status; 
    protected String type1;
    protected String type2;
    protected ArrayList<String> potentials;
    protected ArrayList<String> circumvent;
    protected HashMap<String, Move> moveLibrary;
    protected HashMap<String, Move> battleReady;
    
    /**
     * Constructor for objects of class Pokemon
     */
    public Pokemon()
    {
        
    }
    /*
    public Pokemon(int IVHP, int IVATK, int IVDEF, int IVSPA, int IVSPD, int IVSPEED, int EVHP,
    int EVATK, int EVDEF, int EVSPA, int EVSDF, int EVSPEED, double natureAtk, double natureDef,
    double natrueSpa, double natureSdf, double natureSpeed)
    {
       baseHealth =100;
       baseDefense = 100;
       baseAttack = 100;
       baseSpecialAttack = 100;
       baseSpecialDefense = 100;
       baseSpeed = 100; 
       IVHP = IVHP;
       IVATK = IVATK;
       IVDEF =IVDEF;
       IVSPA = IVSPA;
       IVSPD = IVSPD;
       IVSPEED = IVSPEED;
       EVHP = EVHP;
       EVATK = EVATK;
       EVDEF = EVDEF;
       EVSPA = EVSPA;
       EVSDF = EVSDF;
       EVSPEED = EVSPEED;
       statBoostATK = 1;
       statBoostDEF = 1;
       statBoostSPA = 1;
       statBoostSDF = 1;
       statBoostSPEED =1;
       level = 100;
       natureAtk = natureAtk;
       natureDef = natureDef;
       natureSpa = natureSpa;
       natureSdf = natureSdf;
       natureSpeed = natureSpeed;
       finalHp = (int) ((((IVHP + ( 2 * baseHealth) + (EVHP/4)) * level)/100) + 10);
       finalAtk =(int) (((((IVATK + (2 * baseAttack) + (EVATK/4)) * level)/100)+5)* natureAtk);
       finalDef = (int) (((((IVDEF + (2 * baseDefense) + (EVDEF/4)) * level)/100)+5)* natureDef);
       finalSpa = (int)  (((((IVSPA + (2 * baseSpecialAttack) + (EVSPA/4)) * level)/100)+5)* natureSpa);
       finalSdf = (int) (((((IVSPD + (2 * baseSpecialDefense) + (EVSDF/4)) * level)/100)+5)* natureSdf);
       finalSpeed = (int) (((((IVSPEED + (2 * baseSpeed) + (EVSPEED/4)) * level)/100)+5)* natureSpeed);
       
       status = "none";
    }
    */
    public int showHP()
    {
        return finalHp;
    }    
    
    public int showAtk()
    {
        return finalAtk;
    }
    
    public int showDef()
    {
        return finalDef;
    }
    
    public int showSpa()
    {
        return finalSpa;
    }
    
    public int showSdf()
    {
        return finalSdf;
    }
    
    public int showSpeed()
    {
        return finalSpeed;
    }
    
    public int showLevel()
    {
        return level;
    }
    public void takeDamage(int damage)
    {
        finalHp = finalHp - damage;
        if (finalHp <0)
        {
            finalHp =0;
        }
    }
    
    public String showName()
    {
        return name;
    }
    
    public String showStatus()
    {
        return status;
    }
   
     public Type getType1()
    {
        return primary;
    }
    
    public Type getType2()
    {
        return secondary;
    }
    
    public String showType1() {
        return primary.showType();
    }

    public String showType2() {
        return secondary.showType();
    }
    
    public Move getMove(String searchTerm)
    {
        return battleReady.get(searchTerm);
    }
    
    public Move getMove(int random)
    {
        String key = circumvent.get(random);
        return battleReady.get(key);
    }
    
    public ArrayList<String> getPotentials()
    {
        return potentials;
    }
    
    public ArrayList<String> getCircumvent()
    {
        return circumvent;
    }
}

