/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.word;

/**
 * This enum enumerates all of the unary operations on word containers defined
 * in the WordContainer
 * class. By this we mean all void nullary instance methods
 *
 * @author brian
 */
public enum UnaryWordOperation {

    /**
     * the operation of incrementing a word
     *
     * @see Word#incrementedWord()
     */
    INCREMENT(new UnaryWordOperator() {
        @Override
        public void operate(WordContainer destination) {
            destination.increment();
        }

    }),
    /**
     * the operation of decrementing a word
     *
     * @see Word#decrementedWord()
     */
    DECREMENT(new UnaryWordOperator() {

        @Override
        public void operate(WordContainer destination) {
            destination.decrement();
        }

    }),
    /**
     * the operation of taking a complement of a word
     *
     * @see Word#getComplement()
     */
    COMPLEMENT(new UnaryWordOperator() {

        @Override
        public void operate(WordContainer destination) {
            destination.complement();
        }

    }),
    /**
     * the operation of negating a word
     *
     * @see Word#getOpposite()
     */
    NEGATE(new UnaryWordOperator() {
        @Override
        public void operate(WordContainer destination) {
            destination.invertSign();
        }

    }),
    /**
     * the operation of performing a left shift on a word
     *
     * @see Word#leftShift()
     */
    LEFT_SHIFT(new UnaryWordOperator() {

        @Override
        public void operate(WordContainer destination) {
            destination.leftShift();
        }

    }),
    /**
     * the operation of performing an unsigned right shift on a word
     *
     * @see Word#rightShiftUnsigned()
     */
    RIGHT_SHIFT_UNSIGNED(new UnaryWordOperator() {

        @Override
        public void operate(WordContainer destination) {
            destination.rightShiftUnsigned();
        }

    }),
    /**
     * the operation of performing a signed right shift on a word
     *
     * @see Word#rightShiftSigned()
     */
    RIGHT_SHIFT_SIGNED(new UnaryWordOperator() {

        @Override
        public void operate(WordContainer destination) {
            destination.rightShiftSigned();
        }

    });
    private final UnaryWordOperator unaryWordOperator;

    private UnaryWordOperation(UnaryWordOperator unaryWordOperator) {
        this.unaryWordOperator = unaryWordOperator;
    }

    /**
     * operates on the word container so that the word in the word container is
     * replaced by the result of this operation on that word. This operation
     * typically alters the state of the given word container.
     *
     * @param destination the word container to be operated on
     */
    public void operate(WordContainer destination) {
        unaryWordOperator.operate(destination);
    }

    private static interface UnaryWordOperator {

        void operate(WordContainer destination);

    }

}
