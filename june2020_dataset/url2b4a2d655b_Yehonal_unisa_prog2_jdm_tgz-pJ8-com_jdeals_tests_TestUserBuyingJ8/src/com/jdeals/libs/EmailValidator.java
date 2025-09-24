/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.libs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Email validator using regular expressions.
 *
 * @author Giuseppe Ronca
 */
public class EmailValidator {

    /**
     * The pattern.
     */
    private Pattern pattern;

    /**
     * The matcher.
     */
    private Matcher matcher;

    /**
     * The Constant EMAIL_PATTERN.
     */
    private static final String EMAIL_PATTERN
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Instantiates a new email validator.
     */
    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    /**
     * Validate hex with regular expression.
     *
     * @param hex hex for validation
     * @return true valid hex, false invalid hex
     */
    public boolean validate(final String hex) {

        matcher = pattern.matcher(hex);
        return matcher.matches();

    }
}
