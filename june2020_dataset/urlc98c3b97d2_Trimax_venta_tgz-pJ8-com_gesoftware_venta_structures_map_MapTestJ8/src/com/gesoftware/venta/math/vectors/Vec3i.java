package com.gesoftware.venta.math.vectors;

import java.io.Serializable;

/**
 * Vec3i class definition
 */
public final class Vec3i implements Serializable {
    private int m_X;
    private int m_Y;
    private int m_Z;

    /* *
     * METHOD: Vec3i default class constructor
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec3i() {
        this(0);
    } /* End of 'Vec3i::Vec3i' method */

    /* *
     * METHOD: Vec3i class constructor
     *  PARAM: [IN] v - initial value
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec3i(final int v) {
        this(v, v, v);
    } /* End of 'Vec3i::Vec3i' method */

    /* *
     * METHOD: Vec3i class constructor
     *  PARAM: [IN] x - abscissa component
     *  PARAM: [IN] y - ordinate component
     *  PARAM: [IN] z - applicate component
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec3i(final int x, final int y, final int z) {
        m_X = x;
        m_Y = y;
        m_Z = z;
    } /* End of 'Vec3i::Vec3i' method */

    /* *
     * METHOD: Vec3i class constructor
     *  PARAM: [IN] v - original vector
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec3i(final Vec3i v) {
        this(v.getX(), v.getY(), v.getZ());
    } /* End of 'Vec3i::Vec3i' method */

    /* *
     * METHOD: Vec3i class constructor
     *  PARAM: [IN] v - original vector
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec3i(final Vec3r v) {
        this((int) v.getX(), (int) v.getY(), (int) v.getZ());
    } /* End of 'Vec3i::Vec3i' method */

    /* *
     * METHOD: Adds a number to a vector
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] val - number to add
     * AUTHOR: Eliseev Dmitry
     * */
    public final void add(final int val) {
        add(val, val, val);
    } /* End of 'Vec3i::add' method */

    /* *
     * METHOD: Adds a vector to self
     *  PARAM: [IN] v - vector to add
     * AUTHOR: Eliseev Dmitry
     * */
    public final void add(final Vec3i v) {
        add(v.getX(), v.getY(), v.getZ());
    } /* End of 'Vec3i::add' method */

    /* *
     * METHOD: Adds a vector to self
     *  PARAM: [IN] v - vector to add
     * AUTHOR: Eliseev Dmitry
     * */
    public final void add(final Vec3r v) {
        add((int) v.getX(), (int) v.getY(), (int) v.getZ());
    } /* End of 'Vec3i::add' method */

    /* *
     * METHOD: Adds a vector to self
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     *  PARAM: [IN] z - applicate component
     * AUTHOR: Eliseev Dmitry
     * */
    public final void add(final int x, final int y, final int z) {
        m_X += x;
        m_Y += y;
        m_Z += z;
    } /* End of 'Vec3i::add' method */

    /* *
     * METHOD: Adds two vectors using per-component sum
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] v1 - first vector to add
     *  PARAM: [IN] v2 - second vector to add
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec3i add(final Vec3i v1, final Vec3i v2) {
        return new Vec3i(v1.m_X + v2.m_X, v1.m_Y + v2.m_Y, v1.m_Z + v2.m_Z);
    } /* End of 'Vec3i::add' method */

    /* *
     * METHOD: Adds a number of vectors using per-component sum
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] vectors - vectors to add
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec3i add(final Vec3i... vectors) {
        final Vec3i sum = new Vec3i();
        if (vectors == null)
            return sum;

        for (final Vec3i v : vectors)
            sum.add(v);

        return sum;
    } /* End of 'Vec3i::add' method */

    /* *
     * METHOD: Adds a number to a vector
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] v   - source vector
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     *  PARAM: [IN] z - applicate component
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec3i add(final Vec3i v, final int x, final int y, final int z) {
        return new Vec3i(v.m_X + x, v.m_Y + y, v.m_Z + z);
    } /* End of 'Vec3i::add' method */

