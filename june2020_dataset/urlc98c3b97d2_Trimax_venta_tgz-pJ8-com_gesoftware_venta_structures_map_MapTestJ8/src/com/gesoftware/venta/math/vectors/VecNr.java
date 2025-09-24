package com.gesoftware.venta.math.vectors;

import com.gesoftware.venta.math.tools.Mathematics;

import java.io.Serializable;

/**
 * VecNr class definition
 */
public final class VecNr implements Serializable {
    private final double m_Components[];

    /* *
     * METHOD: VecNr default class constructor
     *  PARAM: [IN] size - vector size
     * AUTHOR: Eliseev Dmitry
     * */
    public VecNr(final int size) {
        this(size, 0.0);
    } /* End of 'VecNr::VecNr' method */

    /* *
     * METHOD: VecNr class constructor
     *  PARAM: [IN] size - vector size
     *  PARAM: [IN] v    - initial value
     * AUTHOR: Eliseev Dmitry
     * */
    public VecNr(final int size, final double v) {
        if (size <= 0)
            throw new NegativeArraySizeException();

        m_Components = new double[size];
        for (int index = 0; index < size; index++)
            m_Components[index] = v;
    } /* End of 'VecNr::VecNr' method */

    /* *
     * METHOD: VecNr class constructor
     *  PARAM: [IN] components - vector components
     * AUTHOR: Eliseev Dmitry
     * */
    public VecNr(final double... components) {
        m_Components = new double[components.length];
        System.arraycopy(components, 0, m_Components, 0, components.length);
    } /* End of 'VecNr::VecNr' method */

    /* *
     * METHOD: VecNr class constructor
     *  PARAM: [IN] v - original vector
     * AUTHOR: Eliseev Dmitry
     * */
    public VecNr(final VecNr v) {
        this(v.m_Components);
    } /* End of 'VecNr::VecNr' method */

    /* *
     * METHOD: VecNr class constructor
     *  PARAM: [IN] v - original vector
     * AUTHOR: Eliseev Dmitry
     * */
    public VecNr(final VecNi v) {
        this(v.size());

        for (int index = 0; index < m_Components.length; index++)
            set(index, v.get(index));
    } /* End of 'VecNr::VecNr' method */

    /* *
     * METHOD: Adds a number to a vector
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] val - number to add
     * AUTHOR: Eliseev Dmitry
     * */
    public final void add(final double val) {
        for (int index = 0; index < m_Components.length; index++)
            m_Components[index] += val;
    } /* End of 'VecNr::add' method */

    /* *
     * METHOD: Adds a vector to self
     *  PARAM: [IN] v - vector to add
     * AUTHOR: Eliseev Dmitry
     * */
    public final void add(final VecNr v) {
        add(v.m_Components);
    } /* End of 'VecNr::add' method */

    /* *
     * METHOD: Adds a vector to self
     *  PARAM: [IN] components - components to add
     * AUTHOR: Eliseev Dmitry
     * */
    public final void add(final double... components) {
        if (components.length != m_Components.length)
            throw new IllegalArgumentException("Components array size is differs from a vector size");

        for (int index = 0; index < m_Components.length; index++)
            m_Components[index] += components[index];
    } /* End of 'VecNr::add' method */

    /* *
     * METHOD: Adds two vectors using per-component sum
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] v1 - first vector to add
     *  PARAM: [IN] v2 - second vector to add
     * AUTHOR: Eliseev Dmitry
     * */
    public static VecNr add(final VecNr v1, final VecNr v2) {
        return add(v1, v2.m_Components);
    } /* End of 'VecNr::add' method */

    /* *
     * METHOD: Adds a number of vectors using per-component sum
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] vectors - vectors to add
     * AUTHOR: Eliseev Dmitry
     * */
    public static VecNr add(final VecNr... vectors) {
        for (final VecNr v : vectors)
            if (v.m_Components.length != vectors[0].m_Components.length)
                throw new IllegalArgumentException("Vectors has different sizes");

        final VecNr sum = new VecNr(vectors[0].m_Components.length);
        for (final VecNr v : vectors)
            sum.add(v);

        return sum;
    } /* End of 'VecNr::add' method */

