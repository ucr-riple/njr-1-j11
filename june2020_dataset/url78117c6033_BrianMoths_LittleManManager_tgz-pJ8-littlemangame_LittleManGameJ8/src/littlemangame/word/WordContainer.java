/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.word;

/**
 * This class is essentially a mutable version of the {@link Word} class.
 *
 * @author brian
 */
public class WordContainer {

    private Word word;

    /**
     * constructs a word container whose value is the given word.
     *
     * @param word the initial value of this word container
     */
    public WordContainer(Word word) {
        this.word = word;
    }

    /**
     * replaces the word in this word container by the result from incrementing
     * the word.
     *
     * @see Word#incrementedWord()
     */
    public void increment() {
        word = word.incrementedWord();
    }

    /**
     * replaces the word in this word container by the result from decrementing
     * the word.
     *
     * @see Word#decrementedWord()
     */
    public void decrement() {
        word = word.decrementedWord();
    }

    /**
     * replaces the word in this word container by its sum with the given word
     *
     * @param summand the amount by which to increment the word in this word
     * container
     *
     * @see Word#plus(littlemangame.word.Word)
     */
    public void incrementBy(Word summand) {
        word = word.plus(summand);
    }

    /**
     * replaces the word in this word container by its difference with the given
     * word
     *
     * @param subtrahend the amount to be subtracted from the word in this word
     * container
     *
     * @see Word#minus(littlemangame.word.Word)
     */
    public void decrementBy(Word subtrahend) {
        word = word.minus(subtrahend);
    }

    /**
     * replaces the word in this word container by its complement
     *
     * @see Word#getComplement()
     */
    public void complement() {
        word = word.getComplement();
    }

    /**
     * replaces the word in this word container by its opposite
     *
     * @see Word#getOpposite()
     */
    public void invertSign() {
        word = word.getOpposite();
    }

    /**
     * replaces the word in this word container by the result of left shifting
     * it
     *
     * @see Word#leftShift()
     */
    public void leftShift() {
        word = word.leftShift();
    }

    /**
     * replaces the word in this word container by the result of left shifting
     * it the given number of times
     *
     * @param word the number of times to left shift the word in this word
     * container
     *
     * @see Word#leftShift(littlemangame.word.Word)
     */
    public void leftShift(Word word) {
        this.word = this.word.leftShift(word);
    }

    /**
     * replaces the word in this word container by the result of unsigned right
     * shifting it
     *
     * @see Word#rightShiftUnsigned()
     */
    public void rightShiftUnsigned() {
        word = word.rightShiftUnsigned();
    }

    /**
     * replaces the word in this word container by the result of unsigned right
     * shifting it the given number of times
     *
     * @see Word#rightShiftUnsigned(littlemangame.word.Word)
     */
    public void rightShiftUnsigned(Word word) {
        this.word = this.word.rightShiftUnsigned(word);
    }

    /**
     * replaces the word in this word container by the result of signed right
     * shifting it
     *
     * @see Word#rightShiftSigned()
     */
    public void rightShiftSigned() {
        word = word.rightShiftSigned();
    }

    /**
     * replaces the word in this word container by the result of signed right
     * shifting it the given number of times
     *
     * @see Word#rightShiftSigned(littlemangame.word.Word)
     */
    public void rightShiftSigned(Word word) {
        this.word = this.word.rightShiftSigned(word);
    }

    /**
     * replaces the word in this word container by its digitwise maximum with
     * the given word
     *
     * @param word the word whose digitwise maximum is to be taken with the word
     * in this word container
     *
     * @see Word#digitwiseMax(littlemangame.word.Word)
     */
    public void digitwiseMax(Word word) {
        this.word = this.word.digitwiseMax(word);
    }

    /**
     * replaces the word in this word container by its digitwise minimum with
     * the given word
     *
     * @param word the word whose digitwise minimum is to be taken with the word
     * in this word container
     *
     * @see Word#digitwiseMin(littlemangame.word.Word)
     */
    public void digitwiseMin(Word word) {
        this.word = this.word.digitwiseMin(word);
    }