    /* *
     * METHOD: Adds a number to a vector
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] v   - source vector
     *  PARAM: [IN] val - number to add
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec3i add(final Vec3i v, final int val) {
        return add(v, val, val, val);
    } /* End of 'Vec3i::add' method */

    /* *
     * METHOD: Diffs a number from a vector
     *  PARAM: [IN] val - number to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public final void diff(final int val) {
        diff(val, val, val);
    } /* End of 'Vec3i::diff' method */

    /* *
     * METHOD: Diffs a vector from self
     *  PARAM: [IN] v - vector to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public final void diff(final Vec3i v) {
        diff(v.getX(), v.getY(), v.getZ());
    } /* End of 'Vec3i::diff' method */

    /* *
     * METHOD: Per-component diff
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     *  PARAM: [IN] z - applicate component
     * AUTHOR: Eliseev Dmitry
     * */
    public final void diff(final int x, final int y, final int z) {
        m_X -= x;
        m_Y -= y;
        m_Z -= z;
    } /* End of 'Vec3i::diff' method */

    /* *
     * METHOD: Diffs two vectors using per-component difference
     * RETURN: Per-component vectors difference
     *  PARAM: [IN] v1 - vector to diff from
     *  PARAM: [IN] v2 - vector to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec3i diff(final Vec3i v1, final Vec3i v2) {
        return new Vec3i(v1.m_X - v2.m_X, v1.m_Y - v2.m_Y, v1.m_Z - v2.m_Z);
    } /* End of 'Vec3i::diff' method */

    /* *
     * METHOD: Diffs a number from an each vector's component
     * RETURN: V - (x, y)
     *  PARAM: [IN] x - absciss
     *  PARAM: [IN] y - ordinate
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec3i diff(final Vec3i v, final int x, final int y, final int z) {
        return new Vec3i(v.m_X - x, v.m_Y - y, v.m_Z - z);
    } /* End of 'Vec3i::diff' method */

    /* *
     * METHOD: Diffs a number from an each vector's component
     * RETURN: V - val*E
     *  PARAM: [IN] val - number to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec3i diff(final Vec3i v, final int val) {
        return new Vec3i(v.m_X - val, v.m_Y - val, v.m_Z - val);
    } /* End of 'Vec3i::diff' method */

    /* *
     * METHOD: Multiplies self with a number
     *  PARAM: [IN] a - multiplier
     * AUTHOR: Eliseev Dmitry
     * */
    public final void multiply(final int val) {
        multiply(val, val, val);
    } /* End of 'Vec3i::multiply' method */

    /* *
     * METHOD: Per-component vectors multiplication
     * RETURN: Vector, multiplied with a vector
     *  PARAM: [IN] v - vector to multiply
     * AUTHOR: Eliseev Dmitry
     * */
    public final void multiply(final Vec3i v) {
        multiply(v.getX(), v.getY(), v.getZ());
    } /* End of 'Vec3i::multiply' method */

    /* *
     * METHOD: Per-component multiplication
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     *  PARAM: [IN] z - applicate component
     * AUTHOR: Eliseev Dmitry
     * */
    public final void multiply(final int x, final int y, final int z) {
        m_X *= x;
        m_Y *= y;
        m_Z *= z;
    } /* End of 'Vec3i::multiply' method */

    /* *
     * METHOD: Per-component vectors multiplication
     * RETURN: Vector, multiplied with a vector
     *  PARAM: [IN] v1 - first vector to multiply
     *  PARAM: [IN] v2 - second vector to multiply
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec3i multiply(final Vec3i v1, final Vec3i v2) {
        return new Vec3i(v1.m_X * v2.m_X, v1.m_Y * v2.m_Y, v1.m_Z * v2.m_Z);
    } /* End of 'Vec3i::multiply' method */

