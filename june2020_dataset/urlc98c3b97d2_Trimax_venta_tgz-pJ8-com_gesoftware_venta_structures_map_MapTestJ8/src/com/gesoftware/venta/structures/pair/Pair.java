package com.gesoftware.venta.structures.pair;

import java.io.Serializable;

/**
 * Pair class definition
 **/
public final class Pair<A, B> implements Serializable {
    private A m_First;
    private B m_Second;

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] a - first object
     *  PARAM: [IN] b - second object
     * AUTHOR: Eliseev Dmitry
     * */
    public Pair(final A first, final B second) {
        m_First = first;
        m_Second = second;
    } /* End of 'Pair::Pair' method */

    @Override
    public final int hashCode() {
        final int hashFirst  = m_First != null ? m_First.hashCode() : 0;
        final int hashSecond = m_Second != null ? m_Second.hashCode() : 0;

        return (hashFirst + hashSecond) * hashSecond + hashFirst;
    } /* End of 'Pair::hashCode' method */

    /* *
     * METHOD: Compares two objects using equality
     * RETURN: True if either objects or their references are equals or both of them are nulls, False otherwise
     *  PARAM: [IN] object1 - first object
     *  PARAM: [IN] object2 - second object
     * AUTHOR: Eliseev Dmitry
     * */
    private boolean equals(final Object object1, final Object object2) {
        return object1 == object2 || ((object1 != null) && (object2 != null) && object1.equals(object2));
    } /* End of 'Pair::equals' method */

    @Override
    public final boolean equals(final Object other) {
        return other instanceof Pair && equals(m_First, ((Pair) other).m_First) && equals(m_Second, ((Pair) other).m_Second);
    } /* End of 'Pair::equals' method */

    @Override
    public final String toString() {
        return "(" + m_First + ", " + m_Second + ")";
    } /* End of 'Pair::toString' method */

    /* *
     * METHOD: Gets first pair's object
     * RETURN: First pair's object
     * AUTHOR: Eliseev Dmitry
     * */
    public final A getFirst() {
        return m_First;
    } /* End of 'Pair::getFirst' method */

    /* *
     * METHOD: Sets first pair object
     *  PARAM: [IN] first - object to set
     * AUTHOR: Eliseev Dmitry
     * */
    public final void setFirst(final A first) {
        m_First = first;
    } /* End of 'Pair::setFirst' method */

    /* *
     * METHOD: Gets second pair's object
     * RETURN: Second pair's object
     * AUTHOR: Eliseev Dmitry
     * */
    public final B getSecond() {
        return m_Second;
    } /* End of 'Pair::getSecond' method */

    /* *
     * METHOD: Sets second pair object
     *  PARAM: [IN] second - object to set
     * AUTHOR: Eliseev Dmitry
     * */
    public final void setSecond(final B second) {
        m_Second = second;
    } /* End of 'Pair::setSecond' method */
} /* End of 'Pair' class */