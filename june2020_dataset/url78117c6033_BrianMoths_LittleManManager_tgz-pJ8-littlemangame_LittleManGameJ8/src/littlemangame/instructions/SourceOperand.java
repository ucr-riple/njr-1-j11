/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.instructions;

import littlemangame.littleman.littlemanutilities.littlemandata.LittleManWordContainer;
import littlemangame.littlemancommands.LittleManCommands.LittleManAction;
import littlemangame.littlemancommands.LittleManCommands.LittleManActionSequence;
import littlemangame.littlemancommands.LittleManCommands.LittleManCommands;

/**
 * An enum to represents source operands for instructions
 *
 * @author brian
 */
public enum SourceOperand {

    /**
     * This source operand is the data operand of the instruction
     */
    IMMEDIATE(LittleManCommands.nullAction),
    /**
     * This source operand is the worksheet
     */
    REGISTER(LittleManWordContainer.WORKSHEET),
    /**
     * This source operand is the word on the notebook page given by the
     * register
     */
    REGISTER_INDIRECT(new LittleManActionSequence(LittleManCommands.memorizePageNumberAtContainerAction(littlemangame.littleman.littlemanutilities.littlemandata.LittleManWordContainer.WORKSHEET), LittleManCommands.memorizeDataAtContainerAction(LittleManWordContainer.REMEMBERED_NOTEBOOK_PAGE))),
    /**
     * This source operand is the word written on the notebook page given by the
     * notebook page operand of the instruction
     */
    MEMORY(LittleManWordContainer.REMEMBERED_NOTEBOOK_PAGE);
    private final LittleManAction operandMemorizer;

    private SourceOperand(LittleManAction operandMemorizer) {
        this.operandMemorizer = operandMemorizer;
    }

    private SourceOperand(LittleManWordContainer littleManWordContainer) {
        this.operandMemorizer = LittleManCommands.memorizeDataAtContainerAction(littleManWordContainer);
    }

    LittleManAction getOperandMemorizer() {
        return operandMemorizer;
    }

}