    /* *
     * METHOD: Multiplies vector with a number
     * RETURN: Vector, multiplied with a number
     *  PARAM: [IN] v - vector to multiply
     *  PARAM: [IN] x - absciss value
     *  PARAM: [IN] y - ordinate value
     *  PARAM: [IN] z - applicate component
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec3i multiply(final Vec3i v, final int x, final int y, final int z) {
        return new Vec3i(v.m_X * x, v.m_Y * y, v.m_Z * z);
    } /* End of 'Vec3i::multiply' method */

    /* *
     * METHOD: Multiplies vector with a number
     * RETURN: Vector, multiplied with a number
     *  PARAM: [IN] v - vector to multiply
     *  PARAM: [IN] a - multiplier
     * AUTHOR: Eliseev Dmitry
     * */
    public static Vec3i multiply(final Vec3i v, final int val) {
        return multiply(v, val, val, val);
    } /* End of 'Vec3i::multiply' method */

    /* *
     * METHOD: Abscissa getter
     * RETURN: Abscissa value
     * AUTHOR: Eliseev Dmitry
     * */
    public final int getX() {
        return m_X;
    } /* End of 'Vec3i::getX' method */

    /* *
     * METHOD: Abscissa getter
     * RETURN: Ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final int getY() {
        return m_Y;
    } /* End of 'Vec3i::getY' method */

    /* *
     * METHOD: Applicate getter
     * RETURN: Ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final int getZ() {
        return m_Z;
    } /* End of 'Vec3i::getZ' method */

    /* *
     * METHOD: Abscissa setter
     *  PARAM: [IN] x - new abscissa value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void setX(final int x) {
        m_X = x;
    } /* End of 'Vec3i::setX' method */

    /* *
     * METHOD: Ordinate setter
     *  PARAM: [IN] y - new ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void setY(final int y) {
        m_Y = y;
    } /* End of 'Vec3i::setY' method */

    /* *
     * METHOD: Applicate setter
     *  PARAM: [IN] z - new applicate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void setZ(final int z) {
        m_Z = z;
    } /* End of 'Vec3i::setZ' method */

    /* *
     * METHOD: Components setter
     *  PARAM: [IN] x - new abscissa value
     *  PARAM: [IN] y - new ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void set(final int x, final int y, final int z) {
        m_X = x;
        m_Y = y;
        m_Z = z;
    } /* End of 'Vec3i::set' method */

    /* *
     * METHOD: Components setter
     *  PARAM: [IN] v - new value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void set(final Vec3i v) {
        m_X = v.m_X;
        m_Y = v.m_Y;
        m_Z = v.m_Z;
    } /* End of 'Vec3i::set' method */

    /* *
     * METHOD: Converts integer vector to a real
     * RETURN: Real vector
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec3r toVec3r() {
        return new Vec3r(this);
    } /* End of 'Vec3i::toVec3r' method */

    /* *
     * METHOD: Gets vector's projection to XY plane
     * RETURN: Vector's projection to XY plane
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2i projectionXY() {
        return new Vec2i(m_X, m_Y);
    } /* End of 'Vec3i::projectionXY' method */

    /* *
     * METHOD: Gets vector's projection to XZ plane
     * RETURN: Vector's projection to XZ plane
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2i projectionXZ() {
        return new Vec2i(m_X, m_Z);
    } /* End of 'Vec3i::projectionXZ' method */

    /* *
     * METHOD: Gets vector's projection to YZ plane
     * RETURN: Vector's projection to YZ plane
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2i projectionYZ() {
        return new Vec2i(m_Y, m_Z);
    } /* End of 'Vec3i::projectionYZ' method */

    @Override
    public final boolean equals(final Object o) {
        return o instanceof Vec3i && ((Vec3i) o).getX() == getX() && ((Vec3i) o).getY() == getY() && ((Vec3i) o).getZ() == getZ();
    } /* End of 'Vec3i::equals' method */

    @Override
    public final String toString() {
        return "[" + m_X + ", " + m_Y + ", " + m_Z + "]";
    } /* End of 'Vec3i::toString' method */
} /* End of 'Vec3i' class */