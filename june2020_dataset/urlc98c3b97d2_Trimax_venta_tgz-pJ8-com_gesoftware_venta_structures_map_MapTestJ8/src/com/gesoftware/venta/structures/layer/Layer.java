package com.gesoftware.venta.structures.layer;

import com.gesoftware.venta.math.vectors.Vec2i;

import java.io.Serializable;

/**
 * Layer class definition
 **/
public abstract class Layer<T extends Serializable> implements Serializable {
    /* Layer's data */
    private final T m_Data[][];

    /* Layer's size */
    private final Vec2i m_Size;

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] width  - layer width
     *  PARAM: [IN] height - layer height
     * AUTHOR: Eliseev Dmitry
     * */
    protected Layer(final int width, final int height) {
        m_Data = createLayer(width, height);
        m_Size = new Vec2i(width, height);
    } /* End of 'Layer::Layer' method */

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] size - layer size
     * AUTHOR: Eliseev Dmitry
     * */
    protected Layer(final Vec2i size) {
        this(size.getX(), size.getY());
    } /* End of 'Layer::Layer' method */

    /* *
     * METHOD: Gets item by it's position
     * RETURN: Item if success, IndexOutOfBounds exception otherwise
     *  PARAM: [IN] x - item absciss
     *  PARAM: [IN] y - item ordinate
     * AUTHOR: Eliseev Dmitry
     * */
    public T getItem(final int x, final int y) {
        if ((x < 0) || (y < 0) || (x >= m_Size.getX()) || (y >= m_Size.getY()))
            throw new IndexOutOfBoundsException("Index [" + x + ", " + y + "] is out of bounds. Layer size: " + m_Size);

        return m_Data[x][y];
    } /* End of 'Layer::getItem' method */

    /* *
     * METHOD: Gets item by it's position
     * RETURN: Item if success, IndexOutOfBounds exception otherwise
     *  PARAM: [IN] position - item position
     * AUTHOR: Eliseev Dmitry
     * */
    public T getItem(final Vec2i position) {
        return getItem(position.getX(), position.getY());
    } /* End of 'Layer::getItem' method */

    /* *
     * METHOD: Sets item by it's position
     * RETURN: Item if success, IndexOutOfBounds exception otherwise
     *  PARAM: [IN] x    - item absciss
     *  PARAM: [IN] y    - item ordinate
     *  PARAM: [IN] item - item to set
     * AUTHOR: Eliseev Dmitry
     * */
    public void setItem(final int x, final int y, final T item) {
        if ((x < 0) || (y < 0) || (x >= m_Size.getX()) || (y >= m_Size.getY()))
            throw new IndexOutOfBoundsException("Index [" + x + ", " + y + "] is out of bounds. Layer size: " + m_Size);

        m_Data[x][y] = item;
    } /* End of 'Layer::setItem' method */

    /* *
     * METHOD: Sets item by it's position
     * RETURN: Item if success, IndexOutOfBounds exception otherwise
     *  PARAM: [IN] position - position to set
     *  PARAM: [IN] item     - item to set
     * AUTHOR: Eliseev Dmitry
     * */
    public void setItem(final Vec2i position, final T item) {
        setItem(position.getX(), position.getY(), item);
    } /* End of 'Layer::setItem' method */

    /* *
     * METHOD: Gets layer's width
     * RETURN: Layer's width
     * AUTHOR: Eliseev Dmitry
     * */
    public final int getWidth() {
        return m_Size.getX();
    } /* End of 'Layer::getWidth' method */

    /* *
     * METHOD: Gets layer's height
     * RETURN: Layer's height
     * AUTHOR: Eliseev Dmitry
     * */
    public final int getHeight() {
        return m_Size.getY();
    } /* End of 'Layer::getHeight' method */

    @Override
    public final String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        /* Append each item code */
        for (int x = 0; x < m_Size.getX(); x++)
            for (int y = 0; y < m_Size.getY(); y++)
                stringBuilder.append(getItem(x, y));

        return stringBuilder.toString();
    } /* End of 'Layer::toString' method */

    /* *
     * METHOD: Creates layer
     * RETURN: Created layer
     *  PARAM: [IN] width  - layer's width
     *  PARAM: [IN] height - layer's height
     * AUTHOR: Eliseev Dmitry
     * */
    protected abstract T[][] createLayer(final int width, final int height);
} /* End of 'Layer' class */
