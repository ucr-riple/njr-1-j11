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

package ru.spbau.sd;

import java.util.HashSet;
import java.util.Set;

import ru.spbau.sd.field.hex.HexFieldDescriptorsFactory;
import ru.spbau.sd.field.sq.SquareFieldDescriptorsFactory;
import ru.spbau.sd.model.framework.Field;
import ru.spbau.sd.model.framework.FieldDescriptorsFactory;
import ru.spbau.sd.model.game.Column;
import ru.spbau.sd.model.game.Drinker;
import ru.spbau.sd.model.game.Light;
import ru.spbau.sd.model.game.MumperHouse;
import ru.spbau.sd.model.game.PoliceStation;
import ru.spbau.sd.model.game.Tavern;
import ru.spbau.sd.view.FieldConsoleWriter;

/**
 * Main app class
 * 
 * @author Artur Huletski (hatless.fox@gmail.com)
 *
 */
public class Main {

    private static final int NUMBER_OF_TRIALS = 500;
    
    private static Set<Integer> sTrialsToBeShown = new HashSet<>();
    static {
        sTrialsToBeShown.add(22);
        sTrialsToBeShown.add(300);
        sTrialsToBeShown.add(500);
    }
   
    public static void main(String[] args) {
        //Setting up game stuff
        FieldDescriptorsFactory fdf = args.length != 0 && args[0] == "sq" ?
                new SquareFieldDescriptorsFactory() :
                new HexFieldDescriptorsFactory();
        Field.init(15, 15, fdf);
        
        Field.getInstance().addMovable(new Drinker(0, 0));
        Field.getInstance().addStationary(new Column(7, 7));
        Field.getInstance().addOutside(new Tavern(9, -1, 20));
        Field.getInstance().addStationary(new Light(10, 3, 3));
        Field.getInstance().addOutside(new PoliceStation(15, 3, 1));//3));
        Field.getInstance().addOutside(new MumperHouse(-1, 4, 30));
        
        // More fun
        /*
        Field.getInstance().addStationary(new Light(12, 7,3));
        Field.getInstance().addMovable(new Drinker(10, 2));
        Field.getInstance().addStationary(new Column(10, 4));
        Field.getInstance().addOutside(new Tavern(15, 7, 5));
        Field.getInstance().addOutside(new Tavern(15, 10, 5));
        Field.getInstance().addOutside(new MumperHouse(15, 13, 20));
        */
        
        for (int i = 1; i <= NUMBER_OF_TRIALS; i++) {
            Field.getInstance().simulateRound();
            if (sTrialsToBeShown.isEmpty() || sTrialsToBeShown.contains(i)) {
                FieldConsoleWriter.printField();
            }
            Field.getInstance().handleEndTurn();
            
            if (sTrialsToBeShown.isEmpty()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                }
            }
        }
    }

}
