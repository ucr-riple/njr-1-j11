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

import ru.spbau.sd.model.framework.Field;
import ru.spbau.sd.model.framework.InteractionStrategy;
import ru.spbau.sd.model.framework.MovableObject;
import ru.spbau.sd.model.framework.Point2D;

public class Policeman extends MovableObject {

    private Drinker mBadGuy;
    
    //here are 2flags. If one more will be added consider refactoring to state pattern
    private boolean mBadGuyIsCaught;
    private boolean mIsGoingHome;
    private PoliceStation mPoliceStation;

    public boolean isGoingHome() { return mIsGoingHome; }
    public boolean isBadGuyCaught() { return mBadGuyIsCaught; }
    public Drinker getCatchedBadGuy() { return mBadGuy; }
    
    public Policeman(int x, int y, PoliceStation ps) {
        super(x, y);
        mPoliceStation = ps;
        registerInteractionHandler(Policeman.class, Drinker.class,
          new InteractionStrategy<Policeman, Drinker>() {
            @Override
            public void performInteraction(Policeman obj1, Drinker drinker) {
                if (drinker == mBadGuy) {
                    Field.getInstance().removeMovable(mBadGuy);
                    setNewPosition(mBadGuy.getX(), mBadGuy.getY());
                    mIsGoingHome = mBadGuyIsCaught = true;
                }
            }
          }
       );
    }
    
    public void recieveCatchOrder(Drinker newBadGuy) {
        mBadGuy = newBadGuy;
        mIsGoingHome = mBadGuyIsCaught = false;
    }

    private boolean shouldWait() {
        //guy that going home has less priority
        if (!isGoingHome()) { return false; }
        for (Policeman colleague : mPoliceStation.getWalkingOfficers()) {
            if (Field.areNear(this, colleague) && !colleague.isGoingHome()) {
                return true;
            }
        }
            
        return false;
    }
    
    
    @Override
    public Point2D move() {
        Point2D policemanPos = getPosition();

        //this code is to prevent livelock
        //live lock is possible when one policeman goes home and another one for bad guy
        // in some conditions
        if (shouldWait()) { return policemanPos; }
        
        
        Point2D destPoint = isBadGuyCaught() || isGoingHome() ?
           new Point2D(mPoliceStation.getX(), mPoliceStation.getY()) :
           new Point2D(mBadGuy.getX(), mBadGuy.getY());
       
        Point2D nextStep = GameUtils.lookUpNextStep(policemanPos, destPoint);
        if (nextStep.equals(policemanPos)) {
            mIsGoingHome = true; //can't catch bad guy -- going home
        }
        return nextStep;
    }
    
    @Override
    public char getSingleCharDescription() { return 'P'; }

}
