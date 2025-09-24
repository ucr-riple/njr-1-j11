/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package littlemangame.littleman.littlemanutilities.littlemandata;

import littlemangame.littleman.littlemanutilities.location.OfficeLocation;
import littlemangame.word.BinaryWordOperation;
import littlemangame.word.UnaryWordOperation;
import littlemangame.word.Word;
import littlemangame.word.WordContainer;

/**
 * A little man word container represents a container the an office immediately
 * accessible to a littleman. Notice not all notebook pages are immediately
 * accessible because the little man must know the page number. Since a little
 * man can only have one page number in his head at once, there is only one
 * little man word container in the notebook.
 *
 * @author brian
 */
public enum LittleManWordContainer {

    /**
     * The word container corresponding to the notebook page sheet
     */
    NOTEBOOK_PAGE_SHEET(OfficeLocation.PAGE_NUMBER_SHEET, new WordContainerGetter() {
        @Override
        public WordContainer getWordContainer(GenericLittleManData<?> littleManData) {
            return littleManData.getInstructionPointer();
        }

    }),
    /**
     * the worksheet
     */
    WORKSHEET(OfficeLocation.WORKSHEET, new WordContainerGetter() {
        @Override
        public WordContainer getWordContainer(GenericLittleManData<?> littleManData) {
            return littleManData.getRegister();
        }

    }),
    /**
     * the page of the notebook whose page number the little man is remembering.
     */
    REMEMBERED_NOTEBOOK_PAGE(OfficeLocation.REMEMBERED_NOTEBOOK_PAGE, new WordContainerInterface() {

        @Override
        public Word getWord(GenericLittleManData<?> littleManData) {
            return littleManData.getNotebook().getWordOnPage(littleManData.useRememberedAddress());
        }

        @Override
        public void doBinaryOperationOnWordContainer(GenericLittleManData<?> littleManData, BinaryWordOperation binaryWordOperation, Word operand) {
            littleManData.getNotebook().doBinaryOperationOnPage(littleManData.useRememberedAddress(), binaryWordOperation, operand);
        }

        @Override
        public void doUnaryOperationOnWordContainer(GenericLittleManData<?> littleManData, UnaryWordOperation unaryWordOperation) {
            littleManData.getNotebook().doUnaryOperationOnPage(littleManData.useRememberedAddress(), unaryWordOperation);
        }

    });

    private final OfficeLocation locationForInstruction;
    private final WordContainerInterface wordContainerInterface;

    private LittleManWordContainer(OfficeLocation locationForInstruction, WordContainerInterface wordContainerInterface) {
        this.locationForInstruction = locationForInstruction;
        this.wordContainerInterface = wordContainerInterface;
    }

    /**
     * returns the location of this little man word container.
     *
     * @return the location of this little man word container.
     */
    public OfficeLocation getOfficeLocation() {
        return locationForInstruction;
    }

    Word getWord(GenericLittleManData<?> littleManData) {
        return wordContainerInterface.getWord(littleManData);
    }

    void doBinaryOperationOnWordContainer(GenericLittleManData<?> littleManData, BinaryWordOperation binaryWordOperation, Word operand) {
        wordContainerInterface.doBinaryOperationOnWordContainer(littleManData, binaryWordOperation, operand);
    }

    void doUnaryOperationOnWordContainer(GenericLittleManData<?> littleManData, UnaryWordOperation unaryWordOperation) {
        wordContainerInterface.doUnaryOperationOnWordContainer(littleManData, unaryWordOperation);

    }

    static private abstract class WordContainerGetter implements WordContainerInterface {

        abstract WordContainer getWordContainer(GenericLittleManData<?> littleManData);

        @Override
        public Word getWord(GenericLittleManData<?> littleManData) {
            return getWordContainer(littleManData).getWord();
        }

        @Override
        public void doBinaryOperationOnWordContainer(GenericLittleManData<?> littleManData, BinaryWordOperation binaryWordOperation, Word operand) {
            getWordContainer(littleManData).doBinaryOperation(binaryWordOperation, operand);
        }

        @Override
        public void doUnaryOperationOnWordContainer(GenericLittleManData<?> littleManData, UnaryWordOperation unaryWordOperation) {
            getWordContainer(littleManData).doUnaryOperation(unaryWordOperation);
        }

    }

    static private interface WordContainerInterface {

        Word getWord(GenericLittleManData<?> littleManData);

        void doBinaryOperationOnWordContainer(GenericLittleManData<?> littleManData, BinaryWordOperation binaryWordOperation, Word operand);

        void doUnaryOperationOnWordContainer(GenericLittleManData<?> littleManData, UnaryWordOperation unaryWordOperation);

    }
}
