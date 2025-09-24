package com.gesoftware.venta.security.model;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Key class definition
 */
public final class Key implements Serializable {
    /* Key modulus */
    private final BigInteger modulus;

    /* Key exponent */
    private final BigInteger exponent;

    /* *
     * METHOD: Key class constructor
     *  PARAM: [IN] modulus  - key modulus
     *  PARAM: [IN] exponent - key exponent
     * AUTHOR: Dmitry Eliseev
     * */
    public Key(final BigInteger modulus, final BigInteger exponent) {
        this.exponent = exponent;
        this.modulus = modulus;
    } /* End of 'Key::Key' method */

    /* *
     * METHOD: Modulus getter
     * RETURN: Key's modulus
     * AUTHOR: Dmitry Eliseev
     * */
    public final BigInteger getModulus() {
        return modulus;
    } /* End of 'Key::getModulus' method */

    /* *
     * METHOD: Exponent getter
     * RETURN: Key's exponent
     * AUTHOR: Dmitry Eliseev
     * */
    public final BigInteger getExponent() {
        return exponent;
    } /* End of 'Key::getExponent' method */
} /* End of 'Key' class */
