/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.pokemon;

import ee.ea.pkg1.moves.DragonClaw;
import ee.ea.pkg1.moves.Earthquake;
import ee.ea.pkg1.moves.FireBlast;
import ee.ea.pkg1.moves.FirePunch;
import ee.ea.pkg1.Move;
import ee.ea.pkg1.types.None;
import ee.ea.pkg1.Pokemon;
import ee.ea.pkg1.moves.SolarBeam;
import ee.ea.pkg1.moves.StoneEdge;
import ee.ea.pkg1.types.Ground;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jason
 */
public class Groundon extends Pokemon{
    public Groundon()
    {
        baseHealth = 100;
        baseAttack = 150;
        baseDefense = 140;
        baseSpecialAttack = 100;
        baseSpecialDefense = 90;
        baseSpeed = 90;
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
        EVATK = 252;
        EVDEF = 6;
        EVSPA = 0;
        EVSDF = 0;
        EVSPEED = 6;
        statBoostATK = 1;
        statBoostDEF = 1;
        statBoostSPA = 1;
        statBoostSDF = 1;
        statBoostSPEED = 1;
        level = 100;
        finalHp = (int) ((((IVHP + (2 * baseHealth) + (EVHP / 4)) * level) / 100) + 10);
        finalAtk = (int) (((((IVATK + (2 * baseAttack) + (EVATK / 4)) * level) / 100) + 5) * natureAtk);
        finalDef = (int) (((((IVDEF + (2 * baseDefense) + (EVDEF / 4)) * level) / 100) + 5) * natureDef);
        finalSpa = (int) (((((IVSPA + (2 * baseSpecialAttack) + (EVSPA / 4)) * level) / 100) + 5) * natureSpa);
        finalSdf = (int) (((((IVSPD + (2 * baseSpecialDefense) + (EVSDF / 4)) * level) / 100) + 5) * natureSdf);
        finalSpeed = (int) (((((IVSPEED + (2 * baseSpeed) + (EVSPEED / 4)) * level) / 100) + 5) * natureSpeed);
        name = "Groudon";
        primary = new Ground();
        secondary = new None();
        moveLibrary = new HashMap<String, Move>();
        Move m1 = new Earthquake();
        Move m2 = new StoneEdge();
        Move m3 = new FirePunch();
        Move m4 = new SolarBeam();
        Move m5 = new FireBlast();
        Move m6 = new DragonClaw();
        moveLibrary.put("earthquake", m1);
        moveLibrary.put("stone edge", m2);
        moveLibrary.put("fire punch", m3);
        moveLibrary.put("solar beam", m4);
        moveLibrary.put("fire blast", m5);
        moveLibrary.put("dragon claw", m6);
        potentials = new ArrayList<String>();
        potentials.add("earthquake");
        potentials.add("stone edge");
        potentials.add("fire punch");
        potentials.add("solar beam");
        potentials.add("fire blast");
        potentials.add("dragon claw");
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
    
    public Groundon(int IVHP, int IVATK, int IVDEF, int IVSPA, int IVSPD, int IVSPEED, int EVHP,
            int EVATK, int EVDEF, int EVSPA, int EVSDF, int EVSPEED, double natureAtk, double natureDef,
            double natrueSpa, double natureSdf, double natureSpeed) {
        baseHealth = 105;
        baseAttack = 150;
        baseDefense = 90;
        baseSpecialAttack = 150;
        baseSpecialDefense = 190;
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
        name = "Groundon";
        primary = new Ground();
        secondary = new None();
        moveLibrary = new HashMap<String, Move>();
        Move m1 = new Earthquake();
        Move m2 = new StoneEdge();
        Move m3 = new FirePunch();
        Move m4 = new SolarBeam();
        Move m5 = new FireBlast();
        Move m6 = new DragonClaw();
        moveLibrary.put("earthquake", m1);
        moveLibrary.put("stone edge", m2);
        moveLibrary.put("fire punch", m3);
        moveLibrary.put("solar beam", m4);
        moveLibrary.put("fire blast", m5);
        moveLibrary.put("dragon claw", m6);
        potentials = new ArrayList<String>();
        potentials.add("earthquake");
        potentials.add("stone edge");
        potentials.add("fire punch");
        potentials.add("solar beam");
        potentials.add("fire blast");
        potentials.add("dragon claw");
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
}
