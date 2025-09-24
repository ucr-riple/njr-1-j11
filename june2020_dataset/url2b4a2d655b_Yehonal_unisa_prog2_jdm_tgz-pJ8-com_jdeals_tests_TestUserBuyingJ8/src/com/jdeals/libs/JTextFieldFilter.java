/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.libs;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * The Class JTextFieldFilter.
 */
public class JTextFieldFilter extends PlainDocument {

    /**
     * The Constant LOWERCASE.
     */
    public static final String LOWERCASE
            = "abcdefghijklmnopqrstuvwxyz";

    /**
     * The Constant UPPERCASE.
     */
    public static final String UPPERCASE
            = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * The Constant ALPHA.
     */
    public static final String ALPHA
            = LOWERCASE + UPPERCASE;

    /**
     * The Constant NUMERIC.
     */
    public static final String NUMERIC
            = "0123456789";

    /**
     * The Constant FLOAT.
     */
    public static final String FLOAT
            = NUMERIC + ".";

    /**
     * The Constant ALPHA_NUMERIC.
     */
    public static final String ALPHA_NUMERIC
            = ALPHA + NUMERIC;

    /**
     * The Constant DATE.
     */
    public static final String DATE
            = NUMERIC + "/";

    /**
     * The accepted chars.
     */
    protected String acceptedChars = null;

    /**
     * The negative accepted.
     */
    protected boolean negativeAccepted = false;

    /**
     * Instantiates a new j text field filter.
     */
    public JTextFieldFilter() {
        this(ALPHA_NUMERIC);
    }

    /**
     * Instantiates a new j text field filter.
     *
     * @param acceptedchars the acceptedchars
     */
    public JTextFieldFilter(String acceptedchars) {
        acceptedChars = acceptedchars;
    }

    /**
     * Sets the negative accepted.
     *
     * @param negativeaccepted the new negative accepted
     */
    public void setNegativeAccepted(boolean negativeaccepted) {
        if (acceptedChars.equals(NUMERIC)
                || acceptedChars.equals(FLOAT)
                || acceptedChars.equals(ALPHA_NUMERIC)) {
            negativeAccepted = negativeaccepted;
            acceptedChars += "-";
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.text.PlainDocument#insertString(int, java.lang.String, javax.swing.text.AttributeSet)
     */
    public void insertString(int offset, String str, AttributeSet attr)
            throws BadLocationException {
        if (str == null) {
            return;
        }

        if (acceptedChars.equals(UPPERCASE)) {
            str = str.toUpperCase();
        } else if (acceptedChars.equals(LOWERCASE)) {
            str = str.toLowerCase();
        }

        for (int i = 0; i < str.length(); i++) {
            if (acceptedChars.indexOf(str.valueOf(str.charAt(i))) == -1) {
                return;
            }
        }

        if (acceptedChars.equals(FLOAT)
                || (acceptedChars.equals(FLOAT + "-") && negativeAccepted)) {
            if (str.indexOf(".") != -1) {
                if (getText(0, getLength()).indexOf(".") != -1) {
                    return;
                }
            }
        }

        if (negativeAccepted && str.indexOf("-") != -1) {
            if (str.indexOf("-") != 0 || offset != 0) {
                return;
            }
        }

        super.insertString(offset, str, attr);
    }
}
