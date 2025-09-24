package com.gesoftware.venta.hashing;

/***
 * Base 64 decoding exception class definition
 ***/
public final class Base64DecoderException extends Exception {
    /* *
     * METHOD: Class constructor
     * AUTHOR: Eliseev Dmitry
     * */
    public Base64DecoderException() {
        super();
    } /* End of 'Base64DecoderException::Base64DecoderException' method */

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] s - message
     * AUTHOR: Eliseev Dmitry
     * */
    public Base64DecoderException(final String message) {
        super(message);
    } /* End of 'Base64DecoderException::Base64DecoderException' method */

    private static final long serialVersionUID = 1L;
} /* End of 'Base64DecoderException' class */
