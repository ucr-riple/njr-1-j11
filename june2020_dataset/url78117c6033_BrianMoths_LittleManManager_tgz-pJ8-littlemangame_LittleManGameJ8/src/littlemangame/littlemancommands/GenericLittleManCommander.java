/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.littlemancommands;

import Renderer.Drawable;
import java.awt.Graphics;
import littlemangame.computer.computercomponents.Notebook;
import littlemangame.littleman.GenericLittleMan;
import littlemangame.littlemancommands.LittleManCommands.LittleManAction;
import littlemangame.littlemancommands.LittleManCommands.LittleManCommands;

/**
 * This class provides a way to tell the little man to do his cycle while hiding
 * his other methods. It also gives a way to load a notebook into the office
 *
 * @author brian
 * @param <T> The type of generic little man to be commanded
 */
public class GenericLittleManCommander<T extends GenericLittleMan<?>> implements Drawable {

    private final T littleMan;
    private LittleManAction doCycleCommand;

    protected GenericLittleManCommander(T littleMan) {
        this.littleMan = littleMan;
        doCycleCommand = LittleManCommands.getDoCycle();
    }

    /**
     * commands the little man to do his cycle.
     *
     * @return whether or not the little man completed the cycle
     */
    public boolean doCycle() {
        return doCycleCommand.doAction(littleMan);
    }

    /**
     * resets the little man and the do cycle action
     *
     * @see GenericLittleMan#reset()
     */
    public void reset() {
        littleMan.reset();
        doCycleCommand = LittleManCommands.getDoCycle();
    }

    @Override
    public void draw(Graphics graphics) {
        littleMan.draw(graphics);
    }

    /**
     * loads a copy of the given notebook into the little man's office
     *
     * @param memory the notebook to be loaded
     */
    public void loadCopyOfNotebook(Notebook memory) {
        littleMan.loadCopyOfMemory(memory);
    }

    /**
     * tests whether the little man is halted
     *
     * @return whether the little man is halted
     */
    public boolean isHalted() {
        return littleMan.isHalted();
    }

    protected final T getLittleMan() {
        return littleMan;
    }

}
