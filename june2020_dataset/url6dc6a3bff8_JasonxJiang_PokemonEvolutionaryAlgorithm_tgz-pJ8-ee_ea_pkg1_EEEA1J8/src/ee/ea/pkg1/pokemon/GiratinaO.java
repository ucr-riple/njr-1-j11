/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.pokemon;

import ee.ea.pkg1.moves.AuraSphere;
import ee.ea.pkg1.moves.DracoMeteor;
import ee.ea.pkg1.types.Dragon;
import ee.ea.pkg1.moves.DragonPulse;
import ee.ea.pkg1.moves.Earthquake;
import ee.ea.pkg1.Move;
import ee.ea.pkg1.Pokemon;
import ee.ea.pkg1.moves.HiddenPowerFire;
import ee.ea.pkg1.moves.Outrage;
import ee.ea.pkg1.moves.ShadowBall;
import ee.ea.pkg1.moves.ShadowSneak;
import ee.ea.pkg1.moves.Thunder;
import ee.ea.pkg1.types.Ghost;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jason
 */
public class GiratinaO extends Pokemon{
    
    

        public GiratinaO() {
            baseHealth = 150;
            baseAttack = 120;
            baseDefense = 100;
            baseSpecialAttack = 120;
            baseSpecialDefense = 100;
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
            EVHP = 6;
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
            name = "Giratina-O";
            primary = new Dragon();
            secondary = new Ghost();
            moveLibrary = new HashMap<String, Move>();
            Move m1 = new DracoMeteor();
            Move m2 = new ShadowSneak();
            Move m3 = new HiddenPowerFire();
            Move m4 = new Earthquake();
            Move m5 = new AuraSphere();
            Move m6 = new DragonPulse();
            Move m7 = new ShadowBall();
            Move m8 = new Thunder();
            moveLibrary.put("draco meteor", m1);
            moveLibrary.put("shadow sneak", m2);
            moveLibrary.put("hidden power fire", m3);
            moveLibrary.put("earthquake", m4);
            moveLibrary.put("aura sphere", m5);
            moveLibrary.put("dragonpulse", m6);
            moveLibrary.put("shadow ball", m7);
            moveLibrary.put("thunder", m8);
            potentials = new ArrayList<String>();
            potentials.add("draco meteor");
            potentials.add("shadow sneak");
            potentials.add("hidden power fire");
            potentials.add("earthquake");
            potentials.add("aura sphere");
            potentials.add("dragon pulse");
            potentials.add("shadow ball");
            potentials.add("thunder");
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

        public GiratinaO(int IVHP, int IVATK, int IVDEF, int IVSPA, int IVSPD, int IVSPEED, int EVHP,
                int EVATK, int EVDEF, int EVSPA, int EVSDF, int EVSPEED, double natureAtk, double natureDef,
                double natrueSpa, double natureSdf, double natureSpeed) {
            baseHealth = 150;
            baseAttack = 120;
            baseDefense = 100;
            baseSpecialAttack = 120;
            baseSpecialDefense = 100;
            baseSpeed = 90;
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
            name = "Giratina-O";
            primary = new Dragon();
            secondary = new Ghost();
            moveLibrary = new HashMap<String, Move>();
            Move m1 = new DracoMeteor();
            Move m2 = new ShadowSneak();
            Move m3 = new HiddenPowerFire();
            Move m4 = new Earthquake();
            Move m5 = new AuraSphere();
            Move m6 = new DragonPulse();
            Move m7 = new ShadowBall();
            Move m8 = new Thunder();
            Move m9 = new Outrage();
            moveLibrary.put("draco meteor", m1);
            moveLibrary.put("shadow sneak", m2);
            moveLibrary.put("hidden power fire", m3);
            moveLibrary.put("earthquake", m4);
            moveLibrary.put("aura sphere", m5);
            moveLibrary.put("dragonpulse", m6);
            moveLibrary.put("shadow ball", m7);
            moveLibrary.put("thunder", m8);
            moveLibrary.put("outrage", m9);
            potentials = new ArrayList<String>();
            potentials.add("draco meteor");
            potentials.add("shadow sneak");
            potentials.add("hidden power fire");
            potentials.add("earthquake");
            potentials.add("aura sphere");
            potentials.add("dragon pulse");
            potentials.add("shadow ball");
            potentials.add("thunder");
            potentials.add("outrage");
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
