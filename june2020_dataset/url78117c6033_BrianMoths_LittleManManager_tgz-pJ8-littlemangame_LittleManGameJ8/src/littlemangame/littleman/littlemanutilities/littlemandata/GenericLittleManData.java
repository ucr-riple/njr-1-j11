/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.littleman.littlemanutilities.littlemandata;

import java.awt.Graphics;
import java.awt.Point;
import littlemangame.computer.GenericOffice;
import littlemangame.computer.computercomponents.Notebook;
import littlemangame.instructions.InstructionFromSet;
import littlemangame.instructions.interfaceandimplementations.Instruction;
import littlemangame.littleman.PositionGetterAdapter;
import littlemangame.littleman.PositionGetterAdapter.PositionGetter;
import littlemangame.word.BinaryWordOperation;
import littlemangame.word.UnaryWordOperation;
import littlemangame.word.Word;
import littlemangame.word.WordContainer;

/**
 * This class is responsible for remembering all the data the little man has and
 * performing the operations that he can do on them. The data that the little
 * man has is his own memory and the data on the computer. At any given time,
 * the little man has access to only three words on the computer. These three
 * words are the word on the worksheet, the word on the notebook page sheet and
 * the word written on his remembered page in the notebook. This class allows
 * the littleman to memorize any of those words as either data or an address. It
 * also allows him to do a unary operation on any of these words or use his
 * remembered data word to do a binary operation on one of these.
 *
 * This class is also responsible for letting the littleman print to the output
 * panel and get memorize what is on the input panel as data.
 *
 * @author brian
 * @param <T>
 */
public class GenericLittleManData<T extends GenericOffice<?, ?, ?, ?, ?>> {

    private final T computer;
    private final LittleManMemory littleManMemory;

    /**
     * constructs a generic little man data representing the data in the given
     * office, where positions are to be communicated with the given position
     * getter adapter.
     *
     * In addition to having the office, the little man has two words he can
     * keep in his head at any time: a data word and a page number word.
     *
     * @param office the office whose data is to be represented
     * @param positionGetterAdapter the adapter which will communicate positions
     */
    public GenericLittleManData(T office, PositionGetterAdapter positionGetterAdapter) {
        this.computer = office;
        littleManMemory = new LittleManMemory();
        positionGetterAdapter.setPositionGetter(makePositionGetter(office));
    }

    /**
     * The little man memorizes the data in the given word container. Any
     * previously memorized data is lost.
     *
     * @param littleManWordContainer the word container whose contents are to be
     * memorized as data
     */
    public void memorizeDataAtContainer(LittleManWordContainer littleManWordContainer) {
        memorizeData(littleManWordContainer.getWord(this));
    }

    /**
     * the little man memorizes the page number stored at the given word
     * container
     *
     * @param littleManWordContainer the word container whose contents are to be
     * memorized as a page number
     */
    public void memorizePageNumberAtContainer(LittleManWordContainer littleManWordContainer) {
        memorizePageNumber(littleManWordContainer.getWord(this));
    }

    /**
     * performs the given binary operation on the given word container using
     * his remembered data as the second operand. Using the remembered data
     * causes it to be forgotten, so the little man will not be remembering any
     * data after this operation.
     *
     * @param littleManWordContainer the word container to be operated on
     * @param binaryWordOperation the operation to perform on the given word
     * container
     */
    public void doBinaryOperationOnContainer(LittleManWordContainer littleManWordContainer, BinaryWordOperation binaryWordOperation) {
        littleManWordContainer.doBinaryOperationOnWordContainer(this, binaryWordOperation, useRememberedData());
    }

    /**
     * performs the given unary operation on the given word container.
     *
     * @param littleManWordContainer the word container to be operated on
     * @param unaryWordOperation the operation to perform
     */
    public void doUnaryOperationOnContainer(LittleManWordContainer littleManWordContainer, UnaryWordOperation unaryWordOperation) {
        littleManWordContainer.doUnaryOperationOnWordContainer(this, unaryWordOperation);
    }

