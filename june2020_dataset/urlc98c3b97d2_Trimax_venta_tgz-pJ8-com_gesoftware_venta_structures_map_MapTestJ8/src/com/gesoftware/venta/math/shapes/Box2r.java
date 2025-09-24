package com.gesoftware.venta.math.shapes;

import com.gesoftware.venta.math.tools.Mathematics;
import com.gesoftware.venta.math.vectors.Vec2r;

import java.io.Serializable;

/**
 * Rectangle class definition
 **/
public class Box2r implements Serializable {
    private final Vec2r m_Position;
    private final Vec2r m_Size;

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] position - rectangle's position
     *  PARAM: [IN] size     - rectangle's sides
     * AUTHOR: Eliseev Dmitry
     * */
    public Box2r(final Vec2r position, final Vec2r size) {
        m_Position = position;
        m_Size     = size;
    } /* End of 'Box2r::Box2r' method */

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] x      - rectangle's position by x
     *  PARAM: [IN] y      - rectangle's position by y
     *  PARAM: [IN] width  - rectangle's width
     *  PARAM: [IN] height - rectangle's height
     * AUTHOR: Eliseev Dmitry
     * */
    public Box2r(final double x, final double y, final double width, final double height) {
        this(new Vec2r(x, y), new Vec2r(width, height));
    } /* End of 'Box2r::Box2r' method */

    /* *
     * METHOD: Gets rectangle's position
     * RETURN: Rectangle's position
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2r getPosition() {
        return m_Position;
    } /* End of 'Box2r::getPosition' method */

    /* *
     * METHOD: Sets rectangle's position
     *  PARAM: [IN] position - new rectangle's position
     * AUTHOR: Eliseev Dmitry
     * */
    public final void setPosition(final Vec2r position) {
        m_Position.set(position);
    } /* End of 'Box2r::setPosition' method */

    /* *
     * METHOD: Gets rectangle's size
     * RETURN: Rectangle's size
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2r getSize() {
        return m_Size;
    } /* End of 'Box2r::getSize' method */

    /* *
     * METHOD: Sets rectangle's size
     *  PARAM: [IN] size - new rectangle's size
     * AUTHOR: Eliseev Dmitry
     * */
    public final void setSize(final Vec2r size) {
        m_Size.set(size);
    } /* End of 'Box2r::setSize' method */

    /* *
     * METHOD: Checks intersection of two rectangles
     * RETURN: True if rectangles are intersects, false otherwise
     *  PARAM: [IN] rect - rectangle to check intersection with
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean intersects(final Box2r rect) {
        final boolean xOverlap = Mathematics.isInRange(m_Position.getX(), rect.getPosition().getX(), rect.getPosition().getX() + rect.getSize().getX()) ||
                                 Mathematics.isInRange(rect.getPosition().getX(), m_Position.getX(), m_Position.getX() + m_Size.getX());

        final boolean yOverlap = Mathematics.isInRange(m_Position.getY(), rect.getPosition().getY(), rect.getPosition().getY() + rect.getSize().getY()) ||
                                 Mathematics.isInRange(rect.getPosition().getY(), m_Position.getY(), m_Position.getY() + m_Size.getY());

        return xOverlap && yOverlap;
    } /* End of 'Box2r::intersects' method */

    /* *
     * METHOD: Checks if rectangle is inside rectangle
     * RETURN: True if rectangle inside given, false otherwise
     *  PARAM: [IN] rect - outer rectangle
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean inside(final Box2r rect) {
        return m_Position.getX() >= rect.getPosition().getX() &&
               m_Position.getY() >= rect.getPosition().getY() &&
               m_Position.getX() + m_Size.getX() <= rect.getPosition().getX() + rect.getSize().getX() &&
               m_Position.getY() + m_Size.getY() <= rect.getPosition().getY() + rect.getSize().getY();
    } /* End of 'Box2r::inside' method */

    /* *
     * METHOD: Checks if point is inside rectangle
     * RETURN: True if point is inside rectangle, False otherwise
     *  PARAM: [IN] point - point to check
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean contains(final Vec2r point) {
        return point.getX() >= m_Position.getX() &&
               point.getY() >= m_Position.getY() &&
               point.getX() <= m_Position.getX() + m_Size.getX() &&
               point.getY() <= m_Position.getY() + m_Size.getY();
    } /* End of 'Box2r::contains' method */

    @Override
    public final boolean equals(final Object obj) {
        return obj instanceof Box2r && ((Box2r) obj).getPosition().equals(m_Position) && ((Box2r) obj).getSize().equals(m_Size);
    } /* End of 'Box2r::equals' method */

    @Override
    public String toString() {
        return "{" + m_Position + ", " + m_Size + "}";
    } /* End of 'Box2r::toString' method */
} /* End of 'Box2r' class */
