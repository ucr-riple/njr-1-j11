package com.gesoftware.venta.math.vectors;

import com.gesoftware.venta.math.tools.Mathematics;

import java.io.Serializable;

/**
 * VecNr class definition
 */
public final class VecNi implements Serializable {
    private final int m_Components[];

    /* *
     * METHOD: VecNr default class constructor
     *  PARAM: [IN] size - vector size
     * AUTHOR: Eliseev Dmitry
     * */
    public VecNi(final int size) {
        this(size, 0);
    } /* End of 'VecNi::VecNi' method */

    /* *
     * METHOD: VecNr class constructor
     *  PARAM: [IN] size - vector size
     *  PARAM: [IN] v    - initial value
     * AUTHOR: Eliseev Dmitry
     * */
    public VecNi(final int size, final int v) {
        if (size <= 0)
            throw new NegativeArraySizeException();

        m_Components = new int[size];
        for (int index = 0; index < size; index++)
            m_Components[index] = v;
    } /* End of 'VecNi::VecNi' method */

    /* *
     * METHOD: VecNr class constructor
     *  PARAM: [IN] components - vector components
     * AUTHOR: Eliseev Dmitry
     * */
    public VecNi(final int... components) {
        m_Components = new int[components.length];
        System.arraycopy(components, 0, m_Components, 0, components.length);
    } /* End of 'VecNi::VecNi' method */

    /* *
     * METHOD: VecNr class constructor
     *  PARAM: [IN] v - original vector
     * AUTHOR: Eliseev Dmitry
     * */
    public VecNi(final VecNi v) {
        this(v.m_Components);
    } /* End of 'VecNi::VecNi' method */

    /* *
     * METHOD: VecNr class constructor
     *  PARAM: [IN] v - original vector
     * AUTHOR: Eliseev Dmitry
     * */
    public VecNi(final VecNr v) {
        this(v.size());

        for (int index = 0; index < m_Components.length; index++)
            set(index, (int) v.get(index));
    } /* End of 'VecNi::VecNr' method */

    /* *
     * METHOD: Adds a number to a vector
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] val - number to add
     * AUTHOR: Eliseev Dmitry
     * */
    public final void add(final int val) {
        for (int index = 0; index < m_Components.length; index++)
            m_Components[index] += val;
    } /* End of 'VecNi::add' method */

    /* *
     * METHOD: Adds a vector to self
     *  PARAM: [IN] v - vector to add
     * AUTHOR: Eliseev Dmitry
     * */
    public final void add(final VecNi v) {
        add(v.m_Components);
    } /* End of 'VecNi::add' method */

    /* *
     * METHOD: Adds a vector to self
     *  PARAM: [IN] components - components to add
     * AUTHOR: Eliseev Dmitry
     * */
    public final void add(final int... components) {
        if (components.length != m_Components.length)
            throw new IllegalArgumentException("Components array size is differs from a vector size");

        for (int index = 0; index < m_Components.length; index++)
            m_Components[index] += components[index];
    } /* End of 'VecNi::add' method */

    /* *
     * METHOD: Adds two vectors using per-component sum
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] v1 - first vector to add
     *  PARAM: [IN] v2 - second vector to add
     * AUTHOR: Eliseev Dmitry
     * */
    public static VecNi add(final VecNi v1, final VecNi v2) {
        return add(v1, v2.m_Components);
    } /* End of 'VecNi::add' method */

    /* *
     * METHOD: Adds a number of vectors using per-component sum
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] vectors - vectors to add
     * AUTHOR: Eliseev Dmitry
     * */
    public static VecNi add(final VecNi... vectors) {
        for (final VecNi v : vectors)
            if (v.m_Components.length != vectors[0].m_Components.length)
                throw new IllegalArgumentException("Vectors has different sizes");

        final VecNi sum = new VecNi(vectors[0].m_Components.length);
        for (final VecNi v : vectors)
            sum.add(v);

        return sum;
    } /* End of 'VecNi::add' method */

    /* *
     * METHOD: Adds a number to a vector
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] v   - source vector
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     *  PARAM: [IN] z - applicate component
     * AUTHOR: Eliseev Dmitry
     * */
    public static VecNi add(final VecNi v, final int... components) {
        final VecNi result = new VecNi(v);
        result.add(components);

        return result;
    } /* End of 'VecNi::add' method */

    /* *
     * METHOD: Adds a number to a vector
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] v   - source vector
     *  PARAM: [IN] val - number to add
     * AUTHOR: Eliseev Dmitry
     * */
    public static VecNi add(final VecNi v, final int val) {
        final VecNi result = new VecNi(v);
        result.add(val);

        return result;
    } /* End of 'VecNi::add' method */

    /* *
     * METHOD: Diffs a number from a vector
     *  PARAM: [IN] val - number to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public final void diff(final int val) {
        for (int index = 0; index < m_Components.length; index++)
            m_Components[index] += val;
    } /* End of 'VecNi::diff' method */

    /* *
     * METHOD: Diffs a vector from self
     *  PARAM: [IN] v - vector to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public final void diff(final VecNi v) {
        diff(v.m_Components);
    } /* End of 'VecNi::diff' method */

    /* *
     * METHOD: Per-component diff
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     *  PARAM: [IN] z - applicate component
     * AUTHOR: Eliseev Dmitry
     * */
    public final void diff(final int... components) {
        if (components.length != m_Components.length)
            throw new IllegalArgumentException("Components array size is differs from a vector size");

        for (int index = 0; index < m_Components.length; index++)
            m_Components[index] -= components[index];
    } /* End of 'VecNi::diff' method */

