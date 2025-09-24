package com.gesoftware.venta.structures.object;

import java.io.Serializable;

/**
 * Synchronized object class definition
 **/
public final class SynchronizedObject<T> implements Serializable {
    private final Object m_Sync;
    private T m_Object;

    /**
     * Function interface definition
     **/
    public interface Function<O> {
        /* *
         * METHOD: Do something with object guaranted in synchronized block
         * RETURN: Reference to accepted object
         *  PARAM: [IN] object - opbject to manipulate
         * AUTHOR: Eliseev Dmitry
         * */
        public abstract O apply(final O object);
    } /* End of 'Function' interface */

    /**
     * Procedure interface definition
     **/
    public interface Procedure<O> {
        /* *
         * METHOD: Do something with object guaranted in synchronized block
         *  PARAM: [IN] object - opbject to manipulate
         * AUTHOR: Eliseev Dmitry
         * */
        public abstract void apply(final O object);
    } /* End of 'Procedure' interface */

    /**
     * Extractor interface definition
     **/
    public interface Extractor<O, E> {
        /* *
         * METHOD: Extracts some information from an object
         * RETURN: Extracted information
         *  PARAM: [IN] object - opbject to extract information
         * AUTHOR: Eliseev Dmitry
         * */
        public abstract E extract(final O object);
    } /* End of 'Extractor' interface */

    /**
     * Checker interface definition
     **/
    public interface Checker<O> {
        /* *
         * METHOD: Checks some condition for stored object
         * RETURN: Condition checking result
         *  PARAM: [IN] object - opbject to check
         * AUTHOR: Eliseev Dmitry
         * */
        public abstract boolean check(final O object);
    } /* End of 'Checker' interface */

    /* *
     * METHOD: Class constructor
     * AUTHOR: Eliseev Dmitry
     * */
    public SynchronizedObject() {
        this(null);
    } /* End of 'SynchronizedObject::SynchronizedObject' method */

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] object - object to store
     * AUTHOR: Eliseev Dmitry
     * */
    public SynchronizedObject(final T object) {
        m_Sync   = new Object();
        m_Object = object;
    } /* End of 'SynchronizedObject::SynchronizedObject' method */

    /* *
     * METHOD: Applies some function to stored object
     *  PARAM: [IN] function - function to apply
     * AUTHOR: Eliseev Dmitry
     * */
    public final void apply(final Function<T> function) {
        if (function == null)
            return;

        synchronized (m_Sync) {
            m_Object = function.apply(m_Object);
        }
    } /* End of 'SynchronizedObject::apply' method */

    /* *
     * METHOD: Applies some function to stored object if it's not null
     *  PARAM: [IN] function - function to apply
     * AUTHOR: Eliseev Dmitry
     * */
    public final void applyIfNotNull(final Function<T> function) {
        if (function == null)
            return;

        synchronized (m_Sync) {
            if (m_Object != null)
                apply(function);
        }
    } /* End of 'SynchronizedObject::applyIfNotNull' method */

    /* *
     * METHOD: Applies some procedure to stored object
     *  PARAM: [IN] procedure - procedure to apply
     * AUTHOR: Eliseev Dmitry
     * */
    public final void apply(final Procedure<T> procedure) {
        if (procedure == null)
            return;

        synchronized (m_Sync) {
            procedure.apply(m_Object);
        }
    } /* End of 'SynchronizedObject::apply' method */

    /* *
     * METHOD: Applies some function to stored object if it's not null
     *  PARAM: [IN] function - function to apply
     * AUTHOR: Eliseev Dmitry
     * */
    public final void applyIfNotNull(final Procedure<T> procedure) {
        if (procedure == null)
            return;

        synchronized (m_Sync) {
            if (m_Object != null)
                apply(procedure);
        }
    } /* End of 'SynchronizedObject::applyIfNotNull' method */


    /* *
     * METHOD: Applies extractor to stored object
     * RETURN: Extracted value
     *  PARAM: [IN] extractor - extractor
     * AUTHOR: Eliseev Dmitry
     * */
    public final <E> E apply(final Extractor<T, E> extractor) {
        if (extractor == null)
            return null;

        synchronized (m_Sync) {
            return extractor.extract(m_Object);
        }
    } /* End of 'SynchronizedObject::apply' method */

    /* *
     * METHOD: Applies extractor to stored object if it's not null
     * RETURN: Extracted value
     *  PARAM: [IN] extractor - extractor
     * AUTHOR: Eliseev Dmitry
     * */
    public final <E> E applyIfNotNull(final Extractor<T, E> extractor) {
        if (extractor == null)
            return null;

        synchronized (m_Sync) {
            return (m_Object == null)?null:apply(extractor);
        }
    } /* End of 'SynchronizedObject::applyIfNotNull' method */

    /* *
     * METHOD: Applies some checker to stored object
     *  PARAM: [IN] checker - checker to apply
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean check(final Checker<T> checker) {
        if (checker == null)
            return false;

        synchronized (m_Sync) {
            return checker.check(m_Object);
        }
    } /* End of 'SynchronizedObject::check' method */

    /* *
     * METHOD: Sets new stored object
     *  PARAM: [IN] object - object to store
     * AUTHOR: Eliseev Dmitry
     * */
    public final void set(final T object) {
        synchronized (m_Sync) {
            m_Object = object;
        }
    } /* End of 'SynchronizedObject::set' method */
} /* End of 'SynchronizedObject' class */
