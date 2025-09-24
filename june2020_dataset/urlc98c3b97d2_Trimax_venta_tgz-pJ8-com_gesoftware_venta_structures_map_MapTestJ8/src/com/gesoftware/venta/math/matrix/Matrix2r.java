package com.gesoftware.venta.math.matrix;

import com.gesoftware.venta.math.vectors.Vec2i;
import com.gesoftware.venta.math.vectors.Vec2r;

/***
 * Matrix2r class definition
 **/
public final class Matrix2r {
    private final double m_Elements[][];

    /* *
     * METHOD: Matrix2r class constructor
     * AUTHOR: Eliseev Dmitry
     * */
    public Matrix2r() {
        m_Elements = new double[2][2];
    } /* End of 'Matrix2r::Matrix2r' method */

    /* *
     * METHOD: Creates a rotation matrix, based on given angle
     *  PARAM: [IN] angle - angle (radians) to create rotation matrix
     * AUTHOR: Eliseev Dmitry
     * */
    public Matrix2r(final double angle) {
        this();

        m_Elements[0][0] =  Math.cos(angle);
        m_Elements[0][1] = -Math.sin(angle);
        m_Elements[1][0] =  Math.sin(angle);
        m_Elements[1][1] =  Math.cos(angle);
    } /* End of 'Matrix2r::Matrix2r' method */

    /* *
     * METHOD: Matrix2r class constructor
     *  PARAM: [IN] matrix - Matrix2r to copy
     * AUTHOR: Eliseev Dmitry
     * */
    public Matrix2r(final Matrix2r matrix) {
        this();

        System.arraycopy(matrix.m_Elements[0], 0, m_Elements[0], 0, 2);
        System.arraycopy(matrix.m_Elements[1], 0, m_Elements[1], 0, 2);
    } /* End of 'Matrix2r::Matrix2r' method */

    /* *
     * METHOD: Checks if (x, y) is correct element coordinates
     * RETURN: True if success, False otherwise
     *  PARAM: [IN] row    - row number
     *  PARAM: [IN] column - column number
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean contains(final int row, final int column) {
        return (row >= 0) && (column >= 0) && (row < 2) && (column < 2);
    } /* End of 'Matrix2r::contains' method */

    /* *
     * METHOD: Checks if (x, y) is correct element coordinates
     * RETURN: True if success, False otherwise
     *  PARAM: [IN] position - coordinates to check
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean contains(final Vec2i position) {
        return contains(position.getX(), position.getY());
    } /* End of 'Matrix2r::contains' method */

    /* *
     * METHOD: Sets new element
     * RETURN: True if success, False otherwise
     *  PARAM: [IN] row    - row number
     *  PARAM: [IN] column - column number
     *  PARAM: [IN] v      - new element value
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean set(final int row, final int column, final int v) {
        if (!contains(row, column))
            return false;

        m_Elements[row][column] = v;
        return true;
    } /* End of 'Matrix::Matrix' method */

    /* *
     * METHOD: Sets new element
     * RETURN: True if success, False otherwise
     *  PARAM: [IN] position - element's position
     *  PARAM: [IN] v        - new element value
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean set(final Vec2i position, final int v) {
        return set(position.getX(), position.getY(), v);
    } /* End of 'Matrix2r::set' method */

    /* *
     * METHOD: Gets an element by it's position
     * RETURN: Element if exists, Integer.MAX_VALUE otherwise
     *  PARAM: [IN] row    - row number
     *  PARAM: [IN] column - column number
     * AUTHOR: Eliseev Dmitry
     * */
    public final double get(final int row, final int column) {
        if (!contains(row, column))
            return Integer.MAX_VALUE;

        return m_Elements[row][column];
    } /* End of 'Matrix2r::Matrix' method */

    /* *
     * METHOD: Gets an element by it's position
     * RETURN: Element if exists, Integer.MAX_VALUE otherwise
     *  PARAM: [IN] position - element's position
     * AUTHOR: Eliseev Dmitry
     * */
    public final double get(final Vec2i position) {
        return get(position.getX(), position.getY());
    } /* End of 'Matrix2r::get' method */

    /* *
     * METHOD: Fills matrix with a given value
     *  PARAM: [IN] v - value to fill matrix
     * AUTHOR: Eliseev Dmitry
     * */
    public final void fillWith(final int v) {
        m_Elements[0][0] = v;
        m_Elements[0][1] = v;
        m_Elements[1][0] = v;
        m_Elements[1][1] = v;
    } /* End of 'Matrix2r::fillWith' method */

