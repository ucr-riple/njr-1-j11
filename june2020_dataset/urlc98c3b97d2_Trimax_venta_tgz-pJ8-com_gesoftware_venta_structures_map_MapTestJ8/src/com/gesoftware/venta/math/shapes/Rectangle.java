package com.gesoftware.venta.math.shapes;

import com.gesoftware.venta.math.vectors.Vec2r;

import java.io.Serializable;

/**
 * Rectangle class definition
 **/
public class Rectangle implements Serializable {
    private final Vec2r m_Position;
    private final Vec2r m_Size;

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] position - rectangle's position
     *  PARAM: [IN] size     - rectangle's sides
     * AUTHOR: Eliseev Dmitry
     * */
    public Rectangle(final Vec2r position, final Vec2r size) {
        m_Position = position;
        m_Size = size;
    } /* End of 'Rectangle::Rectangle' method */

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] x      - rectangle's position by x
     *  PARAM: [IN] y      - rectangle's position by y
     *  PARAM: [IN] width  - rectangle's width
     *  PARAM: [IN] height - rectangle's height
     * AUTHOR: Eliseev Dmitry
     * */
    public Rectangle(final double x, final double y, final double width, final double height) {
        m_Position = new Vec2r(x, y);
        m_Size = new Vec2r(width, height);
    } /* End of 'Rectangle::Rectangle' method */

    /* *
     * METHOD: Gets rectangle's position
     * RETURN: Rectangle's position
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2r getPosition() {
        return m_Position;
    } /* End of 'Rectangle::getPosition' method */

    /* *
     * METHOD: Gets rectangle's size
     * RETURN: Rectangle's size
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2r getSize() {
        return m_Size;
    } /* End of 'Rectangle::getSize' method */

    /* *
     * METHOD: Determine if value from [min; max]
     * RETURN: True if yes, False otherwise
     *  PARAM: [IN] value - value to check
     *  PARAM: [IN] min   - left border
     *  PARAM: [IN] max   - right border
     * AUTHOR: Eliseev Dmitry
     * */
    private boolean valueInRange(final double value, final double min, final double max) {
        return (value >= min) && (value <= max);
    } /* End of 'Rectangle::valueInRange' method */

    /* *
     * METHOD: Checks intersection of two rectangles
     * RETURN: True if rectangles are intersects, false otherwise
     *  PARAM: [IN] rect - rectangle to check intersection with
     * AUTHOR: Eliseev Dmitry
     * */
    public boolean intersects(final Rectangle rect) {
        boolean xOverlap = valueInRange(m_Position.getX(), rect.getPosition().getX(), rect.getPosition().getX() + rect.getSize().getX()) ||
                           valueInRange(rect.getPosition().getX(), m_Position.getX(), m_Position.getX() + m_Size.getX());

        boolean yOverlap = valueInRange(m_Position.getY(), rect.getPosition().getY(), rect.getPosition().getY() + rect.getSize().getY()) ||
                           valueInRange(rect.getPosition().getY(), m_Position.getY(), m_Position.getY() + m_Size.getY());

        return xOverlap && yOverlap;
    } /* End of 'Rectangle::intersects' method */

    /* *
     * METHOD: Checks if rectangle is inside rectangle
     * RETURN: True if rectangle inside given, false otherwise
     *  PARAM: [IN] rect - outer rectangle
     * AUTHOR: Eliseev Dmitry
     * */
    public boolean inside(final Rectangle rect) {
        return m_Position.getX() >= rect.getPosition().getX() &&
               m_Position.getY() >= rect.getPosition().getY() &&
               m_Position.getX() + m_Size.getX() <= rect.getPosition().getX() + rect.getSize().getX() &&
               m_Position.getY() + m_Size.getY() <= rect.getPosition().getY() + rect.getSize().getY();
    } /* End of 'Rectangle::inside' method */

    /* *
     * METHOD: Checks if point is inside rectangle
     * RETURN: True if point is inside rectangle, False otherwise
     *  PARAM: [IN] point - point to check
     * AUTHOR: Eliseev Dmitry
     * */
    public boolean contains(final Vec2r point) {
        return point.getX() >= m_Position.getX() &&
               point.getY() >= m_Position.getY() &&
               point.getX() <= m_Position.getX() + m_Size.getX() &&
               point.getY() <= m_Position.getY() + m_Size.getY();
    } /* End of 'Rectangle::contains' method */

    @Override
    public final boolean equals(final Object obj) {
        return obj instanceof Rectangle && ((Rectangle) obj).getPosition().equals(m_Position) && ((Rectangle) obj).getSize().equals(m_Size);
    } /* End of 'Rectangle::equals' method */

    @Override
    public String toString() {
        return "{" + m_Position + ", " + m_Size + "}";
    } /* End of 'Rectangle::toString' method */
} /* End of 'Rectangle' class */
