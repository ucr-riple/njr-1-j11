package com.gesoftware.venta.structures.option;

import java.io.Serializable;

/**
 * Option class definition
 **/
@SuppressWarnings("unchecked")
public final class Option<T> implements Serializable {
    private final T m_Object;

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] object - object to store
     * AUTHOR: Eliseev Dmitry
     * */
    public Option(final T object) {
        m_Object = object;
    } /* End of 'Option::Option' method */

    /* *
     * METHOD: Gets stored object
     * RETURN: Stored object
     * AUTHOR: Eliseev Dmitry
     * */
    public final T get() {
        return m_Object;
    } /* End of 'Option::get' method */

    /* *
     * METHOD: Gets stored object if exists, replacement object otherwise
     * RETURN: Either stored or replacement object
     * AUTHOR: Eliseev Dmitry
     * */
    public final T get(final T replacement) {
        return (m_Object != null)?m_Object:replacement;
    } /* End of 'Option::get' method */

    /* *
     * METHOD: Checks if stored objects is null
     * RETURN: True if object is null, False otherwise
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean isEmpty() {
        return m_Object == null;
    } /* End of 'Option::isEmpty' method */

    /* *
     * METHOD: Checks if stored objects is instance of given class
     * RETURN: True if object is instance of given class, False otherwise
     *  PARAM: [IN] c - class to check
     * AUTHOR: Eliseev Dmitry
     * */
    public final <C> boolean isInstanceOf(final Class<C> c) {
        return c.isInstance(m_Object);
    }

    /* *
     * METHOD: Tries to represent stored object as instance of given class
     * RETURN: Object representation as given class if success, ClassCastException otherwise
     *  PARAM: [IN] c - class to represent stored object
     * AUTHOR: Eliseev Dmitry
     * */
    public final <C> C asInstanceOf(final Class<C> c) {
        if (isInstanceOf(c))
            return (C) m_Object;

        throw new ClassCastException();
    } /* End of 'Option::asInstanceOf' method */

    /* Empty option */
    public static final Option Empty = new Option(null);
} /* End of 'Option' class */
