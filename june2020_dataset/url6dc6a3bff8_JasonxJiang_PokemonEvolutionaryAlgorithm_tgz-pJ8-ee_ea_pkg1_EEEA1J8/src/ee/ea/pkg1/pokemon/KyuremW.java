/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.pokemon;

import ee.ea.pkg1.moves.Blizzard;
import ee.ea.pkg1.moves.DracoMeteor;
import ee.ea.pkg1.types.Dragon;
import ee.ea.pkg1.moves.DragonPulse;
import ee.ea.pkg1.moves.EarthPower;
import ee.ea.pkg1.moves.FocusBlast;
import ee.ea.pkg1.moves.FusionFlare;
import ee.ea.pkg1.Move;
import ee.ea.pkg1.Pokemon;
import ee.ea.pkg1.moves.IceBeam;
import ee.ea.pkg1.moves.ShadowBall;
import ee.ea.pkg1.types.Ice;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jason
 */
public class KyuremW extends Pokemon{
 

        public KyuremW() {
            baseHealth = 125;
            baseAttack = 120;
            baseDefense = 90;
            baseSpecialAttack = 170;
            baseSpecialDefense = 100;
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
            name = "Kyurem-W";
            primary = new Dragon();
            secondary = new Ice();
            moveLibrary = new HashMap<String, Move>();
            Move m1 = new DragonPulse();
            Move m2 = new FocusBlast();
            Move m3 = new EarthPower();
            Move m4 = new FusionFlare();
            Move m5 = new IceBeam();
            Move m6 = new Blizzard();
            Move m7 = new ShadowBall();
            Move m8 = new DracoMeteor();
            moveLibrary.put("dragon pulse", m1);
            moveLibrary.put("focus blast", m2);
            moveLibrary.put("earth power", m3);
            moveLibrary.put("fusion flare", m4);
            moveLibrary.put("ice beam", m5);
            moveLibrary.put("blizzard", m6);
            moveLibrary.put("shadow ball", m7);
            moveLibrary.put("draco meteor", m8);
            potentials = new ArrayList<String>();
            potentials.add("dragon pulse");
            potentials.add("focus blast");
            potentials.add("earth power");
            potentials.add("fusion flare");
            potentials.add("ice beam");
            potentials.add("blizzard");
            potentials.add("shadow ball");
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
        public KyuremW(int IVHP, int IVATK, int IVDEF, int IVSPA, int IVSPD, int IVSPEED, int EVHP,
                int EVATK, int EVDEF, int EVSPA, int EVSDF, int EVSPEED, double natureAtk, double natureDef,
                double natrueSpa, double natureSdf, double natureSpeed) {
            baseHealth = 125;
            baseAttack = 120;
            baseDefense = 90;
            baseSpecialAttack = 170;
            baseSpecialDefense = 100;
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
            name = "Kyurem-W";
            primary = new Dragon();
            secondary = new Ice();
            moveLibrary = new HashMap<String, Move>();
            Move m1 = new DragonPulse();
            Move m2 = new FocusBlast();
            Move m3 = new EarthPower();
            Move m4 = new FusionFlare();
            Move m5 = new IceBeam();
            Move m6 = new Blizzard();
            Move m7 = new ShadowBall();
            Move m8 = new DracoMeteor();
            moveLibrary.put("dragon pulse", m1);
            moveLibrary.put("focus blast", m2);
            moveLibrary.put("earth power", m3);
            moveLibrary.put("fusion flare", m4);
            moveLibrary.put("ice beam", m5);
            moveLibrary.put("blizzard", m6);
            moveLibrary.put("shadow ball", m7);
            moveLibrary.put("draco meteor", m8);
            potentials = new ArrayList<String>();
            potentials.add("dragon pulse");
            potentials.add("focus blast");
            potentials.add("earth power");
            potentials.add("fusion flare");
            potentials.add("ice beam");
            potentials.add("blizzard");
            potentials.add("shadow ball");
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
