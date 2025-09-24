package com.gesoftware.venta.math.shapes;

import com.gesoftware.venta.math.tools.Mathematics;
import com.gesoftware.venta.math.vectors.Vec2i;

/**
 * Rectangle class definition
 **/
public class Box2i {
    private final Vec2i m_Position;
    private final Vec2i m_Size;

    /* *
     * METHOD: Class constructor
     * AUTHOR: Eliseev Dmitry
     * */
    public Box2i() {
        this(new Vec2i(), new Vec2i());
    } /* End of 'Box2i::Box2i' method */

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] position - rectangle's position
     *  PARAM: [IN] size     - rectangle's sides
     * AUTHOR: Eliseev Dmitry
     * */
    public Box2i(final Vec2i position, final Vec2i size) {
        m_Position = position;
        m_Size     = size;
    } /* End of 'Box2i::Box2i' method */

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] x      - rectangle's position by x
     *  PARAM: [IN] y      - rectangle's position by y
     *  PARAM: [IN] width  - rectangle's width
     *  PARAM: [IN] height - rectangle's height
     * AUTHOR: Eliseev Dmitry
     * */
    public Box2i(final int x, final int y, final int width, final int height) {
        this(new Vec2i(x, y), new Vec2i(width, height));
    } /* End of 'Box2i::Box2i' method */

    /* *
     * METHOD: Gets rectangle's position
     * RETURN: Rectangle's position
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2i getPosition() {
        return m_Position;
    } /* End of 'Box2i::getPosition' method */

    /* *
     * METHOD: Sets rectangle's position
     *  PARAM: [IN] position - new rectangle's position
     * AUTHOR: Eliseev Dmitry
     * */
    public final void setPosition(final Vec2i position) {
        m_Position.set(position);
    } /* End of 'Box2i::setPosition' method */

    /* *
     * METHOD: Gets rectangle's size
     * RETURN: Rectangle's size
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2i getSize() {
        return m_Size;
    } /* End of 'Box2i::Box2i' method */

    /* *
     * METHOD: Sets rectangle's size
     *  PARAM: [IN] size - new rectangle's size
     * AUTHOR: Eliseev Dmitry
     * */
    public final void setSize(final Vec2i size) {
        m_Size.set(size);
    } /* End of 'Box2i::setSize' method */

    /* *
     * METHOD: Gets rectangle's width
     * RETURN: Rectangle's width
     * AUTHOR: Eliseev Dmitry
     * */
    public final int getWidth() {
        return m_Size.getX();
    } /* End of 'Box2i::getWidth' method */

    /* *
     * METHOD: Gets rectangle's height
     * RETURN: Rectangle's height
     * AUTHOR: Eliseev Dmitry
     * */
    public final int getHeight() {
        return m_Size.getY();
    } /* End of 'Box2i::getHeight' method */

    /* *
     * METHOD: Checks intersection of two rectangles
     * RETURN: True if rectangles are intersects, false otherwise
     *  PARAM: [IN] rect - rectangle to check intersection with
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean intersects(final Box2i rect) {
        final boolean xOverlap = Mathematics.isInRange(m_Position.getX(), rect.getPosition().getX(), rect.getPosition().getX() + rect.getSize().getX()) ||
                                 Mathematics.isInRange(rect.getPosition().getX(), m_Position.getX(), m_Position.getX() + m_Size.getX());

        final boolean yOverlap = Mathematics.isInRange(m_Position.getY(), rect.getPosition().getY(), rect.getPosition().getY() + rect.getSize().getY()) ||
                                 Mathematics.isInRange(rect.getPosition().getY(), m_Position.getY(), m_Position.getY() + m_Size.getY());

        return xOverlap && yOverlap;
    } /* End of 'Box2i::intersects' method */

    /* *
     * METHOD: Checks if rectangle is inside rectangle
     * RETURN: True if rectangle inside given, false otherwise
     *  PARAM: [IN] rect - outer rectangle
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean inside(final Box2i rect) {
        return m_Position.getX() >= rect.getPosition().getX() &&
               m_Position.getY() >= rect.getPosition().getY() &&
               m_Position.getX() + m_Size.getX() <= rect.getPosition().getX() + rect.getSize().getX() &&
               m_Position.getY() + m_Size.getY() <= rect.getPosition().getY() + rect.getSize().getY();
    } /* End of 'Box2i::inside' method */

    /* *
     * METHOD: Checks if point is inside rectangle
     * RETURN: True if point is inside rectangle, False otherwise
     *  PARAM: [IN] point - point to check
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean contains(final Vec2i point) {
        return point.getX() >= m_Position.getX() &&
               point.getY() >= m_Position.getY() &&
               point.getX() <= m_Position.getX() + m_Size.getX() &&
               point.getY() <= m_Position.getY() + m_Size.getY();
    } /* End of 'Box2i::contains' method */

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Box2i && ((Box2i) obj).getPosition().equals(m_Position) && ((Box2i) obj).getSize().equals(m_Size);
    } /* End of 'Box2i::equals' method */

    @Override
    public String toString() {
        return "{" + m_Position + ", " + m_Size + "}";
    } /* End of 'Box2i::toString' method */
} /* End of 'Box2i' class */
