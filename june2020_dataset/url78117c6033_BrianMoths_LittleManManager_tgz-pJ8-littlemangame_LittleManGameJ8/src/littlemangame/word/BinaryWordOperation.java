/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.word;

/**
 * This enum enumerates all of the binary operations on word containers defined
 * in the <t>WordContainer</t> class. By this we mean all void instance methods
 * that have a single Word argument.
 *
 * @author brian
 */
public enum BinaryWordOperation {

    /**
     * the operation of setting the word contained in a word container to the
     * given word
     *
     * @see WordContainer#setWord(littlemangame.word.Word)
     */
    SET(new BinaryWordOperator() {
        @Override
        public void operate(Word source, WordContainer destination) {
            destination.setWord(source);
        }

    }),
    /**
     * the operation of adding the given word to the word container.
     *
     * @see WordContainer#incrementBy(littlemangame.word.Word)
     * @see Word#plus(littlemangame.word.Word)
     */
    ADD(new BinaryWordOperator() {
        @Override
        public void operate(Word source, WordContainer destination) {
            destination.incrementBy(source);
        }

    }),
    /**
     * the operation of subtracting the given word from the word container
     *
     * @see WordContainer#decrementBy(littlemangame.word.Word)
     * @see Word#minus(littlemangame.word.Word)
     */
    SUBTRACT(new BinaryWordOperator() {

        @Override
        public void operate(Word source, WordContainer destination) {
            destination.decrementBy(source);
        }

    }),
    /**
     * the operation of taking the digitwise maximum of the given word with the
     * wordcontainer
     *
     * @see WordContainer#digitwiseMax(littlemangame.word.Word)
     * @see Word#digitwiseMax(littlemangame.word.Word)
     */
    DIGITWISE_MAX(new BinaryWordOperator() {

        @Override
        public void operate(Word source, WordContainer destination) {
            destination.digitwiseMax(source);
        }

    }),
    /**
     * the operation of taking the digitwise minimum of the given word with the
     * wordcontainer
     *
     * @see WordContainer#digitwiseMin(littlemangame.word.Word)
     */
    DIGITWISE_MIN(new BinaryWordOperator() {

        @Override
        public void operate(Word source, WordContainer destination) {
            destination.digitwiseMin(source);
        }

    });
    private final BinaryWordOperator binaryWordOperator;

    private BinaryWordOperation(BinaryWordOperator binaryWordOperator) {
        this.binaryWordOperator = binaryWordOperator;
    }

    /**
     * performs the binary operation on the given word container with the give
     * word as the argument. This method typically alters the state of the given
     * word container
     *
     * @param source the argument of the operation
     * @param destination the word container to be operated on.
     */
    public void operate(Word source, WordContainer destination) {
        binaryWordOperator.operate(source, destination);
    }

    private static interface BinaryWordOperator {

        void operate(Word source, WordContainer destination);

    }

}
