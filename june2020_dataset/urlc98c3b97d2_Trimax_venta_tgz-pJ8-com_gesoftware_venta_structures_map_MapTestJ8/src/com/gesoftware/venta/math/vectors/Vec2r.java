package com.gesoftware.venta.math.vectors;

import com.gesoftware.venta.math.tools.Mathematics;

import java.io.Serializable;

/**
 * Vec2i class definition
 */
public final class Vec2r implements Serializable {
    private double m_X;
    private double m_Y;

    /* *
     * METHOD: Vec2r default class constructor
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec2r() {
        this(0.0);
    } /* End of 'Vec2r::Vec2r' method */

    /* *
     * METHOD: Vec2r class constructor
     *  PARAM: [IN] v - initial value
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec2r(final double v) {
        this(v, v);
    } /* End of 'Vec2r::Vec2r' method */

    /* *
     * METHOD: Vec2r class constructor
     *  PARAM: [IN] x - abscissa component
     *  PARAM: [IN] y - ordinate component
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec2r(final double x, final double y) {
        m_X = x;
        m_Y = y;
    } /* End of 'Vec2r::Vec2r' method */

    /* *
     * METHOD: Vec2r class constructor
     *  PARAM: [IN] v - original vector
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec2r(final Vec2r v) {
        this(v.getX(), v.getY());
    } /* End of 'Vec2r::Vec2r' method */

    /* *
     * METHOD: Vec2r class constructor
     *  PARAM: [IN] v - original vector
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec2r(final Vec2i v) {
        this(v.getX(), v.getY());
    } /* End of 'Vec2r::Vec2r' method */

    /* *
     * METHOD: Adds a number to a vector
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] val - number to add
     * AUTHOR: Eliseev Dmitry
     * */
    public final void add(final double val) {
        add(val, val);
    } /* End of 'Vec2r::add' method */

    /* *
     * METHOD: Adds a vector to self
     *  PARAM: [IN] v - vector to add
     * AUTHOR: Eliseev Dmitry
     * */
    public final void add(final Vec2r v) {
        add(v.getX(), v.getY());
    } /* End of 'Vec2r::add' method */

    /* *
     * METHOD: Adds a vector to self
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void add(final double x, final double y) {
        m_X += x;
        m_Y += y;
    } /* End of 'Vec2r::add' method */

    /* *
     * METHOD: Adds two vectors using per-component sum
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] v1 - first vector to add
     *  PARAM: [IN] v2 - second vector to add
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec2r add(final Vec2r v1, final Vec2r v2) {
        return new Vec2r(v1.m_X + v2.m_X, v1.m_Y + v2.m_Y);
    } /* End of 'Vec2r::add' method */

    /* *
     * METHOD: Adds a number of vectors using per-component sum
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] vectors - vectors to add
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec2r add(final Vec2r... vectors) {
        final Vec2r sum = new Vec2r();
        if (vectors == null)
            return sum;

        for (final Vec2r v : vectors)
            sum.add(v);

        return sum;
    } /* End of 'Vec2r::add' method */

    /* *
     * METHOD: Adds a number to a vector
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] v   - source vector
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec2r add(final Vec2r v, final double x, final double y) {
        return new Vec2r(v.m_X + x, v.m_Y + y);
    } /* End of 'Vec2r::add' method */

    /* *
     * METHOD: Adds a number to a vector
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] v   - source vector
     *  PARAM: [IN] val - number to add
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec2r add(final Vec2r v, final double val) {
        return add(v, val, val);
    } /* End of 'Vec2r::add' method */

    /* *
     * METHOD: Diffs a number from a vector
     *  PARAM: [IN] val - number to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public final void diff(final double val) {
        diff(val, val);
    } /* End of 'Vec2r::diff' method */

    /* *
     * METHOD: Diffs a vector from self
     *  PARAM: [IN] v - vector to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public final void diff(final Vec2r v) {
        diff(v.getX(), v.getY());
    } /* End of 'Vec2r::diff' method */

    /* *
     * METHOD: Per-component diff
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void diff(final double x, final double y) {
        m_X -= x;
        m_Y -= y;
    } /* End of 'Vec2r::diff' method */

    /* *
     * METHOD: Diffs two vectors using per-component difference
     * RETURN: Per-component vectors difference
     *  PARAM: [IN] v1 - vector to diff from
     *  PARAM: [IN] v2 - vector to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec2r diff(final Vec2r v1, final Vec2r v2) {
        return new Vec2r(v1.m_X - v2.m_X, v1.m_Y - v2.m_Y);
    } /* End of 'Vec2r::diff' method */

    /* *
     * METHOD: Diffs a number from an each vector's component
     * RETURN: V - (x, y)
     *  PARAM: [IN] x - absciss
     *  PARAM: [IN] y - ordinate
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec2r diff(final Vec2r v, final double x, final double y) {
        return new Vec2r(v.m_X - x, v.m_Y - y);
    } /* End of 'Vec2r::diff' method */

    /* *
     * METHOD: Diffs a number from an each vector's component
     * RETURN: V - val*E
     *  PARAM: [IN] val - number to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec2r diff(final Vec2r v, final double val) {
        return diff(v, val, val);
    } /* End of 'Vec2r::diff' method */

