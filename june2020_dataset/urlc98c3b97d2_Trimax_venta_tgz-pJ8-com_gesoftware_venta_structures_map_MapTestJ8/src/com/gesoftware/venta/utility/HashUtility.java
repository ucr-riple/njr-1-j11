package com.gesoftware.venta.utility;

import com.gesoftware.venta.logging.LoggingUtility;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

/**
 *  Hash utility class definition
 **/
public final class HashUtility {
    private final static Set<Character> c_DeniedCharacters = new HashSet<Character>();

    /* Initialize denied characters */
    static {
        final char[] deniedCharacters = ".,/\\?!@#$%^&*()-=_+:;[]{}<>\"'` ".toCharArray();
        for (final char character : deniedCharacters)
            c_DeniedCharacters.add(character);
    }

    /* *
     * METHOD: Converts bytes array decimal representation into hex
     * RETURN: String with hex representation of decimal characters array
     *  PARAM: [IN] data - array of bytes
     * AUTHOR: Eliseev Dmitry
     * */
    private static String convertToHex(final byte[] data) {
        final StringBuilder sb = new StringBuilder();

        for (final byte aData : data) {
            int halfByte = (aData >>> 4) & 0x0F;
            int twoParts = 0;
            do {
                if ((0 <= halfByte) && (halfByte <= 9))
                    sb.append((char) ('0' + halfByte));
                else
                    sb.append((char) ('a' + (halfByte - 10)));
                halfByte = aData & 0x0F;
            } while (twoParts++ < 1);
        }

        /* That's it */
        return sb.toString();
    } /* End of 'HashUtility::convertToHex' method */

    /* *
     * METHOD: Implementation of SHA1 hash function
     * RETURN: SHA1 hash value
     *  PARAM: [IN] text - string to calculate SHA1 hash
     * AUTHOR: Eliseev Dmitry
     * */
    private static String getHash(final String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("UTF-8"), 0, text.length());
        return convertToHex(md.digest());
    } /* End of 'HashUtility::getHash' method */

    /* *
     * METHOD: Calculate string hash using standard algorithm
     * RETURN: Hash of the string
     *  PARAM: [IN] string - reference to string to calculate hash
     * AUTHOR: Eliseev Dmitry
     * */
    public static String generateHash(final String string) {
        try {
            return getHash(string);
        } catch (final NoSuchAlgorithmException e) {
            LoggingUtility.error("Can't calculate hash by string: " + string);
        } catch (final UnsupportedEncodingException e) {
            LoggingUtility.error("Can't calculate hash by string: " + string);
        }

        return null;
    } /* End of 'HashUtility::generateHash' method */

    /* *
     * METHOD: Removes all not-alphabet characters from string and converts it to lower case
     * RETURN: Cleaned string
     *  PARAM: [IN] string - reference to string to calculate hash
     * AUTHOR: Eliseev Dmitry
     * */
    public static String cleanString(final String string) {
        final StringBuilder stringBuilder = new StringBuilder();

        final char[] characters = string.toLowerCase().toCharArray();
        for (final char character : characters)
            if (!c_DeniedCharacters.contains(character))
                stringBuilder.append(character);

        return stringBuilder.toString();
    } /* End of 'HashUtility::cleanString' method */
} /* End of 'HashUtility' class */