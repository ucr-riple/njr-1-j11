package com.gesoftware.venta.math.vectors;

import com.gesoftware.venta.math.tools.Mathematics;

import java.io.Serializable;

/**
 * Vec3r class definition
 */
public final class Vec3r implements Serializable {
    private double m_X;
    private double m_Y;
    private double m_Z;

    /* *
     * METHOD: Vec3r default class constructor
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec3r() {
        this(0.0);
    } /* End of 'Vec3r::Vec3r' method */

    /* *
     * METHOD: Vec3r class constructor
     *  PARAM: [IN] v - initial value
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec3r(final double v) {
        this(v, v, v);
    } /* End of 'Vec3r::Vec3r' method */

    /* *
     * METHOD: Vec3r class constructor
     *  PARAM: [IN] x - abscissa component
     *  PARAM: [IN] y - ordinate component
     *  PARAM: [IN] z - applicate component
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec3r(final double x, final double y, final double z) {
        m_X = x;
        m_Y = y;
        m_Z = z;
    } /* End of 'Vec3r::Vec3r' method */

    /* *
     * METHOD: Vec3r class constructor
     *  PARAM: [IN] v - original vector
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec3r(final Vec3r v) {
        this(v.getX(), v.getY(), v.getZ());
    } /* End of 'Vec3r::Vec3r' method */

    /* *
     * METHOD: Vec3r class constructor
     *  PARAM: [IN] v - original vector
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec3r(final Vec3i v) {
        this(v.getX(), v.getY(), v.getZ());
    } /* End of 'Vec3r::Vec3r' method */

    /* *
     * METHOD: Adds a number to a vector
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] val - number to add
     * AUTHOR: Eliseev Dmitry
     * */
    public final void add(final double val) {
        add(val, val, val);
    } /* End of 'Vec3r::add' method */

    /* *
     * METHOD: Adds a vector to self
     *  PARAM: [IN] v - vector to add
     * AUTHOR: Eliseev Dmitry
     * */
    public final void add(final Vec3r v) {
        add(v.getX(), v.getY(), v.getZ());
    } /* End of 'Vec3r::add' method */

    /* *
     * METHOD: Adds a vector to self
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     *  PARAM: [IN] z - applicate component
     * AUTHOR: Eliseev Dmitry
     * */
    public final void add(final double x, final double y, final double z) {
        m_X += x;
        m_Y += y;
        m_Z += z;
    } /* End of 'Vec3r::add' method */

    /* *
     * METHOD: Adds two vectors using per-component sum
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] v1 - first vector to add
     *  PARAM: [IN] v2 - second vector to add
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec3r add(final Vec3r v1, final Vec3r v2) {
        return new Vec3r(v1.m_X + v2.m_X, v1.m_Y + v2.m_Y, v1.m_Z + v2.m_Z);
    } /* End of 'Vec3r::add' method */

    /* *
     * METHOD: Adds a number of vectors using per-component sum
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] vectors - vectors to add
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec3r add(final Vec3r... vectors) {
        final Vec3r sum = new Vec3r();
        if (vectors == null)
            return sum;

        for (final Vec3r v : vectors)
            sum.add(v);

        return sum;
    } /* End of 'Vec3r::add' method */

    /* *
     * METHOD: Adds a number to a vector
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] v   - source vector
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     *  PARAM: [IN] z - applicate component
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec3r add(final Vec3r v, final double x, final double y, final double z) {
        return new Vec3r(v.m_X + x, v.m_Y + y, v.m_Z + z);
    } /* End of 'Vec3r::add' method */

    /* *
     * METHOD: Adds a number to a vector
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] v   - source vector
     *  PARAM: [IN] val - number to add
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec3r add(final Vec3r v, final double val) {
        return add(v, val, val, val);
    } /* End of 'Vec3r::add' method */

    /* *
     * METHOD: Diffs a number from a vector
     *  PARAM: [IN] val - number to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public final void diff(final double val) {
        diff(val, val, val);
    } /* End of 'Vec3r::diff' method */

