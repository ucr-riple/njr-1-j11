/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.generic.data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;
import uk.ac.leeds.ccg.andyt.generic.lang.Generic_StaticString;

/**
 * For handling UK Postcodes. The general format of a postcode is given in: 
 * https://en.wikipedia.org/wiki/Postcodes_in_the_United_Kingdom
 * @author geoagdt
 */
public class Generic_UKPostcode_Handler {

    /**
     * For storing a set of all NAA String combinations where: N is a numerical
     * integer from 0 to 9 inclusive; and, Both A are alphabetical characters
     * from _AtoZ_not_CIKMOV.
     */
    protected static TreeSet<String> _NAA;
    /**
     * For storing (in lower case) all the letters of the Alphabet except C, I,
     * K, M, O, and V.
     */
    protected static TreeSet<String> _AtoZ_not_CIKMOV;
    /**
     * For storing (in lower case) all the letters of the Alphabet except Q, V,
     * and X.
     */
    protected static TreeSet<String> _AtoZ_not_QVX;
    /**
     * For storing (in lower case) all the letters of the Alphabet except I, J,
     * and Z.
     */
    protected static TreeSet<String> _AtoZ_not_IJZ;
    /**
     * For storing (in lower case) the letters of the Alphabet A, B, C, D, E, F,
     * G, H, J, K, S, T, U, and W.
     */
    protected static TreeSet<String> _ABCDEFGHJKSTUW;
    /**
     * For storing (in lower case) the letters of the Alphabet A, B, E, H, M, N,
     * P, R, V, W, X, and Y.
     */
    protected static TreeSet<String> _ABEHMNPRVWXY;
    /**
     * For storing the digits 0 to 9 as Strings
     */
    protected static TreeSet<String> _0to9;

    protected static final String s = "";
    protected static final String sSpace = " ";
    protected static final String sNAA = "NAA";
    protected static final String sNA = "NA";
    protected static final String sN = "N";
    protected static final String sAANN = "AANN";
    protected static final String sAANA = "AANA";
    protected static final String sANN = "ANN";
    protected static final String sANA = "ANA";
    protected static final String sAAN = "AAN";
    protected static final String sAN = "AN";