    /* *
     * METHOD: Adds a number to a vector
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] v   - source vector
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     *  PARAM: [IN] z - applicate component
     * AUTHOR: Eliseev Dmitry
     * */
    public static VecNr add(final VecNr v, final double... components) {
        final VecNr result = new VecNr(v);
        result.add(components);

        return result;
    } /* End of 'VecNr::add' method */

    /* *
     * METHOD: Adds a number to a vector
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] v   - source vector
     *  PARAM: [IN] val - number to add
     * AUTHOR: Eliseev Dmitry
     * */
    public static VecNr add(final VecNr v, final double val) {
        final VecNr result = new VecNr(v);
        result.add(val);

        return result;
    } /* End of 'VecNr::add' method */

    /* *
     * METHOD: Diffs a number from a vector
     *  PARAM: [IN] val - number to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public final void diff(final double val) {
        for (int index = 0; index < m_Components.length; index++)
            m_Components[index] += val;
    } /* End of 'VecNr::diff' method */

    /* *
     * METHOD: Diffs a vector from self
     *  PARAM: [IN] v - vector to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public final void diff(final VecNr v) {
        diff(v.m_Components);
    } /* End of 'VecNr::diff' method */

    /* *
     * METHOD: Per-component diff
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     *  PARAM: [IN] z - applicate component
     * AUTHOR: Eliseev Dmitry
     * */
    public final void diff(final double... components) {
        if (components.length != m_Components.length)
            throw new IllegalArgumentException("Components array size is differs from a vector size");

        for (int index = 0; index < m_Components.length; index++)
            m_Components[index] -= components[index];
    } /* End of 'VecNr::diff' method */

    /* *
     * METHOD: Diffs two vectors using per-component difference
     * RETURN: Per-component vectors difference
     *  PARAM: [IN] v1 - vector to diff from
     *  PARAM: [IN] v2 - vector to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public static VecNr diff(final VecNr v1, final VecNr v2) {
        return diff(v1, v2.m_Components);
    } /* End of 'VecNr::diff' method */

    /* *
     * METHOD: Diffs a number from an each vector's component
     * RETURN: V - (x, y)
     *  PARAM: [IN] v          - original vector
     *  PARAM: [IN] components - components to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public static VecNr diff(final VecNr v, final double... components) {
        final VecNr result = new VecNr(v);
        result.diff(components);

        return result;
    } /* End of 'VecNr::diff' method */

    /* *
     * METHOD: Multiplies self with a number
     *  PARAM: [IN] a - multiplier
     * AUTHOR: Eliseev Dmitry
     * */
    public final void multiply(final double val) {
        for (int index = 0; index < m_Components.length; index++)
            m_Components[index] *= val;
    } /* End of 'VecNr::multiply' method */

    /* *
     * METHOD: Per-component vectors multiplication
     * RETURN: Vector, multiplied with a vector
     *  PARAM: [IN] v - vector to multiply
     * AUTHOR: Eliseev Dmitry
     * */
    public final void multiply(final VecNr v) {
        multiply(v.m_Components);
    } /* End of 'VecNr::multiply' method */

    /* *
     * METHOD: Per-component multiplication
     *  PARAM: [IN] components - components to multiply
     * AUTHOR: Eliseev Dmitry
     * */
    public final void multiply(final double... components) {
        if (components.length != m_Components.length)
            throw new IllegalArgumentException("Components array size is differs from a vector size");

        for (int index = 0; index < m_Components.length; index++)
            m_Components[index] *= components[index];
    } /* End of 'VecNr::multiply' method */

    /* *
     * METHOD: Per-component vectors multiplication
     * RETURN: Vector, multiplied with a vector
     *  PARAM: [IN] v1 - first vector to multiply
     *  PARAM: [IN] v2 - second vector to multiply
     * AUTHOR: Eliseev Dmitry
     * */
    public static VecNr multiply(final VecNr v1, final VecNr v2) {
        return multiply(v1, v2.m_Components);
    } /* End of 'VecNr::multiply' method */

    /* *
     * METHOD: Multiplies vector with a number
     * RETURN: Vector, multiplied with a number
     *  PARAM: [IN] v          - vector to multiply
     *  PARAM: [IN] components - new vector components
     * AUTHOR: Eliseev Dmitry
     * */
    public static VecNr multiply(final VecNr v, final double... components) {
        final VecNr result = new VecNr(v);
        result.multiply(components);

        return result;
    } /* End of 'VecNr::multiply' method */

