/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.pokemon;
import ee.ea.pkg1.moves.BlueFlare;
import ee.ea.pkg1.moves.BoltStrike;
import ee.ea.pkg1.moves.FireBlast;
import ee.ea.pkg1.moves.FocusBlast;
import ee.ea.pkg1.Move;
import ee.ea.pkg1.Pokemon;
import ee.ea.pkg1.types.Fire;
import ee.ea.pkg1.moves.UTurn;
import ee.ea.pkg1.moves.ZenHeadbutt;
import ee.ea.pkg1.types.Psychic;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Write a description of class Victini here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Victini extends Pokemon
{
    

    /**
     * Constructor for objects of class Victini
     */
    public Victini()
    {
        baseHealth =100;
        baseDefense = 100;
        baseAttack = 100;
        baseSpecialAttack = 100;
        baseSpecialDefense = 100;
        baseSpeed = 100;
        natureAtk = 1.0;
        natureDef = 1.0;
        natureSpa = 1.0;
        natureSdf = 1.0;
        natureSpeed = 1.0;
        IVHP = 31;
        IVATK = 31;
        IVDEF = 31;
        IVSPA = 31;
        IVSPD = 31;
        IVSPEED = 31;
        EVHP = 252;
        EVATK = 126;
        EVDEF = 0;
        EVSPA = 126;
        EVSDF = 0;
        EVSPEED = 6;
        statBoostATK = 1;
        statBoostDEF = 1;
        statBoostSPA = 1;
        statBoostSDF = 1;
        statBoostSPEED = 1;
        level = 100;
        finalHp = (int) ((((IVHP + ( 2 * baseHealth) + (EVHP/4)) * level)/100) + 10);
        finalAtk = (int) (((((IVATK + (2 * baseAttack) + (EVATK/4)) * level)/100)+5)* natureAtk);
        finalDef = (int) (((((IVDEF + (2 * baseDefense) + (EVDEF/4)) * level)/100)+5)* natureDef);
        finalSpa = (int) (((((IVSPA + (2 * baseSpecialAttack) + (EVSPA/4)) * level)/100)+5)* natureSpa);
        finalSdf = (int) (((((IVSPD + (2 * baseSpecialDefense) + (EVSDF/4)) * level)/100)+5)* natureSdf);
        finalSpeed = (int) (((((IVSPEED + (2 * baseSpeed) + (EVSPEED/4)) * level)/100)+5)* natureSpeed);
        name = "Victini";
        type1 = "Fire";
        type2 = "Psychic"; 
        primary = new Fire();
        secondary = new Psychic();
        moveLibrary = new HashMap<String, Move>();
        Move m1 = new BoltStrike();
        Move m2 = new FocusBlast();
        Move m3 = new UTurn();
        Move m4 = new ZenHeadbutt();
        Move m5 = new BlueFlare();
        Move m6 = new FireBlast();
        moveLibrary.put("bolt strike", m1);
        moveLibrary.put("focus blast", m2);
        moveLibrary.put("u-turn", m3);
        moveLibrary.put("zen headbutt", m4);
        moveLibrary.put("blue flare", m5);
        moveLibrary.put("fire blast", m6);
        potentials = new ArrayList<String>();
        potentials.add("bolt strike");
        potentials.add("focus blast");
        potentials.add("u-turn");
        potentials.add("zen headbutt");
        potentials.add("blue flare");
        potentials.add("fire blast");
        battleReady = new HashMap<String, Move>();
        circumvent = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            int randomIndex = (int) (Math.random() * potentials.size());
            String key = potentials.get(randomIndex);
            while (circumvent.contains(key)) {
                int nextRandom = (int) (Math.random() * potentials.size());
                key = potentials.get(nextRandom);
            }
            battleReady.put(key, moveLibrary.get(key));
            circumvent.add(key);
            //potentials.remove(randomIndex);
        }
        
    }
    
    public Victini(int IVHP, int IVATK, int IVDEF, int IVSPA, int IVSPD, int IVSPEED, int EVHP,
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
       name = "Victini";
       type1 = "Fire";
       type2 = "Psychic";
       primary = new Fire();
       secondary = new Psychic();
        moveLibrary = new HashMap<String, Move>();
        Move m1 = new BoltStrike();
        Move m2 = new FocusBlast();
        Move m3 = new UTurn();
        Move m4 = new ZenHeadbutt();
        Move m5 = new BlueFlare();
        Move m6 = new FireBlast();
        moveLibrary.put("bolt strike", m1);
        moveLibrary.put("focus blast", m2);
        moveLibrary.put("u-turn", m3);
        moveLibrary.put("zen headbutt", m4);
        moveLibrary.put("blue flare", m5);
        moveLibrary.put("fire blast", m6);
        potentials = new ArrayList<String>();
        potentials.add("bolt strike");
        potentials.add("focus blast");
        potentials.add("u-turn");
        potentials.add("zen headbutt");
        potentials.add("blue flare");
        potentials.add("fire blast");
        circumvent = new ArrayList<String>();
        battleReady = new HashMap<String, Move>();
        for (int i = 0; i < 4; i++) {
            int randomIndex = (int) (Math.random() * potentials.size());
            String key = potentials.get(randomIndex);
            while (circumvent.contains(key))
            {
                int nextRandom = (int) (Math.random() * potentials.size());
                key = potentials.get(nextRandom);
            }
            battleReady.put(key, moveLibrary.get(key));
            circumvent.add(key);
            //potentials.remove(randomIndex);
        }
       
    }
}

