/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.littleman;

import Renderer.Drawable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import littlemangame.computer.computercomponents.Notebook;
import littlemangame.instructions.interfaceandimplementations.Instruction;
import littlemangame.littleman.littlemanutilities.littlemandata.GenericLittleManData;
import littlemangame.littleman.littlemanutilities.littlemandata.LittleManTest;
import littlemangame.littleman.littlemanutilities.littlemandata.LittleManWordContainer;
import littlemangame.littleman.littlemanutilities.location.LittleManPosition;
import littlemangame.littleman.littlemanutilities.location.OfficeLocation;
import littlemangame.word.BinaryWordOperation;
import littlemangame.word.UnaryWordOperation;

/**
 * A class representing the little man himself. The little man is aware of the
 * office he is in, as both the little man data and position getter adapter
 * depend on the office.
 *
 * The little man is able to move around the office, change the values of the
 * office's word containers with the words he is remembering, memorize words in
 * the office's word containers, get input from the inputter, and output to the
 * outputter. There are also methods to test the state of the little man, to do
 * a little man action.
 *
 * @author brian
 * @param <T>
 */
public class GenericLittleMan<T extends GenericLittleManData<?>> implements Drawable {

    static private final int pathY = 200;
    static private final int stepSize = 4;
    private final LittleManPosition littleManPosition;
    private final T littleManData;
    private Instruction instruction;
    private boolean isHalted;

    /**
     * constructs a generic little man with the given position getter adapter
     * and little man data.
     *
     * @param littleManData the little man data representing the data in the
     * office and the little man's short term memory
     * @param positionGetterAdapter responsible for stating the position of
     * various components in the office
     */
    public GenericLittleMan(T littleManData, PositionGetterAdapter positionGetterAdapter) {
        this.littleManData = littleManData;
        littleManPosition = new LittleManPosition(pathY, stepSize, new Point(200, pathY), positionGetterAdapter);
        isHalted = false;
    }

    /**
     * the little man takes a step towards the given computer location
     *
     * @param computerLocation the location to which the little man should move
     *
     * @return whether or not the little man is at the given location after the
     * move
     */
    public boolean goToComputerLocation(OfficeLocation computerLocation) {
        return littleManPosition.goTo(computerLocation);
    }

    //<editor-fold defaultstate="collapsed" desc="container">
    /**
     * the little man moves toward the word container. If the little man is
     * already at the word container, he commits the word in the given container
     * into his short
     * term data memory. The previously remembered value is lost.
     *
     * @param littleManWordContainer the word container whose value is to be
     * memorized
     *
     * @return whether or not the little man was able to complete this operation
     */
    public boolean memorizeDataAtContainer(LittleManWordContainer littleManWordContainer) {
        if (!littleManPosition.isAtLocation(littleManWordContainer.getOfficeLocation())) {
            littleManPosition.goTo(littleManWordContainer.getOfficeLocation());
            return false;
        } else {
            littleManData.memorizeDataAtContainer(littleManWordContainer);
            return true;
        }
    }

    /**
     * the little man moves toward the word container. If the little man is
     * already at the word container,
     * the little man commits the word in the given container into his short
     * term page number memory. The previously remembered value is lost.
     *
     * @param littleManWordContainer the word container whose value is to be
     * memorized
     *
     * @return whether or not the little man was able to complete this operation
     */
    public boolean memorizePageNumberAtContainer(LittleManWordContainer littleManWordContainer) {

        if (!littleManPosition.isAtLocation(littleManWordContainer.getOfficeLocation())) {
            littleManPosition.goTo(littleManWordContainer.getOfficeLocation());
            return false;
        } else {
            littleManData.memorizePageNumberAtContainer(littleManWordContainer);
            return true;
        }
    }

    /**
     * the little man moves toward the word container. If the little man is
     * already at the word container,
     * performs the given binary operation on the given container, using his
     * remembered data as an operand. Using the remembered data
     * causes it to be forgotten, so the little man will not be remembering any
     * data after this operation.
     *
     * @param littleManWordContainer the word container on which to perform the
     * operation
     * @param binaryWordOperation the binary operation to perform
     *
     * @return whether or not the little man was able to complete this operation
     */
    public boolean doBinaryOperationOnContainer(LittleManWordContainer littleManWordContainer, BinaryWordOperation binaryWordOperation) {
        if (!littleManPosition.isAtLocation(littleManWordContainer.getOfficeLocation())) {
            littleManPosition.goTo(littleManWordContainer.getOfficeLocation());
            return false;
        } else {
            littleManData.doBinaryOperationOnContainer(littleManWordContainer, binaryWordOperation);
            return true;
        }
    }

