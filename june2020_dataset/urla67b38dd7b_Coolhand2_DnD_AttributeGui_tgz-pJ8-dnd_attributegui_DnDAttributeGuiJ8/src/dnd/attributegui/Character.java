/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dnd.attributegui;

import dnd.attributegui.classes.*;
import dnd.attributegui.generators.*;
import dnd.attributegui.races.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Mike
 */
public class Character {

    List<ActionListener> _listeners = new ArrayList<>();
    private BaseGenerator _generator;
    private BaseRace _race;
    private BaseClass _class;
    private int[] _attributes;
    private int _level;
    private Vector<ClassMap> _classMap;
    public static final BaseClass[] CLASSES = {
        new Ardent(), new Avenger(), new Barbarian(), new Bard(),
        new Battlemind(), new Cleric(), new Druid(), new Fighter(),
        new Invoker(), new Monk(), new Paladin(), new Psion(), new Ranger(),
        new Rogue(), new RunePriest(), new Seeker(), new Shaman(),
        new Sorcerer(), new Warden(), new Warlock(), new Warlord(), new Wizard()
    };
    public static final BaseRace[] RACES = {
        new Deva(), new Dragonborn(), new Dwarf(), new Eladrin(), new Elf(),
        new Githzerai(1), new Githzerai(3), new Gnome(), new Goliath(),
        new HalfElf(), new HalfOrc(), new Halfling(), new Human(0), new Human(1),
        new Human(2), new Human(3), new Human(4), new Human(5), new Minotaur(2),
        new Minotaur(4), new Shardmind(4), new Shardmind(5), new Shifter(),
        new Tiefling(), new Wilden(2), new Wilden(1)
    };
    public static final BaseGenerator[] GENERATORS = {
        new NormalSpread(), new SpecialSpread(), new DualSpecSpread(),
        new NormalRoll(), new PowerRoll()
    };

    public Character() {
        _level = 1;
        _generator = new NormalSpread();
        _race = new Deva();
        _class = new Ardent();
        _classMap = new Vector<>();

        BaseGenerator g = new SpecialSpread();
        for (BaseClass bc : CLASSES) {
            for (BaseRace br : RACES) {
                int[] attributes = g.getAttributes();
                int[] bonuses = br.getBonuses();
                int[] ranks = bc.getRanks();
                int[] preferred = bc.getPreferred();
                int[] sums = {0, 0, 0, 0, 0, 0};
                for (int i = 0; i < 6; i++) {
                    sums[i] += attributes[ranks[i]] + bonuses[i];
                }
                float average = 0;
                for (int index : preferred) {
                    average += sums[index];
                }
                average /= 3;
                ClassMap cm = new ClassMap(bc, br, Math.round(average));
                _classMap.add(cm);
            }
        }
    }

    public void setGenerator(BaseGenerator g) {
        _generator = g;
    }

    public void setRace(BaseRace r) {
        _race = r;
    }

    public void setClass(BaseClass c) {
        _class = c;
    }

    public void setLevel(int l) {
        _level = l;
    }

    public void generateAttributes() {
        int[] attributes = _generator.getAttributes();
        int[] bonuses = _race.getBonuses();
        int[] ranks = _class.getRanks();
        int[] preferred = _class.getPreferred();
        int[] sums = {0, 0, 0, 0, 0, 0};
        for (int i = 0; i < 6; i++) {
            sums[i] += attributes[ranks[i]] + bonuses[i];
        }
        for (int i = 0; i < _level; i++) {
            switch (i) {
                case 4:
                case 8:
                case 14:
                case 18:
                case 24:
                case 28:
                    sums[preferred[0]]++;
                    sums[preferred[1]]++;
                    break;
                case 11:
                case 21:
                    for (int k = 0; k < 6; k++) {
                        sums[k]++;
                    }
                    break;
            }
        }
        _attributes = sums;
        notifyListeners();
    }

    public int[] getAttributes() {
        return _attributes;
    }

    public void addActionListener(ActionListener al) {
        _listeners.add(al);
    }

    private void notifyListeners() {
        for (ActionListener al : _listeners) {
            al.actionPerformed((ActionEvent) null);
        }
    }

    public BaseClass getClassType() {
        return _class;
    }

    public Vector<BaseRace> getRankedRaces(int rank) {
        Vector<BaseRace> br = new Vector<>();
        for (ClassMap item : _classMap) {
            if (item.getClassType().getName().equals(_class.getName())) {
                if (item.getAverage() == rank) {
                    br.add(item.getRace());
                }
            }
        }
        return br;
    }

    public Vector<BaseClass> getRankedClasses(int rank) {
        Vector<BaseClass> bc = new Vector<>();
        for (ClassMap item : _classMap) {
            if (item.getRace().getName().equals(_race.getName())) {
                if (item.getAverage() == rank) {
                    bc.add(item.getClassType());
                }
            }
        }
        return bc;
    }
}
