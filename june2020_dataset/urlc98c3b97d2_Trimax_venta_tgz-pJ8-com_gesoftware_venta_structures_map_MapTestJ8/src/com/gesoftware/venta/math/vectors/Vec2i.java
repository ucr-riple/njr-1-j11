package com.gesoftware.venta.math.vectors;

import java.io.Serializable;

/**
 * Vec2i class definition
 */
public final class Vec2i implements Serializable {
    private int m_X;
    private int m_Y;

    /* *
     * METHOD: Vec2i default class constructor
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec2i() {
        this(0);
    } /* End of 'Vec2i::Vec2i' method */

    /* *
     * METHOD: Vec2i class constructor
     *  PARAM: [IN] v - initial value*
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec2i(final int v) {
        this(v, v);
    } /* End of 'Vec2i::Vec2i' method */

    /* *
     * METHOD: Vec2i class constructor
     *  PARAM: [IN] x - abscissa component
     *  PARAM: [IN] y - ordinate component
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec2i(final int x, final int y) {
        m_X = x;
        m_Y = y;
    } /* End of 'Vec2i::Vec2i' method */

    /* *
     * METHOD: Vec2i class constructor
     *  PARAM: [IN] v - original vector
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec2i(final Vec2i v) {
        this(v.getX(), v.getY());
    } /* End of 'Vec2i::Vec2i' method */

    /* *
     * METHOD: Vec2i class constructor
     *  PARAM: [IN] v - original vector
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec2i(final Vec2r v) {
        this((int) v.getX(), (int) v.getY());
    } /* End of 'Vec2i::Vec2i' method */

    /* *
     * METHOD: Adds a number to a vector
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] val - number to add
     * AUTHOR: Eliseev Dmitry
     * */
    public final void add(final int val) {
        add(val, val);
    } /* End of 'Vec2i::add' method */

    /* *
     * METHOD: Adds a vector to self
     *  PARAM: [IN] v - vector to add
     * AUTHOR: Eliseev Dmitry
     * */
    public final void add(final Vec2i v) {
        add(v.getX(), v.getY());
    } /* End of 'Vec2i::add' method */

    /* *
     * METHOD: Adds a vector to self
     *  PARAM: [IN] v - vector to add
     * AUTHOR: Eliseev Dmitry
     * */
    public final void add(final Vec2r v) {
        add((int) v.getX(), (int) v.getY());
    } /* End of 'Vec2i::add' method */

    /* *
     * METHOD: Adds a vector to self
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void add(final int x, final int y) {
        m_X += x;
        m_Y += y;
    } /* End of 'Vec2i::add' method */

    /* *
     * METHOD: Adds two vectors using per-component sum
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] v1 - first vector to add
     *  PARAM: [IN] v2 - second vector to add
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec2i add(final Vec2i v1, final Vec2i v2) {
        return new Vec2i(v1.m_X + v2.m_X, v1.m_Y + v2.m_Y);
    } /* End of 'Vec2i::add' method */

    /* *
     * METHOD: Adds a number of vectors using per-component sum
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] vectors - vectors to add
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec2i add(final Vec2i... vectors) {
        final Vec2i sum = new Vec2i();
        if (vectors == null)
            return sum;

        for (final Vec2i v : vectors)
            sum.add(v);

        return sum;
    } /* End of 'Vec2i::add' method */

    /* *
     * METHOD: Adds a number to a vector
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] v   - source vector
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec2i add(final Vec2i v, final int x, final int y) {
        return new Vec2i(v.m_X + x, v.m_Y + y);
    } /* End of 'Vec2i::add' method */

    /* *
     * METHOD: Adds a number to a vector
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] v   - source vector
     *  PARAM: [IN] val - number to add
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec2i add(final Vec2i v, final int val) {
        return add(v, val, val);
    } /* End of 'Vec2i::add' method */

    /* *
     * METHOD: Diffs a number from a vector
     *  PARAM: [IN] val - number to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public final void diff(final int val) {
        diff(val, val);
    } /* End of 'Vec2i::diff' method */

    /* *
     * METHOD: Diffs a vector from self
     *  PARAM: [IN] v - vector to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public final void diff(final Vec2i v) {
        diff(v.getX(), v.getY());
    } /* End of 'Vec2i::diff' method */

    /* *
     * METHOD: Per-component diff
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void diff(final int x, final int y) {
        m_X -= x;
        m_Y -= y;
    } /* End of 'Vec2i::diff' method */

    /* *
     * METHOD: Diffs two vectors using per-component difference
     * RETURN: Per-component vectors difference
     *  PARAM: [IN] v1 - vector to diff from
     *  PARAM: [IN] v2 - vector to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec2i diff(final Vec2i v1, final Vec2i v2) {
        return new Vec2i(v1.m_X - v2.m_X, v1.m_Y - v2.m_Y);
    } /* End of 'Vec2i::diff' method */

