/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.littlemancommands.LittleManCommands;

import littlemangame.littleman.GenericLittleMan;

/**
 * Represents an action that can be carried out by a little man. This is
 * typically a method of the littleman class or a sequence of methods, or an
 * action which should only be done if a certain condiition is met. An action
 * can have state, for example indicating the little man's progress. "Elemental"
 * little man actions can only be defined in this package because this
 * constructor has package visibility. Little man actions built out of other
 * little man actions can be constructed anywhere.
 *
 * @author brian
 */
public abstract class LittleManAction {

    LittleManAction() {
    }

    /**
     * makes the given littleman do this action
     *
     * @param littleMan the little man to perform the action
     *
     * @return whether or not the little man completed the action successfully
     */
    public abstract boolean doAction(GenericLittleMan<?> littleMan);

    /**
     * returns a copy of this little man action with its state reset
     *
     * @return a copy of this little man action with its state reset.
     */
    public LittleManAction getResetCopy() {
        return this;
    }

}
