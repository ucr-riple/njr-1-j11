package com.gesoftware.venta.math.matrix;

import com.gesoftware.venta.math.vectors.Vec2i;

/***
 * Matrix class definition
 **/
public final class Matrix {
    /* Matrix size */
    private final Vec2i m_Size;

    /* Matrix elements */
    private final int m_Elements[][];

    /* *
     * METHOD: Matrix class constructor
     *  PARAM: [IN] rows    - the number of rows
     *  PARAM: [IN] columns - the number of columns
     * AUTHOR: Eliseev Dmitry
     * */
    public Matrix(final int rows, final int columns) {
        m_Elements = new int[rows][columns];
        m_Size     = new Vec2i(rows, columns);
    } /* End of 'Matrix::Matrix' method */

    /* *
     * METHOD: Matrix class constructor
     *  PARAM: [IN] size - matrix size
     * AUTHOR: Eliseev Dmitry
     * */
    public Matrix(final Vec2i size) {
        this(size.getX(), size.getY());
    } /* End of 'Matrix::Matrix' method */

    /* *
     * METHOD: Matrix class constructor
     *  PARAM: [IN] matrix - matrix to copy
     * AUTHOR: Eliseev Dmitry
     * */
    public Matrix(final Matrix matrix) {
        this(matrix.getSize());

        for (int row = 0; row < m_Size.getX(); row++)
            System.arraycopy(matrix.m_Elements[row], 0, m_Elements[row], 0, m_Size.getY());
    } /* End of 'Matrix::Matrix' method */

    /* *
     * METHOD: Checks if (x, y) is correct element coordinates
     * RETURN: True if success, False otherwise
     *  PARAM: [IN] row    - row number
     *  PARAM: [IN] column - column number
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean contains(final int row, final int column) {
        return (row >= 0) && (column >= 0) && (row < m_Size.getX()) && (column < m_Size.getY());
    } /* End of 'Matrix::Matrix' method */

    /* *
     * METHOD: Checks if (x, y) is correct element coordinates
     * RETURN: True if success, False otherwise
     *  PARAM: [IN] position - coordinates to check
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean contains(final Vec2i position) {
        return contains(position.getX(), position.getY());
    } /* End of 'Matrix::contains' method */

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
    } /* End of 'Matrix::set' method */

    /* *
     * METHOD: Gets an element by it's position
     * RETURN: Element if exists, Integer.MAX_VALUE otherwise
     *  PARAM: [IN] row    - row number
     *  PARAM: [IN] column - column number
     * AUTHOR: Eliseev Dmitry
     * */
    public final int get(final int row, final int column) {
        if (!contains(row, column))
            return Integer.MAX_VALUE;

        return m_Elements[row][column];
    } /* End of 'Matrix::Matrix' method */

    /* *
     * METHOD: Gets an element by it's position
     * RETURN: Element if exists, Integer.MAX_VALUE otherwise
     *  PARAM: [IN] position - element's position
     * AUTHOR: Eliseev Dmitry
     * */
    public final int get(final Vec2i position) {
        return get(position.getX(), position.getY());
    } /* End of 'Matrix::get' method */

    /* *
     * METHOD: Fills matrix with a given value
     *  PARAM: [IN] v - value to fill matrix
     * AUTHOR: Eliseev Dmitry
     * */
    public final void fillWith(final int v) {
        for (int row = 0; row < m_Size.getX(); row++)
            for (int column = 0; column < m_Size.getY(); column++)
                m_Elements[row][column] = v;
    } /* End of 'Matrix::fillWith' method */


    /* *
     * METHOD: Gets matrix size
     * RETURN: Matrix size
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2i getSize() {
        return m_Size;
    } /* End of 'Matrix::getSize' method */

    @Override
    public final int hashCode() {
        int hashCode = 0;

        for (int x = 0; x < m_Size.getX(); x++)
            for (int y = 0; y < m_Size.getY(); y++)
                hashCode += m_Elements[x][y] * x * y;

        return hashCode;
    } /* End of 'Matrix::hashCode' method */

    @Override
    public final boolean equals(final Object obj) {
        return obj instanceof Matrix && equals(this, (Matrix) obj);
    } /* End of 'Matrix::equals' method */

    /* *
     * METHOD: Compares two matrices (per-element comparation)
     * RETURN: True if matrices are equal, False otherwise
     *  PARAM: [IN] matrix1 - first matrix
     *  PARAM: [IN] matrix2 - second matrix
     * AUTHOR: Eliseev Dmitry
     * */
    public static boolean equals(final Matrix matrix1, final Matrix matrix2) {
        if ((matrix1 == null)&&(matrix2 == null))
            return true;

        if ((matrix1 == null)||(matrix2 == null))
            return false;

        if (!matrix1.m_Size.equals(matrix2.m_Size))
            return false;

        for (int row = 0; row < matrix1.m_Size.getX(); row++)
            for (int column = 0; column < matrix1.m_Size.getY(); column++)
                if (matrix1.m_Elements[row][column] != matrix2.m_Elements[row][column])
                    return false;

        return true;
    } /* End of 'Matrix::equals' method */

    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int row = 0; row < m_Size.getX(); row++) {
            for (int column = 0; column < m_Size.getY(); column++)
                sb.append(m_Elements[row][column]).append(" ");
            sb.append("\n");
        }

        return sb.toString();
    } /* End of 'Matrix::toString' method */
} /* End of 'Matrix' class */
