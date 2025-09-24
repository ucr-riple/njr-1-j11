/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.computer.computercomponents;

import Renderer.Drawable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import littlemangame.word.BinaryWordOperation;
import littlemangame.word.UnaryWordOperation;
import littlemangame.word.Word;
import littlemangame.word.WordContainer;

/**
 * The notebook class essentially holds list of {@link WordContainer}s. Instead
 * of being
 * indexed by ints, a WordContainer is indexed by {@link Word}s. Thus the
 * number of
 * words a notebook can hold is {@link Word#NUM_WORDS}.
 *
 * A notebook is the analog of main memory in a real computer
 *
 * @author brian
 */
public class Notebook implements Drawable {

    static protected final int xPosition = 400;
    static protected final int yPosition = 20;
    static protected final int width = 50;
    static protected final int height = 400;
    static private final int numWords = 100;
    private final List<WordContainer> notebook = new ArrayList<>(numWords);

    {
//        final Word input = Word.valueOfLastDigitsOfInteger(90);
//        final Word newValue = Word.valueOfLastDigitsOfInteger(91);
//        final Word oldValue = Word.valueOfLastDigitsOfInteger(92);
//        final Word newCopy = Word.valueOfLastDigitsOfInteger(93);
//        final Word printOld = Word.valueOfLastDigitsOfInteger(70);
//        final Word printNew = Word.valueOfLastDigitsOfInteger(80);
//        int i = 0;
//        //get input
//        notebook.add(new WordContainer(InstructionFromSet.INPUT.getOpcode()));
//        i++;
//        //save input
//        notebook.add(new WordContainer(InstructionFromSet.STORE_REGISTER_MEMORY.getOpcode()));
//        i++;
//        notebook.add(new WordContainer(input));
//        i++;
//        //jump if zero
//        notebook.add(new WordContainer(InstructionFromSet.JUMP_IF_ZERO.getOpcode()));
//        i++;
//        notebook.add(new WordContainer(printOld));
//        i++;
//        final Word loop = Word.valueOfLastDigitsOfInteger(i);
//        //add oldValue to newValue
//        notebook.add(new WordContainer(InstructionFromSet.LOAD_MEMORY.getOpcode()));
//        i++;
//        notebook.add(new WordContainer(oldValue));
//        i++;
//        notebook.add(new WordContainer(InstructionFromSet.ADD_REGISTER_TO_MEMORY.getOpcode()));
//        i++;
//        notebook.add(new WordContainer(newValue));
//        //move newCopy to oldValue
//        notebook.add(new WordContainer(InstructionFromSet.LOAD_MEMORY.getOpcode()));
//        i++;
//        notebook.add(new WordContainer(newCopy));
//        i++;
//        notebook.add(new WordContainer(InstructionFromSet.STORE_REGISTER_MEMORY.getOpcode()));
//        i++;
//        notebook.add(new WordContainer(oldValue));
//        i++;
//        //move newValue to newCopy
//        notebook.add(new WordContainer(InstructionFromSet.LOAD_MEMORY.getOpcode()));
//        i++;
//        notebook.add(new WordContainer(newValue));
//        i++;
//        notebook.add(new WordContainer(InstructionFromSet.STORE_REGISTER_MEMORY.getOpcode()));
//        i++;
//        notebook.add(new WordContainer(newCopy));
//        i++;
//        //decrement input
//        notebook.add(new WordContainer(InstructionFromSet.DECREMENT_MEMORY.getOpcode()));
//        i++;
//        notebook.add(new WordContainer(input));
//        i++;
//        //load input
//        notebook.add(new WordContainer(InstructionFromSet.LOAD_MEMORY.getOpcode()));
//        i++;
//        notebook.add(new WordContainer(input));
//        i++;
//        //jump to return if zero
//        notebook.add(new WordContainer(InstructionFromSet.JUMP_IF_ZERO.getOpcode()));
//        i++;
//        notebook.add(new WordContainer(printNew));
//        i++;
//        //jump to loop
//        notebook.add(new WordContainer(InstructionFromSet.UNCONDITIONAL_JUMP.getOpcode()));
//        i++;
//        notebook.add(new WordContainer(loop));
//        i++;
//        for (; i < numWords; i++) {
//            notebook.add(new WordContainer(Word.ZERO_WORD));
//        }
//        notebook.set(70, new WordContainer(InstructionFromSet.LOAD_MEMORY.getOpcode()));
//        notebook.set(71, new WordContainer(oldValue));
//        notebook.set(72, new WordContainer(InstructionFromSet.PRINT_UNSIGNED.getOpcode()));
//        notebook.set(73, new WordContainer(InstructionFromSet.HALT.getOpcode()));
//        notebook.set(80, new WordContainer(InstructionFromSet.LOAD_MEMORY.getOpcode()));
//        notebook.set(81, new WordContainer(newValue));
//        notebook.set(82, new WordContainer(InstructionFromSet.PRINT_UNSIGNED.getOpcode()));
//        notebook.set(83, new WordContainer(InstructionFromSet.HALT.getOpcode()));
//
//        setWordAtPage(input, Word.valueOfLastDigitsOfInteger(8));
//        setWordAtPage(oldValue, Word.valueOfLastDigitsOfInteger(1));
        for (int i = 0; i < numWords; i++) {
            notebook.add(new WordContainer(Word.ZERO_WORD));
        }
    }