    /* *
     * METHOD: Diffs a vector from self
     *  PARAM: [IN] v - vector to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public final void diff(final Vec3r v) {
        diff(v.getX(), v.getY(), v.getZ());
    } /* End of 'Vec3r::diff' method */

    /* *
     * METHOD: Per-component diff
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     *  PARAM: [IN] z - applicate component
     * AUTHOR: Eliseev Dmitry
     * */
    public final void diff(final double x, final double y, final double z) {
        m_X -= x;
        m_Y -= y;
        m_Z -= z;
    } /* End of 'Vec3r::diff' method */

    /* *
     * METHOD: Diffs two vectors using per-component difference
     * RETURN: Per-component vectors difference
     *  PARAM: [IN] v1 - vector to diff from
     *  PARAM: [IN] v2 - vector to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec3r diff(final Vec3r v1, final Vec3r v2) {
        return new Vec3r(v1.m_X - v2.m_X, v1.m_Y - v2.m_Y, v1.m_Z - v2.m_Z);
    } /* End of 'Vec3r::diff' method */

    /* *
     * METHOD: Diffs a number from an each vector's component
     * RETURN: V - (x, y)
     *  PARAM: [IN] x - absciss
     *  PARAM: [IN] y - ordinate
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec3r diff(final Vec3r v, final double x, final double y, final double z) {
        return new Vec3r(v.m_X - x, v.m_Y - y, v.m_Z - z);
    } /* End of 'Vec3r::diff' method */

    /* *
     * METHOD: Diffs a number from an each vector's component
     * RETURN: V - val*E
     *  PARAM: [IN] val - number to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec3r diff(final Vec3r v, final double val) {
        return new Vec3r(v.m_X - val, v.m_Y - val, v.m_Z - val);
    } /* End of 'Vec3r::diff' method */

    /* *
     * METHOD: Multiplies self with a number
     *  PARAM: [IN] a - multiplier
     * AUTHOR: Eliseev Dmitry
     * */
    public final void multiply(final double val) {
        multiply(val, val, val);
    } /* End of 'Vec3r::multiply' method */

    /* *
     * METHOD: Per-component vectors multiplication
     * RETURN: Vector, multiplied with a vector
     *  PARAM: [IN] v - vector to multiply
     * AUTHOR: Eliseev Dmitry
     * */
    public final void multiply(final Vec3r v) {
        multiply(v.getX(), v.getY(), v.getZ());
    } /* End of 'Vec3r::multiply' method */

    /* *
     * METHOD: Per-component multiplication
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     *  PARAM: [IN] z - applicate component
     * AUTHOR: Eliseev Dmitry
     * */
    public final void multiply(final double x, final double y, final double z) {
        m_X *= x;
        m_Y *= y;
        m_Z *= z;
    } /* End of 'Vec3r::multiply' method */

    /* *
     * METHOD: Per-component vectors multiplication
     * RETURN: Vector, multiplied with a vector
     *  PARAM: [IN] v1 - first vector to multiply
     *  PARAM: [IN] v2 - second vector to multiply
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec3r multiply(final Vec3r v1, final Vec3r v2) {
        return new Vec3r(v1.m_X * v2.m_X, v1.m_Y * v2.m_Y, v1.m_Z * v2.m_Z);
    } /* End of 'Vec3r::multiply' method */

    /* *
     * METHOD: Multiplies vector with a number
     * RETURN: Vector, multiplied with a number
     *  PARAM: [IN] v - vector to multiply
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     *  PARAM: [IN] z - applicate component
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec3r multiply(final Vec3r v, final double x, final double y, final double z) {
        return new Vec3r(v.m_X * x, v.m_Y * y, v.m_Z * z);
    } /* End of 'Vec3r::multiply' method */

    /* *
     * METHOD: Multiplies vector with a number
     * RETURN: Vector, multiplied with a number
     *  PARAM: [IN] v - vector to multiply
     *  PARAM: [IN] a - multiplier
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec3r multiply(final Vec3r v, final double val) {
        return multiply(v, val, val, val);
    } /* End of 'Vec3r::multiply' method */

