/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.word;

import java.util.Iterator;

/**
 * The word class represents the smallest chunks of data the little man can work
 * with. Here word is meant in the same sense as it is typically meant in
 * computer science.
 *
 * Here, there are 100 possible different values that words can take. The
 * meaning of the values can in principle be anything that is deemed useful.
 * However, there are two interpretations of a word that many of the methods
 * assume. One is an interpretation as an unsigned number between 0 and 99
 * inclusive, and the other is as an signed number between -50 and 49 inclusive.
 * The correspondence between these interpretations is that the words correspond
 * to unsigned numbers from 0 to 49 also correspond to the signed numbers
 * between 0 and 49. The words corresponding to the unsigned numbers between 50
 * and 99 correspond to the signed words obtained by subtracting 100.
 *
 * This class has value semantics. Words are immutable.
 *
 * @author brian
 */
public class Word implements Comparable<Word> {

    /**
     * the number of different values a word can have.
     */
    static public final int NUM_WORDS = 100;

    /**
     * the number of digits in the word.
     */
    static public final int NUM_DIGITS = 2;

    /**
     * the base in which the word is represented
     */
    static public final int BASE = 10;

    /**
     * the smallest signed number that can be represented by a word
     */
    static public final Word MIN_SIGNED = new Word(NUM_WORDS / 2);

    /**
     * the largest signed number that can be represented by word.
     */
    static public final Word MAX_SIGNED = MIN_SIGNED.decrementedWord();

    /**
     * The largest unsigned number that can be represent by a word.
     */
    static public final Word MAX_WORD = new Word(NUM_WORDS - 1);

    /**
     * The word representing zero, the smallest unsigned number that can be
     * represented.
     */
    static public final Word ZERO_WORD = new Word(0);

    /**
     * Converts an int to a word. Any int can be given as input, the output is
     * found by int % 100
     *
     * @param value the integer to be converted to a word.
     *
     * @return the word which, as an unsigned number, is equivalent (mod 100) to
     * the value.
     */
    static public Word valueOfLastDigitsOfInteger(int value) {
        return new Word(value);
    }

    /**
     * A factory method for generating the zero word.
     *
     * @return the word representing 0
     */
    static public Word zeroWord() {
        return ZERO_WORD;
    }

    static private String getDoubleDigitString(int number) {
        return String.format("%02d", number);
    }