    /**
     * returns the word stored in the given container
     *
     * @param littleManWordContainer the container whose contents are to be
     * returned
     *
     * @return the word in the given container
     */
    public Word getContainerWord(LittleManWordContainer littleManWordContainer) {
        return littleManWordContainer.getWord(this);
    }

    /**
     * prints the remembered data to the office's outputter.
     */
    public void printUnsigedToOutputPanel() {
        computer.outputPanel.printlnUnsigned(useRememberedData());
    }

    /**
     * the little man attempts to retrieve input from the office's inputter. If
     * the inputter is disabled, the little man enables it. If it is enabled,
     * the little man checks if it has data entered and retrieves the data it it
     * does.
     *
     * @return whether or not the little man was able to succesfully retrieve
     * data from the inputter.
     */
    public boolean memorizeDataFromInputPanel() {
        if (computer.inputPanel.isDisabled()) {
            computer.inputPanel.requestInput();
            return false;
        }
        if (computer.inputPanel.isWordEntered()) {
            memorizeData(computer.inputPanel.getEnteredWord());
            return true;
        } else {
            return false;
        }
    }

    /**
     * returns the instruction that the memorized data codes for
     *
     * @return the instruction coded for by the remembered data
     */
    public Instruction decodeRememberedInstruction() {
        return InstructionFromSet.decodeInstruction(useRememberedData());
    }

    /**
     * performs the given littleManTest on this littleManData
     *
     * @param littleManTest the test to perform
     *
     * @return the result of the test
     */
    public boolean test(LittleManTest littleManTest) {
        return littleManTest.test(this);
    }

    /**
     * resets the state of the littleman, clearing his memory and resetting the
     * office.
     */
    public void reset() {
        clearMemory();
        computer.reset();
    }

    //<editor-fold defaultstate="collapsed" desc="containers">
    WordContainer getInstructionPointer() {
        return computer.notebookPageSheet;
    }

    WordContainer getRegister() {
        return computer.worksheet;
    }

    Notebook getNotebook() {
        return computer.notebook;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="short term memory">
    private void memorizeData(Word data) {
        littleManMemory.memorizeData(data);
    }

    private void memorizePageNumber(Word address) {
        littleManMemory.memorizePageNumber(address);
    }

    /**
     * clears the memory of the little man
     */
    public void clearMemory() {
        littleManMemory.clearMemory();
    }

    private Word useRememberedData() {
        return littleManMemory.useRememberedData();
    }

    Word useRememberedAddress() {
        return littleManMemory.useRememberedPageNumber();
    }

    private Word getRememberedAddress() {
        return littleManMemory.getRememberedPageNumber();
    }
    //</editor-fold>

    /**
     * draws the little man data
     *
     * @param graphics the graphics on which to draw the data
     * @param x the x coordinate of where to draw the little man memory
     * @param y the y coordinate of where to draw the little man memory
     */
    public void draw(Graphics graphics, int x, int y) {
        computer.draw(graphics);
        littleManMemory.draw(graphics, x, y);
    }

    private PositionGetter makePositionGetter(final GenericOffice<?, ?, ?, ?, ?> computer) {
        return new PositionGetter() {
            @Override
            public Point getWorksheetPosition() {
                return computer.worksheet.getAccessLocation();
            }

            @Override
            public Point getRememberedPagePosition() {
                return computer.notebook.getAccessLocation(getRememberedAddress());
            }

            @Override
            public Point getPageNumberSheetPosition() {
                return computer.notebookPageSheet.getAccessLocation();
            }

            @Override
            public Point getOutputPanelPosition() {
                return computer.outputPanel.getAccessLocation();
            }

            @Override
            public Point getInputPanelPosition() {
                return computer.inputPanel.getAccessLocation();
            }

        };
    }

    public void loadCopyOfMemory(Notebook memory) {
        computer.loadCopyOfNotebook(memory);
    }

    protected final T getComputer() {
        return computer;
    }

}
