/**
  Copyright (c) 2013 Artur Huletski (hatless.fox@gmail.com)
  Permission is hereby granted, free of charge, to any person obtaining a copy 
  of this software and associated documentation files (the "Software"),
  to deal in the Software without restriction, including without limitation
  the rights to use, copy, modify, merge, publish, distribute, sublicense,
  and/or sell copies of the Software, and to permit persons
  to whom the Software is furnished to do so,
  subject to the following conditions:
  
  The above copyright notice and this permission notice shall be included
  in all copies or substantial portions of the Software.
  
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
  OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package ru.spbau.sd.model.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.spbau.sd.model.framework.EndTurnListener;
import ru.spbau.sd.model.framework.Field;
import ru.spbau.sd.model.framework.FieldObject;
import ru.spbau.sd.model.framework.Generator;
import ru.spbau.sd.model.framework.Point2D;

public class PoliceStation extends Generator implements EndTurnListener {

    private List<Policeman> mWaitingOfficers = new ArrayList<>();
    private List<Policeman> mWalkingOfficers = new ArrayList<>();
    private Set<Drinker> mDrinkersToBeCaught = new HashSet<>();
    
    private Policeman mJustReturnedOfficer;
    
    public PoliceStation(int x, int y, int officersCnt) {
        super(x, y);
        
        for (int i = 0; i < officersCnt; i++) {
            mWaitingOfficers.add(new Policeman(-1, -1, this));
        }
        
        Field.getInstance().addEndTurnListener(this);
    }
    
    public List<Policeman> getWalkingOfficers() {
        return Collections.unmodifiableList(mWalkingOfficers);
    }
    
    private void processReturnedOfficers() {
        for (Policeman p : mWalkingOfficers) {
            if (!getEntryPoints().contains(p.getPosition())) { continue; }
            if (!p.isGoingHome()) { break; }
            
            mDrinkersToBeCaught.remove(p.getCatchedBadGuy());
            //remove policeman and drinker from the field
            if (p.isBadGuyCaught()) {
                Field.getInstance().removeMovable(p.getCatchedBadGuy());
            }
            Field.getInstance().removeMovable(p);
            
            //policeman is waiting for next drinker
            mWalkingOfficers.remove(p);
            mJustReturnedOfficer = p;
            break;
        }
    }
    
    private boolean drinkerShouldBeCaught(Drinker d) {
        if (mDrinkersToBeCaught.contains(d)) { return false; }
        return d.getMovementStrategy() == Drinker.MovementStrategy.LAYING ||
               d.getMovementStrategy() == Drinker.MovementStrategy.SLEEP;
    }
    
    private Point2D drinkerIsReachable(Drinker d) {
        for (Point2D ep : getEntryPoints()) {
            if (!ep.equals(GameUtils.lookUpNextStep(ep, d.getPosition()))) {
                return ep;
            }
        }
        return null;
    }
    
    private void processUncaughtDrinkers() {
        if (mWaitingOfficers.size() == 0) { return; }
        if (isBlocked()) { return; }
        
        Drinker drinkerToBeCaught = null;
        Point2D choosenEntryPoint = null;
        for (FieldObject fo_stat : Field.getInstance().getAllFieldObjects()) {
            //our informer is light. If other object will be informers -- create interface
            if (!fo_stat.getClass().equals(Light.class)) { continue; }
            
            Light l = (Light) fo_stat; 
            for (FieldObject fo : l.getLightedFieldObjects()) {
                if (!fo.getClass().equals(Drinker.class)) { continue; }
                Drinker d = (Drinker) fo;
                
                choosenEntryPoint = drinkerIsReachable(d);
                if (drinkerShouldBeCaught(d) && choosenEntryPoint != null) {
                    drinkerToBeCaught = d;
                    break;
                }
            }
        }
        if (drinkerToBeCaught == null || choosenEntryPoint == null) { return; }
        
        Policeman p = mWaitingOfficers.remove(mWaitingOfficers.size() - 1);
        
        mDrinkersToBeCaught.add(drinkerToBeCaught);
        p.recieveCatchOrder(drinkerToBeCaught);
        
        p.setNewPosition(choosenEntryPoint);
        Field.getInstance().addMovable(p);
        mWalkingOfficers.add(p);
    }
    
    
    @Override
    public void handleEndTurn() {
        processReturnedOfficers();
        processUncaughtDrinkers();
        
        //usability. Policeman has to step into Station when returned
        if (mJustReturnedOfficer != null) {
            mWaitingOfficers.add(mJustReturnedOfficer);
            mJustReturnedOfficer = null;
        }
    }

    @Override
    public char getSingleCharDescription() { return 'S'; }

}
