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

import ru.spbau.sd.model.framework.EndTurnListener;
import ru.spbau.sd.model.framework.Field;
import ru.spbau.sd.model.framework.Generator;

public class MumperHouse extends Generator implements EndTurnListener {

    private Mumper mumperOut = null;
    private int delay;
    private int turnsToGo = 0;
    
    public MumperHouse(int x, int y, int delay) {
        super(x, y);
        this.delay = delay;
        
        Field.getInstance().addEndTurnListener(this);
    }
    
    @Override
    public void handleEndTurn() {
        if (mumperOut == null) {
            if (turnsToGo == 0) {
                mumperOut = new Mumper(0, 0, getPosition());
                if (!tryAddMovable(mumperOut)) { mumperOut = null; }
            } else {
                turnsToGo--;
            }
        } else {
            if (getEntryPoints().contains(mumperOut.getPosition()) && mumperOut.hasBottleBeenFound()) {
                Field.getInstance().removeMovable(mumperOut);
                turnsToGo = delay;
                mumperOut = null;
            }
        }
    }

    @Override
    public char getSingleCharDescription() {
        return 'H';
    }

}