    /* *
     * METHOD: Diffs a number from an each vector's component
     * RETURN: V - (x, y)
     *  PARAM: [IN] x - absciss
     *  PARAM: [IN] y - ordinate
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec2i diff(final Vec2i v, final int x, final int y) {
        return new Vec2i(v.m_X - x, v.m_Y - y);
    } /* End of 'Vec2i::diff' method */

    /* *
     * METHOD: Diffs a number from an each vector's component
     * RETURN: V - val*E
     *  PARAM: [IN] val - number to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec2i diff(final Vec2i v, final int val) {
        return new Vec2i(v.m_X - val, v.m_Y - val);
    } /* End of 'Vec2i::diff' method */

    /* *
     * METHOD: Multiplies self with a number
     *  PARAM: [IN] a - multiplier
     * AUTHOR: Eliseev Dmitry
     * */
    public final void multiply(final int val) {
        multiply(val, val);
    } /* End of 'Vec2i::multiply' method */

    /* *
     * METHOD: Per-component vectors multiplication
     * RETURN: Vector, multiplied with a vector
     *  PARAM: [IN] v - vector to multiply
     * AUTHOR: Eliseev Dmitry
     * */
    public final void multiply(final Vec2i v) {
        multiply(v.getX(), v.getY());
    } /* End of 'Vec2i::multiply' method */

    /* *
     * METHOD: Per-component multiplication
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void multiply(final int x, final int y) {
        m_X *= x;
        m_Y *= y;
    } /* End of 'Vec2i::multiply' method */

    /* *
     * METHOD: Per-component vectors multiplication
     * RETURN: Vector, multiplied with a vector
     *  PARAM: [IN] v1 - first vector to multiply
     *  PARAM: [IN] v2 - second vector to multiply
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec2i multiply(final Vec2i v1, final Vec2i v2) {
        return new Vec2i(v1.m_X * v2.m_X, v1.m_Y * v2.m_Y);
    } /* End of 'Vec2i::multiply' method */

    /* *
     * METHOD: Multiplies vector with a number
     * RETURN: Vector, multiplied with a number
     *  PARAM: [IN] v - vector to multiply
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec2i multiply(final Vec2i v, final int x, final int y) {
        return new Vec2i(v.m_X * x, v.m_Y * y);
    } /* End of 'Vec2i::multiply' method */

    /* *
     * METHOD: Multiplies vector with a number
     * RETURN: Vector, multiplied with a number
     *  PARAM: [IN] v - vector to multiply
     *  PARAM: [IN] a - multiplier
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec2i multiply(final Vec2i v, final int val) {
        return multiply(v, val, val);
    } /* End of 'Vec2i::multiply' method */

    /* *
     * METHOD: Abscissa getter
     * RETURN: Abscissa value
     * AUTHOR: Eliseev Dmitry
     * */
    public final int getX() {
        return m_X;
    } /* End of 'Vec2i::getX' method */

    /* *
     * METHOD: Abscissa getter
     * RETURN: Ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final int getY() {
        return m_Y;
    } /* End of 'Vec2i::getY' method */

    /* *
     * METHOD: Abscissa setter
     *  PARAM: [IN] x - new abscissa value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void setX(final int x) {
        m_X = x;
    } /* End of 'Vec2i::setX' method */

    /* *
     * METHOD: Ordinate setter
     *  PARAM: [IN] y - new ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void setY(final int y) {
        m_Y = y;
    } /* End of 'Vec2i::setY' method */

    /* *
     * METHOD: Components setter
     *  PARAM: [IN] x - new abscissa value
     *  PARAM: [IN] y - new ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void set(final int x, final int y) {
        m_X = x;
        m_Y = y;
    } /* End of 'Vec2i::set' method */

    /* *
     * METHOD: Components setter
     *  PARAM: [IN] v - new value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void set(final Vec2i v) {
        m_X = v.m_X;
        m_Y = v.m_Y;
    } /* End of 'Vec2i::set' method */

    /* *
     * METHOD: Converts integer vector to a real
     * RETURN: Real vector
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2r toVec2r() {
        return new Vec2r(m_X, m_Y);
    } /* End of 'Vec2i::toVec2r' method */

    @Override
    public final boolean equals(final Object o) {
        return o instanceof Vec2i && ((Vec2i) o).getX() == getX() && ((Vec2i) o).getY() == getY();
    } /* End of 'Vec2i::equals' method */

    @Override
    public final String toString() {
        return "[" + m_X + ", " + m_Y + "]";
    } /* End of 'Vec2i::toString' method */
} /* End of 'Vec2i' class */