    /* *
     * METHOD: Abscissa getter
     * RETURN: Abscissa value
     * AUTHOR: Eliseev Dmitry
     * */
    public final double getX() {
        return m_X;
    } /* End of 'Vec3r::getX' method */

    /* *
     * METHOD: Abscissa getter
     * RETURN: Ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final double getY() {
        return m_Y;
    } /* End of 'Vec3r::getY' method */

    /* *
     * METHOD: Applicate getter
     * RETURN: Ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final double getZ() {
        return m_Z;
    } /* End of 'Vec3r::getZ' method */

    /* *
     * METHOD: Abscissa setter
     *  PARAM: [IN] x - new abscissa value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void setX(final double x) {
        m_X = x;
    } /* End of 'Vec3r::setX' method */

    /* *
     * METHOD: Ordinate setter
     *  PARAM: [IN] y - new ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void setY(final double y) {
        m_Y = y;
    } /* End of 'Vec3r::setY' method */

    /* *
     * METHOD: Applicate setter
     *  PARAM: [IN] z - new applicate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void setZ(final double z) {
        m_Z = z;
    } /* End of 'Vec3r::setZ' method */

    /* *
     * METHOD: Components setter
     *  PARAM: [IN] x - new abscissa value
     *  PARAM: [IN] y - new ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void set(final double x, final double y, final double z) {
        m_X = x;
        m_Y = y;
        m_Z = z;
    } /* End of 'Vec3r::set' method */

    /* *
     * METHOD: Components setter
     *  PARAM: [IN] v - new value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void set(final Vec3r v) {
        m_X = v.m_X;
        m_Y = v.m_Y;
        m_Z = v.m_Z;
    } /* End of 'Vec3r::set' method */

    /* *
     * METHOD: Converts real vector to an integer
     * RETURN: Real vector
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec3i toVec3i() {
        return new Vec3i(this);
    } /* End of 'Vec3r::toVec3r' method */

    /* *
     * METHOD: Calculates scalar product of two vectors
     * RETURN: Scalar product of vectors
     *  PARAM: [IN] v1 - first vector to compute scalar product
     *  PARAM: [IN] v2 - second vector to compute scalar product
     * AUTHOR: Eliseev Dmitry
     * */
    public static double dot(final Vec3r v1, final Vec3r v2) {
        return v1.m_X * v2.m_X + v1.m_Y * v2.m_Y;
    } /* End of 'Vec3r::dot' method */

    /* *
     * METHOD: Calculates vector's norm
     * RETURN: Vector's norm
     * AUTHOR: Eliseev Dmitry
     * */
    public final double getNorm() {
        return Math.pow(m_X * m_X + m_Y * m_Y + m_Z * m_Z, 0.5);
    } /* End of 'Vec3r::getNorm' method */

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
        m_Z /= norm;
    } /* End of 'Vec3r::normalize' method */

    /* *
     * METHOD: Gets vector's projection to XY plane
     * RETURN: Vector's projection to XY plane
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2r projectionXY() {
        return new Vec2r(m_X, m_Y);
    } /* End of 'Vec3r::projectionXY' method */

    /* *
     * METHOD: Gets vector's projection to XZ plane
     * RETURN: Vector's projection to XZ plane
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2r projectionXZ() {
        return new Vec2r(m_X, m_Z);
    } /* End of 'Vec3r::projectionXZ' method */

    /* *
     * METHOD: Gets vector's projection to YZ plane
     * RETURN: Vector's projection to YZ plane
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2r projectionYZ() {
        return new Vec2r(m_Y, m_Z);
    } /* End of 'Vec3r::projectionYZ' method */

    @Override
    public final boolean equals(final Object o) {
        return o instanceof Vec3r && Mathematics.equals(((Vec3r) o).getX(), m_X) && Mathematics.equals(((Vec3r) o).getY(), m_Y) && Mathematics.equals(((Vec3r) o).getZ(), m_Z);
    } /* End of 'Vec3r::equals' method */

    @Override
    public final String toString() {
        return "[" + m_X + ", " + m_Y + ", " + m_Z + "]";
    } /* End of 'Vec3r::toString' method */
} /* End of 'Vec3r' class */