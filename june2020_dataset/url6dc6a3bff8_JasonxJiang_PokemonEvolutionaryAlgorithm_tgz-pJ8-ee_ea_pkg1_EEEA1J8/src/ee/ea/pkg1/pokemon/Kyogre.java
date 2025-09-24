/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.pokemon;

import ee.ea.pkg1.moves.HydroPump;
import ee.ea.pkg1.Move;
import ee.ea.pkg1.types.None;
import ee.ea.pkg1.Pokemon;
import ee.ea.pkg1.moves.IceBeam;
import ee.ea.pkg1.moves.Scald;
import ee.ea.pkg1.moves.Surf;
import ee.ea.pkg1.moves.Thunder;
import ee.ea.pkg1.types.Water;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jason
 */
public class Kyogre extends Pokemon{
    
  

        public Kyogre() {
            baseHealth = 100;
            baseAttack = 100;
            baseDefense = 90;
            baseSpecialAttack = 150;
            baseSpecialDefense = 140;
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
            EVATK = 0;
            EVDEF = 252;
            EVSPA = 0;
            EVSDF = 6;
            EVSPEED = 0;
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
            name = "Kyogre";
            primary = new Water();
            secondary = new None();
            moveLibrary = new HashMap<String, Move>();
            Move m1 = new Surf();
            Move m2 = new HydroPump();
            Move m3 = new Scald();
            Move m4 = new Thunder();
            Move m5 = new IceBeam();
            //Move m6 = new FireBlast();
            moveLibrary.put("surf", m1);
            moveLibrary.put("hydro pump", m2);
            moveLibrary.put("scald", m3);
            moveLibrary.put("thunder", m4);
            moveLibrary.put("ice beam", m5);
            //moveLibrary.put("fire blast", m6);
            potentials = new ArrayList<String>();
            potentials.add("surf");
            potentials.add("hydro pump");
            potentials.add("scald");
            potentials.add("thunder");
            potentials.add("ice beam");
            //potentials.add("fire blast");
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

        public Kyogre(int IVHP, int IVATK, int IVDEF, int IVSPA, int IVSPD, int IVSPEED, int EVHP,
                int EVATK, int EVDEF, int EVSPA, int EVSDF, int EVSPEED, double natureAtk, double natureDef,
                double natrueSpa, double natureSdf, double natureSpeed) {
            baseHealth = 100;
            baseAttack = 100;
            baseDefense = 90;
            baseSpecialAttack = 150;
            baseSpecialDefense = 140;
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
            name = "Kyogre";
            primary = new Water();
            secondary = new None();
            moveLibrary = new HashMap<String, Move>();
            Move m1 = new Surf();
            Move m2 = new HydroPump();
            Move m3 = new Scald();
            Move m4 = new Thunder();
            Move m5 = new IceBeam();
            //Move m6 = new FireBlast();
            moveLibrary.put("surf", m1);
            moveLibrary.put("hydro pump", m2);
            moveLibrary.put("scald", m3);
            moveLibrary.put("thunder", m4);
            moveLibrary.put("ice beam", m5);
            //moveLibrary.put("fire blast", m6);
            potentials = new ArrayList<String>();
            potentials.add("surf");
            potentials.add("hydro pump");
            potentials.add("scald");
            potentials.add("thunder");
            potentials.add("ice beam");
            //potentials.add("fire blast");
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
