/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.instructions.interfaceandimplementations;

import littlemangame.littlemancommands.LittleManCommands.LittleManAction;
import littlemangame.littlemancommands.LittleManCommands.LittleManActionSequence;

/**
 * A simple instruction. It is simple in the sense that to construct an
 * instruction of this type, you just tell it the operand types and a little man
 * action. There are no additional constructor arguments. In particular, you do
 * not parse an opcode to get information about, for example, what notebook page
 * to use. Hence the name parseless instruction.
 *
 * @author brian
 */
public class ParselessInstruction implements Instruction {

    private final InstructionOperandTypes instructionOperandTypes;
    private final LittleManAction littleManAction;

    /**
     * constructs an instruction with the given operand types representing the
     * given action
     *
     * @param instructionOperandTypes the operands taken by this instruction
     * @param littleManAction the action the little man is to do to carry out
     * this instruction
     */
    public ParselessInstruction(InstructionOperandTypes instructionOperandTypes, LittleManAction littleManAction) {
        this.instructionOperandTypes = instructionOperandTypes;
        this.littleManAction = littleManAction;
    }

    /**
     * constructs an instruction with the given operand types representing the
     * given sequence of actions
     *
     * @param instructionOperandTypes the operands taken by this instruction
     * @param littleManActions a parameter list giving the sequence of actions
     * the little man is to perform
     */
    public ParselessInstruction(InstructionOperandTypes instructionOperandTypes, LittleManAction... littleManActions) {
        this(instructionOperandTypes, new LittleManActionSequence(littleManActions));
    }

    @Override
    public boolean isDataOperandNeeded() {
        return instructionOperandTypes.isDataOperandNeeded();
    }

    @Override
    public LittleManAction getAction() {
        return littleManAction;
    }

    @Override
    public boolean isPageNumberOperandNeeded() {
        return instructionOperandTypes.isAddressOperandNeeded();
    }

    @Override
    public Instruction getResetCopy() {
        return new ParselessInstruction(instructionOperandTypes, littleManAction.getResetCopy());
    }

}