    /**
     * Creates a new Generic_UKPostcode_Handler
     */
    public Generic_UKPostcode_Handler() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Generic_UKPostcode_Handler().run();
    }

    public void run() {
        String postcode;
        postcode = "LS2 9JT";
        Object[] postcodeForm;
        postcodeForm = getPostcodeForm(postcode);
        Integer postcodeFormType;
        postcodeFormType = (Integer) postcodeForm[0];
        String postcodeFormTypeDescription;
        postcodeFormTypeDescription = (String) postcodeForm[1];
        System.out.println("postcodeFormType " + postcodeFormType);
        System.out.println("postcodeFormTypeDescription " + postcodeFormTypeDescription);
    }

    /**
     *
     * @param postcode
     * @return True iff postcode has a valid Postcode Form.
     */
    public boolean isValidPostcodeForm(String postcode) {
        if (postcode == null) {
            return false;
        }
        while (postcode.contains(sSpace)) {
            postcode = postcode.replaceAll(sSpace, s);
        }
        int length = postcode.length();
        if (length < 5) {
            return false;
        }
        String firstPartPostcode;
        firstPartPostcode = postcode.substring(0, length - 3);
        String firstPartPostcodeType;
        firstPartPostcodeType = getFirstPartPostcodeType(firstPartPostcode);
        if (firstPartPostcodeType.equalsIgnoreCase(s)) {
            return false;
        }
        String secondPartPostcode;
        secondPartPostcode = postcode.substring(length - 3, length);
        String secondPartPostcodeType;
        secondPartPostcodeType = getSecondPartPostcodeType(secondPartPostcode);
        if (secondPartPostcodeType.equalsIgnoreCase(s)) {
            return false;
        }
        Object[] postcodeForm;
        postcodeForm = getPostcodeForm(postcode);
        return ((Integer) postcodeForm[0]) == 4;
    }

    /**
     *
     * @param postcode
     * @return
     */
    public Object[] getPostcodeForm(String postcode) {
        Object[] result;
        result = new Object[2];
        // Do any modification?
        while (postcode.contains(sSpace)) {
            postcode = postcode.replaceAll(sSpace, s);
        }
        int length = postcode.length();
        if (length < 5) {
            result[0] = 0;
            result[1] = "Length < 5";
            return result;
        }
        String firstPartPostcode;
        firstPartPostcode = postcode.substring(0, length - 3);
        String secondPartPostcode;
        secondPartPostcode = postcode.substring(length - 3, length);
        String firstPartPostcodeType;
        firstPartPostcodeType = getFirstPartPostcodeType(firstPartPostcode);
        String secondPartPostcodeType;
        secondPartPostcodeType = getSecondPartPostcodeType(secondPartPostcode);
        firstPartPostcodeTypes = getFirstPartPostcodeTypes();
        if (firstPartPostcodeTypes.contains(firstPartPostcodeType)) {
            if (secondPartPostcodeType.equalsIgnoreCase(sNAA)) {
                result[0] = 4;
                result[1] = "Correct form of firstPartPostcode (" + firstPartPostcode + "): " + firstPartPostcodeType + "."
                        + " Correct form of secondPartPostcode (" + secondPartPostcode + "): " + secondPartPostcodeType + ".";
            } else {
                result[0] = 3;
                result[1] = "Correct form of firstPartPostcode (" + firstPartPostcode + "): " + firstPartPostcodeType + "."
                        + " Incorrect form of secondPartPostcode (" + secondPartPostcode + ").";
            }
        } else if (secondPartPostcodeType.equalsIgnoreCase(sNAA)) {
            result[0] = 1;
            result[1] = "Incorrect form of firstPartPostcode (" + firstPartPostcode + ")."
                    + " Incorrect form of secondPartPostcode (" + secondPartPostcode + ").";
        } else {
            result[0] = 2;
            result[1] = "Incorrect form of firstPartPostcode (" + firstPartPostcode + ")."
                    + " Correct form of secondPartPostcode (" + secondPartPostcode + "): " + secondPartPostcodeType + ".";
        }
        return result;
    }

    /**
     * Check SecondPart Postcode is in the format "NAA", "NA" or "N" where a
     * stands for an alphabetical character and n stands for a numerical
     * character.
     *
     * @param secondPartPostcode
     * @return
     */
    public String getSecondPartPostcodeType(String secondPartPostcode) {
        if (secondPartPostcode.length() < 4) {
            switch (secondPartPostcode.length()) {
                case 3: {
                    String _0 = secondPartPostcode.substring(0, 1);
                    String _1 = secondPartPostcode.substring(1, 2);
                    String _2 = secondPartPostcode.substring(2, 3);
                    if (get_0to9().contains(_0)
                            && get_AtoZ_not_CIKMOV().contains(_1)
                            && get_AtoZ_not_CIKMOV().contains(_2)) {
                        return sNAA;
                    }
                    break;
                }
                case 2: {
                    String _0 = secondPartPostcode.substring(0, 1);
                    String _1 = secondPartPostcode.substring(1, 2);
                    if (get_0to9().contains(_0)
                            && get_AtoZ_not_CIKMOV().contains(_1)) {
                        return sNA;
                    }
                    break;
                }
                case 1: {
                    String _0 = secondPartPostcode.substring(0, 1);
                    if (get_0to9().contains(_0)) {
                        return sN;
                    }
                    break;
                }
                default:
                    break;
            }
        }
        return s;
    }

    public HashSet<String> firstPartPostcodeTypes;

    public HashSet<String> getFirstPartPostcodeTypes() {
        if (firstPartPostcodeTypes == null) {
            firstPartPostcodeTypes = new HashSet<String>();
            firstPartPostcodeTypes.add(sAN);
            firstPartPostcodeTypes.add(sANN);
            firstPartPostcodeTypes.add(sAAN);
            firstPartPostcodeTypes.add(sAANN);
            firstPartPostcodeTypes.add(sANA);
            firstPartPostcodeTypes.add(sAANA);
        }
        return firstPartPostcodeTypes;
    }

    /**
     * If firstPartPostcode is not a valid first part for a postcode return an
     * empty string otherwise return "AANN", "AANA", "ANN", "ANA", "AAN", "AN"
     * respectively where A stands for an alphabetical character and N stands
     * for a numerical character.
     *
     * @param firstPartPostcode
     * @return
     */
    public String getFirstPartPostcodeType(String firstPartPostcode) {
        // Resolve type from firstPartPostcode
//        String unresolvedMessage = firstPartPostcode + " is not recognised as a first part of a postcode";
        // Return a String or null (AANN, AANA, ANN, ANA, AAN, AN)
        if (firstPartPostcode.length() > 4 || firstPartPostcode.length() < 2) {
//            System.err.println(unresolvedMessage);
            return s;
        }
        if (firstPartPostcode.length() == 4) {
            String _0 = firstPartPostcode.substring(0, 1);
            String _1 = firstPartPostcode.substring(1, 2);
            String _2 = firstPartPostcode.substring(2, 3);
            String _3 = firstPartPostcode.substring(3, 4);
            if (get_AtoZ_not_QVX().contains(_0)
                    && get_AtoZ_not_IJZ().contains(_1)
                    && get_0to9().contains(_2)
                    && get_0to9().contains(_3)) {
                return sAANN;
            } else if (get_AtoZ_not_QVX().contains(_0)
                    && get_AtoZ_not_IJZ().contains(_1)
                    && get_0to9().contains(_2)
                    && get_ABEHMNPRVWXY().contains(_3)) {
                return sAANA;
            } else {
//                    System.err.println(unresolvedMessage);
                return s;
            }
        }
        if (firstPartPostcode.length() == 3) {
            String _0 = firstPartPostcode.substring(0, 1);
            String _1 = firstPartPostcode.substring(1, 2);
            String _2 = firstPartPostcode.substring(2, 3);
            if (get_AtoZ_not_QVX().contains(_0)) {
                if (get_0to9().contains(_1)) {
                    if (get_0to9().contains(_2)) {
                        return sANN;
                    } else if (get_ABCDEFGHJKSTUW().contains(_2)) {
                        return sANA;
                    } else {
//                            System.err.println(unresolvedMessage);
                        return s;
                    }
                } else if (get_AtoZ_not_IJZ().contains(_1)) {
                    if (get_0to9().contains(_2)) {
                        return sAAN;
                    } else if (get_ABCDEFGHJKSTUW().contains(_2)) {
                        return sANA;
                    } else {
//                                System.err.println(unresolvedMessage);
                        return s;
                    }
                }
            } else {
//                System.err.println(unresolvedMessage);
                return s;
            }
        }
        if (firstPartPostcode.length() == 2) {
            String _0 = firstPartPostcode.substring(0, 1);
            String _1 = firstPartPostcode.substring(1, 2);
            if (get_AtoZ_not_QVX().contains(_0)) {
                if (get_0to9().contains(_1)) {
                    return sAN;
                }
            }
        }
//        System.err.println(unresolvedMessage);
        return "";
    }

    protected TreeSet<String> get_NAA() {
        if (_NAA == null) {
            _NAA = new TreeSet<String>();
            _AtoZ_not_CIKMOV = get_AtoZ_not_CIKMOV();
            Iterator ite1 = _AtoZ_not_CIKMOV.iterator();
            String a0;
            String a1;
            int n0;
            while (ite1.hasNext()) {
                a0 = (String) ite1.next();
                Iterator ite2 = _AtoZ_not_CIKMOV.iterator();
                while (ite2.hasNext()) {
                    a1 = (String) ite2.next();
                    for (n0 = 0; n0 < 10; n0++) {
                        _NAA.add(Integer.toString(n0) + a0 + a1);
                    }
                }
            }
        }
        return _NAA;
    }

    protected TreeSet<String> get_AtoZ_not_QVX() {
        if (_AtoZ_not_QVX == null) {
            _AtoZ_not_QVX = Generic_StaticString.getUpperCaseStringAlphabet_TreeSet();
            _AtoZ_not_QVX.remove("Q");
            _AtoZ_not_QVX.remove("V");
            _AtoZ_not_QVX.remove("X");
        }
        return _AtoZ_not_QVX;
    }

    protected TreeSet<String> get_AtoZ_not_IJZ() {
        if (_AtoZ_not_IJZ == null) {
            _AtoZ_not_IJZ = Generic_StaticString.getUpperCaseStringAlphabet_TreeSet();
            _AtoZ_not_IJZ.remove("I");
            _AtoZ_not_IJZ.remove("J");
            _AtoZ_not_IJZ.remove("Z");
        }
        return _AtoZ_not_IJZ;
    }

    protected TreeSet<String> get_ABCDEFGHJKSTUW() {
        if (_ABCDEFGHJKSTUW == null) {
            _ABCDEFGHJKSTUW = new TreeSet<String>();
            _ABCDEFGHJKSTUW.add("A");
            _ABCDEFGHJKSTUW.add("B");
            _ABCDEFGHJKSTUW.add("C");
            _ABCDEFGHJKSTUW.add("D");
            _ABCDEFGHJKSTUW.add("E");
            _ABCDEFGHJKSTUW.add("F");
            _ABCDEFGHJKSTUW.add("G");
            _ABCDEFGHJKSTUW.add("H");
            _ABCDEFGHJKSTUW.add("J");
            _ABCDEFGHJKSTUW.add("K");
            _ABCDEFGHJKSTUW.add("S");
            _ABCDEFGHJKSTUW.add("T");
            _ABCDEFGHJKSTUW.add("U");
            _ABCDEFGHJKSTUW.add("W");
        }
        return _ABCDEFGHJKSTUW;
    }

    protected TreeSet<String> get_ABEHMNPRVWXY() {
        if (_ABEHMNPRVWXY == null) {
            _ABEHMNPRVWXY = new TreeSet<String>();
            _ABEHMNPRVWXY.add("A");
            _ABEHMNPRVWXY.add("B");
            _ABEHMNPRVWXY.add("E");
            _ABEHMNPRVWXY.add("H");
            _ABEHMNPRVWXY.add("M");
            _ABEHMNPRVWXY.add("N");
            _ABEHMNPRVWXY.add("P");
            _ABEHMNPRVWXY.add("R");
            _ABEHMNPRVWXY.add("V");
            _ABEHMNPRVWXY.add("W");
            _ABEHMNPRVWXY.add("X");
            _ABEHMNPRVWXY.add("Y");
        }
        return _ABEHMNPRVWXY;
    }

    protected TreeSet<String> get_AtoZ_not_CIKMOV() {
        if (_AtoZ_not_CIKMOV == null) {
            _AtoZ_not_CIKMOV = Generic_StaticString.getUpperCaseStringAlphabet_TreeSet();
            _AtoZ_not_CIKMOV.remove("C");
            _AtoZ_not_CIKMOV.remove("I");
            _AtoZ_not_CIKMOV.remove("K");
            _AtoZ_not_CIKMOV.remove("M");
            _AtoZ_not_CIKMOV.remove("O");
            _AtoZ_not_CIKMOV.remove("V");
        }
        return _AtoZ_not_CIKMOV;
    }

    protected TreeSet<String> get_0to9() {
        if (_0to9 == null) {
            _0to9 = new TreeSet<String>();
            _0to9.add("0");
            _0to9.add("1");
            _0to9.add("2");
            _0to9.add("3");
            _0to9.add("4");
            _0to9.add("5");
            _0to9.add("6");
            _0to9.add("7");
            _0to9.add("8");
            _0to9.add("9");
        }
        return _0to9;
    }
}
