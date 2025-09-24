/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.instructions;

import littlemangame.littleman.littlemanutilities.littlemandata.LittleManWordContainer;
import littlemangame.littlemancommands.LittleManCommands.LittleManAction;
import littlemangame.littlemancommands.LittleManCommands.LittleManCommands;

/**
 * An enum representing the possible destination operands for instructions
 *
 * @author brian
 */
public enum DestinationOperand {

    /**
     * This destination operand is the worksheet
     */
    WORKSHEET(LittleManWordContainer.WORKSHEET),
    /**
     * This destination operand is the notebook page given by the worksheet
     */
    WORKSHEET_INDIRECT(LittleManWordContainer.REMEMBERED_NOTEBOOK_PAGE, LittleManCommands.memorizePageNumberAtContainerAction(littlemangame.littleman.littlemanutilities.littlemandata.LittleManWordContainer.WORKSHEET)),
    /**
     * This destination operand is the notebook page sheet
     */
    NOTEBOOK_PAGE_SHEET(LittleManWordContainer.NOTEBOOK_PAGE_SHEET),
    /**
     * This destination operand is the notebook page given by the notebook page
     * operand
     */
    NOTEBOOK(LittleManWordContainer.REMEMBERED_NOTEBOOK_PAGE);
    private final LittleManWordContainer destinationContainer;
    private final LittleManAction preparationAction;

    private DestinationOperand(LittleManWordContainer destinationContainer) {
        this.destinationContainer = destinationContainer;
        this.preparationAction = LittleManCommands.nullAction;
    }

    private DestinationOperand(LittleManWordContainer destinationContainer, LittleManAction preparationAction) {
        this.destinationContainer = destinationContainer;
        this.preparationAction = preparationAction;
    }

    public LittleManWordContainer getDestinationContainer() {
        return destinationContainer;
    }

    /**
     * The action the little man is to perform before he actually begins to
     * write to the destination operand
     *
     * @return
     */
    public LittleManAction getPreparationAction() {
        return preparationAction;
    }

}
