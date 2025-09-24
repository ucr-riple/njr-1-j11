/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.instructions.interfaceandimplementations;

import littlemangame.littlemancommands.LittleManCommands.LittleManAction;

/**
 * Each instruction can take a data operand or not. Independently of this, each
 * instruction can take a page number operand or not. Thus there are four
 * possibilities for operands and instruction can take. This enum enumerates
 * these possibilities.
 *
 * @author brian
 */
public enum InstructionOperandTypes {

    /**
     * Indicates an instruction takes no operands.
     */
    NEITHER(false, false),
    /**
     * Indicates an instruction takes only a data operand.
     */
    DATA_ONLY(true, false),
    /**
     * Indicates an instruction takes only a page number operand.
     */
    PAGE_NUMBER_ONLY(false, true),
    /**
     * Indicates an instruction takes both operands.
     */
    BOTH(true, true);
    private final boolean isDataOperandNeeded, isAddressOperandNeeded;

    private InstructionOperandTypes(boolean isDataOperandNeeded, boolean isAddressOperandNeeded) {
        this.isDataOperandNeeded = isDataOperandNeeded;
        this.isAddressOperandNeeded = isAddressOperandNeeded;
    }

    boolean isDataOperandNeeded() {
        return isDataOperandNeeded;
    }

    boolean isAddressOperandNeeded() {
        return isAddressOperandNeeded;
    }

    Instruction makeInstruction(LittleManAction littleManAction) {
        return new ParselessInstruction(this, littleManAction);
    }

    Instruction makeInstruction(LittleManAction... littleManAction) {
        return new ParselessInstruction(this, littleManAction);
    }

}
