/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dnd.attributegui;

import dnd.attributegui.classes.BaseClass;
import dnd.attributegui.races.BaseRace;

/**
 *
 * @author Mike
 */
public class ClassMap {
    private BaseClass _class;
    private BaseRace _race;
    private long _average;

    public ClassMap(BaseClass bc, BaseRace br, long avg){
        _class = bc;
        _race = br;
        _average = avg;
    }

    public double getAverage(){
        return _average;
    }

    public BaseClass getClassType(){
        return _class;
    }

    public BaseRace getRace(){
        return _race;
    }

}