    /* *
     * METHOD: Multiplies vector with a number
     * RETURN: Vector, multiplied with a number
     *  PARAM: [IN] v - vector to multiply
     *  PARAM: [IN] a - multiplier
     * AUTHOR: Eliseev Dmitry
     * */
    public static VecNr multiply(final VecNr v, final double val) {
        final VecNr result = new VecNr(v);
        result.multiply(val);

        return result;
    } /* End of 'VecNr::multiply' method */

    /* *
     * METHOD: Component getter
     * RETURN: Component value
     *  PARAM: [IN] index - component index
     * AUTHOR: Eliseev Dmitry
     * */
    public final double get(final int index) {
        return m_Components[index];
    } /* End of 'VecNr::get' method */

    /* *
     * METHOD: Abscissa setter
     *  PARAM: [IN] index - component index
     *  PARAM: [IN] val   - new value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void set(final int index, final double val) {
        m_Components[index] = val;
    } /* End of 'VecNr::set' method */

    /* *
     * METHOD: Components setter
     *  PARAM: [IN] components - new vector components
     * AUTHOR: Eliseev Dmitry
     * */
    public final void set(final double... components) {
        if (components.length != m_Components.length)
            throw new IllegalArgumentException("Components array size is differs from a vector size");

        System.arraycopy(components, 0, m_Components, 0, m_Components.length);
    } /* End of 'VecNr::set' method */

    /* *
     * METHOD: Components setter
     *  PARAM: [IN] v - new value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void set(final VecNr v) {
        set(v.m_Components);
    } /* End of 'VecNr::set' method */

    /* *
     * METHOD: Vector size getter
     * RETURN: Vector size
     * AUTHOR: Eliseev Dmitry
     * */
    public final int size() {
        return m_Components.length;
    } /* End of 'VecNr::getSize' method */

    /* *
     * METHOD: Converts real vector to an integer
     * RETURN: Real vector
     * AUTHOR: Eliseev Dmitry
     * */
    public final VecNi toVecNi() {
        return new VecNi(this);
    } /* End of 'VecNr::toVecNi' method */

    /* *
     * METHOD: Calculates scalar product of two vectors
     * RETURN: Scalar product of vectors
     *  PARAM: [IN] v1 - first vector to compute scalar product
     *  PARAM: [IN] v2 - second vector to compute scalar product
     * AUTHOR: Eliseev Dmitry
     * */
    public static double dot(final VecNr v1, final VecNr v2) {
        if (v1.m_Components.length != v2.m_Components.length)
            throw new IllegalArgumentException("Vectors has different sizes");

        double dotProduct = 0.0;
        for (int index = 0; index < v1.m_Components.length; index++)
            dotProduct += v1.m_Components[index] * v2.m_Components[index];

        return dotProduct;
    } /* End of 'VecNr::dot' method */

    /* *
     * METHOD: Calculates vector's norm
     * RETURN: Vector's norm
     * AUTHOR: Eliseev Dmitry
     * */
    public final double getNorm() {
        double sum = 0.0;
        for (final double component : m_Components)
            sum += component * component;

        return Math.pow(sum, 0.5);
    } /* End of 'VecNr::getNorm' method */

    /* *
     * METHOD: Normalized vector
     * RETURN: Scalar product of vectors
     *  PARAM: [IN] v - vector to compute scalar product with
     * AUTHOR: Eliseev Dmitry
     * */
    public final void normalize() {
        final double norm = getNorm();
        if (Mathematics.isNull(norm))
            return;

        multiply(1.0 / norm);
    } /* End of 'VecNr::normalize' method */

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof VecNr))
            return false;

        final VecNr v = (VecNr) o;
        if (m_Components.length != v.m_Components.length)
            return false;

        for (int index = 0; index < m_Components.length; index++)
            if (!Mathematics.equals(m_Components[index], v.m_Components[index]))
                return false;

        return true;
    } /* End of 'VecNr::equals' method */

    @Override
    public final String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[");

        for (final double component : m_Components)
            builder.append(component).append(", ");

        builder.setLength(builder.lastIndexOf(","));
        builder.append("]");

        return builder.toString();
    } /* End of 'VecNr::toString' method */
} /* End of 'VecNr' class */