package ru.spbau.sd.model.game;

import java.util.Random;

import ru.spbau.sd.model.framework.Field;
import ru.spbau.sd.model.framework.InteractionStrategy;
import ru.spbau.sd.model.framework.MovableObject;
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

import ru.spbau.sd.model.framework.Point2D;

public class Mumper extends MovableObject {

  private boolean isLookingForBottle = true;
  public boolean hasBottleBeenFound() { return !isLookingForBottle; }
  private Point2D mumperHouse;
  private Random bottleCompass = new Random();
  private Point2D tmpDest;
  
  public Mumper(int x, int y, Point2D homeSweetHome) {
      super(x, y);

      mumperHouse = homeSweetHome;
      registerInteractionHandler(Mumper.class, Bottle.class,
          new InteractionStrategy<Mumper, Bottle>() {
              @Override
              public void performInteraction(Mumper obj1, Bottle bottle) {
                  Field.getInstance().removeStationary(bottle);
                  isLookingForBottle = false;
              }
          }
      );
  }
  
  @Override
  public Point2D move() {
      Point2D nextStep = null;
      Point2D mumperPos = getPosition();
      Point2D destPoint = mumperHouse;
      
      if (isLookingForBottle) {
          if (tmpDest != null && tmpDest.equals(mumperPos)) {
              tmpDest = null;
          }
          
          if (tmpDest == null) {
              int x = bottleCompass.nextInt(Field.getInstance().getXBound());
              int y = bottleCompass.nextInt(Field.getInstance().getYBound());
              tmpDest = new Point2D(x, y);
          }
          destPoint = tmpDest;
      }
      
      nextStep = GameUtils.lookUpNextStep(mumperPos, destPoint, Bottle.class);

      if (nextStep.equals(mumperPos) || nextStep.equals(tmpDest)) {
          tmpDest = null;
      }
      return nextStep;
  }

  @Override
  public char getSingleCharDescription() {
      return 'M';
  }

}
