/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.computer;

import Renderer.Drawable;
import java.awt.Graphics;
import littlemangame.computer.computercomponents.Notebook;
import littlemangame.computer.computercomponents.NotebookPageSheet;
import littlemangame.computer.computercomponents.OfficeInputter;
import littlemangame.computer.computercomponents.OfficeOutputter;
import littlemangame.computer.computercomponents.Worksheet;
import littlemangame.word.BinaryWordOperation;
import littlemangame.word.UnaryWordOperation;
import littlemangame.word.Word;

/**
 * A class to represent the office that a little man works in. The office is
 * supposed to be analogous to a computer. An office is composed of five
 * components: a worksheet, which is analogous to a register; a notebook which
 * is analogous to memory; a notebookpagesheet, which is analogous to an
 * instruction pointer;a office outputter, which is analogous to an output
 * device; and a office inputter, which is analogous a computer input device.
 *
 * This class has operation for setting the contents of the office's notebook
 * and performing operations on the office's various word containers (i.e., a
 * notebook page, the notebook page sheet, and the worksheet.)
 *
 * @author brian
 * @param <T> the type of worksheet to use
 * @param <U> the type of notebook to use
 * @param <V> the type of notebook page sheet to use
 * @param <W> the type of computer outputter to use
 * @param <X> the type of computer inputter to use
 */
public class GenericOffice<T extends Worksheet, U extends Notebook, V extends NotebookPageSheet, W extends OfficeOutputter, X extends OfficeInputter>
        implements Drawable {

    /**
     * the worksheet in the office (analogous to a register)
     */
    public final T worksheet;

    /**
     * the notebook in the office (analogous to memory)
     */
    public final U notebook;

    /**
     * the notebook page sheet, specifying the page of the notebook the little
     * man is on (analogous to an instruction pointer)
     */
    public final V notebookPageSheet;

    /**
     * the output panel gives a way for the little man to give output
     */
    public final W outputPanel;

    /**
     * this input panel gives a way for the little man to receive input
     */
    public final X inputPanel;

    /**
     * creates an office with the given components
     *
     * @param worksheet the worksheet for the office
     * @param notebook the notebook for the office
     * @param notebookPageSheet the notebook page sheet for the office
     * @param outputPanel the output panel for the office
     * @param inputPanel the input panel for the office
     */
    public GenericOffice(T worksheet, U notebook, V notebookPageSheet, W outputPanel, X inputPanel) {
        this.worksheet = worksheet;
        this.notebook = notebook;
        this.notebookPageSheet = notebookPageSheet;
        this.outputPanel = outputPanel;
        this.inputPanel = inputPanel;
    }

    @Override
    public void draw(Graphics graphics) {
        worksheet.draw(graphics);
        notebook.draw(graphics);
        notebookPageSheet.draw(graphics);
    }

    /**
     * Resets the notebookPageSheet to the zero word and clears the output
     * panel. The worksheet and notebook are left unchanged.
     */
    public void reset() {
        notebookPageSheet.setWord(Word.ZERO_WORD);
        outputPanel.clear();
    }

    /**
     * copies the contents of the given notebook into the notebook of this
     * office
     *
     * @param notebook the notebook to be copied
     */
    public void loadCopyOfNotebook(Notebook notebook) {
        this.notebook.loadCopyOfNotebook(notebook);
    }

    /**
     * gets the value of the word written in the given
     * {@link OfficeWordContainer}.
     *
     * @param computerWordContainer the officeWordContainer whose word is to
     * returned.
     *
     * @return the word stored at the given office word container
     */
    public Word getValueOfComputerWordContainer(OfficeWordContainer computerWordContainer) {
        return computerWordContainer.getWord(this);
    }

    /**
     * performs the given {@link UnaryWordOperation} on the given
     * {@link OfficeWordContainer}.
     *
     * @param officeWordContainer the office word container to be operated on
     * @param unaryWordOperation the operation to perform
     */
    public void doUnaryOperation(OfficeWordContainer officeWordContainer, UnaryWordOperation unaryWordOperation) {
        officeWordContainer.doUnaryOperation(this, unaryWordOperation);
    }

    /**
     * performs the given {@link BinaryWordOperation} on the given
     * {@link OfficeWordContainer} with the given operand.
     *
     * @param officeWordContainer the office word container to be operated on
     * @param binaryWordOperation the operation to perform
     * @param operand the second operand to be used with the given binary word
     * operation
     */
    public void doBinaryWordOperation(OfficeWordContainer officeWordContainer, BinaryWordOperation binaryWordOperation, Word operand) {
        officeWordContainer.doBinaryOperation(this, binaryWordOperation, operand);
    }

}
