package com.gesoftware.venta.math.segment;

import com.gesoftware.venta.math.shapes.Box2r;
import com.gesoftware.venta.math.tools.Mathematics;
import com.gesoftware.venta.math.vectors.Vec2r;

/**
 * Segment class definition
 **/
public final class Segment {
    private final Vec2r m_A;
    private final Vec2r m_B;

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] a - segment startup vertex
     *  PARAM: [IN] b - segment end vertex
     * AUTHOR: Eliseev Dmitry
     * */
    public Segment(final Vec2r a, final Vec2r b) {
        m_A = a;
        m_B = b;
    } /* End of 'Segment::Segment' method */

    /* *
     * METHOD: Gets segment direction
     * RETURN: Segment direction
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2r getDirection() {
        return Vec2r.diff(m_B, m_A);
    } /* End of 'Segment::getDirection' method */

    /* *
     * METHOD: Gets segment length
     * RETURN: Segment length
     * AUTHOR: Eliseev Dmitry
     * */
    public final double getLength() {
        return Mathematics.distance(m_A, m_B);
    } /* End of 'Segment::getLength' method */

    /* *
     * METHOD: Gets first point
     * RETURN: First point
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2r getA() {
        return m_A;
    } /* End of 'Segment::getA' method */

    /* *
     * METHOD: Gets second point
     * RETURN: Second point
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2r getB() {
        return m_B;
    } /* End of 'Segment::getB' method */

    /* *
     * METHOD: Checks intersection with another segment
     * RETURN: True if there is intersection, False otherwise
     *  PARAM: [IN] segment - segment to check intersection with
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean intersects(final Segment segment) {
        /* Is it the same segment */
        if (equals(segment))
            return true;

        /* Do we have bounding boxes intersection */
        return getBoundingBox().intersects(segment.getBoundingBox()) && isSegmentTouchesOrCrossesLine(this, segment) && isSegmentTouchesOrCrossesLine(segment, this);
    } /* End of 'Segment' intersects method */

    private boolean isLineContainsPoint(final Segment segment, final Vec2r point) {
        final Segment line = new Segment(new Vec2r(), segment.getDirection());
        return Mathematics.equals(cross(line.m_B, Vec2r.diff(point, segment.m_A)), 0.0);
    }

    private boolean isPointRightOfLine(final Segment segment, final Vec2r point) {
        final Segment line = new Segment(new Vec2r(), segment.getDirection());
        return cross(line.m_B, Vec2r.diff(point, segment.m_A)) < 0.0;
    }

    private boolean isSegmentTouchesOrCrossesLine(final Segment a, final Segment b) {
        return (isLineContainsPoint(a, b.getA()) || isLineContainsPoint(a, b.getB())) ||
               (isPointRightOfLine(a, b.getA()) ^ isPointRightOfLine(a, b.getB()));
    }

    private double cross(final Vec2r a, final Vec2r b) {
        return a.getX() * b.getY() - b.getX() * a.getY();
    }

    public final Box2r getBoundingBox() {
        final Vec2r LT = new Vec2r(Math.min(m_A.getX(), m_B.getX()), Math.min(m_A.getY(), m_B.getY()));
        final Vec2r RB = new Vec2r(Math.max(m_A.getX(), m_B.getX()), Math.max(m_A.getY(), m_B.getY()));
        return new Box2r(LT, Vec2r.diff(RB, LT));
    }

    /* *
     * METHOD: Checks if a segment is contain a point
     * RETURN: True if point is in segment, False otherwise
     *  PARAM: [IN] point - point to check
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean contains(final Vec2r point) {
        return Mathematics.equals(Mathematics.distance(m_A, point) + Mathematics.distance(point, m_B), getLength());
    } /* End of 'Segment::contains' method */

    @Override
    public final int hashCode() {
        final int hashFirst  = m_A != null ? m_A.hashCode() : 0;
        final int hashSecond = m_B != null ? m_B.hashCode() : 0;

        return (hashFirst + hashSecond) * hashSecond + hashFirst;
    } /* End of 'Segment::hashCode' method */

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Segment && m_A.equals(((Segment) obj).m_A) && m_B.equals(((Segment) obj).m_B);
    } /* End of 'Segment::equals' method */

    @Override
    public final String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<").append(m_A).append("; ").append(m_B).append(">");
        return stringBuilder.toString();
    } /* End of 'Segment::toString' method */
} /* End of 'Segment' class */
