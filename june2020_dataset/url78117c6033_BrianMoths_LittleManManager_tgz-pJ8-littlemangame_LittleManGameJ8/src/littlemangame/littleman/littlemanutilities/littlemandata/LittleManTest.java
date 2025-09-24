/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.littleman.littlemanutilities.littlemandata;

import littlemangame.littleman.littlemanutilities.location.OfficeLocation;
import littlemangame.word.Word;

/**
 * A little man test is an object used to test the state of the little man.
 * Specifically it tests the LittleManData of the little man (so either the
 * state of the office or of the little man's memory.
 *
 * @author brian
 */
public enum LittleManTest {

    /**
     * Tests the if the word written on the worksheet is zero.
     */
    ZERO(OfficeLocation.WORKSHEET, new LittleManRegisterTester() {

        @Override
        boolean testWord(Word word) {
            return word.equals(Word.ZERO_WORD);
        }

    }),
    /**
     * Tests the if the word written on the worksheet is not zero.
     */
    NOT_ZERO(OfficeLocation.WORKSHEET, new LittleManRegisterTester() {

        @Override
        boolean testWord(Word word) {
            return !word.equals(Word.ZERO_WORD);
        }

    }),
    /**
     * Tests the if the word written on the worksheet has a signed value greater
     * than zero.
     */
    GREATER_THAN_ZERO(OfficeLocation.WORKSHEET, new LittleManRegisterTester() {

        @Override
        boolean testWord(Word word) {
            return word.compareToSigned(Word.ZERO_WORD) > 0;
        }

    }),
    /**
     * Tests the if the word written on the worksheet has a signed value
     * greater
     * than or equal to zero
     */
    GREATER_OR_EQUAL_TO_ZERO(OfficeLocation.WORKSHEET, new LittleManRegisterTester() {

        @Override
        boolean testWord(Word word) {
            return word.compareToSigned(Word.ZERO_WORD) >= 0;
        }

    }),
    /**
     * Tests the if the word written on the worksheet has a signed value less
     * than zero
     *
     */
    LESS_THAN_ZERO(OfficeLocation.WORKSHEET, new LittleManRegisterTester() {

        @Override
        boolean testWord(Word word) {
            return word.compareToSigned(Word.ZERO_WORD) < 0;
        }

    }),
    /**
     * Tests the if the word written on the worksheet has a signed value less
     * than or equal to zero
     */
    LESS_OR_EQUAL_ZERO(OfficeLocation.WORKSHEET, new LittleManRegisterTester() {

        @Override
        boolean testWord(Word word) {
            return word.compareToSigned(Word.ZERO_WORD) <= 0;
        }

    });

    private final OfficeLocation computerLocation;
    private final LittleManTester littleManTester;

    private LittleManTest(OfficeLocation computerLocation, LittleManTester littleManTester) {
        this.computerLocation = computerLocation;
        this.littleManTester = littleManTester;
    }

    /**
     * returns the location where this test must occur.
     *
     * @return the location where this test must occur.
     */
    public OfficeLocation getComputerLocation() {
        return computerLocation;
    }

    boolean test(GenericLittleManData<?> littleManData) {
        return littleManTester.test(littleManData);
    }

    private static interface LittleManTester {

        boolean test(GenericLittleManData<?> littleManData);

    }

    private static abstract class LittleManRegisterTester implements LittleManTester {

        Word getRegisterWord(GenericLittleManData<?> littleManData) {
            return littleManData.getContainerWord(LittleManWordContainer.WORKSHEET);
        }

        abstract boolean testWord(Word word);

        @Override
        public boolean test(GenericLittleManData<?> littleManData) {
            return testWord(getRegisterWord(littleManData));
        }

    }

}