    /* *
     * METHOD: Diffs two vectors using per-component difference
     * RETURN: Per-component vectors difference
     *  PARAM: [IN] v1 - vector to diff from
     *  PARAM: [IN] v2 - vector to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public static VecNi diff(final VecNi v1, final VecNi v2) {
        return diff(v1, v2.m_Components);
    } /* End of 'VecNi::diff' method */

    /* *
     * METHOD: Diffs a number from an each vector's component
     * RETURN: V - (x, y)
     *  PARAM: [IN] v          - original vector
     *  PARAM: [IN] components - components to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public static VecNi diff(final VecNi v, final int... components) {
        final VecNi result = new VecNi(v);
        result.diff(components);

        return result;
    } /* End of 'VecNi::diff' method */

    /* *
     * METHOD: Multiplies self with a number
     *  PARAM: [IN] a - multiplier
     * AUTHOR: Eliseev Dmitry
     * */
    public final void multiply(final int val) {
        for (int index = 0; index < m_Components.length; index++)
            m_Components[index] *= val;
    } /* End of 'VecNi::multiply' method */

    /* *
     * METHOD: Per-component vectors multiplication
     * RETURN: Vector, multiplied with a vector
     *  PARAM: [IN] v - vector to multiply
     * AUTHOR: Eliseev Dmitry
     * */
    public final void multiply(final VecNi v) {
        multiply(v.m_Components);
    } /* End of 'VecNi::multiply' method */

    /* *
     * METHOD: Per-component multiplication
     *  PARAM: [IN] components - components to multiply
     * AUTHOR: Eliseev Dmitry
     * */
    public final void multiply(final int... components) {
        if (components.length != m_Components.length)
            throw new IllegalArgumentException("Components array size is differs from a vector size");

        for (int index = 0; index < m_Components.length; index++)
            m_Components[index] *= components[index];
    } /* End of 'VecNi::multiply' method */

    /* *
     * METHOD: Per-component vectors multiplication
     * RETURN: Vector, multiplied with a vector
     *  PARAM: [IN] v1 - first vector to multiply
     *  PARAM: [IN] v2 - second vector to multiply
     * AUTHOR: Eliseev Dmitry
     * */
    public static VecNi multiply(final VecNi v1, final VecNi v2) {
        return multiply(v1, v2.m_Components);
    } /* End of 'VecNi::multiply' method */

    /* *
     * METHOD: Multiplies vector with a number
     * RETURN: Vector, multiplied with a number
     *  PARAM: [IN] v          - vector to multiply
     *  PARAM: [IN] components - new vector components
     * AUTHOR: Eliseev Dmitry
     * */
    public static VecNi multiply(final VecNi v, final int... components) {
        final VecNi result = new VecNi(v);
        result.multiply(components);

        return result;
    } /* End of 'VecNi::multiply' method */

    /* *
     * METHOD: Multiplies vector with a number
     * RETURN: Vector, multiplied with a number
     *  PARAM: [IN] v - vector to multiply
     *  PARAM: [IN] a - multiplier
     * AUTHOR: Eliseev Dmitry
     * */
    public static VecNr multiply(final VecNr v, final int val) {
        final VecNr result = new VecNr(v);
        result.multiply(val);

        return result;
    } /* End of 'VecNi::multiply' method */

    /* *
     * METHOD: Component getter
     * RETURN: Component value
     *  PARAM: [IN] index - component index
     * AUTHOR: Eliseev Dmitry
     * */
    public final int get(final int index) {
        return m_Components[index];
    } /* End of 'VecNi::get' method */

    /* *
     * METHOD: Abscissa setter
     *  PARAM: [IN] index - component index
     *  PARAM: [IN] val   - new value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void set(final int index, final int val) {
        m_Components[index] = val;
    } /* End of 'VecNi::set' method */

    /* *
     * METHOD: Vector size getter
     * RETURN: Vector size
     * AUTHOR: Eliseev Dmitry
     * */
    public final int size() {
        return m_Components.length;
    } /* End of 'VecNr::getSize' method */

    /* *
     * METHOD: Components setter
     *  PARAM: [IN] components - new vector components
     * AUTHOR: Eliseev Dmitry
     * */
    public final void set(final int... components) {
        if (components.length != m_Components.length)
            throw new IllegalArgumentException("Components array size is differs from a vector size");

        System.arraycopy(components, 0, m_Components, 0, m_Components.length);
    } /* End of 'VecNi::set' method */

    /* *
     * METHOD: Components setter
     *  PARAM: [IN] v - new value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void set(final VecNi v) {
        set(v.m_Components);
    } /* End of 'VecNi::set' method */

    /* *
     * METHOD: Converts real vector to an integer
     * RETURN: Real vector
     * AUTHOR: Eliseev Dmitry
     * */
    public final VecNr toVecNr() {
        return new VecNr(this);
    } /* End of 'VecNi::toVecNr' method */

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof VecNi))
            return false;

        final VecNi v = (VecNi) o;
        if (m_Components.length != v.m_Components.length)
            return false;

        for (int index = 0; index < m_Components.length; index++)
            if (!Mathematics.equals(m_Components[index], v.m_Components[index]))
                return false;

        return true;
    } /* End of 'VecNi::equals' method */

    @Override
    public final String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[");

        for (final int component : m_Components)
            builder.append(component).append(", ");

        builder.setLength(builder.lastIndexOf(","));
        builder.append("]");

        return builder.toString();
    } /* End of 'VecNi::toString' method */
} /* End of 'VecNr' class */