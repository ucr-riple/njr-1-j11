/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.littlemancommands.LittleManCommands;

import littlemangame.littleman.GenericLittleMan;
import littlemangame.littleman.littlemanutilities.littlemandata.LittleManWordContainer;
import littlemangame.word.BinaryWordOperation;
import littlemangame.word.UnaryWordOperation;

/**
 * This class defines a set of little man actions that can be used as commands.
 * These actions will be put together into action sequence and conditional
 * actions to form an instruction set. Thus these actions are analogous to
 * micro-operations of a cisc instruction architecture. Many actions simply call
 * a method of the little man class. Others are sequence of these simple
 * actions.
 *
 * @author brian
 */
public class LittleManCommands {

    private static final LittleManAction memorizeDataPointedByInstructionPointer = new LittleManActionSequence(memorizePageNumberAtContainerAction(LittleManWordContainer.NOTEBOOK_PAGE_SHEET), memorizeDataAtContainerAction(LittleManWordContainer.REMEMBERED_NOTEBOOK_PAGE), doUnaryOperationOnContainerAction(LittleManWordContainer.NOTEBOOK_PAGE_SHEET, UnaryWordOperation.INCREMENT));

    /**
     * an action that tells the little man to halt.
     *
     * @see GenericLittleMan#halt()
     */
    public static final LittleManAction haltAction = new LittleManAction() {
        @Override
        public boolean doAction(GenericLittleMan<?> littleMan) {
            littleMan.halt();
            return false;
        }

    };
    private static final LittleManAction fetchDataOperandIfNecessary = new LittleManAction() {
        @Override
        public boolean doAction(GenericLittleMan<?> littleMan) {
            if (littleMan.isDataOperandNeeded()) {
                return memorizeDataPointedByInstructionPointer.doAction(littleMan);
            } else {
                return true;
            }
        }

    };
    private static final LittleManAction memorizeAddressPointedByInstructionPointer = new LittleManActionSequence(memorizePageNumberAtContainerAction(LittleManWordContainer.NOTEBOOK_PAGE_SHEET), memorizePageNumberAtContainerAction(LittleManWordContainer.REMEMBERED_NOTEBOOK_PAGE), doUnaryOperationOnContainerAction(LittleManWordContainer.NOTEBOOK_PAGE_SHEET, UnaryWordOperation.INCREMENT));
    private static final LittleManAction fetchAddressOperandIfNecessary = new LittleManAction() {
        @Override
        public boolean doAction(GenericLittleMan<?> littleMan) {
            if (littleMan.isPageNumberOperandNeeded()) {
                return memorizeAddressPointedByInstructionPointer.doAction(littleMan);
            } else {
                return true;
            }
        }

    };

    /**
     * An action that tells the little man to do nothing. This action has no
     * effect and is always completed on the first try.
     */
    public static final LittleManAction nullAction = new LittleManAction() {
        @Override
        public boolean doAction(GenericLittleMan<?> littleMan) {
            return true;
        }

    };
    private static final LittleManAction doInstruction = new LittleManAction() {
        @Override
        public boolean doAction(GenericLittleMan<?> littleMan) {
            return littleMan.doInstruction();
        }

    };
    private static final LittleManAction clearMemory = new LittleManAction() {
        @Override
        public boolean doAction(GenericLittleMan<?> littleMan) {
            littleMan.clearMemory();
            return true;
        }

    };
    private static final LittleManAction decodeRememberedInstruction = new LittleManAction() {
        @Override
        public boolean doAction(GenericLittleMan<?> littleMan) {
            littleMan.decodeRememberedInstruction();
            return true;
        }

    };
    private static final LittleManAction fetchInstruction = new LittleManActionSequence(memorizeDataPointedByInstructionPointer, decodeRememberedInstruction, clearMemory);
    private static final LittleManAction doCycle = new LittleManActionSequence(fetchInstruction, fetchDataOperandIfNecessary, fetchAddressOperandIfNecessary, doInstruction, clearMemory);
    private static final LittleManAction getDataFromInputPanelAction = new LittleManAction() {

        @Override
        public boolean doAction(GenericLittleMan<?> littleMan) {
            return littleMan.getDataFromInputPanel();
        }

    };

    private static final LittleManAction printUnsignedToOutputPanelAction = new LittleManAction() {
        @Override
        public boolean doAction(GenericLittleMan<?> littleMan) {
            return littleMan.printUnsignedToOutputPanel();
        }

    };

