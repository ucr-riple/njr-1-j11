/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.pokemon;

import ee.ea.pkg1.Move;
import ee.ea.pkg1.types.None;
import ee.ea.pkg1.Pokemon;
import ee.ea.pkg1.moves.ExtremeSpeed;
import ee.ea.pkg1.moves.FirePunch;
import ee.ea.pkg1.moves.IceBeam;
import ee.ea.pkg1.moves.PsychoBoost;
import ee.ea.pkg1.moves.ShadowBall;
import ee.ea.pkg1.moves.SuperPower;
import ee.ea.pkg1.moves.ThunderBolt;
import ee.ea.pkg1.types.Psychic;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jason
 */
public class DeoxysA extends Pokemon{
    


        public DeoxysA() {
            baseHealth = 50;
            baseAttack = 180;
            baseDefense = 20;
            baseSpecialAttack = 180;
            baseSpecialDefense = 20;
            baseSpeed = 150;
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
            EVHP = 0;
            EVATK = 126;
            EVDEF = 0;
            EVSPA = 126;
            EVSDF = 0;
            EVSPEED = 252;
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
            name = "Deoxys-A";
            primary = new Psychic();
            secondary = new None();
            moveLibrary = new HashMap<String, Move>();
            Move m1 = new ExtremeSpeed();
            Move m2 = new FirePunch();
            Move m3 = new IceBeam();
            Move m4 = new PsychoBoost();
            Move m5 = new SuperPower();
            Move m6 = new ThunderBolt();
            Move m7 = new ShadowBall();
            moveLibrary.put("extreme speed", m1);
            moveLibrary.put("fire punch", m2);
            moveLibrary.put("ice beam", m3);
            moveLibrary.put("psycho boost", m4);
            moveLibrary.put("superpower", m5);
            moveLibrary.put("thunderbolt", m6);
            moveLibrary.put("shadow ball", m7);
            potentials = new ArrayList<String>();
            potentials.add("extreme speed");
            potentials.add("fire punch");
            potentials.add("ice beam");
            potentials.add("psycho boost");
            potentials.add("superpower");
            potentials.add("thunderbolt");
            potentials.add("shadow ball");
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

        public DeoxysA(int IVHP, int IVATK, int IVDEF, int IVSPA, int IVSPD, int IVSPEED, int EVHP,
                int EVATK, int EVDEF, int EVSPA, int EVSDF, int EVSPEED, double natureAtk, double natureDef,
                double natrueSpa, double natureSdf, double natureSpeed) {
            baseHealth = 50;
            baseAttack = 180;
            baseDefense = 20;
            baseSpecialAttack = 180;
            baseSpecialDefense = 20;
            baseSpeed = 150;
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
            name = "Deoxys-A";
            primary = new Psychic();
            secondary = new None();
            moveLibrary = new HashMap<String, Move>();
            Move m1 = new ExtremeSpeed();
            Move m2 = new FirePunch();
            Move m3 = new IceBeam();
            Move m4 = new PsychoBoost();
            Move m5 = new SuperPower();
            Move m6 = new ThunderBolt();
            Move m7 = new ShadowBall();
            moveLibrary.put("extreme speed", m1);
            moveLibrary.put("fire punch", m2);
            moveLibrary.put("ice beam", m3);
            moveLibrary.put("psycho boost", m4);
            moveLibrary.put("superpower", m5);
            moveLibrary.put("thunderbolt", m6);
            moveLibrary.put("shadow ball", m7);
            potentials = new ArrayList<String>();
            potentials.add("extreme speed");
            potentials.add("fire punch");
            potentials.add("ice beam");
            potentials.add("psycho boost");
            potentials.add("superpower");
            potentials.add("thunderbolt");
            potentials.add("shadow ball");
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
