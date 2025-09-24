/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.pokemon;

import ee.ea.pkg1.moves.DracoMeteor;
import ee.ea.pkg1.types.Dragon;
import ee.ea.pkg1.moves.DragonClaw;
import ee.ea.pkg1.moves.Earthquake;
import ee.ea.pkg1.moves.ExtremeSpeed;
import ee.ea.pkg1.moves.FireBlast;
import ee.ea.pkg1.types.Flying;
import ee.ea.pkg1.Move;
import ee.ea.pkg1.Pokemon;
import ee.ea.pkg1.moves.Outrage;
import ee.ea.pkg1.moves.OverHeat;
import ee.ea.pkg1.moves.VCreate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jason
 */
public class Rayquaza extends Pokemon{
    
    public Rayquaza()
    {
        baseHealth = 105;
        baseAttack = 150;
        baseDefense = 90;
        baseSpecialAttack = 150;
        baseSpecialDefense = 90;
        baseSpeed = 95;
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
        name = "Rayquaza";
        primary = new Dragon();
        secondary = new Flying();
        moveLibrary = new HashMap<String, Move>();
        Move m1 = new Outrage();
        Move m2 = new FireBlast();
        Move m3 = new Earthquake();
        Move m4 = new VCreate();
        Move m5 = new ExtremeSpeed();
        Move m6 = new OverHeat();
        Move m7 = new DragonClaw();
        Move m8 = new DracoMeteor();
        moveLibrary.put("outrage", m1);
        moveLibrary.put("fire blast", m2);
        moveLibrary.put("earthquake", m3);
        moveLibrary.put("v-create", m4);
        moveLibrary.put("extreme speed", m5);
        moveLibrary.put("overheat", m6);
        moveLibrary.put("dragon claw", m7);
        moveLibrary.put("draco meteor", m8);
        potentials = new ArrayList<String>();
        potentials.add("outrage");
        potentials.add("fire blast");
        potentials.add("earthquake");
        potentials.add("v-create");
        potentials.add("extreme speed");
        potentials.add("overheat");
        potentials.add("dragon claw");
        potentials.add("draco meteor");
        circumvent = new ArrayList<String>();
        battleReady = new HashMap<String, Move>();
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
    
    public Rayquaza(int IVHP, int IVATK, int IVDEF, int IVSPA, int IVSPD, int IVSPEED, int EVHP,
    int EVATK, int EVDEF, int EVSPA, int EVSDF, int EVSPEED, double natureAtk, double natureDef,
    double natrueSpa, double natureSdf, double natureSpeed)
    {
        baseHealth = 105;
        baseAttack = 150;
        baseDefense = 90;
        baseSpecialAttack = 150;
        baseSpecialDefense = 90;
        baseSpeed = 95;
        IVHP = IVHP;
        IVATK = IVATK;
        IVDEF = IVDEF;
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
        statBoostSPEED = 1;
        level = 100;
        natureAtk = natureAtk;
        natureDef = natureDef;
        natureSpa = natureSpa;
        natureSdf = natureSdf;
        natureSpeed = natureSpeed;
        finalHp = (int) ((((IVHP + (2 * baseHealth) + (EVHP / 4)) * level) / 100) + 10);
        finalAtk = (int) (((((IVATK + (2 * baseAttack) + (EVATK / 4)) * level) / 100) + 5) * natureAtk);
        finalDef = (int) (((((IVDEF + (2 * baseDefense) + (EVDEF / 4)) * level) / 100) + 5) * natureDef);
        finalSpa = (int) (((((IVSPA + (2 * baseSpecialAttack) + (EVSPA / 4)) * level) / 100) + 5) * natureSpa);
        finalSdf = (int) (((((IVSPD + (2 * baseSpecialDefense) + (EVSDF / 4)) * level) / 100) + 5) * natureSdf);
        finalSpeed = (int) (((((IVSPEED + (2 * baseSpeed) + (EVSPEED / 4)) * level) / 100) + 5) * natureSpeed);
        name = "Rayquaza";
        primary = new Dragon();
        secondary = new Flying();
        moveLibrary = new HashMap<String, Move>();
        Move m1 = new Outrage();
        Move m2 = new FireBlast();
        Move m3 = new Earthquake();
        Move m4 = new VCreate();
        Move m5 = new ExtremeSpeed();
        Move m6 = new OverHeat();
        Move m7 = new DragonClaw();
        Move m8 = new DracoMeteor();
        moveLibrary.put("outrage", m1);
        moveLibrary.put("fire blast", m2);
        moveLibrary.put("earthquake", m3);
        moveLibrary.put("v-create", m4);
        moveLibrary.put("extreme speed", m5);
        moveLibrary.put("overheat", m6);
        moveLibrary.put("dragon claw", m7);
        moveLibrary.put("draco meteor", m8);
        potentials = new ArrayList<String>();
        potentials.add("outrage");
        potentials.add("fire blast");
        potentials.add("earthquake");
        potentials.add("v-create");
        potentials.add("extreme speed");
        potentials.add("overheat");
        potentials.add("dragon claw");
        potentials.add("draco meteor");
        circumvent = new ArrayList<String>();
        battleReady = new HashMap<String, Move>();
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
}
