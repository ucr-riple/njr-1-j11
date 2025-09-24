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

import java.util.Random;

import ru.spbau.sd.model.framework.Field;
import ru.spbau.sd.model.framework.FieldGeometry;
import ru.spbau.sd.model.framework.FieldObject;
import ru.spbau.sd.model.framework.InteractionStrategy;
import ru.spbau.sd.model.framework.MovableObject;
import ru.spbau.sd.model.framework.Point2D;

public class Drinker extends MovableObject {

    private Bottle bottle = new Bottle(-1, -1);
    private Random rnd = new Random();
    
    //drinker's movement strategies
    public static enum MovementStrategy {
        
        SLEEP {
          @Override
          public Point2D makeMove(FieldObject obj) { 
              return obj.getPosition();
          }
          @Override public char getSingleCharRepr() { return 'Z'; }
        },
        LAYING {
          @Override
          public Point2D makeMove(FieldObject obj) { 
            return obj.getPosition();
          }
          @Override public char getSingleCharRepr() { return '&'; }
        },
        ALIVE {
          private Random rnd = new Random();
          @Override
          public Point2D makeMove(FieldObject obj) {
              FieldGeometry fg = Field.getInstance().getGeometry();
              return fg.getNeighborByDir(rnd.nextInt(fg.getNeighborCnt()), obj.getX(), obj.getY());
          }
          @Override
          public char getSingleCharRepr() { return 'D'; }
        };
        
        abstract public Point2D makeMove(FieldObject obj);
        abstract public char getSingleCharRepr();
    }
    
    private MovementStrategy mMovementStr = MovementStrategy.ALIVE;
    public MovementStrategy getMovementStrategy() { return mMovementStr; }
    
    public Drinker(int x, int y) {
        super(x,y);
        registerInteractionHandler(Drinker.class, Column.class,
          new InteractionStrategy<Drinker, Column>() {
            @Override
            public void performInteraction(Drinker drinker, Column column) {
                drinker.mMovementStr =  MovementStrategy.SLEEP;
            }
          }
        );
        registerInteractionHandler(Drinker.class, Bottle.class,
           new InteractionStrategy<Drinker, Bottle>() {
             @Override
             public void performInteraction(Drinker d, Bottle b) {
                 mMovementStr = MovementStrategy.LAYING;
             }
           }
        );
        registerInteractionHandler(Drinker.class, Drinker.class,
          new InteractionStrategy<Drinker, Drinker>() {
            @Override
            public void performInteraction(Drinker obj1, Drinker buddy) {
                if (buddy.mMovementStr == MovementStrategy.SLEEP) {
                    mMovementStr = MovementStrategy.SLEEP;
                }
                //LAYING -- do nothing
                //ALIVE -- it's bang, so do nothing
            }
        }
       );
       //policemen, light -- do nothing so no reasons for register
    }
    
    @Override
    public Point2D move() { return mMovementStr.makeMove(this); }
    
    @Override
    public char getSingleCharDescription() {
        return mMovementStr.getSingleCharRepr();
    }
    
    @Override
    public void setNewPosition(int x, int y) {
        boolean bottleIsThrowable = 
                mMovementStr == MovementStrategy.ALIVE && bottle != null;
        if (bottleIsThrowable && rnd.nextInt(30) == 0) {
            bottle.setNewPosition(getX(), getY());
            Field.getInstance().addStationary(bottle);
            bottle = null;
        }
        
        super.setNewPosition(x,y);
    }
    
}
