/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.pokemon;

import ee.ea.pkg1.moves.AirSlash;
import ee.ea.pkg1.moves.EarthPower;
import ee.ea.pkg1.types.Flying;
import ee.ea.pkg1.moves.HiddenPowerFire;
import ee.ea.pkg1.moves.HiddenPowerIce;
import ee.ea.pkg1.Move;
import ee.ea.pkg1.Pokemon;
import ee.ea.pkg1.moves.LeafStorm;
import ee.ea.pkg1.moves.SeedFlare;
import ee.ea.pkg1.types.Grass;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jason
 */
public class ShayminS extends Pokemon{
    

        public ShayminS() {
            baseHealth = 100;
            baseAttack = 103;
            baseDefense = 75;
            baseSpecialAttack = 120;
            baseSpecialDefense = 75;
            baseSpeed = 127;
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
            EVATK = 0;
            EVDEF = 0;
            EVSPA = 252;
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
            name = "Shaymin-S";
            primary = new Grass();
            secondary = new Flying();
            moveLibrary = new HashMap<String, Move>();
            Move m1 = new SeedFlare();
            Move m2 = new LeafStorm();
            Move m3 = new AirSlash();
            Move m4 = new EarthPower();
            Move m5 = new HiddenPowerFire();
            Move m6 = new HiddenPowerIce();
            moveLibrary.put("seed flare", m1);
            moveLibrary.put("leaf storm", m2);
            moveLibrary.put("air slash", m3);
            moveLibrary.put("earth power", m4);
            moveLibrary.put("hidden power fire", m5);
            moveLibrary.put("hidden power ice", m6);
            potentials = new ArrayList<String>();
            potentials.add("seed flare");
            potentials.add("leaf storm");
            potentials.add("air slash");
            potentials.add("earth power");
            potentials.add("hidden power fire");
            potentials.add("hidden power ice");
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

        public ShayminS(int IVHP, int IVATK, int IVDEF, int IVSPA, int IVSPD, int IVSPEED, int EVHP,
                int EVATK, int EVDEF, int EVSPA, int EVSDF, int EVSPEED, double natureAtk, double natureDef,
                double natrueSpa, double natureSdf, double natureSpeed) {
            baseHealth = 100;
            baseAttack = 103;
            baseDefense = 75;
            baseSpecialAttack = 120;
            baseSpecialDefense = 75;
            baseSpeed = 127;
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
            name = "Shaymin-S";
            primary = new Grass();
            secondary = new Flying();
            moveLibrary = new HashMap<String, Move>();
            Move m1 = new SeedFlare();
            Move m2 = new LeafStorm();
            Move m3 = new AirSlash();
            Move m4 = new EarthPower();
            Move m5 = new HiddenPowerFire();
            Move m6 = new HiddenPowerIce();
            moveLibrary.put("seed flare", m1);
            moveLibrary.put("leaf storm", m2);
            moveLibrary.put("air slash", m3);
            moveLibrary.put("earth power", m4);
            moveLibrary.put("hidden power fire", m5);
            moveLibrary.put("hidden power ice", m6);
            potentials = new ArrayList<String>();
            potentials.add("seed flare");
            potentials.add("leaf storm");
            potentials.add("air slash");
            potentials.add("earth power");
            potentials.add("hidden power fire");
            potentials.add("hidden power ice");
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