    /**
     * the little man moves toward the word container. If the little man is
     * already at the word container,
     * performs the given unary word operation on the given container.
     *
     * @param littleManWordContainer the word container on which to operate
     * @param unaryWordOperation the operation to perform
     *
     * @return whether or not the little man was able to complete this operation
     */
    public boolean doUnaryOperationOnContainer(LittleManWordContainer littleManWordContainer, UnaryWordOperation unaryWordOperation) {
        if (!littleManPosition.isAtLocation(littleManWordContainer.getOfficeLocation())) {
            littleManPosition.goTo(littleManWordContainer.getOfficeLocation());
            return false;
        } else {
            littleManData.doUnaryOperationOnContainer(littleManWordContainer, unaryWordOperation);
            return true;
        }
    }
    //</editor-fold>

    /**
     * the little man moves toward the output panel. If the little man is
     * already at the output panel, the word remembered as data is printed to
     * the output panel. An error is thrown if there is no remembered data.
     *
     * @return whether or not the little man was able to complete this operation
     */
    public boolean printUnsignedToOutputPanel() {
        if (!littleManPosition.isAtLocation(OfficeLocation.OUTPUT_PANEL)) {
            littleManPosition.goTo(OfficeLocation.OUTPUT_PANEL);
            return false;
        } else {
            littleManData.printUnsigedToOutputPanel();
            return true;
        }
    }

    /**
     * the little man moves toward the input panel. If the little man is
     * already at the input panel, the word at the input panel is committed to
     * memory as data.
     *
     * @return whether or not the little man was able to complete this operation
     */
    public boolean getDataFromInputPanel() {
        if (!littleManPosition.isAtLocation(OfficeLocation.INPUT_PANEL)) {
            littleManPosition.goTo(OfficeLocation.INPUT_PANEL);
            return false;
        } else {
            return littleManData.memorizeDataFromInputPanel();
        }
    }

    /**
     * the little man performs the given test on itself and the result is
     * returned
     *
     * @param littleManTest the test to perform
     *
     * @return the result of the test
     */
    public boolean test(LittleManTest littleManTest) {
        return littleManData.test(littleManTest);
    }

    /**
     * the little man forgets any data or address it was remembering.
     */
    public void clearMemory() {
        littleManData.clearMemory();
    }

    /**
     * the little man copies over the contents of the given notebook into his
     * office's notebook. Any information on his office's notebook is lost.
     *
     * @param memory the source notebook to be copied
     */
    public void loadCopyOfMemory(Notebook memory) {
        littleManData.loadCopyOfMemory(memory);
    }

    /**
     * the little man halts any work he was doing. This should be used to
     * indicate the little man's task is complete.
     */
    public void halt() {
        isHalted = true;
    }

    /**
     * the little man returns to his initial state. He begins working again if
     * halted had been called.
     */
    public void reset() {
        littleManData.reset();
        isHalted = false;
    }

    //<editor-fold defaultstate="collapsed" desc="deal with instructions">
    /**
     * the little man looks up what instruction corresponds to his remembered
     * data. This causes him to use (i.e., forget) his remembered data.
     */
    public void decodeRememberedInstruction() {
        instruction = littleManData.decodeRememberedInstruction();
    }

    /**
     * the little man attempts to complete his last remembered instruction.
     *
     * @return whether the little man was able to complete the instruction.
     */
    public boolean doInstruction() {
        return instruction.getAction().doAction(this);
    }
//</editor-fold>

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillOval(getX(), getY(), 10, 10);
        littleManData.draw(graphics, getX(), getY());
    }

    //<editor-fold defaultstate="collapsed" desc="getters">
    /**
     * tests if the last decoded instruction requires a data operand. Returns
     * false if no instruction has been decoded.
     *
     * @return whether the last decoded instruction requires a data operand
     */
    public boolean isDataOperandNeeded() {
        return instruction != null && instruction.isDataOperandNeeded();
    }

    /**
     * tests if the last decoded instruction requires a page number operand.
     * Returns
     * false if no instruction has been decoded.
     *
     * @return whether the last decoded instruction requires a page number
     * operand
     */
    public boolean isPageNumberOperandNeeded() {
        return instruction != null && instruction.isPageNumberOperandNeeded();
    }

    protected final T getLittleManData() {
        return littleManData;
    }

    /**
     * tests if the little man is halted
     *
     * @return whether the little man is halted
     */
    public boolean isHalted() {
        return isHalted;
    }

    private int getX() {
        return littleManPosition.getX();
    }

    private int getY() {
        return littleManPosition.getY();
    }
    //</editor-fold>

}