    /**
     * replaces the word in this word container by its unsigned maximum with the
     * given word
     *
     * @param word the word whose unsigned maximum is to be taken with the word
     * in this word container
     *
     * @see Word#unsignedMax(littlemangame.word.Word)
     */
    public void unsignedMax(Word word) {
        this.word = this.word.unsignedMax(word);
    }

    /**
     * replaces the word in this word container by its unsigned minimum with the
     * given word
     *
     * @param word the word whose unsigned minimum is to be taken with the word
     * in this word container
     *
     * @see Word#unsignedMin(littlemangame.word.Word)
     */
    public void unsignedMin(Word word) {
        this.word = this.word.unsignedMin(word);
    }

    /**
     * tests if the signed value of the word in this word container is negative
     *
     * @return whether the word in this word container is negative
     *
     * @see Word#isNegative()
     */
    public boolean isNegative() {
        return word.isNegative();
    }

    /**
     * returns the unsigned value of the word in this word container
     *
     * @return the unsigned value of the word in this word container
     *
     * @see Word#getUnsignedValue()
     */
    public int getUnsignedValue() {
        return word.getUnsignedValue();
    }

    /**
     * returns the signed value of the word in this word container
     *
     * @return the signed value of the word in this word container
     *
     * @see Word#getSignedValue()
     */
    public int getSignedValue() {
        return word.getSignedValue();
    }

    /**
     * performs a comparison between the word in this word container and the
     * given word
     *
     * @param t the word to be compared to
     *
     * @return the result of comparing the word in this word container and the
     * given word
     *
     * @see Word#compareTo(littlemangame.word.Word)
     */
    public int compareTo(Word t) {
        return word.compareTo(t);
    }

    /**
     * performs a comparison between the unsigned value of the word in this word
     * container and the unsigned value of the given word
     *
     * @param t the word to be compared to
     *
     * @return the result of comparing the unsigned value of the word in this
     * word container and the unsigned value of the given word
     *
     * @see Word#compareToUnsigned(littlemangame.word.Word)
     */
    public int compareToUnsigned(Word t) {
        return word.compareToUnsigned(t);
    }

    /**
     * performs a comparison between the signed value of the word in this word
     * container and the signed value of the given word
     *
     * @param t the word to be compared to
     *
     * @return the result of comparing the signed value of the word in this word
     * container and the signed value of the given word
     *
     * @see Word#compareToSigned(littlemangame.word.Word)
     */
    public int compareToSigned(Word t) {
        return word.compareToSigned(t);
    }

    /**
     * returns a string representation of this word container. It simply returns
     * a string representation of the word contained in this word container
     *
     * @return a string representation of this word container.
     *
     * @see Word#toString()
     */
    @Override
    public String toString() {
        return word.toString();
    }

    /**
     * returns a string representation of the unsigned value of the word in this
     * word container.
     *
     * @return a string representation of the unsigned value of the word in this
     * word container.
     *
     * @see Word#toStringUnsigned()
     */
    public String toStringUnsigned() {
        return word.toStringUnsigned();
    }

    /**
     * returns a string representation of the signed value of the word in this
     * word container.
     *
     * @return a string representation of the signed value of the word in this
     * word container.
     *
     * @see Word#toStringSigned()
     */
    public String toStringSigned() {
        return word.toStringSigned();
    }

    /**
     * returns the word in this word container
     *
     * @return the word in this word container
     */
    public Word getWord() {
        return word;
    }

    /**
     * replaces the word in this word container by the given word
     *
     * @param word the word to be stored in this word container
     */
    public void setWord(Word word) {
        this.word = word;
    }

    /**
     * Performs the given unary word operation on this word container.
     *
     * @param unaryWordOperation The word operation to be performed on this word
     * container
     */
    public void doUnaryOperation(UnaryWordOperation unaryWordOperation) {
        unaryWordOperation.operate(this);
    }

    /**
     * performs the given binary word operation on this word container. This
     * word container is the first operand of the binary operation. The given
     * word is the second operand.
     *
     * @param binaryWordOperation The word operation to be performed on this
     * word
     * @param operand the second operand of the binary word operation.
     */
    public void doBinaryOperation(BinaryWordOperation binaryWordOperation, Word operand) {
        binaryWordOperation.operate(operand, this);
    }

}