    /**
     * An action that calls the little man's print unsigned to output panel
     * method.
     *
     * @return the printUnsignedToOutputPanelAction
     *
     * @see GenericLittleMan#printUnsignedToOutputPanel()
     */
    public static LittleManAction getPrintUnsignedToOutputPanelAction() {
        return printUnsignedToOutputPanelAction.getResetCopy();
    }

    /**
     * Generates an action that makes the little man do a binary operation on
     * the given
     * word container. He uses his remembered data as an operand.
     *
     * @param littleManWordContainer the word container to do the operation on
     * @param binaryWordOperation the binary operation to perform
     *
     * @return the action representing the little man doing the given binary
     * operation on the given word container
     *
     * @see
     * GenericLittleMan#doBinaryOperationOnContainer(littlemangame.littleman.littlemanutilities.littlemandata.LittleManWordContainer,
     * littlemangame.word.BinaryWordOperation)
     */
    public static LittleManAction doBinaryOperationOnContainerAction(final LittleManWordContainer littleManWordContainer, final BinaryWordOperation binaryWordOperation) {
        return new LittleManAction() {
            @Override
            public boolean doAction(GenericLittleMan<?> littleMan) {
                return littleMan.doBinaryOperationOnContainer(littleManWordContainer, binaryWordOperation);
            }

        };
    }

    /**
     * Generates an action that corresponds to the little man doing an
     * instruction cycle. An instruction cycle entails reading the notebook page
     * sheet, reading the opcode written on that page, getting operands if
     * necessary, incrementing the notebook page sheet as needed, and actually
     * doing the instruction
     *
     * @return an action that corresponds to the little man doing an
     * instruction cycle.
     */
    public static LittleManAction getDoCycle() {
        return doCycle.getResetCopy();
    }

    /**
     * Generates an action that makes the little man do a unary operation on
     * the given
     * word container.
     *
     * @param littleManWordContainer the word container to do the operation on
     * @param unaryWordOperation the unary operation to perform
     *
     * @return the action representing the little man doing the given unary
     * operation on the given word container
     *
     * @see
     * GenericLittleMan#doUnaryOperationOnContainer(littlemangame.littleman.littlemanutilities.littlemandata.LittleManWordContainer,
     * littlemangame.word.UnaryWordOperation)
     */
    public static LittleManAction doUnaryOperationOnContainerAction(final LittleManWordContainer littleManWordContainer, final UnaryWordOperation unaryWordOperation) {
        return new LittleManAction() {
            @Override
            public boolean doAction(GenericLittleMan<?> littleMan) {
                return littleMan.doUnaryOperationOnContainer(littleManWordContainer, unaryWordOperation);
            }

        };
    }

    /**
     * generates an action that makes the little man get data from the input
     * panel
     *
     * @return an action that makes the little man get data from the input panel
     *
     * @see GenericLittleMan#getDataFromInputPanel()
     */
    public static LittleManAction getGetDataFromInputPanelAction() {
        return getDataFromInputPanelAction.getResetCopy();
    }

    /**
     * generate an action that makes the little man memory the data at the given
     * word container as a page number
     *
     * @param littleManWordContainer the container whose contents are to be
     * memorized as an page number
     *
     * @return an action that makes the little man memory the data at the given
     * word container as an page number
     *
     * @see
     * GenericLittleMan#memorizePageNumberAtContainer(littlemangame.littleman.littlemanutilities.littlemandata.LittleManWordContainer)
     */
    public static LittleManAction memorizePageNumberAtContainerAction(final LittleManWordContainer littleManWordContainer) {
        return new LittleManAction() {
            @Override
            public boolean doAction(GenericLittleMan<?> littleMan) {
                return littleMan.memorizePageNumberAtContainer(littleManWordContainer);
            }

        };
    }

    /**
     * generate an action that makes the little man memory the data at the given
     * word container as data
     *
     * @param littleManWordContainer the container whose contents are to be
     * memorized as data
     *
     * @return an action that makes the little man memory the data at the given
     * word container as data
     *
     * @see
     * GenericLittleMan#memorizeDataAtContainer(littlemangame.littleman.littlemanutilities.littlemandata.LittleManWordContainer)
     */
    public static LittleManAction memorizeDataAtContainerAction(final LittleManWordContainer littleManWordContainer) {
        return new LittleManAction() {
            @Override
            public boolean doAction(GenericLittleMan<?> littleMan) {
                return littleMan.memorizeDataAtContainer(littleManWordContainer);
            }

        };

    }

    private LittleManCommands() {
    }

}
