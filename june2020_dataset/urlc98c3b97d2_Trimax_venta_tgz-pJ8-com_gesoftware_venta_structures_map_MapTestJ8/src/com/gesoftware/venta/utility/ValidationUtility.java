package com.gesoftware.venta.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ValidationUtility class definition
 */
public final class ValidationUtility {
    /* Valid e-mail pattern */
    private static final Pattern c_PatternEMail = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", Pattern.CASE_INSENSITIVE);

    /* *
     * METHOD: Checks e-mail using regular expression
     * RETURN: True if string is e-mail, False otherwise
     *  PARAM: [IN] email - e-mail candidate string
     * AUTHOR: Eliseev Dmitry
     * */
    private static boolean isEMailMatchesRegularExpression(final String email) {
        final Matcher matcher = c_PatternEMail.matcher(email);
        return matcher.find();
    } /* End of 'ValidationUtility::isEMailMatchesRegularExpression' method */

    /* *
     * METHOD: Checks string data
     * RETURN: True if string is empty (null or zero-length), False otherwise
     *  PARAM: [IN] string - string to validate
     * AUTHOR: Eliseev Dmitry
     * */
    public static boolean isEmpty(final String string) {
        return string == null || string.length() == 0;
    } /* End of 'ValidationUtility::isEmpty' method */

    /* *
     * METHOD: Checks e-mail
     * RETURN: True if string is e-mail, False otherwise
     *  PARAM: [IN] email - e-mail candidate string
     * AUTHOR: Eliseev Dmitry
     * */
    public static boolean isEMailValid(final String email) {
        return !isEmpty(email) && email.length() > 3 && isEMailMatchesRegularExpression(email);
    } /* End of 'ValidationUtility::isEMailValid' method */
} /* End of 'ValidationUtility' class */