    @Override
    public final int hashCode() {
        return (int) (m_Elements[0][0] +
                      m_Elements[0][1] +
                      m_Elements[1][0] +
                      m_Elements[1][1]);
    } /* End of 'Matrix2r::hashCode' method */

    @Override
    public final boolean equals(final Object obj) {
        return obj instanceof Matrix2r && equals(this, (Matrix2r) obj);
    } /* End of 'Matrix2r::equals' method */

    /* *
     * METHOD: Compares two matrices (per-element comparation)
     * RETURN: True if matrices are equal, False otherwise
     *  PARAM: [IN] matrix1 - first matrix
     *  PARAM: [IN] matrix2 - second matrix
     * AUTHOR: Eliseev Dmitry
     * */
    public static boolean equals(final Matrix2r matrix1, final Matrix2r matrix2) {
        if ((matrix1 == null)&&(matrix2 == null))
            return true;

        if ((matrix1 == null)||(matrix2 == null))
            return false;
        for (int row = 0; row < 2; row++)
            for (int column = 0; column < 2; column++)
                if (matrix1.m_Elements[row][column] != matrix2.m_Elements[row][column])
                    return false;

        return true;
    } /* End of 'Matrix2r::equals' method */

    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int row = 0; row < 2; row++) {
            for (int column = 0; column < 2; column++)
                sb.append(m_Elements[row][column]).append(" ");
            sb.append("\n");
        }

        return sb.toString();
    } /* End of 'Matrix2r::toString' method */

    /* *
     * METHOD: Adds another matrix
     *  PARAM: [IN] m - matrix to multiply
     * AUTHOR: Eliseev Dmitry
     * */
    public final void add(final Matrix2r m) {
        m_Elements[0][0] += m.m_Elements[0][0];
        m_Elements[0][1] += m.m_Elements[0][1];
        m_Elements[1][0] += m.m_Elements[1][0];
        m_Elements[1][1] += m.m_Elements[1][1];
    } /* End of 'Matrix2r::multiply' method */

    /* *
     * METHOD: Adds two matrices
     * RETURN: Sum result
     *  PARAM: [IN]  left - left matrix
     *  PARAM: [IN] right - right matrix
     * AUTHOR: Eliseev Dmitry
     * */
    public static Matrix2r add(final Matrix2r left, final Matrix2r right) {
        final Matrix2r result = new Matrix2r(left);
        left.add(right);

        return result;
    } /* End of 'Matrix2r::add' method */

    /* *
     * METHOD: Multiplies matrix with another matrix
     *  PARAM: [IN] m - matrix to multiply
     * AUTHOR: Eliseev Dmitry
     * */
    public final void multiply(final Matrix2r m) {
        final double element00 = m_Elements[0][0] * m.m_Elements[0][0] + m_Elements[0][1] * m.m_Elements[1][0];
        final double element01 = m_Elements[0][0] * m.m_Elements[1][0] + m_Elements[0][1] * m.m_Elements[1][1];
        final double element10 = m_Elements[1][0] * m.m_Elements[0][0] + m_Elements[1][1] * m.m_Elements[1][0];
        final double element11 = m_Elements[1][0] * m.m_Elements[0][1] + m_Elements[1][1] * m.m_Elements[1][1];

        m_Elements[0][0] = element00;
        m_Elements[0][1] = element01;
        m_Elements[1][0] = element10;
        m_Elements[1][1] = element11;
    } /* End of 'Matrix2r::multiply' method */

    /* *
     * METHOD: Multiplies two matrices
     * RETURN: Multiplication result
     *  PARAM: [IN]  left - left matrix
     *  PARAM: [IN] right - right matrix
     * AUTHOR: Eliseev Dmitry
     * */
    public static Matrix2r multiply(final Matrix2r left, final Matrix2r right) {
        final Matrix2r result = new Matrix2r(left);
        left.multiply(right);

        return result;
    } /* End of 'Matrix2r::multiply' method */

    /* *
     * METHOD: Multiplies matrix with the given vector
     * RETURN: Multiplication result
     *  PARAM: [IN] v - vector to multiply
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2r multiply(final Vec2r v) {
        return new Vec2r(m_Elements[0][0] * v.getX() + m_Elements[0][1] * v.getY(),
                         m_Elements[1][0] * v.getX() + m_Elements[1][0] * v.getY());
    } /* End of 'Matrix2r::multiply' method */

    /* *
     * METHOD: Computes matrix determinant
     * RETURN: Matrix determinant
     * AUTHOR: Eliseev Dmitry
     * */
    public final double det() {
        return m_Elements[0][0] * m_Elements[1][1] - m_Elements[0][1] * m_Elements[1][0];
    } /* End of 'Matrix2r::det' method */
} /* End of 'Matrix2r' class */
