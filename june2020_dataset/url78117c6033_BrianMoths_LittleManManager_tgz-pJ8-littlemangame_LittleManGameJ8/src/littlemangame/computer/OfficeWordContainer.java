/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.computer;

import littlemangame.word.BinaryWordOperation;
import littlemangame.word.UnaryWordOperation;
import littlemangame.word.Word;
import littlemangame.word.WordContainer;

/**
 * A class representing where in an office data can be stored. The places are
 * the worksheet, the page number sheet, or any of the notebook pages.
 *
 * @author brian
 */
public abstract class OfficeWordContainer {

    /**
     * The worksheet, viewed as a word container
     */
    static public final OfficeWordContainer WORKSHEET = new OfficeWordContainer() {

        @Override
        WordContainer getWordContainer(GenericOffice<?, ?, ?, ?, ?> genericComputer) {
            return genericComputer.worksheet;
        }

    };
    static public final OfficeWordContainer PAGE_NUMBER_SHEET = new OfficeWordContainer() {

        @Override
        WordContainer getWordContainer(GenericOffice<?, ?, ?, ?, ?> genericComputer) {
            return genericComputer.notebookPageSheet;
        }

    };

    static public OfficeWordContainer notebookPage(final Word page) {
        return new OfficeWordContainer() {

            @Override
            WordContainer getWordContainer(GenericOffice<?, ?, ?, ?, ?> genericComputer) {
                return new WordContainer(Word.ZERO_WORD); //never used
            }

            @Override
            Word getWord(GenericOffice<?, ?, ?, ?, ?> genericComputer) {
                return genericComputer.notebook.getWordOnPage(page);
            }

            @Override
            void doBinaryOperation(GenericOffice<?, ?, ?, ?, ?> genericComputer, BinaryWordOperation binaryWordOperation, Word operand) {
                genericComputer.notebook.doBinaryOperationOnPage(page, binaryWordOperation, operand);
            }

            @Override
            void doUnaryOperation(GenericOffice<?, ?, ?, ?, ?> genericComputer, UnaryWordOperation unaryWordOperation) {
                genericComputer.notebook.doUnaryOperationOnPage(page, unaryWordOperation);
            }

        };
    }

    private OfficeWordContainer() {
    }

    void doUnaryOperation(GenericOffice<?, ?, ?, ?, ?> genericComputer, UnaryWordOperation unaryWordOperation) {
        final WordContainer wordContainer = getWordContainer(genericComputer);
        wordContainer.doUnaryOperation(unaryWordOperation);
    }

    void doBinaryOperation(GenericOffice<?, ?, ?, ?, ?> genericComputer, BinaryWordOperation binaryWordOperation, Word operand) {
        final WordContainer wordContainer = getWordContainer(genericComputer);
        wordContainer.doBinaryOperation(binaryWordOperation, operand);
    }

    Word getWord(GenericOffice<?, ?, ?, ?, ?> genericComputer) {
        final WordContainer wordContainer = getWordContainer(genericComputer);
        return wordContainer.getWord();
    }

    abstract WordContainer getWordContainer(GenericOffice<?, ?, ?, ?, ?> genericComputer);

}