    /* *
     * METHOD: Multiplies self with a number
     *  PARAM: [IN] a - multiplier
     * AUTHOR: Eliseev Dmitry
     * */
    public final void multiply(final double val) {
        multiply(val, val);
    } /* End of 'Vec2r::multiply' method */

    /* *
     * METHOD: Per-component vectors multiplication
     * RETURN: Vector, multiplied with a vector
     *  PARAM: [IN] v - vector to multiply
     * AUTHOR: Eliseev Dmitry
     * */
    public final void multiply(final Vec2r v) {
        multiply(v.getX(), v.getY());
    } /* End of 'Vec2r::multiply' method */

    /* *
     * METHOD: Per-component multiplication
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void multiply(final double x, final double y) {
        m_X *= x;
        m_Y *= y;
    } /* End of 'Vec2r::multiply' method */

    /* *
     * METHOD: Per-component vectors multiplication
     * RETURN: Vector, multiplied with a vector
     *  PARAM: [IN] v1 - first vector to multiply
     *  PARAM: [IN] v2 - second vector to multiply
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec2r multiply(final Vec2r v1, final Vec2r v2) {
        return new Vec2r(v1.m_X * v2.m_X, v1.m_Y * v2.m_Y);
    } /* End of 'Vec2r::multiply' method */

    /* *
     * METHOD: Multiplies vector with a number
     * RETURN: Vector, multiplied with a number
     *  PARAM: [IN] v - vector to multiply
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec2r multiply(final Vec2r v, final double x, final double y) {
        return new Vec2r(v.m_X * x, v.m_Y * y);
    } /* End of 'Vec2r::multiply' method */

    /* *
     * METHOD: Multiplies vector with a number
     * RETURN: Vector, multiplied with a number
     *  PARAM: [IN] v - vector to multiply
     *  PARAM: [IN] a - multiplier
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec2r multiply(final Vec2r v, final double val) {
        return multiply(v, val, val);
    } /* End of 'Vec2r::multiply' method */

    /* *
     * METHOD: Abscissa getter
     * RETURN: Abscissa value
     * AUTHOR: Eliseev Dmitry
     * */
    public final double getX() {
        return m_X;
    } /* End of 'Vec2r::getX' method */

    /* *
     * METHOD: Ordinate getter
     * RETURN: Ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final double getY() {
        return m_Y;
    } /* End of 'Vec2r::getY' method */

    /* *
     * METHOD: Abscissa setter
     *  PARAM: [IN] x - new abscissa value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void setX(final double x) {
        m_X = x;
    } /* End of 'Vec2r::setX' method */

    /* *
     * METHOD: Ordinate setter
     *  PARAM: [IN] y - new ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void setY(final double y) {
        m_Y = y;
    } /* End of 'Vec2r::setY' method */

    /* *
     * METHOD: Components setter
     *  PARAM: [IN] x - new abscissa value
     *  PARAM: [IN] y - new ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void set(final double x, final double y) {
        m_X = x;
        m_Y = y;
    } /* End of 'Vec2r::set' method */

    /* *
     * METHOD: Components setter
     *  PARAM: [IN] v - value to set
     * AUTHOR: Eliseev Dmitry
     * */
    public final void set(final Vec2r v) {
        set(v.m_X, v.m_Y);
    } /* End of 'Vec2r::set' method */

    /* *
     * METHOD: Calculates scalar product of two vectors
     * RETURN: Scalar product of vectors
     *  PARAM: [IN] v1 - first vector to compute scalar product
     *  PARAM: [IN] v2 - second vector to compute scalar product
     * AUTHOR: Eliseev Dmitry
     * */
    public static double dot(final Vec2r v1, final Vec2r v2) {
        return v1.m_X * v2.m_X + v1.m_Y * v2.m_Y;
    } /* End of 'Vec2r::dot' method */

    /* *
     * METHOD: Calculates vector's norm
     * RETURN: Vector's norm
     *  PARAM: [IN] v - vector's norm
     * AUTHOR: Eliseev Dmitry
     * */
    public final double getNorm() {
        return Math.pow(m_X * m_X + m_Y * m_Y, 0.5);
    } /* End of 'Vec2r::getNorm' method */

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

        m_X /= norm;
        m_Y /= norm;
    } /* End of 'Vec2r::normalize' method */

    @Override
    public final boolean equals(final Object obj) {
        return obj instanceof Vec2r && Mathematics.equals(((Vec2r) obj).getX(), m_X) && Mathematics.equals(((Vec2r) obj).getY(), m_Y);
    } /* End of 'Vec2r::equals' method */

    /* *
     * METHOD: Converts real vector to integer vector
     * RETURN: Rounded integer vector
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2i toVec2i() {
        return new Vec2i((int) m_X, (int) m_Y);
    } /* End of 'Vec2r::toVec2i' method */

    @Override
    public final String toString() {
        return "[" + m_X + "; " + m_Y + "]";
    } /* End of 'Vec2r::toString' method */

    /* Predefined directions */
    public final static Vec2r Right = new Vec2r(0.0, 1.0);
    public final static Vec2r Up    = new Vec2r(1.0, 0.0);
} /* End of 'Vec2r' class */