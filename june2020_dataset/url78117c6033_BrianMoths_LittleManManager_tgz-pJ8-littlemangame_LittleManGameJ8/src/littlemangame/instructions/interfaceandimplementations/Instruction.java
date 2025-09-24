/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.instructions.interfaceandimplementations;

import littlemangame.littlemancommands.LittleManCommands.LittleManAction;

/**
 * An instruction the little man can carry out. These are analogous to
 * instructions on a computer. Since instructions are typically backed by a
 * little man action, they will have state indicating the little man's progress
 * in completing the action.
 *
 * @author brian
 */
public interface Instruction {

    /**
     * returns the action the little man is to do when it does this instruction
     *
     * @return the action the little man is to do when it does this instruction
     */
    public LittleManAction getAction();

    /**
     * tests whether this instruction takes a data operand.
     *
     * @return whether this instruction takes a data operand
     */
    public boolean isDataOperandNeeded();

    /**
     * tests whether this instruction takes a page number operand.
     *
     * @return whether this instruction takes a page number operand.
     */
    public boolean isPageNumberOperandNeeded();

    /**
     * returns a reset copy of this instruction. By reset, we mean that the
     * progress the little man had previously made in completing this
     * instruction is forgotten and the little man must start over.
     *
     * @return a reset copy of this instruction
     */
    public Instruction getResetCopy();

}
