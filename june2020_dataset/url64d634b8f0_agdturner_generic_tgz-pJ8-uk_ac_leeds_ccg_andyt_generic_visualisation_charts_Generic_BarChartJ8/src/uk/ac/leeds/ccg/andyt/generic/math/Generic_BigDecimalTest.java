/**
 * Copyright 2010 Andy Turner, The University of Leeds, UK
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package uk.ac.leeds.ccg.andyt.generic.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Generic_BigDecimalTest {

    public Generic_BigDecimalTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAll() {
        testRoundToAndSetDecimalPlaces_3args(); // Test passes
        testMultiply(); // Test passes
        testDivide(); // Test passes
        testReciprocal(); // Test passes
        testReciprocalWillBeInteger(); // Test passes
        testGetEulerConstant(); // Test passes
        testMax(); // Test passes
        testMin(); // Test passes
        testPositionSignificantDigit(); // Test passes
        testFloorSignificantDigit(); // Test passes
        testCeilingSignificantDigit(); // Test passes
        testRandomUniformTest(); // Test passes
        testExp(); // Test passes
        testLn(); // Test passes
        testLog(); // Test passes
        test_getRandom(); // Test passes
        testSqrt();  // Test passes
        testRoot(); //@TODO Not all tests complete in a reasonable time frame so some have been commented out for the time being.
        testPower(); //@TODO Not all tests completing in a satisfactory time. Some may be getting stuck in infinite loops.
        testCos(); // Test passes
        testSin(); // Test passes
        testTan(); // Test passes
    }

    public void testPowerUnscaled1Precision1_2args() {
        System.out.println("testPowerUnscaled1Precision1_2args");
        testPowerUnscaled1Precision1_2args_test1();
        testPowerUnscaled1Precision1_2args_test2();
        testPowerUnscaled1Precision1_2args_test3();
        testPowerUnscaled1Precision1_2args_test4();
        testPowerUnscaled1Precision1_2args_test5();
    }

    public void testPowerUnscaled1Precision1_2args_test1() {
        System.out.println("testPowerUnscaled1Precision1_2args_test1");
        BigDecimal x = new BigDecimal("10");
        int y = 2;
        BigDecimal expResult = null;
        BigDecimal result = null;
        expResult = new BigDecimal("100");
        result = Generic_BigDecimal.powerUnscaled1Precision1(x, y);
        assertEquals(expResult, result);
    }

    public void testPowerUnscaled1Precision1_2args_test2() {
        System.out.println("testPowerUnscaled1Precision1_2args_test2");
        BigDecimal x = new BigDecimal("0.1");
        int y = 2;
        BigDecimal expResult = null;
        BigDecimal result = null;
        expResult = new BigDecimal("0.01");
        result = Generic_BigDecimal.powerUnscaled1Precision1(x, y);
        assertEquals(expResult, result);
    }

    public void testPowerUnscaled1Precision1_2args_test3() {
        System.out.println("testPowerUnscaled1Precision1_2args_test3");
        BigDecimal x = new BigDecimal("100");
        int y = 3;
        BigDecimal expResult = null;
        BigDecimal result = null;
        expResult = new BigDecimal("1000000");
        result = Generic_BigDecimal.powerUnscaled1Precision1(x, y);
        assertEquals(expResult, result);
    }

    public void testPowerUnscaled1Precision1_2args_test4() {
        System.out.println("testPowerUnscaled1Precision1_2args_test4");
        BigDecimal x = new BigDecimal("0.01");
        int y = 3;
        BigDecimal expResult = null;
        BigDecimal result = null;
        expResult = new BigDecimal("0.000001");
        result = Generic_BigDecimal.powerUnscaled1Precision1(x, y);
        assertEquals(expResult, result);
    }

    public void testPowerUnscaled1Precision1_2args_test5() {
        System.out.println("testPowerUnscaled1Precision1_2args_test5");
        BigDecimal x = new BigDecimal("0.0001");
        int y = 20;
        BigDecimal expResult = null;
        BigDecimal result = null;
        expResult = new BigDecimal("1e-80");
        result = Generic_BigDecimal.powerUnscaled1Precision1(x, y);
        assertEquals(expResult, result);
    }

    public void testMultiply() {
        testMultiplyRoundIfNecessary_5args_1();
        testMultiplyRoundIfNecessary_4args_1();
        testMultiplyRoundIfNecessary_4args_2();
    }

    public void testMultiplyRoundIfNecessary_5args_1() {
        System.out.println("testMultiplyRoundIfNecessary_5args_1");
        testMultiplyRoundIfNecessary_5args_1_test1();
        testMultiplyRoundIfNecessary_5args_1_test2();
    }

    public void testMultiplyRoundIfNecessary_5args_1_test1() {
        System.out.println("testMultiplyRoundIfNecessary_5args_1_test1");
        BigDecimal x = null;
        BigDecimal y = null;
        MathContext a_MathContext = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("1.1");
        y = new BigDecimal("1.1");
        a_RoundingMode = RoundingMode.HALF_UP;
        a_MathContext = new MathContext(3, a_RoundingMode);
        decimalPlaces = 2;
        expResult = new BigDecimal("1.21");
        result = Generic_BigDecimal.multiplyRoundIfNecessary(
                x,
                y,
                a_MathContext,
                decimalPlaces,
                a_RoundingMode);
        printTestMultiply_4args_1(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testMultiplyRoundIfNecessary_5args_1_test2() {
        System.out.println("testMultiplyRoundIfNecessary_5args_1_test2");
        BigDecimal x = null;
        BigDecimal y = null;
        MathContext a_MathContext = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("1.123456789");
        y = new BigDecimal("1234.1234");
        a_RoundingMode = RoundingMode.HALF_UP;
        a_MathContext = new MathContext(14, a_RoundingMode);
        decimalPlaces = 10;
        expResult = new BigDecimal("1386.4843121938");
        result = Generic_BigDecimal.multiplyRoundIfNecessary(
                x,
                y,
                a_MathContext,
                decimalPlaces,
                a_RoundingMode);
        printTestMultiply_4args_1(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testMultiplyRoundIfNecessary_4args_1() {
        System.out.println("testMultiplyRoundIfNecessary_4args_1");
        testMultiplyRoundIfNecessary_4args_1_test1();
        testMultiplyRoundIfNecessary_4args_1_test2();
        testMultiplyRoundIfNecessary_4args_1_test3();
        testMultiplyRoundIfNecessary_4args_1_test4();
        testMultiplyRoundIfNecessary_4args_1_test5();
    }

    public void testMultiplyRoundIfNecessary_4args_1_test1() {
        System.out.println("testMultiplyRoundIfNecessary_4args_1_test1");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("10.123456789");
        y = new BigDecimal("1");
        decimalPlaces = 10;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("10.123456789");
        result = Generic_BigDecimal.multiplyRoundIfNecessary(
                x, y, decimalPlaces, a_RoundingMode);
        printTestMultiply_4args_1(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testMultiplyRoundIfNecessary_4args_1_test2() {
        System.out.println("testMultiplyRoundIfNecessary_4args_1_test2");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("10.123456789");
        y = new BigDecimal("1.0000001");
        decimalPlaces = 10;
        a_RoundingMode = RoundingMode.HALF_UP;
        // The exact answer if precision were unlimited is 10.1234578013456789
        expResult = new BigDecimal("10.1234578013");
        result = Generic_BigDecimal.multiplyRoundIfNecessary(
                x, y, decimalPlaces, a_RoundingMode);
        printTestMultiply_4args_1(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testMultiplyRoundIfNecessary_4args_1_test3() {
        System.out.println("testMultiplyRoundIfNecessary_4args_1_test3");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal(
                "104364183462747328754328957134895713471589717049613406.1234567"
                + "8910111213141516171819919676766754");
        y = new BigDecimal(
                "14632748325142541.00005054133454515451454515144545135451545415"
                + "457485917594001");
        decimalPlaces = 1000;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "15271348307693848498083695561135354493017650234872663109391721"
                + "32466014.413624453917232477798220907377131857747836727698261"
                + "8524788288629667703506739052902096156722546642754");
        result = Generic_BigDecimal.multiplyRoundIfNecessary(
                x, y, decimalPlaces, a_RoundingMode);
        printTestMultiply_4args_1(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testMultiplyRoundIfNecessary_4args_1_test4() {
        System.out.println("testMultiplyRoundIfNecessary_4args_1_test4");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal(
                "-104364183462747328754328957134895713471589717049613406.123456"
                + "78910111213141516171819919676766754");
        y = new BigDecimal(
                "-14632748325142541.0000505413345451545145451514454513545154541"
                + "5457485917594001");
        decimalPlaces = 1000;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("1527134830769384849808369556113535449301765"
                + "023487266310939172132466014.41362445391723247779822090737713"
                + "185774783672769826185247882886296677035067390529020961567225"
                + "46642754");
        result = Generic_BigDecimal.multiplyRoundIfNecessary(
                x, y, decimalPlaces, a_RoundingMode);
        printTestMultiply_4args_1(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testMultiplyRoundIfNecessary_4args_1_test5() {
        System.out.println("testMultiplyRoundIfNecessary_4args_1_test5");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal(
                "-104364183462747328754328957134895713471589717049613406.123456"
                + "78910111213141516171819919676766754");
        y = new BigDecimal("14632748325142541.000050541334545154514545151445451"
                + "35451545415457485917594001");
        decimalPlaces = 1000;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "-1527134830769384849808369556113535449301765023487266310939172"
                + "132466014.41362445391723247779822090737713185774783672769826"
                + "18524788288629667703506739052902096156722546642754");
        result = Generic_BigDecimal.multiplyRoundIfNecessary(
                x, y, decimalPlaces, a_RoundingMode);
        printTestMultiply_4args_1(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testMultiply_4args_1()
     *
     * @param x
     * @param y
     * @param decimalPlaces
     * @param a_RoundingMode
     * @param result
     */
    private void printTestMultiply_4args_1(
            BigDecimal x,
            BigDecimal y,
            int decimalPlaces,
            RoundingMode a_RoundingMode,
            BigDecimal result) {
        System.out.println("x " + x);
        System.out.println("y " + y);
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("a_RoundingMode " + a_RoundingMode);
        System.out.println("result " + result);
    }

    /**
     * Test of multiply method, of class Generic_BigDecimal.
     */
    public void testMultiplyRoundIfNecessary_4args_2() {
        System.out.println("testMultiplyRoundIfNecessary_4args_2");
        testMultiplyRoundIfNecessary_4args_2_test1();
        testMultiplyRoundIfNecessary_4args_2_test2();
        testMultiplyRoundIfNecessary_4args_2_test3();
        testMultiplyRoundIfNecessary_4args_2_test4();
    }

    public void testMultiplyRoundIfNecessary_4args_2_test1() {
        System.out.println("testMultiplyRoundIfNecessary_4args_2_test1");
        BigDecimal x = null;
        BigInteger y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal(
                "1.005");
        y = new BigInteger(
                "2");
        decimalPlaces = 3;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "2.010");
        result = Generic_BigDecimal.multiplyRoundIfNecessary(
                x, y, decimalPlaces, a_RoundingMode);
        printTestMultiply_4args_2(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testMultiplyRoundIfNecessary_4args_2_test2() {
        System.out.println("testMultiplyRoundIfNecessary_4args_2_test2");
        BigDecimal x = null;
        BigInteger y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal(
                "1.006");
        y = new BigInteger(
                "3");
        decimalPlaces = 3;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "3.018");
        result = Generic_BigDecimal.multiplyRoundIfNecessary(
                x, y, decimalPlaces, a_RoundingMode);
        printTestMultiply_4args_2(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testMultiplyRoundIfNecessary_4args_2_test3() {
        System.out.println("testMultiplyRoundIfNecessary_4args_2_test3");
        BigDecimal x = null;
        BigInteger y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal(
                "1.006");
        y = new BigInteger(
                "3");
        decimalPlaces = 2;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "3.02");
        result = Generic_BigDecimal.multiplyRoundIfNecessary(
                x, y, decimalPlaces, a_RoundingMode);
        printTestMultiply_4args_2(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testMultiplyRoundIfNecessary_4args_2_test4() {
        System.out.println("testMultiplyRoundIfNecessary_4args_2_test4");
        BigDecimal x = null;
        BigInteger y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal(
                "1.123456789123456");
        y = new BigInteger(
                "12356789");
        decimalPlaces = 2;
        a_RoundingMode = RoundingMode.HALF_UP;
        // Accurate result 13882318.493816040742784
        expResult = new BigDecimal(
                "13882318.49");
        result = Generic_BigDecimal.multiplyRoundIfNecessary(
                x, y, decimalPlaces, a_RoundingMode);
        printTestMultiply_4args_2(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testMultiplyRoundIfNecessary_4args_2_test10() {
        System.out.println("testMultiplyRoundIfNecessary_4args_2_test10");
        BigDecimal x = null;
        BigInteger y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal(
                "1234567891011121314151617181991967676675410.123456789");
        y = new BigInteger(
                "14632748325142541437589475834957438967342068989089035451545415"
                + "457485917594001");
        decimalPlaces = 1000;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "18065121239467745048010407669300110379939339155567645320133264"
                + "901389499811971334588569293633668157618732327166017614283.96"
                + "9122789");
        result = Generic_BigDecimal.multiplyRoundIfNecessary(
                x, y, decimalPlaces, a_RoundingMode);
        printTestMultiply_4args_2(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testMultiply_4args_2()
     *
     * @param x
     * @param y
     * @param decimalPlaces
     * @param a_RoundingMode
     * @param result
     */
    private void printTestMultiply_4args_2(
            BigDecimal x,
            BigInteger y,
            int decimalPlaces,
            RoundingMode a_RoundingMode,
            BigDecimal result) {
        System.out.println("x " + x);
        System.out.println("y " + y);
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("a_RoundingMode " + a_RoundingMode);
        System.out.println("result " + result);
    }

    public void testDivide() {
        testDivideRoundIfNecessary_4args_1();
        testDivideRoundIfNecessary_4args_2();
        testDivideRoundIfNecessary_4args_3();
        testDivideRoundIfNecessary_4args_4();
    }

    public void testDivideRoundIfNecessary_4args_1() {
        System.out.println("testDivideRoundIfNecessary_4args_1");
        testDivideRoundIfNecessary_4args_1_test1();
        testDivideRoundIfNecessary_4args_1_test2();
        testDivideRoundIfNecessary_4args_1_test3();
        testDivideRoundIfNecessary_4args_1_test4();
        testDivideRoundIfNecessary_4args_1_test5();
        testDivideRoundIfNecessary_4args_1_test6();
        testDivideRoundIfNecessary_4args_1_test7();
    }

    public void testDivideRoundIfNecessary_4args_1_test1() {
        System.out.println("testDivideRoundIfNecessary_4args_1_test1");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("30.121");
        y = new BigDecimal("0.0121");
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 6;
        expResult = new BigDecimal("2489.338843");
        result = Generic_BigDecimal.divideRoundIfNecessary(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printTestDivideRoundIfNecessary_4args_1(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testDivideRoundIfNecessary_4args_1_test2() {
        System.out.println("testDivideRoundIfNecessary_4args_1_test2");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("30.121");
        y = new BigDecimal("0.0121");
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 28;
        expResult = new BigDecimal("2489.3388429752066115702479338843");
        result = Generic_BigDecimal.divideRoundIfNecessary(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printTestDivideRoundIfNecessary_4args_1(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testDivideRoundIfNecessary_4args_1_test3() {
        System.out.println("testDivideRoundIfNecessary_4args_1_test3");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.030121");
        y = new BigDecimal("0.0000121");
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 4;
        expResult = new BigDecimal("2489.3388");
        result = Generic_BigDecimal.divideRoundIfNecessary(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printTestDivideRoundIfNecessary_4args_1(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testDivideRoundIfNecessary_4args_1_test4() {
        System.out.println("testDivideRoundIfNecessary_4args_1_test4");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.030121");
        y = new BigDecimal("0.0000121");
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 6;
        expResult = new BigDecimal("2489.338843");
        result = Generic_BigDecimal.divideRoundIfNecessary(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printTestDivideRoundIfNecessary_4args_1(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testDivideRoundIfNecessary_4args_1_test5() {
        System.out.println("testDivideRoundIfNecessary_4args_1_test5");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("30.121");
        y = new BigDecimal("30.1215415431245365365754725456435315432513245135");
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 6;
        expResult = new BigDecimal("0.999982");
        result = Generic_BigDecimal.divideRoundIfNecessary(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printTestDivideRoundIfNecessary_4args_1(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testDivideRoundIfNecessary_4args_1_test6() {
        System.out.println("testDivideRoundIfNecessary_4args_1_test6");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("30.121");
        y = new BigDecimal("30.1215415431245365365754725456435315432513245135");
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 30;
        expResult = new BigDecimal("0.999982021400738696662826767313");
        result = Generic_BigDecimal.divideRoundIfNecessary(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printTestDivideRoundIfNecessary_4args_1(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testDivideRoundIfNecessary_4args_1_test7() {
        System.out.println("testDivideRoundIfNecessary_4args_1_test7");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("30.121");
        y = new BigDecimal("30.1215415431245365365754725456435315432513245135");
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 25;
        expResult = new BigDecimal("0.9999820214007386966628268");
        result = Generic_BigDecimal.divideRoundIfNecessary(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printTestDivideRoundIfNecessary_4args_1(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testDivide_4args_1()
     *
     * @param x
     * @param y
     * @param decimalPlaces
     * @param a_RoundingMode
     * @param result
     */
    private void printTestDivideRoundIfNecessary_4args_1(
            BigDecimal x,
            BigDecimal y,
            int decimalPlaces,
            RoundingMode a_RoundingMode,
            BigDecimal result) {
        System.out.println("x " + x);
        System.out.println("y " + y);
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("a_RoundingMode " + a_RoundingMode);
        System.out.println("result " + result);
    }

    /**
     * Test of divide method, of class Generic_BigDecimal.
     */
    public void testDivideRoundIfNecessary_4args_2() {
        System.out.println("testDivideRoundIfNecessary_4args_2");
        testDivideRoundIfNecessary_4args_2_test1();
    }

    public void testDivideRoundIfNecessary_4args_2_test1() {
        System.out.println("testDivideRoundIfNecessary_4args_2_test1");
        BigDecimal x = null;
        BigInteger y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("30.121");
        y = new BigInteger("1234567777777777543567543564353642432656543254626");
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 136;
        expResult = new BigDecimal(
                "0.000000000000000000000000000000000000000000000024398012439801"
                + "248608678869240874632067769163126799290634383673965707848596"
                + "2697307032484628");
        result = Generic_BigDecimal.divideRoundIfNecessary(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printTestDivideRoundIfNecessary_4args_2(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testDivide_4args_2()
     *
     * @param x
     * @param y
     * @param decimalPlaces
     * @param a_RoundingMode
     * @param result
     */
    private void printTestDivideRoundIfNecessary_4args_2(
            BigDecimal x,
            BigInteger y,
            int decimalPlaces,
            RoundingMode a_RoundingMode,
            BigDecimal result) {
        System.out.println("x " + x);
        System.out.println("y " + y);
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("a_RoundingMode " + a_RoundingMode);
        System.out.println("result " + result);
    }

    /**
     * Test of divide method, of class Generic_BigDecimal.
     */
    public void testDivideRoundIfNecessary_4args_3() {
        System.out.println("testDivide_4args_3");
        testDivideRoundIfNecessary_4args_3_test1();
    }

    public void testDivideRoundIfNecessary_4args_3_test1() {
        System.out.println("testDivideRoundIfNecessary_4args_3_test1");
        BigInteger x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigInteger("10030");
        y = new BigDecimal("0.0121");
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 100;
        expResult = new BigDecimal(
                "828925.6198347107438016528925619834710743801652892561983471074"
                + "380165289256198347107438016528925619834710744");
        result = Generic_BigDecimal.divideRoundIfNecessary(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printTestDivideRoundIfNecessary_4args_3(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testDivide_4args_3()
     *
     * @param x
     * @param y
     * @param decimalPlaces
     * @param a_RoundingMode
     * @param result
     */
    private void printTestDivideRoundIfNecessary_4args_3(
            BigInteger x,
            BigDecimal y,
            int decimalPlaces,
            RoundingMode a_RoundingMode,
            BigDecimal result) {
        System.out.println("x " + x);
        System.out.println("y " + y);
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("a_RoundingMode " + a_RoundingMode);
        System.out.println("result " + result);
    }

    public void testDivideRoundIfNecessary_4args_4() {
        System.out.println("testDivideRoundIfNecessary_4args_4");
        testDivideRoundIfNecessary_4args_4_test1();
    }

    public void testDivideRoundIfNecessary_4args_4_test1() {
        System.out.println("testDivideRoundIfNecessary_4args_4_test1");
        BigInteger x = null;
        BigInteger y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigInteger("10030");
        y = new BigInteger("23456789");
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 100;
        expResult = new BigDecimal(
                "0.000427594757321643640141879606795286430721613260877266705174"
                + "3527215084724511952595046150604841950021");
        result = Generic_BigDecimal.divideRoundIfNecessary(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printTestDivideRoundIfNecessary_4args_4(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testDivide_4args_4()
     *
     * @param x
     * @param y
     * @param decimalPlaces
     * @param a_RoundingMode
     * @param result
     */
    private void printTestDivideRoundIfNecessary_4args_4(
            BigInteger x,
            BigInteger y,
            int decimalPlaces,
            RoundingMode a_RoundingMode,
            BigDecimal result) {
        System.out.println("x " + x);
        System.out.println("y " + y);
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("a_RoundingMode " + a_RoundingMode);
        System.out.println("result " + result);
    }

    /**
     * Test of powerTestAbove method, of class Generic_BigDecimal.
     */
    public void testPowerTestAbove_6args_1() {
        System.out.println("testPowerTestAbove_6args_1");
        testPowerTestAbove_6args_1_test1();
    }

    public void testPowerTestAbove_6args_1_test1() {
        System.out.println("testPowerTestAbove_6args_1_test1");
        BigDecimal compare = null;
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        boolean expResult = false;
        boolean result;
        compare = new BigDecimal("100");
        x = new BigDecimal("10.00000000000000000000000000001");
        y = new BigInteger("10");
        decimalPlaces = 19;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = true;
        result = Generic_BigDecimal.powerTestAbove(
                compare, x, y, div, decimalPlaces, a_RoundingMode);
        printPowerTestAbove_6args_1(
                compare,
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testPowerTestAbove_6args_1()
     *
     * @param compare
     * @param x
     * @param y
     * @param div
     * @param decimalPlaces
     * @param a_RoundingMode
     * @param result
     */
    private void printPowerTestAbove_6args_1(
            BigDecimal compare,
            BigDecimal x,
            BigInteger y,
            int div,
            int decimalPlaces,
            RoundingMode a_RoundingMode,
            boolean result) {
        System.out.println("compare " + compare);
        System.out.println("x " + x);
        System.out.println("y " + y);
        System.out.println("div " + div);
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("a_RoundingMode " + a_RoundingMode);
        System.out.println("result " + result);
    }

    /**
     * Test of powerTestBelow method, of class Generic_BigDecimal.
     */
    public void testPowerTestBelow_6args_1() {
        System.out.println("testPowerTestBelow_6args_1");
        testPowerTestBelow_6args_1_test1();
        testPowerTestBelow_6args_1_test2();
    }

    public void testPowerTestBelow_6args_1_test1() {
        System.out.println("testPowerTestBelow_6args_1_test1");
        BigDecimal compare = null;
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        boolean expResult;
        boolean result;
        compare = new BigDecimal("100");
        x = new BigDecimal("9.99999999999999999999999999999999999999999999999");
        y = new BigInteger("2");
        div = 64;
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = true;
        result = Generic_BigDecimal.powerTestBelow(
                compare, x, y, div, decimalPlaces, a_RoundingMode);
        printPowerTestBelow_6args_1(
                compare,
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPowerTestBelow_6args_1_test2() {
        System.out.println("testPowerTestBelow_6args_1_test2");
        BigDecimal compare = null;
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        boolean expResult;
        boolean result;
        compare = new BigDecimal("100");
        x = new BigDecimal("10.0000000000000000000000000000000000000000000001");
        y = new BigInteger("2");
        div = 64;
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = false;
        result = Generic_BigDecimal.powerTestBelow(
                compare, x, y, div, decimalPlaces, a_RoundingMode);
        printPowerTestBelow_6args_1(
                compare,
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testPowerTestBelow_6args_1()
     *
     * @param compare
     * @param x
     * @param y
     * @param div
     * @param decimalPlaces
     * @param a_RoundingMode
     * @param result
     */
    private void printPowerTestBelow_6args_1(
            BigDecimal compare,
            BigDecimal x,
            BigInteger y,
            int div,
            int decimalPlaces,
            RoundingMode a_RoundingMode,
            boolean result) {
        System.out.println("compare " + compare);
        System.out.println("x " + x);
        System.out.println("y " + y);
        System.out.println("div " + div);
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("a_RoundingMode " + a_RoundingMode);
        System.out.println("result " + result);
    }

    /**
     * Test of powerTestAbove method, of class Generic_BigDecimal.
     */
    public void testPowerTestAbove_6args_2() {
        System.out.println("testPowerTestAbove_6args_2");
        testPowerTestAbove_6args_2_test1();
        testPowerTestAbove_6args_2_test2();
    }

    public void testPowerTestAbove_6args_2_test1() {
        System.out.println("testPowerTestAbove_6args_2_test1");
        BigDecimal compare = null;
        BigDecimal x = null;
        int y = 0;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        boolean expResult;
        boolean result;
        compare = new BigDecimal("100");
        x = new BigDecimal("9.99999999999999999999999999999999999999999999999");
        y = 2;
        div = 64;
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = false;
        result = Generic_BigDecimal.powerTestAbove(
                compare, x, y, div, decimalPlaces, a_RoundingMode);
        assertEquals(expResult, result);
        printPowerTestAbove_6args_2(
                compare,
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
    }

    public void testPowerTestAbove_6args_2_test2() {
        System.out.println("testPowerTestAbove_6args_2_test2");
        BigDecimal compare = null;
        BigDecimal x = null;
        int y = 0;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        boolean expResult;
        boolean result;
        compare = new BigDecimal("100");
        x = new BigDecimal("10.0000000000000000000000000000000000000000000001");
        y = 2;
        div = 64;
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = true;
        result = Generic_BigDecimal.powerTestAbove(
                compare, x, y, div, decimalPlaces, a_RoundingMode);
        printPowerTestAbove_6args_2(
                compare,
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testPowerTestAbove_6args_2()
     *
     * @param compare
     * @param x
     * @param y
     * @param div
     * @param decimalPlaces
     * @param a_RoundingMode
     * @param result
     */
    private void printPowerTestAbove_6args_2(
            BigDecimal compare,
            BigDecimal x,
            int y,
            int div,
            int decimalPlaces,
            RoundingMode a_RoundingMode,
            boolean result) {
        System.out.println("compare " + compare);
        System.out.println("x " + x);
        System.out.println("y " + y);
        System.out.println("div " + div);
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("a_RoundingMode " + a_RoundingMode);
        System.out.println("result " + result);
    }

    /**
     * Test of powerTestBelow method, of class Generic_BigDecimal.
     */
    public void testPowerTestBelow_6args_2() {
        System.out.println("testPowerTestBelow_6args_2");
        testPowerTestBelow_6args_2_test1();
        testPowerTestBelow_6args_2_test2();
    }

    public void testPowerTestBelow_6args_2_test1() {
        System.out.println("testPowerTestBelow_6args_2_test1");
        BigDecimal compare = null;
        BigDecimal x = null;
        int y = 0;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        boolean expResult;
        boolean result;
        compare = new BigDecimal("100");
        x = new BigDecimal("9.99999999999999999999999999999999999999999999999");
        y = 2;
        div = 64;
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = true;
        result = Generic_BigDecimal.powerTestBelow(
                compare, x, y, div, decimalPlaces, a_RoundingMode);
        printPowerTestBelow_6args_2(
                compare,
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPowerTestBelow_6args_2_test2() {
        System.out.println("testPowerTestBelow_6args_2_test2");
        BigDecimal compare = null;
        BigDecimal x = null;
        int y = 0;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        boolean expResult;
        boolean result;
        compare = new BigDecimal("100");
        x = new BigDecimal("10.0000000000000000000000000000000000000000000001");
        y = 2;
        div = 64;
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = false;
        result = Generic_BigDecimal.powerTestBelow(
                compare, x, y, div, decimalPlaces, a_RoundingMode);
        printPowerTestBelow_6args_2(
                compare,
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testPowerTestBelow_6args_2()
     *
     * @param compare
     * @param x
     * @param y
     * @param div
     * @param decimalPlaces
     * @param a_RoundingMode
     * @param result
     */
    private void printPowerTestBelow_6args_2(
            BigDecimal compare,
            BigDecimal x,
            int y,
            int div,
            int decimalPlaces,
            RoundingMode a_RoundingMode,
            boolean result) {
        System.out.println("compare " + compare);
        System.out.println("x " + x);
        System.out.println("y " + y);
        System.out.println("div " + div);
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("a_RoundingMode " + a_RoundingMode);
        System.out.println("result " + result);
    }

    public void testPower() {
        testPowerNoRounding_3args(); // Test passes
        testPowerUnscaled1Precision1_2args(); // Test passes
        testPowerTestAbove_6args_1(); // Test passes
        testPowerTestBelow_6args_1(); // Test passes
        testPowerTestAbove_6args_2(); // Test passes
        testPowerTestBelow_6args_2(); // Test passes
        testPower_5args_1(); // Test passes
        testPower_5args_2(); // Test passes
        testPowerNoRounding_2args(); // Test passes
        testPower_6args(); // @TODO Not all tests completing in a satisfactory time. Some may be getting stuck.
        testPowerLessThanOne_4args(); // Test passes
    }

    public void testPowerNoRounding_3args() {
        System.out.println("testPowerNoRounding_3args()");
        testPowerNoRounding_3args_test1();
        testPowerNoRounding_3args_test2();
        testPowerNoRounding_3args_test3();
        testPowerNoRounding_3args_test4();
    }

    public void testPowerNoRounding_3args_test1() {
        System.out.println("testPowerNoRounding_3args_test1()");
        BigDecimal x = null;
        BigInteger y = null;
        int div;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.1");
        y = new BigInteger("20");
        div = 2;
        expResult = new BigDecimal("141710986707530.43575626125424226001");
        result = Generic_BigDecimal.powerNoRounding(
                x,
                y,
                div);
        printPowerNoRounding_3args(
                x,
                y,
                div,
                result);
        assertEquals(expResult, result);
    }

    public void testPowerNoRounding_3args_test2() {
        System.out.println("testPowerNoRounding_3args_test2()");
        BigDecimal x = null;
        BigInteger y = null;
        int div;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.10000000000000000000000000000000000000000000001");
        y = new BigInteger("20");
        div = 2;
        expResult = new BigDecimal(
                "141710986707530.4357562612542422600100000000000055572935963737"
                + "425786769119310690200000000000001035182140500991264655503202"
                + "846190000000000000012178613417658720760652978857014000000000"
                + "000000101488445147156006338774823808450000000000000000636790"
                + "244060586706439371443504000000000000000003121520804218562286"
                + "467507076000000000000000000012241258055759067790068655200000"
                + "000000000000000039004008510997029723257970000000000000000000"
                + "000101971264081037986204596000000000000000000000000219938020"
                + "566944676127560000000000000000000000000392046382472272149960"
                + "000000000000000000000000000576538797753341397000000000000000"
                + "000000000000000695672757470095200000000000000000000000000000"
                + "000682032115166760000000000000000000000000000000000534927149"
                + "150400000000000000000000000000000000000327773988450000000000"
                + "000000000000000000000000000151222140000000000000000000000000"
                + "000000000000000049419000000000000000000000000000000000000000"
                + "000010200000000000000000000000000000000000000000000001");
        result = Generic_BigDecimal.powerNoRounding(
                x,
                y,
                div);
        printPowerNoRounding_3args(
                x,
                y,
                div,
                result);
        assertEquals(expResult, result);
    }

    public void testPowerNoRounding_3args_test3() {
        System.out.println("testPowerNoRounding_3args_test3()");
        BigDecimal x = null;
        BigInteger y = null;
        int div;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.10000000000000000000000000000000000000000000001");
        y = new BigInteger("20");
        div = 4;
        expResult = new BigDecimal(
                "141710986707530.4357562612542422600100000000000055572935963737"
                + "425786769119310690200000000000001035182140500991264655503202"
                + "846190000000000000012178613417658720760652978857014000000000"
                + "000000101488445147156006338774823808450000000000000000636790"
                + "244060586706439371443504000000000000000003121520804218562286"
                + "467507076000000000000000000012241258055759067790068655200000"
                + "000000000000000039004008510997029723257970000000000000000000"
                + "000101971264081037986204596000000000000000000000000219938020"
                + "566944676127560000000000000000000000000392046382472272149960"
                + "000000000000000000000000000576538797753341397000000000000000"
                + "000000000000000695672757470095200000000000000000000000000000"
                + "000682032115166760000000000000000000000000000000000534927149"
                + "150400000000000000000000000000000000000327773988450000000000"
                + "000000000000000000000000000151222140000000000000000000000000"
                + "000000000000000049419000000000000000000000000000000000000000"
                + "000010200000000000000000000000000000000000000000000001");
        result = Generic_BigDecimal.powerNoRounding(
                x,
                y,
                div);
        printPowerNoRounding_3args(
                x,
                y,
                div,
                result);
        assertEquals(expResult, result);
    }

    public void testPowerNoRounding_3args_test4() {
        System.out.println("testPowerNoRounding_3args_test4()");
        BigDecimal x = null;
        BigInteger y = null;
        int div;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.10000000000000000000000000000000000000000000001");
        y = new BigInteger("200");
        div = 4;
        expResult = new BigDecimal(
                "32661431824467802229454073873432080851768274685145101404793774"
                + "576452998252512362581671667487048088656160662612395254996698"
                + "38852249217152696461.356901206565068735933481767168239913930"
                + "280323499656669613922968471032648138484549082976751149433925"
                + "750688556791475306905311908660060388833579486748894003125370"
                + "170045198537082600520758490362276327665108342115091975283768"
                + "184152685278624427232999470161745982272431522769026873464191"
                + "277044336165203154533720842603909488070802890388314902444582"
                + "682572923969512160999464798009520889416891681948951349176578"
                + "807265355963730373521315146367317991604961553810355716293381"
                + "253016526054040082578844980335745953798075748936975523133781"
                + "785381204061390192709205696583196145427000033625199001463461"
                + "552007509265668218628528184456849309333730137540867860942906"
                + "512918372446982316973041147062117618506875265082339285867786"
                + "377056803521592210075199563777335841908308241223251553712732"
                + "423504024673219809533123674093710308460601672325499755394141"
                + "765447564658590157637418775366272168987744817003658783192269"
                + "436740408933591810125181631527264100366551135208830844734340"
                + "052471548921979476253604807537617878708830823986376040966816"
                + "205531265081613902040442458022209228726532175757080502103671"
                + "260343175494974108896366363326169591146775486402549519978595"
                + "560695581430775704111970302440586374304684229293958752128140"
                + "388073651216766201744877883343369918698669366515144728430484"
                + "593109371560594787119646703761730963856783445030500561162899"
                + "400492768208872907438041247533887293898865408935284378132990"
                + "641890354621602288671087155904239746345001906108163179050849"
                + "493827588180978547881521397837520159835370682108572143363865"
                + "577486997125233899592941574463888892333696491052862914529406"
                + "391922118750167937062895729399852697624933511422839129707225"
                + "381832100528471756451279450235517844342084923735642465915508"
                + "417264011490757453457518620357012187822562887555854803164255"
                + "233137100810443172952775381229111764220063398378269526497245"
                + "024147852208621401392306266493534077142652212509301692553319"
                + "350887731639706841848517256392339244759841065616315227664478"
                + "427078579821858288017176588911219912297278459432183089082996"
                + "624150594612251357507088875170794168585596179905338817500027"
                + "206464472725839115152032431682180814927471032553381243895058"
                + "352147755624009443494300679928536505541369569146147045188769"
                + "100481674018094657840338226345020372404259774981139349656714"
                + "502625722161000694517021805853186592783832875096424968453470"
                + "591432063352425196021317637334818146165950047352700906519734"
                + "364802115757382590726495705549669730331936157290156579177121"
                + "785755513544129674673580151027552795172013761582766062013188"
                + "623602423815679997155329453730442059846871354053406643213109"
                + "224217162429414480745283016226322124267068021621768684384764"
                + "291990747940797903996533879729817358244044421620316390518869"
                + "835835873961304390292113878315868768474954371598413354008302"
                + "259607053824254635813713908025875259156585725724748661222646"
                + "849388307774842228001184848936165591491569573825465867285591"
                + "420686284704410722138747987721605575381347770874021242324164"
                + "948439640686460295820094367805094682159456941191636174328433"
                + "302523578806096575238297678957111155129567124045492652606204"
                + "205024696277747069423253051155720216484447535401501554946781"
                + "804419612070412558285140850278735772992058871002334886069754"
                + "528201647136730570100494509193225854076078795534073947021792"
                + "218566038443335348288941315444462946432980377260633670897733"
                + "416253184928441782357884920919685037093026261744086468127625"
                + "751490027032120428674049247743547767907916071245368256153102"
                + "369678672227515647583000186397385351466860489281314295436218"
                + "011242729929655782850105861245381149707044982802050292624863"
                + "077699493412206953007389208971168152930065107610637712942195"
                + "498262435777505861742785784369515634177701669119140703188938"
                + "476331039653456667288848403987031824207847643606764585858620"
                + "470928827009570960111166203211321016348347777636054916854198"
                + "001426811261510239579111778530402998542240650566877395959955"
                + "045064410420744465727808784837454400905986595099701222952496"
                + "070742946651678631843490169522868994938987826247425517826206"
                + "534898481790267633944274939440824052950395217386337266191181"
                + "836187769048716504484643945850974015308258094640705797336348"
                + "691293938420214497790186454279550272887856647454341610769460"
                + "302135900911310334307902401718328812302504014032528291240119"
                + "048667102323271926559210654533038201877449145303332506923572"
                + "465006904940685136619672459993012700413631915613397816712742"
                + "668290650788799570745419241231996871924380636198612941048429"
                + "121524146340643994095073130595831745687545421004479106516173"
                + "209296357367685099972119953855284958650351907223809713675361"
                + "124677852941085066273611068917208890592879305690237284534706"
                + "178061366841473098383640629483708224738119699652153994249182"
                + "747528259146223510785799284535412461197862235305354435361263"
                + "942478958586234920318350373813551750477416960893551652327937"
                + "514192583213736216202415742797126923936525590647802020776637"
                + "788487640242097299246192463491429908667851573393029261875310"
                + "770386756669766959675421407121608131793282076686286392061419"
                + "972601410093735268178631063328543664688385619490992305616578"
                + "450303094024585054518587680960986366781862669699145945465463"
                + "984120702579334357346446695435114770665690236421798600904697"
                + "382122466811455071513309341258456232392352376352774860423692"
                + "909938039781545795965777440720931029842931339578472178325517"
                + "108960945189288028016750780165782332129321283630491413295059"
                + "302586168568433724780844776076122333860000742844259902840419"
                + "992620364428698462118597649581931886119268720739388623554386"
                + "517407825772232837273665279508542647411066565258347874824908"
                + "412029504708087880627376609017052313156696308292318481235284"
                + "022891635466230881948210196179585746085885371676062798971605"
                + "416432728093246782649563823084535365411330294647266749336165"
                + "284407309275166723853517611067826466970731604290150241096640"
                + "848738456281423792224115541822758254256696444012256692183533"
                + "598616976376137756688506031511953166754544712875764446544943"
                + "612948268663859173442027683063022381685286222138220436146301"
                + "997731422313017774391364296980752385201909221696700771902326"
                + "982466009062101017006896673103555409600744877589153564890316"
                + "319521129849494087046862413902967995711862805056794729735181"
                + "359644271828434369316341582411926820237001732950684152032674"
                + "886605543141618436702698910261437010668800465247178793111285"
                + "645279615966330680160312220546680106833640059941564644428903"
                + "209229044088900258052013149311913225186653285083023566069845"
                + "947552923542201186029017803655248750644292456835466968977746"
                + "015968354788696887137679183531666630414463740895309686450177"
                + "130666328564271332474718447924727827403198528628759612981960"
                + "247367054935743541403921237911171312655444051299310110574731"
                + "627563518568431348297451458238711598610141671122568716565882"
                + "073808514325171824034990554406483170369561951445089019074620"
                + "357190814276091932117906913648991641277494332935193005419883"
                + "270812254984886595961663704435297090629480640738137326608446"
                + "379984384651068566515933134984565024991498903843520013628260"
                + "422555012349786764999104941200926343934274066019612373871143"
                + "084177885866765104145730062886593017615562560337577791843376"
                + "643075013375439557306696966989012220431865099337683157411375"
                + "683081004446286997751744072003561879787347606956826279112385"
                + "895014877702432649228717529922940939067693221734185754850242"
                + "656374966912768013311175060344407853723778182921557352115848"
                + "157992935343762083633575902739905121241873579910175193239072"
                + "640583033773927904390693906167715718005061646964661619850294"
                + "663664082259912123648048254962242492560992835640265424749462"
                + "208461508734214485351705659192753232921376476228595882928314"
                + "670243502333958093683891566916839892128145113727730563443641"
                + "375498015667365555417116514099355911781873411967124478319673"
                + "104483103403773607353476955848306305906755546364375089686184"
                + "466300950002917832235081497199718106452797781825924030508060"
                + "763834704572400339518774113122438916525582517669840449421085"
                + "741577750520283950181587394510045532584566939013798293836977"
                + "792897857352766577025157832181761362403698346675722271546955"
                + "292793908063958635335744277061544769113858044434465967275615"
                + "326711569591890100384675656041209576792656884858620322497983"
                + "239403095237516760951305784275847723022547719268083846271823"
                + "735318884131798881790995760811623503580152929866710020935567"
                + "600578531473971352480451465608038276147778920410264669145152"
                + "029610430823993358064511753897154522128649394358539586879851"
                + "751798500848058878282308964092107816990051604387533349829117"
                + "508584373557793461006198784188836749428605632366549829426681"
                + "649248274899393212958233580465857470011121150330780790501801"
                + "101347339521436870749573917776894223484888538680605048812509"
                + "889085873574610484080106437370444199510460704048003194585458"
                + "244228384338073908271548907618963564789426667849256684732821"
                + "595473571996296742764130064248078960739894526218598513228484"
                + "482203203832953567614071502390458287097109590835965709370324"
                + "810428915592663476532298387157534571732918360086878292131950"
                + "001225485074448272607876499488009501403904511693548580879992"
                + "797007053743912000001394774611126808322115259724223111028126"
                + "400000000189112118738511766661434881256783931640000000000023"
                + "543369902086743437464659975945712000000000000002672621970309"
                + "124642023852009653280000000000000000274368336958127978854722"
                + "514080000000000000000000025217678029239703938853172250000000"
                + "000000000000002049592850085518962824600000000000000000000000"
                + "000145008590565985776630000000000000000000000000000008748632"
                + "914991600400000000000000000000000000000000437606688424950000"
                + "000000000000000000000000000000017422382340000000000000000000"
                + "000000000000000000000517599000000000000000000000000000000000"
                + "000000000010200000000000000000000000000000000000000000000000"
                + "1");
        result = Generic_BigDecimal.powerNoRounding(
                x,
                y,
                div);
        printPowerNoRounding_3args(
                x,
                y,
                div,
                result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testPowerNoRounding_3args()
     *
     * @param compare
     * @param x
     * @param y
     * @param result
     */
    private void printPowerNoRounding_3args(
            BigDecimal x,
            BigInteger y,
            int div,
            BigDecimal result) {
        System.out.println("x " + x);
        System.out.println("y " + y);
        System.out.println("result " + result);
    }

    public void testPowerNoRounding_2args() {
        System.out.println("testPowerNoRounding_2args()");
        testPowerNoRounding_2args_test1();
        testPowerNoRounding_2args_test2();
        testPowerNoRounding_2args_test3();
        testPowerNoRounding_2args_test4();
        testPowerNoRounding_2args_test5();
        testPowerNoRounding_2args_test6();
        testPowerNoRounding_2args_test7();
        testPowerNoRounding_2args_test8();
        testPowerNoRounding_2args_test9();
        testPowerNoRounding_2args_test10();
        testPowerNoRounding_2args_test11();
        testPowerNoRounding_2args_test12();
    }

    public void testPowerNoRounding_2args_test1() {
        System.out.println("testPowerNoRounding_2args_test1()");
        BigDecimal x = null;
        int y = 0;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.1");
        y = 2;
        expResult = new BigDecimal("26.01");
        result = Generic_BigDecimal.powerNoRounding(
                x,
                y);
        printPowerNoRounding_2args(
                x,
                y,
                result);
        assertEquals(expResult, result);
    }

    public void testPowerNoRounding_2args_test2() {
        System.out.println("testPowerNoRounding_2args_test2()");
        BigDecimal x = null;
        int y = 0;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.1");
        y = 20;
        expResult = new BigDecimal("141710986707530.43575626125424226001");
        result = Generic_BigDecimal.powerNoRounding(
                x,
                y);
        printPowerNoRounding_2args(
                x,
                y,
                result);
        assertEquals(expResult, result);
    }

    public void testPowerNoRounding_2args_test3() {
        System.out.println("testPowerNoRounding_2args_test3()");
        BigDecimal x = null;
        int y = 0;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.10");
        y = 20;
        expResult = new BigDecimal(
                "141710986707530.4357562612542422600100000000000000000000");
        result = Generic_BigDecimal.powerNoRounding(
                x,
                y);
        printPowerNoRounding_2args(
                x,
                y,
                result);
        assertEquals(expResult, result);
    }

    public void testPowerNoRounding_2args_test4() {
        System.out.println("testPowerNoRounding_2args_test4()");
        BigDecimal x = null;
        int y = 0;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.10000000000000000000000000000000000000000000001");
        y = 20;
        expResult = new BigDecimal(
                "141710986707530.4357562612542422600100000000000055572935963737"
                + "425786769119310690200000000000001035182140500991264655503202"
                + "846190000000000000012178613417658720760652978857014000000000"
                + "000000101488445147156006338774823808450000000000000000636790"
                + "244060586706439371443504000000000000000003121520804218562286"
                + "467507076000000000000000000012241258055759067790068655200000"
                + "000000000000000039004008510997029723257970000000000000000000"
                + "000101971264081037986204596000000000000000000000000219938020"
                + "566944676127560000000000000000000000000392046382472272149960"
                + "000000000000000000000000000576538797753341397000000000000000"
                + "000000000000000695672757470095200000000000000000000000000000"
                + "000682032115166760000000000000000000000000000000000534927149"
                + "150400000000000000000000000000000000000327773988450000000000"
                + "000000000000000000000000000151222140000000000000000000000000"
                + "000000000000000049419000000000000000000000000000000000000000"
                + "000010200000000000000000000000000000000000000000000001");
        result = Generic_BigDecimal.powerNoRounding(
                x,
                y);
        printPowerNoRounding_2args(
                x,
                y,
                result);
        assertEquals(expResult, result);
    }

    public void testPowerNoRounding_2args_test5() {
        System.out.println("testPowerNoRounding_2args_test5()");
        BigDecimal x = null;
        int y = 0;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.2");
        y = 2;
        expResult = new BigDecimal("27.04");
        result = Generic_BigDecimal.powerNoRounding(
                x,
                y);
        printPowerNoRounding_2args(
                x,
                y,
                result);
        assertEquals(expResult, result);
    }

    public void testPowerNoRounding_2args_test6() {
        System.out.println("testPowerNoRounding_2args_test6()");
        BigDecimal x = null;
        int y = 0;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.3");
        y = 2;
        expResult = new BigDecimal("28.09");
        result = Generic_BigDecimal.powerNoRounding(
                x,
                y);
        printPowerNoRounding_2args(
                x,
                y,
                result);
        assertEquals(expResult, result);
    }

    public void testPowerNoRounding_2args_test7() {
        System.out.println("testPowerNoRounding_2args_test7()");
        BigDecimal x = null;
        int y = 0;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.4");
        y = 2;
        expResult = new BigDecimal("29.16");
        result = Generic_BigDecimal.powerNoRounding(
                x,
                y);
        printPowerNoRounding_2args(
                x,
                y,
                result);
        assertEquals(expResult, result);
    }

    public void testPowerNoRounding_2args_test8() {
        System.out.println("testPowerNoRounding_2args_test8()");
        BigDecimal x = null;
        int y = 0;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.5");
        y = 2;
        expResult = new BigDecimal("30.25");
        result = Generic_BigDecimal.powerNoRounding(
                x,
                y);
        printPowerNoRounding_2args(
                x,
                y,
                result);
        assertEquals(expResult, result);
    }

    public void testPowerNoRounding_2args_test9() {
        System.out.println("testPowerNoRounding_2args_test9()");
        BigDecimal x = null;
        int y = 0;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.6");
        y = 2;
        expResult = new BigDecimal("31.36");
        result = Generic_BigDecimal.powerNoRounding(
                x,
                y);
        printPowerNoRounding_2args(
                x,
                y,
                result);
        assertEquals(expResult, result);
    }

    public void testPowerNoRounding_2args_test10() {
        System.out.println("testPowerNoRounding_2args_test10()");
        BigDecimal x = null;
        int y = 0;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.7");
        y = 2;
        expResult = new BigDecimal("32.49");
        result = Generic_BigDecimal.powerNoRounding(
                x,
                y);
        printPowerNoRounding_2args(
                x,
                y,
                result);
        assertEquals(expResult, result);
    }

    public void testPowerNoRounding_2args_test11() {
        System.out.println("testPowerNoRounding_2args_test11()");
        BigDecimal x = null;
        int y = 0;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.8");
        y = 2;
        expResult = new BigDecimal("33.64");
        result = Generic_BigDecimal.powerNoRounding(
                x,
                y);
        printPowerNoRounding_2args(
                x,
                y,
                result);
        assertEquals(expResult, result);
    }

    public void testPowerNoRounding_2args_test12() {
        System.out.println("testPowerNoRounding_2args_test12()");
        BigDecimal x = null;
        int y = 0;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.9");
        y = 2;
        expResult = new BigDecimal("34.81");
        result = Generic_BigDecimal.powerNoRounding(
                x,
                y);
        printPowerNoRounding_2args(
                x,
                y,
                result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testPowerNoRounding_2args()
     *
     * @param compare
     * @param x
     * @param y
     * @param result
     */
    private void printPowerNoRounding_2args(
            BigDecimal x,
            int y,
            BigDecimal result) {
        System.out.println("x " + x);
        System.out.println("y " + y);
        System.out.println("result " + result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal.
     */
    //@Test
    public void testPower_6args() {
        //@TODO Not all tests completing in a satisfactory time so these are commented out until this issue is resolved satisfactorally...
        System.out.println("testPower_6args()");
        testPower_6args_test1(); // Test passes
        testPower_6args_test2(); // Test passes
        testPower_6args_test3(); // Test passes
        testPower_6args_test4(); // Test passes
//        testPower_6args_test5(); // Test either stuck or not completing in satisfactory time
//        testPower_6args_test6(); // Test either stuck or not completing in satisfactory time
//        testPower_6args_test7(); // Test either stuck or not completing in satisfactory time
//        testPower_6args_test8(); // Test either stuck or not completing in satisfactory time
        testPower_6args_test9(); // Test passes
        testPower_6args_test10(); // Test passes
        testPower_6args_test11(); // Test passes
//        testPower_6args_test12(); // Test either stuck or not completing in satisfactory time
        testPower_6args_test13(); // Test passes
        testPower_6args_test14(); // Test passes
        testPower_6args_test15(); // Test passes
        testPower_6args_test16(); // Test passes
        testPower_6args_test17(); // Test passes
//        testPower_6args_test18(); // Test either stuck or not completing in satisfactory time
//        testPower_6args_test19(); // Test either stuck or not completing in satisfactory time
//        testPower_6args_test20(); // Test either stuck or not completing in satisfactory time
        testPower_6args_test21(); // Test passes
        testPower_6args_test22(); // Test passes
        testPower_6args_test23(); // Test passes
        testPower_6args_test24(); // Test passes
    }

    /**
     * Test of power method, of class Generic_BigDecimal.
     */
    public void testPower_6args_test1() {
        System.out.println("testPower_6args_test1");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.1");
        y = new BigDecimal("0.0122");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
//        expResult = new BigDecimal(
//                "1.020075592235233496296126665026067483207748617172583488021310"
//                + "8734138226078100585587805435645098947069");
        expResult = new BigDecimal(
                "1.020075592235233496296126665026067483207748617172583488021310"
                + "8734138226078100585587805435645098947068");
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal.
     */
    public void testPower_6args_test2() {
        System.out.println("testPower_6args_test2");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.1");
        y = new BigDecimal("0.000122");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "1.000198787101384878642452720694744868971170929418775037222019"
                + "2032254156701221277775377001688988217835");
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal.
     */
    public void testPower_6args_test3() {
        System.out.println("testPower_6args_test3");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.1");
        y = new BigDecimal("-0.000122");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.999801252407073026532411361647635459308346680589674812109435"
                + "6355033399665871513258400090997521879138");
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal. This has been known to
     * take about 48 seconds to complete.
     */
    public void testPower_6args_test4() {
        System.out.println("testPower_6args_test4");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.1");
        y = new BigDecimal("-0.000122");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.999801252407073026532411361647635459308346680589674812109435"
                + "6355033399665871513258400090997521879138");
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal. This has been known to
     * take about 48 seconds to complete.
     */
    public void testPower_6args_test5() {
        System.out.println("testPower_6args_test5");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.1");
        y = new BigDecimal("-0.000000002");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.999999996741518925848289291581390088493740454739378865003717"
                + "6765269179691805288720372301300181098212");
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal. This has been known to
     * take about 48 seconds to complete.
     */
    public void testPower_6args_test6() {
        System.out.println("testPower_6args_test6");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.1");
        y = new BigDecimal("-0.000000000002");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.999999999996741518920544748674219018662252981309537110886643"
                + "3325558136648752946348246771904715458001");
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal.
     */
    public void testPower_6args_test7() {
        System.out.println("testPower_6args_test7");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.1");
        y = new BigDecimal("-0.000000000002");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.999999999996741518920544748674219018662252981309537110886643"
                + "3325558136648752946348246771904715458001");
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal.
     */
    public void testPower_6args_test8() {
        System.out.println("testPower_6args_test8");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal(
                "2.718281828459045235360287471352662497757247093699959574966967"
                + "627724076630353547594571382178525166427427466391932003059921"
                + "817413596629043572900334295260595630738132328627943490763233"
                + "829880753195251019011573834187930702154089149934884167509244"
                + "761460668082264800168477411853742345442437107539077744992069"
                + "551702761838606261331384583000752044933826560297606737113200"
                + "709328709127443747047230696977209310141692836819025515108657"
                + "463772111252389784425056953696770785449969967946864454905987"
                + "931636889230098793127736178215424999229576351482208269895193"
                + "668033182528869398496465105820939239829488793320362509443117"
                + "301238197068416140397019837679320683282376464804295311802328"
                + "782509819455815301756717361332069811250996181881593041690351"
                + "598888519345807273866738589422879228499892086805825749279610"
                + "484198444363463244968487560233624827041978623209002160990235"
                + "304369941849146314093431738143640546253152096183690888707016"
                + "768396424378140592714563549061303107208510383750510115747704"
                + "1718986106873969655212671546889570350354");
        y = x.negate();
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.065988035845312537076790187596846424938577048252796436402473"
                + "5415723927466340880862459929685632483709");
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal.
     */
    public void testPower_6args_test9() {
        System.out.println("testPower_6args_test9");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.9");
        y = new BigDecimal("200");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.000000000705507910865533257124642715759347962165079496127873"
                + "1576287122320926208555158293415657929853");
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal.
     */
    public void testPower_6args_test10() {
        System.out.println("testPower_6args_test10");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("1.1");
        y = new BigDecimal("200");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
//        expResult = new BigDecimal(
//                "189905276.4604618242121820463954116340585832240009877848127251"
//                + "456103762646167989140750662066593328455813588159");
        expResult = new BigDecimal(
                "189905276.4604618242121820463954116340585832240009877848127251"
                + "456103762646167989140750662066593328455813588181");
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal.
     */
    public void testPower_6args_test11() {
        System.out.println("testPower_6args_test11");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("1.0000000000000000000000000000000001");
        y = new BigDecimal("20000");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
//        expResult = new BigDecimal(
//                "1.000000000000000000000000000002000000000000000000000000000001"
//                + "9999000000000000000000000000013331331204");
        expResult = new BigDecimal(
                "1.000000000000000000000000000002000000000000000000000000000001"
                + "9999000000000000000000000000013331333400");
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal.
     */
    public void testPower_6args_test12() {
        System.out.println("testPower_6args_test12");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("1.000000000000000001234567");
        y = new BigDecimal("2000078764654345654");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "11.80925729618850935047900283877251871123117528236149368178447"
                + "43606317427155216550611985572658924014225");
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal.
     */
    public void testPower_6args_test13() {
        System.out.println("testPower_6args_test13");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.9");
        y = new BigDecimal("0.9");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.909532576082962189535366090754262974443473210154553394006625"
                + "8156584379857622915775444454069734604216");
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal.
     */
    public void testPower_6args_test14() {
        System.out.println("testPower_6args_test14");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.1");
        y = new BigDecimal("0.9");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.125892541179416721042395410639580060609361740946693106910792"
                + "301952664761578250202412105096627594617");
        expResult = expResult.setScale(decimalPlaces);
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal.
     */
    public void testPower_6args_test15() {
        System.out.println("testPower_6args_test15");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.9");
        y = new BigDecimal("0.1");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.989519258206214392646230170419804832155538415337091539600605"
                + "544414212962464564723600065458219541611");
        expResult = expResult.setScale(decimalPlaces);
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal.
     */
    public void testPower_6args_test16() {
        System.out.println("testPower_6args_test16");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.9");
        y = new BigDecimal("0.12");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.987436328376606708739063494229909542239622211966688276138278"
                + "1495703008692225765762583023898416587461");
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal.
     */
    public void testPower_6args_test17() {
        System.out.println("testPower_6args_test17");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.09");
        y = new BigDecimal("0.12");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.749047055475647117310421468815370379819904146132649930111430"
                + "8616900168333963941855154477042245424988");
//        expResult = new BigDecimal(
//                "0.749047055475647117310421468815370379819904146132649930111430"
//                + "8616900168333963941855154477042245371721");
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal.
     */
    public void testPower_6args_test18() {
        System.out.println("testPower_6args_test18");
        //assertNull(1);
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.1");
        y = new BigDecimal("0.999991");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.100002072348056530317390970017713311383030160316867649898675"
                + "1042536233958380984224300502095831919852");
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal.
     */
    public void testPower_6args_test19() {
        System.out.println("testPower_6args_test19");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.1");
        y = new BigDecimal("0.9999999991");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                //0.10000000020723265858419098518432
                "0.100000000207232449276900938780572607600670362630328624897040"
                + "9053787443199517073876579178209190227768");
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal.
     */
    public void testPower_6args_test20() {
        System.out.println("testPower_6args_test20");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.92");
        y = new BigDecimal(
                "0.0040983606557377051313184601610828394768759608268737792968");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.999658330476842250517859191452511308247518473356535240882710"
                + "507544024872721215314306435063439340094");
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal.
     */
    public void testPower_6args_test21() {
        System.out.println("testPower_6args_test21");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("-32");
        y = new BigDecimal("0.2");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("-2");
        expResult = expResult.setScale(decimalPlaces);
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_6args_test22() {
        System.out.println("testPower_6args_test22");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5");
        y = new BigDecimal("5");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("3125");
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_6args_test23() {
        System.out.println("testPower_6args_test23");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5.1");
        y = new BigDecimal("5");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("3450.25251");
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_6args_test24() {
        System.out.println("testPower_6args_test24");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("5");
        y = new BigDecimal("5.1");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "3670.684197150059393482698955287184067730384433616795842754956"
                + "9355366665963868814955884483346517180703125");
        expResult = expResult.setScale(decimalPlaces);
//        decimalPlaces = 28;
//        a_RoundingMode = RoundingMode.HALF_UP;
//        expResult = new BigDecimal(
//                "3670.6841971500593934826989552872");
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        printPower_6args(
                x,
                y,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testPower_6args()
     *
     * @param compare
     * @param x
     * @param y
     * @param decimalPlaces
     * @param a_RoundingMode
     * @param result
     */
    private void printPower_6args(
            BigDecimal x,
            BigDecimal y,
            int decimalPlaces,
            RoundingMode a_RoundingMode,
            BigDecimal result) {
        System.out.println("x " + x);
        System.out.println("y " + y);
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("a_RoundingMode " + a_RoundingMode);
        System.out.println("result " + result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal.
     */
    public void testPowerLessThanOne_4args() {
        System.out.println("powerLessThanOne");
        BigDecimal x = null;
        BigDecimal y = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        // Test 1
        x = new BigDecimal("5.1");
        y = new BigDecimal("0.011");
        decimalPlaces = 10;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("1.0180832024");
        result = Generic_BigDecimal.power(
                x,
                y,
                decimalPlaces,
                a_RoundingMode);
        assertEquals(expResult, result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal.
     */
    public void testPower_5args_1() {
        System.out.println("testPower_5args_1");
        testPower_5args_1_test1();
        testPower_5args_1_test2();
        testPower_5args_1_test3();
        testPower_5args_1_test4();
        testPower_5args_1_test5();
        testPower_5args_1_test6();
        testPower_5args_1_test7();
        testPower_5args_1_test8();
        testPower_5args_1_test9();
        testPower_5args_1_test10();
        testPower_5args_1_test11();
    }

    public void testPower_5args_1_test1() {
        System.out.println("testPower_5args_1_test1");
        BigDecimal x = null;
        int y = 0;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 10;
        x = new BigDecimal("5.1");
        y = 13;
        div = 8;
        expResult = new BigDecimal("1579109656.3156692196");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_1(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_1_test2() {
        System.out.println("testPower_5args_1_test2");
        BigDecimal x = null;
        int y = 0;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 10;
        x = new BigDecimal("5.1");
        y = 13;
        div = 12;
        expResult = new BigDecimal("1579109656.3156692196");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_1(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_1_test3() {
        System.out.println("testPower_5args_1_test3");
        BigDecimal x = null;
        int y = 0;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 10;
        x = new BigDecimal("5.1");
        y = 117;
        div = 8;
        expResult = new BigDecimal(
                "61053505308866480538551405717028781301447815633741042337063285"
                + "637565296816330773386.1309180477");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_1(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_1_test4() {
        System.out.println("testPower_5args_1_test4");
        BigDecimal x = null;
        int y = 0;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 10;
        x = new BigDecimal("3.14159265");
        y = 10;
        div = 3;
        expResult = new BigDecimal(
                "93648.0464059980");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_1(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_1_test5() {
        System.out.println("testPower_5args_1_test5");
        BigDecimal x = null;
        int y = 0;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 20;
        x = new BigDecimal("3.14159265");
        y = 10;
        div = 3;
        expResult = new BigDecimal(
                "93648.04640599799415742896");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_1(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_1_test6() {
        System.out.println("testPower_5args_1_test6");
        BigDecimal x = null;
        int y = 0;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 30;
        x = new BigDecimal("3.14159265");
        y = 10;
        div = 3;
        expResult = new BigDecimal(
                "93648.046405997994157428955669854799");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_1(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_1_test7() {
        System.out.println("testPower_5args_1_test7");
        BigDecimal x = null;
        int y = 0;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 30;
        x = new BigDecimal("3");
        y = -10;
        div = 3;
        expResult = new BigDecimal(
                "0.000016935087808430286711036597");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_1(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_1_test8() {
        System.out.println("testPower_5args_1_test8");
        BigDecimal x = null;
        int y = 0;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 40;
        x = new BigDecimal("3");
        y = -10;
        div = 3;
        expResult = new BigDecimal(
                "0.0000169350878084302867110365967247540178");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_1(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_1_test9() {
        System.out.println("testPower_5args_1_test9");
        BigDecimal x = null;
        int y = 0;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 40;
        x = new BigDecimal("3.00001");
        y = -10;
        div = 3;
        expResult = new BigDecimal(
                "0.0000169345233158524213740774680469856220");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_1(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_1_test10() {
        System.out.println("testPower_5args_1_test10");
        BigDecimal x = null;
        int y = 0;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 130;
        x = new BigDecimal("3.00001");
        y = -178;
        div = 3;
        expResult = new BigDecimal(
                "1.180752785902410385735038642706422321146970536E-85");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_1(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_1_test11() {
        System.out.println("testPower_5args_1_test11");
        BigDecimal x = null;
        int y = 0;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 30;
        x = new BigDecimal("0.1");
        y = -10;
        div = 3;
        expResult = new BigDecimal(
                "10000000000.000000000000000000000000000000");
//        expResult = new BigDecimal(
//                "1E+10");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_1(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_1_test12() {
        System.out.println("testPower_5args_1_test12");
        BigDecimal x = null;
        int y = 0;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 3000;
        x = new BigDecimal("0.11111111111111178978978921212121");
        y = -678;
        div = 3;
        expResult = new BigDecimal(
                "93648.046405997994157428955669854799");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_1(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testPower_5args_1()
     *
     * @param x
     * @param y
     * @param div
     * @param decimalPlaces
     * @param a_RoundingMode
     * @param result
     */
    private void printTestPower_5args_1(
            BigDecimal x,
            int y,
            int div,
            int decimalPlaces,
            RoundingMode a_RoundingMode,
            BigDecimal result) {
        System.out.println("x " + x);
        System.out.println("y " + y);
        System.out.println("div " + div);
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("a_RoundingMode " + a_RoundingMode);
        System.out.println("result " + result);
    }

    /**
     * Test of power method, of class Generic_BigDecimal.
     */
    public void testPower_5args_2() {
        System.out.println("testPower_5args_2");
        testPower_5args_2_test1();
        testPower_5args_2_test2();
        testPower_5args_2_test3();
        testPower_5args_2_test4();
        testPower_5args_2_test5();
        testPower_5args_2_test6();
        testPower_5args_2_test7();
        testPower_5args_2_test8();
        testPower_5args_2_test9();
        testPower_5args_2_test10();
        testPower_5args_2_test11();
        testPower_5args_2_test12();
        testPower_5args_2_test13();
        testPower_5args_2_test14();
        testPower_5args_2_test15();
        testPower_5args_2_test16();
        testPower_5args_2_test17();
        testPower_5args_2_test18();
        testPower_5args_2_test19();
        testPower_5args_2_test20();
    }

    public void testPower_5args_2_test1() {
        System.out.println("testPower_5args_2_test1");
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 100;
        x = new BigDecimal("1");
        y = new BigInteger("2");
        div = 2;
        expResult = new BigDecimal(
                "1");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_2(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_2_test2() {
        System.out.println("testPower_5args_2_test2");
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 100;
        x = new BigDecimal("2");
        y = new BigInteger("2");
        div = 2;
        expResult = new BigDecimal(
                "4");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_2(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_2_test3() {
        System.out.println("testPower_5args_2_test3");
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 100;
        x = new BigDecimal("2");
        y = new BigInteger("2");
        div = 8;
        expResult = new BigDecimal(
                "4");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_2(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_2_test4() {
        System.out.println("testPower_5args_2_test4");
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 100;
        x = new BigDecimal("2");
        y = new BigInteger("3");
        div = 2;
        expResult = new BigDecimal(
                "8");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_2(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_2_test5() {
        System.out.println("testPower_5args_2_test5");
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 100;
        x = new BigDecimal("2");
        y = new BigInteger("3");
        div = 8;
        expResult = new BigDecimal(
                "8");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_2(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_2_test6() {
        System.out.println("testPower_5args_2_test6");
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 100;
        x = new BigDecimal("2");
        y = new BigInteger("4");
        div = 4;
        expResult = new BigDecimal(
                "16");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_2(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_2_test7() {
        System.out.println("testPower_5args_2_test7");
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 100;
        x = new BigDecimal("2");
        y = new BigInteger("4");
        div = 2;
        expResult = new BigDecimal(
                "16");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_2(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_2_test8() {
        System.out.println("testPower_5args_2_test8");
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 100;
        x = new BigDecimal("2");
        y = new BigInteger("4");
        div = 3;
        expResult = new BigDecimal(
                "16");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_2(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_2_test9() {
        System.out.println("testPower_5args_2_test9");
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 100;
        x = new BigDecimal("2");
        y = new BigInteger("8");
        div = 8;
        expResult = new BigDecimal(
                "256");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_2(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_2_test10() {
        System.out.println("testPower_5args_2_test10");
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 100;
        x = new BigDecimal("2");
        y = new BigInteger("10");
        div = 64;
        expResult = new BigDecimal(
                "1024");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_2(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_2_test11() {
        System.out.println("testPower_5args_2_test11");
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 100;
        x = new BigDecimal("2");
        y = new BigInteger("11");
        div = 64;
        expResult = new BigDecimal(
                "2048");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_2(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_2_test12() {
        System.out.println("testPower_5args_2_test12");
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 100;
        x = new BigDecimal("2");
        y = new BigInteger("65");
        div = 32;
        expResult = new BigDecimal(
                "36893488147419103232");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_2(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_2_test13() {
        System.out.println("testPower_5args_2_test13");
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 100;
        x = new BigDecimal("2");
        y = new BigInteger("65");
        div = 17;
        expResult = new BigDecimal(
                "36893488147419103232");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_2(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_2_test14() {
        System.out.println("testPower_5args_2_test14");
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 100;
        x = new BigDecimal("5.1");
        y = new BigInteger("117");
        div = 8;
        expResult = new BigDecimal(
                "61053505308866480538551405717028781301447815633741042337063285"
                + "637565092974889024013.95170173462077405598021935614839617008"
                + "687986713833910778545970838802156925519570602363713165609193"
                + "54");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_2(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_2_test15() {
        System.out.println("testPower_5args_2_test15");
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 100;
        x = new BigDecimal("0.1122");
        y = new BigInteger("10");
        div = 8;
        expResult = new BigDecimal(
                "3.161757585765373988090713703424E-10");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_2(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_2_test16() {
        System.out.println("testPower_5args_2_test16");
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 100;
        x = new BigDecimal("0.001122");
        y = new BigInteger("1034");
        div = 8;
        expResult = new BigDecimal(
                "0");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_2(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_2_test17() {
        System.out.println("testPower_5args_2_test17");
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 10000;
        x = new BigDecimal("0.001122");
        y = new BigInteger("1034");
        div = 8;
        expResult = new BigDecimal(
                "4.927357297629103147819324052152950275036601937850815403560445"
                + "236083700295574927045053216349848466569243304082385070586331"
                + "538839593433725186784660546994199784794405666417896880399734"
                + "392822152057717524084020821246913991425766042466809293424533"
                + "034972894114375629109690271014735839013650881281424404883404"
                + "952627178442044469256079442076023740811913748135717170634334"
                + "445973831766327749399479523802859361773136381923354508933461"
                + "618023938839877142689146213275481918263220429948169622162364"
                + "922337446992380378212665889994137343237617557677875626890254"
                + "295259706779905218435234455553753174796971982647768423787118"
                + "272807358331025975590389280878424837370222082758234244287706"
                + "456396898801155129679937344396324435783061607404812097234868"
                + "223924852041257089352428950170312134239367962109331109855790"
                + "350671174934941334586824970429002651992224894052283952295809"
                + "492316458272466319934117994496319511555952649951450177044090"
                + "043910161446057619100938879513439717388717664624689258094857"
                + "330731919006273181884439658027453268974869186067292641819566"
                + "514956984747329303816033104406738539019781020532599948507727"
                + "988529526687300872650069258742566120276271867340143033851056"
                + "789292747869626164371979694745519442059826501796596482340361"
                + "594017578274163660408966234284546668539961240874506432650150"
                + "962204807023954584202461954563649423166017673746765136181101"
                + "831836280153612589414848846170450328866059576479132229822554"
                + "368961449058866258909507183670738364373401695108104036319657"
                + "411787253318074758252059071601242090847802375207964848101217"
                + "240762394670426166686940320598217178293910712816082566723018"
                + "277853037528206089402413221557917916227355715767056870601127"
                + "171796774936494333940602256515033485043086215408933562045459"
                + "236127236363165656336833326716457246722275433533702177482434"
                + "176687040511148125279215965350737478875515854849066442403789"
                + "652291244024162928193197244590528924711755971667621792047252"
                + "734277545494085654143191267754076377548548279266561650484126"
                + "999279685602865864569540281127913486201906412912817807453099"
                + "410562658382724925596828822866110767698571645903112559112400"
                + "078560969490848054213197577561936787146926676157384475182508"
                + "100587514117344003219530478249469418533026074576366867297461"
                + "387290316061973897819596285809715456825028542616230425563570"
                + "433524229591761963338129315616129109658611061754492874730583"
                + "528968182186218155776892535845513654601365679547807926196958"
                + "603519737159661537672735062412090435893226036152222921236129"
                + "013231354189711006651658658781804723559652913654659746633325"
                + "405103399891184766762208994172861736113917300597267029682915"
                + "954208886059165634882109331499342718272753691056654173191966"
                + "435899634973815441157597773782979741183717596828280117000116"
                + "229752327279731633753836817650405027400452401990752146863213"
                + "788033245337415675675000281001230106541302735966668688378528"
                + "736203480118517708977302740545122325040451589429208511769945"
                + "859450470074880842465140671770861465213884520202745850889929"
                + "851209699075320366569240296373127293730488339784386301462443"
                + "393273966477304719599878835469922158098817241892186876600894"
                + "811711817735060315993183660630801180299388370977016001053753"
                + "213675590341472292056479804429326923743981250071473193821515"
                + "915066035479225636617768291794944E-3051");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_2(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_2_test18() {
        System.out.println("testPower_5args_2_test18");
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 10000;
        x = new BigDecimal("0.0012345678991622");
        y = new BigInteger("1234");
        div = 8;
        expResult = new BigDecimal(
                "8.501309043595503534858369295274746396278923131310551482437978"
                + "961099991282995536024130634771054723737567510248365858917231"
                + "808995514915177754278268538033054531901346973695941956769685"
                + "729610800471300015155325762523212459304928882008541290136448"
                + "164697202788344400871671423873916602607681361130208799214930"
                + "190634832273692766016402172989257259574619028114555353482935"
                + "885283201670178379414086061377809789063858635712731974532512"
                + "430310980023129185818120757432867331649583134291701959195137"
                + "729994754704454960784837427814154835399466742746222888199322"
                + "185691398841244989033783917831898297087694127334821959823479"
                + "560817125415700617393543446659425729143468979736023831630340"
                + "291883200802015650903509389558971413332344895685883054561707"
                + "854340007144964401666766582625306847399794811880488947288749"
                + "500445957984619975261662034931894815609905146393901404336951"
                + "726392362334745376069094901376259664138106029706873774338173"
                + "998329612594142761937455247432615013087149603712001682535027"
                + "487425695214888131492006015586387144812760140264885210350022"
                + "385571183468262416433409054829313787356217286799499124417308"
                + "643563055390840771184992546154685006868848737894861390391409"
                + "652736342833892345333471166044356661607374015039399319274718"
                + "629478937611112391808059758444081736104635530893031155401711"
                + "673096847613763965152316408173575789911244622621569819962160"
                + "974284289755137304410385414276774910129688662502430115684211"
                + "305043916746636944858613978083992656812345045926463710124242"
                + "872379317940073605025489112358266685006669123714884708927439"
                + "575426947912616191851513190852767791448069336538448488951966"
                + "436659843996475485313360233640553751372827806934551841424342"
                + "157725009543570890223152253563288261584639816015870633616805"
                + "683134344263615367388668849038519919346201987837309595807403"
                + "718115617674167506300541837716501813908555972354625254540683"
                + "928046281922405458446981053787881445368430905255377016518170"
                + "113140563107615310054843269354650004168801318589079765801259"
                + "842775168756959968188252335431745319932389096359454986676414"
                + "011610028757894591810956706626561300829131209434392416569502"
                + "141827755845524803576284528831533313691000225371337937565727"
                + "416258357361247370778641312576571435906709049598433637869426"
                + "245593194706827798900884879338116900931898068707334976831839"
                + "215750020927621680289776239758865004682751232836834561154530"
                + "197215226122620319389943412936769554657213178728950944501797"
                + "039022570088560027217747633236633920979496320588682775057916"
                + "009158350376537176872951520778371161324081739839202968670224"
                + "229972959153366128266511727756008999699629586301651148690711"
                + "371796038695569976090142721415725908235169696667844504772472"
                + "736976544942017666534880236850216672807647363830166005657534"
                + "171567373816577363431316616044039394078774589888717044488084"
                + "644522676289533172266925566791315322656094992410632065645396"
                + "380509305484786578772631063641740921733037857157400156071911"
                + "020031521457831211857239554432678681664642838766003136008130"
                + "129077927242892574138931736152628228463796956539174013954892"
                + "792553892387483738547963113646915043658437009663206139368176"
                + "159964357304162665535919759783652929030066644758752767833010"
                + "410369287010756428079116908776272929715107491462040237349428"
                + "684250204705736280788513376326532955500066049276222779281096"
                + "096756928936140563456490724817027743722271023583886738488933"
                + "061353838760233314417772662177147902966575423722023809984290"
                + "392061646793078078814005028975430990216115523464876904124422"
                + "223862890087913210798522753191413395243907996283739569642210"
                + "244602147214777891517294936823722328014924206255329285478843"
                + "242768703400460960231139714600064620770464936072766209246068"
                + "001436415856197371778286474381782580916353234797514144983276"
                + "211337188385353920068002449139542467912279400044121727926761"
                + "803852730768611548931120161525698845041370421892412093778104"
                + "745283421666724059788026496249189559093802977763691408091609"
                + "142134121969282634573919857399828585560634031473179571643083"
                + "783808705265238498624915950546029209543773191347021783369878"
                + "064938930413118498634885015874247944667984191553912172650072"
                + "707360380125285762521870414832881235235495810169441806505751"
                + "410420427162949183602660804248108261697912973545722496566734"
                + "031552691187532397080861867628983441089434386222107891651768"
                + "009689455386050198483378408658051348635073517417074986893516"
                + "809658468756417818261992978162100241689365042289910302048880"
                + "668879802020609547163584043402329200170942940017537437036586"
                + "839966336108968391846619896256151126487470606396269903867297"
                + "906077328929013196940913689665108348787185943866272099058941"
                + "901510264511251756607818028877192639267509937281747260532310"
                + "944163987018930018783955113759662369114939078486137333961539"
                + "526873631068991739648940494355858390232176614093384693622420"
                + "011139723392319121907335218016788125567617837405231286280386"
                + "694610492170510062764269879513345743770334391701759618750592"
                + "095118260919871813035646088502264831152265077198437063338482"
                + "130789544767815219698964668358168356688056592917908797647821"
                + "705412172370423395311733554057242343877758435748283702730078"
                + "242470967847669725871834821527137084885921073698664574920903"
                + "530527792194811676429277067815580464712394558156962539394897"
                + "059280295797315118741492881864032577951288182863069610930500"
                + "059224365654476691962029610532184783002186573781854736492904"
                + "565915933709278870064582939867368802415510056126011853717941"
                + "064181885081220156682835784634844622353783970271341998461216"
                + "475057645889401559331454188428301965249141374467473887220244"
                + "934834282333506871494750086904041314916341348107129943354661"
                + "625814231790475354074385012903967994878807059698070011985709"
                + "961271426096489639456300191830839942172279156535627221475915"
                + "573789110618901998835717141475677114720083752104740057253833"
                + "190765965547964444608147838708615265918002739090946164599149"
                + "408169445447456338009921249339171373172788937040615057462928"
                + "972482269975680079544132583259441223009437283042950763862314"
                + "391176503883320604664808288433482496178775762776512005388047"
                + "410260111049601002224431547061166136239769725780929612481677"
                + "155332655223655783365346890417816768355980483289894226077957"
                + "044439824627196514323213427796618252575012014386085013928263"
                + "834276287574437588709514610827522468792615834939104288133068"
                + "375259673875098070501886720795065319174689340681481756754150"
                + "297808463032191538533928197268662625849497992170965785935507"
                + "140926152173690023441115066205059469854367305763215156748232"
                + "703087457290093111633254634388438823165743886696298824349345"
                + "656496377276196412103923039892904397552591077832462093232455"
                + "91101586589950430363520701982464337600308855098521E-3590");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_2(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_2_test19() {
        System.out.println("testPower_5args_2_test19");
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 1000;
        x = new BigDecimal("-0.0012345678991622");
        y = new BigInteger("12");
        div = 8;
        expResult = new BigDecimal(
                "-1.25365998693568959676678134576311434191784180749571395739232"
                + "878897951918031315109522890632250338730775085419737311675655"
                + "57247684654582741706554500513064226816E-35");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_2(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testPower_5args_2_test20() {
        System.out.println("testPower_5args_2_test20");
        BigDecimal x = null;
        BigInteger y = null;
        int div = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 500;
        x = new BigDecimal("-0.0012345678991622");
        y = new BigInteger("43");
        div = 8;
        expResult = new BigDecimal(
                "8.612795922579100431286467891265924977006154049807999513099100"
                + "860398926168811375495791586088327052448905995372673275625194"
                + "854714215437648883074238808406389133715622539770003076165360"
                + "359498335943518537092852659172457402813915377353569315634343"
                + "600751669994990651558870998167491502327268726540343541308219"
                + "777136364418488951763650222930343714459852346095478286203471"
                + "16113445581957E-126");
//        expResult = new BigDecimal(
//                "8.612795922579100431286467891265924977006154049807999513099100"
//                + "860398926168811375495791586088327052448905995372673275625194"
//                + "854714215437648883074238808406389133715622539770003076165360"
//                + "359498335943518537092852659172457402813915377353569315634343"
//                + "600751669994990651558870998167491502327268726540343541308219"
//                + "777136364418488951763650222930343714459852346095478286203471"
//                + "161134455819574164533018002589203787042509960656914325772667"
//                + "716029826898919327817949231116069267646148848457486764814939"
//                + "53915727671554972214E-126");
//        expResult = new BigDecimal(
//                "8.612795922579100431286467891265924977006154049807999513099100"
//                + "860398926168811375495791586088327052448905995372673275625194"
//                + "854714215437648883074238808406389133715622539770003076165360"
//                + "359498335943518537092852659172457402813915377353569315634343"
//                + "600751669994990651558870998167491502327268726540343541308219"
//                + "777136364418488951763650222930343714459852346095478286203471"
//                + "161134455819574164533018002589203787042509960656909323878421"
//                + "932179493755007178830228770626643007805900714109670294445996"
//                + "80564930616575338758E-126");
        result = Generic_BigDecimal.power(
                x, y, div, decimalPlaces, a_RoundingMode);
        printTestPower_5args_2(
                x,
                y,
                div,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testPower_5args_2()
     *
     * @param x
     * @param y
     * @param div
     * @param decimalPlaces
     * @param a_RoundingMode
     * @param result
     */
    private void printTestPower_5args_2(
            BigDecimal x,
            BigInteger y,
            int div,
            int decimalPlaces,
            RoundingMode a_RoundingMode,
            BigDecimal result) {
        System.out.println("x " + x);
        System.out.println("y " + y);
        System.out.println("div " + div);
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("a_RoundingMode " + a_RoundingMode);
        System.out.println("result " + result);
    }

    public void testGetDecimalPlacePrecision() {
        BigDecimal value;
        int significantDigits;
        int result;
        int expResult;
        value = new BigDecimal("0.001234567891011");
        significantDigits = 3;
        result = Generic_BigDecimal.getDecimalPlacePrecision(
                value,
                significantDigits);
        expResult = 5;
        assertEquals(expResult, result);
        value = new BigDecimal("0.00100234567891011");
        significantDigits = 3;
        result = Generic_BigDecimal.getDecimalPlacePrecision(
                value,
                significantDigits);
        expResult = 5;
        assertEquals(expResult, result);
        value = new BigDecimal("100.001234567891011");
        significantDigits = 3;
        result = Generic_BigDecimal.getDecimalPlacePrecision(
                value,
                significantDigits);
        expResult = 1;
        assertEquals(expResult, result);
        value = new BigDecimal("1.001234567891011");
        significantDigits = 3;
        result = Generic_BigDecimal.getDecimalPlacePrecision(
                value,
                significantDigits);
        expResult = 2;
        assertEquals(expResult, result);
        value = new BigDecimal("10.001234567891011");
        significantDigits = 3;
        result = Generic_BigDecimal.getDecimalPlacePrecision(
                value,
                significantDigits);
        expResult = 1;
        assertEquals(expResult, result);
        value = new BigDecimal("0.001234567891011");
        significantDigits = 5;
        result = Generic_BigDecimal.getDecimalPlacePrecision(
                value,
                significantDigits);
        expResult = 7;
        assertEquals(expResult, result);
        value = new BigDecimal("23.001234567891011");
        significantDigits = 3;
        result = Generic_BigDecimal.getDecimalPlacePrecision(
                value,
                significantDigits);
        expResult = 1;
        assertEquals(expResult, result);

    }

    /**
     * Test of reciprocal method, of class Generic_BigDecimal.
     */
    public void testReciprocal() {
        testReciprocal_test1();
        testReciprocal_test2();
        testReciprocal_test3();
        testReciprocal_test4();
    }

    public void testReciprocal_test1() {
        System.out.println("testReciprocal_test1");
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 10;
        x = new BigDecimal("1");
        expResult = new BigDecimal("1.0000000000");
        //expResult = new BigDecimal("1");
        result = Generic_BigDecimal.reciprocal(x, decimalPlaces, a_RoundingMode);
        printTestReciprocal(x, decimalPlaces, a_RoundingMode, result);
        assertEquals(expResult, result);
    }

    public void testReciprocal_test2() {
        System.out.println("testReciprocal_test2");
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 10;
        x = new BigDecimal("0.1");
        // "1E+1" is equivallent to "10" in numerical value, but they are not 
        // equal Strings and assertEquals fails if "10" is used to construct 
        // expResult.
        //expResult = new BigDecimal("1E+1");
        //expResult = new BigDecimal("10");
        expResult = new BigDecimal("10.0000000000");
        result = Generic_BigDecimal.reciprocal(x, decimalPlaces, a_RoundingMode);
        printTestReciprocal(x, decimalPlaces, a_RoundingMode, result);
        assertEquals(expResult, result);
    }

    public void testReciprocal_test3() {
        System.out.println("testReciprocal_test3");
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 10;
        x = new BigDecimal("0.00000000000000000000001");
        // "1E+23" is equivallent to "100000000000000000000000" in numerical 
        // value, but they are not equal Strings and assertEquals fails if 
        // "100000000000000000000000" is used to construct expResult.
        //expResult = new BigDecimal("100000000000000000000000");
        //expResult = new BigDecimal("1E+23");
        expResult = new BigDecimal("100000000000000000000000.0000000000");
        result = Generic_BigDecimal.reciprocal(x, decimalPlaces, a_RoundingMode);
        printTestReciprocal(x, decimalPlaces, a_RoundingMode, result);
        assertEquals(expResult, result);
    }

    public void testReciprocal_test4() {
        System.out.println("testReciprocal_test4");
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_RoundingMode = RoundingMode.HALF_UP;
        decimalPlaces = 100;
        x = new BigDecimal("1000000000000000000000000.00000000000000000000001");
//        expResult = new BigDecimal(
//                "0.000000000000000000000000999999999999999999999999999999999999"
//                + "99999999999");
        expResult = new BigDecimal(
                "9.999999999999999999999999999999999999999999999900000000000000"
                + "000000000000000E-25");
        result = Generic_BigDecimal.reciprocal(x, decimalPlaces, a_RoundingMode);
        printTestReciprocal(x, decimalPlaces, a_RoundingMode, result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testReciprocal()
     *
     * @param x
     * @param decimalPlaces
     * @param a_RoundingMode
     * @param result
     */
    private void printTestReciprocal(
            BigDecimal x,
            int decimalPlaces,
            RoundingMode a_RoundingMode,
            BigDecimal result) {
        System.out.println("x " + x);
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("a_RoundingMode " + a_RoundingMode);
        System.out.println("result " + result);
    }

    /**
     * Test of reciprocalWillBeIntegerReturnBigInteger method, of class
     * Generic_BigDecimal.
     */
    public void testReciprocalWillBeInteger() {
        System.out.println("reciprocalWillBeInteger");
        testReciprocalWillBeInteger_test1();
        testReciprocalWillBeInteger_test2();
        testReciprocalWillBeInteger_test3();
    }

    public void testReciprocalWillBeInteger_test1() {
        System.out.println("testReciprocalWillBeInteger_test1");
        BigDecimal x = null;
        BigInteger expResult = null;
        BigInteger result = null;
        x = new BigDecimal("1");
        expResult = new BigInteger("1");
        result = Generic_BigDecimal.reciprocalWillBeIntegerReturnBigInteger(x);
        printTestReciprocalWillBeInteger(x, result);
        assertEquals(expResult, result);
    }

    public void testReciprocalWillBeInteger_test2() {
        System.out.println("testReciprocalWillBeInteger_test2");
        BigDecimal x = null;
        BigInteger expResult = null;
        BigInteger result = null;
        x = new BigDecimal("0.1");
        expResult = new BigInteger("10");
        result = Generic_BigDecimal.reciprocalWillBeIntegerReturnBigInteger(x);
        printTestReciprocalWillBeInteger(x, result);
        assertEquals(expResult, result);
    }

    public void testReciprocalWillBeInteger_test3() {
        System.out.println("testReciprocalWillBeInteger_test3");
        BigDecimal x = null;
        BigInteger expResult = null;
        BigInteger result = null;
        x = new BigDecimal("0.00000000000000000000001");
        expResult = new BigInteger("100000000000000000000000");
        result = Generic_BigDecimal.reciprocalWillBeIntegerReturnBigInteger(x);
        printTestReciprocalWillBeInteger(x, result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testReciprocalWillBeInteger()
     *
     * @param x
     * @param decimalPlaces
     * @param a_RoundingMode
     * @param result
     */
    private void printTestReciprocalWillBeInteger(
            BigDecimal x,
            BigInteger result) {
        System.out.println("x " + x);
        System.out.println("result " + result);
    }

    /**
     * Test of log method, of class Generic_BigDecimal.
     */
    public void testLog() {
        testLog_4args_1();
        testLog_4args_2();
    }

    /**
     * Test of log method, of class Generic_BigDecimal.
     */
    public void testLog_4args_1() {
        System.out.println("testLog_4args_1");
        testLog_4args_1_test1();
        testLog_4args_1_test2();
        testLog_4args_1_test3();
    }

    public void testLog_4args_1_test1() {
        System.out.println("testLog_4args_1_test1");
        int base_int = 0;
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        base_int = 10;
        x = new BigDecimal("10");
        decimalPlaces = 10;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("1");
        result = Generic_BigDecimal.log(
                base_int,
                x,
                decimalPlaces,
                a_RoundingMode);
        assertEquals(expResult, result);
    }

    public void testLog_4args_1_test2() {
        System.out.println("testLog_4args_1_test2");
        int base_int = 0;
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        base_int = 10;
        x = new BigDecimal("100");
        decimalPlaces = 10;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("2.0000000000");
        result = Generic_BigDecimal.log(
                base_int,
                x,
                decimalPlaces,
                a_RoundingMode);
        assertEquals(expResult, result);
    }

    public void testLog_4args_1_test3() {
        System.out.println("testLog_4args_1_test3");
        int base_int = 0;
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        base_int = 10;
        x = new BigDecimal("100100.1");
        decimalPlaces = 10;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("5.0004345113");
        result = Generic_BigDecimal.log(
                base_int,
                x,
                decimalPlaces,
                a_RoundingMode);
        assertEquals(expResult, result);
    }

    public void testLog_4args_2() {
        System.out.println("testLog_4args_2");
        testLog_4args_2_test1();
    }

    public void testLog_4args_2_test1() {
        System.out.println("testLog_4args_2_test1");
        BigDecimal base = null;
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        decimalPlaces = 10;
        a_RoundingMode = RoundingMode.HALF_UP;
        base = new BigDecimal("10");
        x = new BigDecimal("10");
        expResult = new BigDecimal("1");
        result = Generic_BigDecimal.log(base, x, decimalPlaces, a_RoundingMode);
        assertEquals(expResult, result);
    }

    public void testLog_4args_2_test2() {
        System.out.println("testLog_4args_2_test2");
        BigDecimal base = null;
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        decimalPlaces = 10;
        a_RoundingMode = RoundingMode.HALF_UP;
        base = new BigDecimal("10");
        x = new BigDecimal("1");
        expResult = new BigDecimal("0");
        result = Generic_BigDecimal.log(base, x, decimalPlaces, a_RoundingMode);
        assertEquals(expResult, result);
    }

    public void testLog_4args_2_test3() {
        System.out.println("testLog_4args_2_test3");
        BigDecimal base = null;
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        decimalPlaces = 10;
        a_RoundingMode = RoundingMode.HALF_UP;
        base = new BigDecimal("11");
        x = new BigDecimal("10");
        expResult = new BigDecimal("0.9602525678");
        result = Generic_BigDecimal.log(base, x, decimalPlaces, a_RoundingMode);
        assertEquals(expResult, result);
    }

    public void testLog_4args_2_test4() {
        System.out.println("testLog_4args_2_test4");
        BigDecimal base = null;
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        decimalPlaces = 10;
        a_RoundingMode = RoundingMode.HALF_UP;
        base = new BigDecimal("10.5");
        x = new BigDecimal("10");
        expResult = new BigDecimal("0.9792503710");
        result = Generic_BigDecimal.log(base, x, decimalPlaces, a_RoundingMode);
        assertEquals(expResult, result);
    }

    public void testRoundToAndSetDecimalPlaces_3args() {
        testRoundToAndSetDecimalPlaces_3args_test1();
        testRoundToAndSetDecimalPlaces_3args_test2();
        testRoundToAndSetDecimalPlaces_3args_test3();
        testRoundToAndSetDecimalPlaces_3args_test4();
        testRoundToAndSetDecimalPlaces_3args_test5();
        testRoundToAndSetDecimalPlaces_3args_test6();
        testRoundToAndSetDecimalPlaces_3args_test7();
        testRoundToAndSetDecimalPlaces_3args_test8();
        testRoundToAndSetDecimalPlaces_3args_test9();
    }

    public void testRoundToAndSetDecimalPlaces_3args_test1() {
        System.out.println("testRoundToAndSetDecimalPlaces_3args_test1");
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("1");
        decimalPlaces = 2;
        a_RoundingMode = RoundingMode.HALF_UP;
        //expResult = new BigDecimal("1");
        expResult = new BigDecimal("1.00");
        result = Generic_BigDecimal.roundToAndSetDecimalPlaces(
                x, decimalPlaces, a_RoundingMode);
        printTestRound(x, decimalPlaces, a_RoundingMode, result);
        assertEquals(expResult, result);
    }

    public void testRoundToAndSetDecimalPlaces_3args_test2() {
        System.out.println("testRoundToAndSetDecimalPlaces_3args_test2");
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("1.015");
        decimalPlaces = 2;
        a_RoundingMode = RoundingMode.HALF_UP;
        //expResult = new BigDecimal("1");
        expResult = new BigDecimal("1.02");
        result = Generic_BigDecimal.roundToAndSetDecimalPlaces(
                x, decimalPlaces, a_RoundingMode);
        printTestRound(x, decimalPlaces, a_RoundingMode, result);
        assertEquals(expResult, result);
    }

    public void testRoundToAndSetDecimalPlaces_3args_test3() {
        System.out.println("testRoundToAndSetDecimalPlaces_3args_test3");
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("1543543.005");
        decimalPlaces = 5;
        a_RoundingMode = RoundingMode.HALF_UP;
        //expResult = new BigDecimal("1");
        expResult = new BigDecimal("1543543.00500");
        result = Generic_BigDecimal.roundToAndSetDecimalPlaces(
                x, decimalPlaces, a_RoundingMode);
        printTestRound(x, decimalPlaces, a_RoundingMode, result);
        assertEquals(expResult, result);
    }

    public void testRoundToAndSetDecimalPlaces_3args_test4() {
        System.out.println("testRoundToAndSetDecimalPlaces_3args_test4");
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("1.0001");
        decimalPlaces = 2;
        a_RoundingMode = RoundingMode.HALF_UP;
        //expResult = new BigDecimal("1");
        expResult = new BigDecimal("1.00");
        result = Generic_BigDecimal.roundToAndSetDecimalPlaces(
                x, decimalPlaces, a_RoundingMode);
        printTestRound(x, decimalPlaces, a_RoundingMode, result);
        assertEquals(expResult, result);
    }

    public void testRoundToAndSetDecimalPlaces_3args_test5() {
        System.out.println("testRoundToAndSetDecimalPlaces_3args_test5");
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("1.005");
        decimalPlaces = 2;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("1.01");
        result = Generic_BigDecimal.roundToAndSetDecimalPlaces(
                x, decimalPlaces, a_RoundingMode);
        printTestRound(x, decimalPlaces, a_RoundingMode, result);
        assertEquals(expResult, result);
    }

    public void testRoundToAndSetDecimalPlaces_3args_test6() {
        System.out.println("testRoundToAndSetDecimalPlaces_3args_test6");
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.0001");
        decimalPlaces = 2;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("0.00");
        result = Generic_BigDecimal.roundToAndSetDecimalPlaces(
                x, decimalPlaces, a_RoundingMode);
        printTestRound(x, decimalPlaces, a_RoundingMode, result);
        assertEquals(expResult, result);
    }

    public void testRoundToAndSetDecimalPlaces_3args_test7() {
        System.out.println("testRoundToAndSetDecimalPlaces_3args_test7");
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.005");
        decimalPlaces = 2;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("0.01");
        result = Generic_BigDecimal.roundToAndSetDecimalPlaces(
                x, decimalPlaces, a_RoundingMode);
        printTestRound(x, decimalPlaces, a_RoundingMode, result);
        assertEquals(expResult, result);
    }

    public void testRoundToAndSetDecimalPlaces_3args_test8() {
        System.out.println("testRoundToAndSetDecimalPlaces_3args_test8");
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.00123456789");
        decimalPlaces = 6;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("0.001235");
        result = Generic_BigDecimal.roundToAndSetDecimalPlaces(
                x, decimalPlaces, a_RoundingMode);
        printTestRound(x, decimalPlaces, a_RoundingMode, result);
        assertEquals(expResult, result);
    }

    public void testRoundToAndSetDecimalPlaces_3args_test9() {
        System.out.println("testRoundToAndSetDecimalPlaces_3args_test9");
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.00123456789");
        decimalPlaces = 7;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("0.0012346");
        result = Generic_BigDecimal.roundToAndSetDecimalPlaces(
                x, decimalPlaces, a_RoundingMode);
        printTestRound(x, decimalPlaces, a_RoundingMode, result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testRound()
     *
     * @param x
     * @param decimalPlaces
     * @param a_RoundingMode
     * @param result
     */
    private void printTestRound(
            BigDecimal x,
            int decimalPlaces,
            RoundingMode a_RoundingMode,
            BigDecimal result) {
        System.out.println("x " + x);
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("a_RoundingMode " + a_RoundingMode);
        System.out.println("result " + result);
    }

    /**
     * Test of getEMinDecimalPlaces method, of class Generic_BigDecimal.
     */
    public void testGetEulerConstantToAMinimumDecimalPlacePrecision() {
        System.out.println("testGetEulerConstantToAMinimumDecimalPlacePrecision");
        testGetEulerConstantToAMinimumDecimalPlacePrecision_test1();
    }

    public void testGetEulerConstantToAMinimumDecimalPlacePrecision_test1() {
        System.out.println("testGetEulerConstantToAMinimumDecimalPlacePrecision_test1");
        int decimalPlaces = 0;
        Generic_BigDecimal instance = new Generic_BigDecimal();
        BigDecimal expResult = null;
        BigDecimal result = null;
        decimalPlaces = 1000;
        expResult = new BigDecimal(
                "2.718281828459045235360287471352662497757247093699959574966967"
                + "627724076630353547594571382178525166427427466391932003059921"
                + "817413596629043572900334295260595630738132328627943490763233"
                + "829880753195251019011573834187930702154089149934884167509244"
                + "761460668082264800168477411853742345442437107539077744992069"
                + "551702761838606261331384583000752044933826560297606737113200"
                + "709328709127443747047230696977209310141692836819025515108657"
                + "463772111252389784425056953696770785449969967946864454905987"
                + "931636889230098793127736178215424999229576351482208269895193"
                + "668033182528869398496465105820939239829488793320362509443117"
                + "301238197068416140397019837679320683282376464804295311802328"
                + "782509819455815301756717361332069811250996181881593041690351"
                + "598888519345807273866738589422879228499892086805825749279610"
                + "484198444363463244968487560233624827041978623209002160990235"
                + "304369941849146314093431738143640546253152096183690888707016"
                + "768396424378140592714563549061303107208510383750510115747704"
                + "1718986106873969655212671546889570350354");
        result = instance.getEulerConstantToAMinimumDecimalPlacePrecision(
                decimalPlaces);
        printTestGetEulerConstantToAMinimumDecimalPlacePrecision(
                decimalPlaces, result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testGetEMinDecimalPlaces()
     *
     * @param decimalPlaces
     * @param a_RoundingMode
     * @param result
     */
    private void printTestGetEulerConstantToAMinimumDecimalPlacePrecision(
            int decimalPlaces,
            BigDecimal result) {
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("result " + result);
    }

    public void testGetEulerConstant() {
        testGetEulerConstantToAFixedDecimalPlacePrecision();
        testGetEulerConstantToAMinimumDecimalPlacePrecision();
    }

    public void testGetEulerConstantToAFixedDecimalPlacePrecision() {
        System.out.println("testGetEulerConstantToAFixedDecimalPlacePrecision");
        testGetEulerConstantToAFixedDecimalPlacePrecision_test1();
        testGetEulerConstantToAFixedDecimalPlacePrecision_test2();
    }

    public void testGetEulerConstantToAFixedDecimalPlacePrecision_test1() {
        System.out.println("testGetEulerConstantToAFixedDecimalPlacePrecision_test1");
        Generic_BigDecimal instance;
        int decimalPlaces;
        RoundingMode a_RoundingMode;
        BigDecimal expResult = null;
        BigDecimal result = null;
        instance = new Generic_BigDecimal();
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "2.718281828459045235360287471352662497757247093699959574966967"
                + "6277240766303535475945713821785251664274");
        result = instance.getEulerConstantToAFixedDecimalPlacePrecision(
                decimalPlaces, a_RoundingMode);
        printTestGetEulerConstantToAFixedDecimalPlacePrecision(
                decimalPlaces, a_RoundingMode, result);
        assertEquals(expResult, result);
    }

    public void testGetEulerConstantToAFixedDecimalPlacePrecision_test2() {
        System.out.println("testGetEulerConstantToAFixedDecimalPlacePrecision_test2");
        Generic_BigDecimal instance;
        int decimalPlaces;
        RoundingMode a_RoundingMode;
        BigDecimal expResult = null;
        BigDecimal result = null;
        instance = new Generic_BigDecimal();
        decimalPlaces = 1000;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "2.718281828459045235360287471352662497757247093699959574966967"
                + "627724076630353547594571382178525166427427466391932003059921"
                + "817413596629043572900334295260595630738132328627943490763233"
                + "829880753195251019011573834187930702154089149934884167509244"
                + "761460668082264800168477411853742345442437107539077744992069"
                + "551702761838606261331384583000752044933826560297606737113200"
                + "709328709127443747047230696977209310141692836819025515108657"
                + "463772111252389784425056953696770785449969967946864454905987"
                + "931636889230098793127736178215424999229576351482208269895193"
                + "668033182528869398496465105820939239829488793320362509443117"
                + "301238197068416140397019837679320683282376464804295311802328"
                + "782509819455815301756717361332069811250996181881593041690351"
                + "598888519345807273866738589422879228499892086805825749279610"
                + "484198444363463244968487560233624827041978623209002160990235"
                + "304369941849146314093431738143640546253152096183690888707016"
                + "768396424378140592714563549061303107208510383750510115747704"
                + "1718986106873969655212671546889570350354");
        result = instance.getEulerConstantToAFixedDecimalPlacePrecision(
                decimalPlaces, a_RoundingMode);
        printTestGetEulerConstantToAFixedDecimalPlacePrecision(decimalPlaces, a_RoundingMode, result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testGetE()
     *
     * @param decimalPlaces
     * @param a_RoundingMode
     * @param result
     */
    private void printTestGetEulerConstantToAFixedDecimalPlacePrecision(
            int decimalPlaces,
            RoundingMode a_RoundingMode,
            BigDecimal result) {
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("a_RoundingMode " + a_RoundingMode);
        System.out.println("result " + result);
    }

//    /**
//     * Test of exp method, of class Generic_BigDecimal.
//     */
//    @Test
//    public void testExp() {
//        System.out.println("exp");
//        BigDecimal x = null;
//        Generic_BigDecimal a_Generic_BigDecimal = null;
//        int decimalPlaces = 0;
//        RoundingMode a_RoundingMode = null;
//        BigDecimal expResult = null;
//        BigDecimal result = null;
//        // Test 1
//        System.out.println("Test 1");
//        x = new BigDecimal("2");
//        decimalPlaces = 10;
//        a_RoundingMode = RoundingMode.HALF_UP;
//        expResult = new BigDecimal("7.3890545668");
//        result = Generic_BigDecimal.exp(
//                x, a_Generic_BigDecimal, decimalPlaces, a_RoundingMode);
//        assertEquals(expResult, result);
//        // Test 2
//        System.out.println("Test 2");
//        x = new BigDecimal("0.02");
//        decimalPlaces = 10;
//        a_RoundingMode = RoundingMode.HALF_UP;
//        expResult = new BigDecimal("0.0000240835");
//        result = Generic_BigDecimal.exp(
//                x, a_Generic_BigDecimal, decimalPlaces, a_RoundingMode);
//        assertEquals(expResult, result);
//        // Test 3
//        System.out.println("Test 3");
//        x = new BigDecimal("0.000000012");
//        decimalPlaces = 10;
//        a_RoundingMode = RoundingMode.HALF_UP;
//        expResult = new BigDecimal("0.0000000000");
//        result = Generic_BigDecimal.exp(
//                x, a_Generic_BigDecimal, decimalPlaces, a_RoundingMode);
//        assertEquals(expResult, result);
//        // Test 4
//        System.out.println("Test 4");
//        x = new BigDecimal("0.000000012");
//        decimalPlaces = 100;
//        a_RoundingMode = RoundingMode.HALF_UP;
//        expResult = new BigDecimal(
//                "0.000000000000000000000294430173036448917951275819843218363632"
//                + "0493797907240661183598466156605197338635");
//        result = Generic_BigDecimal.exp(
//                x, a_Generic_BigDecimal, decimalPlaces, a_RoundingMode);
//        assertEquals(expResult, result);
//        // Test 5
//        System.out.println("Test 5");
//        x = new BigDecimal("12345678");
//        decimalPlaces = 100;
//        a_RoundingMode = RoundingMode.HALF_UP;
//        expResult = new BigDecimal(
//                "22469987448871299679547963367551101970728469329227896523830704"
//                + "212846507527510201039606390983108596821296225851730388291621"
//                + "272917661346368888415646572300956841739166190426540883627613"
//                + "348893590470374000362490449021446785496741635356940111945686"
//                + "043140310007547314300518322792319063942712106014037660400230"
//                + "919068637137868831868396197015034391985215607858957818538709"
//                + "568080692348556936455075086327755092097759426247312136394078"
//                + "821328086903190588371416349228004475077599700795549144690602"
//                + "812073237117996499428152920084501432775597288687705787036941"
//                + "05868688623026692684.163632156232358690924047183584316158333"
//                + "675721857593135877969643591237953992931456902718860364075323"
//                + "7");
//        result = Generic_BigDecimal.exp(
//                x, a_Generic_BigDecimal, decimalPlaces, a_RoundingMode);
//        assertEquals(expResult, result);
//        // Test 6
//        System.out.println("Test 6");
//        x = new BigDecimal("12345678.123");
//        decimalPlaces = 100;
//        a_RoundingMode = RoundingMode.HALF_UP;
//        expResult = new BigDecimal(
//                "22470010283468276518762514010953191215303006538532034030126326"
//                + "620962331189895056345375543147172117369967073803251319001037"
//                + "999345297397157238784684181524853564474178872550431567586472"
//                + "414884052274145227899161017052949184849733116040215401862870"
//                + "570329567454894710992095140122009037170837676003807366037808"
//                + "764439244685114826069263513915784079341369099585929816346707"
//                + "050688538189251614194691098696814575956887401354704941859947"
//                + "700532834819152111890953576912100514177537005772539122006868"
//                + "358101311353118135581111789951492983239533032293197596392044"
//                + "10899554214585338563.173832288281282191708046629849175523366"
//                + "725273703318641625995899652501875438449418493817553176244532"
//                + "");
//        result = Generic_BigDecimal.exp(
//                x, a_Generic_BigDecimal, decimalPlaces, a_RoundingMode);
//        assertEquals(expResult, result);
//    }
    /**
     * Test of max method, of class Generic_BigDecimal.
     */
    public void testMax() {
        System.out.println("testMax");
        testMax_test1();
        testMax_test2();
    }

    public void testMax_test1() {
        System.out.println("testMax_test1");
        BigDecimal x = null;
        BigDecimal y = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.00000000000000000000000000000000000000000000001");
        y = new BigDecimal("0.00000000000000000000000000000000000000000000002");
        expResult = new BigDecimal(
                "0.00000000000000000000000000000000000000000000002");
        result = Generic_BigDecimal.max(x, y);
        printTestMax(x, y, result);
        assertEquals(expResult, result);
    }

    public void testMax_test2() {
        System.out.println("testMax_test2");
        BigDecimal x = null;
        BigDecimal y = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.00000000000000000000000000000000000000000000001");
        y = new BigDecimal("-0.0000000000000000000000000000000000000000000002");
        expResult = new BigDecimal(
                "0.00000000000000000000000000000000000000000000001");
        result = Generic_BigDecimal.max(x, y);
        printTestMax(x, y, result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testGetMax()
     *
     * @param x
     * @param y
     * @param result
     */
    private void printTestMax(
            BigDecimal x,
            BigDecimal y,
            BigDecimal result) {
        System.out.println("x " + x);
        System.out.println("y " + y);
        System.out.println("result " + result);
    }

    /**
     * Test of min method, of class Generic_BigDecimal.
     */
    public void testMin() {
        System.out.println("testMin");
        testMin_test1();
    }

    public void testMin_test1() {
        System.out.println("testMin_test1");
        BigDecimal x = null;
        BigDecimal y = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.00000000000000000000000000000000000000000000001");
        y = new BigDecimal("0.00000000000000000000000000000000000000000000002");
        expResult = new BigDecimal(
                "0.00000000000000000000000000000000000000000000001");
        result = Generic_BigDecimal.min(x, y);
        printTestMin(x, y, result);
        assertEquals(expResult, result);
    }

    public void testMin_test2() {
        System.out.println("testMin_test2");
        BigDecimal x = null;
        BigDecimal y = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.00000000000000000000000000000000000000000000001");
        y = new BigDecimal("-0.0000000000000000000000000000000000000000000002");
        expResult = new BigDecimal(
                "-0.0000000000000000000000000000000000000000000002");
        result = Generic_BigDecimal.min(x, y);
        printTestMin(x, y, result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testGetMin()
     *
     * @param x
     * @param y
     * @param result
     */
    private void printTestMin(
            BigDecimal x,
            BigDecimal y,
            BigDecimal result) {
        System.out.println("x " + x);
        System.out.println("y " + y);
        System.out.println("result " + result);
    }

    /**
     * Test of positionSignificantDigit method, of class Generic_BigDecimal.
     */
    public void testPositionSignificantDigit() {
        System.out.println("testPositionSignificantDigit");
        testPositionSignificantDigit_test1();
        testPositionSignificantDigit_test2();
        testPositionSignificantDigit_test3();
        testPositionSignificantDigit_test4();
        testPositionSignificantDigit_test5();
        testPositionSignificantDigit_test6();
        testPositionSignificantDigit_test7();
        testPositionSignificantDigit_test8();
    }

    public void testPositionSignificantDigit_test1() {
        System.out.println("testPositionSignificantDigit_test1");
        BigDecimal x = null;
        int expResult = 0;
        int result;
        x = new BigDecimal("100000");
        expResult = 6;
        result = Generic_BigDecimal.positionSignificantDigit(x);
        printTestPositionSignificantDigit(x, result);
        assertEquals(expResult, result);
    }

    public void testPositionSignificantDigit_test2() {
        System.out.println("testPositionSignificantDigit_test2");
        BigDecimal x = null;
        int expResult = 0;
        int result;
        x = new BigDecimal("1000000000000000000000000000000000000000000000000");
        expResult = 49;
        result = Generic_BigDecimal.positionSignificantDigit(x);
        printTestPositionSignificantDigit(x, result);
        assertEquals(expResult, result);
    }

    public void testPositionSignificantDigit_test3() {
        System.out.println("testPositionSignificantDigit_test3");
        BigDecimal x = null;
        int expResult = 0;
        int result;
        x = new BigDecimal("0.1234");
        expResult = -1;
        result = Generic_BigDecimal.positionSignificantDigit(x);
        printTestPositionSignificantDigit(x, result);
        assertEquals(expResult, result);
    }

    public void testPositionSignificantDigit_test4() {
        System.out.println("testPositionSignificantDigit_test4");
        BigDecimal x = null;
        int expResult = 0;
        int result;
        x = new BigDecimal("0.00000000000000000000000000000000001234567891011");
        expResult = -35;
        result = Generic_BigDecimal.positionSignificantDigit(x);
        printTestPositionSignificantDigit(x, result);
        assertEquals(expResult, result);
    }

    public void testPositionSignificantDigit_test5() {
        System.out.println("testPositionSignificantDigit_test5");
        BigDecimal x = null;
        int expResult = 0;
        int result;
        x = new BigDecimal("-100000");
        expResult = 6;
        result = Generic_BigDecimal.positionSignificantDigit(x);
        printTestPositionSignificantDigit(x, result);
        assertEquals(expResult, result);
    }

    public void testPositionSignificantDigit_test6() {
        System.out.println("testPositionSignificantDigit_test6");
        BigDecimal x = null;
        int expResult = 0;
        int result;
        x = new BigDecimal(
                "-1000000000000000000000000000000000000000000000000");
        expResult = 49;
        result = Generic_BigDecimal.positionSignificantDigit(x);
        printTestPositionSignificantDigit(x, result);
        assertEquals(expResult, result);
    }

    public void testPositionSignificantDigit_test7() {
        System.out.println("testPositionSignificantDigit_test7");
        BigDecimal x = null;
        int expResult = 0;
        int result;
        x = new BigDecimal("-0.1234");
        expResult = -1;
        result = Generic_BigDecimal.positionSignificantDigit(x);
        printTestPositionSignificantDigit(x, result);
        assertEquals(expResult, result);
    }

    public void testPositionSignificantDigit_test8() {
        System.out.println("testPositionSignificantDigit_test8");
        BigDecimal x = null;
        int expResult = 0;
        int result;
        x = new BigDecimal(
                "-0.00000000000000000000000000000000001234567891011");
        expResult = -35;
        result = Generic_BigDecimal.positionSignificantDigit(x);
        printTestPositionSignificantDigit(x, result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testPositionSignificantDigit()
     *
     * @param x
     * @param result
     */
    private void printTestPositionSignificantDigit(
            BigDecimal x,
            int result) {
        System.out.println("x " + x);
        System.out.println("result " + result);
    }

    /**
     * Test of floorSignificantDigit method, of class Generic_BigDecimal.
     */
    public void testFloorSignificantDigit() {
        System.out.println("testFloorSignificantDigit");
        testFloorSignificantDigit_test1();
    }

    public void testFloorSignificantDigit_test1() {
        System.out.println("testFloorSignificantDigit_test1");
        BigDecimal x = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.0001");
        expResult = new BigDecimal("0.0001");
        result = Generic_BigDecimal.floorSignificantDigit(x);
        assertEquals(expResult, result);
    }

    public void testFloorSignificantDigit_test2() {
        System.out.println("testFloorSignificantDigit_test2");
        BigDecimal x = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.00012");
        expResult = new BigDecimal("0.0001");
        result = Generic_BigDecimal.floorSignificantDigit(x);
        assertEquals(expResult, result);
    }

    public void testFloorSignificantDigit_test3() {
        System.out.println("testFloorSignificantDigit_test3");
        BigDecimal x = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.0009");
        expResult = new BigDecimal("0.0009");
        result = Generic_BigDecimal.floorSignificantDigit(x);
        assertEquals(expResult, result);
    }

    public void testFloorSignificantDigit_test4() {
        System.out.println("testFloorSignificantDigit_test4");
        BigDecimal x = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("1.00099");
        expResult = new BigDecimal("1");
        result = Generic_BigDecimal.floorSignificantDigit(x);
        assertEquals(expResult, result);
    }

    public void testFloorSignificantDigit_test5() {
        System.out.println("testFloorSignificantDigit_test5");
        BigDecimal x = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("10008798.00099");
        //expResult = new BigDecimal("10000000");
        expResult = new BigDecimal("1E+7");
        result = Generic_BigDecimal.floorSignificantDigit(x);
        assertEquals(expResult, result);
    }

    public void testFloorSignificantDigit_test6() {
        System.out.println("testFloorSignificantDigit_test6");
        BigDecimal x = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("-1.00099");
        expResult = new BigDecimal("-1");
        result = Generic_BigDecimal.floorSignificantDigit(x);
        assertEquals(expResult, result);
    }

    public void testFloorSignificantDigit_test7() {
        System.out.println("testFloorSignificantDigit_test7");
        BigDecimal x = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("-10008798.00099");
        //expResult = new BigDecimal("-20000000");
        expResult = new BigDecimal("-1E+7");
        result = Generic_BigDecimal.floorSignificantDigit(x);
        assertEquals(expResult, result);
    }

    public void testFloorSignificantDigit_test8() {
        System.out.println("testFloorSignificantDigit_test8");
        BigDecimal x = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("-0.00099");
        expResult = new BigDecimal("-0.0009");
        result = Generic_BigDecimal.floorSignificantDigit(x);
        assertEquals(expResult, result);
    }

    public void testFloorSignificantDigit_test9() {
        System.out.println("testFloorSignificantDigit_test9");
        BigDecimal x = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("-0.99");
        expResult = new BigDecimal("-0.9");
        result = Generic_BigDecimal.floorSignificantDigit(x);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of printTestFloorSignificantDigit()
     *
     * @param x
     * @param result
     */
    private void printTestFloorSignificantDigit(
            BigDecimal x,
            int result) {
        System.out.println("x " + x);
        System.out.println("result " + result);
    }

    /**
     * Test of ceilingSignificantDigit method, of class Generic_BigDecimal.
     */
    public void testCeilingSignificantDigit() {
        System.out.println("testCeilingSignificantDigit");
        testCeilingSignificantDigit_test1();
        testCeilingSignificantDigit_test2();
        testCeilingSignificantDigit_test3();
        testCeilingSignificantDigit_test4();
        testCeilingSignificantDigit_test5();
        testCeilingSignificantDigit_test6();
        testCeilingSignificantDigit_test7();
        testCeilingSignificantDigit_test8();
        testCeilingSignificantDigit_test9();
    }

    public void testCeilingSignificantDigit_test1() {
        System.out.println("testCeilingSignificantDigit_test1");
        BigDecimal x = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.0001");
        expResult = new BigDecimal("0.0002");
        result = Generic_BigDecimal.ceilingSignificantDigit(x);
        printTestCeilingSignificantDigit(
                x,
                result);
        assertEquals(expResult, result);
    }

    public void testCeilingSignificantDigit_test2() {
        System.out.println("testCeilingSignificantDigit_test2");
        BigDecimal x = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.00012");
        expResult = new BigDecimal("0.0002");
        result = Generic_BigDecimal.ceilingSignificantDigit(x);
        printTestCeilingSignificantDigit(
                x,
                result);
        assertEquals(expResult, result);
    }

    public void testCeilingSignificantDigit_test3() {
        System.out.println("testCeilingSignificantDigit_test3");
        BigDecimal x = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.0009");
        expResult = new BigDecimal("0.001");
        result = Generic_BigDecimal.ceilingSignificantDigit(x);
        printTestCeilingSignificantDigit(
                x,
                result);
        assertEquals(expResult, result);
    }

    public void testCeilingSignificantDigit_test4() {
        System.out.println("testCeilingSignificantDigit_test4");
        BigDecimal x = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("1.00099");
        expResult = new BigDecimal("2");
        result = Generic_BigDecimal.ceilingSignificantDigit(x);
        printTestCeilingSignificantDigit(
                x,
                result);
        assertEquals(expResult, result);
    }

    public void testCeilingSignificantDigit_test5() {
        System.out.println("testCeilingSignificantDigit_test5");
        BigDecimal x = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("10008798.00099");
        //expResult = new BigDecimal("20000000");
        expResult = new BigDecimal("2E+7");
        result = Generic_BigDecimal.ceilingSignificantDigit(x);
        printTestCeilingSignificantDigit(
                x,
                result);
        assertEquals(expResult, result);
    }

    public void testCeilingSignificantDigit_test6() {
        System.out.println("testCeilingSignificantDigit_test6");
        BigDecimal x = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("-1.00099");
        expResult = new BigDecimal("-2");
        result = Generic_BigDecimal.ceilingSignificantDigit(x);
        printTestCeilingSignificantDigit(
                x,
                result);
        assertEquals(expResult, result);
    }

    public void testCeilingSignificantDigit_test7() {
        System.out.println("testCeilingSignificantDigit_test7");
        BigDecimal x = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("-10008798.00099");
        //expResult = new BigDecimal("-10000000");
        expResult = new BigDecimal("-2E+7");
        result = Generic_BigDecimal.ceilingSignificantDigit(x);
        printTestCeilingSignificantDigit(
                x,
                result);
        assertEquals(expResult, result);
    }

    public void testCeilingSignificantDigit_test8() {
        System.out.println("testCeilingSignificantDigit_test8");
        BigDecimal x = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("-0.00099");
        expResult = new BigDecimal("-0.001");
        result = Generic_BigDecimal.ceilingSignificantDigit(x);
        printTestCeilingSignificantDigit(
                x,
                result);
        assertEquals(expResult, result);
    }

    public void testCeilingSignificantDigit_test9() {
        System.out.println("testCeilingSignificantDigit_test9");
        BigDecimal x = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("-0.99");
        expResult = new BigDecimal("-1");
        result = Generic_BigDecimal.ceilingSignificantDigit(x);
        printTestCeilingSignificantDigit(
                x,
                result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testCeilingSignificantDigit()
     *
     * @param testNumber
     * @param x
     * @param result
     */
    private void printTestCeilingSignificantDigit(
            BigDecimal x,
            BigDecimal result) {
        System.out.println("x " + x);
        System.out.println("result " + result);
    }

    /**
     * Test of root.
     */
    public void testRoot() {
        testRootUnscaled1Precision1_2args(); // Test passes
        testRoot_4args_1(); //@TODO Not all tests complete in a reasonable time frame so some have been commented out for the time being.
        testRoot_4args_2(); //@TODO Not all tests complete in a reasonable time frame so some have been commented out for the time being.
    }

    public void testRootUnscaled1Precision1_2args() {
        System.out.println("testPowerPrecision1_2args");
        testRootUnscaled1Precision1_2args_test1();
    }

    public void testRootUnscaled1Precision1_2args_test1() {
        BigDecimal x = new BigDecimal("100");
        int y = 2;
        BigDecimal expResult = null;
        BigDecimal result = null;
        int decimalPrecision = 10;
        expResult = new BigDecimal(BigInteger.TEN);
        expResult = expResult.setScale(decimalPrecision);
        result = Generic_BigDecimal.rootUnscaled1Precision1(
                x, y,
                decimalPrecision);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of rootRoundIfNecessary method, of class Generic_BigDecimal.
     */
    public void testRoot_4args_1() {
        System.out.println("testRoot_4args_1");
        testRoot_4args_1_test1(); // Test passes
        testRoot_4args_1_test2(); // Test passes
        testRoot_4args_1_test3(); // Test passes
        testRoot_4args_1_test4(); // Test passes
        testRoot_4args_1_test5(); // Test passes
        testRoot_4args_1_test6(); // Test passes
        //testRoot_4args_1_test7(); // Tests does not complete in a reasonable time frame
        //testRoot_4args_1_test8(); // Tests does not complete in a reasonable time frame
        //testRoot_4args_1_test9(); // Tests does not complete in a reasonable time frame
    }

    public void testRoot_4args_1_test1() {
        System.out.println("testRoot_4args_1_test1");
        BigDecimal x = null;
        BigInteger root = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.25");
        root = new BigInteger("42");
        decimalPlaces = 10;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("0.9675317785");
        result = Generic_BigDecimal.rootRoundIfNecessary(
                x, root, decimalPlaces, a_RoundingMode);
        printTestRoot_4args_1(
                x,
                root,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testRoot_4args_1_test2() {
        System.out.println("testRoot_4args_1_test2");
        BigDecimal x = null;
        BigInteger root = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("27");
        root = new BigInteger("3");
        decimalPlaces = 10;
        a_RoundingMode = RoundingMode.HALF_UP;
        //expResult = new BigDecimal("3");
        expResult = new BigDecimal("3.0000000000");
        result = Generic_BigDecimal.rootRoundIfNecessary(
                x, root, decimalPlaces, a_RoundingMode);
        printTestRoot_4args_1(
                x,
                root,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testRoot_4args_1_test3() {
        System.out.println("testRoot_4args_1_test3");
        BigDecimal x = null;
        BigInteger root = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("8904831940328.25");
        root = new BigInteger("100023");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
//        expResult = new BigDecimal(
//                "1.000298152025337254460647917046876546344432360719824792024188"
//                + "0690659179835221951205476639783947640616");
        expResult = new BigDecimal(
                "1.000298152025337254460647917046876546344432360719824792024188"
                + "0690659179835221951205476639783947640617");
        result = Generic_BigDecimal.rootRoundIfNecessary(
                x, root, decimalPlaces, a_RoundingMode);
        printTestRoot_4args_1(
                x,
                root,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testRoot_4args_1_test4() {
        System.out.println("testRoot_4args_1_test4");
        BigDecimal x = null;
        BigInteger root = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.25");
        root = new BigInteger("100023");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.999986140340178623210350534074937443141133112706862977788610"
                + "0664594081621639848435662325435814102018");
        result = Generic_BigDecimal.rootRoundIfNecessary(
                x, root, decimalPlaces, a_RoundingMode);
        printTestRoot_4args_1(
                x,
                root,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testRoot_4args_1_test5() {
        System.out.println("testRoot_4args_1_test5");
        BigDecimal x = null;
        BigInteger root = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.999");
        root = new BigInteger("100023");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.999999989997297335815964171495970213458904791728888003317236"
                + "9473900240303824000659869633098129663322");
        result = Generic_BigDecimal.rootRoundIfNecessary(
                x, root, decimalPlaces, a_RoundingMode);
        printTestRoot_4args_1(
                x,
                root,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testRoot_4args_1_test6() {
        System.out.println("testRoot_4args_1_test6");
        BigDecimal x = null;
        BigInteger root = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("89048.25");
        root = new BigInteger("100023");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
//        expResult = new BigDecimal(
//                "1.015243902337169733771715514244796218250514955824391661165307"
//                + "1501461496931644677321800994560628165067");
         expResult = new BigDecimal(
                 "1.00011394962121160039192193569934499772696785109598019812727"
                 + "58749782805563101995300728186461240097898");
        result = Generic_BigDecimal.rootRoundIfNecessary(
                x, root, decimalPlaces, a_RoundingMode);
        printTestRoot_4args_1(
                x,
                root,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testRoot_4args_1_test7() {
        System.out.println("testRoot_4args_1_test7");
        BigDecimal x = null;
        BigInteger root = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("8904831940328.25");
        root = new BigInteger("1000000000");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "1.000000029817615604530522955297464844042643450238720000794888"
                + "829957679681379258243392341722104230672");
        result = Generic_BigDecimal.rootRoundIfNecessary(
                x, root, decimalPlaces, a_RoundingMode);
        printTestRoot_4args_1(
                x,
                root,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testRoot_4args_1_test8() {
        System.out.println("testRoot_4args_1_test8");
        BigDecimal x = null;
        BigInteger root = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.25");
        root = new BigInteger("1000000000");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.999999998613705639841015408557905737253169342556579813124573"
                + "7217463304587103469885523969834565259117");
        result = Generic_BigDecimal.rootRoundIfNecessary(
                x, root, decimalPlaces, a_RoundingMode);
        printTestRoot_4args_1(
                x,
                root,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testRoot_4args_1_test9() {
        System.out.println("testRoot_4args_1_test9");
        BigDecimal x = null;
        BigInteger root = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.999");
        root = new BigInteger("1000000000");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.999999999998999499666416967000315767959920576358096366321598"
                + "7363152842107633912159255642659280381426");
        result = Generic_BigDecimal.rootRoundIfNecessary(
                x, root, decimalPlaces, a_RoundingMode);
        printTestRoot_4args_1(
                x,
                root,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testRoot_4args_1()
     *
     * @param x
     * @param root
     * @param decimalPlaces
     * @param a_RoundingMode
     * @param result
     */
    public void printTestRoot_4args_1(
            BigDecimal x,
            BigInteger root,
            int decimalPlaces,
            RoundingMode a_RoundingMode,
            BigDecimal result) {
        System.out.println("x " + x);
        System.out.println("root " + root);
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("a_RoundingMode " + a_RoundingMode);
        System.out.println("result " + result);
    }

    /**
     * Test of rootRoundIfNecessary method, of class Generic_BigDecimal.
     */
    public void testRoot_4args_2() {
        System.out.println("testRoot_4args_2");
        testRoot_4args_2_test1(); // Test passes
        testRoot_4args_2_test2(); // Test passes
        testRoot_4args_2_test3(); // Test passes
        testRoot_4args_2_test4(); // Test passes
        testRoot_4args_2_test5(); // Test passes
        testRoot_4args_2_test6(); // Test passes
        //testRoot_4args_2_test7(); //@TODO This test either gets stuck or would take an age to complete.
        //testRoot_4args_2_test8(); //@TODO This test either gets stuck or would take an age to complete.
        //testRoot_4args_2_test9(); //@TODO This test either gets stuck or would take an age to complete.
        testRoot_4args_2_test10(); // Test passes
        testRoot_4args_2_test11(); // Test passes
        testRoot_4args_2_test12(); // Test passes
    }

    public void testRoot_4args_2_test1() {
        System.out.println("testRoot_4args_2_test1");
        BigDecimal x = null;
        int root = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.25");
        root = 42;
        decimalPlaces = 10;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("0.9675317785");
        result = Generic_BigDecimal.rootRoundIfNecessary(
                x, root, decimalPlaces, a_RoundingMode);
        printTestRoot_4args_2(
                x,
                root,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testRoot_4args_2_test2() {
        System.out.println("testRoot_4args_2_test2");
        BigDecimal x = null;
        int root = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("27");
        root = 3;
        decimalPlaces = 10;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("3.0000000000");
        //expResult = new BigDecimal("3");
        result = Generic_BigDecimal.rootRoundIfNecessary(
                x, root, decimalPlaces, a_RoundingMode);
        printTestRoot_4args_2(
                x,
                root,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testRoot_4args_2_test3() {
        System.out.println("testRoot_4args_2_test3");
        BigDecimal x = null;
        int root = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("8904831940328.25");
        root = 100023;
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "1.000298152025337254460647917046876546344432360719824792024188"
                + "0690659179835221951205476639783947640617");
        result = Generic_BigDecimal.rootRoundIfNecessary(
                x, root, decimalPlaces, a_RoundingMode);
        printTestRoot_4args_2(
                x,
                root,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testRoot_4args_2_test4() {
        System.out.println("testRoot_4args_2_test4");
        BigDecimal x = null;
        int root = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.25");
        root = 100023;
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.999986140340178623210350534074937443141133112706862977788610"
                + "0664594081621639848435662325435814102018");
        result = Generic_BigDecimal.rootRoundIfNecessary(
                x, root, decimalPlaces, a_RoundingMode);
        printTestRoot_4args_2(
                x,
                root,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testRoot_4args_2_test5() {
        System.out.println("testRoot_4args_2_test5");
        BigDecimal x = null;
        int root = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.999");
        root = 100023;
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.999999989997297335815964171495970213458904791728888003317236"
                + "9473900240303824000659869633098129663322");
        result = Generic_BigDecimal.rootRoundIfNecessary(
                x, root, decimalPlaces, a_RoundingMode);
        printTestRoot_4args_2(
                x,
                root,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testRoot_4args_2_test6() {
        System.out.println("testRoot_4args_2_test6");
        BigDecimal x = null;
        int root = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("89048.25");
        root = 100023;
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
//        expResult = new BigDecimal(
//                "1.015243902337169733771715514244796218250514955824391661165307"
//                + "1501461496931644677321800994560628165067");
        expResult = new BigDecimal(
                "1.000113949621211600391921935699344997726967851095980198127275"
                + "8749782805563101995300728186461240097898");
        result = Generic_BigDecimal.rootRoundIfNecessary(
                x, root, decimalPlaces, a_RoundingMode);
        printTestRoot_4args_2(
                x,
                root,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testRoot_4args_2_test7() {
        System.out.println("testRoot_4args_2_test7");
        //@TODO This test either gets stuck or would take an age to complete.
        BigDecimal x = null;
        int root = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("8904831940328.25");
        root = 1000000000;
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "1.000000029817615604530522955297464844042643450238720000794888"
                + "829957679681379258243392341722104230672");
        result = Generic_BigDecimal.rootRoundIfNecessary(
                x, root, decimalPlaces, a_RoundingMode);
        printTestRoot_4args_2(
                x,
                root,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testRoot_4args_2_test8() {
        //@TODO This test either gets stuck or would take an age to complete.
        System.out.println("testRoot_4args_2_test8");
        BigDecimal x = null;
        int root = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.25");
        root = 1000000000;
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.999999998613705638454721047973532813135945294316465347162595"
                + "0794816212223235738396849891311358363145");
//        // Expected result before rounding implemented. Is this a result of 
//        // error?
//        expResult = new BigDecimal(
//                "0.999999998613705639841015408557905737253169342556579813124573"
//                + "7217463304587103469885523969834565259117");
        result = Generic_BigDecimal.rootRoundIfNecessary(
                x, root, decimalPlaces, a_RoundingMode);
        printTestRoot_4args_2(
                x,
                root,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testRoot_4args_2_test9() {
        //@TODO This test either gets stuck or would take an age to complete.
        System.out.println("testRoot_4args_2_test9");
        BigDecimal x = null;
        int root = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.999");
        root = 1000000000;
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.999999999998999499665416466665732735126112562031740141406732"
                + "3221950570597106160577635181243915018588");
//        // Expected result before rounding implemented. Is this a result of 
//        // error?
//        expResult = new BigDecimal(
//                "0.999999999998999499666416967000315767959920576358096366321598"
//                + "7363152842107633912159255642659280381426");
        result = Generic_BigDecimal.rootRoundIfNecessary(
                x, root, decimalPlaces, a_RoundingMode);
        printTestRoot_4args_2(
                x,
                root,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testRoot_4args_2_test10() {
        System.out.println("testRoot_4args_2_test10");
        BigDecimal x = null;
        int root = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.1");
        root = 356;
        decimalPlaces = 3;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.994");
        result = Generic_BigDecimal.rootRoundIfNecessary(
                x, root, decimalPlaces, a_RoundingMode);
        printTestRoot_4args_2(
                x,
                root,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testRoot_4args_2_test11() {
        System.out.println("testRoot_4args_2_test11");
        BigDecimal x = null;
        int root = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.1");
        root = 356;
        decimalPlaces = 4;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.9936");
        result = Generic_BigDecimal.rootRoundIfNecessary(
                x, root, decimalPlaces, a_RoundingMode);
        printTestRoot_4args_2(
                x,
                root,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testRoot_4args_2_test12() {
        System.out.println("testRoot_4args_2_test12");
        BigDecimal x = null;
        int root = 0;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.1");
        root = 356;
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.993552936417354226471692858641777111504937855829958649327917"
                + "9203022268144882986474329796822248724061");
        result = Generic_BigDecimal.rootRoundIfNecessary(
                x, root, decimalPlaces, a_RoundingMode);
        printTestRoot_4args_2(
                x,
                root,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testRoot_4args_2()
     *
     * @param x
     * @param root
     * @param decimalPlaces
     * @param a_RoundingMode
     * @param result
     */
    public void printTestRoot_4args_2(
            BigDecimal x,
            int root,
            int decimalPlaces,
            RoundingMode a_RoundingMode,
            BigDecimal result) {
        System.out.println("x " + x);
        System.out.println("root " + root);
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("a_RoundingMode " + a_RoundingMode);
        System.out.println("result " + result);
    }

    public void testExp() {
        System.out.println("testExp");
        testExp_test1();
        testExp_test2();
        testExp_test3();
        testExp_test4();
        testExp_test5();
        testExp_test6();
        testExp_test7();
        testExp_test8();
        testExp_test9();
        testExp_test10();
        testExp_test11();
    }

    public void testExp_test1() {
        System.out.println("testExp_test1");
        BigDecimal y = null;
        Generic_BigDecimal a_Generic_BigDecimal = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        y = new BigDecimal("1");
        a_Generic_BigDecimal = new Generic_BigDecimal();
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "2.718281828459045235360287471352662497757247093699959574966967"
                + "6277240766303535475945713821785251664274");
        result = Generic_BigDecimal.exp(
                y,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode);
        printTestExp(
                y,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testExp_test2() {
        System.out.println("testExp_test2");
        BigDecimal y = null;
        Generic_BigDecimal a_Generic_BigDecimal = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        y = new BigDecimal("1");
        a_Generic_BigDecimal = new Generic_BigDecimal();
        decimalPlaces = 1000;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "2.718281828459045235360287471352662497757247093699959574966967"
                + "627724076630353547594571382178525166427427466391932003059921"
                + "817413596629043572900334295260595630738132328627943490763233"
                + "829880753195251019011573834187930702154089149934884167509244"
                + "761460668082264800168477411853742345442437107539077744992069"
                + "551702761838606261331384583000752044933826560297606737113200"
                + "709328709127443747047230696977209310141692836819025515108657"
                + "463772111252389784425056953696770785449969967946864454905987"
                + "931636889230098793127736178215424999229576351482208269895193"
                + "668033182528869398496465105820939239829488793320362509443117"
                + "301238197068416140397019837679320683282376464804295311802328"
                + "782509819455815301756717361332069811250996181881593041690351"
                + "598888519345807273866738589422879228499892086805825749279610"
                + "484198444363463244968487560233624827041978623209002160990235"
                + "304369941849146314093431738143640546253152096183690888707016"
                + "768396424378140592714563549061303107208510383750510115747704"
                + "1718986106873969655212671546889570350354");
        result = Generic_BigDecimal.exp(
                y,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode);
        printTestExp(
                y,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testExp_test3() {
        System.out.println("testExp_test3");
        BigDecimal y = null;
        Generic_BigDecimal a_Generic_BigDecimal = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        y = new BigDecimal("1");
        a_Generic_BigDecimal = new Generic_BigDecimal();
        decimalPlaces = 1001;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "2.718281828459045235360287471352662497757247093699959574966967"
                + "627724076630353547594571382178525166427427466391932003059921"
                + "817413596629043572900334295260595630738132328627943490763233"
                + "829880753195251019011573834187930702154089149934884167509244"
                + "761460668082264800168477411853742345442437107539077744992069"
                + "551702761838606261331384583000752044933826560297606737113200"
                + "709328709127443747047230696977209310141692836819025515108657"
                + "463772111252389784425056953696770785449969967946864454905987"
                + "931636889230098793127736178215424999229576351482208269895193"
                + "668033182528869398496465105820939239829488793320362509443117"
                + "301238197068416140397019837679320683282376464804295311802328"
                + "782509819455815301756717361332069811250996181881593041690351"
                + "598888519345807273866738589422879228499892086805825749279610"
                + "484198444363463244968487560233624827041978623209002160990235"
                + "304369941849146314093431738143640546253152096183690888707016"
                + "768396424378140592714563549061303107208510383750510115747704"
                + "17189861068739696552126715468895703503540");
        result = Generic_BigDecimal.exp(
                y,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode);
        printTestExp(
                y,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testExp_test4() {
        System.out.println("testExp_test4");
        BigDecimal y = null;
        Generic_BigDecimal a_Generic_BigDecimal = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        y = new BigDecimal("1");
        a_Generic_BigDecimal = new Generic_BigDecimal();
        decimalPlaces = 2000;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "2.718281828459045235360287471352662497757247093699959574966967"
                + "627724076630353547594571382178525166427427466391932003059921"
                + "817413596629043572900334295260595630738132328627943490763233"
                + "829880753195251019011573834187930702154089149934884167509244"
                + "761460668082264800168477411853742345442437107539077744992069"
                + "551702761838606261331384583000752044933826560297606737113200"
                + "709328709127443747047230696977209310141692836819025515108657"
                + "463772111252389784425056953696770785449969967946864454905987"
                + "931636889230098793127736178215424999229576351482208269895193"
                + "668033182528869398496465105820939239829488793320362509443117"
                + "301238197068416140397019837679320683282376464804295311802328"
                + "782509819455815301756717361332069811250996181881593041690351"
                + "598888519345807273866738589422879228499892086805825749279610"
                + "484198444363463244968487560233624827041978623209002160990235"
                + "304369941849146314093431738143640546253152096183690888707016"
                + "768396424378140592714563549061303107208510383750510115747704"
                + "171898610687396965521267154688957035035402123407849819334321"
                + "068170121005627880235193033224745015853904730419957777093503"
                + "660416997329725088687696640355570716226844716256079882651787"
                + "134195124665201030592123667719432527867539855894489697096409"
                + "754591856956380236370162112047742722836489613422516445078182"
                + "442352948636372141740238893441247963574370263755294448337998"
                + "016125492278509257782562092622648326277933386566481627725164"
                + "019105900491644998289315056604725802778631864155195653244258"
                + "698294695930801915298721172556347546396447910145904090586298"
                + "496791287406870504895858671747985466775757320568128845920541"
                + "334053922000113786300945560688166740016984205580403363795376"
                + "452030402432256613527836951177883863874439662532249850654995"
                + "886234281899707733276171783928034946501434558897071942586398"
                + "772754710962953741521115136835062752602326484728703920764310"
                + "059584116612054529703023647254929666938115137322753645098889"
                + "031360205724817658511806303644281231496550704751025446501172"
                + "721155519486685080036853228183152196003735625279449515828418"
                + "82947876108526398140");
        result = Generic_BigDecimal.exp(
                y,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode);
        printTestExp(
                y,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testExp_test5() {
        System.out.println("testExp_test5");
        BigDecimal y = null;
        Generic_BigDecimal a_Generic_BigDecimal = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        y = new BigDecimal("2");
        a_Generic_BigDecimal = new Generic_BigDecimal();
        decimalPlaces = 5;
        a_RoundingMode = RoundingMode.HALF_UP;
        //expResult = new BigDecimal("7.38095");
        expResult = new BigDecimal("7.38906");
        result = Generic_BigDecimal.exp(
                y,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode);
        printTestExp(
                y,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testExp_test6() {
        System.out.println("testExp_test6");
        BigDecimal y = null;
        Generic_BigDecimal a_Generic_BigDecimal = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        y = new BigDecimal("2");
        a_Generic_BigDecimal = new Generic_BigDecimal();
        decimalPlaces = 1001;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "7.389056098930650227230427460575007813180315570551847324087127"
                + "822522573796079057763384312485079121794773753161265478866123"
                + "884603692781273374478392213398077774900122895607410753702391"
                + "330947550682086581820269647868208404220982255234875742462541"
                + "414679928129331888070763301019337899740729986960095303307515"
                + "320818823684694793029913558771445683123923272764602588339996"
                + "461212849285209678905138824663987122813726861064735626379295"
                + "182227842948434586135287693866985752001549960148075071971293"
                + "369418851997228882636255971941095866191479871504328397693264"
                + "610235116312389990010513783406764498663892685615821864215577"
                + "248492011193531621171951731747269796829345199850541848631971"
                + "356859470229125573983561105149793681450277644807642985104182"
                + "117055944191787683471285276497809713462504140235242158740938"
                + "668254271570392645296404550628778001311092650138483345302646"
                + "363141560471888117657942786348599076704527119372958723995987"
                + "073310814961253109770593530099050329681075421090877626308572"
                + "48500382787227614486674505649873858771575");
        result = Generic_BigDecimal.exp(
                y,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode);
        printTestExp(
                y,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testExp_test7() {
        System.out.println("testExp_test7");
        BigDecimal y = null;
        Generic_BigDecimal a_Generic_BigDecimal = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        y = new BigDecimal("2.1");
        a_Generic_BigDecimal = new Generic_BigDecimal();
        decimalPlaces = 10;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("8.1661699126");
        result = Generic_BigDecimal.exp(
                y,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode);
        printTestExp(
                y,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testExp_test8() {
        System.out.println("testExp_test8");
        BigDecimal y = null;
        Generic_BigDecimal a_Generic_BigDecimal = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        y = new BigDecimal("2.10111010101010101010101111");
        a_Generic_BigDecimal = new Generic_BigDecimal();
        decimalPlaces = 20;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("8.17524021958327462764");
        result = Generic_BigDecimal.exp(
                y,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode);
        printTestExp(
                y,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testExp_test9() {
        System.out.println("testExp_test9");
        BigDecimal y = null;
        Generic_BigDecimal a_Generic_BigDecimal = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        y = new BigDecimal("0.5");
        a_Generic_BigDecimal = new Generic_BigDecimal();
        decimalPlaces = 20;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("1.64872127070012814685");
        result = Generic_BigDecimal.exp(
                y,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode);
        printTestExp(
                y,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testExp_test10() {
        System.out.println("testExp_test10");
        BigDecimal y = null;
        Generic_BigDecimal a_Generic_BigDecimal = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        y = new BigDecimal("0.0000001");
        a_Generic_BigDecimal = new Generic_BigDecimal();
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "1.000000100000005000000166666670833333416666668055555575396825"
                + "6448412725970017912257498096039783583187");
        result = Generic_BigDecimal.exp(
                y,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode);
        printTestExp(
                y,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testExp_test11() {
        System.out.println("testExp_test11");
        BigDecimal y = null;
        Generic_BigDecimal a_Generic_BigDecimal = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        y = new BigDecimal("-0.0000001");
        a_Generic_BigDecimal = new Generic_BigDecimal();
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.999999900000004999999833333337499999916666668055555535714285"
                + "9623015845458554067460314955106642650045");
        result = Generic_BigDecimal.exp(
                y,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode);
        printTestExp(
                y,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testExp()
     *
     * @param y
     * @param a_Generic_BigDecimal
     * @param decimalPlaces
     * @param a_RoundingMode
     * @param result
     */
    private void printTestExp(
            BigDecimal y,
            Generic_BigDecimal a_Generic_BigDecimal,
            int decimalPlaces,
            RoundingMode a_RoundingMode,
            BigDecimal result) {
        System.out.println("y " + y);
        System.out.println("a_Generic_BigDecimal" + a_Generic_BigDecimal);
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("a_RoundingMode " + a_RoundingMode);
        System.out.println("result " + result);
    }

    public void testLn() {
        System.out.println("testLn");
        testLn_test1();
        testLn_test2();
        testLn_test3();
        testLn_test4();
        testLn_test5();
        testLn_test6();
    }

    public void testLn_test1() {
        System.out.println("testLn_test1");
        BigDecimal x = null;
        Generic_BigDecimal a_Generic_BigDecimal = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("1");
        a_Generic_BigDecimal = new Generic_BigDecimal();
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0E-100");
        result = Generic_BigDecimal.ln(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode);
        printTestLn(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testLn_test2() {
        System.out.println("testLn_test2");
        BigDecimal x = null;
        Generic_BigDecimal a_Generic_BigDecimal = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("2");
        a_Generic_BigDecimal = new Generic_BigDecimal();
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.693147180559945309417232121458176568075500134360255254120680"
                + "0094933936219696947156058633269964186875");
        result = Generic_BigDecimal.ln(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode);
        printTestLn(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testLn_test3() {
        System.out.println("testLn_test3");
        BigDecimal x = null;
        Generic_BigDecimal a_Generic_BigDecimal = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_Generic_BigDecimal = new Generic_BigDecimal();
        decimalPlaces = 100;
        x = new BigDecimal(a_Generic_BigDecimal.getEulerConstantToAMinimumDecimalPlacePrecision(decimalPlaces).toString());
        a_RoundingMode = RoundingMode.HALF_UP;
        //expResult = new BigDecimal("1");
        expResult = new BigDecimal(
                "1.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        result = Generic_BigDecimal.ln(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode);
        printTestLn(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testLn_test4() {
        System.out.println("testLn_test4");
        BigDecimal x = null;
        Generic_BigDecimal a_Generic_BigDecimal = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_Generic_BigDecimal = new Generic_BigDecimal();
        decimalPlaces = 100;
        x = new BigDecimal("3");
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "1.098612288668109691395245236922525704647490557822749451734694"
                + "3336374942932186089668736157548137320888");
        result = Generic_BigDecimal.ln(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode);
        printTestLn(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testLn_test5() {
        System.out.println("testLn_test5");
        BigDecimal x = null;
        Generic_BigDecimal a_Generic_BigDecimal = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_Generic_BigDecimal = new Generic_BigDecimal();
        decimalPlaces = 100;
        x = new BigDecimal("3000000000");
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "21.82187812561452084755716832908180357305740395548170623603464"
                + "54423456477803147812889975906006201167737");
        result = Generic_BigDecimal.ln(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode);
        printTestLn(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testLn_test6() {
        System.out.println("testLn_test6");
        BigDecimal x = null;
        Generic_BigDecimal a_Generic_BigDecimal = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        a_Generic_BigDecimal = new Generic_BigDecimal();
        decimalPlaces = 100;
        x = new BigDecimal("0.5");
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "-0.6931471805599453094172321214581765680755001343602552541206"
                + "800094933936219696947156058633269964186875");
        result = Generic_BigDecimal.ln(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode);
        printTestLn(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /*
     * For printing out individual tests of testLn() @param x @param
     * a_Generic_BigDecimal @param decimalPlaces @param a_RoundingMode @param
     * result
     */
    private void printTestLn(
            BigDecimal x,
            Generic_BigDecimal a_Generic_BigDecimal,
            int decimalPlaces,
            RoundingMode a_RoundingMode,
            BigDecimal result) {
        System.out.println("x " + x);
        System.out.println("a_Generic_BigDecimal" + a_Generic_BigDecimal);
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("a_RoundingMode " + a_RoundingMode);
        System.out.println("result " + result);
    }

    public void testTan() {
        System.out.println("testTan");
        Generic_BigDecimal a_Generic_BigDecimal = new Generic_BigDecimal(1000);
        BigDecimal x;
        int decimalPlaces;
        RoundingMode roundingMode;
        BigDecimal expResult = null;
        BigDecimal result = null;
        BigDecimal aPI1000 = a_Generic_BigDecimal.get_PI();
        decimalPlaces = 100;
        roundingMode = RoundingMode.HALF_UP;
        // Test PI/4
        x = Generic_BigDecimal.divideRoundIfNecessary(
                aPI1000,
                BigInteger.valueOf(4),
                decimalPlaces + 2,
                roundingMode);
        expResult = new BigDecimal("1");
        expResult.setScale(decimalPlaces);
        result = Generic_BigDecimal.tan(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                roundingMode);
        result = result.stripTrailingZeros();
        assertEquals(expResult, result);
        roundingMode = RoundingMode.HALF_UP;
    }

    public void testCos() {
        System.out.println("testCos");
        Generic_BigDecimal a_Generic_BigDecimal = new Generic_BigDecimal(1000);
        BigDecimal x;
        int decimalPlaces;
        RoundingMode roundingMode;
        BigDecimal expResult = null;
        BigDecimal result = null;
        BigDecimal aPI1000 = a_Generic_BigDecimal.get_PI();
        // Test 0
        x = new BigDecimal("0");
        decimalPlaces = 100;
        roundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("1");
        expResult = expResult.setScale(decimalPlaces);
        result = Generic_BigDecimal.cos(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                roundingMode);
        assertEquals(expResult, result);
        decimalPlaces = 30;
        roundingMode = RoundingMode.HALF_UP;
        // Test PI/4
        x = Generic_BigDecimal.divideRoundIfNecessary(
                aPI1000,
                BigInteger.valueOf(4),
                decimalPlaces + 2,
                roundingMode);
        expResult = new BigDecimal("0.707106781186547524400844362105");
        result = Generic_BigDecimal.cos(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                roundingMode);
        assertEquals(expResult, result);
        // Test 87*PI/180
        x = Generic_BigDecimal.divideRoundIfNecessary(
                aPI1000.multiply(BigDecimal.valueOf(87)),
                BigInteger.valueOf(180),
                decimalPlaces + 10,
                roundingMode);
        BigDecimal sqrt30 = Generic_BigDecimal.sqrt(
                BigDecimal.valueOf(30), decimalPlaces + 10, roundingMode);
        BigDecimal sqrt10 = Generic_BigDecimal.sqrt(
                BigDecimal.valueOf(10), decimalPlaces + 10, roundingMode);
        BigDecimal sqrt6 = Generic_BigDecimal.sqrt(
                BigDecimal.valueOf(6), decimalPlaces + 10, roundingMode);
        BigDecimal sqrt5 = Generic_BigDecimal.sqrt(
                BigDecimal.valueOf(5), decimalPlaces + 10, roundingMode);
        BigDecimal sqrt2 = Generic_BigDecimal.sqrt(
                BigDecimal.valueOf(2), decimalPlaces + 10, roundingMode);
        // (sqrt(30) + sqrt(10) + sqrt(20 + 4*sqrt(5)) - sqrt(6) - sqrt(2) - sqrt(60 + 12*sqrt(5)))/16
        BigDecimal splurge1 = Generic_BigDecimal.sqrt(
                BigDecimal.valueOf(60).add(BigDecimal.valueOf(12).multiply(sqrt5)), decimalPlaces + 10, roundingMode);
        // (sqrt(30) + sqrt(10) + sqrt(20 + 4*sqrt(5)) - sqrt(6) - sqrt(2) - splurge1)/16
        BigDecimal splurge2 = Generic_BigDecimal.sqrt(
                BigDecimal.valueOf(20).add(BigDecimal.valueOf(4).multiply(sqrt5)), decimalPlaces + 10, roundingMode);
        // (sqrt(30) + sqrt(10) + splurge2 - sqrt(6) - sqrt(2) - splurge1)/16
        BigDecimal splurge = sqrt30.add(sqrt10).add(splurge2).subtract(sqrt6).subtract(sqrt2).subtract(splurge1);
        expResult = Generic_BigDecimal.divideRoundIfNecessary(
                splurge,
                BigDecimal.valueOf(16),
                decimalPlaces,
                roundingMode);
        result = Generic_BigDecimal.cos(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                roundingMode);
        assertEquals(expResult, result);
        // Test 81*PI/180
        x = Generic_BigDecimal.divideRoundIfNecessary(
                aPI1000.multiply(BigDecimal.valueOf(81)),
                BigInteger.valueOf(180),
                decimalPlaces + 10,
                roundingMode);
        BigDecimal sqrt90 = Generic_BigDecimal.sqrt(
                BigDecimal.valueOf(90), decimalPlaces + 10, roundingMode);
        BigDecimal sqrt18 = Generic_BigDecimal.sqrt(
                BigDecimal.valueOf(18), decimalPlaces + 10, roundingMode);
//        BigDecimal sqrt10 = Generic_BigDecimal.sqrt(
//                BigDecimal.valueOf(10), decimalPlaces + 10, roundingMode);
//        BigDecimal sqrt20 = Generic_BigDecimal.sqrt(
//                BigDecimal.valueOf(20), decimalPlaces + 10, roundingMode);
//        BigDecimal sqrt5 = Generic_BigDecimal.sqrt(
//                BigDecimal.valueOf(5), decimalPlaces + 10, roundingMode);
//        BigDecimal sqrt2 = Generic_BigDecimal.sqrt(
//                BigDecimal.valueOf(2), decimalPlaces + 10, roundingMode);
        // (sqrt(90) + sqrt(18) + sqrt(10) + sqrt(2) - sqrt(20 - 4*sqrt(5)) - sqrt(180 - 36*sqrt(5)))/5
        splurge1 = Generic_BigDecimal.sqrt(
                BigDecimal.valueOf(20).subtract(BigDecimal.valueOf(4).multiply(sqrt5)), decimalPlaces + 10, roundingMode);
        // (sqrt(90) + sqrt(18) + sqrt(10) + sqrt(2) - splurge1 - sqrt(180 - 36*sqrt(5)))/5
        splurge2 = Generic_BigDecimal.sqrt(
                BigDecimal.valueOf(180).subtract(BigDecimal.valueOf(36).multiply(sqrt5)), decimalPlaces + 10, roundingMode);
        // (sqrt(90) + sqrt(18) + sqrt(10) + sqrt(2) - splurge1 - splurge2)/5
        splurge = sqrt90.add(sqrt18).add(sqrt10).add(sqrt2).subtract(splurge1).subtract(splurge2);
        expResult = Generic_BigDecimal.divideRoundIfNecessary(
                splurge,
                BigDecimal.valueOf(32),
                decimalPlaces,
                roundingMode);
        result = Generic_BigDecimal.cos(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                roundingMode);
        assertEquals(expResult, result);
        // Test 75*PI/180
        x = Generic_BigDecimal.divideRoundIfNecessary(
                aPI1000.multiply(BigDecimal.valueOf(75)),
                BigInteger.valueOf(180),
                decimalPlaces + 10,
                roundingMode);
//        BigDecimal sqrt6 = Generic_BigDecimal.sqrt(
//                BigDecimal.valueOf(6), decimalPlaces + 10, roundingMode);
//        BigDecimal sqrt2 = Generic_BigDecimal.sqrt(
//                BigDecimal.valueOf(2), decimalPlaces + 10, roundingMode);
        BigDecimal sqrt6subtractsqrt2 = sqrt6.subtract(sqrt2);
        expResult = Generic_BigDecimal.divideRoundIfNecessary(
                sqrt6subtractsqrt2,
                BigDecimal.valueOf(4),
                decimalPlaces,
                roundingMode);
        result = Generic_BigDecimal.cos(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                roundingMode);
        assertEquals(expResult, result);
        // Test 72*PI/180
        x = Generic_BigDecimal.divideRoundIfNecessary(
                aPI1000.multiply(BigDecimal.valueOf(72)),
                BigInteger.valueOf(180),
                decimalPlaces + 10,
                roundingMode);
//        BigDecimal sqrt5 = Generic_BigDecimal.sqrt(
//                BigDecimal.valueOf(5), decimalPlaces + 10, roundingMode);
        BigDecimal sqrtsubtract1 = sqrt5.subtract(BigDecimal.ONE);
        expResult = Generic_BigDecimal.divideRoundIfNecessary(
                sqrtsubtract1,
                BigDecimal.valueOf(4),
                decimalPlaces,
                roundingMode);
        result = Generic_BigDecimal.cos(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                roundingMode);
        assertEquals(expResult, result);
        // Test 54*PI/180
        x = Generic_BigDecimal.divideRoundIfNecessary(
                aPI1000.multiply(BigDecimal.valueOf(54)),
                BigInteger.valueOf(180),
                decimalPlaces + 10,
                roundingMode);
//        BigDecimal sqrt5 = Generic_BigDecimal.sqrt(
//                BigDecimal.valueOf(5), decimalPlaces + 10, roundingMode);
        BigDecimal tenSubtractTwoTimesSqrt5 = BigDecimal.valueOf(10).subtract(
                sqrt5.multiply(BigDecimal.valueOf(2)));
        expResult = Generic_BigDecimal.sqrt(
                tenSubtractTwoTimesSqrt5,
                decimalPlaces + 5,
                roundingMode);
        expResult = Generic_BigDecimal.divideRoundIfNecessary(
                expResult,
                BigDecimal.valueOf(4),
                decimalPlaces,
                roundingMode);
        result = Generic_BigDecimal.cos(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                roundingMode);
        assertEquals(expResult, result);
    }

    /**
     * sin(x) = cos((PI/2)-x) 2PI = 360 PI = 180 3PI/4 = 135 PI/3 = 120 PI/2 =
     * 90 PI/3 = 60 PI/4 = 45 PI/5 = 36 sqrt(10 - 2*sqrt(5))/4 PI/10 = 18
     * (sqrt(5) - 1)/4 PI/12 = 15 (sqrt(6) - sqrt(2))/4 PI/20 = 9 (sqrt(90) +
     * sqrt(18) + sqrt(10) + sqrt(2) - sqrt(20 - 4*sqrt(5)) - sqrt(180 -
     * 36*sqrt(5)))/32 PI/60 = 3 (sqrt(30) + sqrt(10) + sqrt(20 + 4*sqrt(5)) -
     * sqrt(6) - sqrt(2) - sqrt(60 + 12*sqrt(5))/16
     */
    public void testSin() {
        System.out.println("testSin");
        Generic_BigDecimal a_Generic_BigDecimal = new Generic_BigDecimal(1000);
        BigDecimal x;
        int decimalPlaces;
        RoundingMode roundingMode;
        BigDecimal expResult = null;
        BigDecimal result = null;
        BigDecimal aPI1000 = a_Generic_BigDecimal.get_PI();
        decimalPlaces = 30;
        roundingMode = RoundingMode.HALF_UP;
        // Test PI/4
        x = Generic_BigDecimal.divideRoundIfNecessary(
                aPI1000,
                BigInteger.valueOf(4),
                decimalPlaces + 2,
                roundingMode);
        expResult = new BigDecimal("0.707106781186547524400844362105");
        result = Generic_BigDecimal.sin(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                roundingMode);
        assertEquals(expResult, result);
        // Test PI/60
        x = Generic_BigDecimal.divideRoundIfNecessary(
                aPI1000,
                BigInteger.valueOf(60),
                decimalPlaces + 10,
                roundingMode);
        BigDecimal sqrt30 = Generic_BigDecimal.sqrt(
                BigDecimal.valueOf(30), decimalPlaces + 10, roundingMode);
        BigDecimal sqrt10 = Generic_BigDecimal.sqrt(
                BigDecimal.valueOf(10), decimalPlaces + 10, roundingMode);
        BigDecimal sqrt6 = Generic_BigDecimal.sqrt(
                BigDecimal.valueOf(6), decimalPlaces + 10, roundingMode);
        BigDecimal sqrt5 = Generic_BigDecimal.sqrt(
                BigDecimal.valueOf(5), decimalPlaces + 10, roundingMode);
        BigDecimal sqrt2 = Generic_BigDecimal.sqrt(
                BigDecimal.valueOf(2), decimalPlaces + 10, roundingMode);
        // (sqrt(30) + sqrt(10) + sqrt(20 + 4*sqrt(5)) - sqrt(6) - sqrt(2) - sqrt(60 + 12*sqrt(5)))/16
        BigDecimal splurge1 = Generic_BigDecimal.sqrt(
                BigDecimal.valueOf(60).add(BigDecimal.valueOf(12).multiply(sqrt5)), decimalPlaces + 10, roundingMode);
        // (sqrt(30) + sqrt(10) + sqrt(20 + 4*sqrt(5)) - sqrt(6) - sqrt(2) - splurge1)/16
        BigDecimal splurge2 = Generic_BigDecimal.sqrt(
                BigDecimal.valueOf(20).add(BigDecimal.valueOf(4).multiply(sqrt5)), decimalPlaces + 10, roundingMode);
        // (sqrt(30) + sqrt(10) + splurge2 - sqrt(6) - sqrt(2) - splurge1)/16
        BigDecimal splurge = sqrt30.add(sqrt10).add(splurge2).subtract(sqrt6).subtract(sqrt2).subtract(splurge1);
        expResult = Generic_BigDecimal.divideRoundIfNecessary(
                splurge,
                BigDecimal.valueOf(16),
                decimalPlaces,
                roundingMode);
        result = Generic_BigDecimal.sin(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                roundingMode);
        assertEquals(expResult, result);
        // Test PI/20
        x = Generic_BigDecimal.divideRoundIfNecessary(
                aPI1000,
                BigInteger.valueOf(20),
                decimalPlaces + 10,
                roundingMode);
        BigDecimal sqrt90 = Generic_BigDecimal.sqrt(
                BigDecimal.valueOf(90), decimalPlaces + 10, roundingMode);
        BigDecimal sqrt18 = Generic_BigDecimal.sqrt(
                BigDecimal.valueOf(18), decimalPlaces + 10, roundingMode);
//        BigDecimal sqrt10 = Generic_BigDecimal.sqrt(
//                BigDecimal.valueOf(10), decimalPlaces + 10, roundingMode);
//        BigDecimal sqrt20 = Generic_BigDecimal.sqrt(
//                BigDecimal.valueOf(20), decimalPlaces + 10, roundingMode);
//        BigDecimal sqrt5 = Generic_BigDecimal.sqrt(
//                BigDecimal.valueOf(5), decimalPlaces + 10, roundingMode);
//        BigDecimal sqrt2 = Generic_BigDecimal.sqrt(
//                BigDecimal.valueOf(2), decimalPlaces + 10, roundingMode);
        // (sqrt(90) + sqrt(18) + sqrt(10) + sqrt(2) - sqrt(20 - 4*sqrt(5)) - sqrt(180 - 36*sqrt(5)))/5
        splurge1 = Generic_BigDecimal.sqrt(
                BigDecimal.valueOf(20).subtract(BigDecimal.valueOf(4).multiply(sqrt5)), decimalPlaces + 10, roundingMode);
        // (sqrt(90) + sqrt(18) + sqrt(10) + sqrt(2) - splurge1 - sqrt(180 - 36*sqrt(5)))/5
        splurge2 = Generic_BigDecimal.sqrt(
                BigDecimal.valueOf(180).subtract(BigDecimal.valueOf(36).multiply(sqrt5)), decimalPlaces + 10, roundingMode);
        // (sqrt(90) + sqrt(18) + sqrt(10) + sqrt(2) - splurge1 - splurge2)/5
        splurge = sqrt90.add(sqrt18).add(sqrt10).add(sqrt2).subtract(splurge1).subtract(splurge2);
        expResult = Generic_BigDecimal.divideRoundIfNecessary(
                splurge,
                BigDecimal.valueOf(32),
                decimalPlaces,
                roundingMode);
        result = Generic_BigDecimal.sin(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                roundingMode);
        assertEquals(expResult, result);
        // Test PI/12
        x = Generic_BigDecimal.divideRoundIfNecessary(
                aPI1000,
                BigInteger.valueOf(12),
                decimalPlaces + 10,
                roundingMode);
//        BigDecimal sqrt6 = Generic_BigDecimal.sqrt(
//                BigDecimal.valueOf(6), decimalPlaces + 10, roundingMode);
//        BigDecimal sqrt2 = Generic_BigDecimal.sqrt(
//                BigDecimal.valueOf(2), decimalPlaces + 10, roundingMode);
        BigDecimal sqrt6subtractsqrt2 = sqrt6.subtract(sqrt2);
        expResult = Generic_BigDecimal.divideRoundIfNecessary(
                sqrt6subtractsqrt2,
                BigDecimal.valueOf(4),
                decimalPlaces,
                roundingMode);
        result = Generic_BigDecimal.sin(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                roundingMode);
        assertEquals(expResult, result);
        // Test PI/10
        x = Generic_BigDecimal.divideRoundIfNecessary(
                aPI1000,
                BigInteger.valueOf(10),
                decimalPlaces + 10,
                roundingMode);
//        BigDecimal sqrt5 = Generic_BigDecimal.sqrt(
//                BigDecimal.valueOf(5), decimalPlaces + 10, roundingMode);
        BigDecimal sqrtsubtract1 = sqrt5.subtract(BigDecimal.ONE);
        expResult = Generic_BigDecimal.divideRoundIfNecessary(
                sqrtsubtract1,
                BigDecimal.valueOf(4),
                decimalPlaces,
                roundingMode);
        result = Generic_BigDecimal.sin(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                roundingMode);
        assertEquals(expResult, result);
        // Test PI/5
        x = Generic_BigDecimal.divideRoundIfNecessary(
                aPI1000,
                BigInteger.valueOf(5),
                decimalPlaces + 10,
                roundingMode);
//        BigDecimal sqrt5 = Generic_BigDecimal.sqrt(
//                BigDecimal.valueOf(5), decimalPlaces + 10, roundingMode);
        BigDecimal tenSubtractTwoTimesSqrt5 = BigDecimal.valueOf(10).subtract(
                sqrt5.multiply(BigDecimal.valueOf(2)));
        expResult = Generic_BigDecimal.sqrt(
                tenSubtractTwoTimesSqrt5,
                decimalPlaces + 5,
                roundingMode);
        expResult = Generic_BigDecimal.divideRoundIfNecessary(
                expResult,
                BigDecimal.valueOf(4),
                decimalPlaces,
                roundingMode);
        result = Generic_BigDecimal.sin(
                x,
                a_Generic_BigDecimal,
                decimalPlaces,
                roundingMode);
        assertEquals(expResult, result);
    }

    /**
     * Test of sqrt method, of class Generic_BigDecimal.
     */
    //@Test
    public void testSqrt() {
        testSqrt_test1();
        testSqrt_test2();
        testSqrt_test3();
        testSqrt_test4();
    }

    public void testSqrt_test1() {
        System.out.println("testSqrt_test1");
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("100");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("10");
        expResult = expResult.setScale(decimalPlaces);
        //expResult = BigDecimal.TEN;
        //expResult = new BigDecimal("1E+1");
        result = Generic_BigDecimal.sqrt(
                x,
                decimalPlaces,
                a_RoundingMode);
        // For some reason 1E+1 does not equate to 10 except logically!
        // This is probably a BigDecimal property/feature rather than a bug.
        // In BigDEcimal the value is stored as a String and although
        // numerically equivallent the String representations are different 
        // It is probably safer to use compareTo and test if result == 0.
        // Another way is to write out the BigDecimal forcing a default String
        // notation and always compare with the same.
        result = new BigDecimal(result.toPlainString());
// Code for illustrating 1E+1 not the same as 10 issue
//        BigDecimal ten1 = new BigDecimal("1E+1");
//        BigDecimal ten2 = new BigDecimal("10");
//        BigDecimal ten3 = BigDecimal.TEN;
//        BigDecimal ten4 = new BigDecimal("5").add(new BigDecimal("5"));
//        BigDecimal ten5 = Generic_BigDecimal.divide(
//                new BigDecimal("100"),
//                new BigDecimal("10"),
//                2,
//                RoundingMode.HALF_UP);
//        BigDecimal x = new BigDecimal("100");
//        int decimalPlaces = 100;
//        RoundingMode a_RoundingMode = RoundingMode.HALF_UP;
//        BigDecimal result = Generic_BigDecimal.sqrt(
//                x, new Generic_BigDecimal(),
//                decimalPlaces, a_RoundingMode);
//        System.out.println("result " + result);
//        System.out.println("result " + result.toPlainString());
//        System.out.println("ten1 " + ten1.toPlainString());
//        System.out.println("ten2 " + ten2.toPlainString());
//        System.out.println("ten3 " + ten3.toPlainString());
//        System.out.println("ten4 " + ten4.toPlainString());
//        System.out.println("ten5 " + ten5.toPlainString());
        printTestSqrt(
                x,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testSqrt_test2() {
        System.out.println("testSqrt_test2");
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("10000");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("100");
        expResult = expResult.setScale(decimalPlaces);
        //expResult = new BigDecimal("1E+2");
        result = Generic_BigDecimal.sqrt(
                x,
                decimalPlaces,
                a_RoundingMode);
        result = new BigDecimal(result.toPlainString());
        printTestSqrt(
                x,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testSqrt_test3() {
        System.out.println("testSqrt_test3");
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.01");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal("0.1");
        expResult = expResult.setScale(decimalPlaces);
        //expResult = new BigDecimal("1E+2");
        result = Generic_BigDecimal.sqrt(
                x,
                decimalPlaces,
                a_RoundingMode);
        result = new BigDecimal(result.toPlainString());
        printTestSqrt(
                x,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    public void testSqrt_test4() {
        System.out.println("testSqrt_test4");
        BigDecimal x = null;
        int decimalPlaces = 0;
        RoundingMode a_RoundingMode = null;
        BigDecimal expResult = null;
        BigDecimal result = null;
        x = new BigDecimal("0.000000001");
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        expResult = new BigDecimal(
                "0.000031622776601683793319988935444327185337195551393252168268"
                + "5750485279259443863923822134424810837930");
        //expResult = new BigDecimal("1E+2");
        result = Generic_BigDecimal.sqrt(
                x,
                decimalPlaces,
                a_RoundingMode);
        result = new BigDecimal(result.toPlainString());
        printTestSqrt(
                x,
                decimalPlaces,
                a_RoundingMode,
                result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testSqrt()
     *
     * @param x
     * @param decimalPlaces
     * @param a_Generic_BigDecimal
     * @param a_RoundingMode
     * @param result
     */
    private void printTestSqrt(
            BigDecimal x,
            int decimalPlaces,
            RoundingMode a_RoundingMode,
            BigDecimal result) {
        System.out.println("x " + x);
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("a_RoundingMode " + a_RoundingMode);
        System.out.println("result " + result);
    }

    /**
     * Test of randomUniformTest method, of class Generic_BigDecimal.
     */
    public void testRandomUniformTest() {
        System.out.println("testRandomUniformTest");
        testRandomUniformTest_test1();
        testRandomUniformTest_test2();
        testRandomUniformTest_test3();
        testRandomUniformTest_test4();
    }

    public void testRandomUniformTest_test1() {
        System.out.println("testRandomUniformTest_test1");
        Random a_Random = null;
        BigDecimal probability = null;
        int decimalPlaces;
        RoundingMode a_RoundingMode;
        boolean expResult = false;
        boolean result;
        long seed;
        seed = 0L;
        a_Random = new Random(seed);
//        // The following commented code reveals that the first 4096 consecutive
//        // tests for probability 0.5 return true!
//        probability = new BigDecimal("0.5");
//        boolean alternator = false;
//        for (long l = 0; l < 10000L; l++) {
//            a_Random = new Random(l);
//            result = Generic_BigDecimal.randomUniformTest(a_Random, probability);
//            if (!alternator){
//              if (result == alternator) {
//                    System.out.println("result changed to " + result + " on iteration " + l);
//                    alternator = true;
//                }
//            } else {
//              if (result == alternator) {
//                    System.out.println("result changed to " + result + " on iteration " + l);
//                    alternator = false;
//                }
//            }
//        }
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        probability = new BigDecimal("0.5");
        expResult = true;
        result = Generic_BigDecimal.randomUniformTest(
                a_Random,
                probability,
                decimalPlaces,
                a_RoundingMode);
        printTestRandomUniformTest(
                a_Random, probability, decimalPlaces, a_RoundingMode, result);
        assertEquals(expResult, result);
    }

    public void testRandomUniformTest_test2() {
        System.out.println("testRandomUniformTest_test2");
        Random a_Random = null;
        BigDecimal probability = null;
        int decimalPlaces;
        RoundingMode a_RoundingMode;
        boolean expResult = false;
        boolean result;
        long seed;
        seed = 0L;
        a_Random = new Random(seed);
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        probability = new BigDecimal("0.025");
        expResult = false;
        result = Generic_BigDecimal.randomUniformTest(
                a_Random,
                probability,
                decimalPlaces,
                a_RoundingMode);
        printTestRandomUniformTest(
                a_Random, probability, decimalPlaces, a_RoundingMode, result);
        assertEquals(expResult, result);
    }

    public void testRandomUniformTest_test3() {
        System.out.println("testRandomUniformTest_test3");
        Random a_Random = null;
        BigDecimal probability = null;
        int decimalPlaces;
        RoundingMode a_RoundingMode;
        boolean expResult = false;
        boolean result;
        long seed;
        seed = 0L;
        a_Random = new Random(seed);
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        probability = new BigDecimal("0.0125");
        expResult = false;
        result = Generic_BigDecimal.randomUniformTest(
                a_Random,
                probability,
                decimalPlaces,
                a_RoundingMode);
        printTestRandomUniformTest(
                a_Random, probability, decimalPlaces, a_RoundingMode, result);
        assertEquals(expResult, result);
    }

    public void testRandomUniformTest_test4() {
        System.out.println("testRandomUniformTest_test4");
        Random a_Random = null;
        BigDecimal probability = null;
        int decimalPlaces;
        RoundingMode a_RoundingMode;
        boolean expResult = false;
        boolean result;
        long seed;
        seed = 1234567L;
        a_Random = new Random(seed);
        decimalPlaces = 100;
        a_RoundingMode = RoundingMode.HALF_UP;
        probability = new BigDecimal("0.92");
        expResult = true;
        result = Generic_BigDecimal.randomUniformTest(
                a_Random,
                probability,
                decimalPlaces,
                a_RoundingMode);
        printTestRandomUniformTest(
                a_Random, probability, decimalPlaces, a_RoundingMode, result);
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testRandomUniformTest()
     *
     * @param a_Random
     * @param probability
     * @param result
     */
    private void printTestRandomUniformTest(
            Random a_Random,
            BigDecimal probability,
            int decimalPlaces,
            RoundingMode a_RoundingMode,
            boolean result) {
        System.out.println("a_Random " + a_Random);
        System.out.println("probability " + probability);
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("a_RoundingMode " + a_RoundingMode);
        System.out.println("result " + result);
    }

//    /**
//     * Test of getRandom method, of class Generic_BigDecimal.
//     */
//    @Test
//    public void testGetRandom_4args() {
//        System.out.println("getRandom");
//        Random a_Random = null;
//        int decimalPlaces = 0;
//        BigDecimal lowerLimit = null;
//        BigDecimal upperLimit = null;
//        BigDecimal expResult = null;
//        BigDecimal result = null;
//        long seed;
//        // Test 1
//        System.out.println("Test 1");
//            seed = 0L;
//            a_Random = new Random(seed);
//            decimalPlaces = 10;
//            lowerLimit = new BigDecimal("0");
//         upperLimit = new BigDecimal("1");
//            result = Generic_BigDecimal.getRandom(
//                    a_Random, decimalPlaces, lowerLimit, upperLimit);
//            System.out.println("result " + result);
//
//        assertEquals(expResult, result);
//   }
    /**
     * Test of getRandom method, of class Generic_BigDecimal.
     */
    public void test_getRandom() {
        System.out.println("test_getRandom");
        test_getRandom_test1();
        test_getRandom_test2();
    }

    public void test_getRandom_test1() {
        System.out.println("test_getRandom_test1");
        int decimalPlaces = 0;
        BigDecimal expResult = null;
        BigDecimal result = null;
        long seed;
        int numberOfRandomInstances;
        Generic_BigDecimal a_Generic_BigDecimal = new Generic_BigDecimal();
        seed = 0L;
        // For multiple uses the initialised Random[]
        // a_Generic_BigDecimal._RandomArray it is intended to be re-used.
        numberOfRandomInstances = 1000;
        a_Generic_BigDecimal.init_RandomArrayMinLength(
                numberOfRandomInstances,
                seed);
        decimalPlaces = 1000;
        expResult = new BigDecimal(
                "0.058427164938625116053827514073627362958427064938847316953827"
                + "504950497261958417066150847306953817281750397261948349286150"
                + "837206959584170649286150069538175049726172619483170639288372"
                + "069528175039403972519483160561408372059428172716403962519473"
                + "392851408362059494831605392851400594281740397251625194731605"
                + "382783620594271640394938625184731695514073620584271627064938"
                + "615084733827504973629584847316953827504905842716493862516150"
                + "847306953817736295842706493849286150837206955049726195841706"
                + "170639286140837228175039726194831706392851408372281740397261"
                + "948394831605392851400594281740396251625184731605382773620594"
                + "271640394938625184730695514073629584271616053827514073622716"
                + "403962519473847306953827504905842716493862516150847306952817"
                + "736295841706493839286150837206955049726195841706069538175049"
                + "726127064938615084738372069528175039958417064928615061408372"
                + "059428177261948317063928392851408362059440397251948316050594"
                + "281740397251170639286140837283620594271640399483160539285140"
                + "5140736205842716625194731605382738275049");
//        BigDecimal smallProbability = new BigDecimal("0.0001");
//        for (int i = 0; i < 10000; i ++) {
//            result = Generic_BigDecimal.getRandom(
//                    a_Generic_BigDecimal, decimalPlaces);
//            if (result.compareTo(smallProbability) == -1){
//                System.out.println("result " + result.toPlainString());
//            }
//        }
        result = Generic_BigDecimal.getRandom(
                a_Generic_BigDecimal, decimalPlaces);
        printTestGetRandom_Random_int(
                a_Generic_BigDecimal, decimalPlaces, result);
        assertEquals(expResult, result);
    }

    public void test_getRandom_test2() {
        System.out.println("test_getRandom_test2");
        int decimalPlaces = 0;
        BigDecimal expResult = null;
        BigDecimal result = null;
        decimalPlaces = 10;
        System.out.println("Test 2");
        BigDecimal lowerBound = BigDecimal.ZERO;
        BigDecimal upperBound = BigDecimal.ONE;
        Generic_BigInteger a_Generic_BigInteger = new Generic_BigInteger();
        result = Generic_BigDecimal.getRandom(
                a_Generic_BigInteger,
                decimalPlaces,
                lowerBound,
                upperBound);
        printTestGetRandom_4args(
                decimalPlaces, lowerBound, upperBound, result);
        expResult = new BigDecimal("0.4932604312");
        assertEquals(expResult, result);
    }

    /**
     * For printing out individual tests of testGetRandom_4args()
     *
     * @param a_Random
     * @param probability
     * @param result
     */
    private void printTestGetRandom_Random_int(
            Generic_BigDecimal a_Generic_BigDecimal,
            int decimalPlaces,
            BigDecimal result) {
        System.out.println("a_Generic_BigDecimal " + a_Generic_BigDecimal);
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("result " + result);
    }

    /**
     * For printing out individual tests of testGetRandom_4args()
     *
     * @param a_Random
     * @param probability
     * @param result
     */
    private void printTestGetRandom_4args(
            int decimalPlaces,
            BigDecimal lowerLimit,
            BigDecimal upperLimit,
            BigDecimal result) {
        System.out.println("decimalPlaces " + decimalPlaces);
        System.out.println("lowerLimit " + lowerLimit);
        System.out.println("upperLimit " + upperLimit);
        System.out.println("result " + result);
    }
}