    /**
     * Constructs a notebook. No guarantees are made about the initial contents
     * of the notebook.
     */
    public Notebook() {

    }

    /**
     * Copy constructor. Creates a notebook whose contents are identical to the
     * given notebook. Subsequent changes in the given notebook will not affect
     * this notebook.
     *
     * @param memory the notebook to be copied
     */
    public Notebook(Notebook memory) {
        loadCopyOfNotebook(memory);
    }

    @Override
    public void draw(Graphics graphics) {
        final Color color = graphics.getColor();
        graphics.setColor(Color.blue);
        graphics.drawRect(xPosition, yPosition, width, height);
        final int step = height / numWords;
        final int maxYPos = yPosition + height;
        for (int yPos = 20; yPos <= maxYPos; yPos += step) {
            graphics.drawLine(xPosition, yPos, xPosition + width, yPos);
        }
        graphics.setColor(color);
    }

    /**
     * sets the memory at the page specified by the given word to the given
     * word.
     *
     * @param page a word whose unsigned value gives the page to be written to
     * @param wordToBeStored the word to be stored on the given page
     */
    public void setWordAtPage(Word page, Word wordToBeStored) {
        notebook.get(page.getUnsignedValue()).setWord(wordToBeStored);
    }

    /**
     * modifies the contents of this notebook to be identical to the given
     * notebook. This is a deep copy: subsequent changes to the given notebook
     * will not affect this notebook.
     *
     * @param notebook the notebook to be copied
     */
    public final void loadCopyOfNotebook(Notebook notebook) {
        Iterator<Word> wordIterator = Word.getIterator();
        while (wordIterator.hasNext()) {
            final Word word = wordIterator.next();
            setWordAtPage(word, notebook.getNotebookPageValue(word).getWord());
        }
    }

    /**
     * returns the word container corresponding to the page corresponding to the
     * given word
     *
     * @param page the page whose word is to be retrieved
     *
     * @return the given page as a word container
     */
    private WordContainer getNotebookPageValue(Word page) {
        return notebook.get(page.getUnsignedValue());
    }

    /**
     * returns the word stored on the page corresponding to the given word
     *
     * @param page the page whose word is to be retrieved
     *
     * @return the word stored on the given page
     */
    public Word getWordOnPage(Word page) {
        return getNotebookPageValue(page).getWord();
    }

    /**
     * performs the given unary operation to the word on the given page of this
     * notebook
     *
     * @param page the page on which to do the unary operation
     * @param unaryWordOperation the unary operation to perform
     */
    public void doUnaryOperationOnPage(Word page, UnaryWordOperation unaryWordOperation) {
        getNotebookPageValue(page).doUnaryOperation(unaryWordOperation);
    }

    /**
     * performs the given binary operation to the word on the given page of this
     * notebook.
     *
     * @param page the page on which to do the binary operation
     * @param binaryWordOperation the binary operation to perform
     * @param operand the second operand of the binary operation
     */
    public void doBinaryOperationOnPage(Word page, BinaryWordOperation binaryWordOperation, Word operand) {
        getNotebookPageValue(page).doBinaryOperation(binaryWordOperation, operand);
    }

    /**
     * gets the point where the little man has to go to access the given page
     *
     * @param page the pages whose location is to be returned
     *
     * @return the point the little man has to go to in order to access this
     * page.
     */
    public Point getAccessLocation(Word page) {
        final int x = xPosition - 10;
        final int y = yPosition + page.getUnsignedValue() * height / numWords;
        return new Point(x, y);
    }

}