    /**
     * A method to create an iterator to iterate over all words from in
     * (unsigned) order.
     *
     * @return An iterator which generates all words from 0 to 99 in order.
     */
    static public Iterator<Word> getIterator() {
        return new Iterator<Word>() {

            private Word word = MAX_WORD;
            private boolean hasNext = true;

            @Override
            public boolean hasNext() {
                return hasNext;
            }

            @Override
            public Word next() {
                word = word.incrementedWord();
                hasNext = !word.equals(Word.MAX_WORD);
                return word;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        };
    }

    private final int value;

    private Word(int value) {
        value = value % NUM_WORDS;
        if (value < 0) {
            value += NUM_WORDS;
        }
        this.value = value;
    }

    /**
     * Copy constructor. Creates a new word instance identical to the one given.
     *
     * @param word the word to be copied
     *
     */
    public Word(Word word) {
        value = word.getUnsignedValue();
    }

    //<editor-fold defaultstate="collapsed" desc="arithmetic and logic">
    /**
     * gives the word coming after this word in order of value as an unsigned
     * number . If this word is
     * already represents the largest unsigned number, the returned word is
     * zero.
     *
     * @return the word that comes after this word, wrapping around to zero if
     * this word is already the largest.
     */
    public Word incrementedWord() {
        return new Word(getUnsignedValue() + 1);
    }

    /**
     * gives the word that comes before this word in order of value. If this
     * word is already the smallest word (the zero word) the largest word is
     * returned.
     *
     * @return the word that comes before this word, wrapping around to the
     * maximum word if this word is already zero.
     */
    public Word decrementedWord() {
        return new Word(getUnsignedValue() - 1);
    }

    /**
     * computes the sum of this word with the summand. The (unsigned) values of
     * words are added. If this result is less than or equal to the maximum
     * unsigned word, so that it can be represented as a word, that word is
     * returned.
     * Otherwise the the number of words is subtracted from that value and the
     * word
     * corresponding to the result of the subtraction is obtained.
     *
     * @param summand the word to be added to this word
     *
     * @return the sum of the two words
     */
    public Word plus(Word summand) {
        return new Word(getUnsignedValue() + summand.getUnsignedValue());
    }

    /**
     * computes the difference between two words. If the value of the difference
     * is negative, then the maximum word is added to it.
     *
     * @param subtrahend the word to subtract from this word
     *
     * @return the value of the difference between this word and the subtrahend.
     */
    public Word minus(Word subtrahend) {
        return new Word(getUnsignedValue() - subtrahend.getUnsignedValue());
    }

    /**
     * returns the _base_ complement of this word. Each digit is replaced by
     * nine minus that digit.
     *
     * @return
     */
    public Word getComplement() {
        return MAX_WORD.minus(this);
    }

    /**
     * computes the opposite of this word (when this word is interpreted as a
     * signed word). An exceptional case is when this word is the smallest
     * signed value (-50). In this case, the opposite cannot be represented as a
     * signed word so the equivalent (mod 100) word is returned (which happens
     * to be -50).
     *
     * @return the opposite of this word.
     */
    public Word getOpposite() {
        return getComplement().incrementedWord();
    }

    /**
     * Shifts the digits of the (unsigned) word to the left. The digit
     * previously on the left is discarded. The new digit of the left is zero.
     *
     * @return the result of shifting this word to the left.
     */
    public Word leftShift() {
        return new Word(getUnsignedValue() * 10);
    }

    /**
     * Computes the result of shifting this word left as many times as indicated
     * by the given word.
     *
     * @param word the number of times this word is to be shifted left
     *
     * @return the result of shifting this word left the given number of times.
     */
    public Word leftShift(Word word) {
        Word shiftedWord = new Word(this);
        while (word.compareTo(ZERO_WORD) > 0 && !shiftedWord.equals(ZERO_WORD)) {
            word = word.decrementedWord();
            shiftedWord = shiftedWord.leftShift();
        }
        return shiftedWord;
    }

    /**
     * Computes the resulting of shifting the digits of an unsigned word right.
     * The digit previously on the right is discarded. The new digit on the left
     * is zero
     *
     * @return the result of shifting the digits of this word, viewed as an
     * unsigned word, right
     */
    public Word rightShiftUnsigned() {
        return new Word(getUnsignedValue() / 10);
    }

    /**
     * computes the result of shifting the digits of an unsigned word right the
     * given number of times.
     *
     * @param word the number of times this word is to be shifted right
     *
     * @return the result of shifting this word right the given number of times.
     */
    public Word rightShiftUnsigned(Word word) {
        Word shiftedWord = new Word(this);
        while (word.compareTo(ZERO_WORD) > 0 && !shiftedWord.equals(ZERO_WORD)) {
            word = word.decrementedWord();
            shiftedWord = shiftedWord.rightShiftUnsigned();
        }
        return shiftedWord;
    }

    /**
     * Computes the resulting of shifting the digits of an signed word right.
     * The digit previously on the right is discarded. The new digit on the left
     * is zero if this word is positive or 9 if this word is negative.
     *
     * @return the result of shifting the digits of this word, viewed as an
     * signed word, right
     */
    public Word rightShiftSigned() {
        Word returnValue = rightShiftUnsigned();
        if (isNegative()) {
            returnValue = returnValue.plus(new Word(90));
        }
        return returnValue;
    }

    /**
     * computes the result of shifting the digits of an signed word right the
     * given number of times.
     *
     * @param word the number of times this word is to be shifted right
     *
     * @return the result of shifting this word right the given number of times.
     */
    public Word rightShiftSigned(Word word) {
        Word shiftedWord = new Word(this);
        while (word.compareTo(ZERO_WORD) > 0 && !shiftedWord.equals(ZERO_WORD) && !shiftedWord.equals(MAX_WORD)) {
            word = word.decrementedWord();
            shiftedWord = shiftedWord.rightShiftSigned();
        }
        return shiftedWord;
    }

    /**
     * computes the word formed by taking the maximum of each digit of this word
     * with each digit of the given word.
     *
     * @param word the word with which the digitwise maximum of this word is to
     * be
     * computed
     *
     * @return the digitwise maximum of this word with the given word.
     */
    public Word digitwiseMax(Word word) {
        Word returnWord = ZERO_WORD;
        for (int i = 0; i < NUM_DIGITS; i++) {
            returnWord = returnWord.plus(maskExceptDigit(i).unsignedMax(maskExceptDigit(i)));
        }
        return returnWord;
    }

    /**
     * computes the word formed by taking the minimum of each digit of this word
     * with each digit of the given word.
     *
     * @param word the word with which the digitwise minimum of this word is to
     * be
     * computed
     *
     * @return the digitwise minimum of this word with the given word.
     */
    public Word digitwiseMin(Word word) {
        Word returnWord = ZERO_WORD;
        for (int i = 0; i < NUM_DIGITS; i++) {
            returnWord = returnWord.plus(maskExceptDigit(i).unsignedMin(maskExceptDigit(i)));
        }
        return returnWord;
    }

    private Word maskExceptDigit(int i) {
        Word digitWord = new Word(i);
        return rightShiftUnsigned(digitWord).maskExceptZerothDigit().leftShift(digitWord);
    }

    private Word maskExceptZerothDigit() {
        return new Word(getUnsignedValue() % BASE);
    }

    /**
     * Computes the maximum of this word and the given word (as unsigned words).
     *
     * @param word the word whose maximum with this word is to be taken
     *
     * @return the larger of this word and the given word (as unsigned words)
     */
    public Word unsignedMax(Word word) {
        return compareTo(word) > -1 ? this : word;
    }

    /**
     * Computes the minimum of this word and the given word (as unsigned words).
     *
     * @param word the word whose minimum with this word is to be taken
     *
     * @return the smaller of this word and the given word (as unsigned words)
     */
    public Word unsignedMin(Word word) {
        return compareTo(word) > -1 ? word : this;
    }

    //</editor-fold>
    /**
     * Determines if this word is less than zero as a signed word.
     *
     * @return the result of a comparison between the value of the signed word
     * and zero.
     */
    public boolean isNegative() {
        return compareToSigned(ZERO_WORD) == -1;
    }

    /**
     * returns the unsigned value of this word.
     *
     * @return an int equal to the unsigned value of this word.
     */
    public int getUnsignedValue() {
        return value;
    }

    /**
     * returns the signed value of this word
     *
     * @return the signed number represented by this word.
     */
    public int getSignedValue() {
        if (compareTo(MAX_SIGNED) <= 0) {
            return value;
        } else {
            return value - NUM_WORDS;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="compare equals hashcode">
    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Word other = (Word) obj;
        return this.value == other.value;
    }

    /**
     * compares the unsigned values of two words
     *
     * @param word the word to which this word is compared
     *
     * @return -1 if the unsigned value this word is smaller than that of the
     * given word, 0 if they are equal, or 1 if this word is larger
     */
    @Override
    public int compareTo(Word word) {
        return Integer.compare(value, word.value);
    }

    /**
     * compares the unsigned values of two words
     *
     * @param word the word to which this word is compared
     *
     * @return -1 if the unsigned value this word is smaller than that of the
     * given word, 0 if they are equal, or 1 if this word is larger
     */
    public int compareToUnsigned(Word word) {
        return compareTo(word);
    }

    /**
     * compares the signed values of two words
     *
     * @param word the word to which this word is compared
     *
     * @return -1 if the signed value this word is smaller than that of the
     * given word, 0 if they are equal, or 1 if this word is larger
     */
    public int compareToSigned(Word word) {
        return Integer.compare(getSignedValue(), word.getSignedValue());
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="toStrings">
    /**
     * returns a string representation of the word. Specifically it is a string
     * representing the unsigned value of this word.
     *
     * @return a string representation of this word
     */
    @Override
    public String toString() {
        return toStringUnsigned();
    }

    /**
     * returns a string representing the unsigned value of this word.
     *
     * @return a string representing the unsigned value of this word.
     */
    public String toStringUnsigned() {
        return getDoubleDigitString(getUnsignedValue());
    }

    /**
     * returns a string representing the signed value of this word.
     *
     * @return a string representing the signed value of this word.
     */
    public String toStringSigned() {
        return getDoubleDigitString(getSignedValue());
    }
    //</editor-fold>

}
